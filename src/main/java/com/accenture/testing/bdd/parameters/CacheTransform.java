package com.accenture.testing.bdd.parameters;

public class CacheTransform extends ParamTransform {

  @Override
  String key() {
    return "cache";
  }

  @Override
  String transform(String params, ParamTransformer pt) {
    return pt.getCachedValue(params);
  }
}
