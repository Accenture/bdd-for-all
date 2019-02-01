# Grammar (aka Step Definitions)

Step Definitions are code bocks associated with an [expression](https://docs.cucumber.io/cucumber/step-definitions/#expressions) that link to one or more [Gherkin steps](https://docs.cucumber.io/gherkin/reference/#steps).

We wanted to create a simple lexicon with this library. Although there are many ways to describe a situation or step, the goal is to keep the these limited, which in turn makes them easier to use.
<br /><br /><br />

***
#### *Cheatsheet* | [The Basics](#basics) | [Request Params](#params) | [Request Payloads](#payloads) | [Setting Headers](#headers) | [Handling the Response](#response) <a name="#top">&nbsp;</a>
***

<br />

| Id  | Step                                                                                                                  | Applies To | Description                                                                                                                                   | Params  |
|-----|-----------------------------------------------------------------------------------------------------------------------|------------|-----------------------------------------------------------------------------------------------------------------------------------------------|---------|
| 1   | I am a (JSON\|XML) API consumer                                                                                       | Request    | Initializes a new JSON or XML request                                                                                                         | [examples](#basics) |
| 1a  | I'm a {string}                                                                                                        | Request    | Initializes a new JSON or XML request using a custom description (e.g. I'm a "Mobile device".  Useful when thinking more of consumers/devices.| [examples](#basics) |
| 2   | I am executing test {string}                                                                                          | Request    | Set's the test id and adds it as the "X-Correlation-ID" header (name of the header configurable - see [configuration](docs/CONFIGURATION.md)) | [examples](#basics) |
| 3   | I request (GET\|POST\|PUT\|PATCH\|DELETE) {string}                                                                                        | Request    | Set's the request type and path for the request.                                                                                              | [examples](#basics) |
| 4   | I request (GET\|POST\|PUT\|PATCH\|DELETE) {string} on {string}                                                                            | Request    | Set's the request type and path for the request, while overriding the [default host](CONFIGURATION.md) for the request.                       | [examples](#basics) |
| 5   | I set the (JSON\|XML) body to                                                                                         | Request    | Set's the body as well as the content type based on the content provided.                                                                     | [examples](#payloads) |
| 5a  | I set the (JSON\|XML) body from values                                                                                | Request    | Set's the body as well as the content type based on the content provided using a datatable of name value pairs and dot notations (e.g. users[0].name) | [examples](#payloads) |
| 6   | I set the SOAPAction to {string} and body as                                                                          | Request    | Shortcut to supply a SOAPAction in addition to the payload.  An alternative to calling the above step and adding a header step.               | [examples](#payloads) |
| 7   | I provide the parameter {string} with a value of {string}                                                             | Request    | Allows you to set individual parameters - an alternative to setting them as part of the path (e.g. /path?param=param)                         | [examples](#params) |
| 8   | I provide the parameters                                                                                              | Request    | Provide a list of parameters as a datatable - an alternative to setting them as part of the path (e.g. /path?param=param&param2=param2...)    | [examples](#params) |
| 9   | I provide the header {string} with a value of {string}                                                                | Request    | Add a named header to the request in addition to the default headers you can add through [configuration](CONFIGURATION.md).                   | [examples](#headers) |
| 10  | I provide the headers                                                                                                 | Request    | Add a set of headers using a datatable                                                                                                        | [examples](#headers) |
| 11  | I should get a status code of {int}                                                                                   | Request    | Match the response code returned by the server                                                                                                | [examples](#basics) |
| 12  | record the response as {string}                                                                                       | Request    | Save this request to the cache, so it's [headers and body](CHAINING.md) can be referenced later.                                              | [examples](#response) |
| 13  | evaluating {string} should return (true\|false)                                                                        | Response   | Evaluate a [GPath](GPATH.md) expression against the response body (advanced use)                                                              | [examples](#response) |
| 14  | the response value of {string} (should\|should not) equal {string}                                                     | Response   | Match a JSON/XML path value against a string or boolean value                                                                                 | [examples](#response) |
| 15  | the response value of {string} (should\|should not) equal (integer\|float\|long) {string}                                | Response   | Match a JSON/XML path against a numeric value                                                                                                 | [examples](#response) |
| 16  | path {string} must occur (only\|more than\|at least\|less than\|at most) {integer} times                                  | Response   | Match the number of occurances of a given path.                                                                                               | [examples](#response) |
| 17  | the value {string} must occur (only\|more than\|at least\|less than\|at most) {integer} times for {string}                | Response   | Match the number of occurances of a path with a given string or boolean value.                                                                | [examples](#response) |
| 18  | the (integer\|float\|long) {string} must occur (only\|more than\|at least\|less than\|at most) {integer} times for {string} | Response   | Match the number of occurances of a path with given numeric value.                                                                            | [examples](#response) |
| 19  | path {string} (does\|does not) contain duplicates                                                                      | Response   | Check to see if a given path collection contains duplicates                                                                                   | [examples](#response) |
| 20  | the response (should\|should not) contain the following elements                                                       | Response   | Check to see if the response has the elements list in the proceeding datable                                                                  | [examples](#response) |
| 21  | path value {string} should (equal\|not equal) {string} value                                                           | Respsonse  | Compare two response paths                                                                                                                    | [examples](#response) |
| 22  | the following path values should (equal\|not equal) each other                                                         | Response   | Compare the paths in the proceeding datatable                                                                                                 | [examples](#response) |
| 23  | match header named {string} with a value of {string}                                                                  | Response   | Match a header value                                                                                                                          | [examples](#response) |
| 24  | the following header name/value combinations are (equal\|not equal)                                                    | Response   | Match the proceeding header/value combinations                                                                                                | [examples](#response) |
| 25  | I wait {long} (MILLISECONDS\|SECONDS\|MINUTES)                                                                        | Request   | Sleep for a timeframe before executing the next step, could actually be any JAVA TimeUnit                                                       | [examples](#basics) |

Some things to remember...

* Strings in parens - e.g. (should|should not) represent the text options you can choose from.  You can only choose one.
* Strings - e.g. {string} - should be in quotes "I am a string"
* True and false values are always represented as "TRUE" and "FALSE"
* Header values are always string, no numeric or boolean comparisons
* You can use variables in as any string/boolean/numeric value (see [Chaining](CHAINING.md) and [Data Generation](DATAGEN.md) for more)

### Examples

The following show examples of each of the step definitions.  More can be found in the [test](../test/resources/features/) directory in each of the .feature files.

#### The Basics <a name="basics">&nbsp;</a>[(back to top)](#top)

There are step definitions you will use almost all the time (if not all the time).  These include first 4 steps in the above table as well as #11.  These are all about request initialization and grabbing the initial response, see the below examples...

```gherkin
  Scenario: Status code
    Given I am a XML API consumer
      And I am executing test "XML_404"
     When I request GET "/404"
     Then I should get a status code of 404
     
  Scenario: Simple Post
    Given I am a JSON API consumer
      And I am executing test "SIMPLE_POST"
     When I request POST "/post"
     Then I should get a status code of 200

  Scenario: Domain override
    Given I am a JSON API consumer
      And I am executing test "DOMAIN_OVR"
     When I request POST "/" on "http://example.com"
     Then I should get a status code of 200     
```

In the first scenario, I am initializing a test as an XML consumer (e.g. `I am a XML API consumer`) as opposed to the second scenario which initializes as a JSON consumer (e.g. `I am a JSON API consumer`).

I then set up the test id for this scenario `I am executing test "XML_404"`, which by default adds the "X-Correlation-ID" header to the request (although the name of this header is [configurable](CONFIGURATION.md).)

After that, I make a GET request for the path /404 (e.g. `I request GET "/404"`).  Instead of a GET this could be a POST like it is in the second scenario (`I request POST "/post"`), but this tells our program the HTTP method and the path to hit.

Once everything is set up, I execute the request and check the status code with the last line `I should get a status code of 200`.

You'll also notice in the last scenario we specify a host (e.g. `I request POST "/" on "http://example.com"`).  You can configure a [default host](CONFIGURATION.md), but if for any reason you want to hit different domain, you can by using this step definition. 

> NOTE: Although not required, it's highly recommended you set the test id.  This will let you [track and debug](https://www.baeldung.com/mdc-in-log4j-2-logback) your applications much better.

```gherkin
   Scenario: Custom Consumer
     Given I'm a "Mobile device"
       And I am executing test "CUSTOM_CONSUMER"
      When I request GET "/json/users"
      Then I should get a status code of 200
       And the response value of "users[0].email" should equal "Sincere@april.biz"
```

The snippet above shows the scenario using a custom "initial context" or "given" (e.g. I'm a "Mobile Device").  In BDD it's always good to relate stories to a particular user or context.  This little feature let's you alias the JSON or XML options so you can fit it better into your organization.

You must configure each of these contexts in your application.conf file. If there's no mapping in the configuration file, will default to JSON parser.  You can see a working example in our test configuration - [/src/test/resources/application.conf](/src/test/resources/application.conf) - and test case - [/src/test/resources/features/BasicSteps.feature](/src/test/resources/features/BasicSteps.feature) in test BS4.

#### Setting up Request Parameters <a name="params">&nbsp;</a>[(back to top)](#top)

There are a few ways to set up request parameters.  It's really around readability and what works best for the particular scenario.

```gherkin
  Scenario: Test sending parameters normally (SCENARIO 1)
  Given I am a JSON API consumer
   When I request GET "/params?param1=param1&param2=param2"
   Then I should get a status code of 200

   Scenario: Test sending parameters with a simple stepdef  (SCENARIO 2)
   Given I am a JSON API consumer
    When I request GET "/params"
     And I provide the parameter "param1" with a value of "param1"
     And I provide the parameter "param2" with a value of "param2"
    Then I should get a status code of 200
    
   Scenario: Test sending parameters as datatable  (SCENARIO 3)
   Given I am a JSON API consumer
    When I request GET "/params"
     And I provide the parameters
     | param1 | param1    |
     | param2 | param2    |
    Then I should get a status code of 200
```

As you can see, we're sending the same request three different ways.

* SCENARIO 1 - We're simple sending it as part of the path. This is useful when there are one or two params with no encoding needed
* SCENARIO 2 - We're using the step definition to set each parameter, much like calling request.setParam(val, val). Will handle encoding of parameter names and values and is really good when there are one or two params.
* SCENARIO 3 - When you have a lot of params and need the names/values encoded, this would be the best method.

> Remember, it's all about readability!

#### Request Payloads <a name="payloads">&nbsp;</a>[(back to top)](#top)

There are times - a lot of them - when we need to send over a JSON or XML payload in the body of our request.  The following shows how we'd accomplish this.

```gherkin
  Scenario: Testing a post
    Given I am a JSON API consumer
      And I am executing test "BSJ2"
     When I request POST "/json/users/post"
      And I set the JSON body to
      '''
      {
        "user": {
          "id": 1,
          "name": "Mike P",
          "complete": false
        }
      }
      '''
     Then I should get a status code of 201
     
  Scenario: Testing with SOAPAction
    Given I am a XML API consumer
      And I am executing test "BSJ2"
     When I request POST "/json/users/post"
      And I set the SOAPAction to "http://example.com/#GetDepartureBoard" and body as
      '''
      <element>
        <item>Hello!</item>
      </element>
      '''
     Then I should get a status code of 201
     
     
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
```

Nice and easy, right? 

You can set the type, in this case it's JSON, but you could set it to XML changing the step to `I set the XML body to`. This works with both POST as shown above and with GET (e.g. `I request GET "/json/users/post"`) as well.

The second scenario shows a common shortcut we found ourselves using a lot. For soap requests (e.g. XML) it let's you set the SOAPAction header and the body with one step definition.

For the third scenario, we're using data tables.  Using a simple "dot notation", you can set up complex objects.  In this case, the final JSON would look something like...

```json
{
  "users": [
    {
      "favorites": [
        1,
        2,
        3
      ],
      "name": "Mike",
      "active": true,
      "id": 1
    },
    {
      "favorites": [
        4,
        7,
        9
      ],
      "name": "Bob",
      "active": false,
      "id": 2
    }
  ]
}
```

As you can see, we can have simple and complex objects, numbers and booleans all with this simple syntax.  Some teams find this easier to use when working with the business.

Another important note, this will add one of the following headers based on the type of payload...

* JSON - 'Content-Type: application/json; charset=utf-8'
* XML - 'Content-Type: application/xml; charset=utf-8'

Make note, it's just not the content type that get's set, but also the charset.

> Notice the three ticks at the top and bottom of the payload, this let's you write your JSON or XML unencumbered.




#### Setting up the Headers <a name="headers">&nbsp;</a>[(back to top)](#top)

Whether it's set up content type, authorization, language type headers or more, we always find the need to add headers to our requests and this library gives you a few ways to do so.

```gherkin
 Scenario: Sending headers with stepdef
 Given I am a JSON API consumer
   And I am executing test "HS1"
  When I request GET "/headers"
   And I provide the header "header1" with a value of "header1"
   And I provide the header "header2" with a value of "header2"
  Then I should get a status code of 200

 Scenario: Sending headers as datatable
 Given I am a JSON API consumer
   And I am executing test "HS3"
  When I request GET "/headers"
   And I provide the headers
   | header1 | header1    |
   | header2 | header2    |
  Then I should get a status code of 200
```

The first option, much like the way we can set a [parameter](#params) with a step def, set's a single header. This is convenient for sending one or two headers over at a time.

And just like params, you have the option to specify headers in a data table as well (e.g. the second scenario).

But that's not all!

You can also add default headers to every request by adding them to the [configuration](CONFIGURATION.md) and as we mentioned in the [basics](#basics) section above, when the following stepdef is executed...

```gherkin
And I am executing test "HS3"
```

The value HS3 is added as a header with the name "X-Correlation-ID", the name of which is also [configurable](CONFIGURATION.md).

> When deciding on the method to set your headers, think about how they are used.  Are they static (like user agent)? Or is it a basic auth which will change from run to run.  This will help you decide if you should do through configuration or make it part of the scenario.

#### Handling the Response <a name="response">&nbsp;</a>[(back to top)](#top)

We have a variety of ways of validating response data - pretty much rows #11 on in the [cheatsheet](#top).  The best ways to see these in action is to check our test...

* [JSON Responses](../src/test/resources/features/ResponseStepsJson.feature)
* [XML Responses](../src/test/resources/features/ResponseStepsJson.feature)
* [Response Headers](../src/test/resources/features/HeaderSteps.feature)

Also remember, to review [request chaining](CHAINING.md) and [data generation](DATAGEN.md) as they allow you to create complex scenarios and workflows.

> Don't forget to read up on the [GPath](GPATH.md) syntax, as you will be using it with everything you do.
