package com.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.qa.util.TestBase;

public class CustomerCreationPage extends TestBase{
	
	@FindBy(xpath = "//input[@id='inpt_customform4']")
	WebElement form;
	
	@FindBy(xpath = "//div[@class='dropdownDiv']//div")
	List<WebElement> selectForm;
	
	@FindBy(xpath = "//div[@id='isperson_F']//div//span//span[@id='isperson_fs']//input")
	WebElement companyRadioBtn;
	
	@FindBy(xpath = "//div[@id='isperson_T']//div//span//span[@id='isperson_fs']//input")
	WebElement individualRadioBtn;
	
	@FindBy(id = "firstname")
	WebElement firstName;
	
	@FindBy(id = "lastname")
	WebElement lastName;
	
	@FindBy(id = "companyname")
	WebElement companyName;
	
	@FindBy(xpath = "//span[@id='parent_actionbuttons_parent_fs']//a[2]")
	WebElement parentCompArrowBtn;
	
	@FindBy(xpath = "//div[@id='parent_fs_tooltipMenu']//a[1]")
	WebElement parentCompListBtn;
	
	@FindBy(id = "st")
	WebElement parentCompSearchBox;
	
	@FindBy(id = "Search")
	WebElement parentCompSearchBtn;
	
	@FindBy(xpath = "//div[@id='inner_popup_div']//table//tr//td//following-sibling::td//a")
	List<WebElement> parentList;
	
	@FindBy(id = "btn_secondarymultibutton_submitter")
	WebElement saveBtn;
	
	@FindBy(xpath = "//div[@class='descr']")
	WebElement confirmationMsg;
	
	@FindBy(xpath = "//div[@class='uir-record-name']")
	WebElement customer;
	
	public CustomerCreationPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void verifyCustomer(String customerFirstName, String customerLastName, String customerCompany, ExtentTest logInfo) {
		eleAvailability(driver, By.xpath("//div[@class='descr']"), 10);
	    String confirmationMessage = confirmationMsg.getText().trim();
	    String customer = customerFirstName+" "+customerLastName+" "+customerCompany;
	    if(confirmationMessage.equals("Customer successfully Saved")) {
	    	System.out.println("Customer '"+customer.trim()+"' created successfully");
	    	logInfo.pass("Customer '"+customer.trim()+"' created successfully");
	    }else {
	    	System.out.println("Customer '"+customer.trim()+"' unable to create");
	    	logInfo.fail("Customer '"+customer.trim()+"' unable to create");
	    }
	}
	
	public void createNewCustomer(String customerType, String customerFirstName, String customerLastName, String customerCompany, String customerParentCompany) throws InterruptedException {
		Thread.sleep(4000);
		// Change the Form to "Standard Customer Form"
		eleClickable(driver, By.xpath("//input[@id='inpt_customform4']"), 10);
		form.click();
		for(int i=0;i<selectForm.size();i++) {
			String formValue = selectForm.get(i).getText().trim();
			if(formValue.equals("Standard Customer Form")) {
				selectForm.get(i).click();
			}
		}
		Thread.sleep(5000);
		
		// Handling customer type & entering customer name
		if(customerType.equals("COMPANY")) {
			companyRadioBtn.click();
			// Enter company name
			companyName.sendKeys(customerCompany);
		}
		else {
			individualRadioBtn.click();
			// Enter first name, last name
			firstName.sendKeys(customerFirstName);
			lastName.sendKeys(customerLastName);
			companyName.sendKeys(customerCompany);
		}
		
		// Enter Parent Company
		parentCompArrowBtn.click();
		parentCompListBtn.click();
		Thread.sleep(5000);
		parentCompSearchBox.sendKeys(customerParentCompany);
		parentCompSearchBtn.click();
		Thread.sleep(5000);
		for(int j=0;j<parentList.size();j++) {
			String parentCustomer = parentList.get(j).getText().trim();
			if(parentCustomer.equals(customerParentCompany)) {
				parentList.get(j).click();
				break;
			}
		}
		Thread.sleep(5000);
		
		// Click on save button
		saveBtn.click();
	}
	
}
