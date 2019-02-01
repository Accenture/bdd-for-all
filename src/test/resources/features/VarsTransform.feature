@Faker
Feature: Testing out the variable transformer

  @Smoke @Json @Vars @ResponseMatch
  Scenario: Testing out (VT1)
    Given I am a JSON API consumer
      And I am executing test "VT1"
     When I request GET "/mirror"
      And I set the JSON body to
      """
      {
        "lead": {
          "first_name": "{{vars::User.Name.First}}"
        }
      }
      """
     Then I should get a status code of 200
      And the response value of "lead.first_name" should equal "Mike"