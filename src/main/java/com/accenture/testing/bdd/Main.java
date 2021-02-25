package com.accenture.testing.bdd;

import io.cucumber.core.options.CommandlineOptionsParser;
import io.cucumber.core.options.CucumberProperties;
import io.cucumber.core.options.CucumberPropertiesParser;
import io.cucumber.core.options.RuntimeOptions;
import io.cucumber.core.runtime.Runtime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {

  /**
   * handle the parsing of arguments to pass to.
   *
   * @param argv the command line arguments
   */
  public static void main(String[] argv) {
    run(argv, Thread.currentThread().getContextClassLoader());
  }

  /**
   * Launches the Cucumber-JVM command line.
   *
   * @param argv        runtime options. See details in the {@code cucumber.api.cli.Usage.txt} resource.
   * @param classLoader classloader used to load the runtime
   * @return 0 if execution was successful, 1 if it was not (test failures)
   */
  public static byte run(String[] argv, ClassLoader classLoader) {


    List<String> args = new ArrayList<String>(Arrays.asList(argv));

    // add the glue
    args.add("-g");
    args.add("com.accenture.testing.bdd.api.steps");

    // add the pretty plugin
    args.add("-p");
    args.add("pretty");

    RuntimeOptions propertiesFileOptions = new CucumberPropertiesParser().parse(CucumberProperties.fromPropertiesFile()).build();
    RuntimeOptions environmentOptions = new CucumberPropertiesParser().parse(CucumberProperties.fromEnvironment()).build(propertiesFileOptions);
    RuntimeOptions systemOptions = new CucumberPropertiesParser().parse(CucumberProperties.fromSystemProperties()).build(environmentOptions);
    CommandlineOptionsParser commandlineOptionsParser = new CommandlineOptionsParser(System.out);
    RuntimeOptions runtimeOptions = commandlineOptionsParser.parse(args.stream().toArray(String[]::new)).addDefaultGlueIfAbsent().addDefaultFeaturePathIfAbsent().addDefaultFormatterIfAbsent().addDefaultSummaryPrinterIfAbsent().enablePublishPlugin().build(systemOptions);
    Optional<Byte> exitStatus = commandlineOptionsParser.exitStatus();
    if (!exitStatus.isPresent()) {
      Runtime runtime = Runtime.builder().withRuntimeOptions(runtimeOptions).withClassLoader(() -> {
        return classLoader;
      }).build();
      runtime.run();
      return runtime.exitStatus();
    }
    return exitStatus.get();
  }
}