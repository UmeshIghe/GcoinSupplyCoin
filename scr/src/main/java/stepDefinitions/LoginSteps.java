package stepDefinitions;

import utils.DateUtils;
import utils.JsonReqResParams;
import utils.JsonUtil;
import utils.MailClient;
import utils.ReflectionUtils;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginSteps {

	public static JsonUtil jsonUtil = new JsonUtil();
	public static JsonReqResParams reqResParams = new JsonReqResParams();

	@Given("^user wants to send request using \"([^\"]*)\" with updated fields \"([^\"]*)\" and \"([^\"]*)\"$")
	public void user_wants_to_send_request_using_with_updated_fields_and(
			String jsonFileName, String fieldName, String fieldValue) {

		String updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				jsonFileName, fieldName, fieldValue);
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

	@When("^user send a post request to \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void user_send_a_post_request_to_and_expects_statusCode(
			String endPoint, String statusCode) throws Throwable {
		System.out.println("Sending request to : " + endPoint);
		reqResParams.setResponse(jsonUtil.postRequest(
				reqResParams.getUpdatedReq(), endPoint, statusCode));
	}

	@Then("^save value of parameter from json \"([^\"]*)\"$")
	public void validate_response_message_for(String params) {
		System.out.println(reqResParams.getResponse().jsonPath()
				.getString(params));

		ReflectionUtils.setRequestField(reqResParams, params, reqResParams
				.getResponse().jsonPath().getString(params));
		reqResParams.setJwtAuth(reqResParams.getResponse().jsonPath()
				.getString(params));
	}

	@Then("^save value of parameter from json \"([^\"]*)\" to field \"([^\"]*)\"$")
	public void validate_response(String params, String fieldName) {
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

	@When("^user send get request to api \"([^\"]*)\" with authId as header and expects statusCode \"([^\"]*)\"$")
	public void user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode(
			String endPoint, String statusCode) throws Throwable {
		System.out.println("Sending request to : " + endPoint);
		reqResParams.setResponse(jsonUtil.getRequest(endPoint, statusCode,
				reqResParams.getJwtAuth()));
	}

	@When("^user send get request to api \"([^\"]*)\" with authId as header with updated request parameter \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode(
			String endPoint, String reqParam, String statusCode)
			throws Throwable {
		endPoint = endPoint + "/"
				+ ReflectionUtils.getRequestField(reqResParams, reqParam);
		System.out.println("Sending request to : " + endPoint);
		reqResParams.setResponse(jsonUtil.getRequest(endPoint, statusCode,
				reqResParams.getJwtAuth()));
	}

	@When("^user send request to invite new user using \"([^\"]*)\" with endpoint \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void user_send_inviteUser(String jsonFileName, String endPoint,
			String statusCode) throws Throwable {
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
				jsonFileName, "email", emailId);
		updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				updatedRequest, "name", userName);
		reqResParams.setUpdatedReq(updatedRequest);

		reqResParams.setResponse(jsonUtil.postRequestWithAuth(
				reqResParams.getUpdatedReq(), endPoint, statusCode,
				reqResParams.getJwtAuth()));
	}

	@When("^user send request to request code api using \"([^\"]*)\" with endpoint \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void user_send_a_post_request_toInviteUSer_and_expects_statusCode(
			String jsonFileName, String endPoint, String statusCode)
			throws Throwable {
		System.out.println("Sending request to : " + endPoint);

		String updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				jsonFileName, "email", reqResParams.getEmailId());
		reqResParams.setUpdatedReq(updatedRequest);
		reqResParams.setResponse(jsonUtil.postRequestWithAuth(
				reqResParams.getUpdatedReq(), endPoint, statusCode,
				reqResParams.getJwtAuth()));
		reqResParams.setVerificationCode(new MailClient()
				.checkMail(reqResParams.getEmailId()));
		System.out.println("Set verfication coed="
				+ reqResParams.getVerificationCode());
	}

	@When("^user send request to verify code api using \"([^\"]*)\" with endpoint \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void verifyCOde(String jsonFileName, String endPoint,
			String statusCode) throws Throwable {
		System.out.println("Sending request to : " + endPoint);

		String updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				jsonFileName, "email", reqResParams.getEmailId());

		updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				updatedRequest, "code", reqResParams.getVerificationCode());

		reqResParams.setUpdatedReq(updatedRequest);

		reqResParams.setResponse(jsonUtil.postRequestWithAuth(
				reqResParams.getUpdatedReq(), endPoint, statusCode,
				reqResParams.getJwtAuth()));

	}

	@When("^user send request to registerUser api using \"([^\"]*)\" with endpoint \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void registerUser(String jsonFileName, String endPoint,
			String statusCode) throws Throwable {
		System.out.println("Sending request to : " + endPoint);

		String updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				jsonFileName, "email", reqResParams.getEmailId());

		updatedRequest = jsonUtil.updateJasonFileWithUserInputList(
				updatedRequest, "name", reqResParams.getUserName());

		reqResParams.setUpdatedReq(updatedRequest);

		reqResParams.setResponse(jsonUtil.postRequestWithAuth(
				reqResParams.getUpdatedReq(), endPoint, statusCode,
				reqResParams.getJwtAuth()));

	}

	@When("^user send request to get user invite api \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void getUserInvite(String endPoint, String statusCode)
			throws Throwable {
		endPoint = endPoint.replace("{{user_id}}", reqResParams.getUserId());
		System.out.println("Sending request to : " + endPoint);

		reqResParams.setResponse(jsonUtil.getRequest(endPoint, statusCode,
				reqResParams.getJwtAuth()));

	}

	@When("^user send request to invitation api \"([^\"]*)\" and expects statusCode \"([^\"]*)\"$")
	public void sendInvitationApi(String endPoint, String statusCode)
			throws Throwable {
		endPoint = endPoint.replace("{{user_id}}", reqResParams.getUserId());
		endPoint = endPoint.replace("{{invitaion_id}}",
				reqResParams.getInvitationId());
		System.out.println("Sending request to : " + endPoint);

		reqResParams.setResponse(jsonUtil.postRequestWithAuth("", endPoint,
				statusCode, reqResParams.getJwtAuth()));

	}
}
