{
  description = "A clj-nix flake";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
    clj-nix.url = "github:jlesquembre/clj-nix";
  };

  outputs =
    {
      self,
      nixpkgs,
      flake-utils,
      clj-nix,
    }:

    flake-utils.lib.eachDefaultSystem (
      system:
      let
        pkgs = import nixpkgs {
          inherit system;
          overlays = [
            clj-nix.overlays.default
          ];
        };

        cerulean-src = pkgs.stdenv.mkDerivation {
          name = "cerulean-source";
          src = self;
          buildPhase = '':'';
          installPhase = ''
          mkdir -p $out
          cp -r * $out/
          cp -r ${npmDeps}/node_modules $out/node_modules
          '';
        };

        clj-bin = pkgs.mkCljBin {
            projectSrc = cerulean-src;
            name = "cerulean";
            main-ns = "net.coruscation.cerulean.cli";
            version = "1.0";
            jdkRunner = pkgs.jdk25_headless;
            buildCommand = ''
            clj -X:uberjar
            export jarPath=./target/*.jar
            '';
          };
        deps-cache = pkgs.mk-deps-cache { lockfile = ./deps-lock.json; };
        deps = with pkgs; [
          (nodejs-slim.override {
            enableNpm = true;
          })
          (emacs-nox.override {
            withNativeCompilation=false;
            withMailutils=false;
            withSQLite3=false;
            withDbus=false;
            withSystemd=false;
            withGpm=false;
            withToolkitScrollBars=false;
            withTreeSitter=false;
            withWebP=false;
          })
          gitMinimal
          babashka-unwrapped
          pkgs.dockerTools.binSh
          pkgs.dockerTools.usrBinEnv
        ];

        dev-deps = with pkgs; [
          jdk25_headless
          (pkgs.clojure.override{jdk=pkgs.jdk25_headless;})
        ];

        ci-deps = with pkgs; [
          gnupg
        ];

        npmDeps = pkgs.buildNpmPackage(finalAttrs: {
          src = self;
          pname = "cerulean-npm-deps";
          version = "0.0";
          dontNpmBuild = true;
          npmDepsHash = (builtins.readFile ./npm-deps.sha256);
          buildPhase = '':'';
          installPhase = ''
          mkdir $out
          cp -r ./node_modules $out/node_modules
          '';
        });

        make-docker =
          { extra-deps, name }:
          (pkgs.dockerTools.buildLayeredImage {
            name = name;
            created = "now";
            tag = "latest";
            contents = [
              (pkgs.writeShellApplication {
                name = "cerulean";
                text = ''
                git config --global --add safe.directory /workspace
                ${clj-bin}/bin/cerulean "$@"
                '';
              })
              pkgs.gnumake
              pkgs.dockerTools.binSh
              pkgs.dockerTools.usrBinEnv
              (pkgs.buildEnv {
                name = "image-root";
                paths = deps  ++ extra-deps;
                pathsToLink = [
                  "/bin"
                  "/usr/bin/"
                ];
              })
            ];
            enableFakechroot = true;
            fakeRootCommands = ''
              mkdir -p /tmp
              mkdir -p /root
              chmod 777 /tmp
              chmod 777 /root
              ln -s "${deps-cache}/.m2" /root/.m2
              mkdir -p /cerulean
              cp -r "${cerulean-src}"/* /cerulean/
              find -L /cerulean -type d -print0 | xargs -0 chmod ugo+rwx
              find -L /cerulean -type f -print0 | xargs -0 chmod ugo+rw
            '';
            config = {
              Env = [
                "HOME=/root/"
              ];
              WorkingDir = "/cerulean";
              Entrypoint = "cerulean";
            };
          });
      in
      {
        packages = {
          uberjar = clj-bin;
          docker = (make-docker {
            name = "cerulean";
            extra-deps = [ ]; });
          docker-dev = (
            make-docker {
              name = "cerulean-dev";
              extra-deps = [
                (pkgs.clojure.override{jdk=pkgs.jdk25_headless;})
                pkgs.toybox
              ];
            }
          );
        };
        devShells.default = pkgs.mkShell {
          packages =  [] ++ deps ++ dev-deps;
        };
        devShells.ci = pkgs.mkShell {
          packages =  [] ++ deps ++ dev-deps ++ ci-deps;
        };
      }
    );
}
