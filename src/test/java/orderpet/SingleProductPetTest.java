package orderpet;

import base.BaseTest;
import datafactory.login.LoginUserData;
import dataobjects.login.LoginUserDo;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.billingsummary.BillingSummaryPO;
import pageobjects.common.CommonPO;
import pageobjects.confirmation.ConfirmationPO;
import pageobjects.header.HeaderPO;
import pageobjects.home.HomePagePO;
import pageobjects.login.LoginPO;
import pageobjects.paymentlisting.PaymentDetailsPO;
import pageobjects.petlisting.PetListingPO;
import pageobjects.shoppingcart.ShoppingCartPO;
import pageobjects.subcategorylisting.SubCategoryListPO;
import utilities.Constants;
import utilities.JavaHelpers;

import java.time.LocalDate;
import java.util.ArrayList;

public class SingleProductPetTest extends BaseTest {

    @Test(priority = 6)
    @Description("Verify that user is able to order the pet product Successfully.")
    public void verifyThatUserIsAbleToOrderThePetProductSuccessfully() throws InterruptedException {

        HeaderPO headerPO = new HeaderPO(driver);
        LoginPO loginPO = new LoginPO(driver);
        LoginUserDo loginUserDO = new LoginUserData().getValidLoginData();
        HomePagePO homePo = new HomePagePO(driver);
        CommonPO commonPO = new CommonPO(driver);
        PetListingPO petListingPO = new PetListingPO(driver);
        SubCategoryListPO subCategoryListPO = new SubCategoryListPO(driver);
        ShoppingCartPO shoppingCartPO = new ShoppingCartPO(driver);
        PaymentDetailsPO paymentDetailsPO = new PaymentDetailsPO(driver);
        BillingSummaryPO billingSummaryPO = new BillingSummaryPO(driver);
        JavaHelpers javaHelpers = new JavaHelpers();
        ConfirmationPO confirmationPO = new ConfirmationPO(driver);

        Allure.step("Step 1. Navigate to URl.");
        seleniumHelpers.navigateToPage(Constants.URL);

        Allure.step("Step 2: Click on the 'Sign In' link");
        headerPO.clickOnSignInLink();

        Allure.step("Step 3 - Verify that User navigate to the Sign In Page");
        Assert.assertTrue(loginPO.isLoginPageTextDisplayed(), Constants.USER_NOT_LOGGED_IN);

        Allure.step("Step 4: Enter valid username and password, then click on submit");
        loginPO.enterLoginDetails(loginUserDO);

        Allure.step("Step 5: Verify that user is logged in with correct user.");
        Assert.assertTrue(commonPO.isWelcomeContentDisplayed(), "User is not logged with correct user!");

        Allure.step("Step 6: Click on any pet link and verify that user successfully navigated to pet listing page");
        String petName = "Reptiles";
        homePo.clickOnNavbarLinks(petName);
        String actualPetName = commonPO.getHeaderText();

        Allure.step("Step 7: Verify that user is successfully navigated to pet listing page");
        Assert.assertEquals(actualPetName, petName, "Actual pet name does not match expected!");

        Allure.step("Step 8: Click on any product ID according to sub category name and verify that user successfully navigated to sub category listing page");
        String subCategory = "Rattlesnake";
        String selectedPetProductId = petListingPO.clickOnPetAndGetProductId(subCategory);
        Allure.step("Step 9: Verify that sub category pet name matches when sub category list displays.");
        Assert.assertEquals(commonPO.getHeaderText(), subCategory, "Sub category pet name does not match expected");

        Allure.step("Step 10: Verify that list of 'Product ID' is matched with sub category product.");
        ArrayList<String> listOfSubCategoryPets = subCategoryListPO.getProductID();
        Assert.assertTrue(listOfSubCategoryPets.stream().allMatch(str -> str.equals(selectedPetProductId)), "All product ids not matched");

        Allure.step("Verify that 'Item Id' text field, 'Description' text field, 'List Price' amount and 'Add To Cart' button is displayed and get the details and click on 'Add to Cart' button");
        Allure.step("Step 12: Verify that 'Item Id' text is displayed in sub Category section.");
        Assert.assertTrue(subCategoryListPO.checkTableDetailsPresent(2, 1), "Item Id text is not present");

        Allure.step("Step 13: Verify that 'Description' text is displayed in sub Category section.");
        Assert.assertTrue(subCategoryListPO.checkTableDetailsPresent(2, 3), "Description text is not present");

        Allure.step("Step 14: Verify that 'List Price' text is displayed in sub Category section.");
        Assert.assertTrue(subCategoryListPO.checkTableDetailsPresent(2, 4), "List Price amount is not present");

        String itemId = subCategoryListPO.getTableDetails(2, 1);
        System.out.println("Item id: " + itemId);

        String description = subCategoryListPO.getTableDetails(2, 3);
        System.out.println("Subcategory description: " + description);

        String listPriceIn$ = subCategoryListPO.getTableDetails(2, 4);
        String listPriceFormated = listPriceIn$.replace("$", "").trim();
        System.out.println("Subcategory list price formated : " + listPriceFormated);

        Allure.step("Step 15: Verify that 'Add To Cart' button is displayed in sub Category section.");
        Assert.assertTrue(subCategoryListPO.checkTableDetailsPresent(2, 5), "Add To Cart button is not displayed in sub Category section.");

        Allure.step("Step 16: Click on 'Add To Cart' button in sub Category section.");
        subCategoryListPO.clickOnAddToCartButton(2, 5);

        Allure.step("Step 17: Verify that page is redirected to shopping cart");
        String shoppingCartName = "Shopping Cart";

        Allure.step("Step 18: Verify that header text displayed the 'Shopping Cart' name when user is on shopping cart page.");
        Assert.assertEquals(commonPO.getHeaderText(), shoppingCartName, "Header text for shopping cart is mismatch.");

        Allure.step("Step 12: Verify that 'Item Id' text is displayed in shopping cart.");
        Assert.assertTrue(shoppingCartPO.checkTableDetailsPresent(2, 1));

        Allure.step("Step 13: Verify that 'Product Id' text is matched in shopping cart page with the sub category section.");
        Assert.assertEquals(shoppingCartPO.getTableDetails(2, 2), selectedPetProductId, "Product Id is not correct in the Shopping cart page");

        Allure.step("Step 14: Verify that 'Description' text is matched in shopping cart page with the sub category section.");
        Assert.assertEquals(shoppingCartPO.getTableDetails(2, 3), description, "Description is not correct in the Shopping cart page");

        Allure.step("Step 15: Verify that 'List price' amount is matched in shopping cart page with the sub category section.");
        Assert.assertEquals(shoppingCartPO.getTableDetails(2, 6), listPriceIn$, "Product Id is not correct in the Shopping cart page");

        Allure.step("Step 16: Get the 'Total Cost' price from the shopping cart.");
        String totalCostIn$ = shoppingCartPO.getTableDetails(2, 7);
        String totalCost = totalCostIn$.replace("$", "").trim();

        //Converting into float
        float totalCostFormat = Float.parseFloat(totalCost);
        System.out.println("Total Cost in shopping cart: " + totalCostFormat);

        Allure.step("Step 17: Get the 'Sub Total' price from the shopping cart.");
        String subTotalIn$ = shoppingCartPO.getTableDetails(3, 1);

        Allure.step("Step 18: Format the 'Sub Total' price from the shopping cart.");
        String subTotalFormated = subTotalIn$.replace("Sub Total:", "").replace("$", "").trim();
        System.out.println("Sub total Formated:" + subTotalFormated);

        Allure.step("Step 19: Get the quantity from the Shopping cart");
        String qty = shoppingCartPO.getQuantityDetails(2, 5);
        float qtyFormated = Float.parseFloat(qty);
        System.out.println("Quantity of shopping cart: " + qtyFormated);

        Allure.step("Step 20: Verify that 'Total Cost' is as per quantity displayed.");
        Assert.assertEquals(totalCostFormat, Float.parseFloat(listPriceFormated) * qtyFormated, "Total Price is not displayed as per quantity in shopping page.");

        Allure.step("Step 21: Verify that total cost in shopping cart");
        Assert.assertEquals(shoppingCartPO.getAdditionOfTotalCost(), Float.parseFloat(subTotalFormated), "Sub Total is not matched.");

        Allure.step("Step 22: Click on 'Proceed To CheckOut' button.");
        shoppingCartPO.clickOnProceedToCheckoutButton();

        Allure.step("Step 23: Verify that user successfully navigated to Payment details page and click on continue button");
        String paymentDetailsHeader = "Payment Details";
        Assert.assertEquals(paymentDetailsPO.getHeaderText(), paymentDetailsHeader, "Header text is not matched");

        Allure.step("Step 24: Click on 'Continue' button.");
        paymentDetailsPO.clickOnContinueButton();

        Allure.step("Step 25: Verify that user successfully navigated to Billing summary page and click on confirm button");
        Assert.assertEquals(billingSummaryPO.getDate(), javaHelpers.getDateTimeFormatter("yyyy/MM/dd", LocalDate.now()), "Date is not matched");

        Allure.step("Step 26: Click on 'Confirm' button.");
        billingSummaryPO.clickOnConfirmButton();

        Allure.step("Step 27: Verify that confirmation message displayed when product is submitted.");
        String successMessage = "Thank you, your order has been submitted.";
        Assert.assertEquals(confirmationPO.getSuccessMessage(), successMessage, "The success message is not correct");

        Allure.step("Step 28: Verify that pet description is displayed correct.");
        Assert.assertEquals(confirmationPO.getTableData(2, 2), description, "Description is not matched.");

        Allure.step("Step 29: Verify that item id is displayed correct.");
        Assert.assertEquals(confirmationPO.getTableData(2, 1), itemId, "Item id mismatched.");

        Allure.step("Step 30: Verify that price is displayed correct.");
        Assert.assertEquals(confirmationPO.getTableData(2, 4), listPriceIn$, "Price is mismatch");

        Allure.step("Step 31: Verify that Quantity displayed correct.");
        Assert.assertEquals(confirmationPO.getTableData(2, 3), qty, "Quantity is mismatch");

        Allure.step("Step 32: Verify that sub total displayed correct.");
        Assert.assertEquals(confirmationPO.getTableData(2, 5), totalCostIn$, "SubTotal is mismatch");

        Allure.step("Step 33: Verify that addition of total price is displayed correct.");
        Assert.assertEquals(confirmationPO.getAdditionOfTotalCost(), confirmationPO.getSubTotal(), "Total price mismatch");

    }

    @Test(priority = 7)
    @Description("Verify that user is able to update the qty and order the pet product Successfully.")
    public void verifyThatUserIsAbleToUpdateTheQuantityAndOrderThePetProductSuccessfully() throws InterruptedException {
        HeaderPO headerPO = new HeaderPO(driver);
        LoginPO loginPO = new LoginPO(driver);
        LoginUserDo loginUserDO = new LoginUserData().getValidLoginData();
        HomePagePO homePo = new HomePagePO(driver);
        PetListingPO petListingPO = new PetListingPO(driver);
        SubCategoryListPO subCategoryListPO = new SubCategoryListPO(driver);
        ShoppingCartPO shoppingCartPO = new ShoppingCartPO(driver);
        PaymentDetailsPO paymentDetailsPO = new PaymentDetailsPO(driver);
        BillingSummaryPO billingSummaryPO = new BillingSummaryPO(driver);
        ConfirmationPO confirmationPO = new ConfirmationPO(driver);
        JavaHelpers javaHelpers = new JavaHelpers();
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
        Assert.assertTrue(commonPO.isWelcomeContentDisplayed(), "User is not logged with correct user!");

        Allure.step("Step 6: Click on any pet link and verify that user successfully navigated to pet listing page");
        String petName = "Birds";
        homePo.clickOnNavbarLinks(petName);
        String actualPetName = commonPO.getHeaderText();

        Allure.step("Step 7: Verify that user is successfully navigated to pet listing page");
        Assert.assertEquals(actualPetName, petName, "Actual pet name does not match expected!");

        Allure.step("Step 8: Click on any product ID according to sub category name and verify that user successfully navigated to sub category listing page");
        String subCategory = "Finch";
        String selectedPetProductId = petListingPO.clickOnPetAndGetProductId(subCategory);

        Allure.step("Step 9: Verify that sub category pet name matches when sub category list displays.");
        Assert.assertEquals(commonPO.getHeaderText(), subCategory, "Sub category pet name does not match expected");

        Allure.step("Step 10: Verify that list of product id is matched with sub category product.");
        ArrayList<String> listOfSubCategoryPets = subCategoryListPO.getProductID();
        Assert.assertTrue(listOfSubCategoryPets.stream().allMatch(str -> str.equals(selectedPetProductId)), "All product ids not matched");


        Allure.step("Verify that 'Item Id' text field, 'Description' text field, 'List Price' amount and 'Add To Cart' button is displayed and get the details and click on 'Add to Cart' button");
        Allure.step("Step 12: Verify that 'Item Id' text is displayed in sub Category section.");
        Assert.assertTrue(subCategoryListPO.checkTableDetailsPresent(2, 1), "Item Id text is not present");

        Allure.step("Step 13: Verify that 'Description' text is displayed in sub Category section.");
        Assert.assertTrue(subCategoryListPO.checkTableDetailsPresent(2, 3), "Description text is not present");

        Allure.step("Step 14: Verify that 'List Price' text is displayed in sub Category section.");
        Assert.assertTrue(subCategoryListPO.checkTableDetailsPresent(2, 4), "List Price amount is not present");

        String itemId = subCategoryListPO.getTableDetails(2, 1);
        System.out.println("Item id: " + itemId);

        String description = subCategoryListPO.getTableDetails(2, 3);
        System.out.println("Subcategory description: " + description);

        String listPriceIn$ = subCategoryListPO.getTableDetails(2, 4);
        String listPriceFormated = listPriceIn$.replace("$", "").trim();
        System.out.println("Subcategory list price formated : " + listPriceFormated);

        Allure.step("Step 15: Verify that 'Add To Cart' button is displayed in sub Category section.");
        Assert.assertTrue(subCategoryListPO.checkTableDetailsPresent(2, 5), "Add To Cart button is not displayed in sub Category section.");

        Allure.step("Step 16: Click on 'Add To Cart' button in sub Category section.");
        subCategoryListPO.clickOnAddToCartButton(2, 5);

        Allure.step("Step 17: Verify that page is redirected to shopping cart");
        String shoppingCartName = "Shopping Cart";

        Allure.step("Step 18: Verify that header text displayed the 'Shopping Cart' name when user is on shopping cart page.");
        Assert.assertEquals(commonPO.getHeaderText(), shoppingCartName, "Header text for shopping cart is mismatch.");

        int updateQtyTo = 3;
        Allure.step("Step 17: Verify that additionally quantity is updated when previous qty is cleared and new qty is entered.");
        shoppingCartPO.enterQuantity(itemId, updateQtyTo);

        Allure.step("Step 18: Click on 'Update' button.");
        shoppingCartPO.clickOnUpdateButton();

        Allure.step("Step 19: Verify list price,total cost and sub total on shopping cart page");
        Assert.assertEquals(shoppingCartPO.getListPrice(itemId), listPriceIn$, "List price mismatch.");

        String totalCostPetIdIn$ = shoppingCartPO.getPetTableData(itemId, 6);
        String listPrice = listPriceIn$.substring(1);
        String totalPrice = totalCostPetIdIn$.substring(1);

        float subTotal = shoppingCartPO.getSubTotal();
//        System.out.println("Sub Total: " + subTotal);
        Allure.step("Step 20: Verify that total price in shopping cart.");
        Assert.assertEquals(Float.parseFloat(totalPrice), Float.parseFloat(listPrice) * updateQtyTo);

        Allure.step("Step 21: Verify that total cost in shopping cart");
        Assert.assertEquals(shoppingCartPO.getAdditionOfTotalCost(), subTotal, "Sub Total is not matched.");

        Allure.step("Step 22: Verify that item id is displayed correct.");
        Assert.assertEquals(shoppingCartPO.getItemId(2), itemId, "Item id is not matching");

        Allure.step("Step 23: Click on 'Proceed To CheckOut' button.");
        shoppingCartPO.clickOnProceedToCheckoutButton();

        Allure.step("Step 24: Verify that user successfully navigated to Payment details page and click on continue button");
        String paymentDetailsHeader = "Payment Details";
        Assert.assertEquals(paymentDetailsPO.getHeaderText(), paymentDetailsHeader, "Header text is not matched");

        Allure.step("Step 25: Click on 'Continue' button.");
        paymentDetailsPO.clickOnContinueButton();

        Allure.step("Step 26: Verify that user successfully navigated to Billing summary page and click on confirm button");
        Assert.assertEquals(billingSummaryPO.getDate(), javaHelpers.getDateTimeFormatter("yyyy/MM/dd", LocalDate.now()), "Date is not matched");

        Allure.step("Step 27: Click on 'Confirm' button.");
        billingSummaryPO.clickOnConfirmButton();

        Allure.step("Step 28: Verify that confirmation displayed when product is submitted.");
        String successMessage = "Thank you, your order has been submitted.";
        Assert.assertEquals(confirmationPO.getSuccessMessage(), successMessage, "The success message is not correct");
        Allure.step("Step 28: Verify that pet description is displayed correct.");
        Assert.assertEquals(confirmationPO.getTableData(2, 2), description, "Description is not matched.");

        Allure.step("Step 29: Verify that item id is displayed correct.");
        Assert.assertEquals(confirmationPO.getTableData(2, 1), itemId, "Item id mismatched.");

        Allure.step("Step 30: Verify that price is displayed correct.");
        Assert.assertEquals(confirmationPO.getTableData(2, 4), listPriceIn$, "Price is mismatch");

        Allure.step("Step 31: Verify that Quantity displayed correct.");
        Assert.assertEquals(confirmationPO.getTableData(2, 3), String.valueOf(updateQtyTo), "Quantity is mismatch");

        Allure.step("Step 32: Verify that sub total displayed correct.");
        Assert.assertEquals(confirmationPO.getTableData(2, 5), totalCostPetIdIn$, "SubTotal is mismatch");

        Allure.step("Step 33: Verify that addition of total price is displayed correct.");
        Assert.assertEquals(confirmationPO.getAdditionOfTotalCost(), confirmationPO.getSubTotal(), "Total price mismatch");
    }

    @Test(priority = 8)
    @Description("Verify that user is able to remove the product from cart successfully.")
    public void verifyThatUserIsAbleToRemoveTheProductFromCartSuccessfully() throws InterruptedException {
        HeaderPO headerPO = new HeaderPO(driver);
        LoginPO loginPO = new LoginPO(driver);
        LoginUserDo loginUserDO = new LoginUserData().getValidLoginData();
        HomePagePO homePo = new HomePagePO(driver);
        CommonPO commonPO = new CommonPO(driver);
        PetListingPO petListingPO = new PetListingPO(driver);
        SubCategoryListPO subCategoryListPO = new SubCategoryListPO(driver);
        ShoppingCartPO shoppingCartPO = new ShoppingCartPO(driver);

        Allure.step("Step 1. Navigate to URl.");
        seleniumHelpers.navigateToPage(Constants.URL);

        Allure.step("Step 2: Click on the 'Sign In' link");
        headerPO.clickOnSignInLink();

        Allure.step("Step 3 - Verify that User navigate to the Sign In Page");
        Assert.assertTrue(loginPO.isLoginPageTextDisplayed(), Constants.USER_NOT_LOGGED_IN);

        Allure.step("Step 4: Enter valid username and password, then click on submit");
        loginPO.enterLoginDetails(loginUserDO);

        Allure.step("Step 5: Verify that user is logged in with correct user.");
        Assert.assertTrue(commonPO.isWelcomeContentDisplayed(), "User is not logged with correct user!");

        Allure.step("Step 6: Click on any pet link and verify that user successfully navigated to pet listing page");
        String petName = "Cats";

        homePo.clickOnNavbarLinks(petName);
        String actualPetName = commonPO.getHeaderText();

        Allure.step("Step 7: Verify that user is successfully navigated to pet listing page");
        Assert.assertEquals(actualPetName, petName, "Actual pet name does not match expected!");

        Allure.step("Step 8: Click on any product ID according to sub category name and verify that user successfully navigated to sub category listing page");
        String subCategory = "Persian";
        String selectedPetProductId = petListingPO.clickOnPetAndGetProductId(subCategory);

        Allure.step("Step 9: Verify that sub category pet name matches when sub category list displays.");
        Assert.assertEquals(commonPO.getHeaderText(), subCategory, "Sub category pet name does not match expected");

        Allure.step("Step 10: Verify that list of product id is matched with sub category product.");
        ArrayList<String> listOfSubCategoryPets = subCategoryListPO.getProductID();
        Assert.assertTrue(listOfSubCategoryPets.stream().allMatch(str -> str.equals(selectedPetProductId)), "All product ids not matched");

        //Get the "Adult Male Persian" item id.
        String petItemExpectedId = subCategoryListPO.getTableDetails(3, 1);

//        String listPriceIn$ = subCategoryListPO.getListPrice(petItemExpectedId);
        String petDescription = subCategoryListPO.getTableDetails(3, 3);

        Allure.step("Step 12: Click on 'Add To Cart' button.");
        subCategoryListPO.clickOnAddToCartButton(3, 5);

        Allure.step("Step 13: Verify that page is redirected to shopping cart");
        String shoppingCartName = "Shopping Cart";

        Allure.step("Step 14: Verify that header text displayed correct in the 'Shopping Cart' name when user is on shopping cart page.");
        Assert.assertEquals(commonPO.getHeaderText(), shoppingCartName, "Header text for shopping cart is mismatch.");

        Allure.step("Step 15: Verify that product id is displayed correct in shopping cart when product id matched with subcategory page.");
        Assert.assertEquals(shoppingCartPO.getProductID(petItemExpectedId), selectedPetProductId, "Product Id is not matched.");

        Allure.step("Step 16: Verify that description name of the pet matched when description is matched with sub category page.");
        Assert.assertEquals(shoppingCartPO.getDescription(petItemExpectedId), petDescription, "Pet Description mismatch");

        Allure.step("Step 17: Verify that 'Remove' button is displayed on the shopping cart.");
        Assert.assertTrue(shoppingCartPO.removeButtonEnabled(petItemExpectedId));

        Allure.step("Step 18: Click on 'Remove' button.");
        shoppingCartPO.clickOnRemoveButton(petItemExpectedId);

        String cartEmptyMessage = "Your cart is empty.";

        Allure.step("Step 19: Verify that product is removed from the shopping cart.");
        Assert.assertEquals(shoppingCartPO.getEmptyCartText(), cartEmptyMessage, "Shopping cart is not empty");
    }
}
