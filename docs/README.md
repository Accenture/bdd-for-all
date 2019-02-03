No longer do your business, development and testing teams need to translate each others requirements and reports into something else to do their job.  This natural language, [behavior-driven development (BDD)](https://en.wikipedia.org/wiki/Behavior-driven_development) library exists to simplify the documenting of requirements and the testing of your API's.

* [Why This?](#why-this)
* [Process at a Glance](#process-at-a-glance)
* [Users Guide](USERGUIDE.md)

## Why This?

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

## Process at a Glance

In most environments, contributors - which include Product Owners, Subject Matter Experts, Architects, Solution Owner/Architects, Tech/Team Leads, and more - create [scenarios](docs/SCENARIOS.md) which are added to [feature files](docs/FEATURES.md). In most cases these are added to existing project repositories (or newly created ones).

The developer then reviews the requirements (or acceptance criteria) and creates functionality to support, while running those scnearios as as tests with every change to confirm that there's been no regressions and that the feature is functionally complete.

![The BDD + TDD Workflow](samples/bdd+tdd.png "BDD + TDD Workflow")

## Using

For the definitive guide, checkout the [User Guide](docs/USERGUIDE.md).