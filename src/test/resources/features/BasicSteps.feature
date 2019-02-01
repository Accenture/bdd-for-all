@BasicSteps @Smoke @Regression
Feature: Testing out the basic step definitions for JSON

  @Overides @Json @RemoteServer
  Scenario: Domain override (BS1)
    Given I am a JSON API consumer
      And I am executing test "BS1"
     When I request POST "/" on "http://example.com"
     Then I should get a status code of 200

  @Json @ResponseTime
  Scenario: Before timeout (BS2)
    Given I am a JSON API consumer
      And I am executing test "BS2"
     When I request GET "/delayed"
      And I request a maximum response time of 600
     Then I should get a status code of 200

  @Json
  Scenario: Status code (BS3)
    Given I am a JSON API consumer
      And I am executing test "BS3"
     When I request GET "/404"
     Then I should get a status code of 404

  @Overides @Json @CustomConsumer
  Scenario: Custom Consumer
    Given I'm a "Mobile device"
      And I am executing test "BS4"
     When I request GET "/json/users"
     Then I should get a status code of 200
      And the response value of "users[0].email" should equal "Sincere@april.biz"