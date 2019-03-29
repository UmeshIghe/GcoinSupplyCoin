package com.scr.responsiblegold.step.definitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.scr.responsiblegold.api.requests.DoreProducts;
import com.scr.responsiblegold.api.requests.LinkPartners;
import com.scr.responsiblegold.utils.DateUtils;
import com.scr.responsiblegold.utils.JsonReqResParams;
import com.scr.responsiblegold.utils.JsonUtil;
import com.scr.responsiblegold.utils.MailClient;
import com.scr.responsiblegold.utils.ReflectionUtils;
import com.scr.responsiblegold.utils.ScannerExample;

import junit.framework.Assert;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CommomStepsNew {

	public static JsonUtil jsonUtil = new JsonUtil();
	public static JsonReqResParams reqResParams = new JsonReqResParams();
	private static ObjectMapper objectMapper = new ObjectMapper().configure(Feature.DEFAULT_VIEW_INCLUSION, false);

	public static void user_wants_to_send_request_using_with_updated_fields_and(String apiClassName, String fieldName,
			String fieldValue) {

		String updatedRequest = jsonUtil.updateJsonFileWithUserInputList(getRequestObjectMapper(apiClassName),
				fieldName, fieldValue);
		reqResParams.setUpdatedReq(updatedRequest);

	}

	public static void user_wants_to_send_request_using_withOut_updated_fields_and(String jsonString) {

		String updatedRequest = jsonUtil.updateJsonFileWithUserInputList(jsonString, StringUtils.EMPTY,
				StringUtils.EMPTY);

		reqResParams.setUpdatedReq(updatedRequest);
	}

	public static void user_send_a_post_request_to_and_expects_statusCode(String endPoint, String statusCode) {
		log.info("Sending request to : " + endPoint);

		reqResParams.setResponse(jsonUtil.postRequest(reqResParams.getUpdatedReq(), endPoint, statusCode));
	}

	public static void validate_response_message_for(String params) {
		System.out.println(reqResParams.getResponse().jsonPath().getString(params));

		ReflectionUtils.setRequestField(reqResParams, params, reqResParams.getResponse().jsonPath().getString(params));
		reqResParams.setJwtAuth(reqResParams.getResponse().jsonPath().getString(params));
	}

	public static void save_value_of_parameter_from_json(String params, String fieldName) {
		System.out.println("from Json " + params + "="
				+ jsonUtil.extractValue(reqResParams.getResponse().body().asString(), params));

		ReflectionUtils.setRequestField(reqResParams, fieldName,
				reqResParams.getResponse().jsonPath().getString(params));
		System.out.println(
				"Setting Variable " + fieldName + "=" + ReflectionUtils.getRequestField(reqResParams, fieldName));

	}

	public static void user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode(String endPoint,
			String statusCode) {
		System.out.println("Sending request to : " + endPoint);
		reqResParams.setResponse(jsonUtil.getRequest(endPoint, statusCode, reqResParams.getJwtAuth()));
	}

	public static void user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode(
			String endPoint, String reqParam, String statusCode) {
		endPoint = endPoint + "/" + ReflectionUtils.getRequestField(reqResParams, reqParam);
		System.out.println("Sending request to : " + endPoint);
		reqResParams.setResponse(jsonUtil.getRequest(endPoint, statusCode, reqResParams.getJwtAuth()));
		reqResParams.setCompanyPrefix(reqResParams.getResponse().jsonPath().getString("company_prefix"));

	}

	public static void user_send_inviteUser(String inviteUserJson, String endPoint, String statusCode,
			String userType) {
		System.out.println("Sending request to : " + endPoint);

		endPoint = endPoint + "/" + reqResParams.getOrganizationid() + "/user";

		String tempName = DateUtils.getDateTimeDDMMYYHHMMSS();
		String emailId = "scrinviteuser+testautomation" + tempName + "@gmail.com";
		reqResParams.setEmailId(emailId);
		String userName = "MA" + tempName;
		reqResParams.setUserName(userName);
		String updatedRequest = jsonUtil.updateJsonFileWithUserInputList(getRequestObjectMapper(inviteUserJson),
				"email", emailId);
		updatedRequest = jsonUtil.updateJsonFileWithUserInputList(updatedRequest, "name", userName);
		updatedRequest = jsonUtil.updateJsonFileWithUserInputList(updatedRequest, "role", userType);
		reqResParams.setUpdatedReq(updatedRequest);

		reqResParams.setResponse(jsonUtil.postRequestWithAuth(reqResParams.getUpdatedReq(), endPoint, statusCode,
				reqResParams.getJwtAuth()));
	}

	public static void user_send_inviteOrg(String apiClassName, String endPoint, String statusCode, String orgType) {
		System.out.println("Sending request to : " + endPoint);

		String tempName = DateUtils.getDateTimeDDMMYYHHMMSS();
		String emailId = "";
		String userName = "";
		if (orgType.contains("Refiner")) {
			userName = "Refiner" + tempName;
			emailId = "scrinviteuser+testautomation" + userName + "@gmail.com";
			reqResParams.setRefineremailId(emailId);
		} else if (orgType.contains("Miner")) {
			userName = "Miner" + tempName;
			emailId = "scrinviteuser+testautomation" + userName + "@gmail.com";
			reqResParams.setMineremailId(emailId);
		} else if (orgType.contains("Logistic")) {
			userName = "Logistic" + tempName;
			emailId = "scrinviteuser+testautomation" + userName + "@gmail.com";
			reqResParams.setLogisticemailId(emailId);
		} else {
			userName = "Vault" + tempName;
			emailId = "scrinviteuser+testautomation" + userName + "@gmail.com";
			reqResParams.setVaultemailId(emailId);
		}
		reqResParams.setEmailId(emailId);
		reqResParams.setUserName(userName);
		String updatedRequest = jsonUtil.updateJsonFileWithUserInputList(getRequestObjectMapper(apiClassName),
				"invited_members[0].email", emailId);
		updatedRequest = jsonUtil.updateJsonFileWithUserInputList(updatedRequest, "name", userName);
		updatedRequest = jsonUtil.updateJsonFileWithUserInputList(updatedRequest, "type", orgType);

		reqResParams.setUpdatedReq(updatedRequest);

		reqResParams.setResponse(jsonUtil.postRequestWithAuth(reqResParams.getUpdatedReq(), endPoint, statusCode,
				reqResParams.getJwtAuth()));
		System.out.println("Setting of Org ids");
		if (orgType.contains("Refiner")) {

			reqResParams.setRefinerOrgId(jsonUtil.extractValue(reqResParams.getResponse().body().asString(),
					"organization.organization_id"));
			System.out.println("Setting of Org id for Refiner: " + reqResParams.getRefinerOrgId());
		} else if (orgType.contains("Miner")) {

			reqResParams.setMinerOrgId(jsonUtil.extractValue(reqResParams.getResponse().body().asString(),
					"organization.organization_id"));
			System.out.println("Setting of Org id for Miner: " + reqResParams.getMinerOrgId());
		} else if (orgType.contains("Logistic")) {

			reqResParams.setLogisticOrgId(jsonUtil.extractValue(reqResParams.getResponse().body().asString(),
					"organization.organization_id"));
			System.out.println("Setting of Org id for Logistic: " + reqResParams.getLogisticOrgId());
		} else {
			reqResParams.setVaultOrgId(jsonUtil.extractValue(reqResParams.getResponse().body().asString(),
					"organization.organization_id"));
			System.out.println("Setting of Org id for Vault: " + reqResParams.getVaultOrgId());
		}

	}

	public static void user_create_report(String apiClassName, String endPoint, String reqParam, String rqParam1,
			String statusCode, String templateType) {
		System.out.println("Sending request to : " + endPoint);

		endPoint = endPoint + "/" + ReflectionUtils.getRequestField(reqResParams, reqParam) + "/" + rqParam1;

		String orgId = ReflectionUtils.getRequestField(reqResParams, reqParam);

		String updatedRequest = jsonUtil.updateJsonFileWithUserInputList(getRequestObjectMapper(apiClassName),
				"organization_id", orgId);
		updatedRequest = jsonUtil.updateJsonFileWithUserInputList(updatedRequest, "template", templateType);

		reqResParams.setUpdatedReq(updatedRequest);

		reqResParams.setResponse(jsonUtil.postRequestWithAuth(reqResParams.getUpdatedReq(), endPoint, statusCode,
				reqResParams.getJwtAuth()));
		System.out.println("Setting of Org ids");

	}

	public static void user_links_miner_and_refiner(String orgType, String endPoint, String partners,
			String statusCode) {

//		reqResParams.setMinerOrgId("ck7AhDL");
//		reqResParams.setRefinerOrgId("ckxoNva");
//		reqResParams.setVaultOrgId("cihmUXJ");
//		reqResParams.setLogisticOrgId("ciVHvKS");

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
				partToAdd.add(getOrgTypeId(partners));
			}

			LinkPartners obj = new LinkPartners(partToAdd);
			try {
				String apiBody = objectMapper.writeValueAsString(obj);

				String updatedRequest = jsonUtil.updateJsonFileWithUserInputList(apiBody, "", "");

				reqResParams.setUpdatedReq(updatedRequest);
				reqResParams.setResponse(jsonUtil.postRequestWithAuth(reqResParams.getUpdatedReq(), endPoint,
						statusCode, reqResParams.getJwtAuth()));
			} catch (IOException e) {
				log.error(e.getMessage());
			}

		}
	}

	private static String getOrgTypeId(String orgType) {
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

	public static void user_send_a_post_request_To_requestcode_api_and_expects_statusCode(String apiClassName,
			String endPoint, String statusCode) {
		System.out.println("Sending request to : " + endPoint);

		String updatedRequest = jsonUtil.updateJsonFileWithUserInputList(getRequestObjectMapper(apiClassName), "email",
				reqResParams.getEmailId());
		reqResParams.setUpdatedReq(updatedRequest);
		reqResParams.setResponse(jsonUtil.postRequestWithAuth(reqResParams.getUpdatedReq(), endPoint, statusCode,
				reqResParams.getJwtAuth()));
		reqResParams.setVerificationCode(new MailClient().checkMail(reqResParams.getEmailId()));
		System.out.println("Set verfication coed=" + reqResParams.getVerificationCode());
	}

	public static void user_send_request_to_verify_code_api(String apiClassName, String endPoint, String statusCode) {
		System.out.println("Sending request to : " + endPoint);

		String updatedRequest = jsonUtil.updateJsonFileWithUserInputList(getRequestObjectMapper(apiClassName), "email",
				reqResParams.getEmailId());

		updatedRequest = jsonUtil.updateJsonFileWithUserInputList(updatedRequest, "code", "1111111");

		reqResParams.setUpdatedReq(updatedRequest);

		reqResParams.setResponse(jsonUtil.postRequestWithAuth(reqResParams.getUpdatedReq(), endPoint, statusCode,
				reqResParams.getJwtAuth()));

	}

	public static void user_send_request_to_registerUser_api(String apiClassName, String endPoint, String statusCode) {
		System.out.println("Sending request to : " + endPoint);

		String updatedRequest = jsonUtil.updateJsonFileWithUserInputList(getRequestObjectMapper(apiClassName), "email",
				reqResParams.getEmailId());

		updatedRequest = jsonUtil.updateJsonFileWithUserInputList(updatedRequest, "name", reqResParams.getUserName());

		reqResParams.setUpdatedReq(updatedRequest);

		reqResParams.setResponse(jsonUtil.postRequestWithAuth(reqResParams.getUpdatedReq(), endPoint, statusCode,
				reqResParams.getJwtAuth()));

	}

	public static void user_send_request_to_get_user_invite_api(String endPoint, String statusCode) {
		endPoint = endPoint.replace("{{user_id}}", reqResParams.getUserId());
		System.out.println("Sending request to : " + endPoint);

		reqResParams.setResponse(jsonUtil.getRequest(endPoint, statusCode, reqResParams.getJwtAuth()));

	}

	public static void user_send_request_to_invitation_api(String endPoint, String statusCode) {
		endPoint = endPoint.replace("{{user_id}}", reqResParams.getUserId());
		endPoint = endPoint.replace("{{invitaion_id}}", reqResParams.getInvitationId());
		System.out.println("Sending request to : " + endPoint);

		reqResParams.setResponse(jsonUtil.postRequestWithAuth("", endPoint, statusCode, reqResParams.getJwtAuth()));

	}

	public static void user_send_get_request_to_api_with_authId_as_header_with_orgId_and_expects_statusCode(
			String endPoint, String reqParam, String rqParam1, String statusCode) {
		endPoint = endPoint + "/" + ReflectionUtils.getRequestField(reqResParams, reqParam) + "/" + rqParam1;
		System.out.println("Sending request to : " + endPoint);
		reqResParams.setResponse(jsonUtil.getRequest(endPoint, statusCode, reqResParams.getJwtAuth()));

	}

	public static void user_send_get_request_to_api_with_authId_as_header_with_orgId_and_exports_csv(String endPoint,
			String reqParam, String rqParam1, String rqParam2, String statusCode) {
		endPoint = endPoint + "/" + ReflectionUtils.getRequestField(reqResParams, reqParam) + "/" + rqParam1 + "?"
				+ rqParam2;
		System.out.println("Sending request to : " + endPoint);
		reqResParams.setResponse(jsonUtil.getRequest(endPoint, statusCode, reqResParams.getJwtAuth()));

	}

	public static void user_send_get_request_to_api_with_authId_as_header_with_reportId_and_expects_statusCode(
			String endPoint, String reqParam, String rqParam1, String reportId, String statusCode) {
		endPoint = endPoint + "/" + ReflectionUtils.getRequestField(reqResParams, reqParam) + "/" + rqParam1 + "="
				+ ReflectionUtils.getRequestField(reqResParams, reportId);
		System.out.println("Sending request to : " + endPoint);
		reqResParams.setResponse(jsonUtil.getRequest(endPoint, statusCode, reqResParams.getJwtAuth()));

	}

	public static void user_send_get_request_to_api_with_reportType_and_expects_statusCode(String endPoint,
			String reqParam, String reqParam1, String reqParam2, String templateType, String statusCode) {

		endPoint = endPoint + "/" + ReflectionUtils.getRequestField(reqResParams, reqParam) + "/" + reqParam1 + "?"
				+ reqParam2 + "=" + templateType;

		System.out.println("Sending request to : " + endPoint);
		reqResParams.setResponse(jsonUtil.getRequest(endPoint, statusCode, reqResParams.getJwtAuth()));

	}

	public static void user_send_delete_request_to_api_with_authId_as_header_with_reportId_and_expects_statusCode(
			String endPoint, String reqParam, String reqParam1, String reportId, String statusCode) {

		endPoint = endPoint + "/" + ReflectionUtils.getRequestField(reqResParams, reqParam) + "/" + reqParam1 + "/"
				+ ReflectionUtils.getRequestField(reqResParams, reportId);
		System.out.println("Sending request to : " + endPoint);
		reqResParams.setResponse(jsonUtil.deleteRequestWithAuth(endPoint, statusCode, reqResParams.getJwtAuth()));

	}

	public static void assert_compare_value_of_parameter_from_json(String params, String fieldName) {
		System.out.println("from Json " + params + "="
				+ jsonUtil.extractValue(reqResParams.getResponse().body().asString(), params));

		String jsonValue = jsonUtil.extractValue(reqResParams.getResponse().body().asString(), params);
		Assert.assertTrue(jsonValue.equals(fieldName));
		System.out.println(
				"Setting Variable " + fieldName + "=" + ReflectionUtils.getRequestField(reqResParams, fieldName));

	}

	public static void assert_not_null_value_of_parameter_from_json(String params) {
		System.out.println("from Json " + params + "="
				+ jsonUtil.extractValue(reqResParams.getResponse().body().asString(), params));

		String jsonValue = jsonUtil.extractValue(reqResParams.getResponse().body().asString(), params);
		Assert.assertNotNull(jsonValue);

	}

	public static String getRequestObjectMapper(String apiClassName) {
		Object obj = ReflectionUtils.getClass(apiClassName);
		try {
			return objectMapper.writeValueAsString(obj);

		} catch (JsonGenerationException e) {
			log.error(e.getMessage());
		} catch (JsonMappingException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static void user_creates_dore_product(String doreClassName, String endPoint, String statusCode) {

		com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
		objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY).withGetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
		try {
			Object obj = ReflectionUtils.getClass(doreClassName);
			String jsonString = objectMapper.writeValueAsString(obj);
			System.out.println(jsonString);

			String updatedRequest = jsonUtil.updateJsonFileWithUserInputList(jsonString, "company_prefix",
					reqResParams.getCompanyPrefix());
			String referenceNo = RandomStringUtils.randomNumeric(7);

			updatedRequest = jsonUtil.updateJsonFileWithUserInputList(updatedRequest, "display_name",
					"Dore" + referenceNo);

			updatedRequest = jsonUtil.updateJsonFileWithUserInputList(updatedRequest,
					"shared_properties.['Item Reference Number']", referenceNo);
			updatedRequest = jsonUtil.updateJsonFileWithUserInputList(updatedRequest, "item_reference", referenceNo);

			reqResParams.setUpdatedReq(updatedRequest);
			reqResParams.setResponse(jsonUtil.postRequestWithAuth(reqResParams.getUpdatedReq(), endPoint, statusCode,
					reqResParams.getJwtAuth()));

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void verifyHeaders(String expcsvheaders) {
		ScannerExample.headerVerify(reqResParams.getResponse().body().asString(), expcsvheaders);
	}
	public static void user_retires_product(String statusCode, String endPoint) {

		String productId = jsonUtil.extractValue(reqResParams.getResponse().body().asString(), "_id");
		endPoint = "organization/" + reqResParams.getOrganizationid() + "/product/" + productId + "/" + endPoint;
		System.out.println("Sending request to : " + endPoint);
		jsonUtil.postRequestWithAuth("", endPoint, statusCode, reqResParams.getJwtAuth());
	}

}
