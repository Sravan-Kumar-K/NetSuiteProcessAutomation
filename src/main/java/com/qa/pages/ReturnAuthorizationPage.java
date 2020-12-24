package com.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.qa.util.TestBase;

public class ReturnAuthorizationPage extends TestBase{
	
	String referenceNumberText;
	
	@FindBy(xpath = "//input[@id='inpt_orderstatus2']")
	WebElement approvalStatus;
	
	@FindBy(xpath = "//div[@class='dropdownDiv']//div")
	List<WebElement> approvalStatusList;
	
	@FindBy(xpath = "//input[@id='btn_multibutton_submitter']")
	WebElement saveBtn;
	
	@FindBy(xpath = "//div[@class='descr']")
	WebElement confirmationMsg;
	
	@FindBy(xpath = "//div[@class='uir-record-id']")
	WebElement returnAuthNumber;
	
	@FindBy(xpath = "//div[@class='uir-record-name']")
	WebElement returnAuthVendor;
	
	@FindBy(xpath = "//div[@class='uir-record-status']")
	WebElement returnAuthStatus;
	
	@FindBy(xpath = "//input[@id='return']")
	WebElement returnBtn;
	
	@FindBy(xpath = "//input[@id='refund']")
	WebElement refundBtn;
	
	public ReturnAuthorizationPage() {
		PageFactory.initElements(driver, this);
	}
	
	public ItemShipmentPage clickOnReturnBtn() {
		returnBtn.click();
		return new ItemShipmentPage();
	}
	
	public void generateReturn() throws InterruptedException {
		
		// Change Approval status to Approved
		Thread.sleep(8000);
		approvalStatus.click();
		for(int i=0;i<approvalStatusList.size();i++) {
			String statusValue = approvalStatusList.get(i).getText().trim();
			if(statusValue.equals("Pending Return")) {
				approvalStatusList.get(i).click();
			}
		}
		saveBtn.click();
		
	}
	
	public BillCreditPage clickOnRefundBtn() {
		eleClickable(driver, By.xpath("//input[@id='refund']"), 20);
		refundBtn.click();
		return new BillCreditPage();
	}
	
	public void verifyReturnCreation(ExtentTest logInfo) {
		eleAvailability(driver, By.xpath("//div[@class='descr']"), 20);
	    String confirmationMessage = confirmationMsg.getText().trim();
	    String returnAuthNumberText = returnAuthNumber.getText().trim();
	    String returnAuthVendorText = returnAuthVendor.getText().trim();
	    String returnAuthStatusText = returnAuthStatus.getText().trim();
	    if(confirmationMessage.equals("Transaction successfully Saved") && returnAuthStatusText.equals("PENDING RETURN")) {
	    	System.out.println("Return Authorization '"+returnAuthNumberText+"' created successfully for '"+returnAuthVendorText+"' vendor  with '"+returnAuthStatusText+"' status");
	    	logInfo.pass("Return Authorization '"+returnAuthNumberText+"' created successfully for '"+returnAuthVendorText+"' vendor  with '"+returnAuthStatusText+"' status");
	    }else {
	    	logInfo.fail("Return Authorization not created");
	    }
	}
}
