Feature: Main Method Test with simple get

  Scenario: Main method test with simple get
    Given I am a HTML API consumer
      And I am executing test "MAIN1"
     When I request GET "/" on "http://example.com"
     Then I should get a status code of 200