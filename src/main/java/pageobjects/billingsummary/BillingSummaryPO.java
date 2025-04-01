package pageobjects.billingsummary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePo;

public class BillingSummaryPO extends BasePo {

    @FindBy(xpath = "//b[text()='Order']/ancestor::th/font[2]/b")
    private WebElement dateTime;
    @FindBy(xpath = "//a[text()='Confirm']")
    private WebElement confirmButton;

    public BillingSummaryPO(WebDriver driver) {
        super(driver);
    }

    public String getDate() {
        String date = selenium.getText(dateTime).trim(); // Ensure no leading/trailing spaces
        if (date.isEmpty()) {
            throw new IllegalStateException("Date element is empty or not found!");
        }

        String[] dateParts = date.split("\\s+"); // Splits by one or more spaces

        return dateParts.length > 0 ? dateParts[0] : ""; // Return first part if exists
    }

    public void clickOnConfirmButton() throws InterruptedException {
        selenium.clickOn(confirmButton);
    }
}
