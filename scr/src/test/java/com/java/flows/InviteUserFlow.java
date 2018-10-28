package com.java.flows;

import org.testng.annotations.Test;

import stepDefinitions.CommomSteps;

public class InviteUserFlow {
	CommomSteps steps = new CommomSteps();

	@Test
	public void InviteUserFlowTest() {

		steps.user_wants_to_send_request_using_withOut_updated_fields_and("admin-auth.json");
		steps.user_send_a_post_request_to_and_expects_statusCode("oauth/token", "201");
		steps.save_value_of_parameter_from_json("id_token", "jwtAuth");
		steps.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
		steps.save_value_of_parameter_from_json("organization_ids[0]", "organizationid");
		steps.user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode("organization", "organizationid", "200");
		steps.user_send_inviteUser("inviteUser.json", "organization", "201");
		steps.user_send_a_post_request_To_requestcode_api_and_expects_statusCode("requestUser.json", "passwordless/start", "201");
		steps.user_send_request_to_verify_code_api("verifyCode.json", "oauth/ro", "201");
		steps.save_value_of_parameter_from_json("id_token", "jwtAuth");
		steps.user_send_request_to_registerUser_api("registerUser.json", "user", "201");
		steps.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
		steps.save_value_of_parameter_from_json("user_id", "userId");
		steps.user_send_request_to_get_user_invite_api("user/{{user_id}}/invitation", "200");
		steps.save_value_of_parameter_from_json("[0].invitation_id", "invitationId");
		steps.user_send_request_to_invitation_api("user/{{user_id}}/invitation/{{invitaion_id}}", "201");
	}
}
