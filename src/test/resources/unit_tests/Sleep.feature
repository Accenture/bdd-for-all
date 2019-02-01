Feature: Feature file for testing with main method

  Scenario: Simple GET request
    Given I am a JSON API consumer
      And I am executing test "TIMEMOUT"
      And I wait 500 "MILLISECONDS"
     When I request GET "/" on "http://example.com"
     Then I should get a status code of 200