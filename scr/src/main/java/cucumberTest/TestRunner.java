package cucumberTest;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "Feature", glue = { "stepDefinition" }, tags = { "@tagtest" }, format = {
		"pretty", "json:target/cucumber.json" })
public class TestRunner {

}