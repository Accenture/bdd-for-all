package com.accenture.testing.bdd.parameters;

import junit.framework.TestCase;
import org.junit.Assert;

public class ParamTransformerTest extends TestCase {

  /**
   * make sure tostring doesn't throw any exceptions.
   * Reason - using reflection for it.
   */
  public void testToString() {
    ParamTransformer pt = new ParamTransformer();
    Assert.assertNotNull(pt.toString());
  }

}
