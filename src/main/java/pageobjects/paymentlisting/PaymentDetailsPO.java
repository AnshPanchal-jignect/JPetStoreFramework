package pageobjects.paymentlisting;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePo;

public class PaymentDetailsPO extends BasePo {
    public PaymentDetailsPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "tbody>tr:first-of-type>th")
    private WebElement headerText;

    @FindBy(css = "input[value='Continue']")
    private WebElement continueButton;


    public String getHeaderText() {
        return selenium.getText(headerText);
    }

    public void clickOnContinueButton() throws InterruptedException {
        selenium.clickOn(continueButton);
    }
}
