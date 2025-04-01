package search;

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
import pageobjects.petlisting.PetListingPO;
import utilities.Constants;

import java.util.Arrays;
import java.util.List;

public class SearchPetTest extends BaseTest {
    @Test(priority = 5)
    @Description("Verify that pet list is displayed when user search for pets.")
    public void verifyThatPetsListIsDisplayedWhenUserClickOnSearch() throws InterruptedException {

        LoginPO loginPO = new LoginPO(driver);
        LoginUserDo loginUserDO = new LoginUserData().getValidLoginData();
        HeaderPO headerPO = new HeaderPO(driver);
        PetListingPO petListingPO = new PetListingPO(driver);
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
        Assert.assertTrue(commonPO.isWelcomeContentDisplayed(), "User is not logged in!");

        String petNameSearch = "Fish";
        Allure.step("Step 6: Search with the "+petNameSearch+" Pet in the search input field and click on search button.");
        headerPO.searchProduct(petNameSearch);

        // Expected fish names found in search results
        String expectedProductId = "FI-";
        List<String> actualProductId = petListingPO.listOfActualPets();
        System.out.println("Array : " + Arrays.toString(actualProductId.toArray()));

        // Validating the list is not empty
        Allure.step("Step 7: Verify the pet catalog list is empty.");
        Assert.assertFalse(actualProductId.isEmpty(), "No products found for search: " + petNameSearch);

        Allure.step("Step 8: Verify the pet catalog starts with '" + expectedProductId+"'");
        Assert.assertTrue(actualProductId.stream().allMatch(s -> s.startsWith(expectedProductId)),
                "Not all elements start with " + expectedProductId + " for search: " + petNameSearch);
        /*
        // Pet prefixes for different pet categories
        Map<String, String> petPrefixes = Map.of(
                "Fish", "FI-",
                "Dogs", "DG-",
                "Reptiles", "RP-",
                "Cats", "CT-",
                "Birds", "BD-"
        );
        // List to capture failed assertions
        List<String> failedAssertions = new ArrayList<>();
        Allure.step("Step 6: Verify that pets are displayed when user search for pet.");
        petPrefixes.forEach((petCategory, expectedValue) -> {
            HeaderDO headerDO = new HeaderDO();
            Allure.step("Step 7: Enter the '" + petCategory + "' in the search bar and click on 'Search' button.");
            headerDO.setInputSearchField(petCategory);
            // Perform search
            try {
                headerPO.searchProduct(headerDO);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Key: " + petCategory);
            System.out.println("Value: " + expectedValue);
            Allure.step("Step 8: Verify that pets are displayed.");
            // Get actual product names
            List<String> actualValues = petCatalogListingPO.listOfActualPets();

            // Ensure the list is not empty
            if (actualValues.isEmpty()) {
                String errorMessage = "No products found for search: " + petCategory;
                System.out.println(errorMessage);
                failedAssertions.add(errorMessage);
            } else {
                Assert.assertTrue(actualValues.stream().allMatch(s -> s.startsWith(expectedValue)),
                        "Not all elements start with " + expectedValue + " for search: " + petCategory);


                Allure.step("Step: Verify that 'Return to Menu Link' is displayed.");
                String actualValueReturnMenuLink = petCatalogListingPO.getTextForReturnMenuLink();
                String expectedValueReturnMenuLink = "Return to Main Menu";
                Assert.assertEquals(actualValueReturnMenuLink, expectedValueReturnMenuLink, "Return to Main Menu Link is not displayed");
            }
        });
        if (!failedAssertions.isEmpty()) {
            Assert.fail("Test failed due to the following errors:\n" + String.join("\n", failedAssertions));
        }
*/
    }
}
