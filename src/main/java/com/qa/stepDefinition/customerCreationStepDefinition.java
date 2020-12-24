package com.qa.stepDefinition;

import java.util.Map;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.qa.pages.AuthenticationPage;
import com.qa.pages.CustomerCreationPage;
import com.qa.pages.HomePage;
import com.qa.pages.LoginPage;
import com.qa.util.TestBase;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class customerCreationStepDefinition extends TestBase{
	
	LoginPage loginPage;
	AuthenticationPage authPage;
	HomePage homePage;
	CustomerCreationPage customerCreationPage;
	cucumber.api.Scenario scenario;
	
//	@Given("^User login to Micron Optics role in Netsuite$")
//	public void user_login_to_Micron_Optics_role_with(cucumber.api.Scenario scenario) {
//		this.scenario = scenario;
//		ExtentTest loginfo = null;
//		try {
//			test = extent.createTest(Feature.class, "Creation of Purchase Order in Netsuite");
//			test = test.createNode(Scenario.class, scenario.getName());
//			loginfo = test.createNode(new GherkinKeyword("Given"), "login");
//			
//			TestBase.init();
//			loginPage = new LoginPage();
//			authPage = loginPage.login();
//			homePage = authPage.Authentication();
//			homePage.changeRole(prop.getProperty("role"));
//			
//			loginfo.pass("Login Successful in Netsuite");
//		}catch (AssertionError | Exception e) {
//			testStepHandle("FAIL", driver, loginfo, e);
//		}
//	}

	@Then("^Create two customers with the below data & save the customer$")
	public void create_two_customers_with_the_below_data_save_the_customer(DataTable customerData) throws InterruptedException {
		ExtentTest loginfo = null;
		for(Map<String,String> data: customerData.asMaps(String.class, String.class)) {
			customerCreationPage = homePage.clickOnNewCustomerLink();
			String customerType = data.get("Type");
			String customerFirstName = data.get("First name");
			String customerLastName = data.get("Last name");
			String customerCompany = data.get("Company name");
			String customerParentCompany = data.get("Parent Company");
			customerCreationPage.createNewCustomer(customerType, customerFirstName, customerLastName, customerCompany, customerParentCompany);
			customerCreationPage.verifyCustomer(customerFirstName, customerLastName, customerCompany, loginfo);
		}
	}

//	@Then("^close browser$")
//	public void logout_from_the_account_close_the_browser() {
//		driver.close();
//	}
	
}
