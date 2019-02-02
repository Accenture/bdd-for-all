package com.accenture.testing.bdd.parameters;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import java.io.File;
import java.net.URL;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import junit.framework.TestCase;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FakerTransformTest extends TestCase {

  static final Logger LOG = LoggerFactory.getLogger(FakerTransformTest.class);

  /** test valid call to method */
  public void test_generate_valid() {
    FakerTransform ft = new FakerTransform();
    String formatOption = "address.city,en-US";
    String ret = ft.transform(formatOption, null);
    Assert.assertFalse(
        String.format("Valid method call %s, fails and returns %s", formatOption, ret),
        formatOption.equals(ret));
  }

  /** test invalid call to method */
  public void test_generate_invalid() {
    FakerTransform ft = new FakerTransform();
    String formatOption = "address.citys,en-US";
    String ret = ft.transform(formatOption, null);
    Assert.assertTrue(
        String.format("Invalid method call %s, succeeds and returns %s", formatOption, ret),
        formatOption.equals(ret));
  }

  /**
   * validate locales content
   *
   * @throws Exception because the detectorfactory is volatile
   */
  public void test_locales() throws Exception {

    URL url = getClass().getClassLoader().getResource("lang_profiles/");
    File dir = new File(url.getFile());
    DetectorFactory.loadProfile(dir);

    FakerTransform ft = new FakerTransform();

    String[] locales = new String[] {"es", "fr", "zh-cn", "ru" };

    for (String locale : locales) {

      // build up some text so detector can guess well
      String ret =
          IntStream.rangeClosed(1, 10)
              .mapToObj(i -> ft.transform(String.format("address.streetAddress,%s", locale), null))
              .collect(Collectors.joining(" "));
      Detector detector = DetectorFactory.create();
      detector.append(ret);
      String detected = detector.detect();

      Assert.assertEquals(
          String.format("%s locale does not match detected locale %s", locale, detected),
          locale,
          detected);
    }
  }
}
