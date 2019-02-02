package com.accenture.testing.bdd.api.http;

import junit.framework.TestCase;
import org.junit.Assert;

public class APIRequestStateTest extends TestCase {

  /**
   * make sure tostring doesn't throw any exceptions.
   * Reason - using reflection for it.
   */
  public void testToString() {
    APIRequestState rs = new APIRequestState();
    Assert.assertNotNull(rs.toString());
  }

}
