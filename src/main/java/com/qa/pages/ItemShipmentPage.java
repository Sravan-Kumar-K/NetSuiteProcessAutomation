package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.qa.util.TestBase;

public class ItemShipmentPage extends TestBase{
	
	@FindBy(xpath = "//input[@id='itemreceive1']//following-sibling::img")
	WebElement receiveCheckBox;
	
	@FindBy(xpath = "//a[@id='inventorydetail_helper_popup_1']")
	WebElement invDetailBtn;
	
	@FindBy(xpath = "//input[@id='secondaryok']")
	WebElement okBtn;
	
	@FindBy(xpath = "//input[@id='btn_secondarymultibutton_submitter']")
	WebElement saveBtn;
	
	@FindBy(xpath = "//div[@class='descr']")
	WebElement confirmationMsg;
	
	@FindBy(xpath = "//div[@class='uir-record-id']")
	WebElement isNumber;
	
	@FindBy(xpath = "//div[@class='uir-record-name']")
	WebElement isVendorName;
	
	@FindBy(xpath = "//span[@class='inputreadonly']//a[contains(text(),'Vendor Return Authorization')]")
	WebElement returnAuthLink;
	
	public ItemShipmentPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnReturnAuthLink() {
		returnAuthLink.click();
	}
	
	public void generateItemShipment() throws InterruptedException {
		Thread.sleep(6000);
		// Select the line item to be shipped
		eleClickable(driver, By.xpath("//input[@id='itemreceive1']"), 10);
		receiveCheckBox.click();
		
		// Configure Inventory detail
		invDetailBtn.click();
		driver.switchTo().frame("childdrecord_frame");
		Thread.sleep(3000);
		okBtn.click();
		
		// Click on Save button
		saveBtn.click();
	}
	
	public void verifyIsCreation(ExtentTest logInfo) {
		eleAvailability(driver, By.xpath("//div[@class='descr']"), 20);
		String confirmationMessage = confirmationMsg.getText().trim();
		String isNumberText = isNumber.getText().trim();
	    String isVendorText = isVendorName.getText().trim();
	    if(confirmationMessage.equals("Transaction successfully Saved")) {
	    	System.out.println("Item Shipment '"+isNumberText+" "+isVendorText+"' created successfully");
	    	logInfo.pass("Item Shipment '"+isNumberText+" "+isVendorText+"' created successfully");
	    }else {
	    	logInfo.fail("Item Shipment not created");
	    }
	}
	
}
