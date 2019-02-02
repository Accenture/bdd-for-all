package com.accenture.testing.bdd.api.http;

import com.accenture.testing.bdd.http.ResponseState;
import io.restassured.response.Response;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APIResponseState extends ResponseState {

  private static final Logger LOG = LoggerFactory.getLogger(APIResponseState.class);
  private Response response;
  private APIResponseStateType responseStateType;

  public APIResponseState(Response response, APIResponseStateType responseStateType) {
    this.response = response;
    this.responseStateType = responseStateType;
  }

  @Override
  public Response getResponse() {
    return response;
  }

  @Override
  protected Object getValue(String path) {
    return responseStateType.evaluate(path, this);
  }

  @Override
  @Nullable
  public List<?> getValues(String path) {
    try {

      // first as list
      return (List<?>) responseStateType.evaluate(path, this);

    } catch (Exception e) {

      // now as single value
      try {

        Object val = getValue(path);
        if (Objects.nonNull(val)) {
          return Collections.singletonList(val);
        }

      } catch (Exception e2) {
        LOG.error("Trying to parse path {}", path, e);
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }

}
