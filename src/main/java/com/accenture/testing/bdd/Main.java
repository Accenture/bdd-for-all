package com.accenture.testing.bdd;

import cucumber.runtime.Runtime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    final Runtime runtime = Runtime.builder()
        .withArgs(args.toArray(new String[0]))
        .withClassLoader(classLoader)
        .build();

    runtime.run();
    return runtime.exitStatus();
  }
}