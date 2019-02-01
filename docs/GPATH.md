# GPath (the magic behind it all)

<a href="http://groovy-lang.org/processing-xml.html" target="_blank">GPath</a> is a widely used XML and JSON parser from the <a href="http://groovy-lang.org" target="_blank">groovy programming</a> language.

This is how we allow you to access and script against your responses.

### The Simple

You can access elements and arrays using a syntax common in almost any languages...

* DOT - elements seperated by periods form the path (e.g. results.cnt ).  
* BRACKETS - element proceeded by brackets surrounding a number provide array access (e.g. results[0].id)

Let's take the following JSON response...

```json
{
  "results": {
    "cnt": 2,
    "items": [
      {
        "id": 1,
        "title": "Example 1",
        "crazy-key": "Crazy value 1",
        "cost": 10
      },
      {
        "id": 2,
        "title": "Example 2",
        "crazy-key": "Crazy value 2",
        "cost": 30
      },
      {
        "id": 3,
        "title": "Title 3",
        "crazy-key": "Crazy value 3",
        "cost": 10
      }
    ]
  }
}
```

If I wanted to access the value for cnt, all I need to do is say `results.cnt` or to get the id of the first result, I just say `results[0].id`

Now let's put that in the context of a scenario...

```gherkin
    Given I am a JSON API consumer
      And I am executing test "RSJ2"
     When I request GET "/search"
     Then I should get a status code of 200
      And the response value of "results.cnt" should equal integer 2
```

The above scenario would pass and everyone would be happy!  We could also peek into the array and check a text value...


```gherkin
    Given I am a JSON API consumer
      And I am executing test "RSJ2"
     When I request GET "/search"
     Then I should get a status code of 200
      And the response value of "results.items[0].title" should not equal "Example 2"
```

We can even do negative matching as seen above! 

But what about XML?

Well, let's take the following XML...

```xml
<root>
  <results>
    <cnt>2</cnt>
    <items>
       <element>
          <cost>10</cost>
          <crazy-key>Crazy value 1</crazy-key>
          <id>1</id>
          <title>Example 1</title>
       </element>
       <element>
          <cost>30</cost>
          <crazy-key>Crazy value 2</crazy-key>
          <id>2</id>
          <title>Example 2</title>
       </element>
    </items>
  </results>
</root>
```

Now what if we wanted to match the cnt value from that?  Guess what?  Same syntax...

```gherkin
    Given I am a XML API consumer
      And I am executing test "RSJ2"
     When I request GET "/search"
     Then I should get a status code of 200
      And the response value of "results.cnt" should equal integer "2"
```

Changing from JSON consumer to XML consumer is all you need to do to make it work.  What about matching up the title?

Well, that's a little different, but only because we introduced a new element (XML is a little too verbose)...

```gherkin
    Given I am a XML API consumer
      And I am executing test "RSJ2"
     When I request GET "/search"
     Then I should get a status code of 200
      And the response value of "results.items.element[0].title" should not equal "Example 2"
```

As you can see, we need to add the .element because of the way XML has to nest elements. 

> Play around with the gists to see what you can do - [XML gist](https://groovy-playground.appspot.com/#?load=e99d19c5fd7eea34fd5ab913a892f347) and the [JSON gist](https://groovy-playground.appspot.com/#?load=1a549fdc317e77d268b2b3f5902c1ebe)

### Advanced Usage

Outside of the simple path query syntax, you also have the power of groovy at your fingertips.  Let's try this again...

```json
{
  "results": {
    "cnt": 2,
    "items": [
      {
        "id": 1,
        "title": "Example 1",
        "crazy-key": "Crazy value 1",
        "cost": 10
      },
      {
        "id": 2,
        "title": "Example 2",
        "crazy-key": "Crazy value 2",
        "cost": 30
      },
     {
       "id": 3,
       "title": "Title 3",
       "crazy-key": "Crazy value 3",
       "cost": 10
     }
    ]
  }
}
```

Above we have the same JSON from before, but now we want to do something a little more complicated, like sum the cost of all items.  This is simple with the GPath syntax...

```gherkin
    Given I am a XML API consumer
      And I am executing test "RSJ2"
     When I request GET "/search"
     Then I should get a status code of 200
      And evaluating "results.items.cost.sum() == 50" should return true
```

Wow that was easy, but what if we wanted a sum of all the nodes whose title contained the word "Example"...

```gherkin
    Given I am a XML API consumer
      And I am executing test "RSJ2"
     When I request GET "/search"
     Then I should get a status code of 200
      And evaluating "results.items.findAll { it.title.contains("Example") }.cost.sum()" should return true
```

See, we can do some really cool stuff here. [Here's the gist](https://groovy-playground.appspot.com/#?load=d67a69e8d12ea827c24cca77ae143281), see what you can do.
 
Hopefully, for the sake of readability you don't have to use some of the more advanced syntax, but this library would be useless if it didn't support exceptions to the rule. 

> Recommended Reading - http://groovy-lang.org/processing-xml.html and http://james-willett.com/2017/05/rest-assured-gpath-json/