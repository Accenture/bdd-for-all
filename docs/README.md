No longer do your business, development and testing teams need to translate each others requirements and reports into something else to do their job.  This natural language, [behavior-driven development (BDD)](https://en.wikipedia.org/wiki/Behavior-driven_development) library exists to simplify the documenting of requirements and the testing of your API's.

Built on <a href="https://cucumber.io/" target="_blank">Cucumber</a> and <a href="http://rest-assured.io/" target="_blank">Rest Assured</a>, this library provides a [standard grammar](docs/GRAMMAR.md) (based on Gherkin) that all teams within your organization can use to relay requirements and test your applications.

*Example*

```gherkin
    Given I am a JSON API consumer
      And I am executing test "RSJ2"
     When I request GET "/json/users"
     Then I should get a status code of 200
      And the response value of "users[0].email" should equal "Sincere@april.biz"
      And the response value of "users[0].address.city" should not equal "Boston"
```

Although there are libraries like <a href="https://github.com/intuit/karate" target="_blank">Karate</a> and others that provide similar functionality, they are focused mainly on technical users, which means your Product/Solution Owners and SME's are still writing their own docs.  

This library was born out of this frustration. 

* [Why BDD For All?](#why-bdd-for-all)
* [Process at a Glance](#process-at-a-glance)
* [Users Guide](USERGUIDE.md)

### Why BDD For All?

Requirements, always being interpreted from one document format (user story, technical docs, test cases, etc...) to another, create an additional overhead. Even worse, things often get lost or misinterpreted in translation.  We don't have time for that in one or two week sprints.

This library provides a lot of help out of the box to fix this...

* Simplifies and streamlines your Test Driven Development (TDD) flows.
* Simple [integration with build tools](RUNNING.md#running) (Maven, Gradle, & SBT) as well as your DevOps pipelines (Jenkins, Bamboo, CircleCI, etc...).
* Test against any API's [regardless of language or platform](RUNNING.md#running-stand-alone).
* Standard, well tested [step definitions](GRAMMAR.md) that are easy to read/write for the entire business.
* Create complex flows with [request chaining](CHAINING.md) (e.g. search for and then edit record).
* Data generation, supporting over 40 locales out of the box
* Run within [your project](RUNNING.md#running) or as a [command line](RUNNING.md#running-stand-alone) program
* Option to log all requests as [cURLs](OTHERFEATURES.md#curl-logging) to import into your favorite tools (postman, soapUI and more)
* Supports complex [Groovy GPath](GPATH.md) expressions for those cases that require additional complexity
* Easy to use with any test framework ([JUnit](RUNNING.md#running), TestNG, etc...) and works as a standalone library.
* Tests count towards your [code coverage](OTHERFEATURES.md#jacoco-code-coverage), reducing the need for unit tests.
* Simple [intuitive reporting](REPORTING.md) out of the box.
* [Expressions](OTHERFEATURES.md#running-select-tests-aka-tagging) that allow you easily choose what tests to run (e.g. smoke, regression, etc...).

## How's it Work?

In most environments, contributors - which include Product Owners, Subject Matter Experts, Architects, Solution Owner/Architects, Tech/Team Leads, and more - create [scenarios](docs/SCENARIOS.md) which are added to [feature files](docs/FEATURES.md). In most cases these are added to existing project repositories (or newly created ones).

The developer then reviews the requirements (or acceptance criteria) and creates functionality to support, while running those scnearios as as tests with every change to confirm that there's been no regressions and that the feature is functionally complete.

![The BDD + TDD Workflow](samples/bdd+tdd.png "BDD + TDD Workflow")

### Samples

The [test](../src/test/resources/features/) directory has a variety of sample feature files that show how to use all the features of the library.

## Using

For the definitive guide, checkout the [User Guide](USERGUIDE.md).