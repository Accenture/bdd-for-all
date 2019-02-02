package com.accenture.testing.bdd.api.steps;

import static org.junit.Assert.assertEquals;

import com.accenture.testing.bdd.api.http.APIRequestState;
import com.accenture.testing.bdd.comparison.Operator;
import com.accenture.testing.bdd.parameters.DefaultParamTransformer;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import java.util.Map;
import java.util.Objects;

public class ResponseSteps implements En {

  /**
   * container for cucumber lambda methods.
   *
   * @param requestState injected object
   * @param paramTransformer injected object
   */
  public ResponseSteps(APIRequestState requestState, DefaultParamTransformer paramTransformer) {

    /**
     * cache the result for later use.
     *
     * @param id the id for the response
     */
    Then(
        "record the response as {string}",
        (String id) -> {
          paramTransformer.cache(id, requestState.getResponseState());
        });

    /**
     * pulls the response from host and matches statusCode.
     *
     * @param statusCode the status of the request as returned by the endpoint
     */
    Then(
        "I should get a status code of {int}",
        (Integer statusCode) -> {
          Integer status = requestState.getResponseState().getResponse().getStatusCode();
          assertEquals(
              String.format(
                  "Expected status code of %d, but got %d from %s",
                  statusCode, status, requestState.getURI()),
              statusCode,
              status);
        });

    /**
     * executes a query against the response, which should return either true or false
     *
     * @param query the query to be executed
     * @param result either true or false
     */
    Then(
        "^evaluating \"([^\"]*)\" should return (true|false)$",
        (String query, String result) -> {
          query = paramTransformer.transform(query);
          requestState.getResponseState().matches(query, Boolean.valueOf(result));
        });

    /**
     * element should/not equal value.
     *
     * @param path the query path (json, xml, etc...)
     * @param val the value to match against the path val
     */
    Then(
        "^the response value of \"([^\"]*)\" (should|should not) equal \"([^\"]*)\"$",
        (String path, String operation, String val) -> {
          Object in;
          switch (val) {
            case "TRUE":
            case "FALSE":
              in = Boolean.valueOf(val);
              break;
            default:
              in = paramTransformer.transform(val);
              break;
          }

          switch (operation) {
            case "should not":
              requestState.getResponseState().notMatches(paramTransformer.transform(path), in);
              break;
            default:
              requestState.getResponseState().matches(paramTransformer.transform(path), in);
              break;
          }
        });

    /**
     * element should/not equal value.
     *
     * @param path the query path (json, xml, etc...)
     * @param val the value to match against the path val
     */
    Then(
        "^the response value of \"([^\"]*)\" (should|should not) equal (integer|float|long) \"([^\"]*)\"$",
        (String path, String operation, String type, String val) -> {
          Object in = null;
          switch (type) {
            case "integer":
              in = Integer.valueOf(val);
              break;
            case "float":
              in = Float.valueOf(val);
              break;
            case "long":
              in = Long.valueOf(val);
              break;
            default:
              in = null;
              break;
          }

          switch (operation) {
            case "should not":
              requestState.getResponseState().notMatches(paramTransformer.transform(path), in);
              break;
            default:
              requestState.getResponseState().matches(paramTransformer.transform(path), in);
              break;
          }
        });

    /**
     * check path for number of occurances
     *
     * @param path the query path
     * @param comparator the comparator
     * @param cnt the number of occurrences
     */
    Then(
        "^path \"([^\"]*)\" must occur (only|more than|at least|less than|at most) (\\d+) times$",
        (String path, String comparator, Integer cnt) -> {
          comparator = comparator.replace(' ', '_');
          requestState.getResponseState().hasXElements(path, Operator.valueOf(comparator), cnt);
        });

    /**
     * check path for number of occurrences with particular value (string/true|false)
     *
     * @param value the actual value
     * @param comparator the comparator
     * @param cnt the number of occurrences
     * @param path the query path
     */
    Then(
        "^the value \"([^\"]*)\" must occur (only|more than|at least|less than|at most) (\\d+) times for \"([^\"]*)\"$",
        (String val, String comparator, Integer cnt, String path) -> {
          comparator = comparator.replace(' ', '_');
          Object in;
          switch (val) {
            case "TRUE":
            case "FALSE":
              in = Boolean.valueOf(val);
              break;
            default:
              in = paramTransformer.transform(val);
              break;
          }

          requestState
              .getResponseState()
              .hasXElementsWithVal(path, Operator.valueOf(comparator), cnt, in);
        });

    /**
     * check path for number of occurrences with particular value (integer, float, long)
     *
     * @param value the actual value
     * @param type the number type
     * @param comparator the comparator
     * @param cnt the number of occurrences
     * @param path the query path
     */
    Then(
        "^the (integer|float|long) \"([^\"]*)\" must occur (only|more than|at least|less than|at most) (\\d+) times for \"([^\"]*)\"$",
        (String type, String val, String comparator, Integer cnt, String path) -> {
          comparator = comparator.replace(' ', '_');
          Object in = null;
          switch (type) {
            case "integer":
              in = Integer.valueOf(val);
              break;
            case "float":
              in = Float.valueOf(val);
              break;
            case "long":
              in = Long.valueOf(val);
              break;
            default:
              break;
          }

          requestState
              .getResponseState()
              .hasXElementsWithVal(path, Operator.valueOf(comparator), cnt, in);
        });

    /**
     * does the path have duplicates
     *
     * @param path the query path
     * @param type the comparator
     */
    Then(
        "^path \"([^\"]*)\" (does|does not) contain duplicates$",
        (String path, String type) -> {
          requestState.getResponseState().hasDuplicates(path, Objects.equals("does", type));
        });

    /**
     * does (or does not) contain element
     *
     * @param operation whether it should or shouldn't exist
     * @param table a table of elements to validate
     */
    Then(
        "^the response (should|should not) contain the following elements$",
        (String operation, DataTable table) -> {
          table
              .asList(String.class)
              .forEach(
                  path -> {
                    String val = paramTransformer.transform((String) path);
                    switch (operation) {
                      case "should not":
                        requestState.getResponseState().hasXElements(val, Operator.only, 0);
                        break;
                      default:
                        requestState.getResponseState().hasXElements(val, Operator.at_least, 1);
                        break;
                    }
                  });
        });

    /**
     * the path values match (or don't).
     *
     * @param path1 the first path
     * @param op should or shouldn't match
     * @param path2 the second path
     */
    Then(
        "^path value \"([^\"]*)\" should (equal|not equal) \"([^\"]*)\" value$",
        (String path1, String op, String path2) -> {
          path1 = paramTransformer.hasToken(path1) ? paramTransformer.transform(path1) : path1;
          path2 = paramTransformer.hasToken(path2) ? paramTransformer.transform(path2) : path2;
          requestState.getResponseState().elementsMatch(path1, path2, op.replace(' ', '_'));
        });

    /**
     * the element values match (or don't).
     *
     * @param op whether should or shouldn't match
     * @param datatable list of path pairs
     */
    Then(
        "^the following path values should (equal|not equal) each other$",
        (String op, DataTable dt) -> {
          Map<String, String> paths = dt.asMap(String.class, String.class);
          paths.forEach(
              (path1, path2) -> {
                path1 =
                    paramTransformer.hasToken(path1) ? paramTransformer.transform(path1) : path1;
                path2 =
                    paramTransformer.hasToken(path2) ? paramTransformer.transform(path2) : path2;
                requestState.getResponseState().elementsMatch(path1, path2, op.replace(' ', '_'));
              });
        });
  }

}
