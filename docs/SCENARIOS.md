# Scenarios (well, actually Scenario Outlines)

If you've taken the [10 Minute Tutorial](https://docs.cucumber.io/guides/10-minute-tutorial/) from cucumber.io, you know what a scenario is - an individual test. You will have 1 to N of them in your .feature files.

By now you've seen all the examples of them throughout each of the readme's, so we're not going to repeat ourselves. What we do want to introduce, though, is the scenario outline.

A [scenario outline](https://docs.cucumber.io/gherkin/reference/#scenario-outline) works a lot like a template. It allows you to run the same "Scenario" multiple times with different values. You'll always find a use for this, but as the doc says, it's important to minimize it's usage for the sake of readability.

### A Bad Use Case

We've seen folks try all sorts of things with scenario outlines, since they only have to write one scenario and they can pass anything they want in.  

```gherkin

  Scenario Outline: Doing Everything
    Given I am a XML API consumer
      And I am executing test "<ID>"
     When I request GET "<ENDPOINT>"
     Then I should get a status code of 200
      And the response value of "<PATH>" should not equal "<VALUE>"
      
  Examples:
    | ID    | ENDPOINT    | PATH                 |   VALUE     |
    | TST1  | /ep1        | options[1].val       | Some Text   |
    | TST2  | /ep2        | results.item[0].val  | A Value     |
    | TST3  | /ep3        | user.name.value      | Mike        |

```

Let's use the above as an example. First, let us explain what's happening.

* Scenario Outline - This keyword explains that the scenario will be run multiple times
* <?> - Anything inside the greater/less than signs, is a variable, that will be replaced when the scenario is executed
* Examples - A table of values to replace when running the scenario.  Each row is an execution.

Now let's get back to what's wrong.  As we've said throughout the docs, our docs should be readable by all and easy to understand as we use them to convey requirements.  The above doesn't do this, though.  It hits a few endpoints, doesn't describe why it's doing it and just does some value matching.

This is a common shortcut folks take, which is against everything we are trying to accomplish!

> A scenario a single behavior, with a enough detail to describe it

### A Typical (Good) Use Case

Let's go back to our case from the [Generating Data](DATAGEN.md) section...

```gherkin
  Scenario: Create a sales lead record.
    Given I am a JSON API consumer
      And I am executing test "SALES_LEAD_CREATE"
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
```

In the above, we're trying to create a sales lead in english.  But what if we're a global system, that needs support for more than just english.  Do we create one of these for every lang we need to support?

No! We use a `Scenario Outline`.

```gherkin
  Scenario Outline: Create a sales lead record.
    Given I am a JSON API consumer
      And I am executing test "SALES_LEAD_CREATE-<LANG>"
     When I request GET "/mirror"
      And I set the JSON body to
      """
      {
        "lead": {
          "leadId": "{{faker::idNumber.valid,<LANG>::leadId-<LANG>}}",
          "first_name": "{{faker::name.firstName,<LANG>}}",
          "last_name": "{{faker::name.lastName,<LANG>}}",
          "suffix": "{{faker::name.suffix,<LANG>}}",
          "phone": "{{faker::name.phone,<LANG>}}",
          "address": {
            "line1": "{{faker::address.streetAddress,<LANG>}}",
            "city": "{{faker::address.city,<LANG>::city-<LANG>}}",
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
```

With some subtle changes, we can now run for 5 different languages to test out system support.  But what did we do?

1. We change "Scenario" to "Scenario Outline"
2. Where we were passing a hardcode value for language to the [faker macro](DATAGEN.md), we changed to use the outline variable syntax
3. We added the LANG variable to the test id and caches to make them unique and easier to debug.
4. We added the "Examples" table with all the languages we'd like to support with the test.

It's that easy!

> Make sure to check out our [Chaining](CHAINING.md), [Generatin Data](DATAGEN.md) and [GPath](GPATH.md) sections for more advanced usages of the library.
