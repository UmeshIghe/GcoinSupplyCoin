@tag0
Feature: Login API Oauth-token
  I want to test oauth-token api to check  login scenarios

  #@tag1
  #Scenario Outline: Sucessful Login scenario2
  #Given user wants to send request  "admin-auth.json"
  #When user send a post request to "oauth/token" and expects statusCode "201"
  #Then validate response message for "something"
  @tag0
  Scenario Outline: Sucessful Login scenario
    Given user wants to send request using "admin-auth.json" with updated fields <fieldName> and <fieldValue>
    When user send a post request to "oauth/token" and expects statusCode "201"
    Then save value of parameter from json "id_token"

    Examples: 
      | fieldName  | fieldValue    |
      | "password" | "Welcome@123" |
