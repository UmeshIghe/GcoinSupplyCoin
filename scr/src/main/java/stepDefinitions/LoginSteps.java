package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class LoginSteps {

	@Given("^User want to login using \"([^\"]*)\" api$")
	public void user_want_to_login_using_api(String api) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		
	}

	@When("^user updates api body with \"([^\"]*)\" and \"([^\"]*)\"$")
	public void user_updates_api_body_with_and(String emailId, String password)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		
	}

	@When("^send api \"([^\"]*)\" reuqest and excepts status code \"([^\"]*)\"$")
	public void send_api_reuqest_and_excepts_status_code(String requestType,
			String statusCode) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		
	}

}
