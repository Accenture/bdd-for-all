package com.accenture.testing.bdd.http;

import static org.assertj.core.api.Assertions.*;

import com.accenture.testing.bdd.comparison.Matcher;
import com.accenture.testing.bdd.comparison.Operator;
import io.restassured.response.Response;
import java.util.List;
import java.util.Objects;
import com.accenture.testing.bdd.parameters.ParamTransformer;

public abstract class ResponseState {

  /**
   * get the restassured response.
   *
   * @return a response object for play
   */
  public abstract Response getResponse();

  /**
   * get a value from the response based on a given lookup path.
   *
   * @param path a path to extract the value from
   * @return the value for the path
   */
  protected abstract Object getValue(String path);

  /**
   * get the value if it's a transform token, get that value otherwise try to query the value with
   * the path.
   *
   * @param pt the current transformer
   * @param path the path to extract the value from
   * @return the path value
   */
  public Object getValue(ParamTransformer pt, String path) {
    return pt.hasToken(path) ? pt.transform(path) : getValue(path);
  }

  /**
   * get a count of matching values for path.
   *
   * @param path the path to pull matching elements from.
   * @return a list of values for the path
   */
  public abstract List<?> getValues(String path);

  /**
   * asserts provided path value matches expected object value.
   *
   * @param path the path to extract the value from
   * @param obj the value to match against
   */
  public void matches(String path, Object obj) {
    Object val = getValue(path);
    assertThat(obj)
        .as("Expected %s value to be %s was %s", path, obj, val)
        .isEqualTo(val);
  }

  /**
   * asserts provided path value matches does not match object value.
   *
   * @param path the path to extract the value from
   * @param obj the value to match against
   */
  public void notMatches(String path, Object obj) {
    Object val = getValue(path);
    assertThat(obj)
        .as("Expected %s value to not match %s, but %s did", path, obj, val)
        .isNotEqualTo(val);
  }

  /**
   * asserts provided path value contains expected object value.
   *
   * @param path the path to extract the value from
   * @param obj the value it is expected to contain
   */
  public void contains(String path, Object obj) {
    String val = Objects.toString(getValue(path));
    assertThat(obj)
        .as("Expected %s value to contain %s was %s", path, obj, val)
        .matches(s -> {
          String txt = Objects.toString(s, null);
          return txt != null && val.contains(Objects.toString(obj));
        });
  }

  /**
   * asserts provided path value does not contain expected object value.
   *
   * @param path the path to extract the value from
   * @param obj the value it is expected to not contain
   */
  public void notContains(String path, Object obj) {
    String val = Objects.toString(getValue(path));
    assertThat(obj)
        .as("Expected %s value to contain %s was %s", path, obj, val)
        .matches(s -> {
          String txt = Objects.toString(s, null);
          return txt != null && !val.contains(Objects.toString(obj));
        });
  }

  /**
   * the provided path returns x elements.
   *
   * @param path the path to pull matching elements from
   * @param operator the operator to use
   * @param cnt the number of occurrences
   */
  public void hasXElements(String path, Operator operator, int cnt) {
    List<?> vals = getValues(path);
    int actual = Objects.isNull(vals) ? 0 : vals.size();
    assertThat(operator.calc(actual, cnt))
        .as("Expected %s to be %s %d element(s), but actual was %d", path, operator.toString(), cnt, actual)
        .isTrue();
  }

  /**
   * the provided path returns x elements with value.
   *
   * @param path the path to pull matching elements from
   * @param operator the operator to use
   * @param cnt the number of occurrences
   * @param val the value to match against
   */
  public void hasXElementsWithVal(String path, Operator operator, int cnt, Object val) {
    int actual =
        (int) getValues(path).stream().filter(o -> Objects.nonNull(o) && o.equals(val)).count();
    assertThat(operator.calc(actual, cnt))
        .as("Expected %s to be %s %d element(s) with %s, but actual was %d", path, operator.toString(), cnt, val, actual)
        .isTrue();
  }

  /**
   * assuming path returns a collection of objects, checks for duplicates.
   *
   * @param path the path to pull matching elements from
   * @param does whether it has duplicates or not
   */
  public void hasDuplicates(String path, boolean does) {
    List<?> all = getValues(path);
    int distinct = (int) all.stream().distinct().count();
    boolean equals = does ? all.size() - distinct > 0 : all.size() - distinct == 0;
    assertThat(equals)
        .as("Collection from %s contains %d duplicates", path, all.size() - distinct)
        .isTrue();
  }

  /**
   * do the provided elements match (or not).
   *
   * @param el1 the first path
   * @param el2 the second path
   * @param matcher the type of match to perform
   */
  public void elementsMatch(String el1, String el2, String matcher) {
    Object val = getValue(el1);
    Object val2 = getValue(el2);
    Matcher match = Matcher.valueOf(matcher);
    assertThat(match.match(val, val2))
        .as("Expected %s (%s) to %s %s (%s), but didn't", el1, val, match.toString(), el2, val2)
        .isTrue();
  }
}
