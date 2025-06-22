goog.provide('uix.core');
goog.scope(function(){
  uix.core.goog$module$goog$object = goog.module.get('goog.object');
});
var module$node_modules$react$index=shadow.js.require("module$node_modules$react$index", {});
uix.core.built_in_static_method_names = new cljs.core.PersistentVector(null, 5, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"childContextTypes","childContextTypes",578717991),new cljs.core.Keyword(null,"contextTypes","contextTypes",-2023853910),new cljs.core.Keyword(null,"contextType","contextType",1033066077),new cljs.core.Keyword(null,"getDerivedStateFromProps","getDerivedStateFromProps",-991834739),new cljs.core.Keyword(null,"getDerivedStateFromError","getDerivedStateFromError",166658477)], null);
/**
 * Creates class based React component
 */
uix.core.create_class = (function uix$core$create_class(p__102325){
var map__102326 = p__102325;
var map__102326__$1 = cljs.core.__destructure_map(map__102326);
var fields = map__102326__$1;
var contextType = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"contextType","contextType",1033066077));
var componentDidUpdate = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"componentDidUpdate","componentDidUpdate",-1983477981));
var componentDidCatch = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"componentDidCatch","componentDidCatch",821717733));
var displayName = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"displayName","displayName",-809144601));
var childContextTypes = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"childContextTypes","childContextTypes",578717991));
var getSnapshotBeforeUpdate = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"getSnapshotBeforeUpdate","getSnapshotBeforeUpdate",861122184));
var contextTypes = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"contextTypes","contextTypes",-2023853910));
var getDerivedStateFromProps = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"getDerivedStateFromProps","getDerivedStateFromProps",-991834739));
var getDerivedStateFromError = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"getDerivedStateFromError","getDerivedStateFromError",166658477));
var componentWillUnmount = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"componentWillUnmount","componentWillUnmount",1573788814));
var shouldComponentUpdate = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"shouldComponentUpdate","shouldComponentUpdate",1795750960));
var render = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"render","render",-1408033454));
var defaultProps = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"defaultProps","defaultProps",-533483149));
var getInitialState = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"getInitialState","getInitialState",1541760916));
var constructor$ = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"constructor","constructor",-1953928811));
var componentDidMount = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102326__$1,new cljs.core.Keyword(null,"componentDidMount","componentDidMount",955710936));
var methods$ = uix.lib.map__GT_js(cljs.core.apply.cljs$core$IFn$_invoke$arity$variadic(cljs.core.dissoc,fields,new cljs.core.Keyword(null,"displayName","displayName",-809144601),new cljs.core.Keyword(null,"getInitialState","getInitialState",1541760916),new cljs.core.Keyword(null,"constructor","constructor",-1953928811),cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([new cljs.core.Keyword(null,"render","render",-1408033454),uix.core.built_in_static_method_names], 0)));
var static_methods = uix.lib.map__GT_js(cljs.core.select_keys(fields,uix.core.built_in_static_method_names));
var ctor = (function (props){
var this$ = this;
module$node_modules$react$index.Component.apply(this$,arguments);

if(cljs.core.truth_(constructor$)){
(constructor$.cljs$core$IFn$_invoke$arity$2 ? constructor$.cljs$core$IFn$_invoke$arity$2(this$,props) : constructor$.call(null,this$,props));
} else {
}

if(cljs.core.truth_(getInitialState)){
(this$.state = (getInitialState.cljs$core$IFn$_invoke$arity$1 ? getInitialState.cljs$core$IFn$_invoke$arity$1(this$) : getInitialState.call(null,this$)));
} else {
}

return this$;
});
uix.core.goog$module$goog$object.extend(ctor.prototype,module$node_modules$react$index.Component.prototype,methods$);

if(cljs.core.truth_(render)){
(ctor.prototype.render = render);
} else {
}

uix.core.goog$module$goog$object.extend(ctor,module$node_modules$react$index.Component,static_methods);

if(cljs.core.truth_(displayName)){
(ctor.displayName = displayName);

(ctor.cljs$lang$ctorStr = displayName);

(ctor.cljs$lang$ctorPrWriter = (function (this$,writer,opt){
return cljs.core._write(writer,displayName);
}));
} else {
}

(ctor.cljs$lang$type = true);

(ctor.prototype.constructor = ctor);

(ctor.uix_component_QMARK_ = true);

return ctor;
});
/**
 * Creates React's ref type object.
 */
uix.core.create_ref = (function uix$core$create_ref(){
return module$node_modules$react$index.createRef();
});
uix.core.glue_args = (function uix$core$glue_args(props){
var G__102347 = props.argv;
if(cljs.core.truth_(props.children)){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__102347,new cljs.core.Keyword(null,"children","children",-940561982),props.children);
} else {
return G__102347;
}
});
uix.core.memo_compare_args = (function uix$core$memo_compare_args(a,b){
return cljs.core._EQ_.cljs$core$IFn$_invoke$arity$2(uix.core.glue_args(a),uix.core.glue_args(b));
});
/**
 * Takes component `f` and optional comparator function `should-update?`
 *   that takes previous and next props of the component.
 *   Returns memoized `f`.
 * 
 *   When `should-update?` is not provided uses default comparator
 *   that compares props with clojure.core/=
 */
uix.core.memo = (function uix$core$memo(var_args){
var G__102361 = arguments.length;
switch (G__102361) {
case 1:
return uix.core.memo.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return uix.core.memo.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(uix.core.memo.cljs$core$IFn$_invoke$arity$1 = (function (f){
return uix.core.memo.cljs$core$IFn$_invoke$arity$2(f,uix.core.memo_compare_args);
}));

(uix.core.memo.cljs$core$IFn$_invoke$arity$2 = (function (f,should_update_QMARK_){
var fm = module$node_modules$react$index.memo(f,should_update_QMARK_);
if(cljs.core.truth_(f.uix_component_QMARK_)){
(fm.uix_component_QMARK_ = true);
} else {
}

return fm;
}));

(uix.core.memo.cljs$lang$maxFixedArity = 2);

/**
 * Takes initial value or a function that computes it and returns a stateful value,
 *   and a function to update it.
 * 
 *   See: https://react.dev/reference/react/useState
 */
uix.core.use_state = (function uix$core$use_state(value){
return uix.hooks.alpha.use_state(value);
});
/**
 * An alternative to `use-state`. Accepts a reducer of type (state, action) => new-state,
 *   and returns the current state paired with a dispatch method.
 * 
 *   See: https://react.dev/reference/react/useReducer
 */
uix.core.use_reducer = (function uix$core$use_reducer(var_args){
var G__102376 = arguments.length;
switch (G__102376) {
case 2:
return uix.core.use_reducer.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return uix.core.use_reducer.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(uix.core.use_reducer.cljs$core$IFn$_invoke$arity$2 = (function (f,value){
return uix.hooks.alpha.use_reducer.cljs$core$IFn$_invoke$arity$2(f,value);
}));

(uix.core.use_reducer.cljs$core$IFn$_invoke$arity$3 = (function (f,value,init_state){
return uix.hooks.alpha.use_reducer.cljs$core$IFn$_invoke$arity$3(f,value,init_state);
}));

(uix.core.use_reducer.cljs$lang$maxFixedArity = 3);

/**
 * Takes optional initial value and returns React's ref hook wrapped in atom-like type.
 */
uix.core.use_ref = (function uix$core$use_ref(var_args){
var G__102388 = arguments.length;
switch (G__102388) {
case 0:
return uix.core.use_ref.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return uix.core.use_ref.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(uix.core.use_ref.cljs$core$IFn$_invoke$arity$0 = (function (){
return uix.core.use_ref.cljs$core$IFn$_invoke$arity$1(null);
}));

(uix.core.use_ref.cljs$core$IFn$_invoke$arity$1 = (function (value){
var ref = uix.hooks.alpha.use_ref(null);
if((ref.current == null)){
(ref.current = (function (){var x102397 = ({"current": value});
(x102397.cljs$core$IDeref$ = cljs.core.PROTOCOL_SENTINEL);

(x102397.cljs$core$IDeref$_deref$arity$1 = (function (this$){
var this$__$1 = this;
return this$__$1.current;
}));

(x102397.cljs$core$IReset$ = cljs.core.PROTOCOL_SENTINEL);

(x102397.cljs$core$IReset$_reset_BANG_$arity$2 = (function (this$,v){
var this$__$1 = this;
return (this$__$1.current = v);
}));

(x102397.cljs$core$ISwap$ = cljs.core.PROTOCOL_SENTINEL);

(x102397.cljs$core$ISwap$_swap_BANG_$arity$2 = (function (this$,f){
var this$__$1 = this;
return (this$__$1.current = (function (){var G__102413 = this$__$1.current;
return (f.cljs$core$IFn$_invoke$arity$1 ? f.cljs$core$IFn$_invoke$arity$1(G__102413) : f.call(null,G__102413));
})());
}));

(x102397.cljs$core$ISwap$_swap_BANG_$arity$3 = (function (this$,f,a){
var this$__$1 = this;
return (this$__$1.current = (function (){var G__102422 = this$__$1.current;
var G__102423 = a;
return (f.cljs$core$IFn$_invoke$arity$2 ? f.cljs$core$IFn$_invoke$arity$2(G__102422,G__102423) : f.call(null,G__102422,G__102423));
})());
}));

(x102397.cljs$core$ISwap$_swap_BANG_$arity$4 = (function (this$,f,a,b){
var this$__$1 = this;
return (this$__$1.current = (function (){var G__102427 = this$__$1.current;
var G__102428 = a;
var G__102429 = b;
return (f.cljs$core$IFn$_invoke$arity$3 ? f.cljs$core$IFn$_invoke$arity$3(G__102427,G__102428,G__102429) : f.call(null,G__102427,G__102428,G__102429));
})());
}));

(x102397.cljs$core$ISwap$_swap_BANG_$arity$5 = (function (this$,f,a,b,xs){
var this$__$1 = this;
return (this$__$1.current = cljs.core.apply.cljs$core$IFn$_invoke$arity$5(f,this$__$1.current,a,b,xs));
}));

return x102397;
})());
} else {
}

return ref.current;
}));

(uix.core.use_ref.cljs$lang$maxFixedArity = 1);

/**
 * Creates React Context with an optional default value
 */
uix.core.create_context = (function uix$core$create_context(var_args){
var G__102437 = arguments.length;
switch (G__102437) {
case 0:
return uix.core.create_context.cljs$core$IFn$_invoke$arity$0();

break;
case 1:
return uix.core.create_context.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(uix.core.create_context.cljs$core$IFn$_invoke$arity$0 = (function (){
return module$node_modules$react$index.createContext();
}));

(uix.core.create_context.cljs$core$IFn$_invoke$arity$1 = (function (default_value){
return module$node_modules$react$index.createContext(default_value);
}));

(uix.core.create_context.cljs$lang$maxFixedArity = 1);

/**
 * Takes React context and returns its current value
 */
uix.core.use_context = (function uix$core$use_context(context){
return uix.hooks.alpha.use_context(context);
});
uix.core.use_debug = (function uix$core$use_debug(var_args){
var G__102458 = arguments.length;
switch (G__102458) {
case 1:
return uix.core.use_debug.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return uix.core.use_debug.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(uix.core.use_debug.cljs$core$IFn$_invoke$arity$1 = (function (v){
return uix.hooks.alpha.use_debug.cljs$core$IFn$_invoke$arity$1(v);
}));

(uix.core.use_debug.cljs$core$IFn$_invoke$arity$2 = (function (v,fmt){
return uix.hooks.alpha.use_debug.cljs$core$IFn$_invoke$arity$2(v,fmt);
}));

(uix.core.use_debug.cljs$lang$maxFixedArity = 2);

/**
 * Accepts a value and returns a new copy of the value that will defer to more urgent updates.
 *   If the current render is the result of an urgent update, like user input,
 *   React will return the previous value and then render the new value after the urgent render has completed.
 * 
 *   See: https://react.dev/reference/react/useDeferredValue
 */
uix.core.use_deferred_value = (function uix$core$use_deferred_value(var_args){
var G__102464 = arguments.length;
switch (G__102464) {
case 1:
return uix.core.use_deferred_value.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return uix.core.use_deferred_value.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(uix.core.use_deferred_value.cljs$core$IFn$_invoke$arity$1 = (function (v){
return uix.hooks.alpha.use_deferred_value.cljs$core$IFn$_invoke$arity$1(v);
}));

(uix.core.use_deferred_value.cljs$core$IFn$_invoke$arity$2 = (function (v,initial){
return uix.hooks.alpha.use_deferred_value.cljs$core$IFn$_invoke$arity$2(v,initial);
}));

(uix.core.use_deferred_value.cljs$lang$maxFixedArity = 2);

/**
 * Returns a stateful value for the pending state of the transition, and a function to start it.
 * 
 *   See: https://react.dev/reference/react/useTransition
 */
uix.core.use_transition = (function uix$core$use_transition(){
return uix.hooks.alpha.use_transition();
});
/**
 * Marks updates in `f` as transitions
 *   See: https://react.dev/reference/react/startTransition
 */
uix.core.start_transition = (function uix$core$start_transition(f){
return module$node_modules$react$index.startTransition(f);
});
/**
 * Returns unique ID that is stable across the server and client, while avoiding hydration mismatches.
 * 
 *   See: https://react.dev/reference/react/useId
 */
uix.core.use_id = (function uix$core$use_id(){
return uix.hooks.alpha.use_id();
});
/**
 * EXPERIMENTAL: Creates a stable event handler from a function, allowing it to be used in use-effect
 * without adding the function as a dependency.
 *   See: https://react.dev/learn/separating-events-from-effects
 */
uix.core.use_effect_event = (function uix$core$use_effect_event(f){
var ref = uix.core.use_ref.cljs$core$IFn$_invoke$arity$1(null);
cljs.core.reset_BANG_(ref,f);

return uix.hooks.alpha.use_callback((function() { 
var G__102770__delegate = function (args){
return cljs.core.apply.cljs$core$IFn$_invoke$arity$2(cljs.core.deref(ref),args);
};
var G__102770 = function (var_args){
var args = null;
if (arguments.length > 0) {
var G__102771__i = 0, G__102771__a = new Array(arguments.length -  0);
while (G__102771__i < G__102771__a.length) {G__102771__a[G__102771__i] = arguments[G__102771__i + 0]; ++G__102771__i;}
  args = new cljs.core.IndexedSeq(G__102771__a,0,null);
} 
return G__102770__delegate.call(this,args);};
G__102770.cljs$lang$maxFixedArity = 0;
G__102770.cljs$lang$applyTo = (function (arglist__102772){
var args = cljs.core.seq(arglist__102772);
return G__102770__delegate(args);
});
G__102770.cljs$core$IFn$_invoke$arity$variadic = G__102770__delegate;
return G__102770;
})()
,[uix.hooks.alpha.use_clj_deps(cljs.core.PersistentVector.EMPTY)]);
});
/**
 * For reading and subscribing from external data sources in a way that’s compatible
 *   with concurrent rendering features like selective hydration and time slicing.
 * 
 *   subscribe: function to register a callback that is called whenever the store changes
 *   get-snapshot: function that returns the current value of the store
 *   get-server-snapshot: function that returns the snapshot used during server rendering
 * 
 *   See: https://react.dev/reference/react/useSyncExternalStore
 */
uix.core.use_sync_external_store = (function uix$core$use_sync_external_store(var_args){
var G__102501 = arguments.length;
switch (G__102501) {
case 2:
return uix.core.use_sync_external_store.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return uix.core.use_sync_external_store.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(uix.core.use_sync_external_store.cljs$core$IFn$_invoke$arity$2 = (function (subscribe,get_snapshot){
return uix.hooks.alpha.use_sync_external_store.cljs$core$IFn$_invoke$arity$2(subscribe,get_snapshot);
}));

(uix.core.use_sync_external_store.cljs$core$IFn$_invoke$arity$3 = (function (subscribe,get_snapshot,get_server_snapshot){
return uix.hooks.alpha.use_sync_external_store.cljs$core$IFn$_invoke$arity$3(subscribe,get_snapshot,get_server_snapshot);
}));

(uix.core.use_sync_external_store.cljs$lang$maxFixedArity = 3);

/**
 * Lets you show a different state while an async action is underway
 * 
 *   state: current state value
 *   update-fn: (current-state, optimistic-value) => optimistic-state
 * 
 *   Returns a tuple of [optimistic-state, add-optimistic!]
 *   optimistic-state: resulting optimistic state
 *   add-optimistic!: takes optimistic-value and calls update-fn
 * 
 *   See: https://react.dev/reference/react/useOptimistic
 */
uix.core.use_optimistic = (function uix$core$use_optimistic(state,update_fn){
return uix.hooks.alpha.use_optimistic(state,update_fn);
});
/**
 * Allows you to update state based on the result of a form action
 * 
 *   f: the function to be called when the form is submitted or button pressed
 *   state: the value you want the state to be initially
 *   permalink: a string containing the unique page URL that this form modifies
 * 
 *   Returns a triplet of [state, form-action, pending?]
 *   state: current form state
 *   form-action: a new action that you can pass as the `action` prop to your form component or `form-action` prop to any button component within the form
 *   pending?: a boolean indicating whether the form is currently pending
 * 
 *   See: https://react.dev/reference/react/useActionState
 */
uix.core.use_action_state = (function uix$core$use_action_state(var_args){
var G__102550 = arguments.length;
switch (G__102550) {
case 2:
return uix.core.use_action_state.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return uix.core.use_action_state.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(uix.core.use_action_state.cljs$core$IFn$_invoke$arity$2 = (function (f,state){
return uix.hooks.alpha.use_action_state.cljs$core$IFn$_invoke$arity$2(f,state);
}));

(uix.core.use_action_state.cljs$core$IFn$_invoke$arity$3 = (function (f,state,permalink){
return uix.hooks.alpha.use_action_state.cljs$core$IFn$_invoke$arity$3(f,state,permalink);
}));

(uix.core.use_action_state.cljs$lang$maxFixedArity = 3);

/**
 * Lets you read the value of a resource like a Promise or context.
 * 
 *   See: https://react.dev/reference/react/use
 */
uix.core.use = (function uix$core$use(resource){
return uix.hooks.alpha.use(resource);
});
/**
 * Interop with React components. Takes a function that returns UIx component
 *   and returns same component wrapped into interop layer.
 */
uix.core.as_react = (function uix$core$as_react(f){
return (function (p1__102569_SHARP_){
var G__102570 = cljs_bean.core.bean.cljs$core$IFn$_invoke$arity$1(p1__102569_SHARP_);
return (f.cljs$core$IFn$_invoke$arity$1 ? f.cljs$core$IFn$_invoke$arity$1(G__102570) : f.call(null,G__102570));
});
});
/**
 * Special case for traditional hot-reloading via shadow-cljs,
 *   when UI tree is rendered from the root after evert hot-reload
 */
uix.core.lazy_shadow_reloadable = (function uix$core$lazy_shadow_reloadable(f,loadable){
var lazy_component = module$node_modules$react$index.lazy((function (){
return (f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)).then((function (_){
return ({"default": (function (props){
return uix.compiler.alpha.component_element(cljs.core.deref(loadable),uix.compiler.attributes.interpret_props(uix.core.glue_args(props)),[]);
})});
}));
}));
(lazy_component.uix_component_QMARK_ = true);

return lazy_component;
});
/**
 * Like React.lazy, but supposed to be used with UIx components
 */
uix.core.lazy = (function uix$core$lazy(var_args){
var G__102604 = arguments.length;
switch (G__102604) {
case 1:
return uix.core.lazy.cljs$core$IFn$_invoke$arity$1((arguments[(0)]));

break;
case 2:
return uix.core.lazy.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

(uix.core.lazy.cljs$core$IFn$_invoke$arity$1 = (function (f){
var lazy_component = module$node_modules$react$index.lazy((function (){
return (f.cljs$core$IFn$_invoke$arity$0 ? f.cljs$core$IFn$_invoke$arity$0() : f.call(null)).then((function (component){
return ({"default": component});
}));
}));
(lazy_component.uix_component_QMARK_ = true);

return lazy_component;
}));

(uix.core.lazy.cljs$core$IFn$_invoke$arity$2 = (function (f,loadable){
return uix.core.lazy_shadow_reloadable(f,loadable);
}));

(uix.core.lazy.cljs$lang$maxFixedArity = 2);

/**
 * Creates React's error boundary component
 * 
 *   display-name       — the name of the component to be displayed in stack trace
 *   derive-error-state — maps error object to component's state that is used in render-fn
 *   did-catch          — 2 arg function for side-effects, logging etc.
 *   receives the exception and additional component info as args
 *   render-fn          — takes state value returned from error->state and a vector
 *   of arguments passed into error boundary
 */
uix.core.create_error_boundary = (function uix$core$create_error_boundary(p__102619,render_fn){
var map__102620 = p__102619;
var map__102620__$1 = cljs.core.__destructure_map(map__102620);
var display_name = cljs.core.get.cljs$core$IFn$_invoke$arity$3(map__102620__$1,new cljs.core.Keyword(null,"display-name","display-name",694513143),cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.gensym.cljs$core$IFn$_invoke$arity$1("uix.error-boundary")));
var derive_error_state = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102620__$1,new cljs.core.Keyword(null,"derive-error-state","derive-error-state",-1373095498));
var did_catch = cljs.core.get.cljs$core$IFn$_invoke$arity$2(map__102620__$1,new cljs.core.Keyword(null,"did-catch","did-catch",2139522313));
var constructor$ = (function (this$,_){
return (this$.state = ({"argv": null}));
});
var derive_state = (function (error){
return ({"argv": (derive_error_state.cljs$core$IFn$_invoke$arity$1 ? derive_error_state.cljs$core$IFn$_invoke$arity$1(error) : derive_error_state.call(null,error))});
});
var render = (function (){
var this$ = this;
var props = this$.props;
var state = this$.state;
var set_state = (function (new_value){
return this$.setState(({"argv": new_value}));
});
var G__102633 = new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [state.argv,set_state], null);
var G__102634 = uix.core.glue_args(props);
return (render_fn.cljs$core$IFn$_invoke$arity$2 ? render_fn.cljs$core$IFn$_invoke$arity$2(G__102633,G__102634) : render_fn.call(null,G__102633,G__102634));
});
var class$ = uix.core.create_class(new cljs.core.PersistentArrayMap(null, 5, [new cljs.core.Keyword(null,"constructor","constructor",-1953928811),constructor$,new cljs.core.Keyword(null,"displayName","displayName",-809144601),display_name,new cljs.core.Keyword(null,"getDerivedStateFromError","getDerivedStateFromError",166658477),derive_state,new cljs.core.Keyword(null,"componentDidCatch","componentDidCatch",821717733),did_catch,new cljs.core.Keyword(null,"render","render",-1408033454),render], null));
(class$.uix_component_QMARK_ = true);

return class$;
});
/**
 * Like React's `forwardRef`, but should be used only for UIx components
 *   when passing them into React components that inject a ref
 */
uix.core.forward_ref = (function uix$core$forward_ref(component){
var ref_comp = module$node_modules$react$index.forwardRef((function (props,ref){
var argv = (function (){var G__102641 = props.argv;
var G__102641__$1 = (cljs.core.truth_(props.children)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__102641,new cljs.core.Keyword(null,"children","children",-940561982),props.children):G__102641);
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__102641__$1,new cljs.core.Keyword(null,"ref","ref",1289896967),ref);

})();
var argv__$1 = cljs.core.merge.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([argv,cljs.core.dissoc.cljs$core$IFn$_invoke$arity$2(cljs_bean.core.bean.cljs$core$IFn$_invoke$arity$1(props),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"argv","argv",-1462790895),new cljs.core.Keyword(null,"children","children",-940561982)], null))], 0));
return uix.compiler.alpha.component_element(component,uix.compiler.attributes.interpret_props(argv__$1),[]);
}));
(ref_comp.uix_component_QMARK_ = true);

return ref_comp;
});
uix.core.clone_element = (function uix$core$clone_element(var_args){
var args__5755__auto__ = [];
var len__5749__auto___102791 = arguments.length;
var i__5750__auto___102793 = (0);
while(true){
if((i__5750__auto___102793 < len__5749__auto___102791)){
args__5755__auto__.push((arguments[i__5750__auto___102793]));

var G__102794 = (i__5750__auto___102793 + (1));
i__5750__auto___102793 = G__102794;
continue;
} else {
}
break;
}

var argseq__5756__auto__ = ((((2) < args__5755__auto__.length))?(new cljs.core.IndexedSeq(args__5755__auto__.slice((2)),(0),null)):null);
return uix.core.clone_element.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),(arguments[(1)]),argseq__5756__auto__);
});

(uix.core.clone_element.cljs$core$IFn$_invoke$arity$variadic = (function (element,props,children){
var type = element.type;
var okey = element.key;
var oref = element.ref;
var update_children = (function (p1__102647_SHARP_){
if(cljs.core.truth_(p1__102647_SHARP_)){
return cljs.core.map.cljs$core$IFn$_invoke$arity$2((function (el){
if(cljs.core.truth_((function (){var G__102661 = el;
if((G__102661 == null)){
return null;
} else {
return G__102661._store;
}
})())){
(el._store.validated = true);
} else {
}

return el;
}),p1__102647_SHARP_);
} else {
return null;
}
});
var children__$1 = update_children(children);
var props__$1 = cljs.core.update.cljs$core$IFn$_invoke$arity$3(props,new cljs.core.Keyword(null,"children","children",-940561982),update_children);
if(((typeof type === 'string') || (cljs.core.not(type.uix_component_QMARK_)))){
var oprops = element.props;
var nel = uix.compiler.alpha.component_element(type,uix.compiler.attributes.interpret_props((function (){var G__102669 = props__$1;
if(cljs.core.seq(children__$1)){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__102669,new cljs.core.Keyword(null,"children","children",-940561982),cljs.core.into_array.cljs$core$IFn$_invoke$arity$1(children__$1));
} else {
return G__102669;
}
})()),[]);
var nprops = Object.assign(({}),oprops,({"key": okey, "ref": oref}),nel.props);
return uix.compiler.alpha.component_element(type,uix.compiler.attributes.interpret_props(nprops),[]);
} else {
var oprops = element.props.argv;
return uix.compiler.alpha.component_element(type,uix.compiler.attributes.interpret_props((function (){var G__102673 = oprops;
var G__102673__$1 = (cljs.core.truth_(okey)?cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__102673,new cljs.core.Keyword(null,"key","key",-1516042587),okey):G__102673);
var G__102673__$2 = cljs.core.merge.cljs$core$IFn$_invoke$arity$variadic(cljs.core.prim_seq.cljs$core$IFn$_invoke$arity$2([G__102673__$1,props__$1], 0))
;
if(cljs.core.seq(children__$1)){
return cljs.core.assoc.cljs$core$IFn$_invoke$arity$3(G__102673__$2,new cljs.core.Keyword(null,"children","children",-940561982),children__$1);
} else {
return G__102673__$2;
}
})()),[]);
}
}));

(uix.core.clone_element.cljs$lang$maxFixedArity = (2));

/** @this {Function} */
(uix.core.clone_element.cljs$lang$applyTo = (function (seq102648){
var G__102649 = cljs.core.first(seq102648);
var seq102648__$1 = cljs.core.next(seq102648);
var G__102651 = cljs.core.first(seq102648__$1);
var seq102648__$2 = cljs.core.next(seq102648__$1);
var self__5734__auto__ = this;
return self__5734__auto__.cljs$core$IFn$_invoke$arity$variadic(G__102649,G__102651,seq102648__$2);
}));

uix.core.suspense = module$node_modules$react$index.Suspense;
uix.core.strict_mode = module$node_modules$react$index.StrictMode;
uix.core.profiler = module$node_modules$react$index.Profiler;
uix.core.client_QMARK_ = (typeof document !== 'undefined');
uix.core.server_QMARK_ = (!(uix.core.client_QMARK_));
/**
 * @nosideeffects
 */
uix.core.set_display_name = (function uix$core$set_display_name(f,name){
(f.displayName = name);

return Object.defineProperty(f,"name",({"value": name}));
});

//# sourceMappingURL=uix.core.js.map
