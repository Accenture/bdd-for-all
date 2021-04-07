package com.accenture.testing.bdd.conversion;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.jalokim.propertiestojson.resolvers.PrimitiveJsonTypesResolver;
import pl.jalokim.propertiestojson.resolvers.primitives.string.TextToConcreteObjectResolver;
import static pl.jalokim.propertiestojson.Constants.EMPTY_STRING;
import static pl.jalokim.propertiestojson.Constants.SIMPLE_ARRAY_DELIMITER;
import static pl.jalokim.propertiestojson.resolvers.primitives.utils.JsonObjectHelper.hasJsonArraySignature;
import static pl.jalokim.propertiestojson.resolvers.primitives.utils.JsonObjectHelper.isValidJsonObjectOrArray;

@ToString
@EqualsAndHashCode
public class CustomTextToElementResolver implements TextToConcreteObjectResolver<List<?>> {

  private final String arrayElementSeparator;
  private final boolean resolveTypeOfEachElement;

  public CustomTextToElementResolver() {
    this(true);
  }

  public CustomTextToElementResolver(boolean resolveTypeOfEachElement) {
    this(resolveTypeOfEachElement, SIMPLE_ARRAY_DELIMITER);
  }

  public CustomTextToElementResolver(boolean resolveTypeOfEachElement, String arrayElementSeparator) {
    this.resolveTypeOfEachElement = resolveTypeOfEachElement;
    this.arrayElementSeparator = arrayElementSeparator;
  }

  @Override
  public Optional<List<?>> returnObjectWhenCanBeResolved(PrimitiveJsonTypesResolver primitiveJsonTypesResolver, String propertyValue, String propertyKey) {

    if (isSimpleArray(propertyValue) && !isValidJsonObjectOrArray(propertyValue)) {

      // cleanup
      if (hasJsonArraySignature(propertyValue)) {
        propertyValue = Arrays.stream(propertyValue
        .replaceAll("]\\s*$", EMPTY_STRING)
        .replaceAll("^\\s*\\[\\s*", EMPTY_STRING)
        .split(arrayElementSeparator))
        .map(String::trim)
        .collect(Collectors.joining(arrayElementSeparator));
      }

      // returns
      List<Object> elements = Arrays.stream(propertyValue.split(arrayElementSeparator))
          .map(item -> {
            if (resolveTypeOfEachElement) return primitiveJsonTypesResolver.getResolvedObject(item, propertyKey);
            else return item;
          })
          .collect(Collectors.toList());

      return Optional.of(elements);
    }

    return Optional.empty();
  }

  private boolean isSimpleArray(String propertyValue) {
    return propertyValue.contains(arrayElementSeparator) && hasJsonArraySignature(propertyValue);
  }


}