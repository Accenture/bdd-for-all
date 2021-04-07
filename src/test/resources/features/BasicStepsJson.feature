@BasicSteps
Feature: Testing out the basic step definitions for JSON

  @Json @Smoke
  Scenario: Testing a post (BSJ1)
    Given I am a JSON API consumer
      And I am executing test "BSJ2"
     When I request POST "/json/users/post"
      And I set the JSON body to
      """
      {}
      """
     Then I should get a status code of 201

  @Smoke @Json @ResponseMatch
  Scenario: Test setting up JSON as file (BSJ2)
    Given I am a JSON API consumer
    And I am executing test "BSJ2"
    When I request GET "/mirror"
    And I set the JSON body from file "src/test/resources/data/payload.json"
    Then I should get a status code of 200
    And the response value of "firstName" should equal "mike"
    And the response value of "nested.lastName" should equal "parcewski"

  @Smoke @Json @GPath @ResponseMatch
  Scenario: Test setting up JSON as table (BSJ3)
    Given I am a JSON API consumer
      And I am executing test "BSJ3"
     When I request GET "/mirror"
      And I set the JSON body from values
      | users[0].id        | 1        |
      | users[0].name      | Mike     |
      | users[0].active    | true     |
      | users[1].id        | 2        |
      | users[1].name      | Bob      |
      | users[1].active    | false    |
     Then I should get a status code of 200
      And evaluating "users.count{ it.id == 1 } == 1" should return true
      And evaluating "users[0].id == 4" should return false

  @Smoke @Json @ResponseMatch @Datatable
  Scenario: Testing comma separated values not as array (FT5)
    Given I am a JSON API consumer
    And I am executing test "FT5"
    When I request GET "/mirror"
    And I set the JSON body from values
      | name9 | baker, chef, fun |
    Then I should get a status code of 200
    And the response value of "name9" should equal "baker, chef, fun"

  @Smoke @Json @ResponseMatch @Datatable
  Scenario: Testing comma separated values as an array (FT6)
    Given I am a JSON API consumer
    And I am executing test "FT6"
    When I request GET "/mirror"
    And I set the JSON body from values
      | numbers | [one,two,three] |
    Then I should get a status code of 200
    And the response value of "numbers[0]" should equal "one"
