package com.accenture.testing.bdd.parameters;

import com.accenture.testing.bdd.http.ResponseState;

public class ResponseCacheTransform extends ParamTransform {

  String key;

  public ResponseCacheTransform(String key) {
    this.key = key;
  }

  @Override
  String key() {
    return key;
  }

  @Override
  String transform(String params, ParamTransformer pt) {
    String[] pieces = params.split("->");
    ResponseState rs = pt.getResponseState(pieces[0]);

    if (key().contains(".header")) {
      return rs.getResponse().getHeader(pieces[1]);
    }

    return rs.getValue(pt, pieces[1]).toString();
  }
}
