package com.accenture.testing.bdd.parameters;

import static org.joor.Reflect.on;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.joor.Reflect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FakerTransform extends ParamTransform {

  private static final Logger LOG = LoggerFactory.getLogger(FakerTransform.class);

  @Override
  String key() {
    return "faker";
  }

  @Override
  String transform(String params, ParamTransformer pt) {

    List<String> args = new ArrayList<>(Arrays.asList(params.split(",")));

    String reflect = args.remove(0);
    String lang = args.isEmpty() ? Locale.getDefault().toLanguageTag() : args.remove(0);

    try {
      Reflect ref = on(Faker.class.getName()).create(new Locale(lang));
      for (String s : reflect.split("\\.")) {
        ref = ref.call(s);
      }
      return ref.get();
    } catch (Exception e) {
      LOG.error("Attempting to generate data on {}", params, e);
    }

    return params;
  }
}
