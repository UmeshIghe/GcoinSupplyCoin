package apiRequests;

import org.codehaus.jackson.annotate.JsonProperty;

public class InviteUser {

	public InviteUser() {
		setDefaultValues();
	}

	public String name;
	public String email;
	public String role;

	@JsonProperty(value = "role")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

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

	private void setDefaultValues() {
		setName("{{userName}}");
		setEmail("{{email}}");
		setRole("user");

	}
}
