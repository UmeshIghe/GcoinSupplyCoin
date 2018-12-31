package com.java.flows;

import org.testng.annotations.DataProvider;

import org.testng.annotations.Test;

import stepDefinitions.CommomStepsNew;

public class InviteUserFlowNew {
	CommomStepsNew steps = new CommomStepsNew();

	@DataProvider(name = "typeOfUser")
	public static Object[][] role() {
		return new Object[][] { { "InviteUser", "user" },
				{ "InviteUser", "admin" }, { "InviteOrg", "Refiner Operator" } };
	}

	@Test(dataProvider = "typeOfUser")
	public void InviteUserFlowTest(String jsonFileName, String userType) {

		steps.user_wants_to_send_request_using_withOut_updated_fields_and("AdminAuth");
		steps.user_send_a_post_request_to_and_expects_statusCode("oauth/token",
				"201");
		steps.save_value_of_parameter_from_json("id_token", "jwtAuth");
		steps.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode(
				"user", "200");
		steps.save_value_of_parameter_from_json("organization_ids[0]",
				"organizationid");
		steps.user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode(
				"organization", "organizationid", "200");
		// used parametrization here , fileName and usertype is passed using
		// data provider
		steps.user_send_inviteUser(jsonFileName, "organization", "201",
				userType);
		steps.user_send_a_post_request_To_requestcode_api_and_expects_statusCode(
				"RequestUser", "passwordless/start", "201");
		steps.user_send_request_to_verify_code_api("VerifyCode", "oauth/ro",
				"201");
		steps.save_value_of_parameter_from_json("id_token", "jwtAuth");
		steps.user_send_request_to_registerUser_api("RegisterUser", "user",
				"201");
		steps.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode(
				"user", "200");
		steps.save_value_of_parameter_from_json("user_id", "userId");
		steps.user_send_request_to_get_user_invite_api(
				"user/{{user_id}}/invitation", "200");
		steps.save_value_of_parameter_from_json("[0].invitation_id",
				"invitationId");
		steps.user_send_request_to_invitation_api(
				"user/{{user_id}}/invitation/{{invitaion_id}}", "201");
	}

	@DataProvider(name = "orgType")
	public static Object[][] orgType() {
		return new Object[][] { { "InviteOrg", "Refiner Operator" } };
	}

	@Test(dataProvider = "orgType")
	public void InviteOrgnization(String apiClassName, String userType) {

		steps.user_wants_to_send_request_using_withOut_updated_fields_and("AdminAuth");
		steps.user_send_a_post_request_to_and_expects_statusCode("oauth/token",
				"201");
		steps.save_value_of_parameter_from_json("id_token", "jwtAuth");
		steps.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode(
				"user", "200");
		steps.save_value_of_parameter_from_json("organization_ids[0]",
				"organizationid");
		steps.user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode(
				"organization", "organizationid", "200");
		// used parametrization here , fileName and usertype is passed using
		// data provider
		steps.user_send_inviteOrg(apiClassName, "organization", "201",
				userType);
		steps.user_send_a_post_request_To_requestcode_api_and_expects_statusCode(
				"RequestUser", "passwordless/start", "201");
		steps.user_send_request_to_verify_code_api("VerifyCode", "oauth/ro",
				"201");
		steps.save_value_of_parameter_from_json("id_token", "jwtAuth");
		steps.user_send_request_to_registerUser_api("RegisterUser", "user",
				"201");
		steps.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode(
				"user", "200");
		steps.save_value_of_parameter_from_json("user_id", "userId");
		steps.user_send_request_to_get_user_invite_api(
				"user/{{user_id}}/invitation", "200");
		steps.save_value_of_parameter_from_json("[0].invitation_id",
				"invitationId");
		steps.user_send_request_to_invitation_api(
				"user/{{user_id}}/invitation/{{invitaion_id}}", "201");

	}
}
