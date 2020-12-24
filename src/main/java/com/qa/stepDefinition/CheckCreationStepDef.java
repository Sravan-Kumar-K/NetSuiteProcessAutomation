package com.qa.stepDefinition;

import java.util.Map;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.qa.pages.ChecksPage;
import com.qa.pages.HomePage;
import com.qa.util.ExcelDataToDataTable;
import com.qa.util.TestBase;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.en.Then;

public class CheckCreationStepDef extends TestBase{
	
	HomePage homePage;
	ChecksPage checksPage;
	
	@Then("^Create a check using excel data at \"([^\"]*)\"$")
	public void create_a_check_using_excel_data_at(@Transform(ExcelDataToDataTable.class) DataTable checksData) throws InterruptedException, ClassNotFoundException {
		ExtentTest loginfo = null;
		loginfo = test.createNode(new GherkinKeyword("Given"), "Creation of check");
		for(Map<String,String> data: checksData.asMaps(String.class, String.class)) {
			try {
				homePage = new HomePage();
				String payee = data.get("Payee");
				String expenseAccount = data.get("Expense account");
				String expenseAmount = data.get("Expense amount");
				String item = data.get("Item");
				String itemTaxCode = data.get("Item tax code");
				String itemLocation = data.get("Item location");
		        checksPage = homePage.clickOnWriteChecksLink();
		        checksPage.createNewCheck(payee, expenseAccount, expenseAmount, item, itemTaxCode, itemLocation);
		        checksPage.verifyCheckCreation(loginfo);
			} catch (AssertionError | Exception e) {
				loginfo.fail("Check not created");
				testStepHandle("FAIL", driver, loginfo, e);
			}
		}
    }
}