package com.qa.ExtentReportsListeners;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportListener {
	public static WebDriver driver;
	public static ExtentHtmlReporter report = null;
	public static ExtentReports extent = null;
	public static ExtentTest test = null;

	public static ExtentReports setUp() {
		String reportLocation = "./Reports/Extent_reports.html";
		report = new ExtentHtmlReporter(reportLocation);
		report.config().setDocumentTitle("Test Report");
		report.config().setReportName("Test Report");
		report.config().setTheme(Theme.STANDARD);
		report.start();
		extent = new ExtentReports();
		extent.attachReporter(report);
		return extent;
	}

	public static void testStepHandle(String teststatus, WebDriver driver, ExtentTest extenttest, Throwable throwable) {
		switch (teststatus) {
		case "FAIL":
			extenttest.fail(MarkupHelper.createLabel("Test case is Failed", ExtentColor.RED));
			extenttest.error(throwable.fillInStackTrace());
//			if (driver != null) {
//				driver.quit();
//			}
			break;
		case "PASS":
			extenttest.pass(MarkupHelper.createLabel("Test case is passed", ExtentColor.GREEN));
			break;
		default:
			break;
		}

	}

}