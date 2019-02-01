package org.accenture.testing.bdd.parameters;

import com.typesafe.config.Config;
import java.util.Objects;
import org.accenture.testing.bdd.util.BDDConfig;

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
