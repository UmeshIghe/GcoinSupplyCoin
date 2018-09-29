package stepDefinitions;

import utils.JsonReqResParams;
import utils.JsonUtil;
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
		reqResParams.setResponse(jsonUtil.postRequest(
				reqResParams.getUpdatedReq(), endPoint, statusCode));
	}

	@Then("^save value of parameter from json \"([^\"]*)\"$")
	public void validate_response_message_for(String params) {
		System.out.println(reqResParams.getResponse().jsonPath()
				.getString(params));
		reqResParams.setJwtAuth(reqResParams.getResponse().jsonPath()
				.getString(params));
	}

	@When("^user send get request to api \"([^\"]*)\" with authId as header and expects statusCode \"([^\"]*)\"$")
	public void user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode(
			String endPoint, String statusCode) throws Throwable {

		jsonUtil.getRequest(endPoint, statusCode, reqResParams.getJwtAuth());
	}
}
