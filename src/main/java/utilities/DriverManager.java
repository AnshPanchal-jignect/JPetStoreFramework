package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    private WebDriver driver;

    public WebDriver setUp(String browserName) {
        switch (browserName.toLowerCase().trim()) {
            case "chrome":
                // Set up ChromeDriver using WebDriverManager
                WebDriverManager.chromedriver().setup();

                // Create a map to store preferences
                Map<String, Object> prefs = new HashMap<String, Object>();

                //Disable Browser Notifications (1 = Allow, 2 = Block)
                prefs.put("profile.default_content_setting_values.notifications", 2);

                // Pass preferences to ChromeOptions
                ChromeOptions chromeOptions = new ChromeOptions();

                // Set experimental options (Chrome preferences)
                chromeOptions.setExperimentalOption("prefs", prefs);

                // Launch Chrome in Incognito mode (no browsing history, cookies, or cache)
                chromeOptions.addArguments("--incognito");

                // Initialize ChromeDriver with the configured options
                driver = new ChromeDriver(chromeOptions);

                break;
            case "firefox":
                // Set up FirefoxDriver using WebDriverManager
                WebDriverManager.firefoxdriver().setup();

                // Create an instance of FirefoxOptions
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                // Accept insecure certificates (useful for testing on self-signed HTTPS sites)
                firefoxOptions.setAcceptInsecureCerts(true);

                // Disable browser notifications
                firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                firefoxOptions.addPreference("dom.push.enabled", false);

                // Launch Firefox in Private (Incognito) mode
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome-headless":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new");

                // Set window size (ensures consistent viewport across environments)
                options.addArguments("window-size=1440,900");

                // Bypass proxy (prevents connectivity issues)
                options.addArguments("--proxy-server='direct://'");
                options.addArguments("--proxy-bypass-list=*");

                // Initialize ChromeDriver with headless options
                driver = new ChromeDriver(options);
                break;
            case "ie":
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Please specify valid browser name. Valid browser names are: firefox, chrome,chrome-headless, ie ,edge");
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit(); // Close all browser windows and safely terminate WebDriver
                driver = null; // Prevent memory leaks by clearing the instance
            }
        } catch (Exception e) {
            System.err.println("Error while quitting the WebDriver: " + e.getMessage());
        }
    }

}
