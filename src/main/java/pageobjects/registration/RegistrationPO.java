package pageobjects.registration;

import dataobjects.registration.RegistrationDo;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePo;

public class RegistrationPO extends BasePo {
    public RegistrationPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h3[contains(text(),'User Information')]/following-sibling::table//td[contains(text(),'User ID')]/following-sibling::td/input")
    private WebElement userIdTextBoxField;

    @FindBy(xpath = "//td[contains(text(),'New password:')]/following::input[@name='password']")
    private WebElement passwordRegistrationTextBoxField;

    @FindBy(xpath = "//h3[contains(text(),'Account Information')]//preceding::td[text()='Repeat password:']//following::input[@name='repeatedPassword']")
    private WebElement repeatPasswordTextBoxField;

    @FindBy(css = "input[name='account.firstName']")
    private WebElement firstNameTextBoxField;

    @FindBy(css = "input[name='account.lastName']")
    private WebElement lastNameTextBoxField;

    @FindBy(css = "input[name='account.email']")
    private WebElement emailTextBoxField;

    @FindBy(css = "input[name='account.phone']")
    private WebElement phoneNumberTextBoxField;

    @FindBy(css = "input[name='account.address1']")
    private WebElement address1TextBoxField;

    @FindBy(xpath = "//h3[contains(text(),'Account Information')]/following-sibling::table//td[contains(text(),'Address 2:')]/following-sibling::td/input")
    private WebElement address2TextBoxField;

    @FindBy(xpath = "//h3[contains(text(),'Account Information')]/following-sibling::table//td[contains(text(),'City:')]/following-sibling::td/input")
    private WebElement cityTextBoxField;

    @FindBy(xpath = "//h3[contains(text(),'Account Information')]/following-sibling::table//td[contains(text(),'State:')]/following-sibling::td/input")
    private WebElement stateTextBoxField;

    @FindBy(xpath = "//h3[contains(text(),'Account Information')]/following-sibling::table//td[contains(text(),'Zip:')]/following-sibling::td/input")
    private WebElement zipTextBoxField;

    @FindBy(xpath = "//h3[contains(text(),'Account Information')]/following-sibling::table//td[contains(text(),'Country:')]/following-sibling::td/input")
    private WebElement countryTextBoxField;

    @FindBy(xpath = "//h3[contains(text(),'Profile Information')]/following-sibling::table//td[contains(text(),'Enable MyList')]/following-sibling::td/input")
    private WebElement enableMyListCheckbox;

    @FindBy(xpath = "//h3[contains(text(),'Profile Information')]/following-sibling::table//td[contains(text(),'Enable MyBanner')]/following-sibling::td/input")
    private WebElement enableMyBannerCheckbox;

    @FindBy(xpath = "//h3[contains(text(),'Profile Information')]/following::input[@value='Save Account Information']")
    private WebElement saveAccountInformationButton;

    @FindBy(xpath = "//h3[contains(text(), 'User Information')]")
    private WebElement userInformationText;

    public boolean isUserInformationTextDisplayed() {
        try {
            selenium.waitTillElementIsVisible(userInformationText);
            return userInformationText.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void enterValidRegistrationDetails(RegistrationDo registrationDo) throws InterruptedException {
        selenium.enterText(userIdTextBoxField, registrationDo.getUserId(), true);
        selenium.enterText(passwordRegistrationTextBoxField, registrationDo.getPasswordRegistration(), true);
        selenium.enterText(repeatPasswordTextBoxField, registrationDo.getRepeatPassword(), true);
        selenium.enterText(firstNameTextBoxField, registrationDo.getFirstName(), true);
        selenium.enterText(lastNameTextBoxField, registrationDo.getLastName(), true);
        selenium.enterText(emailTextBoxField, registrationDo.getEmail(), true);
        selenium.enterText(phoneNumberTextBoxField, registrationDo.getPhoneNumber(), true);
        selenium.enterText(address1TextBoxField, registrationDo.getAddress1(), true);
        selenium.enterText(address2TextBoxField, registrationDo.getAddress2(), true);
        selenium.enterText(cityTextBoxField, registrationDo.getCity(), true);
        selenium.enterText(stateTextBoxField, registrationDo.getState(), true);
        selenium.enterText(zipTextBoxField, registrationDo.getZip(), true);
        selenium.enterText(countryTextBoxField, registrationDo.getCountry(), true);

        selenium.click(saveAccountInformationButton);
    }

    public boolean isSaveAccountInformationButtonDisplayed() {
        return selenium.isElementPresent(saveAccountInformationButton);
    }

    public void clickOnSaveButton() throws InterruptedException {
        selenium.click(saveAccountInformationButton);
    }

    public boolean isSaveButtonDisabled(){
        selenium.waitTillElementIsVisible(saveAccountInformationButton);
        return !saveAccountInformationButton.isEnabled();
    }

}
