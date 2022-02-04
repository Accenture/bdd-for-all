package com.accenture.testing.bdd.parameters;

import com.accenture.testing.bdd.config.BDDConfig;
import java.util.Objects;
import org.apache.commons.configuration2.HierarchicalConfiguration;

public class VariableTransform extends ParamTransform {

  private static HierarchicalConfiguration config = BDDConfig.getConfig();

  @Override
  String key() {
    return "vars";
  }

  @Override
  String transform(String params, ParamTransformer pt) {
    String confVal = config.configurationAt("vars").getString(params);
    if (Objects.isNull(confVal)) {
      return params;
    }
    return confVal;
  }
}
