package utilities;

import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SeleniumHelpers extends WaitHelpers {
    JavaHelpers helper;
    Actions actions;

    public SeleniumHelpers(WebDriver driver) {
        super(driver);
        helper = new JavaHelpers();
        actions = new Actions(driver);
    }

    /**
     * @param maxNumber int
     * @param minNumber int
     * @return randomNumber
     */
    public int getRandomNumber(int maxNumber, int minNumber) {
        return (int) Math.floor(Math.random() * ((maxNumber) - minNumber) + minNumber);
    }

    // Take screenshot

    /**
     * Take screenshot of the web page
     *
     * @param fileName screenshot file name
     * @throws IOException

    public void takeScreenshot(String fileName) throws IOException {
    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    Files.copy(scrFile,
    new File(Constants.SCREENSHOT_LOCATION + "\\" + fileName + helper.getTimeStamp("_yyyyMMdd_HHmmss") + ".png"));
    }

     */
    //Elements

    /**
     * Enter text to input field
     *
     * @param e
     * @param text
     * @param clear
     */
    public void enterText(WebElement e, String text, boolean clear) {
        e = waitTillElementIsClickable(e);
        if (clear) {
            e.clear();
        }
        e.sendKeys(text);
    }

    /**
     * Enter text to input field
     *
     * @param by    By object
     * @param text  input text
     * @param clear set true if want to clear field else set false
     */
    public void enterText(By by, String text, boolean clear) {
        WebElement e = waitTillElementIsClickable(by);
        if (clear) {
            e.clear();
        }
        e.sendKeys(text);
    }

    /**
     * Enter Key strokes to textbox
     *
     * @param e   WebElement object
     * @param key Keys value
     */
    public void enterText(WebElement e, Keys key) {
        e.sendKeys(key);
    }

    /**
     * Clear text box using Keys
     *
     * @param e WebElement Object
     */
    public void clearTextBoxUsingKeys(WebElement e) {
        waitTillElementIsClickable(e).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }

    /**
     * Enter text to input field
     *
     * @param e     WebElement object
     * @param text  input text
     * @param clear set true if want to clear field else set false
     * @throws InterruptedException
     */
    public void enterTextCharacterByCharacter(WebElement e, String text, boolean clear) throws InterruptedException {
        e = waitTillElementIsClickable(e);
        if (clear) {
            e.clear();
        }

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            String s = String.valueOf(c);
            e.sendKeys(s);
            Thread.sleep(5000); // Waiting for 0.5 second
        }
    }

    /**
     * Double click and enter text to input field
     *
     * @param e    WebElement object
     * @param text input text
     * @throws InterruptedException
     */
    public void fillFieldAfterDoubleClick(WebElement e, String text) {
        Actions actions = new Actions(driver);
        actions.moveToElement(e);
        actions.doubleClick();
        actions.sendKeys(text);
        actions.build().perform();
    }

    /**
     * Get Text from field
     *
     * @param e WebElement object
     * @return text from field
     */
    public String getText(WebElement e) {
        return waitTillElementIsVisible(e).getText().trim();
    }

    /**
     * Get Text from field
     *
     * @param by By object
     * @return text from field
     */
    public String getText(By by) {
        return waitTillElementIsVisible(by, Constants.WEB_DRIVER_WAIT_DURATION).getText().trim();
    }

    /**
     * Click on Element
     *
     * @param by By object
     * @return
     * @throws InterruptedException Exception
     */
    public String clickOn(By by) throws InterruptedException {
        waitTillElementIsClickable(by).click();
        waitForJavascriptToLoad();
        return null;
    }

    /**
     * Click on Element
     *
     * @param e WebElement object
     * @throws InterruptedException
     */
    public void clickOn(WebElement e) throws InterruptedException {
        waitTillElementIsClickable(e).click();
        waitForJavascriptToLoad();
    }

    /**
     * Click on Element (without waiting for it's clickable)
     *
     * @param e WebElement object
     * @throws InterruptedException
     */
    public void click(WebElement e) throws InterruptedException {
        e.click();
        waitForJavascriptToLoad();
    }

    /**
     * To determine whether WebElement has given Attribute or not
     *
     * @param e             WebElement object
     * @param attributeName attribute name e.g. style
     * @return boolean
     */
    public boolean isElementAttributePresent(WebElement e, String attributeName) {
        return e.getAttribute(attributeName) != null;
    }

    /**
     * To get Element attribute value
     *
     * @param e             WebElement object
     * @param attributeName attribute name e.g. style
     * @return attribute value
     */
    public String getElementAttributeValue(WebElement e, String attributeName) {
        if (isElementAttributePresent(e, attributeName)) {
            return e.getAttribute(attributeName);
        }
        return "Attribute" + attributeName + " not found";
    }

    /**
     * method verify whether element is present on screen
     *
     * @param targetElement element to be present
     * @return true if element is present else throws exception
     */
    public Boolean isElementPresent(By targetElement) {
        return waitInCaseElementVisible(targetElement, Constants.WEB_DRIVER_WAIT_DURATION) != null && waitInCaseElementVisible(targetElement, Constants.WEB_DRIVER_WAIT_DURATION).isDisplayed();
    }

    /**
     * method verify whether element is present on screen
     *
     * @param targetElement element to be present
     * @return true if element is present else throws exception
     */
    public Boolean isElementPresent(WebElement targetElement) {
        return waitInCaseElementVisible(targetElement, Constants.WEB_DRIVER_WAIT_DURATION) != null && waitInCaseElementVisible(targetElement, Constants.WEB_DRIVER_WAIT_DURATION).isDisplayed();
    }

    /**
     * Is selected ?
     *
     * @param e WebElement object
     * @return true / false
     */
    public boolean isSelected(WebElement e) {
        return waitInCaseElementVisible(e, Constants.WEB_DRIVER_WAIT_DURATION).isSelected();
    }


    //Checkboxes

    /**
     * Select / De-select checkbox
     *
     * @param e      WebElement object
     * @param select true if you want to select it, false if you want to de-select it
     */
    public void checkbox(WebElement e, boolean select) {
        if (select) {
            if (!e.isSelected()) {
                waitTillElementIsClickable(e).click();
            }
        } else {
            if (e.isSelected()) {
                waitTillElementIsClickable(e).click();
            }
        }
    }

    /**
     * Is checkbox selected ?
     *
     * @param e WebElement object
     * @return true / false
     */
    public boolean isCheckboxSelected(WebElement e) {
        return e.isSelected();
    }


    //Navigation
    public void navigateToPage(String url) {
        driver.get(url);
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void back() {
        driver.navigate().back();
    }

    public String getURL() {
        return driver.getCurrentUrl();
    }


    //Alerts
    public void waitTillAlertPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.WEB_DRIVER_WAIT_DURATION));
        wait.until(ExpectedConditions.alertIsPresent());
    }

    public void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public String getTextFromAlert() {
        return driver.switchTo().alert().getText().trim();
    }


    //Drop-down
    public void selectDropDownValueByText(WebElement e, String text) {
        new Select(e).selectByVisibleText(text);
    }

    public String getSelectedDropDownValue(WebElement e) {
        return new Select(e).getFirstSelectedOption().getText().trim();
    }

    public String selectDropDownValueByIndex(WebElement e, int index) {
        new Select(e).selectByIndex(index);
        return new Select(e).getFirstSelectedOption().getText().trim();
    }

    public List<String> getAllDropDownValues(WebElement e) {
        List<WebElement> list = new Select(e).getOptions();
        return list.stream().map(x -> x.getText().trim()).collect(Collectors.toList());
    }


    //Action events
    public void focusOnElement(WebElement e) {
        actions.moveToElement(e);
        actions.click();
    }

    public void doubleClickOnElement(WebElement e) {
        actions.doubleClick(e).build().perform();
    }

    public void doubleClickOnElementWithOffset(WebElement e, int x_off, int y_off) {
        actions.moveToElement(e, x_off, y_off).doubleClick().build().perform();
    }

    public void singleClickOnElementWithOffset(WebElement e, int x_off, int y_off) {
        actions.moveToElement(e, x_off, y_off).click().build().perform();
    }

    public void singleClickOnElement(WebElement e) {
        actions.moveToElement(e).click().build().perform();
    }

    public void dragAndDrop(WebElement drag, WebElement drop) throws InterruptedException {
        Actions actions = new Actions(driver);
        actions.clickAndHold(drag).build().perform();
        hardWait(3);
        actions.moveToElement(drop).build().perform();
        hardWait(3);
        actions.release(drop).build().perform();
        hardWait(3);
    }


    //Page scrolls
    public WebElement pageScrollInView(WebElement e) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView(true);", e);
        return e;
    }

    public WebElement pageScrollInView(By by) {
        WebElement e = driver.findElement(by);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView(true);", e);
        return e;
    }

    public void windowFocus() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.focus();");
    }


    //Java-script Helpers
    public void javascriptSetValue(WebElement e, String value) {
        String script = "arguments[0].value='" + value + "';";
        ((JavascriptExecutor) driver).executeScript(script, e);
    }

    /**
     * Get textbox value using javascript method
     *
     * @param element WebElement object having type Id
     * @return text
     */
    public String javascriptGetValue(WebElement element) {
        String id = element.toString().replaceAll("]", "").split("id:")[1].trim();
        return javascriptGetValue(id);
    }

    public String javascriptGetValue(String elementId) {
        String script = "return document.getElementById('" + elementId + "').value;";
        return ((JavascriptExecutor) driver).executeScript(script).toString().trim();
    }


    public void javascriptClickOn(WebElement e) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
    }

    public void javascriptSetAnAttribute(WebElement e, String attribute, String value) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        String jsScript = "arguments[0].setAttribute(arguments[1], arguments[2])";
        jse.executeScript(jsScript, e, attribute, value);
    }


    //Browser's Tab handler
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    public void switchToWindow(int tabNumber) {
        int i = 1;
        for (String winHandle : getWindowHandles()) {
            driver.switchTo().window(winHandle);
            if (i == tabNumber)
                break;
            i++;
        }
    }

    public void switchToWindow(String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    //iFrames
    public void switchToIframe(String iframeId) {
        driver.switchTo().frame(iframeId);
    }

    public void switchToIframe(int iframeIndex) {
        driver.switchTo().frame(iframeIndex);
    }

    public void switchToIframe(WebElement e) {
        driver.switchTo().frame(e);
    }

    public void switchToMainIframe(String s) {
        driver.switchTo().defaultContent();
    }

}
