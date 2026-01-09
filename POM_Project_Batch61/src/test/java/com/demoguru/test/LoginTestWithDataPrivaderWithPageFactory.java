package com.demoguru.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.demoguru.ReadProperties;
import com.demoguru.pages.DemoGuruPage;
import com.demoguru.pages.DemoGuruPageWithPageFactory;


public class LoginTestWithDataPrivaderWithPageFactory extends ReadProperties {

	private WebDriver driver;
	private static final Logger logger = 
			LogManager.getLogger(LoginTestWithDataPrivaderWithPageFactory.class); 
	
	DemoGuruPageWithPageFactory page;
	
	@BeforeSuite
	public void openBrowser() {
		System.setProperty("webdriver.chrome.driver", getChromeDriver());

		driver = new ChromeDriver();
		
		page = PageFactory.initElements(driver, DemoGuruPageWithPageFactory.class);
		logger.info("Broswer open success");
	}

	@BeforeTest
	public void windowsMaximize() {
		driver.manage().window().maximize();
	}

	@BeforeClass
	public void openDemoGuru() {
		driver.get(getLoginUrl());
		logger.info("Demoguru website open successfully");
	}
	
	@BeforeMethod
	public void clearInputBox() {
		try {
		page.clearUserNameAndPass();
		}catch (Exception e) {
			logger.error("Username or password element not found:"+e.getMessage());
		}
	}

	@Test(dataProvider = "loginData")
	public void checkWithValidData(String un, String ps) throws InterruptedException {
		page.login(un, ps);
		String message=null;
		try {
//		File screenshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//		FileUtils.copyFile(screenshot, new File("target/screeshot/Login "+page.getTimeStamp()));
		Thread.sleep(2000);
		
		
		 message=page.getSuccessMessage();
		
			 writeResult(un,ps,true);
		logger.info("Success message read success");
		Assert.assertEquals(message, "Login Successfully");
		}catch (Exception e) {
			logger.warn("Success message not found and asserration is fail: "+e.getMessage());
			writeResult(un,ps,false);
			 
			Assert.assertEquals(message, "Login Successfully");
		}
	}

	@AfterMethod
	public void afterMethod() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		page.clickOnSignOff();
	}

	@AfterSuite
	public void closeBrowser() {
		driver.quit();
		logger.info("Browser close!");
	}
	@DataProvider
	public Object[][] loginData() throws IOException{

		return getLoginCreFromExcelFile();		
	}
	
	
	
	
	
}
