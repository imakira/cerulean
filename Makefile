MAKEFLAGS += --always-make
DOCKER_USERNAME = coruscation
VERSION_FILE = .cerulean-version

git-add-all:
	git add ./

watch:
	clj -X:watch

build:
	clj -X:build

styles-dev:
	npx @tailwindcss/cli -i ./resources/main.css -o ./public/main.css --watch

styles-release:
	npx @tailwindcss/cli -m -i ./resources/main.css -o ./public/main.css

docker-build-and-load: git-add-all
	docker image rm cerulean:latest || true
	nix build .#docker
	docker load < result
	docker image tag cerulean:latest ${DOCKER_USERNAME}/cerulean:latest

docker-build-and-load-dev: git-add-all
	docker image rm cerulean-dev:latest || true
	nix build .#docker-dev
	docker load < result

define with-secrets
	env env $$(gpg --batch --decrypt --passphrase $$CERULEAN_PASSPHRASE  ./secrets.gpg | xargs) $(MAKE) $1
endef

docker-publish-impl: # docker-build-and-load
	docker login -u ${DOCKER_USERNAME} -p $${DOCKER_TOKEN}; \
	docker push ${DOCKER_USERNAME}/cerulean; \
	cerulean_version="$$(cat $(VERSION_FILE))"; \
	if test -n "$${cerulean_version}"; then \
		docker image tag cerulean:latest ${DOCKER_USERNAME}/cerulean:"$${cerulean_version}"; \
		docker push ${DOCKER_USERNAME}/cerulean:"$${cerulean_version}"; \
	fi; \

docker-publish:
	$(call with-secrets,docker-publish-impl)

nix-deps-lock: git-add-all
	nix run github:jlesquembre/clj-nix#deps-lock

nix-npm-deps-lock: git-add-all
	nix run nixpkgs#prefetch-npm-deps package-lock.json > npm-deps.sha256

live-static-site:
	cd workspace/docs && python -m http.server

uberjar:
	clj -X:uberjar

test:
	clj -M:uberjar:kaocha --skip-meta slow

test-slow:
	clj -M:uberjar:kaocha --focus-meta slow

test-all:
	clj -M:uberjar:kaocha

npm-update:
	npm update

clj-deps-update:
	neil dep update

update-dependencies: | npm-update clj-deps-update nix-deps-lock nix-npm-deps-lock

ci-init:
	git config --global user.name "cerulean-ci"
	git config --global user.email "ci@coruscation.net"

prepare-release-version:
	echo $$(date -u +"%Y-%m-%dT%H.%M.%S") > $(VERSION_FILE)
	git add $(VERSION_FILE)

release: | test-all
	$(MAKE) docker-publish

release-if-necessary:
	if test -n "$$(git rev-list  --after='24 hours' HEAD)"; then \
		echo "release"; \
		$(MAKE) release;\
	else \
		echo "last commit committed before 24 hours, do not release"; \
	fi \

ci-release-nightly: | ci-init prepare-release-version
	$(MAKE) release-if-necessary

ci-test-all: | ci-init
	$(MAKE) test-all
