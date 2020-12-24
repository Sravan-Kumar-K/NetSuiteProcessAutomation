package com.qa.stepDefinition;

import java.util.Map;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
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

public class POCreationStepDef extends TestBase{
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
	
	@Before
	public void login(cucumber.api.Scenario scenario) {
		this.scenario = scenario;
		ExtentTest loginfo = null;
		try {
			test = extent.createTest(Feature.class, scenario.getId().split(";")[0].replace("-"," "));
			test = test.createNode(Scenario.class, scenario.getName());
			loginfo = test.createNode(new GherkinKeyword("Given"), "login");
			
			TestBase.init();
			loginPage = new LoginPage();
			authPage = loginPage.login();
			homePage = authPage.Authentication();
			homePage.changeRole(prop.getProperty("role"));
			
			loginfo.pass("Login Successful in Netsuite");
		}catch (AssertionError | Exception e) {
			testStepHandle("FAIL", driver, loginfo, e);
		}
	}
	
	@After
	public void close_browser() {
		driver.close();
	}
	
	@Then("^Create a Purchase Order with excel data at \"([^\"]*)\"$")
	public void create_a_Purchase_Order_with_excel_data_at(@Transform(ExcelDataToDataTable.class) DataTable poData) throws InterruptedException {
		ExtentTest loginfo = null;
		try {
			loginfo = test.createNode(new GherkinKeyword("Given"), "create_a_Purchase_Order");
			for(Map<String,String> data: poData.asMaps(String.class, String.class)) {
				homePage = new HomePage();
				String vendor = data.get("Vendor");
				String location = data.get("Location");
				String item = data.get("Item");
			    poCreationPage = homePage.clickOnNewPOLink();
			    poCreationPage.createNewPO(vendor, location, item);
			    poCreationPage.verifyPOCreation(loginfo);
			}
		} catch (AssertionError | Exception e) {
			testStepHandle("FAIL", driver, loginfo, e);
		}
	}
	
	@Then("^Approve PO & create Item Receipt$")
	public void approve_PO_create_Item_Receipt() throws InterruptedException {
		ExtentTest loginfo = null;
		try {
			loginfo = test.createNode(new GherkinKeyword("Then"), "approve_PO_create_Item_Receipt");

			poCreationPage.approvePO();
			poCreationPage.verifyPOApproval(loginfo);
			irPage = poCreationPage.clickOnReceiveBtn();
			irPage.generateItemReceipt();
			irPage.verifyIrCreation(loginfo);
		}catch (AssertionError | Exception e) {
			testStepHandle("FAIL", driver, loginfo, e);
		}
	}
	
	@Then("^Generate Vendor Invoice$")
	public void generate_Vendor_Invoice() throws InterruptedException {
		ExtentTest loginfo = null;
		try {
			loginfo = test.createNode(new GherkinKeyword("Then"), "generate_Vendor_Invoice");
			irPage.clickOnPOLink();
		    billPage = poCreationPage.clickOnBillBtn();
		    billPage.generateVendorBill();
		    billPage.verifyVBCreation(loginfo);
		}catch (AssertionError | Exception e) {
			testStepHandle("FAIL", driver, loginfo, e);
		}
	}
	
	@Then("^Approve Bill & accept payment$")
	public void approve_Bill_accept_payment() throws InterruptedException {
		ExtentTest loginfo = null;
		try {
			loginfo = test.createNode(new GherkinKeyword("Then"), "approve_Bill_accept_payment");
			billPage.approveBill();
			billPage.verifyBillApproval(loginfo);
			paymentPage = billPage.clickOnPaymentBtn();
			paymentPage.generatePayment();
			paymentPage.verifyPaymentCreation(loginfo);
		}catch (AssertionError | Exception e) {
			testStepHandle("FAIL", driver, loginfo, e);
		}
	}
	
	@Then("^Generate Vendor Return Authorization$")
	public void generate_Vendor_Return_Authorization() throws InterruptedException {
		ExtentTest loginfo = null;
		try {
			loginfo = test.createNode(new GherkinKeyword("Then"), "generate_Vendor_Return_Authorization");
			paymentPage.clickOnBillLink();
			billPage.clickOnPOLink();
			returnPage = poCreationPage.clickOnRetrunBtn();
			returnPage.generateReturn();
			returnPage.verifyReturnCreation(loginfo);
		}catch (AssertionError | Exception e) {
			testStepHandle("FAIL", driver, loginfo, e);
		}
	}
	
	@Then("^Generate Item Shipment$")
	public void generate_Item_Shipment() throws InterruptedException {
		ExtentTest loginfo = null;
		try {
			loginfo = test.createNode(new GherkinKeyword("Then"), "generate_Item_Shipment");
			isPage = returnPage.clickOnReturnBtn();
			isPage.generateItemShipment();
			isPage.verifyIsCreation(loginfo);
		}catch (AssertionError | Exception e) {
			testStepHandle("FAIL", driver, loginfo, e);
		}
	}
	
	@Then("^Generate Bill Credit$")
	public void generate_Bill_Credit() throws InterruptedException {
		ExtentTest loginfo = null;
		try {
			loginfo = test.createNode(new GherkinKeyword("Then"), "generate_Bill_Credit");
			isPage.clickOnReturnAuthLink();
			creditPage = returnPage.clickOnRefundBtn();
			creditPage.generateBillCredit();
			creditPage.verifyBCCreation(loginfo);
		}catch (AssertionError | Exception e) {
			testStepHandle("FAIL", driver, loginfo, e);
		}
	}
	
}