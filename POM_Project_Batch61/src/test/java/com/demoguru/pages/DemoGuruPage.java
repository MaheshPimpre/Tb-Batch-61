package com.demoguru.pages;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;


public class DemoGuruPage {

	private WebDriver driver;
	private static final Logger logger = 
			LogManager.getLogger(DemoGuruPage.class);
	
	public DemoGuruPage(WebDriver driver) {
		this.driver=driver;
	}
	
	By username=By.name("userName");
	By password=By.name("password");
	By submitButton=By.name("submit");
	By sign_Off=By.cssSelector("body > div:nth-child(6) > table > tbody > tr > td:nth-child(2) > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(1) > a");
	By successMessage=By.xpath("/html/body/div[2]/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[1]/td/h3");

	public void login(String un, String ps) {
		driver.findElement(username).sendKeys(un);
		driver.findElement(password).sendKeys(ps);
		driver.findElement(submitButton).click();
	}
	
	public void clearUserNameAndPass() {
		driver.findElement(username).clear();;
		driver.findElement(password).clear();;
	}
	public void clickOnSignOff() {
		try {
			if(driver.findElement(sign_Off).isDisplayed()) {
			driver.findElement(sign_Off).click();
			}
			}catch (Exception e) {
				logger.error("Sign-Off button not found: "+e.getMessage());
			}
	}
	public String getSuccessMessage() {
		String message=null;
		try {
			 message= driver.findElement(successMessage).getText();
			
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
