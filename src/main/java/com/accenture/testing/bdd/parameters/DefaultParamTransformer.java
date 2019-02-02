package com.accenture.testing.bdd.parameters;

public class DefaultParamTransformer extends ParamTransformer {

  /** initializer, ensures there's a cache. */
  public DefaultParamTransformer() {
    addTransform(new CacheTransform());
    addTransform(new ResponseCacheTransform("response"));
    addTransform(new ResponseCacheTransform("response.body"));
    addTransform(new ResponseCacheTransform("response.headers"));
    addTransform(new FakerTransform());
    addTransform(new VariableTransform());
  }
}
