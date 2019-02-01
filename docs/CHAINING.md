# Chaining Responses (aka Workflows)

It's more common than not, that a scenario might need to retrieve or create data before it can make the request it needs.

We solve this with "chaining", which is the ability to save the response of any request to a cache and then reference it later using a simple macro.

Let's take the following case...

```gherkin
  Scenario: Retrieve a list of users and then look up the first one returned
    Given I am a JSON API consumer
      And I am executing test "RSJ0"
     When I request GET "/json/users"
      And record the response as "users"
      And I am a JSON API consumer
      And I am executing test "BSJ1-2"
     Then I request GET "json/users/{{response::users->users[0].id}}"
      And I should get a status code of 200
```

Let's break this down...

```gherkin
    Given I am a JSON API consumer
      And I am executing test "RSJ0"
     When I request GET "/json/users"
```

These first few we're used to using.  They set up the parser and provide the request path.

```gherkin
      And record the response as "users"
```

This is the start of the magic.  The above saves the response body and headers to a cache with the key "users"

```gherkin
      And I am a JSON API consumer
      And I am executing test "BSJ1-2"
     Then I request GET "json/users/{{response::users->users[0].id}}"
```

Next up, we're telling the program we want to send another request and setting up the new request path.  Except this time you'll notice a new syntax...

```
{{response::users->users[0].id}}
```
Using our standard macro syntax, this has three important parts...

| response  | users | -> | users[0].id |
|-----------|-------|----|-------------|
| This tells the program to look to the response macro | The cache key is next, this tells the response macro to grab the response with the key "users" | | And last, is our [GPath](GPATH.md) expression which is executed against the response |
 
Easy, right?

To save a response...

```gherkin
  And record the response as "users"
``` 

and to get a value out...

```gherkin
{{response::users->users[0].id}}
```

You can also access headers.  Using the above example, let's say the endpoint provided a one-time edit link for the user and you wanted to change his status to expired.

Well you can!!!!!

```gherkin
 And I request POST {{response.headers::users->X-Edit-Link}} 
```

It's that simple. Now to recap...

* {{response::ID->PATH}} will pull return the value of the PATH expression, executed against the saved response ID
* {{response.body::ID->PATH}} is really longhand for the above call
* {{response.headers::ID->NAME}} will grab the header value based on NAME from the cache with the provided ID

> As always you'll want to know how to use [GPath](GPATH.md) expressions in order to take advantage of this or any response matching functionality.

[Home](README.md) 