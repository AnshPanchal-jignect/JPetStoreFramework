package registration;

import base.BaseTest;
import datafactory.registration.RegistrationData;
import dataobjects.login.LoginUserDo;
import dataobjects.registration.RegistrationDo;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.common.CommonPO;
import pageobjects.header.HeaderPO;
import pageobjects.home.HomePagePO;
import pageobjects.login.LoginPO;
import pageobjects.registration.RegistrationPO;
import utilities.Constants;

public class RegistrationTest extends BaseTest {

    @Test(priority = 3)
    @Description("Verify that user Register successfully.")
    public void verifyThatUserRegisterSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LoginPO loginPO = new LoginPO(driver);
        RegistrationPO registrationPO = new RegistrationPO(driver);
        HomePagePO homePo = new HomePagePO(driver);
        LoginUserDo loginUserDo = new LoginUserDo();
        CommonPO commonPO = new CommonPO(driver);

        RegistrationDo registrationDo = new RegistrationData().getRegisterCustomerData();


        Allure.step("Step 1. Navigate to URl.");
        seleniumHelpers.navigateToPage(Constants.URL);


        Allure.step("Step 2: Click on the 'Sign In' link.");
        headerPO.clickOnSignInLink();

        Allure.step("Step 3 - Verify that User navigate to the Sign In Page");
        Assert.assertTrue(loginPO.isLoginPageTextDisplayed(), Constants.USER_NOT_LOGGED_IN);

        Allure.step("Step 4: Click on the 'Register Now!' link");
        loginPO.clickOnRegisterButton();

        Allure.step("Step 5: Verify that registration page displayed when user click on 'register' button");
        Assert.assertTrue(registrationPO.isUserInformationTextDisplayed(), "Registration page is not displayed");

        Allure.step("Step 6: Verify that 'Save Account Information' button is displayed");
        Assert.assertTrue(registrationPO.isSaveAccountInformationButtonDisplayed(), "'Save Account Information' button should be displayed");

        Allure.step("Step 7: Enter Valid Registration Details and Tap on 'Save Account Information' Button");
        registrationPO.enterValidRegistrationDetails(registrationDo);

        Assert.assertTrue(homePo.isImageDisplayed(), "Registration page should be redirected to login window.");

        Allure.step("Step 8: Click on the 'Sign In' link");
        headerPO.clickOnSignInLink();
        Thread.sleep(2000);
        Allure.step("Step 9: Enter the username and password and click on 'Login' button");

        String user = registrationDo.getUserId();
        System.out.println("Register Username:" + user);
        loginUserDo.setUserName(user);

        String pass = registrationDo.getPasswordRegistration();
        System.out.println("Register Password:" + pass);
        loginUserDo.setPassword(pass);

        loginPO.enterLoginDetails(loginUserDo);

        Allure.step("Step 10: Verify that user is logged in with correct user.");
        Assert.assertTrue(commonPO.isWelcomeContentDisplayed(), "User is not logged with correct user!");

        Allure.step(("Step 11: Verify that Logout link is displayed on Header"));
        Assert.assertTrue(headerPO.isLogoutLinkDisplayed(), "Sign Out Link should be displayed to logout");

        Allure.step(("Step 12: Click on 'Sign Out' Link."));
        headerPO.clickOnSignOutLink();

        Allure.step("Step 13: Verify that home page is displayed when user logged out.");
        Assert.assertTrue(homePo.isImageDisplayed(),"Logout should be successful");
    }

    @Test(priority = 4)
    @Description("Verify that validation message is displayed for empty form when user click on 'Save Account Information' button.")
    public void VerifyThatValidationMessageIsDisplayedWhenUserClickOnSaveAccountInformationButton() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LoginPO loginPO = new LoginPO(driver);
        RegistrationPO registrationPO = new RegistrationPO(driver);

        Allure.step("Step 1. Navigate to URl.");
        seleniumHelpers.navigateToPage(Constants.URL);

        Allure.step("Step 2: Click on the 'Sign In' link.");
        headerPO.clickOnSignInLink();

        Allure.step("Step 3 - Verify that User navigate to the Sign In Page");
        Assert.assertTrue(loginPO.isLoginPageTextDisplayed(), Constants.USER_NOT_LOGGED_IN);

        Allure.step("Step 4: Click on the 'Register Now!' link");
        loginPO.clickOnRegisterButton();

        Allure.step("Step 5: Verify that registration page displayed when user click on 'register' button");
        Assert.assertTrue(registrationPO.isUserInformationTextDisplayed(), "Registration page is not displayed");

        Allure.step("Step 6: Verify that 'Save Account Information' button is displayed");
        Assert.assertTrue(registrationPO.isSaveAccountInformationButtonDisplayed(), "'Save Account Information' button should be displayed");

        Allure.step("Step 7: Click on 'Save Account Information' button.");
        registrationPO.clickOnSaveButton();

        Allure.step("Step 8: Verify that 'Save Account Information' button is disabled");
        Assert.assertTrue(registrationPO.isSaveButtonDisabled(),"The save button should be disabled when registration form is empty.");
    }
}
