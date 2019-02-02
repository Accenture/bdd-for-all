package com.accenture.testing.bdd.api.http;

import com.accenture.testing.bdd.http.ResponseState;

public enum APIResponseStateType {

  /** XML based path evaluator (used for XML responses). */
  XML {
    @Override
    public Object evaluate(String path, ResponseState responseState) {
      return responseState.getResponse().xmlPath().get(path);
    }
  },
  /** JSON based path evaluator (used for JSON responses). */
  JSON {
    @Override
    public Object evaluate(String path, ResponseState responseState) {
      return responseState.getResponse().jsonPath().get(path);
    }
  },
  /** HTML based path evaluator (XML eval with loosened rules for HTML). */
  HTML {
    @Override
    public Object evaluate(String path, ResponseState responseState) {
      return responseState.getResponse().htmlPath().get(path);
    }
  };

  /**
   * use the evaluator from the ResponseState.Response to pull a value.
   *
   * @param path the GSON path
   * @param responseState the response state from the current object
   * @return the object value
   */
  public abstract Object evaluate(String path, ResponseState responseState);
}
