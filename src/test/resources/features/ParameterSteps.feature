@Params
Feature: Testing out the parameter step definitions

  @Smoke @Json
  Scenario: Test sending parameters normally (PS1)
  Given I am a JSON API consumer
    And I am executing test "PS1"
   When I request GET "/params?param1=param1&param2=param2"
   Then I should get a status code of 200

  @Smoke @Json
  Scenario: Test sending parameters with stepdef (PS2)
  Given I am a JSON API consumer
    And I am executing test "PS2"
   When I request GET "/params"
    And I provide the parameter "param1" with a value of "param1"
    And I provide the parameter "param2" with a value of "param2"
   Then I should get a status code of 200

  @Smoke @Json
  Scenario: Test sending invalid parameters with stepdef (PS3)
  Given I am a JSON API consumer
    And I am executing test "PS3"
   When I request GET "/params"
    And I provide the parameter "param1" with a value of "param1"
   Then I should get a status code of 404

  @Smoke @Json
  Scenario: Test sending parameters as datatable (PS4)
  Given I am a JSON API consumer
    And I am executing test "PS4"
   When I request GET "/params"
    And I provide the parameters
    | param1 | param1    |
    | param2 | param2    |
   Then I should get a status code of 200

  @Smoke @Json
  Scenario: Test sending parameters as datatable (PS5)
  Given I am a JSON API consumer
    And I am executing test "PS5"
   When I request GET "/params"
    And I provide the parameters
    | param1 | param1    |
    | param2 | param3    |
   Then I should get a status code of 404

  @Smoke @Json
  Scenario: Mixing and matching add params (PS6)
  Given I am a JSON API consumer
    And I am executing test "PS3"
   When I request GET "/params?param1=param1"
    And I provide the parameter "param2" with a value of "param2"
   Then I should get a status code of 200