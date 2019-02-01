# Generating Data (and Variables)

To make life simpler - for us and you - we've exposed a *faker* macro to make it easy to generate all kinds of data.

Based on the [JAVA Faker](https://github.com/DiUS/java-faker) library (which itself is based on the [Ruby Faker](https://github.com/stympy/faker) library, this powerful macro let's you generate all kinds of data, in a bunch of languages!

### First an example

Let's say you want to create a record.  That record requires some user information, maybe an address, and phone and some other data.  You could hardcode this into your scenario, or, even better, you could use the faker macro...

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

### What We Did

Using our standard macro syntax this really breaks down to...

1. {{**faker**::idNumber.valid,en-US::leadId}} - `faker` is the name of the macro to be executed
2. {{faker::**idNumber.valid**,en-US::leadId}} - `isNumber.valid` are actually method calls on the [faker object](http://dius.github.io/java-faker/apidocs/com/github/javafaker/Faker.html), in this case faker.idNumber().valid()
3. {{faker::idNumber.valid,**en-US**)::leadId}} - `en-US` is the locale to be used to generate.  The faker lib supports like 40 [locales](https://github.com/DiUS/java-faker#supported-locales)
4. {{faker::idNumber.valid,en-US)**::leadId**}} - `::leadId` is optional. `::` is used as a separator, this tells the macro to store this value in the simple cache. `leadId` is the key for the cached value. 

As noted in #4 above the `::leadId` argument of the macro is optional, but it provides you a way to reference dynamically generated data for validations. Much the same way we use [Response Chaining](CHAINING.md), this is a powerful feature. Take for example, the lines...

```gherkin
      And the response value of "lead.leadId" should equal "{{cache::leadId}}"
      And the response value of "lead.address.city" should equal "{{cache::city}}"
```

In this case, when generating the data to send, we also told the program to cache.  The particular service we're using sends some elements back to us, which we can then validate to make sure the record was saved correctly.

> Want to run the same test case in multiple languages?  You can!  See [Scenarios](SCENARIOS.md) for more information.

