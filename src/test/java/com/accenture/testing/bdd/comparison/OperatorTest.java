package com.accenture.testing.bdd.comparison;

import java.util.Arrays;
import java.util.stream.Stream;
import junit.framework.TestCase;

public class OperatorTest extends TestCase {

  /** checks to make sure operators are functioning properly */
  public void testTypes() {

    OperatorContainer[] operations =
        new OperatorContainer[] {
          new OperatorContainer("only", 2, 3, false),
          new OperatorContainer("only", 3, 2, false),
          new OperatorContainer("only", 2, 2, true),
          new OperatorContainer("more_than", 2, 3, false),
          new OperatorContainer("more_than", 2, 2, false),
          new OperatorContainer("more_than", 3, 2, true),
          new OperatorContainer("at_least", 2, 3, false),
          new OperatorContainer("at_least", 2, 2, true),
          new OperatorContainer("at_least", 3, 2, true),
          new OperatorContainer("less_than", 4, 3, false),
          new OperatorContainer("less_than", 2, 2, false),
          new OperatorContainer("less_than", 1, 2, true),
          new OperatorContainer("less_than", 200, 201, true),
          new OperatorContainer("at_most", 4, 3, false),
          new OperatorContainer("at_most", 2, 2, true),
          new OperatorContainer("at_most", 1, 2, true),
        };

    Stream<OperatorContainer> stream = Arrays.stream(operations);
    stream.forEach(
        opc -> {
          Operator op = Operator.valueOf(opc.type);
          assertTrue(
              String.format(
                  "%s operator failed %d %s %d should of been %s",
                  opc.type, opc.in, op.toString(), opc.match, opc.result),
              op.calc(opc.in, opc.match) == opc.result);
        });
  }

  /** container for operator info */
  public class OperatorContainer {

    String type;
    int in;
    int match;
    boolean result;

    private OperatorContainer(String type, int in, int match, boolean result) {
      this.type = type;
      this.in = in;
      this.match = match;
      this.result = result;
    }
  }
}
