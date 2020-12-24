package com.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.qa.util.TestBase;

public class BillPage extends TestBase{
	
	@FindBy(xpath = "//input[@id='inpt_customform1']")
	WebElement vbForm;
	
	@FindBy(xpath = "//div[@class='dropdownDiv']//div")
	List<WebElement> selectForm;
	
	@FindBy(xpath = "//input[@id='btn_multibutton_submitter']")
	WebElement saveBtn;
	
	@FindBy(xpath = "//div[@class='descr']")
	WebElement confirmationMsg;
	
	@FindBy(xpath = "//input[@id='edit']")
	WebElement editBtn;
	
	@FindBy(xpath = "//input[@id='inpt_approvalstatus4']")
	WebElement approvalStatus;
	
	@FindBy(xpath = "//div[@class='dropdownDiv']//div")
	List<WebElement> approvalStatusList;
	
	@FindBy(xpath = "//div[@class='uir-record-status']")
	WebElement billStatus;
	
	@FindBy(xpath = "//input[@id='payment']")
	WebElement paymentBtn;
	
	@FindBy(xpath = "//input[@id='return']")
	WebElement returnBtn;
	
	@FindBy(xpath = "//a[@id='rlrcdstabtxt']")
	WebElement relatedRecordsTab;
	
	@FindBy(xpath = "//a[@id='purchaseorderstxt']")
	WebElement poSubTab;
	
	@FindBy(xpath = "//a[@id='purchaseorders_displayval']")
	WebElement poLink;
	
	public BillPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void approveBill() throws InterruptedException {
		editBtn.click();
		Thread.sleep(10000);
		eleClickable(driver, By.xpath("//input[@id='inpt_approvalstatus4']"), 10);
		approvalStatus.click();
		for(int i=0;i<approvalStatusList.size();i++) {
			String statusValue = approvalStatusList.get(i).getText().trim();
			if(statusValue.equals("Approved")) {
				approvalStatusList.get(i).click();
			}
		}
		
		saveBtn.click();
	}
	
	public void verifyBillApproval(ExtentTest logInfo) {
		eleAvailability(driver, By.xpath("//div[@class='descr']"), 20);
	    String confirmationMessage = confirmationMsg.getText().trim();
	    String billStatusText = billStatus.getText().trim();
	    if(confirmationMessage.equals("Transaction successfully Saved") && billStatusText.equals("OPEN")) { 
	    	System.out.println("Invoice approved successfully with '"+billStatusText+"' status");
	    	logInfo.pass("Invoice approved successfully with '"+billStatusText+"' status");
	    }else {
	    	logInfo.fail("Invoice is not approved");
	    }
	}
	
	public PaymentPage clickOnPaymentBtn() {
		paymentBtn.click();
		return new PaymentPage();
	}
	
	public void generateVendorBill() throws InterruptedException {
		
		// Change the Form to "Standard Vendor Bill"
		Thread.sleep(10000);
		eleClickable(driver, By.xpath("//input[@id='inpt_customform1']"), 10);
		vbForm.click();
		for(int i=0;i<selectForm.size();i++) {
			String formValue = selectForm.get(i).getText().trim();
			if(formValue.equals("Standard Vendor Bill")) {
				selectForm.get(i).click();
			}
		}
		Thread.sleep(10000);
		
		// Save the Vendor Bill
		saveBtn.click();
	}
	
	public void verifyVBCreation(ExtentTest logInfo) {
		eleAvailability(driver, By.xpath("//div[@class='descr']"), 20);
		String confirmationMessage = confirmationMsg.getText().trim();
	    if(confirmationMessage.equals("Transaction successfully Saved")) {
	    	System.out.println("Vendor Bill created successfully");
	    	logInfo.pass("Vendor Bill created successfully");
	    }else {
	    	logInfo.fail("Vendor Bill not created");
	    }
	}
	
	public void clickOnPOLink() throws InterruptedException {
		Thread.sleep(10000);
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		relatedRecordsTab.click();
		poSubTab.click();
		eleClickable(driver, By.xpath("//a[@id='purchaseorders_displayval']"), 5);
		poLink.click();
	}
	
}
