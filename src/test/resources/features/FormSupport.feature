@Upload
Feature: Testing out uploads

  @Smoke
  Scenario: Simple form parameter example
    Given I am a JSON API consumer
    And I am executing test "US01"
    When I request POST "/formPost"
    And I am submitting a form
    And I provide the parameter "first" with a value of "mike"
    And I provide the parameter "last" with a value of "something"
    And I should get a status code of 200

  @Smoke
  Scenario: Simple upload example
    Given I am a JSON API consumer
    And I am executing test "US01"
    When I request POST "/uploadFile"
    And I am submitting a form
    And I provide the file "file" at "src/test/resources/data/file.txt" as "text/plain"
    And I should get a status code of 200

  Scenario: Upload file with form parameters
    Given I am a JSON API consumer
    And I am executing test "US01"
    When I request POST "/uploadFileX"
    And I am submitting a form
    And I provide the file "file" at "src/test/resources/data/file.txt" as "text/plain"
    And I provide the parameter "first" with a value of "mike"
    And I provide the parameter "last" with a value of "something"
    And I should get a status code of 200
