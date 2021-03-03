package com.accenture.testing.bdd.config;

import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.tree.ImmutableNode;

@Slf4j
public class BDDConfig {

  static final CombinedConfiguration CONFIG = new ConfigLoader().loadConfiguration();

  static final RestAssuredConfig restAssuredConfig =
      RestAssuredConfig.config().sslConfig(getSSLConfig()).httpClient(getHttpConfig());

  static {
    RestAssured.config = CurlRestAssuredConfigFactory.updateConfig(restAssuredConfig, getCurlOptions());
  }

  /**
   * typesafe config for module.
   *
   * @return initialized config for project
   */
  public static HierarchicalConfiguration<ImmutableNode> getConfig() {
    return CONFIG.configurationAt("bdd-for-all");
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
