package com.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.qa.util.TestBase;

public class ItemReceiptPage extends TestBase{
	
	@FindBy(xpath = "//div[@class='dropdownDiv']//div")
	List<WebElement> selectForm;
	
	@FindBy(xpath = "//input[@id='itemreceive1']//following-sibling::img")
	WebElement receiveCheckBox;
	
	@FindBy(xpath = "//input[@id='btn_secondarymultibutton_submitter']")
	WebElement saveBtn;
	
	@FindBy(xpath = "//div[@class='descr']")
	WebElement confirmationMsg;
	
	@FindBy(xpath = "//div[@class='uir-record-id']")
	WebElement irNumber;
	
	@FindBy(xpath = "//div[@class='uir-record-name']")
	WebElement irVendorName;
	
	@FindBy(xpath = "//input[@id='inpt_customform1']")
	WebElement irForm;
	
	@FindBy(xpath = "//span[@class='inputreadonly']//a")
	WebElement poLink;
	
	public ItemReceiptPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnPOLink() {
		poLink.click();
	}
	
	public void generateItemReceipt() throws InterruptedException {
		// Change the Form to "Standard Item Receipt Form"
		Thread.sleep(10000);
		eleClickable(driver, By.xpath("//input[@id='inpt_customform1']"), 10);
		irForm.click();
		for(int i=0;i<selectForm.size();i++) {
			String formValue = selectForm.get(i).getText().trim();
			if(formValue.equals("Standard Item Receipt")) {
				selectForm.get(i).click();
			}
		}
		Thread.sleep(10000);
		
		// Select the line item to receive
		eleClickable(driver, By.xpath("//input[@id='itemreceive1']"), 10);
		receiveCheckBox.click();
		
		// Save the Item Receipt
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		
		saveBtn.click();
	}
	
	public void verifyIrCreation(ExtentTest logInfo) {
		eleAvailability(driver, By.xpath("//div[@class='descr']"), 20);
		String confirmationMessage = confirmationMsg.getText().trim();
		String irNumberText = irNumber.getText().trim();
	    String irVendorText = irVendorName.getText().trim();
	    if(confirmationMessage.equals("Transaction successfully Saved")) {
	    	System.out.println("Item Receipt '"+irNumberText+" "+irVendorText+"' created successfully");
	    	logInfo.pass("Item Receipt '"+irNumberText+" "+irVendorText+"' created successfully");
	    }else {
	    	logInfo.fail("Item Receipt is not created");
	    }
	}
}