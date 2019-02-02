package com.accenture.testing.bdd.api.http;

import junit.framework.TestCase;
import org.junit.Assert;

public class APIResponseStateTest extends TestCase {

  /**
   * make sure tostring doesn't throw any exceptions.
   * Reason - using reflection for it.
   */
  public void testToString() {
    APIResponseState rs = new APIResponseState(null, APIResponseStateType.JSON);
    Assert.assertNotNull(rs.toString());
  }

}
