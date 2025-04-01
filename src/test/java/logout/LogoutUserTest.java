package logout;

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

public class LogoutUserTest extends BaseTest {

    @Test(priority = 99)
    @Description("Verify that user able to logout when user click on logout link")
    public void verifyThatUserAbleToLogoutWhenUserClickOnLogoutLink() throws InterruptedException {
        HeaderPO headerPO = new HeaderPO(driver);
        LoginPO loginPO = new LoginPO(driver);
        LoginUserDo loginUserDO = new LoginUserData().getValidLoginData();
        HomePagePO homePo = new HomePagePO(driver);
        CommonPO commonPO = new CommonPO(driver);

//        Reporter.log("Step 1. Navigate to URl.");
        Allure.step("Step 1. Navigate to URl.");
        seleniumHelpers.navigateToPage(Constants.URL);

//        Reporter.log("Step 2: Click on the 'Sign In' link");
        Allure.step("Step 2: Click on the 'Sign In' link");
        headerPO.clickOnSignInLink();

//        Reporter.log("Step 3 - Verify that User navigate to the Sign-In Page");
        Allure.step("Step 3 - Verify that User navigate to the Sign In Page");
        Assert.assertTrue(loginPO.isLoginPageTextDisplayed(), Constants.USER_NOT_LOGGED_IN);

//        Reporter.log("Step 4: Enter valid username and password, then click on submit");
        Allure.step("Step 4: Enter valid username and password, then click on submit");
        loginPO.enterLoginDetails(loginUserDO);

//        Reporter.log("Step 5: Verify that user is logged in with correct user.");
        Allure.step("Step 5: Verify that user is logged in with correct user.");
        Assert.assertTrue(commonPO.isWelcomeContentDisplayed(), "User is not logged in with correct user!");

        Allure.step("Step 6: Verify that Logout link is displayed on Header");
        Assert.assertTrue(headerPO.isLogoutLinkDisplayed(), "Sign Out Link should be displayed to logout");

        Allure.step("Step 7: Click on 'Sign Out' link.");
        headerPO.clickOnSignOutLink();

        Allure.step("Step 8: Verify that home page is displayed.");
        Assert.assertTrue(homePo.isImageDisplayed(),"Logout should be successful");
    }
}
