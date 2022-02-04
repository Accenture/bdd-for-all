Feature: Main Method Test of Sleep Function

  Scenario: Testing the sleep feature
    Given I am a JSON API consumer
      And I am executing test "MAIN-TIMEOUT"
      And I wait 450 "MILLISECONDS"
     When I request GET "/" on "http://example.com"
     Then I should get a status code of 200