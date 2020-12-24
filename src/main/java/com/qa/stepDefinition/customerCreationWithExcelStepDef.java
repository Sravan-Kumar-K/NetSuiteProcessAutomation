package com.qa.stepDefinition;

import java.util.Map;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.qa.pages.CustomerCreationPage;
import com.qa.pages.HomePage;
import com.qa.util.ExcelDataToDataTable;
import com.qa.util.TestBase;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.en.Then;

public class customerCreationWithExcelStepDef extends TestBase{
	HomePage homePage;
	CustomerCreationPage customerCreationPage;
	@Then("^Create two customers with customer data from excel at \"([^\"]*)\"$")
	public void create_two_customers_with_customer_data_from_excel_at(@Transform(ExcelDataToDataTable.class) DataTable customerData) throws InterruptedException, ClassNotFoundException {
		ExtentTest loginfo = null;
		loginfo = test.createNode(new GherkinKeyword("Then"), "create_two_customers_with_customer_data_from_excel_at");
		for(Map<String,String> data: customerData.asMaps(String.class, String.class)) {
			String customerType = data.get("Type");
			String customerFirstName = data.get("First Name");
			String customerLastName = data.get("Last Name");
			String customerCompany = data.get("Company Name");
			String customerParentCompany = data.get("Parent Company");
			try {
				homePage = new HomePage();
				customerCreationPage = homePage.clickOnNewCustomerLink();
				customerCreationPage.createNewCustomer(customerType, customerFirstName, customerLastName, customerCompany, customerParentCompany);
				customerCreationPage.verifyCustomer(customerFirstName, customerLastName, customerCompany, loginfo);
			} catch (AssertionError | Exception e) {
				String customer = customerFirstName+" "+customerLastName+" "+customerCompany;
				System.out.println("Customer '"+customer.trim()+"' unable to create");
				loginfo.fail("Customer '"+customer.trim()+"' unable to create");
				testStepHandle("FAIL", driver, loginfo, e);
			}
		}
	}
}