@Faker
Feature: Testing out faker transformers

  @Smoke @Json @Cache @ResponseMatch
  Scenario: Testing generating a few types of props and validation from cache (FT2)
    Given I am a JSON API consumer
      And I am executing test "FT2"
     When I request GET "/mirror"
      And I set the JSON body to
      """
      {
        "lead": {
          "leadId": "{{faker::idNumber.valid,en-US::leadId}}",
          "first_name": "{{faker::name.firstName,en-US}}",
          "last_name": "{{faker::name.lastName,en-US}}",
          "suffix": "{{faker::name.suffix,en-US}}",
          "phone": "{{faker::name.phone,en-US}}",
          "address": {
            "line1": "{{faker::address.streetAddress,en-US}}",
            "city": "{{faker::address.city,en-US::city}}",
            "state": "{{faker::address.stateAbbr,en-US}}"
          }
        }
      }
      """
     Then I should get a status code of 200
      And the response value of "lead.leadId" should equal "{{cache::leadId}}"
      And the response value of "lead.address.city" should equal "{{cache::city}}"

  @Smoke @Json @Cache @ResponseMatch @Lang
  Scenario Outline: Testing generating a few types of props and validation from cache (FT2)
    Given I am a JSON API consumer
      And I am executing test "FT2"
     When I request GET "/mirror"
      And I set the JSON body to
      """
      {
        "lead": {
          "leadId": "{{faker::idNumber.valid,<LANG>::leadId}}",
          "first_name": "{{faker::name.firstName,<LANG>}}",
          "last_name": "{{faker::name.lastName,<LANG>}}",
          "suffix": "{{faker::name.suffix,<LANG>}}",
          "phone": "{{faker::name.phone,<LANG>}}",
          "address": {
            "line1": "{{faker::address.streetAddress,<LANG>}}",
            "city": "{{faker::address.city,<LANG>::city}}",
            "state": "{{faker::address.stateAbbr,<LANG>}}"
          }
        }
      }
      """
     Then I should get a status code of 200
      And the response value of "lead.leadId" should equal "{{cache::leadId}}"
      And the response value of "lead.address.city" should equal "{{cache::city}}"

    Examples:
    | LANG    |
    | en-US   |
    | ru      |
    | zh-CN   |
    | fr      |