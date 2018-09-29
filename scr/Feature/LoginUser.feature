@tag1
Feature: Login API Oauth-token
  I want to test oauth-token api to check  login scenarios

  @tag1
  Scenario: Sucessful Login scenario
    Given User want to login using "oauth/token" api
    When user updates api body with "madhur.agarwal@emergenttech.com" and "Welcome@123"
    And send api "post" reuqest and excepts status code "201"
