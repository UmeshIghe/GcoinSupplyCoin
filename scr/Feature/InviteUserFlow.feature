@tag1
Feature: Login API Oauth-token
  I want to test oauth-token api to check  login scenarios

  @tag1
  Scenario Outline: Sucessful Login scenario
    Given user wants to send request using "admin-auth.json" with updated fields <fieldName> and <fieldValue>
    When user send a post request to "oauth/token" and expects statusCode "201"
    Then save value of parameter from json "id_token"
    When user send get request to api "user" with authId as header and expects statusCode "200"

    Examples: 
      | fieldName  | fieldValue    |
      | "password" | "Welcome@123" |
