package com.accenture.testing.bdd.api.http;

import com.accenture.testing.bdd.http.ResponseState;
import io.restassured.response.Response;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class APIResponseState extends ResponseState {

  private Response response;
  private APIResponseStateType responseStateType;

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
        log.error("Trying to parse path {}", path, e);
      }
    }
    return null;
  }

}
