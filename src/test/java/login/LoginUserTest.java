package login;

import base.BaseTest;
import datafactory.login.LoginUserData;
import dataobjects.login.LoginUserDo;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.common.CommonPO;
import pageobjects.header.HeaderPO;
import pageobjects.home.HomePagePO;
import pageobjects.login.LoginPO;
import utilities.Constants;

public class LoginUserTest extends BaseTest {

    @Test(priority = 1)
    @Description("Verify that user is logged in when user enter valid credentials")
    public void verifyThatUserIsLoggedInWhenUserEnterValidCredentials() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LoginPO loginPO = new LoginPO(driver);
        LoginUserDo loginUserDO = new LoginUserData().getValidLoginData();
        CommonPO commonPO = new CommonPO(driver);

        Allure.step("Step 1. Navigate to URl.");
        seleniumHelpers.navigateToPage(Constants.URL);

        Allure.step("Step 2: Click on the 'Sign In' link");
        headerPO.clickOnSignInLink();

        Allure.step("Step 3 - Verify that User navigate to the Sign In Page");
        Assert.assertTrue(loginPO.isLoginPageTextDisplayed(), Constants.USER_NOT_LOGGED_IN);

        Allure.step("Step 4: Enter valid username and password, then click on submit");
        loginPO.enterLoginDetails(loginUserDO);

        Allure.step("Step 5: Verify that user is logged in with correct user.");
        Assert.assertTrue(commonPO.isWelcomeContentDisplayed(), "User is not logged with correct user!!!");
    }

    @Test(priority = 2)
    @Description("Verify that user is not able to login when user enter invalid credentials")
    public void verifyThatUserIsNotAbleToLoginWhenUserEnterInValidCredentials() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LoginUserDo loginUserDo = new LoginUserData().getInvalidLoginData();
        LoginPO loginPO = new LoginPO(driver);

        Allure.step("Step 1. Navigate to URl.");
        seleniumHelpers.navigateToPage(Constants.URL);

        Allure.step("Step 2: Click on the 'Sign In' link");
        headerPO.clickOnSignInLink();

        Allure.step("Step 3 - Verify that User navigate to the Sign In Page");
        Assert.assertTrue(loginPO.isLoginPageTextDisplayed(), Constants.USER_NOT_LOGGED_IN);

        Allure.step("Step 4: Enter Invalid username and password, then click on submit.");
        loginPO.enterLoginDetails(loginUserDo);

        Allure.step("Step 5: Verify that error message is displayed.");
        Assert.assertTrue(loginPO.isLoginErrorMessageDisplayed(),"Error message not displayed for invalid login attempt.");

    }
}
