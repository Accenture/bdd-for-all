package com.accenture.testing.bdd.parameters;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakerTransform extends ParamTransform {

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
      Faker faker = Faker.instance(new Locale(lang));
      return faker.expression("#{" + reflect + "}");
    } catch (Exception e) {
      log.error("Attempting to generate data on {}", params, e);
    }

    return params;
  }
}
