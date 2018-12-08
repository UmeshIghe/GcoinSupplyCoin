package apiRequests;

import org.codehaus.jackson.annotate.JsonProperty;

public class RegisterUser {

	public RegisterUser() {
		setDefaultValues();
	}

	public String name;
	public String email;
	public String password;

	@JsonProperty(value = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty(value = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty(value = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private void setDefaultValues() {
		setName("{{userName}}");
		setEmail("{{email}}");
		setPassword("Welcome@123");

	}
}
