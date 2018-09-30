@tag1
Feature: Login API Oauth-token
  I want to test oauth-token api to check  login scenarios

  @tag1
  Scenario Outline: Sucessful Login scenario
    Given user wants to send request using "admin-auth.json" with updated fields <fieldName> and <fieldValue>
    When user send a post request to "oauth/token" and expects statusCode "201"
    #Then save value of parameter from json "id_token"
    Then save value of parameter from json "id_token" to field "jwtAuth"
    When user send get request to api "user" with authId as header and expects statusCode "200"
    Then save value of parameter from json "organization_ids[0]" to field "organizationid"
    When user send get request to api "organization" with authId as header with updated request parameter "organizationid" and expects statusCode "200"
    When user send request to invite new user using "inviteUser.json" with endpoint "organization" and expects statusCode "201"
    When user send request to request code api using "requestUser.json" with endpoint "passwordless/start" and expects statusCode "201"
    When user send request to verify code api using "verifyCode.json" with endpoint "oauth/ro" and expects statusCode "201"
    Then save value of parameter from json "id_token" to field "jwtAuth"
    When user send request to registerUser api using "registerUser.json" with endpoint "user" and expects statusCode "201"
    When user send get request to api "user" with authId as header and expects statusCode "200"
    Then save value of parameter from json "user_id" to field "userId"
    When user send request to get user invite api "user/{{user_id}}/invitation" and expects statusCode "200"
    Then save value of parameter from json "[0].invitation_id" to field "invitationId"
    When user send request to invitation api "user/{{user_id}}/invitation/{{invitaion_id}}" and expects statusCode "201"

    Examples: 
      | fieldName  | fieldValue                        |
      | "username" | "madhur.agarwal@emergenttech.com" |
