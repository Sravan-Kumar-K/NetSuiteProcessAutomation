package com.qa.pages;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.util.TestBase;

public class HomePage extends TestBase{
	
	@FindBy(xpath = "//span[text()='Lists']")
	WebElement listLink;
	
	@FindBy(xpath = "//span[text()='Relationships']")
	WebElement relationshipsLink;
	
	@FindBy(xpath = "//span[text()='Customers']")
	WebElement customersLink;
	
	@FindBy(xpath = "//span[text()='New']")
	WebElement newLink;
	
	@FindBy(xpath = "//span[text()='Transactions']")
	WebElement transactionsLink;
	
	@FindBy(xpath = "//span[text()='Purchases']")
	WebElement PurchasesLink;
	
	@FindBy(xpath = "//span[text()='Enter Purchase Orders']")
	WebElement enterPurchaseOrderLink;
	
	@FindBy(xpath = "//span[text()='Bank']")
	WebElement bankLink;
	
	@FindBy(xpath = "//span[text()='Write Checks']")
	WebElement checksLink;
	
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	public ChecksPage clickOnWriteChecksLink() throws InterruptedException {
		Thread.sleep(5000);
		eleAvailability(driver, By.xpath("//span[text()='Transactions']"), 10);
		action.moveToElement(transactionsLink).build().perform();
		eleAvailability(driver, By.xpath("//span[text()='Bank']"), 10); // Explicit Wait
		action.moveToElement(bankLink).build().perform();
		eleAvailability(driver, By.xpath("//span[text()='Write Checks']"), 10); // Explicit Wait
		checksLink.click();
		
		if(isAlertPresent()) {
	    	Thread.sleep(1000);
			Alert alert = driver.switchTo().alert();
			String alertMsg = alert.getText();
			System.out.println("Alert occured: "+alertMsg);
			alert.accept();
	    }
		
		return new ChecksPage();
	}
	
	public POCreationPage clickOnNewPOLink() throws InterruptedException {
		Thread.sleep(5000);
		eleAvailability(driver, By.xpath("//span[text()='Transactions']"), 10);
		action.moveToElement(transactionsLink).build().perform();
		eleAvailability(driver, By.xpath("//span[text()='Purchases']"), 10); // Explicit Wait
		action.moveToElement(PurchasesLink).build().perform();
		eleAvailability(driver, By.xpath("//span[text()='Enter Purchase Orders']"), 10); // Explicit Wait
		enterPurchaseOrderLink.click();
		
		return new POCreationPage();
	}
	
	public void changeRole(String role) {
		
		action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//*[@id=\"spn_cRR_d1\"]/a"))).build().perform();
		eleAvailability(driver, By.xpath("//*[@id=\"ns-header-menu-userrole-item6\"]/a"), 5);
		
		// Change User role
		List<WebElement> rolesDropdown = driver.findElements(By.xpath("//ul[@class='ns-menu ns-active']//li[contains(@id,'ns-header-menu-userrole-item')]//a//span"));
		
		//Selecting Micron Optics Role from the Dropdown
		for(int i=0;i<rolesDropdown.size();i++) {
			WebElement roleListElement = rolesDropdown.get(i);
			String roleValue = roleListElement.getText().trim();
			if(roleValue.equals(role)) {
				roleListElement.click();
				break;
			}
		}
	}
	
	public CustomerCreationPage clickOnNewCustomerLink() throws InterruptedException {
		if(driver.getTitle().equals("Notice")) {
			driver.findElement(By.xpath("//input[@id='goback']")).click();
			Thread.sleep(9000);
		}
		Thread.sleep(5000);
		eleAvailability(driver, By.xpath("//span[text()='Lists']"), 10);
		action.moveToElement(listLink).build().perform();
		eleAvailability(driver, By.xpath("//span[text()='Relationships']"), 10); // Explicit Wait
		action.moveToElement(relationshipsLink).build().perform();
		eleAvailability(driver, By.xpath("//span[text()='Customers']"), 10); // Explicit Wait
		action.moveToElement(customersLink).build().perform();
		eleAvailability(driver, By.xpath("//span[text()='New']"), 10);
		newLink.click();
		
		return new CustomerCreationPage();
		
	}
	
	
}
