package com.accenture.testing.bdd.api.steps;

import static org.junit.Assert.assertTrue;

import com.accenture.testing.bdd.api.http.APIRequestState;
import com.accenture.testing.bdd.api.http.APIResponseStateType;
import com.accenture.testing.bdd.parameters.DefaultParamTransformer;
import com.accenture.testing.bdd.util.BDDConfig;
import com.typesafe.config.Config;
import cucumber.api.Scenario;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.http.ContentType;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.jalokim.propertiestojson.util.PropertiesToJsonConverter;

public class BasicSteps implements En {

  private static Config config = BDDConfig.getConfig();
  private static final Logger CURL_LOG = LoggerFactory.getLogger("curl");

  /**
   * container for cucumber lambda methods.
   *
   * @param requestState injected object
   * @param paramTransformer injected object
   */
  public BasicSteps(APIRequestState requestState, DefaultParamTransformer paramTransformer) {

    /** reset the request state */
    Before(
        (Scenario scenario) -> {
          CURL_LOG.debug("## SCENARIO: {}", scenario.getName());
        });

    /**
     * Set up for the type of consumer.
     *
     * @stepInfo Initiates
     * @param type the type of response expected
     */
    Given(
        /**
         * @desc Initiates the request and the right parser - either JSON, XML or HTML
         * @examples InitReq
         */
        "^I am a (JSON|XML|HTML) API consumer$",
        (String type) -> {
          requestState.reset();
          requestState.setResponseStateType(APIResponseStateType.valueOf(type));
        });

    /**
     * Set up for the type of consumer.
     *
     * @stepInfo Initiates
     * @param type the type of response expected
     */
    Given(
        /**
         * @desc Initiates the request and the right parser - either JSON, XML or HTML
         * @examples InitReq
         */
        "I'm a {string}",
        (String consumer) -> {
          requestState.reset();
          String type = config.getConfig("consumers").getString(consumer);
          requestState.setResponseStateType(APIResponseStateType.valueOf(type));
        });

    /**
     * set the correlation-id
     *
     * @param id the X-Correlation-ID header value
     */
    When(
        /**
         * @desc Sets the correlation-id header (default named X-Correlation-ID, but can be
         *     overridden in config)
         * @examples ReqId
         */
        "I am executing test {string}",
        (String id) -> {
          requestState.setHeader("User-Agent", config.getString("request.userAgent"));
          requestState.setHeader(config.getString("request.correlationIdName"), id);
        });

    /**
     * set the request timeout.
     *
     * @param timeout the timeout in millis
     */
    When(
        "I request a maximum response time of {long}",
        (Long timeout) -> {
          long actual = requestState.getResponseState().getResponse().getTime();
          assertTrue(
              String.format(
                  "Request expected to return in %d or less, but returned in %d", timeout, actual),
              timeout >= actual);
        });

    /**
     * execute the get/post request
     *
     * @param method the type of request (e.g. httpmethod)
     * @param uri the uri to execute against
     */
    When(
        "^I request (GET|POST|DELETE|PATCH|PUT) \"([^\"]*)\"$",
        (String method, String uri) -> {
          uri = paramTransformer.transform(uri);
          requestState.setHttpMethod(method);
          requestState.setURI(uri);
        });

    /**
     * execute the get/post request
     *
     * @param method the type of request (e.g. httpmethod)
     * @param uri the uri to execute against
     * @param host the host of the URL
     */
    When(
        "^I request (GET|POST|DELETE|PATCH|PUT) \"([^\"]*)\" on \"([^\"]*)\"$",
        (String method, String uri, String host) -> {
          uri = paramTransformer.transform(uri);
          host = paramTransformer.transform(host);
          requestState.setHttpMethod(method);
          requestState.setURI(uri);
          requestState.setHost(host);
        });

    /**
     * sets the request body json or xml.
     *
     * @param type the type of content (JSON or XML)
     * @param body the actual json or xml
     */
    When(
        "^I set the (JSON|XML) body to$",
        (String type, String body) -> {
          requestState.setHeader("Content-Type", ContentType.valueOf(type).withCharset("utf-8"));
          requestState.setBody(paramTransformer.transform(body));
        });

    /**
     * sets the body using a table
     *
     * @param type the type of content (JSON or XML)
     * @param table the actual json or xml as dot notation
     */
    When(
        "^I set the (JSON|XML) body from values$",
        (String type, DataTable table) -> {
          Map<String,String> props = table.asMap(String.class,String.class);
          Map<String,String> transformed = props.entrySet().stream()
              .collect(Collectors.toMap(
                 e -> e.getKey(),
                 e -> paramTransformer.transform(e.getValue())
              ));
          String body = new PropertiesToJsonConverter().convertToJson(transformed);
          requestState.setHeader("Content-Type", ContentType.valueOf(type).withCharset("utf-8"));
          requestState.setBody(paramTransformer.transform(body));
        });


    /**
     * sets the request body json or xml.
     *
     * @param action the SOAPAction
     * @param body the XML
     */
    When(
        "I set the SOAPAction to {string} and body as",
        (String action, String body) -> {
          requestState.setHeader("SOAPAction", paramTransformer.transform(action));
          requestState.setHeader("Content-Type", ContentType.XML.withCharset("utf-8"));
          requestState.setBody(paramTransformer.transform(body));
        });

    /**
     * pause the execution before next step
     *
     * @param time the time to pause
     * @param timeunit the unit of measurement
     */
    When(
        "I wait {long} {string}",
        (Long time, String timeunit) -> {
          try {
            TimeUnit tu = TimeUnit.valueOf(timeunit.toUpperCase(Locale.US));
            tu.sleep(time);
          } catch (Exception e) {
            throw new Exception("Invalid timeunit type " + timeunit);
          }
        });

  }

}
