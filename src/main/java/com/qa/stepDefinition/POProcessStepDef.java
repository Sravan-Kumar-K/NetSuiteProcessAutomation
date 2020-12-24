package com.qa.stepDefinition;

import java.util.Map;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.qa.pages.AuthenticationPage;
import com.qa.pages.BillCreditPage;
import com.qa.pages.BillPage;
import com.qa.pages.HomePage;
import com.qa.pages.ItemReceiptPage;
import com.qa.pages.ItemShipmentPage;
import com.qa.pages.LoginPage;
import com.qa.pages.POCreationPage;
import com.qa.pages.PaymentPage;
import com.qa.pages.ReturnAuthorizationPage;
import com.qa.util.ExcelDataToDataTable;
import com.qa.util.TestBase;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;

public class POProcessStepDef extends TestBase{
	LoginPage loginPage;
	AuthenticationPage authPage;
	HomePage homePage;
	POCreationPage poCreationPage;
	ItemReceiptPage irPage;
	BillPage billPage;
	PaymentPage paymentPage;
	ReturnAuthorizationPage returnPage;
	ItemShipmentPage isPage;
	BillCreditPage creditPage;
	cucumber.api.Scenario scenario;
	
	@Then("^Automate the PO process using excel data at \"([^\"]*)\"$")
	public void automate_the_PO_process_using_excel_data_at(@Transform(ExcelDataToDataTable.class) DataTable poData) throws Throwable {
		ExtentTest loginfo = null;
		loginfo = test.createNode(new GherkinKeyword("Then"), "Automation of Purchase Order process");
		for(Map<String,String> data: poData.asMaps(String.class, String.class)) {
			String vendor = data.get("Vendor");
			String location = data.get("Location");
			String item = data.get("Item");
			try {
				homePage = new HomePage();
				loginfo.pass("Automating purchase process for the following data set: Vendor = "+vendor+", Location = "+location+" and Item Name = "+item);
			    poCreationPage = homePage.clickOnNewPOLink();
			    poCreationPage.createNewPO(vendor, location, item);
			    poCreationPage.verifyPOCreation(loginfo);
			    
			    // PO approval & Item receipt generation
			    poCreationPage.approvePO();
				poCreationPage.verifyPOApproval(loginfo);
				irPage = poCreationPage.clickOnReceiveBtn();
				irPage.generateItemReceipt();
				irPage.verifyIrCreation(loginfo);
				
				// Vendor Bill generation
				irPage.clickOnPOLink();
			    billPage = poCreationPage.clickOnBillBtn();
			    billPage.generateVendorBill();
			    billPage.verifyVBCreation(loginfo);
				
				// Bill approval & Payment generation
				billPage.approveBill();
				billPage.verifyBillApproval(loginfo);
				paymentPage = billPage.clickOnPaymentBtn();
				paymentPage.generatePayment();
				paymentPage.verifyPaymentCreation(loginfo);
				
				// Vendor Return Authorization generation
				paymentPage.clickOnBillLink();
				billPage.clickOnPOLink();
				returnPage = poCreationPage.clickOnRetrunBtn();
				returnPage.generateReturn();
				returnPage.verifyReturnCreation(loginfo);
				
				// Item shipment generation
				isPage = returnPage.clickOnReturnBtn();
				isPage.generateItemShipment();
				isPage.verifyIsCreation(loginfo);
				
				// Bill credit generation
				creditPage = returnPage.clickOnRefundBtn();
				creditPage.generateBillCredit();
				creditPage.verifyBCCreation(vendor, location, item, loginfo);
			}catch (AssertionError | Exception e) {
				loginfo.fail("Purchase process automation failed for the following data set: Vendor = "+vendor+", Location = "+location+" and Item Name = "+item);
				testStepHandle("FAIL", driver, loginfo, e);
			}
		}
	}
}
