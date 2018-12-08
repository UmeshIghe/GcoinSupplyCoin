package apiRequests;

import org.codehaus.jackson.annotate.JsonProperty;

public class AdminAuth {
	public AdminAuth() {
		setDefaultValues();
	}

	public String username;
	public String password;

	@JsonProperty(value = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty(value = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private void setDefaultValues() {
		setUsername("madhur.agarwal@emergenttech.com");
		setPassword("Welcome@123");

	}
}
