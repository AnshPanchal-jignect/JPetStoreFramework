package pageobjects.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePo;

public class CommonPO extends BasePo {
    public CommonPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@id='BackLink']/a")
    private WebElement returnToMenuLink;

    @FindBy(css = "div[id='Catalog'] h2")
    private WebElement headerText;

    @FindBy(css = "div[id='WelcomeContent']")
    private WebElement welcomeMessage;

    public void clickOnReturnToMainMenuButton() throws InterruptedException {
        selenium.click(returnToMenuLink);
    }

    public String getHeaderText() {
        return selenium.getText(headerText);
    }

    public boolean isWelcomeContentDisplayed() {
        return welcomeMessage.isDisplayed();
    }
}
