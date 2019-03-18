package com.scr.responsiblegold.flows;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scr.responsiblegold.api.requests.ExpectedHeaders;
import com.scr.responsiblegold.api.requests.ReportAuth;
import com.scr.responsiblegold.step.definitions.CommomStepsNew;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportFlowITCase {

	private static final Gson gson = new GsonBuilder().create();

	ReportAuth username = new ReportAuth();
	String email = username.getUsername();
	public static final String CSVHEADERS = "Organization 1Name,Company Prefix, Product Type,Serial Number,SGTIN,Weight,Au%,Ag%,Impurity%, pbk.rsa, Creation Date, Acceptance Date,Registration Date,Transfer out Date, Re-chip Timestamp,Re-chip Message,Re-chip User,Reject Timestamp,Reject Message,Reject User";

	@Test(dataProvider = "typeOfTemplate", priority = 1)
	public void testReportGeneration(String apiClassName, String templateType) {
		System.out.println("GeneRateReportTestCase: testReportGeneration() START");
		CommomStepsNew.user_wants_to_send_request_using_withOut_updated_fields_and(gson.toJson(new ReportAuth()));
		CommomStepsNew.user_send_a_post_request_to_and_expects_statusCode("oauth/token", "201");
		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
		CommomStepsNew.save_value_of_parameter_from_json("organization_ids[0]", "organizationid");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode(
				"organization", "organizationid", "200");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_orgId_and_expects_statusCode(
				"organization", "organizationid", "report", "200");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_orgId_and_exports_csv("organization",
				"organizationid", "report", "export=csv", "200");
		CommomStepsNew.verifyHeaders(CSVHEADERS);

		// used parameterization here , fileName and templateType is passed using data
		// provider

		CommomStepsNew.user_create_report(apiClassName, "organization", "organizationid", "report", "201",
				templateType);

		CommomStepsNew.save_value_of_parameter_from_json("report_id", "reportId");

		CommomStepsNew.user_send_get_request_to_api_with_reportType_and_expects_statusCode("organization",
				"organizationid", "report", "type", templateType, "200");

		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_reportId_and_expects_statusCode(
				"organization", "organizationid", "report?report_id", "reportId", "200");

		CommomStepsNew.user_send_delete_request_to_api_with_authId_as_header_with_reportId_and_expects_statusCode(
				"organization", "organizationid", "report", "reportId", "200");

		System.out.println("GeneRateReportTestCase: testReportGeneration() END");
	}

//	@Test(dataProvider = "typeOfTemplate", priority = 1 )
	public void testGetReportAssertions(String apiClassName, String templateType) {
		System.out.println("GeneRateReportTestCase: testReportGeneration() START");
		CommomStepsNew.user_wants_to_send_request_using_withOut_updated_fields_and(gson.toJson(new ReportAuth()));
		CommomStepsNew.user_send_a_post_request_to_and_expects_statusCode("oauth/token", "201");
		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
		CommomStepsNew.save_value_of_parameter_from_json("organization_ids[0]", "organizationid");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode(
				"organization", "organizationid", "200");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_orgId_and_expects_statusCode(
				"organization", "organizationid", "report", "200");

		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[0]name");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("[0]template", "production");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[0]description");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("[0]type", "standard");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[0]metaData");

		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[1]name");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("[1]template", "inventory");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[1]description");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("[1]type", "standard");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[1]metaData");

		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[2]name");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("[2]template", "rechip");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[2]description");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("[2]type", "standard");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[2]metaData");

		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[3]name");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("[3]template", "rejection");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[3]description");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("[3]type", "standard");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[3]metaData");

		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[4]name");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("[4]template", "received");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[4]description");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("[4]type", "standard");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[4]metaData");

		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[5]name");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("[5]template", "transferout");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[5]description");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("[5]type", "standard");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[5]metaData");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[6]name");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[6]template");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[6]description");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[6]createdDate");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("[6]type", "custom");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[6]metaData");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[6]report_id");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("[6]created_by");

		System.out.println("GeneRateReportTestCase: testGetReportAssertions() END");
	}

//    @Test(dataProvider = "typeOfTemplate", priority = 1  )
	public void testgetReport403AssertionGetReport(String apiClassName, String templateType) {
		System.out.println("GeneRateReportTestCase: testReportGeneration() START");
		CommomStepsNew.user_wants_to_send_request_using_withOut_updated_fields_and(gson.toJson(new ReportAuth()));
		CommomStepsNew.user_send_a_post_request_to_and_expects_statusCode("oauth/token", "201");
		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_orgId_and_expects_statusCode(
				"organization", "", "report", "403");

		System.out.println("GeneRateReportTestCase: testgetReport403AssertionGetReport() END");
	}

//	@Test(dataProvider = "typeOfTemplate", priority = 1 )
	public void testgetReport403AssertionReportCsv(String apiClassName, String templateType) {
		System.out.println("GeneRateReportTestCase: testReportGeneration() START");
		CommomStepsNew.user_wants_to_send_request_using_withOut_updated_fields_and(gson.toJson(new ReportAuth()));
		CommomStepsNew.user_send_a_post_request_to_and_expects_statusCode("oauth/token", "201");
		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
		CommomStepsNew.save_value_of_parameter_from_json("organization_ids[0]", "organizationid");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode(
				"organization", "organizationid", "200");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_orgId_and_exports_csv("organization", "",
				"report", "export=csv", "403");

		System.out.println("GeneRateReportTestCase: testgetReport403AssertionReportCsv() END");
	}

//	@Test(dataProvider = "typeOfTemplate", priority = 1 )
	public void testgetReportAssertionReportCsv(String apiClassName, String templateType) {
		System.out.println("GeneRateReportTestCase: testReportGeneration() START");
		CommomStepsNew.user_wants_to_send_request_using_withOut_updated_fields_and(gson.toJson(new ReportAuth()));
		CommomStepsNew.user_send_a_post_request_to_and_expects_statusCode("oauth/token", "201");
		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
		CommomStepsNew.save_value_of_parameter_from_json("organization_ids[0]", "organizationid");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode(
				"organization", "organizationid", "200");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_orgId_and_exports_csv("organization",
				"organizationid", "report", "export=csv", "200");

		System.out.println("GeneRateReportTestCase: testgetReportAssertionReportCsv() END");
	}

//	@Test(dataProvider = "typeOfTemplate", priority = 1)
	public void testReportGenerationbbyId400WrongId(String apiClassName, String templateType) {
		System.out.println("GeneRateReportTestCase: testReportGeneration() START");
		CommomStepsNew.user_wants_to_send_request_using_withOut_updated_fields_and(gson.toJson(new ReportAuth()));
		CommomStepsNew.user_send_a_post_request_to_and_expects_statusCode("oauth/token", "201");
		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
		CommomStepsNew.save_value_of_parameter_from_json("organization_ids[0]", "organizationid");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode(
				"organization", "organizationid", "200");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_reportId_and_expects_statusCode(
				"organization", "organizationid", "report?report_id", "", "400");
	}

//	@Test(dataProvider = "typeOfTemplate", priority = 1)
	public void AssertionForGetCustomReportByID(String apiClassName, String templateType) {
		System.out.println("GeneRateReportTestCase: testReportGeneration() START");
		CommomStepsNew.user_wants_to_send_request_using_withOut_updated_fields_and(gson.toJson(new ReportAuth()));
		CommomStepsNew.user_send_a_post_request_to_and_expects_statusCode("oauth/token", "201");
		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
		CommomStepsNew.save_value_of_parameter_from_json("organization_ids[0]", "organizationid");

		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode(
				"organization", "organizationid", "200");
		// used parameterization here , fileName and templateType is passed using data
		// provider

		CommomStepsNew.user_create_report(apiClassName, "organization", "organizationid", "report", "201",
				templateType);

		CommomStepsNew.save_value_of_parameter_from_json("report_id", "reportId");

		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_reportId_and_expects_statusCode(
				"organization", "organizationid", "report?report_id", "reportId", "200");

		CommomStepsNew.assert_not_null_value_of_parameter_from_json("name");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("template", templateType);
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("description");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("type", "custom");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("metaData");

		CommomStepsNew.assert_not_null_value_of_parameter_from_json("modifiedDate");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("report_id");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("organization_id");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("created_by", email);

		CommomStepsNew.assert_not_null_value_of_parameter_from_json("filters");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("gtin");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("weight");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("au");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("ag");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("impurity");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("pbk");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("organization_name");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("company_prefix");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("product_type");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("serial_number");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("created_date");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("acceptance_date");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("registration_date");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("transfer_out_date");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("rechip_timestamp");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("rechip_message");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("rechip_user");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("reject_timestamp");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("reject_message");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("reject_user");

		CommomStepsNew.user_send_delete_request_to_api_with_authId_as_header_with_reportId_and_expects_statusCode(
				"organization", "organizationid", "report", "reportId", "200");

		System.out.println("GeneRateReportTestCase: testReportGenerationbbyId() END");
	}

//	@Test(dataProvider = "typeOfTemplate", priority = 1)
	public void AssertionForCreateReport(String apiClassName, String templateType) {
		System.out.println("GeneRateReportTestCase: testReportGeneration() START");
		CommomStepsNew.user_wants_to_send_request_using_withOut_updated_fields_and(gson.toJson(new ReportAuth()));
		CommomStepsNew.user_send_a_post_request_to_and_expects_statusCode("oauth/token", "201");
		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
		CommomStepsNew.save_value_of_parameter_from_json("organization_ids[0]", "organizationid");

		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_with_parameter_and_expects_statusCode(
				"organization", "organizationid", "200");
		// used parameterization here , fileName and templateType is passed using data
		// provider

		CommomStepsNew.user_create_report(apiClassName, "organization", "organizationid", "report", "201",
				templateType);
		CommomStepsNew.save_value_of_parameter_from_json("report_id", "reportId");

		CommomStepsNew.assert_not_null_value_of_parameter_from_json("name");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("template", templateType);
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("description");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("modifiedDate");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("report_id");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("organization_id");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("created_by", email);

		CommomStepsNew.user_send_delete_request_to_api_with_authId_as_header_with_reportId_and_expects_statusCode(
				"organization", "organizationid", "report", "reportId", "200");

		System.out.println("GeneRateReportTestCase: testReportGenerationbbyId() END");
	}

//	@Test(dataProvider = "typeOfTemplate", priority = 1)
	public void AssertionForDeleteReport(String apiClassName, String templateType) {
		System.out.println("GeneRateReportTestCase: testReportGeneration() START");
		CommomStepsNew.user_wants_to_send_request_using_withOut_updated_fields_and(gson.toJson(new ReportAuth()));
		CommomStepsNew.user_send_a_post_request_to_and_expects_statusCode("oauth/token", "201");
		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
		CommomStepsNew.save_value_of_parameter_from_json("organization_ids[0]", "organizationid");

		CommomStepsNew.user_send_delete_request_to_api_with_authId_as_header_with_reportId_and_expects_statusCode(
				"organization", "", "report", "reportId", "403");

		System.out.println("GeneRateReportTestCase: AssertionForDeleteReport() END");
	}

//   	@Test(dataProvider = "typeOfTemplate", priority = 1)
	public void AssertionForReportGenerationType(String apiClassName, String templateType) {
		System.out.println("GeneRateReportTestCase: testReportGeneration() START");
		CommomStepsNew.user_wants_to_send_request_using_withOut_updated_fields_and(gson.toJson(new ReportAuth()));
		CommomStepsNew.user_send_a_post_request_to_and_expects_statusCode("oauth/token", "201");
		CommomStepsNew.save_value_of_parameter_from_json("id_token", "jwtAuth");
		CommomStepsNew.user_send_get_request_to_api_with_authId_as_header_and_expects_statusCode("user", "200");
		CommomStepsNew.save_value_of_parameter_from_json("organization_ids[0]", "organizationid");

		CommomStepsNew.user_send_get_request_to_api_with_reportType_and_expects_statusCode("organization",
				"organizationid", "report", "type", templateType, "200");

		CommomStepsNew.assert_not_null_value_of_parameter_from_json("name");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("template", templateType);
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("description");
		CommomStepsNew.assert_compare_value_of_parameter_from_json("type", "standard");
		CommomStepsNew.assert_not_null_value_of_parameter_from_json("metaData");

		System.out.println("GeneRateReportTestCase: AssertionForReportGenerationType() END");
		System.out.println();
	}

	@DataProvider(name = "typeOfTemplate")
	public static Object[][] role() {
		return new Object[][] { { "GenerateProductionReport", "production" }, { "GenerateRechipReport", "rechip" },
				{ "GenerateInventoryReport", "inventory" }, { "GenerateRejectionReport", "rejection" },
				{ "GenerateTransferOutReport", "transferout" }, { "GenerateAssetReceivedReport", "received" } };
	}

}
