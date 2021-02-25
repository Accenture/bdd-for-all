package com.accenture.testing.bdd.util;

import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;

public class BDDConfig {

  private static final Config config = ConfigFactory.load();
  private static RestAssuredConfig restAssuredConfig =
      RestAssuredConfig.config().sslConfig(getSSLConfig()).httpClient(getHttpConfig());

  static {
    RestAssured.config =
        CurlLoggingRestAssuredConfigFactory.updateConfig(restAssuredConfig, getCurlOptions());
  }

  /**
   * typesafe config for module.
   *
   * @return initialized config for project
   */
  public static Config getConfig() {
    return config.getConfig("bddcore");
  }

  /**
   * config for rest assured.
   *
   * @return config for rest assured
   */
  public static RestAssuredConfig getRestAssuredConfig() {
    return restAssuredConfig;
  }

  /**
   * CURL logging configuration for RestAssured.
   *
   * @return configured options for curl logger
   */
  public static Options getCurlOptions() {
    return Options.builder()
        .updateCurl(curl -> curl.setInsecure(false))
        .printMultiliner()
        .useLongForm()
        .build();
  }

  /**
   * get the ssl configuration.
   *
   * @return the ssl configuration
   */
  public static SSLConfig getSSLConfig() {
    return new SSLConfig().allowAllHostnames().relaxedHTTPSValidation();
  }

  /**
   * http client configuration.
   *
   * @return the http config
   */
  public static HttpClientConfig getHttpConfig() {
    return HttpClientConfig.httpClientConfig()
        .setParam("http.connection.timeout", getConfig().getInt("http.connection.requestTimeout"))
        .setParam("http.socket.timeout", getConfig().getInt("http.connection.socketTimeout"))
        .setParam(
            "http.connection-manager.timeout",
            getConfig().getInt("http.connection.managerTimeout"));
  }
}
