package com.accenture.testing.bdd.parameters;

import com.accenture.testing.bdd.http.ResponseState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamTransformer {

  private static final Logger LOG = LoggerFactory.getLogger(ParamTransformer.class);

  private static final Pattern PATTERN = Pattern.compile("\\{\\{(.*?)\\:\\:(.*?)(\\:\\:(.*?))?}}");
  private final List<ParamTransform> transforms = new ArrayList<>();
  private Map<String, ResponseState> results = new HashMap<>();
  private Map<String, String> cache = new HashMap<>();

  /**
   * add a param transform.
   *
   * @param pt the transform to add
   */
  public void addTransform(ParamTransform pt) {
    transforms.add(pt);
  }

  /**
   * get a transform by name.
   *
   * @param name the name of the transform
   * @return the named transform
   */
  public ParamTransform getTransform(String name) {
    return transforms
        .stream()
        .filter(transform -> transform.key().matches(name))
        .findFirst()
        .orElse(null);
  }

  /**
   * cache a result.
   *
   * @param id the id to save this result as
   * @param rs the object to cache
   */
  public void cache(String id, ResponseState rs) {
    results.put(id, rs);
  }

  /**
   * get a cached responsestate.
   *
   * @param id the id for the response
   * @return a matching response if one exists
   */
  public ResponseState getResponseState(String id) {
    return results.get(id);
  }

  /**
   * cache a value.
   *
   * @param id the id to save this result as
   * @param s the object to cache
   */
  public void cacheValue(String id, String s) {
    cache.put(id, s);
  }

  /**
   * get a cached value.
   *
   * @param id the id for the value
   * @return a matching value if one exists
   */
  public String getCachedValue(String id) {
    return cache.get(id);
  }

  /**
   * does this string contain a token pattern.
   *
   * @param input the input string
   * @return whether or not there's a token
   */
  public boolean hasToken(String input) {
    return PATTERN.matcher(input).find();
  }

  /**
   * transform the path provided.
   *
   * @param input the parameter string
   * @return a transformed string
   */
  public String transform(String input) {

    Matcher matcher = PATTERN.matcher(input);
    String ret = input;

    while (matcher.find()) {

      String orig = matcher.group(0);
      String id = matcher.group(1);
      String params = matcher.group(2);
      String cacheKey = matcher.group(4);

      try {

        ParamTransform transform = getTransform(id);
        if (transform == null) {
          throw new NotImplementedException("No known transform for: " + id);
        }

        String val = transform.transform(params, this);
        if (val != null) {
          ret = ret.replace(orig, val);
        }

        if (cacheKey != null) {
          cacheValue(cacheKey, val);
        }

      } catch (Exception e) {
        LOG.error("Trying to replace {}", matcher.group(0), e);
        throw e;
      }
    }

    return ret;
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }

}
