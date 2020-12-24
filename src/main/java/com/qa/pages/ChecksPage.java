package com.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.qa.util.TestBase;

public class ChecksPage extends TestBase{
	
	@FindBy(xpath = "//span[@id='parent_actionbuttons_entity_fs']//a[2]")
	WebElement payeeArrowBtn;
	
	@FindBy(xpath = "//div[@id='entity_fs_tooltipMenu']//a[@id='entity_popup_list']")
	WebElement payeeListBtn;
	
	@FindBy(xpath = "//input[@id='st']")
	WebElement searchBox;
	
	@FindBy(xpath = "//input[@id='Search']")
	WebElement searchBtn;
	
	@FindBy(xpath = "//div[@id='inner_popup_div']//table//tr//td//following-sibling::td//a")
	List<WebElement> searchList;
	
	@FindBy(xpath = "//span[@id='parent_actionbuttons_expense_account_fs']//a[2]")
	WebElement expenseAccountArrowBtn;
	
	@FindBy(xpath = "//div[@id='expense_account_fs_tooltipMenu']//a[@id='account_popup_list']")
	WebElement expenseAccountListBtn;
	
	@FindBy(xpath = "//table[@id='expense_splits']//tr[@class='uir-machine-row uir-machine-row-odd listtextnonedit uir-machine-row-focused']//td[(count(//table[@id='expense_splits']//div[text()='Amount']//parent::td//preceding-sibling::*)+1)]")
	WebElement expenseAmountDiv;
	
	@FindBy(xpath = "//input[@id='amount_formattedValue']")
	WebElement expenseAmountBox;
	
	@FindBy(xpath = "//input[@id='expense_addedit']")
	WebElement expenseAddBtn;
	
	@FindBy(xpath = "//a[@id='itemtxt']")
	WebElement itemsSublist;
	
	@FindBy(xpath = "//span[@id='parent_actionbuttons_item_item_fs']//a[2]")
	WebElement itemArrowBtn;
	
	@FindBy(xpath = "//div[@id='item_item_fs_tooltipMenu']//a[@id='item_popup_list']")
	WebElement itemListBtn;
	
	@FindBy(xpath = "//table[@id='item_splits']//tr[@class='uir-machine-row uir-machine-row-odd listtextnonedit uir-machine-row-focused']//td[(count(//table[@id='item_splits']//div[text()='Tax Code']//parent::td//preceding-sibling::*)+1)]")
	WebElement taxCodeDiv;
	
	@FindBy(xpath = "//span[@id='parent_actionbuttons_item_taxcode_fs']//a[2]")
	WebElement taxCodeArrowBtn;
	
	@FindBy(xpath = "//div[@id='item_taxcode_fs_tooltipMenu']//a[@id='taxcode_popup_list']")
	WebElement taxCodeListBtn;
	
	@FindBy(xpath = "//table[@id='item_splits']//tr[@class='uir-machine-row uir-machine-row-odd listtextnonedit uir-machine-row-focused']//td[(count(//table[@id='item_splits']//div[text()='Location']//parent::td//preceding-sibling::*)+1)]")
	WebElement locationDiv;
	
	@FindBy(xpath = "//input[@id='inpt_location40']")
	WebElement location;
	
	@FindBy(xpath = "//div[@class='dropdownDiv']//div")
	List<WebElement> locationList;
	
	@FindBy(xpath = "//input[@id='item_addedit']")
	WebElement itemAddBtn;
	
	@FindBy(xpath = "//input[@id='btn_secondarymultibutton_submitter']")
	WebElement saveBtn;
	
	@FindBy(xpath = "//div[@class='descr']")
	WebElement confirmationMsg;
	
	@FindBy(xpath = "//div[@class='uir-record-id']")
	WebElement checkNumber;
	
	public ChecksPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void verifyCheckCreation(ExtentTest logInfo) {
		eleAvailability(driver, By.xpath("//div[@class='descr']"), 20);
	    String confirmationMessage = confirmationMsg.getText().trim();
	    String checkNumberText = checkNumber.getText().trim();
	    if(confirmationMessage.equals("Transaction successfully Saved")) {
	    	System.out.println("Check '"+checkNumberText+"' created successfully");
	    	logInfo.pass("Check '"+checkNumberText+"' created successfully");
	    }else {
	    	logInfo.fail("Check not created");
	    }
	}
	
	public void createNewCheck(String payeeData, String expenseAccountData, String expenseAmountData, String itemData, String itemTaxCodeData, String itemLocationData) throws InterruptedException {
		
		Thread.sleep(8000);
		// Select Payee
		payeeArrowBtn.click();
		payeeListBtn.click();
		Thread.sleep(7000);
		searchBox.sendKeys(payeeData);
		searchBtn.click();
		Thread.sleep(7000);
		for(int j=0;j<searchList.size();j++) {
			String parentCustomer = searchList.get(j).getText().trim();
			if(parentCustomer.equals(payeeData)) {
				searchList.get(j).click();
				break;
			}
		}
		Thread.sleep(10000);
		
		// Scroll down
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		
		// Select expense Account
		expenseAccountArrowBtn.click();
		expenseAccountListBtn.click();
		Thread.sleep(7000);
		searchBox.sendKeys(expenseAccountData);
		searchBtn.click();
		Thread.sleep(6000);
		for(int j=0;j<searchList.size();j++) {
			String parentCustomer = searchList.get(j).getText().trim();
			if(parentCustomer.trim().equals(expenseAccountData)) {
				searchList.get(j).click();
				break;
			}
		}
		Thread.sleep(4000);
		
		// Select expense amount
		expenseAmountDiv.click();
		expenseAmountBox.sendKeys(expenseAmountData);
		expenseAddBtn.click();
		Thread.sleep(3000);
		
		// Enter Line item data
		itemsSublist.click();
		
		// Select Item
		itemArrowBtn.click();
		itemListBtn.click();
		Thread.sleep(6000);
		searchBox.sendKeys(itemData);
		searchBtn.click();
		Thread.sleep(5000);
		for(int j=0;j<searchList.size();j++) {
			String parentCustomer = searchList.get(j).getText().trim();
			if(parentCustomer.contains(itemData)) {
				searchList.get(j).click();
				break;
			}
		}
		Thread.sleep(5000);
		
		// Select tax code
		taxCodeDiv.click();
		taxCodeArrowBtn.click();
		taxCodeListBtn.click();
		Thread.sleep(7000);
		for(int j=0;j<searchList.size();j++) {
			String parentCustomer = searchList.get(j).getText().trim();
			if(parentCustomer.contains(itemTaxCodeData)) {
				searchList.get(j).click();
				break;
			}
		}
		Thread.sleep(5000);
		
		// Select Location
		locationDiv.click();
		location.click();
		for(int i=0;i<locationList.size();i++) {
			String locationValue = locationList.get(i).getText().trim();
			if(locationValue.equals(itemLocationData)) {
				locationList.get(i).click();
			}
		}
		
		Thread.sleep(5000);
		itemAddBtn.click();
		
		// Click on save button
		saveBtn.click();
	}
}
