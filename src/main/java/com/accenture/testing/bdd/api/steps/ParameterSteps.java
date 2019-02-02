package com.accenture.testing.bdd.api.steps;

import com.accenture.testing.bdd.parameters.DefaultParamTransformer;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import com.accenture.testing.bdd.api.http.APIRequestState;

public class ParameterSteps implements En {

  /**
   * container for cucumber lambda methods.
   *
   * @param requestState injected object
   * @param paramTransformer injected object
   */
  public ParameterSteps(APIRequestState requestState, DefaultParamTransformer paramTransformer) {

    /**
     * add a parameter to the request.
     *
     * @param name the parameter name
     * @param val the paramater value
     */
    When(
        "I provide the parameter {string} with a value of {string}",
        (String name, String val) -> {
          name = paramTransformer.transform(name);
          val = paramTransformer.transform(val);
          requestState.setParameter(name, val);
        });

    /**
     * adds parameters to request.
     *
     * @param table the parameters to add
     */
    When(
        "^I provide the parameters$",
        (DataTable table) -> {
          table
              .asMap(String.class, String.class)
              .forEach(
                  (name, val) -> {
                    name = paramTransformer.transform((String) name);
                    val = paramTransformer.transform((String) val);
                    requestState.setParameter((String) name, (String) val);
                  });
        });
  }
}
