package com.accenture.testing.bdd.api.http;

import com.accenture.testing.bdd.http.ResponseState;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import com.accenture.testing.bdd.http.RequestState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class APIRequestState extends RequestState {

  @ToString.Exclude
  private ResponseState responseState = null;
  private APIResponseStateType responseStateType;

  @Override
  public void reset() {
    responseState = null;
    super.reset();
  }

  /**
   * get the response. NOTE: if not explicitly set, restassured sets the charset and content-type
   * headers
   *
   * @return response object to play with
   */
  @Override
  public ResponseState getResponseState() {

    if (responseState != null) {
      return responseState;
    }

    // set up the request
    RequestSpecification request = RestAssured.given().baseUri(getHost()).basePath(getUri());

    // set up test headers
    getHeaders()
        .forEach(
            (el1, el2) -> {
              request.header(el1, el2);
            });

    // parameters
    getParameters()
        .forEach(
            (name, list) -> {
              request.queryParam(name, list);
            });

    // set the body (if needed)
    if (getBody() != null) {
      request.body(getBody());
    }

    // perform the request
    responseState = new APIResponseState(request.request(getHttpMethod()), getResponseStateType());

    // temporary hack to close response
    responseState.getResponse().body().asString();

    return responseState;
  }

}
