package com.accenture.testing.bdd.api.steps;

import com.accenture.testing.bdd.api.http.APIRequestState;
import com.accenture.testing.bdd.comparison.Matcher;
import com.accenture.testing.bdd.parameters.DefaultParamTransformer;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Assert;

public class HeaderSteps implements En {

  /**
   * container for cucumber lambda methods.
   *
   * @param requestState injected object
   * @param paramTransformer injected object
   */
  public HeaderSteps(APIRequestState requestState, DefaultParamTransformer paramTransformer) {

    /**
     * add a header to the request.
     *
     * @param name the header name
     * @param val the header value
     */
    When(
        "I provide the header {string} with a value of {string}",
        (String name, String val) -> {
          name = paramTransformer.transform(name);
          val = paramTransformer.transform(val);
          requestState.setHeader(name, val);
        });

    /**
     * adds a bunch of headers to the request.
     *
     * @param table the headers to add
     */
    When(
        "^I provide the headers$",
        (DataTable table) -> {
          table
              .asMap(String.class, String.class)
              .forEach(
                  (name, val) -> {
                    name = paramTransformer.transform((String) name);
                    val = paramTransformer.transform((String) val);
                    requestState.setHeader((String) name, (String) val);
                  });
        });

    /**
     * header "name" equals "value".
     *
     * @param name the header name
     * @param val the header value
     */
    Then(
        "match header named {string} with a value of {string}",
        (String name, String val) -> {
          name = paramTransformer.transform(name);
          val = paramTransformer.transform(val);
          String hv =
              Optional.of(requestState.getResponseState().getResponse().getHeader(name))
                  .orElse("<EMPTY>");
          Assert.assertTrue(
              String.format("Expected %s to be %s was %s", name, val, hv), Objects.equals(hv, val));
        });

    /**
     * header value matching as a datatable.
     *
     * @param table the headers to match
     */
    When(
        "^the following header name/value combinations are (equal|not equal)$",
        (String op, DataTable table) -> {
          final AtomicReference<String> operation = new AtomicReference<>(op.replace(' ', '_'));
          Map<String,String> pairs = table.asMap(String.class, String.class);
          pairs.forEach((name, val) -> {
            name = paramTransformer.transform((String)name);
            val = paramTransformer.transform((String)val);
            String hv =
                Optional.of(requestState.getResponseState().getResponse().getHeader(name))
                    .orElse("<EMPTY>");
            Assert.assertTrue(
                String.format("Expected %s to be %s was %s", name, val, hv), Matcher.valueOf(operation.get()).match(val, hv));
          });
        });
  }
}
