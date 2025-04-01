package pageobjects.header;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePo;

public class HeaderPO extends BasePo {
    public HeaderPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#MenuContent > a:nth-child(3)")
    private WebElement signInLink;

    @FindBy(css = "input[name='signon']")
    private WebElement loginButton;

    @FindBy(css = "a[href*='signoff']")
    private WebElement signOutLink;

    @FindBy(css = "input[name='searchProducts']")
    private WebElement searchButton;

    @FindBy(xpath = "//input[@name='keyword']")
    private WebElement inputSearchTextField;


    public void clickOnSignInLink() throws InterruptedException {
        selenium.click(signInLink);
    }

    public boolean isLogoutLinkDisplayed() {
        return signOutLink.isDisplayed();
    }

    public void clickOnSignOutLink() throws InterruptedException {
        selenium.click(signOutLink);
    }

    public void searchProduct(String petName) throws InterruptedException {
        selenium.enterText(inputSearchTextField, petName, true);
        clickOnSearchButton();
    }

    public void clickOnSearchButton() throws InterruptedException {
        selenium.click(searchButton);
    }


}
