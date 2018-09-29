package utils;

import com.jayway.restassured.response.Response;

public class JsonReqResParams {

	private Response response;
	private String updatedReq;
	private static String jwtAuth;

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
