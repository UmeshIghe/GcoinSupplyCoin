package utils;

import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.type.TypeReference;
//import org.jbehave.core.model.ExamplesTable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import common.Logger;

@Component
public class JsonUtil {
	// ObjectMapper objectMapper = new
	// ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
	// false);
	ObjectMapper objectMapper = new ObjectMapper().configure(
			Feature.DEFAULT_VIEW_INCLUSION, false);
	final Logger logger = Logger.getLogger(JsonUtil.class);

	String baseUrl = "https://scr.dev.gmint.io/api/2.0/";

	String contentType = "application/json";

	int DEFAULT_TIMEOUT = 30000;

	// public List<Map<String, Object>> parseToListMap(String jsonFilePath) {
	// ArrayList<Map<String, Object>> reqMapObject = new ArrayList<Map<String,
	// Object>>();
	//
	// ObjectMapper mapper = new ObjectMapper();
	// File jsonFile = new File(jsonFilePath);
	// try {
	// reqMapObject = mapper.readValue(jsonFile, new
	// TypeReference<ArrayList<Map<String, Object>>>() {
	// });
	// } catch (Exception e) {
	// logger.error(e.toString());
	// }
	// return reqMapObject;
	//
	// }

	public Map<String, Object> parseToMap(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> reqMapObject = new HashMap<String, Object>();

		try {
			reqMapObject = mapper.readValue(jsonString,
					new TypeReference<Map<String, Object>>() {
					});
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return reqMapObject;

	}

	public void parseResponse(String response) {
		JSONParser parser = new JSONParser();
		Object obj = null;
		JSONObject jsonObject;

		try {
			obj = parser.parse(response);
		} catch (ParseException e) {

			e.printStackTrace();
		}

		jsonObject = (JSONObject) obj;
		String[] arr = (String[]) jsonObject.get("errors");
	}

	// public List<Map<String, Object>> parseResponseToListMap(String
	// responseContent) {
	// ArrayList<Map<String, Object>> reqMapObject = new ArrayList<Map<String,
	// Object>>();
	// ObjectMapper mapper = new ObjectMapper();
	//
	// try {
	// reqMapObject = mapper.readValue(responseContent, new
	// TypeReference<ArrayList<Map<String, Object>>>() {
	// });
	// } catch (Exception e) {
	// logger.error(e.toString());
	// }
	// return reqMapObject;
	//
	// }

	public Response postRequest(String object, String api, String expStatusCode) {

		RestAssured.baseURI = baseUrl + api;

		Response response = null;
		try {
			// String request = objectMapper.writeValueAsString(object);
			logger.info("Request String:" + object);

			response = given().contentType("application/json").body(object)
					.when().post();

		} catch (Exception e) {

			e.printStackTrace();
		}
		logger.info("Response String:" + response.asString());
		parseToMap(response.asString());
		SoftAssert softAssert = new SoftAssert();
		softAssert.andThat(expStatusCode, response.getStatusCode(),
				object.toString(), response.asString());
		softAssert.assertAll();
		return response;
	}

	public Response getRequest(String api, String expStatusCode,
			String authToken) {

		RestAssured.baseURI = baseUrl + api;

		Response response = null;
		try {
			// String request = objectMapper.writeValueAsString(object);
			// logger.info("Request String:" + object);

			// response = given().contentType("application/json").body(object)
			// .when().post();

			response = given().contentType("application/json")
					.header("Authorization", authToken).when().get();

		} catch (Exception e) {

			e.printStackTrace();
		}
		logger.info("Response String:" + response.asString());
		parseToMap(response.asString());
		SoftAssert softAssert = new SoftAssert();
		softAssert.andThat(expStatusCode, response.getStatusCode(), "",
				response.asString());
		softAssert.assertAll();
		return response;
	}

	public String extractValue(String response, String param) {
		DocumentContext docCtx;
		String value = "";
		try {
			docCtx = JsonPath.parse(response);
			JsonPath jsonPath = JsonPath.compile("$." + param);
			value = docCtx.read(jsonPath).toString();
			// logger.info(value);
		} catch (Exception e) {
			value = e.getMessage();
			// e.printStackTrace();
		}
		return value;

	}

	// public List<Map<String, Object>> updateJsonWithAttributes(
	// ExamplesTable attributeTable) {
	// ArrayList<Map<String, Object>> reqMapObject = new ArrayList<Map<String,
	// Object>>();
	// for (int row = 0; row < attributeTable.getRows().size(); row++) {
	// for (int attr = 0; attr < attributeTable.getHeaders().size(); attr++) {
	// String attrKey = attributeTable.getHeaders().get(attr);
	// String attrValue = attributeTable.getRow(row).get(
	// attributeTable.getHeaders().get(attr));
	//
	// reqMapObject.get(row).put(attrKey, attrValue);
	// }
	// }
	// return reqMapObject;
	// }

	public String updateJasonFileWithUserInputList(String jsonFilepath,
			String fieldName, String fieldValue) {
		if (!jsonFilepath.contains(".json")) {

			return updateJsonFileWithJsonPath(jsonFilepath, fieldName,
					fieldValue);
			// return updateJasonFileWithAttributes(jsonFilepath,
			// attributeTable);
		} else {
			return updateJsonFileWithJsonPath(getJsonFile(jsonFilepath),
					fieldName, fieldValue);
		}
	}

	public String updateJsonFileWithJsonPath(String filepath, String fieldName,
			String fieldValue) {
		Object obj = null;
		String updateKey = fieldName;
		String updateValue = fieldValue;
		String wildCard = "$.";
		JSONParser parser = new JSONParser();
		JsonPath jsonPath = null;
		DocumentContext docCtx = null;

		try {
			if (!filepath.contains(".json")) {
				obj = parser.parse(filepath);

			} else {
				obj = parser.parse(new FileReader(filepath));
			}

			docCtx = JsonPath.parse(obj);
			if ("REMOVE".equalsIgnoreCase(updateValue)) {
				docCtx.delete(wildCard.concat(updateKey));
			} else {
				logger.info("Field to Update: ".concat(updateKey).concat("=")
						.concat(updateValue));
				docCtx.set(wildCard.concat(updateKey), updateValue);
				jsonPath = JsonPath.compile(wildCard.concat(updateKey));
				String updatedField = docCtx.read(jsonPath).toString();
				logger.info("Field Updated :".concat(updateKey).concat("=")
						.concat(updatedField));
			}

			jsonPath = JsonPath.compile("$");

		} catch (Exception e) {

			logger.error(updateKey.concat(" field not present in Json: "
					.concat(filepath)));
			assertNotNull("Json is incorrect as it doesnt have :" + updateKey
					+ " field to update, please correct jsonFile", null);

		}
		return docCtx.read(jsonPath).toString();
	}

	public String getJsonFile(String fileName) {
		File root = new File(System.getProperty("user.dir"));

		try {
			boolean recursive = true;

			Collection files = FileUtils.listFiles(root, null, recursive);

			for (Iterator iterator = files.iterator(); iterator.hasNext();) {
				File file = (File) iterator.next();
				if (file.getName().equals(fileName))
					return file.getAbsolutePath();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}