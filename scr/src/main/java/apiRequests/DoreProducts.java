package com.scr.responsiblegold.api.requests;

import java.util.HashMap;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DoreProducts {
	private String company_prefix;
	private String display_name;
	private String item_reference;
	private String template_name;

	@JsonProperty("shared_properties")
	private Sharedproperties sharedProps;
	@JsonProperty("asset_schema")
	private AssertSchema assertSchema;

	@Data
	public class AssertSchema {
		private String type;
		@JsonProperty("properties")
		private Properties props;

		public AssertSchema() {
			setProps(new Properties());
			setType("object");
		}

	}

	@Data
	public class Properties {

		@JsonProperty("Weight (kg)")
		private Map<String, String> Weight;

		@JsonProperty("Assay - Au (%)")
		private Map<String, String> AssayAu;

		@JsonProperty("Assay - Ag (%)")
		private Map<String, String> AssayAg;

		@JsonProperty("Assay - Pt (%)")
		private Map<String, String> AssayPt;

		@JsonProperty("Assay - Pd (%)")
		private Map<String, String> AssayPd;

		@JsonProperty("Assay - Other impurities (%)")
		private Map<String, String> AssayOtherImpurities;

		public Properties() {
			setDefaultValues();
		}

		private void setDefaultValues() {
			Map<String, String> data = new HashMap<>();
			data.put("type", "number");
			setWeight(data);
			setAssayAg(data);
			setAssayAu(data);
			setAssayOtherImpurities(data);
			setAssayPd(data);
			setAssayPt(data);

		}

	}

	@Data
	public class Sharedproperties {
		@JsonProperty("Product Name")
		private String Product_Name;
		@JsonProperty("Item Reference Number")
		private String Item_Reference_Number;
		@JsonProperty("Product Description")
		private String Product_Description;
		@JsonProperty("External Identifier 1")
		private String External_Identifier_1;
		@JsonProperty("External Identifier 2")
		private String External_Identifier_2;

		public Sharedproperties() {
			setDefaultValues();
		}

		private void setDefaultValues() {
			setProduct_Name("DoreProjectName");
			setItem_Reference_Number("03112");
			setProduct_Description("Dore Bar Sample");
			setExternal_Identifier_1("1");
			setExternal_Identifier_2("2");

		}
	}

	public DoreProducts() {
		setDefaultValues();
	}

	private void setDefaultValues() {
		setAssertSchema(new AssertSchema());
		setCompany_prefix("{{company_prefix}}");
		setDisplay_name("Dore031143");
		setItem_reference("03112");
		setTemplate_name("Doré");

		setSharedProps(new Sharedproperties());

	}

}
