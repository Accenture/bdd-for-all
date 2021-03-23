package com.accenture.testing.bdd;

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

    List<String> args = new ArrayList<String>(Arrays.asList(argv));

    // add the glue
    args.add("-g");
    args.add("com.accenture.testing.bdd.api.steps");

    // add the pretty plugin
    args.add("-p");
    args.add("pretty");

    BDDForAll bddForAll = new BDDForAll();
    bddForAll.run(args);

  }


}