@Headers
Feature: Testing out the parameter step definitions

 @Smoke
 Scenario: Test sending headers with stepdef (HS1)
 Given I am a JSON API consumer
   And I am executing test "HS1"
  When I request GET "/headers"
   And I provide the header "header1" with a value of "header1"
   And I provide the header "header2" with a value of "header2"
  Then I should get a status code of 200

 @Regression
 Scenario: Test sending invalid headers with stepdef (HS2)
 Given I am a JSON API consumer
   And I am executing test "HS2"
  When I request GET "/headers"
   And I provide the header "header1" with a value of "header3"
  Then I should get a status code of 404

 @Smoke
 Scenario: Test sending headers as datatable (HS3)
 Given I am a JSON API consumer
   And I am executing test "HS3"
  When I request GET "/headers"
   And I provide the headers
   | header1 | header1    |
   | header2 | header2    |
  Then I should get a status code of 200

 @Smoke
 Scenario: Test sending invalid headers as datatable (HS4)
 Given I am a JSON API consumer
   And I am executing test "HS4"
  When I request GET "/headers"
   And I provide the headers
   | header1 | header3    |
   | header2 | header4    |
  Then I should get a status code of 404

 @Smoke @ResponseMatch
 Scenario: Test getting header value (HS5)
 Given I am a JSON API consumer
   And I am executing test "HS5"
  When I request GET "/headers"
   And I provide the headers
   | header1 | header1    |
   | header2 | header2    |
   And match header named "user" with a value of "1"

 @Smoke @ResponseMatch @ResponseCache
 Scenario: Chaining and header caching (HS6)
 Given I am a JSON API consumer
   And I am executing test "HS6-1"
  When I request GET "/headers"
   And I provide the headers
   | header1 | header1    |
   | header2 | header2    |
   And record the response as "hedahs"
  Then I am a JSON API consumer
   And I am executing test "HS6-2"
  Then I request GET "json/users/{{response.headers::hedahs->user}}"
   And I should get a status code of 200

 @Smoke @ResponseMatch
 Scenario: Header value match as datatable (HS7)
 Given I am a JSON API consumer
   And I am executing test "HS7"
  When I request GET "/headers"
   And I provide the headers
   | header1 | header1    |
   | header2 | header2    |
   And match header named "user" with a value of "1"
   And the following header name/value combinations are equal
   | user | 1    |
   | x-id | 2    |

 @Regression @ResponseMatch 
 Scenario: Header value match as datatable (HS8)
 Given I am a JSON API consumer
   And I am executing test "HS8"
  When I request GET "/headers"
   And I provide the headers
   | header1 | header1    |
   | header2 | header2    |
   And the following header name/value combinations are not equal
   | user | 2    |
   | x-id | 3    |