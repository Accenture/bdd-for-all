package com.accenture.testing.bdd.api.steps;

import com.accenture.testing.bdd.api.http.APIRequestState;
import io.cucumber.java8.En;

public class FormSteps implements En {

  /**
   * container for cucumber lambda methods.
   *
   * @param requestState injected object
   */
  public FormSteps(APIRequestState requestState) {

    /**
     * tells the request we're submitting a form
     *
     * @param type the type of content (JSON or XML)
     * @param body the actual json or xml
     */
    When(
        "I am submitting a form", () -> requestState.setIsForm(Boolean.TRUE) );

    /**
     * sets the request body json or xml.
     *
     * @param type the type of content (JSON or XML)
     * @param body the actual json or xml
     */
    When(
        "I provide the file {string} at {string} as {string}",
        (String name, String file, String type) -> requestState.addFile(name, file, type));

  }

}
