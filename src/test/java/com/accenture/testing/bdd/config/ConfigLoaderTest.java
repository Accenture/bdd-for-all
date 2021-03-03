package com.accenture.testing.bdd.config;

import java.util.List;
import junit.framework.TestCase;
import org.apache.commons.configuration2.CombinedConfiguration;
import org.junit.Assert;

public class ConfigLoaderTest extends TestCase {

  ConfigLoader loader = new ConfigLoader();

  String[] additionalConfigs = {
      "classpath:config.yml",
      "classpath:additional-config/config-test.yml",
      "classpath:additional-config/"
  };

  /**
   * test getting path by string
   * always should be a configuration in classpath
   */
  public void testGetPathWithString() {
    List<String> paths = loader.getPaths("classpath:application.yml");
    Assert.assertFalse(
        String.format(
          "Found configuration %s",
          paths),
        paths.isEmpty());
  }

  /**
   * test the base paths to scan for
   * simply looking at total count
   */
  public void testSimpleGetPaths() {
    List<String> paths = loader.getPaths();
    int expected = 2;
    Assert.assertEquals(
          String.format(
              "Expected paths to be %d and got %d",
              expected, paths.size()
          ),
        expected,
          paths.size()
        );
  }

  /**
   * see what happens when adding additional config locations
   * should find 3 configs...
   * Core application.yml (discovery)
   * config/application.yaml (discovery)
   * data/config-test.yml (told to find)
   * data/application.yaml (discovery - custom)
   */
  public void testAdditionalGetPaths() {
    System.setProperty("spring.config.additional-location", String.join(",", additionalConfigs));
    List<String> paths = loader.getPaths();
    System.clearProperty("spring.config.additional-location");
    int expected = 4;
    Assert.assertEquals(
        String.format(
            "Expected paths to be %d and got %d - %s",
            expected, paths.size(), paths.toString()
        ),
        expected,
        paths.size()
    );
  }

  /**
   * test the configuration actually loading
   */
  public void testConfigurationParam() {
    CombinedConfiguration configuration = loader.loadConfiguration();
    String actual = configuration.getString("bdd-for-all.request.server.host");
    String expected = "http://localhost:8181";
    Assert.assertEquals(
        "Configuration for bdd-for-all.request.server.host doesn't match",
        expected,
        actual
    );

  }

  /**
   * test the configuration actually loading
   * assumes to pick up override in config/application.yml
   */
  public void testConfigurationParamOverride() {
    CombinedConfiguration configuration = loader.loadConfiguration();
    String actual = configuration.getString("bdd-for-all.request.userAgent");
    String expected = "ACN-BDD-CUCUMBER2";
    Assert.assertEquals(
        "Configuration for bdd-for-all.request.userAgent doesn't match",
        expected,
        actual
    );

  }


  /**
   * test changing the name
   * assumes to pick up override in config/application.yml
   */
  public void testConfigurationNameChange() {
    System.setProperty("spring.config.name", "example");
    CombinedConfiguration configuration = loader.loadConfiguration();
    System.clearProperty("spring.config.name");
    String actual = configuration.getString("bdd-for-all.request.userAgent");
    String expected = "EXAMPLE";
    Assert.assertEquals(
        "Configuration for bdd-for-all.request.userAgent doesn't match",
        expected,
        actual
    );
  }


}