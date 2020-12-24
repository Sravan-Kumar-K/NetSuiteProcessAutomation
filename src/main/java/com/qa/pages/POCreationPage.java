package com.qa.pages;
// //table[@id='item_splits']//tr[@class='uir-machine-row uir-machine-row-odd listtextnonedit uir-machine-row-focused']//td[(count(//div[text()='Amount']//parent::td//preceding-sibling::*)+1)]
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.qa.util.TestBase;

public class POCreationPage extends TestBase{
	
	@FindBy(xpath = "//input[@id='inpt_customform1']")
	WebElement POform;
	
	@FindBy(xpath = "//div[@class='dropdownDiv']//div")
	List<WebElement> selectForm;
	
	@FindBy(xpath = "//span[@id='parent_actionbuttons_entity_fs']//a[2]")
	WebElement vendorArrowBtn;
	
	@FindBy(xpath = "//div[@id='entity_fs_tooltipMenu']//a[@id='entity_popup_list']")
	WebElement vendorListBtn;
	
	@FindBy(xpath = "//input[@id='st']")
	WebElement vendorSearchBox;
	
	@FindBy(xpath = "//input[@id='Search']")
	WebElement vendorSearchBtn;
	
	@FindBy(xpath = "//div[@id='inner_popup_div']//table//tr//td//following-sibling::td//a")
	List<WebElement> vendorList;
	
	@FindBy(xpath = "//input[@id='inpt_location5']")
	WebElement location;
	
	@FindBy(xpath = "//div[@class='dropdownDiv']//div")
	List<WebElement> locationList;
	
	@FindBy(xpath = "//a[@id='itemtxt']")
	WebElement itemSubtab;
	
	@FindBy(xpath = "//span[@id='parent_actionbuttons_item_item_fs']//a[2]")
	WebElement itemArrowBtn;
	
	@FindBy(xpath = "//div[@id='item_item_fs_tooltipMenu']//a[@id='item_popup_list']")
	WebElement itemListBtn;
	
	@FindBy(xpath = "//input[@id='st']")
	WebElement itemSearchBox;
	
	@FindBy(xpath = "//input[@id='Search']")
	WebElement itemSearchBtn;
	
	@FindBy(xpath = "//div[@id='inner_popup_div']//table//tr//td//following-sibling::td//a")
	List<WebElement> itemList;
	
	@FindBy(xpath = "//input[@id='item_addedit']")
	WebElement itemAddBtn;
	
	@FindBy(xpath = "//table[@id='item_splits']//tr[@class='uir-machine-row uir-machine-row-odd listtextnonedit uir-machine-row-focused']//td[9]")
	WebElement taxCodeDiv;
	
	@FindBy(xpath = "//span[@id='item_taxcode_fs']//div//input")
	WebElement taxCode;
	
	@FindBy(xpath = "//div[@class='dropdownDiv']//div")
	List<WebElement> taxCodeList;
	
	@FindBy(xpath = "//input[@id='btn_secondarymultibutton_submitter']")
	WebElement savePOBtn;
	
	@FindBy(xpath = "//div[@class='descr']")
	WebElement confirmationMsg;
	
	@FindBy(xpath = "//div[@class='uir-record-id']")
	WebElement poNumber;
	
	@FindBy(xpath = "//div[@class='uir-record-name']")
	WebElement poVendor;
	
	@FindBy(xpath = "//div[@class='uir-record-status']")
	WebElement poStatus;
	
	@FindBy(xpath = "//input[@id='edit']")
	WebElement editBtn;
	
	@FindBy(xpath = "//input[@id='inpt_approvalstatus2']")
	WebElement approvalStatus;
	
	@FindBy(xpath = "//div[@class='dropdownDiv']//div")
	List<WebElement> approvalStatusList;
	
	@FindBy(xpath = "//input[@id='receive']")
	WebElement receiveBtn;
	
	@FindBy(xpath = "//input[@id='bill']")
	WebElement billBtn;
	
	@FindBy(xpath = "//input[@id='return']")
	WebElement returnBtn;
	
	public POCreationPage() {
		action = new Actions(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void approvePO() throws InterruptedException {
		// Change Approval status to Approved
		editBtn.click();
		Thread.sleep(10000);
		eleClickable(driver, By.xpath("//input[@id='inpt_approvalstatus2']"), 10);
		approvalStatus.click();
		for(int i=0;i<approvalStatusList.size();i++) {
			String statusValue = approvalStatusList.get(i).getText().trim();
			if(statusValue.equals("Approved")) {
				approvalStatusList.get(i).click();
			}
		}
		
		// Click on save button
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		
		savePOBtn.click();
	}
	
	public ItemReceiptPage clickOnReceiveBtn() {
		receiveBtn.click();
		return new ItemReceiptPage();
	}
	
	public BillPage clickOnBillBtn() {
		eleClickable(driver, By.xpath("//input[@id='bill']"), 10);
		billBtn.click();
		return new BillPage();
	}
	
	public ReturnAuthorizationPage clickOnRetrunBtn() {
		eleClickable(driver, By.xpath("//input[@id='return']"), 10);
		returnBtn.click();
		return new ReturnAuthorizationPage();
	}
	
	public void verifyPOApproval(ExtentTest logInfo) {
		eleAvailability(driver, By.xpath("//div[@class='descr']"), 20);
	    String confirmationMessage = confirmationMsg.getText().trim();
	    String poNumberText = poNumber.getText().trim();
	    String poVendorText = poVendor.getText().trim();
	    String poStatusText = poStatus.getText().trim();
	    if(confirmationMessage.equals("Transaction successfully Saved") && poStatusText.equals("PENDING RECEIPT")) { 
	    	System.out.println("Transaction '"+poNumberText+" "+poVendorText+"' approved successfully with '"+poStatusText+"' status");
	    	logInfo.pass("Transaction '"+poNumberText+" "+poVendorText+"' approved successfully with '"+poStatusText+"' status");
	    }else {
	    	logInfo.fail("Purchase Order unable to approve");
	    }
	}
	
	public void verifyPOCreation(ExtentTest logInfo) {
		eleAvailability(driver, By.xpath("//div[@class='descr']"), 20);
	    String confirmationMessage = confirmationMsg.getText().trim();
	    String poNumberText = poNumber.getText().trim();
	    String poVendorText = poVendor.getText().trim();
	    if(confirmationMessage.equals("Transaction successfully Saved")) {
	    	System.out.println("Transaction '"+poNumberText+" "+poVendorText+"' created successfully");
	    	logInfo.pass("Transaction '"+poNumberText+" "+poVendorText+"' created successfully");
	    }else {
	    	logInfo.fail("Purchase Order not created");
	    }
	}
	
	public void createNewPO(String vendorData, String locationData, String itemData) throws InterruptedException {
		
		// Change the Form to "Standard Purchase Order"
		Thread.sleep(10000);
		eleClickable(driver, By.xpath("//input[@id='inpt_customform1']"), 10);
		POform.click();
		for(int i=0;i<selectForm.size();i++) {
			String formValue = selectForm.get(i).getText().trim();
			if(formValue.equals("Standard Purchase Order")) {
				selectForm.get(i).click();
			}
		}
		Thread.sleep(10000);
		
		// Select Vendor
		vendorArrowBtn.click();
		vendorListBtn.click();
		Thread.sleep(8000);
		vendorSearchBox.sendKeys(vendorData);
		vendorSearchBtn.click();
		Thread.sleep(7000);
		for(int j=0;j<vendorList.size();j++) {
			String parentCustomer = vendorList.get(j).getText().trim();
			if(parentCustomer.equals(vendorData)) {
				vendorList.get(j).click();
				break;
			}
		}
		if(isAlertPresent()) {
	    	Thread.sleep(1000);
			Alert alert = driver.switchTo().alert();
			String alertMsg = alert.getText();
			System.out.println("Alert occured: "+alertMsg);
			alert.accept();
			Thread.sleep(15000);
	    }
		Thread.sleep(8000);
		
		// Select the location
		eleClickable(driver, By.xpath("//input[@id='inpt_location5']"), 10);
		location.click();
		for(int i=0;i<locationList.size();i++) {
			String locationValue = locationList.get(i).getText().trim();
			if(locationValue.equals(locationData)) {
				locationList.get(i).click();
			}
		}
		
		// Select line item and tax code
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		action.click(itemSubtab).build().perform();
		itemArrowBtn.click();
		itemListBtn.click();
		Thread.sleep(7000);
		itemSearchBox.sendKeys(itemData);
		itemSearchBtn.click();
		Thread.sleep(7000);
		for(int j=0;j<itemList.size();j++) {
			String parentCustomer = itemList.get(j).getText().trim();
			if(parentCustomer.equals(itemData)) {
				itemList.get(j).click();
				break;
			}
		}
		Thread.sleep(4000);
		
		// Enter tax code
//		taxCodeDiv.click();
//		eleClickable(driver, By.xpath("//span[@id='item_taxcode_fs']//div//input"), 10);
//		taxCode.click();
//		for(int i=0;i<taxCodeList.size();i++) {
//			String taxCodeValue = taxCodeList.get(i).getText().trim();
//			if(taxCodeValue.equals(taxCodeData)) {
//				taxCodeList.get(i).click();
//			}
//		}
		
		// Click Add button
		itemAddBtn.click();
		
		// Save the PO
		savePOBtn.click();
		
	}
	
}
