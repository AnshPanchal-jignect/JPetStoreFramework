package pageobjects.login;

import dataobjects.login.LoginUserDo;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePo;

public class LoginPO extends BasePo {
    public LoginPO(WebDriver driver) {
        super(driver);
    }

    //Update the locator using unique name
    @FindBy(xpath = "//input[@name='username']")
    private WebElement userNameField;

    @FindBy(css = "input[name='password']")
    private WebElement passwordField;

    @FindBy(css = "input[name='signon']")
    private WebElement loginButton;

    @FindBy(xpath = "//p[contains(text(), 'Please enter your username and password.')]")
    private WebElement loginPageText;

    @FindBy(xpath = "//li[contains(text(),'Invalid username or password.  Signon failed.')]")
    private WebElement loginErrorMessageText;

    @FindBy(css = "a[href='/actions/Account.action?newAccountForm=']")
    private WebElement registerNowLink;

    public boolean isLoginPageTextDisplayed() {
        selenium.waitTillElementIsVisible(loginPageText);
        return loginPageText.isDisplayed();
    }

    public boolean isLoginErrorMessageDisplayed() {
        selenium.waitTillElementIsVisible(loginErrorMessageText);
        return loginErrorMessageText.isDisplayed();
    }

    public void clickOnRegisterButton() throws InterruptedException {
        selenium.clickOn(registerNowLink);
    }

    public void enterLoginDetails(LoginUserDo loginUserDo) throws InterruptedException {
        selenium.enterText(userNameField, loginUserDo.getUserName(), true);
        selenium.enterText(passwordField, loginUserDo.getPassword(), true);
        selenium.clickOn(loginButton);
    }
}
