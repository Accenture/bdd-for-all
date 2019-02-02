package com.accenture.testing.bdd.parameters;

public abstract class ParamTransform {

  @Override
  public String toString() {
    return key();
  }

  /**
   * they key for this transformation.
   *
   * @return the key for the transformation
   */
  abstract String key();

  /**
   * transform the input based on the transformer type.
   *
   * @param params the parameters for the transform
   * @param pt the calling transformer
   * @return the transformed string
   */
  abstract String transform(String params, ParamTransformer pt);
}
