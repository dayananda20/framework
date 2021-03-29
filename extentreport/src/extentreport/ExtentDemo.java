package extentreport;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentDemo
{
	static ExtentTest test;
	static ExtentReports report;
	
	
	public static String capture(WebDriver driver) throws IOException 
	{
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File("src/../BStackImages/" + System.currentTimeMillis()
		+ ".png");
		String errflpath = Dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, Dest);
		return errflpath;
	}
	@BeforeClass
	public static void startTest()
	{
		report = new ExtentReports("./extentreport/ExtentReportResults.html");
		test = report.startTest("ExtentReport");
	}
	@Test
	public void extentReportsDemo() throws Exception
	{
		System.setProperty("webdriver.chrome.driver", "D:\\selenium\\Browserdriver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.co.in");
		if(driver.getTitle().equals("Google"))
		{
			test.log(LogStatus.PASS, "Navigated to the specified URL");
			test.log(LogStatus.INFO, "information about the page loaded");
			test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+ "Test Failed");
		}
		else
		{
			test.log(LogStatus.FAIL, "Test Failed");
		}
	}
	@AfterClass
	public static void endTest()
	{
		report.endTest(test);
		report.flush();
	}
}