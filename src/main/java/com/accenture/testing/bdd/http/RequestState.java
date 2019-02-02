package com.accenture.testing.bdd.http;

import com.accenture.testing.bdd.util.BDDConfig;
import com.typesafe.config.Config;
import io.restassured.http.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RequestState {

  private static final Logger LOG = LoggerFactory.getLogger(RequestState.class);
  private static Config config = BDDConfig.getConfig();
  private static Pattern QS_PATTERN = Pattern.compile("(\\w+)=?([^&]+)?");
  private Map<String, List<String>> params = new HashMap<>();
  private Map<String, String> headers = new HashMap<>();
  private String body;
  private Method httpMethod;
  private String uri;
  private String host;

  /**
   * get response object; will execute request. if it hasn't already been executed or it's been
   * reset
   *
   * @return a responsestate to play with
   */
  public abstract ResponseState getResponseState();

  /** resets the state of this object. */
  public void reset() {
    params.clear();
    resetHeaders();
    body = null;
    httpMethod = null;
    uri = null;
    setHost(config.getString("request.server.host"));
  }

  /**
   * resets the headers by clearing them and then loading the default headers from the
   * configuration.
   */
  public void resetHeaders() {
    headers.clear();
    config
        .getObjectList("request.defaults.headers")
        .forEach(
            obj -> {
              Map<String, Object> header = obj.unwrapped();
              header.forEach((k, v) -> setHeader(k, v.toString()));
            });
  }

  /**
   * get the HTTP method for this request.
   *
   * @return the http method.
   */
  public Method getHttpMethod() {
    return httpMethod;
  }

  /**
   * set method for the request.
   *
   * @param method the request method
   */
  public void setHttpMethod(String method) {
    try {
      httpMethod = Method.valueOf(method);
    } catch (IllegalArgumentException iae) {
      LOG.error("Not a valid httpmethod {}", method);
    } catch (NullPointerException npe) {
      LOG.error("method was null");
    }
  }

  /**
   * get the URI for the request.
   *
   * @return the URI for the request
   */
  public String getURI() {
    return uri;
  }

  /**
   * set the URI for the request.
   *
   * @param uri the uri for the request.
   */
  public void setURI(String uri) {
    setParamsFromURI(uri);
    if (Objects.nonNull(uri) && uri.contains("?")) {
      uri = uri.substring(0, uri.indexOf('?'));
    }
    this.uri = uri;
  }

  /**
   * get the URI for the request.
   *
   * @return the URI for the request
   */
  public String getHost() {
    return host;
  }

  /**
   * set the URI for the request.
   *
   * @param host the uri for the request.
   */
  public void setHost(String host) {
    this.host = host;
  }

  /**
   * get the request headers.
   *
   * @return the request headers
   */
  public Map<String, String> getHeaders() {
    return headers;
  }

  /**
   * Set map headers for the request.
   *
   * @param headers reset the headers to this map
   */
  public void setHeaders(Map<String, String> headers) {
    headers.putAll(headers);
  }

  /**
   * add header name/value to the request.
   *
   * @param name the header name
   * @param value the header value
   */
  public void setHeader(String name, String value) {
    headers.put(name, value);
  }

  /**
   * Get request paramters.
   *
   * @return the request parameters
   */
  public Map<String, List<String>> getParameters() {
    return params;
  }

  /**
   * get named parameter value.
   *
   * @param name the name of the parameter
   * @return the parameter value
   */
  public List<String> getParameter(String name) {
    return params.get(name);
  }

  /**
   * sets the parameters from a URI.
   *
   * @param uri the uri to parse
   */
  private void setParamsFromURI(String uri) {

    if (Objects.isNull(uri) || !uri.contains("?")) {
      return;
    }

    Matcher matcher = QS_PATTERN.matcher(uri.substring(uri.indexOf('?') + 1));
    while (matcher.find()) {
      setParameter(matcher.group(1), matcher.group(2));
    }
  }

  /**
   * add a named parameter (either qs or form depending on request type).
   *
   * @param name the parameter name
   * @param val the parameter value
   */
  public void setParameter(String name, String val) {
    if (StringUtils.isBlank(name)) {
      return;
    }
    params.computeIfAbsent(name, list -> new ArrayList<>()).add(val);
  }

  /**
   * get the request body.
   *
   * @return the request body
   */
  public String getBody() {
    return body;
  }

  /**
   * set the request body.
   *
   * @param body the body as text
   */
  public void setBody(String body) {
    this.body = body;
  }
}
