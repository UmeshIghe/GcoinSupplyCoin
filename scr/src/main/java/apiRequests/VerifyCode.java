package apiRequests;

import org.codehaus.jackson.annotate.JsonProperty;

public class VerifyCode {

	public VerifyCode() {
		setDefaultValues();
	}

	public String code;
	public String connection;
	public String email;

	@JsonProperty(value = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@JsonProperty(value = "connection")
	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	@JsonProperty(value = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private void setDefaultValues() {
		setCode("804478");
		setConnection("email");
		setEmail("{{email}}");

	}
}
