package apiRequests;

import org.codehaus.jackson.annotate.JsonProperty;

public class RequestUser {

	public RequestUser() {
		setDefaultValues();
	}

	public String send;
	public String connection;
	public String email;

	@JsonProperty(value = "send")
	public String getSend() {
		return send;
	}

	public void setSend(String send) {
		this.send = send;
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
		setSend("code");
		setConnection("email");
		setEmail("{{email}}");
	}
}
