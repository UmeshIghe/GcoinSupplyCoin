package stepDefinitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;

import apiRequests.LinkPartners;
import utils.DateUtils;
import utils.JsonReqResParams;
import utils.JsonUtil;
import utils.MailClient;
import utils.ReflectionUtils;
//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
//import cucumber.api.java.en.When;

public class CommomStepsNew {

	public static JsonUtil jsonUtil = new JsonUtil();
	public static JsonReqResParams reqResParams = new JsonReqResParams();
	ObjectMapper objectMapper = new ObjectMapper().configure(
			Feature.DEFAULT_VIEW_INCLUSION, false);

	// @Given("^user wants to send request using \"([^\"]*)\" with updated fields \"([^\"]*)\" and \"([^\"]*)\"$")
	public void user_wants_to_send_request_using_with_updated_fields_and(
			String apiClassName, String fieldName, String fieldValue) {

		String updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				getRequestObjectMapper(apiClassName), fieldName, fieldValue);
		reqResParams.setUpdatedReq(updatedRequest);

	}

	public void user_wants_to_send_request_using_withOut_updated_fields_and(
			String apiClassName) {

		String updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				getRequestObjectMapper(apiClassName), StringUtils.EMPTY,
				StringUtils.EMPTY);
		reqResParams.setUpdatedReq(updatedRequest);
	}

	// @Given("^user wants to send request using \"([^\"]*)\")")
	// public void user_wants_to_send_request(
	// String jsonFileName) {
	//
	// String updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
	// jsonFileName );
	// reqResParams.setUpdatedReq(updatedRequest);
	//
	// }

	// @When("^user send a post request to \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void user_send_a_post_request_to_and_expects_statusCode(
			String endPoint, String statusCode) {
		System.out.println("Sending request to : " + endPoint);
		reqResParams.setResponse(jsonUtil.postRequest(
				reqResParams.getUpdatedReq(), endPoint, statusCode));
	}

	// @Then("^save value of parameter from json \"([^\"]*)\"$")
	public void validate_response_message_for(String params) {
		System.out.println(reqResParams.getResponse().jsonPath()
				.getString(params));

		ReflectionUtils.setRequestField(reqResParams, params, reqResParams
				.getResponse().jsonPath().getString(params));
		reqResParams.setJwtAuth(reqResParams.getResponse().jsonPath()
				.getString(params));
	}

	// @Then("^save value of parameter from json \"([^\"]*)\" to field \"([^\"]*)\"$")
	public void save_value_of_parameter_from_json(String params,
			String fieldName) {
		System.out.println("from Json "
				+ params
				+ "="
				+ jsonUtil.extractValue(reqResParams.getResponse().body()
						.asString(), params));

		ReflectionUtils.setRequestField(reqResParams, fieldName, reqResParams
				.getResponse().jsonPath().getString(params));
		System.out.println("Setting Variable " + fieldName + "="
				+ ReflectionUtils.getRequestField(reqResParams, fieldName));
		// reqResParams.setJwtAuth(reqResParams.getResponse().jsonPath()
		// .getString(params));
	}

	// @When("^user send get request to api \"([^\"]*)\" with authId as header and expects statusCode \"([^\"]*)\"$")
	public void user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode(
			String endPoint, String statusCode) {
		System.out.println("Sending request to : " + endPoint);
		reqResParams.setResponse(jsonUtil.getRequest(endPoint, statusCode,
				reqResParams.getJwtAuth()));
	}

	// @When("^user send get request to api \"([^\"]*)\" with authId as header with updated request parameter \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode(
			String endPoint, String reqParam, String statusCode) {
		endPoint = endPoint + "/"
				+ ReflectionUtils.getRequestField(reqResParams, reqParam);
		System.out.println("Sending request to : " + endPoint);
		reqResParams.setResponse(jsonUtil.getRequest(endPoint, statusCode,
				reqResParams.getJwtAuth()));
	}

	// @When("^user send request to invite new user using \"([^\"]*)\" with endpoint \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void user_send_inviteUser(String apiClassName, String endPoint,
			String statusCode, String userType) {
		System.out.println("Sending request to : " + endPoint);

		endPoint = endPoint
				+ "/"
				+ ReflectionUtils.getRequestField(reqResParams,
						"organizationid") + "/user";

		String tempName = DateUtils.getDateTimeDDMMYYHHMMSS();
		String emailId = "Scrinviteuser+" + tempName + "@gmail.com";
		reqResParams.setEmailId(emailId);
		String userName = "MA" + tempName;
		reqResParams.setUserName(userName);
		String updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				getRequestObjectMapper(apiClassName), "email", emailId);
		updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				updatedRequest, "name", userName);
		updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				updatedRequest, "role", userType);
		reqResParams.setUpdatedReq(updatedRequest);

		reqResParams.setResponse(jsonUtil.postRequestWithAuth(
				reqResParams.getUpdatedReq(), endPoint, statusCode,
				reqResParams.getJwtAuth()));
	}

	public void user_send_inviteOrg(String apiClassName, String endPoint,
			String statusCode, String orgType) {
		System.out.println("Sending request to : " + endPoint);

		String tempName = DateUtils.getDateTimeDDMMYYHHMMSS();
		String emailId = "";
		String userName = "";
		if (orgType.contains("Refiner")) {
			userName = "Refiner" + tempName;
			emailId = "Scrinviteuser+" + userName + "@gmail.com";
			reqResParams.setRefineremailId(emailId);
		} else if (orgType.contains("Miner")) {
			userName = "Miner" + tempName;
			emailId = "Scrinviteuser+" + userName + "@gmail.com";
			reqResParams.setMineremailId(emailId);
		} else if (orgType.contains("Logistic")) {
			userName = "Logistic" + tempName;
			emailId = "Scrinviteuser+" + userName + "@gmail.com";
			reqResParams.setLogisticemailId(emailId);
		} else {
			userName = "Vault" + tempName;
			emailId = "Scrinviteuser+" + userName + "@gmail.com";
			reqResParams.setVaultemailId(emailId);
		}
		reqResParams.setEmailId(emailId);
		reqResParams.setUserName(userName);
		String updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				getRequestObjectMapper(apiClassName),
				"invited_members[0].email", emailId);
		updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				updatedRequest, "name", userName);
		updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				updatedRequest, "type", orgType);
		reqResParams.setUpdatedReq(updatedRequest);

		reqResParams.setResponse(jsonUtil.postRequestWithAuth(
				reqResParams.getUpdatedReq(), endPoint, statusCode,
				reqResParams.getJwtAuth()));
		System.out.println("Setting of Org ids");
		if (orgType.contains("Refiner")) {

			reqResParams.setRefinerOrgId(jsonUtil.extractValue(reqResParams
					.getResponse().body().asString(),
					"organization.organization_id"));
			System.out.println("Setting of Org id for Refiner: "
					+ reqResParams.getRefinerOrgId());
		} else if (orgType.contains("Miner")) {

			reqResParams.setMinerOrgId(jsonUtil.extractValue(reqResParams
					.getResponse().body().asString(),
					"organization.organization_id"));
			System.out.println("Setting of Org id for Miner: "
					+ reqResParams.getMinerOrgId());
		} else if (orgType.contains("Logistic")) {

			reqResParams.setLogisticOrgId(jsonUtil.extractValue(reqResParams
					.getResponse().body().asString(),
					"organization.organization_id"));
			System.out.println("Setting of Org id for Logistic: "
					+ reqResParams.getLogisticOrgId());
		} else {
			reqResParams.setVaultOrgId(jsonUtil.extractValue(reqResParams
					.getResponse().body().asString(),
					"organization.organization_id"));
			System.out.println("Setting of Org id for Vault: "
					+ reqResParams.getVaultOrgId());
		}

	}

	public void user_links_miner_and_refiner(String orgType, String endPoint,
			String partners, String statusCode) {
		// TODO Auto-generated method stub
//		reqResParams.setMinerOrgId("minerOrgId");
//		reqResParams.setRefinerOrgId("refinerOrgId");
//		reqResParams.setVaultOrgId("vaultOrgId");
//		reqResParams.setLogisticOrgId("logisticOrgId");

		String orgTypeID = getOrgTypeId(orgType);
		if (orgTypeID != null) {
			endPoint = endPoint + "/" + orgTypeID;
			System.out.println("Sending request to : " + endPoint);
			List<String> partToAdd = new ArrayList<>();
			if (partners.contains(",")) {
				String[] partArr = partners.split(",");

				for (String partType : partArr) {
					partToAdd.add(getOrgTypeId(partType));

				}

			} else {
				partToAdd.add(partners);
			}

			LinkPartners obj = new LinkPartners(partToAdd);
			try {
				String apiBody = objectMapper.writeValueAsString(obj);

				String updatedRequest = jsonUtil
						.updateJasonFileWithUserInputList(apiBody, "", "");

				reqResParams.setUpdatedReq(updatedRequest);
				reqResParams.setResponse(jsonUtil.postRequestWithAuth(
						reqResParams.getUpdatedReq(), endPoint, statusCode,
						reqResParams.getJwtAuth()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private String getOrgTypeId(String orgType) {
		// TODO Auto-generated method stub
		if ("Miner".equalsIgnoreCase(orgType)) {
			return reqResParams.getMinerOrgId();
		} else if ("Refiner".equalsIgnoreCase(orgType)) {
			return reqResParams.getRefinerOrgId();
		} else if ("Logistic".equalsIgnoreCase(orgType)) {
			return reqResParams.getLogisticOrgId();
		} else if ("Vault".equalsIgnoreCase(orgType)) {
			return reqResParams.getVaultOrgId();
		} else {
			return null;
		}

	}

	// @When("^user send request to request code api using \"([^\"]*)\" with endpoint \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void user_send_a_post_request_To_requestcode_api_and_expects_statusCode(
			String apiClassName, String endPoint, String statusCode) {
		System.out.println("Sending request to : " + endPoint);

		String updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				getRequestObjectMapper(apiClassName), "email",
				reqResParams.getEmailId());
		reqResParams.setUpdatedReq(updatedRequest);
		reqResParams.setResponse(jsonUtil.postRequestWithAuth(
				reqResParams.getUpdatedReq(), endPoint, statusCode,
				reqResParams.getJwtAuth()));
		reqResParams.setVerificationCode(new MailClient()
				.checkMail(reqResParams.getEmailId()));
		System.out.println("Set verfication coed="
				+ reqResParams.getVerificationCode());
	}

	// @When("^user send request to verify code api using \"([^\"]*)\" with endpoint \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void user_send_request_to_verify_code_api(String apiClassName,
			String endPoint, String statusCode) {
		System.out.println("Sending request to : " + endPoint);

		String updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				getRequestObjectMapper(apiClassName), "email",
				reqResParams.getEmailId());

		updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				updatedRequest, "code", reqResParams.getVerificationCode());

		reqResParams.setUpdatedReq(updatedRequest);

		reqResParams.setResponse(jsonUtil.postRequestWithAuth(
				reqResParams.getUpdatedReq(), endPoint, statusCode,
				reqResParams.getJwtAuth()));

	}

	// @When("^user send request to registerUser api using \"([^\"]*)\" with endpoint \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void user_send_request_to_registerUser_api(String apiClassName,
			String endPoint, String statusCode) {
		System.out.println("Sending request to : " + endPoint);

		String updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				getRequestObjectMapper(apiClassName), "email",
				reqResParams.getEmailId());

		updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				updatedRequest, "name", reqResParams.getUserName());

		reqResParams.setUpdatedReq(updatedRequest);

		reqResParams.setResponse(jsonUtil.postRequestWithAuth(
				reqResParams.getUpdatedReq(), endPoint, statusCode,
				reqResParams.getJwtAuth()));

	}

	// @When("^user send request to get user invite api \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void user_send_request_to_get_user_invite_api(String endPoint,
			String statusCode) {
		endPoint = endPoint.replace("{{user_id}}", reqResParams.getUserId());
		System.out.println("Sending request to : " + endPoint);

		reqResParams.setResponse(jsonUtil.getRequest(endPoint, statusCode,
				reqResParams.getJwtAuth()));

	}

	// @When("^user send request to invitation api \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void user_send_request_to_invitation_api(String endPoint,
			String statusCode) {
		endPoint = endPoint.replace("{{user_id}}", reqResParams.getUserId());
		endPoint = endPoint.replace("{{invitaion_id}}",
				reqResParams.getInvitationId());
		System.out.println("Sending request to : " + endPoint);

		reqResParams.setResponse(jsonUtil.postRequestWithAuth("", endPoint,
				statusCode, reqResParams.getJwtAuth()));

	}

	public String getRequestObjectMapper(String apiClassName) {
		Object obj = ReflectionUtils.getClass(apiClassName);
		try {
			return objectMapper.writeValueAsString(obj);

		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
