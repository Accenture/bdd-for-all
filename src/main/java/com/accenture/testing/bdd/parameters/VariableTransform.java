package com.accenture.testing.bdd.parameters;

import com.accenture.testing.bdd.util.BDDConfig;
import com.typesafe.config.Config;
import java.util.Objects;

public class VariableTransform extends ParamTransform {

  private static Config config = BDDConfig.getConfig();

  @Override
  String key() {
    return "vars";
  }

  @Override
  String transform(String params, ParamTransformer pt) {
    String confVal = config.getConfig("vars").getString(params);
    if (Objects.isNull(confVal)) {
      return params;
    }
    return confVal;
  }
}
