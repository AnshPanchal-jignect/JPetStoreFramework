package base;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utilities.DriverManager;
import utilities.SeleniumHelpers;

public class BaseTest {
    protected WebDriver driver;
    protected SeleniumHelpers seleniumHelpers;
    private DriverManager driverManager;

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        driverManager = new DriverManager();
        driver = driverManager.setUp(browser);
        seleniumHelpers = new SeleniumHelpers(driver);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            // Check if the test failed
            if (result.getStatus() == ITestResult.FAILURE) {
                System.out.println("Test Failed: " + result.getName());
                System.out.println("Failure Reason: " + result.getThrowable().getMessage());
            }
        } catch (Exception e) {
            // Log the exception instead of ignoring
            System.err.println("Error while taking screenshot: " + e.getMessage());
        } finally {
            // Ensure WebDriver is always closed after test execution
            driverManager.tearDown();
        }
    }

}


