package utils;

import com.jayway.restassured.response.Response;

public class JsonReqResParams {

	private Response response;
	private String updatedReq;
	private String jwtAuth;
	private String organizationid;
	private String emailId;
	private String verificationCode;
	private String userName;
	private String userId;
	private String invitationId;

	public String getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(String invitationId) {
		this.invitationId = invitationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getJwtAuth() {
		return jwtAuth;
	}

	public void setJwtAuth(String jwtAuth) {
		this.jwtAuth = jwtAuth;
	}

	public String getUpdatedReq() {
		return updatedReq;
	}

	public void setUpdatedReq(String updatedReq) {
		this.updatedReq = updatedReq;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
}
