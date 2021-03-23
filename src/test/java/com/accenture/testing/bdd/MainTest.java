package com.accenture.testing.bdd;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class MainTest extends TestCase {

  @Rule
  public final ExpectedSystemExit exit = ExpectedSystemExit.none();

  public void testMain() {
    exit.expectSystemExitWithStatus(0);
    System.setProperty("CUCUMBER_PLUGIN", "pretty, json:target/cucumber/main-simple-cucumber.json");
    List<String> vals = new ArrayList<>();
    vals.add("src/test/resources/unit_tests/Simple.feature");
    Main.main(vals.toArray(new String[0]));
  }

  public void testSleep() {

    System.setProperty("CUCUMBER_PLUGIN", "pretty, json:target/cucumber/main-sleep-cucumber.json");

    exit.expectSystemExitWithStatus(0);
    List<String> vals = new ArrayList<>();
    vals.add("src/test/resources/unit_tests/Sleep.feature");
    long time = System.currentTimeMillis();
    Main.main(vals.toArray(new String[0]));

    // no perfect way to do this since there are other JVM factors in place like startup
    Assert.assertTrue(System.currentTimeMillis()-time > 500);

  }

  @AfterClass
  public void whenComplete() {
    System.clearProperty("CUCUMBER_PLUGIN");
  }

}
