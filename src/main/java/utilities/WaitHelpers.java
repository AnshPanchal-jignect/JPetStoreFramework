package utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WaitHelpers {
    protected WebDriver driver;

    public WaitHelpers(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * To wait until element is clickable
     *
     * @param e WebElement object
     * @return WebElement object
     */
    public WebElement waitTillElementIsClickable(WebElement e) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.WEB_DRIVER_WAIT_DURATION));
        wait.until(ExpectedConditions.elementToBeClickable(e));
        return e;
    }

    /**
     * To wait until element is clickable
     *
     * @param by By object
     * @return WebElement object
     */
    public WebElement waitTillElementIsClickable(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.WEB_DRIVER_WAIT_DURATION));
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    /**
     * o wait until element is visible
     *
     * @param e WebElement object
     * @return WebElement object
     */
    public WebElement waitTillElementIsVisible(WebElement e) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.WEB_DRIVER_WAIT_DURATION));
        wait.until(ExpectedConditions.visibilityOf(e));
        return e;
    }

    /**
     * o wait until element is visible
     *
     * @param e                     WebElement object
     * @param waitDurationInSeconds wait duration in seconds
     * @return WebElement object
     */
    public WebElement waitTillElementIsVisible(WebElement e, int waitDurationInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitDurationInSeconds));
        wait.until(ExpectedConditions.visibilityOf(e));
        return e;
    }

    /**
     * o wait until element is visible
     *
     * @param by                    By object
     * @param waitDurationInSeconds wait duration in seconds
     * @return WebElement object
     */
    public WebElement waitTillElementIsVisible(By by, int waitDurationInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitDurationInSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Wait until element is invisible
     *
     * @param e WebElement object
     */
    public void waitTillElementIsNotVisible(WebElement e) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.WEB_DRIVER_WAIT_DURATION));
        wait.until(ExpectedConditions.invisibilityOf(e));
    }

    /**
     * Wait until element is invisible
     *
     * @param by By object
     */
    public void waitTillElementIsNotVisible(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.WEB_DRIVER_WAIT_DURATION));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    /**
     * Wait until element is invisible
     *
     * @param e                     WebElement object
     * @param waitDurationInSeconds wait duration in seconds
     */
    public void waitTillElementIsNotVisible(WebElement e, int waitDurationInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitDurationInSeconds));
        wait.until(ExpectedConditions.invisibilityOf(e));
    }

    /**
     * Wait until element is invisible
     *
     * @param waitDurationInSeconds wait duration in seconds
     * @param by                    By object
     */
    public void waitTillElementIsNotVisible(By by, int waitDurationInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitDurationInSeconds));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    /**
     * Wait for specified duration and check if element is visible or not
     *
     * @param e        WebElement object
     * @param duration wait duration in seconds
     * @return WebElement if visible or null if not visible
     */
    public WebElement waitInCaseElementVisible(WebElement e, int duration) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        try {
            return wait.until(ExpectedConditions.visibilityOf(e));
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Wait for specified duration and check if element is visible or not
     *
     * @param by       By object
     * @param duration wait duration in seconds
     * @return WebElement if visible or null if not visible
     */
    public WebElement waitInCaseElementVisible(By by, int duration) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Wait for specified duration and check if element is clickable or not
     *
     * @param e        WebElement object
     * @param duration wait duration in seconds
     * @return WebElement if clickable or null if not visible
     */
    public WebElement waitInCaseElementClickable(WebElement e, int duration) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(e));
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Wait till Element count is less than provided number
     *
     * @param by    By object
     * @param count provide number
     */
    public void waitTillElementsCountIsLessThan(By by, int count) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.WEB_DRIVER_WAIT_DURATION));
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(by, count));
    }

    /**
     * Wait till Element count is more than provided number
     *
     * @param by    By object
     * @param count provide number
     */
    public void waitTillElementsCountIsMoreThan(By by, int count) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.WEB_DRIVER_WAIT_DURATION));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(by, count));
    }

    /**
     * Wait till Element count is equal to provided number
     *
     * @param by    By object
     * @param count provide number
     */
    public void waitTillElementsCountIsEqualTo(By by, int count) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.WEB_DRIVER_WAIT_DURATION));
        wait.until(ExpectedConditions.numberOfElementsToBe(by, count));
    }

    /**
     * Wait till frame is available for switching
     *
     * @param e WebElement object
     */
    public void waitTillFrameToBeAvailableAndSwitchToIt(WebElement e) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.WEB_DRIVER_WAIT_DURATION));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(e));
    }

    /**
     * Wait till element not attached to DOM
     *
     * @param e WebElement object
     */
    public void waitTillElementNotAttachedToDOM(WebElement e) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.WEB_DRIVER_WAIT_DURATION));
        wait.until(ExpectedConditions.stalenessOf(e));
    }

    /**
     * Waiting before performing next action
     *
     * @param seconds provide duration e.g. 1,2 etc
     */
    public void hardWait(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000L);
    }

    /**
     * Wait till all elements are located
     *
     * @param by By object
     * @return List of WebElement
     */
    public List<WebElement> waitTillAllElementsAreLocated(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.WEB_DRIVER_WAIT_DURATION));
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Wait till element is refreshed
     *
     * @param e WebElement object
     * @return WebElement object
     */
    public WebElement waitTillElementIsRefreshed(WebElement e) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.WEB_DRIVER_WAIT_DURATION));
        return wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(e)));
    }

    /**
     * This function will wait for page to load (waiting for java script to finish loading) before moving further
     *
     */
    public void waitForJavascriptToLoad() throws InterruptedException {
        Thread.sleep(2000);
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.WEB_DRIVER_WAIT_DURATION));
        try {
            wait.until(expectation);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }

}



