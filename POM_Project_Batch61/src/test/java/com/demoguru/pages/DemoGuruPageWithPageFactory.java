package com.demoguru.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class DemoGuruPageWithPageFactory {
	
	private static final Logger logger = 
			LogManager.getLogger(DemoGuruPage.class);

	@CacheLookup
	@FindBy(name="userName")
	WebElement username;
	@CacheLookup
	@FindBy(name="password")
	WebElement password;
	@CacheLookup
	@FindBy(name="submit")
	WebElement submitButton;
	@CacheLookup
	@FindBy(css ="body > div:nth-child(6) > table > tbody > tr > td:nth-child(2) > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(1) > a")
	WebElement sign_Off;
	@CacheLookup
	@FindBy(xpath="/html/body/div[2]/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[1]/td/h3")
	WebElement successMessage;

	public void login(String un, String ps) {
		username.sendKeys(un);
		password.sendKeys(ps);
		submitButton.click();
	}
	
	public void clearUserNameAndPass() {
		username.clear();
		password.clear();;
	}
	public void clickOnSignOff() {
		try {
			if(sign_Off.isDisplayed()) {
			sign_Off.click();
			}
			}catch (Exception e) {
				logger.error("Sign-Off button not found: "+e.getMessage());
			}
	}
	public String getSuccessMessage() {
		String message=null;
		try {
			 message= successMessage.getText();
			
				return message;
			}catch (Exception e) {
				logger.error(e.getMessage());
				return message;
			}
	} 
	
	public static String getTimeStamp() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
	}

}
