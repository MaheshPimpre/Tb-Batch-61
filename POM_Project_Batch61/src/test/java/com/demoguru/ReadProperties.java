package com.demoguru;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadProperties {

	private String chromeDriver;
	private String excelFilePath;
	private String screenShotPath;
	private String loginUrl;
	private Object[][] loginCreFromExcelFile;
	
	private static final Logger logger = 
			LogManager.getLogger(ReadProperties.class); 
	
	public ReadProperties() {
		Properties prop=null;
		try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/DemoGuru.properties")) {
			prop = new Properties();
			prop.load(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load config.properties file");
		}
		String chromeDriverPath = prop.getProperty("chromedriver");
		this.chromeDriver=chromeDriverPath;
		
		String excelfilepath = prop.getProperty("excelfilepath");
		this.excelFilePath=excelfilepath;
		
		String captureScreenshotPath = prop.getProperty("captureScreenshot");
		this.screenShotPath=captureScreenshotPath;
		
		String loginUrl = prop.getProperty("loginUrl");
		this.loginUrl=loginUrl;
		
		loginCreFromExcelFile = readLoginCreFromExcelFile(excelFilePath);
	}

	public String getChromeDriver() {
		return chromeDriver;
	}

	public void setChromeDriver(String chromeDriver) {
		this.chromeDriver = chromeDriver;
	}

	public String getExcelFilePath() {
		return excelFilePath;
	}

	public void setExcelFilePath(String excelFilePath) {
		this.excelFilePath = excelFilePath;
	}

	public String getScreenShotPath() {
		return screenShotPath;
	}

	public void setScreenShotPath(String screenShotPath) {
		this.screenShotPath = screenShotPath;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	
	public Object[][] getLoginCreFromExcelFile() {
		return loginCreFromExcelFile;
	}

	public void setLoginCreFromExcelFile(Object[][] loginCreFromExcelFile) {
		this.loginCreFromExcelFile = loginCreFromExcelFile;
	}

	private static Object[][] readLoginCreFromExcelFile(String excelFilePath) {
		Workbook book=null;
		FileInputStream file=null;
		try {
		
		String filePath=excelFilePath;
		 file=new FileInputStream(new File(filePath));
		
		 book=new XSSFWorkbook(file);
		
		Sheet sheet = book.getSheet("DemoGuruLoginData");
		
		int lastRowNum = sheet.getLastRowNum();
		Row row = sheet.getRow(1);
		short lastCellNum = row.getLastCellNum();
		Object[][] obj=new Object[lastRowNum][lastCellNum];
		
		logger.info("Last Row No: "+lastRowNum);
		for(int i=1;i<=lastRowNum;i++) {
			 row = sheet.getRow(i);
			 lastCellNum = row.getLastCellNum();
			System.out.println("Last Row No: "+lastCellNum);
			for(int j=0;j<lastCellNum;j++) {
				Cell cell = row.getCell(j);
				String cellValue = cell.getStringCellValue();
				System.out.println(cellValue);
				obj[i-1][j]=cellValue;
				System.out.println(obj[i-1][j]);
			}
		}
		return obj;
		}catch (IOException e) {
			
			logger.error(e.getMessage());
		}
		finally {
			  try {
				book.close();
				file.close();
			  } catch (IOException e) {
				logger.error(e.getMessage());
			  }
		        
		}
		return null;
	}
	
	public void writeResult(String un, String ps, boolean result ) {
		Workbook book=null;
		FileInputStream file=null;
		try {
		
		
		 try {
			file=new FileInputStream(new File(excelFilePath));
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		
		 book=new XSSFWorkbook(file);
		
		Sheet sheet = book.getSheet("DemoGuruLoginData");
		
		int lastRowNum = sheet.getLastRowNum();
		Row row = sheet.getRow(1);
		short lastCellNum = row.getLastCellNum();
		
		System.out.println("Last Row No: "+lastRowNum);
		for(int i=1;i<=lastRowNum;i++) {
			 row = sheet.getRow(i);
			 lastCellNum = row.getLastCellNum();
			System.out.println("Last Row No: "+lastCellNum);
			String username = row.getCell(0).getStringCellValue();
			String password = row.getCell(1).getStringCellValue();
			if(username.equals(un)&&password.equals(ps)) {
				row.createCell(lastCellNum).setCellValue(result);
			}
		}
		logger.info("Result write successfully for "+un+" user result is: "+result);
		}catch (IOException e) {
			logger.error("Result write error: "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(new File(excelFilePath));
				book.write(fos);
				System.out.println("Result write success");
				  book.close();
			        file.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			
		}
	}
	
}
