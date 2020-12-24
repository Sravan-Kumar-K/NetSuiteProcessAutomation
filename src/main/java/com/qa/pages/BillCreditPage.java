package com.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.qa.util.TestBase;

public class BillCreditPage extends TestBase{
	
	@FindBy(xpath = "//input[@id='inpt_customform6']")
	WebElement bcform;
	
	@FindBy(xpath = "//div[@class='dropdownDiv']//div")
	List<WebElement> selectForm;
	
	@FindBy(xpath = "//input[@id='btn_multibutton_submitter']")
	WebElement saveBtn;
	
	@FindBy(xpath = "//div[@class='descr']")
	WebElement confirmationMsg;
	
	public BillCreditPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void generateBillCredit() throws InterruptedException {
		// Change the Form to "Standard Vendor Credit"
		Thread.sleep(10000);
		eleClickable(driver, By.xpath("//input[@id='inpt_customform6']"), 10);
		bcform.click();
		for(int i=0;i<selectForm.size();i++) {
			String formValue = selectForm.get(i).getText().trim();
			if(formValue.equals("Standard Vendor Credit")) {
				selectForm.get(i).click();
			}
		}
		Thread.sleep(10000);
		
		// Save the Vendor Credit
		saveBtn.click();
	}
	
	public void verifyBCCreation(ExtentTest logInfo) {
		eleAvailability(driver, By.xpath("//div[@class='descr']"), 20);
		String confirmationMessage = confirmationMsg.getText().trim();
	    if(confirmationMessage.equals("Transaction successfully Saved")) {
	    	System.out.println("Bill Credit created successfully");
	    	logInfo.pass("Bill Credit created successfully");
	    }else {
	    	logInfo.fail("Bill credit not created");
	    }
	}
	
	public void verifyBCCreation(String vendor, String location, String item, ExtentTest logInfo) {
		eleAvailability(driver, By.xpath("//div[@class='descr']"), 20);
		String confirmationMessage = confirmationMsg.getText().trim();
	    if(confirmationMessage.equals("Transaction successfully Saved")) {
	    	System.out.println("Bill Credit created successfully");
	    	logInfo.pass("Bill Credit created successfully");
	    	logInfo.pass("Purchase process automation completed for the following data set: Vendor = "+vendor+", Location = "+location+" and Item Name = "+item);
	    }else {
	    	logInfo.fail("Bill credit not created");
	    }
	}
	
}
