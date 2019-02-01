Feature: Feature file for testing with main method

  Scenario: Simple GET request
    Given I am a HTML API consumer
      And I am executing test "BS1"
     When I request GET "/" on "http://example.com"
     Then I should get a status code of 200