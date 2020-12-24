package com.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.qa.util.TestBase;

public class PaymentPage extends TestBase{
	
	@FindBy(xpath = "//input[@id='inpt_approvalstatus5']")
	WebElement approvalStatus;
	
	@FindBy(xpath = "//div[@class='dropdownDiv']//div")
	List<WebElement> approvalStatusList;
	
	@FindBy(xpath = "//input[@id='btn_multibutton_submitter']")
	WebElement saveBtn;
	
	@FindBy(xpath = "//div[@class='descr']")
	WebElement confirmationMsg;
	
	@FindBy(xpath = "//div[@class='uir-record-id']")
	WebElement paymentNumber;
	
	@FindBy(xpath = "//div[@class='uir-record-name']")
	WebElement paymentVendor;
	
	@FindBy(xpath = "//div[@class='uir-record-status']")
	WebElement paymentStatus;
	
	@FindBy(xpath = "//a[@id='apply_displayval']")
	WebElement billLink;
	
	public PaymentPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void generatePayment() throws InterruptedException {
		
		// Change Approval status to Approved
		Thread.sleep(10000);
		approvalStatus.click();
		for(int i=0;i<approvalStatusList.size();i++) {
			String statusValue = approvalStatusList.get(i).getText().trim();
			if(statusValue.equals("Approved")) {
				approvalStatusList.get(i).click();
			}
		}
		saveBtn.click();
	}
	
	public void verifyPaymentCreation(ExtentTest logInfo) {
		
		eleAvailability(driver, By.xpath("//div[@class='descr']"), 20);
	    String confirmationMessage = confirmationMsg.getText().trim();
	    String paymentNumberText = paymentNumber.getText().trim();
	    String paymentVendorText = paymentVendor.getText().trim();
	    String paymentStatusText = paymentStatus.getText().trim();
	    if(confirmationMessage.equals("Transaction successfully Saved")) {
	    	System.out.println("Payment record '"+paymentNumberText+"' created successfully for '"+paymentVendorText+"' vendor  with '"+paymentStatusText+"' status");
	    	logInfo.pass("Payment record '"+paymentNumberText+"' created successfully for '"+paymentVendorText+"' vendor  with '"+paymentStatusText+"' status");
	    }else {
	    	logInfo.fail("Payment not created");
	    }
	}
	
	public void clickOnBillLink() {
		billLink.click();
	}
	
}
