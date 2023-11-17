package QKART_TESTNG;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerClass implements ITestListener {
    public static void takeScreenshot(WebDriver driver, String screenshotType, String description) {
        try {
            File theDir = new File("/screenshots");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            String timestamp = String.valueOf(java.time.LocalDateTime.now());
            String fileName = String.format("screenshot_%s_%s_%s.png", timestamp, screenshotType, description);
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile = new File("screenshots/" + fileName);
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onStart(ITestContext context) {
        System.out.println("onStart method started");
    }

    public void onTestStart(ITestResult result) {
        System.out.println("New Test Started" +result.getName());
        takeScreenshot(QKART_Tests.driver, "TestStart", result.getName());
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("onTestSuccess Method" +result.getName());
        takeScreenshot(QKART_Tests.driver, "TestSuccess", result.getName());
    }

    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed : " + result.getName() + " Taking Screenshot ! ");
        takeScreenshot(QKART_Tests.driver, "TestFailed", result.getName());
    }

}