@ResponseMatch
Feature: Testing out the request step definitions for JSON

  @Smoke @Json @ResponseCache
  Scenario: Request chaining & simple cache test (RSJ0)
    Given I am a JSON API consumer
      And I am executing test "RSJ0"
     When I request GET "/json/users"
      And record the response as "users"
      And I am a JSON API consumer
      And I am executing test "BSJ1-2"
     Then I request GET "json/users/{{response::users->users[0].id}}"
      And I should get a status code of 200

  @Smoke @Json @GPath
  Scenario: Test simple evaluation to true/false (RSJ1)
    Given I am a JSON API consumer
      And I am executing test "RSJ1"
     When I request GET "/json/users"
     Then I should get a status code of 200
      And evaluating "users.count{ it.id == 1 } == 1" should return true
      And evaluating "users[0].id == 4" should return false

  @Smoke @Json
  Scenario: Test string matching (RSJ2)
    Given I am a JSON API consumer
      And I am executing test "RSJ2"
     When I request GET "/json/users"
     Then I should get a status code of 200
      And the response value of "users[0].email" should equal "Sincere@april.biz"
      And the response value of "users[0].address.city" should not equal "Boston"

  @Smoke @Json
  Scenario: Test integer matching (RSJ3)
    Given I am a JSON API consumer
      And I am executing test "RSJ3"
     When I request GET "/json/users"
     Then I should get a status code of 200
      And the response value of "users[0].id" should equal integer "1"
      And the response value of "users[0].id" should not equal integer "2"

  @Regression @Json
  Scenario: Test float matching (RSJ4)
    Given I am a JSON API consumer
      And I am executing test "RSJ4"
     When I request GET "/json/users"
     Then I should get a status code of 200
      And the response value of "users[0].address.geo.lng" should equal float "81.1496"
      And the response value of "users[0].address.geo.lng" should not equal float "55.554"

  @Regression @Json
  Scenario: Test boolean matching (RSJ5)
    Given I am a JSON API consumer
      And I am executing test "RSJ5"
     When I request GET "/json/todos"
     Then I should get a status code of 200
      And the response value of "todos[0].completed" should equal "FALSE"
      And the response value of "todos[0].completed" should not equal "TRUE"

  @Regression
  Scenario: Test long matching (RSJ6)

  @Smoke @Json @Elements
  Scenario: Path occurs X number of times (RSJ7)
    Given I am a JSON API consumer
      And I am executing test "RSJ7"
     When I request GET "/json/todos"
     Then path "todos.userId" must occur only 200 times

  @Smoke @Json @Elements
  Scenario: Path occurs more than X times (RSJ8)
    Given I am a JSON API consumer
      And I am executing test "RSJ8"
     When I request GET "/json/todos"
     Then path "todos.userId" must occur more than 199 times

  @Regression @Json @Elements
  Scenario: Path occurs less than X times (RSJ9)
    Given I am a JSON API consumer
      And I am executing test "RSJ9"
     When I request GET "/json/todos"
     Then path "todos.userId" must occur less than 201 times

  @Regression @Json @Elements
  Scenario: Path occurances are equal to or more than X times (RSJ10)
    Given I am a JSON API consumer
      And I am executing test "RSJ10"
     When I request GET "/json/todos"
     Then path "todos.userId" must occur at least 200 times

  @Regression @Json @Elements
  Scenario: Path occurances are less than or equal to X times (RSJ11)
    Given I am a JSON API consumer
      And I am executing test "RSJ11"
     When I request GET "/json/todos"
     Then path "todos.userId" must occur at most 200 times

  @Smoke @Json @ElementAnalysis
  Scenario: Path with value occurs X number of times (RSJ12)
    Given I am a JSON API consumer
      And I am executing test "RSJ12"
     When I request GET "/json/todos"
     Then the value "TRUE" must occur only 90 times for "todos.completed"

  @Regression @Json @ElementAnalysis
  Scenario: Path with value occurs more than X times (RSJ13)
    Given I am a JSON API consumer
      And I am executing test "RSJ13"
     When I request GET "/json/todos"
     Then the value "TRUE" must occur more than 89 times for "todos.completed"

  @Regression @Json @ElementAnalysis
  Scenario: Path with value occurs less than X times (RSJ14)
    Given I am a JSON API consumer
      And I am executing test "RSJ14"
     When I request GET "/json/todos"
     Then the value "TRUE" must occur less than 91 times for "todos.completed"

  @Regression @Json @ElementAnalysis
  Scenario: Path with value occurances are equal to or more than X times (RSJ15)
    Given I am a JSON API consumer
      And I am executing test "RSJ15"
     When I request GET "/json/todos"
     Then the value "TRUE" must occur at least 90 times for "todos.completed"

  @Regression @Json @ElementAnalysis
  Scenario: Path with value occurances are less than or equal to X times (RSJ16)
    Given I am a JSON API consumer
      And I am executing test "RSJ16"
     When I request GET "/json/todos"
     Then the value "TRUE" must occur at most 90 times for "todos.completed"

  @Regression @Json @ElementAnalysis
  Scenario: Path with numeric value occurs X number of times (RSJ17)
    Given I am a JSON API consumer
      And I am executing test "RSJ17"
     When I request GET "/json/todos"
     Then the integer "10" must occur only 20 times for "todos.userId"

  @Regression @Json @ElementAnalysis
  Scenario: Path with numeric value occurs more than X times (RSJ18)
    Given I am a JSON API consumer
      And I am executing test "RSJ18"
     When I request GET "/json/todos"
     Then the integer "10" must occur more than 19 times for "todos.userId"

  @Regression @Json @ElementAnalysis
  Scenario: Path with numeric value occurs less than X times (RSJ19)
    Given I am a JSON API consumer
      And I am executing test "RSJ19"
     When I request GET "/json/todos"
     Then the integer "10" must occur less than 21 times for "todos.userId"

  @Regression @Json @ElementAnalysis
  Scenario: Path with numeric value occurances are equal to or more than X times (RSJ20)
    Given I am a JSON API consumer
      And I am executing test "RSJ20"
     When I request GET "/json/todos"
     Then the integer "10" must occur at least 20 times for "todos.userId"

  @Regression @Json @ElementAnalysis
  Scenario: Path with numeric value occurances are less than or equal to X times (RSJ21)
    Given I am a JSON API consumer
      And I am executing test "RSJ21"
     When I request GET "/json/todos"
     Then the integer "10" must occur at least 20 times for "todos.userId"

  @Smoke @Json @Collections
  Scenario: Path has duplicates in collection (RSJ22)
    Given I am a JSON API consumer
      And I am executing test "RSJ22"
     When I request GET "/json/todos"
     Then path "todos.userId" does contain duplicates

  @Regression @Json @Collections
  Scenario: Path has duplicates in collection (RSJ23)
    Given I am a JSON API consumer
      And I am executing test "RSJ23"
     When I request GET "/json/todos"
     Then path "todos.id" does not contain duplicates

  @Smoke @Json @ElementAnalysis
  Scenario: Should contain the elements (RSJ24)
    Given I am a JSON API consumer
      And I am executing test "RSJ24"
     When I request GET "/json/users/1"
     Then the response should contain the following elements
     | user.id      |
     | user.name    |

  @Regression @Json @ElementAnalysis
  Scenario: Should contain the elements (RSJ25)
    Given I am a JSON API consumer
      And I am executing test "RSJ25"
     When I request GET "/json/users/1"
     Then the response should not contain the following elements
     | user.fakeElement |
     | user.nonElement  |
     | user.fakePath    |

  @Smoke @Json @ElementAnalysis
  Scenario: Elements match (RSJ26)
    Given I am a JSON API consumer
      And I am executing test "RSJ26"
     When I request GET "/json/todos"
     Then path value "todos[0].userId" should equal "todos[1].userId" value

  @Regression @Json @ElementAnalysis
  Scenario: Elements don't match (RSJ27)
    Given I am a JSON API consumer
      And I am executing test "RSJ27"
     When I request GET "/json/todos"
     Then path value "todos[0].id" should not equal "todos[1].id" value

  @Smoke @Json @ElementAnalysis
  Scenario: List of elements match (RSJ28)
    Given I am a JSON API consumer
    And I am executing test "RSJ28"
    When I request GET "/json/todos"
    Then the following path values should equal each other
    | todos[0].userId     | todos[1].userId       |
    | todos[0].completed  | todos[1].completed    |

  @Regression @Json @ElementAnalysis
  Scenario: List of elements do not match (RSJ28)
    Given I am a JSON API consumer
    And I am executing test "RSJ28"
    When I request GET "/json/todos"
    Then the following path values should not equal each other
    | todos[0].ud     | todos[1].id       |
    | todos[0].title  | todos[1].title    |