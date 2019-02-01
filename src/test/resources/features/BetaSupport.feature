@Advanced @Beta @Regression
Feature: Starter tests for beta functionality

  @Html @RemoteServer @ResponseMatch
  Scenario: Test basic HTML functionality
    Given I am a HTML API consumer
      And I am executing test "HTML1"
     When I request GET "/" on "http://example.com"
     Then I should get a status code of 200
      And the response value of "html.body.div.h1" should equal "Example Domain"

  @Xml @ResponseMatch
  Scenario: Test basic XML functionality
    Given I am a XML API consumer
      And I am executing test "PT1"
     When I request GET "/mirror"
      And I set the XML body to
      """
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
      """
     Then I should get a status code of 200
      And the response value of "root.results.cnt" should equal "2"
      And the response value of "results.items.element[0].title" should not equal "Example 2"