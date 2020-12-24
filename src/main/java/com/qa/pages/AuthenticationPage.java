package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.util.TestBase;

public class AuthenticationPage extends TestBase {
	
	@FindBy(xpath = "//td[@class='smalltextnolink']//following-sibling::td")
	WebElement secQuestion;
	
	@FindBy(name = "submitter")
	WebElement authSubmitBtn;
	
	public AuthenticationPage() {
		PageFactory.initElements(driver, this);
	}

	public HomePage Authentication() {
		// Handling the Security Question Page
		String actQuestion = secQuestion.getText();
		if (actQuestion.trim().equals(prop.getProperty("question1"))) {
			driver.findElement(By.id("null")).sendKeys("nickname");
		} 
		else if (actQuestion.trim().equals(prop.getProperty("question2"))) {
			driver.findElement(By.id("null")).sendKeys("name");
		} 
		else if (actQuestion.trim().equals(prop.getProperty("question3"))) {
			driver.findElement(By.id("null")).sendKeys("job");
		} 
		else if (actQuestion.trim().equals(prop.getProperty("question4"))) {
			driver.findElement(By.id("null")).sendKeys("child");
		} 
		else if (actQuestion.trim().equals(prop.getProperty("question5"))) {
			driver.findElement(By.id("null")).sendKeys("grade");
		}

		authSubmitBtn.click();
		
		return new HomePage();
	}

}
