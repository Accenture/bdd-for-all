package com.accenture.testing.bdd.http;

import com.accenture.testing.bdd.config.BDDConfig;
import com.typesafe.config.Config;
import io.restassured.http.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class RequestState {

  private static Config config = BDDConfig.getConfig();
  private static Pattern QS_PATTERN = Pattern.compile("(\\w+)=?([^&]+)?");
  @Getter Map<String, List<String>> parameters = new HashMap<>();
  @Getter Map<String, String> headers = new HashMap<>();
  @Getter Map<String, FileInfo> files = new HashMap<>();
  @Getter @Setter String body;
  @Getter String uri;
  @Getter Method httpMethod;
  @Getter @Setter String host;
  @Getter @Setter Boolean isForm = Boolean.FALSE;

  /**
   * get response object; will execute request. if it hasn't already been executed or it's been
   * reset
   *
   * @return a responsestate to play with
   */
  public abstract ResponseState getResponseState();

  /** resets the state of this object. */
  public void reset() {
    parameters.clear();
    files.clear();
    resetHeaders();
    body = null;
    httpMethod = null;
    uri = null;
    setHost(config.getString("request.server.host"));
    setIsForm(Boolean.FALSE);
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
   * set method for the request.
   *
   * @param method the request method
   */
  public void setHttpMethod(String method) {
    try {
      httpMethod = Method.valueOf(method);
    } catch (IllegalArgumentException iae) {
      log.error("Not a valid httpmethod {}", method);
    } catch (NullPointerException npe) {
      log.error("method was null");
    }
  }

  /**
   * set the URI for the request.
   *
   * @param uri the uri for the request.
   */
  public void setUri(String uri) {
    setParamsFromURI(uri);
    if (Objects.nonNull(uri) && uri.contains("?")) {
      uri = uri.substring(0, uri.indexOf('?'));
    }
    this.uri = uri;
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
   * get named parameter value.
   *
   * @param name the name of the parameter
   * @return the parameter value
   */
  public List<String> getParameter(String name) {
    return parameters.get(name);
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
    if (name == null || name.isBlank()) {
      return;
    }
    parameters.computeIfAbsent(name, list -> new ArrayList<>()).add(val);
  }

  /**
   * add header name/value to the request.
   *
   * @param name the header name
   * @param file the file location
   * @param type the media type
   */
  public void addFile(String name, String file, String type) {
    files.put(name, FileInfo.newInstance(type, file));
  }

}
