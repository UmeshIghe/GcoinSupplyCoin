package com.scr.responsiblegold.flows;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scr.responsiblegold.api.requests.AdminAuth;
import com.scr.responsiblegold.api.requests.RegisterUser;
import com.scr.responsiblegold.api.requests.RequestUser;
import com.scr.responsiblegold.step.definitions.CommomStepsNew;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserInviteFlowITCase {

	private static final Gson gson = new GsonBuilder().create();

	@Test(dataProvider = "orgType", priority = 1, groups = "a")
	public void testInviteOrgnization(String apiClassName, String userType) {
		System.out.println("UserInviteFlowITCase: testInviteOrgnization() START");
		CommomStepsNew.user_wants_to_send_request_using_withOut_updated_fields_and(gson.toJson(new AdminAuth()));
		CommomStepsNew.user_send_a_post_request_to_and_expects_statusCode("oauth/token", "201");
		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
		CommomStepsNew.save_value_of_parameter_from_json("organization_ids[0]", "organizationid");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode(
				"organization", "organizationid", "200");
		// used parametrization here , fileName and usertype is passed using
		// data provider
		CommomStepsNew.user_send_inviteOrg(apiClassName, "organization", "201", userType);
		CommomStepsNew.user_send_a_post_request_To_requestcode_api_and_expects_statusCode(
				RequestUser.class.getSimpleName(), "passwordless/start", "201");
		CommomStepsNew.user_send_request_to_verify_code_api("VerifyCode", "oauth/ro", "201");
		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
		CommomStepsNew.user_send_request_to_registerUser_api(RegisterUser.class.getSimpleName(), "user", "201");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
		CommomStepsNew.save_value_of_parameter_from_json("user_id", "userId");
		CommomStepsNew.user_send_request_to_get_user_invite_api("user/{{user_id}}/invitation", "200");
		CommomStepsNew.save_value_of_parameter_from_json("[0].invitation_id", "invitationId");
		CommomStepsNew.user_send_request_to_invitation_api("user/{{user_id}}/invitation/{{invitaion_id}}", "201");
		System.out.println("UserInviteFlowITCase: testInviteOrgnization() END");
	}

	@Test(dependsOnGroups = "a")
	public void ztestUserInviteFlow() {
		CommomStepsNew.user_wants_to_send_request_using_withOut_updated_fields_and(gson.toJson(new AdminAuth()));
		CommomStepsNew.user_send_a_post_request_to_and_expects_statusCode("oauth/token", "201");
		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
		CommomStepsNew.user_links_miner_and_refiner("Miner", "organization", "Refiner", "201");

		AdminAuth au = new AdminAuth();
		au.setUsername(CommomStepsNew.reqResParams.getMineremailId());

		CommomStepsNew.user_wants_to_send_request_using_withOut_updated_fields_and(gson.toJson(au));
		CommomStepsNew.user_send_a_post_request_to_and_expects_statusCode("oauth/token", "201");
		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
		
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
		CommomStepsNew.save_value_of_parameter_from_json("organization_ids[0]", "organizationid");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode(
				"organization", "organizationid", "200");
		
		CommomStepsNew.user_creates_dore_product("doreProduct.json", "product", "201");

	}
//		System.out.println("UserInviteFlowITCase: testUserInviteFlow() START");
//		CommomStepsNew.user_wants_to_send_request_using_withOut_updated_fields_and(gson.toJson(new AdminAuth()));
//		CommomStepsNew.user_send_a_post_request_to_and_expects_statusCode("oauth/token", "201");
//		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
//		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
//		CommomStepsNew.save_value_of_parameter_from_json("organization_ids[0]", "organizationid");
//		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode(
//				"organization", "organizationid", "200");
//		// used parameterization here , fileName and usertype is passed using
//		// data provider
//		CommomStepsNew.user_send_inviteUser(jsonFileName, "organization", "201", userType);
//		CommomStepsNew.user_send_a_post_request_To_requestcode_api_and_expects_statusCode("RequestUser",
//				"passwordless/start", "201");
//		CommomStepsNew.user_send_request_to_verify_code_api("VerifyCode", "oauth/ro", "201");
//		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
//		CommomStepsNew.user_send_request_to_registerUser_api("RegisterUser", "user", "201");
//		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
//		CommomStepsNew.save_value_of_parameter_from_json("user_id", "userId");
//		CommomStepsNew.user_send_request_to_get_user_invite_api("user/{{user_id}}/invitation", "200");
//		CommomStepsNew.save_value_of_parameter_from_json("[0].invitation_id", "invitationId");
//		CommomStepsNew.user_send_request_to_invitation_api("user/{{user_id}}/invitation/{{invitaion_id}}", "201");
//		System.out.println("UserInviteFlowITCase: testUserInviteFlow() END");
//	}

	@DataProvider(name = "orgType")
	public static Object[][] orgType() {
		return new Object[][] { { "InviteOrg", "Miner Operator" },
//			{ "InviteOrg", "//logistic Operator" },
				{ "InviteOrg", "Refiner Operator" } /* , { "InviteOrg", "Vault" } */ };
	}

	@DataProvider(name = "typeOfUser")
	public static Object[][] role() {
		return new Object[][] { { "InviteUser", "user" }, { "InviteUser", "admin" } };
	}
}
