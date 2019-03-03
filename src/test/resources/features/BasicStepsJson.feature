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

  @Smoke @Json @GPath @ResponseMatch
  Scenario: Test setting up JSON as table (BSJ2)
    Given I am a JSON API consumer
      And I am executing test "BSJ2"
     When I request GET "/mirror"
      And I set the JSON body from values
      | users[0].id        | 1      |
      | users[0].name      | Mike   |
      | users[0].favorites | 1,2,3  |
      | users[0].active    | true   |
      | users[1].id        | 2      |
      | users[1].name      | Bob    |
      | users[1].favorites | 4,7,9  |
      | users[1].active    | false  |
     Then I should get a status code of 200
      And evaluating "users.count{ it.id == 1 } == 1" should return true
      And evaluating "users[0].id == 4" should return false
