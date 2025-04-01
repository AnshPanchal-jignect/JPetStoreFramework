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
import java.util.List;


public class MultipleOrderPetTest extends BaseTest {

    @Test(priority = 9)
    @Description("Verify that user is able to order multiple pet product randomly.")
    public void verifyThatUserIsAbleToOrderMultiplePetProductSuccessfully() throws InterruptedException {
        HeaderPO headerPO = new HeaderPO(driver);
        LoginPO loginPO = new LoginPO(driver);
        LoginUserDo loginUserDO = new LoginUserData().getValidLoginData();
        HomePagePO homePo = new HomePagePO(driver);
        CommonPO commonPO = new CommonPO(driver);
        PetListingPO petListingPO = new PetListingPO(driver);
        SubCategoryListPO subCategoryListPO = new SubCategoryListPO(driver);
        ShoppingCartPO shoppingCartPO = new ShoppingCartPO(driver);
        JavaHelpers javaHelpers = new JavaHelpers();
        PaymentDetailsPO paymentDetailsPO = new PaymentDetailsPO(driver);
        BillingSummaryPO billingSummaryPO = new BillingSummaryPO(driver);
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

//        List<String> navbarPetNameList = homePo.getListOfPetNameNavbar();
//        System.out.println("List of pet Name: " + navbarPetNameList);

        Allure.step("Step 6: Get the random pet name from navbar to click.");
        String randomPetName = homePo.getRandomPetCategory();
        System.out.println("Random Pet Name: " + randomPetName);

        Allure.step("Step 7: Click on the 'Navbar' link randomly");
        homePo.clickOnRandomNavbarLinks(randomPetName);

        Allure.step("Step 8: Verify that Pet Name displayed correct on header.");
        Assert.assertEquals(commonPO.getHeaderText(), randomPetName, "Pet Name " + randomPetName + " should be displayed correct.");

        Allure.step("Step 9: Click on the 'Product Id' link and Get the ProductId");
        String petProductId = petListingPO.clickOnRandomPetAndGetProductId();

        Allure.step("Step 10: Get the subcategory name from the header.");
        String subCategoryHeaderName = commonPO.getHeaderText();
        System.out.println("Sub category header name: " + subCategoryHeaderName);

        Allure.step("Step 11: Verify that list of 'Product Id' is matched with sub category 'Product Id'.");
        ArrayList<String> listOfSubCategoryProductId = subCategoryListPO.getProductID();
        Assert.assertTrue(listOfSubCategoryProductId.stream().allMatch(str -> str.equals(petProductId)), "Product Id is not matched with " + petProductId);

        Allure.step("Step 12: Get the 'Item Id' text field, 'Description' text field, 'List Price' amount and click on 'Add to Cart' button");
        String randomItemId = subCategoryListPO.getRandomItemId();
        System.out.println(randomItemId);

        String description = subCategoryListPO.getDescription(randomItemId);
        System.out.println("Description :" + description);

        String listPriceIn$ = subCategoryListPO.getListPrice(randomItemId);
        String listPriceFormated = listPriceIn$.replace("$", "").trim();
        System.out.println("List price formated: " + listPriceFormated);

        Allure.step("Step 13: Click on the 'Add To Cart' button randomly");
        subCategoryListPO.clickOnAddToCartButton(randomItemId);

        Allure.step("Step 14: Verify that page is redirected to shopping cart");
        String shoppingCartName = "Shopping Cart";

        Allure.step("Step 15: Verify that header text is displayed the 'Shopping Cart' name when user is on shopping cart page.");
        Assert.assertEquals(commonPO.getHeaderText(), shoppingCartName, "Header text for shopping cart is mismatch.");

        Allure.step("Step 16: Verify that 'Item Id' is displayed correctly when user is on shopping cart page.");
        Assert.assertEquals(shoppingCartPO.getTableDetails(2, 1), randomItemId, "Item id is not displayed correctly");

        Allure.step("Step 17: Verify that 'Product Id' is displayed correctly when user is on shopping cart page.");
        Assert.assertEquals(shoppingCartPO.getTableDetails(2, 2), petProductId, "Item id is not displayed correctly");

        Allure.step("Step 18: Verify that 'Description' text is matched in shopping cart page with the sub category section.");
        Assert.assertEquals(shoppingCartPO.getTableDetails(2, 3), description, "Description is not correct in the Shopping cart page");

        Allure.step("Step 19: Verify that 'List price' amount is matched in shopping cart page with the sub category section.");
        Assert.assertEquals(shoppingCartPO.getTableDetails(2, 6), listPriceIn$, "Product Id is not correct in the Shopping cart page");

        Allure.step("Step 20: Get the 'Total Cost' price from the shopping cart.");
        String totalCostIn$ = shoppingCartPO.getTableDetails(2, 7);

        Allure.step("Step 21: Format the total cost from the shopping cart.");
        String totalCostFormatting = totalCostIn$.replace("$", "").trim();

        Allure.step("Step 22: Format the total cost into float format.");
        float totalCostFormat = Float.parseFloat(totalCostFormatting);
        System.out.println("Total Cost in shopping cart: " + totalCostFormat);

        Allure.step("Step 23: Get the 'Sub Total' price from the shopping cart.");
        String subTotalIn$ = shoppingCartPO.getTableDetails(3, 1);

        Allure.step("Step 24: Format the 'Sub Total' price from the shopping cart.");
        String subTotalFormated = subTotalIn$.replace("Sub Total:", "").replace("$", "").trim();
        System.out.println("Sub total Formated:" + subTotalFormated);

        Allure.step("Step 25: Get the quantity from the Shopping cart");
        String qty = shoppingCartPO.getQuantityDetails(2, 5);
        float qtyFormated = Float.parseFloat(qty);
        System.out.println("Quantity of shopping cart: " + qtyFormated);

        Allure.step("Step 26: Verify that 'Total Cost' is as per quantity displayed.");
        Assert.assertEquals(totalCostFormat, Float.parseFloat(listPriceFormated) * qtyFormated, "Total Price is not displayed as per quantity in shopping page.");

        Allure.step("Step 27: Verify that total cost in shopping cart");
        Assert.assertEquals(shoppingCartPO.getAdditionOfTotalCost(), Float.parseFloat(subTotalFormated), "Sub Total is not matched.");

        Allure.step("Step 28: Click on the 'Return to Main Menu' button.");
        commonPO.clickOnReturnToMainMenuButton();

        //    Adding 2nd product to cart
        Allure.step("Step 29: Get the random pet name from navbar to click.");
        String secondRandomPetName = homePo.getRandomPetCategory();
        System.out.println("Random Pet Name: " + secondRandomPetName);

        Allure.step("Step 30: Again Click on the 'Navbar' link randomly");
        homePo.clickOnRandomNavbarLinks(secondRandomPetName);

        Allure.step("Step 31: Verify that Pet Name displayed correct on header.");
        Assert.assertEquals(commonPO.getHeaderText(), secondRandomPetName, "Pet Name " + secondRandomPetName + " should be displayed correct.");

        Allure.step("Step 32: Click on the 'Product Id' link and Get the ProductId");
        String petProductIdSecond = petListingPO.clickOnRandomPetAndGetProductId();

        Allure.step("Step 33: Get the subcategory name from the header text.");
        String subCategoryHeaderNameSecond = commonPO.getHeaderText();
        System.out.println("Sub category header name: " + subCategoryHeaderNameSecond);

        Allure.step("Step 34: Verify that list of 'Product ID' is matched with sub category product.");
        ArrayList<String> listOfSecondSubCategoryProductId = subCategoryListPO.getProductID();
        Assert.assertTrue(listOfSecondSubCategoryProductId.stream().allMatch(str -> str.equals(petProductIdSecond)), "All product ids should be matched");

        Allure.step("Step 35: Get the 'Item Id' text field, 'Description' text field, 'List Price' amount and click on 'Add to Cart' button");
        String secondRandomItemId = subCategoryListPO.getRandomItemId();
        System.out.println(secondRandomItemId);

        String secondDescription = subCategoryListPO.getDescription(secondRandomItemId);
        System.out.println("Description :" + secondDescription);

        String secondListPriceIn$ = subCategoryListPO.getListPrice(secondRandomItemId);
        String secondListPriceFormated = secondListPriceIn$.replace("$", "").trim();
        System.out.println("List price formated: " + secondListPriceFormated);

        Allure.step("Step 36: Click on the 'Add To Cart' button randomly");
        subCategoryListPO.clickOnAddToCartButton(secondRandomItemId);

        Allure.step("Step 37: Verify that page is redirected to shopping cart");
        String shoppingCartNameSecond = "Shopping Cart";

        Allure.step("Step 38: Verify that header text displayed the 'Shopping Cart' name when user is on shopping cart page.");
        Assert.assertEquals(commonPO.getHeaderText(), shoppingCartNameSecond, "Header text for shopping cart is mismatch.");

        Allure.step("Step 39: Get the 'Item Id' text, for first cart and verify the item id is displayed correct.");
        String firstItemIdShoppingCart = shoppingCartPO.getItemIdShoppingCart(randomItemId);
        System.out.println("Shopping Cart First Id" + firstItemIdShoppingCart);
        Assert.assertEquals(firstItemIdShoppingCart, randomItemId, "Shopping Cart Item Id Mismatch");

        Allure.step("Step 40: Get the 'Item Id' text, for second cart and verify the item id is displayed correct.");
        String secondItemIdShoppingCart = shoppingCartPO.getItemIdShoppingCart(secondRandomItemId);
        System.out.println("Shopping Cart Second Id: " + secondItemIdShoppingCart);
        Assert.assertEquals(secondItemIdShoppingCart, secondRandomItemId, "Shopping Cart Item Id Mismatch for Second cart.");

        Allure.step("Step 41: Get the 'Product Id' text, for first cart and verify the product id is displayed correct.");
        String firstProductIdShoppingCart = shoppingCartPO.getProductIDShoppingCart(randomItemId);
        Assert.assertEquals(firstProductIdShoppingCart, petProductId, "Product Id first should be displayed correct in shopping cart.");

        Allure.step("Step 42: Get the 'Product Id' text, for second cart and verify the product id is displayed correct.");
        String secondProductIdShoppingCart = shoppingCartPO.getProductIDShoppingCart(secondRandomItemId);
        Assert.assertEquals(secondProductIdShoppingCart, petProductIdSecond, "Product Id second should be displayed correct in shopping cart.");

        Allure.step("Step 43: Get the 'Description' text, for first cart and verify the 'Description' is displayed correct.");
        String firstDescriptionShoppingCart = shoppingCartPO.getDescriptionShoppingCart(randomItemId);
        Assert.assertEquals(firstDescriptionShoppingCart, description, "Description first product should be displayed correct in shopping cart.");

        Allure.step("Step 44: Get the 'Description' text, for second cart and verify the 'Description' is displayed correct.");
        String secondDescriptionShoppingCart = shoppingCartPO.getDescriptionShoppingCart(secondRandomItemId);
        Assert.assertEquals(secondDescriptionShoppingCart, secondDescription, "Description second product should be displayed correct in shopping cart.");

        Allure.step("Step 45: Get the 'Qty' text, for first cart and verify the 'Qty' is displayed correct.");
        String firstQtyShoppingCart = shoppingCartPO.getQtyShoppingCart(randomItemId);
        float firstQtyShoppingCartFormatted = Float.parseFloat(firstQtyShoppingCart);
        Assert.assertEquals(firstQtyShoppingCart, qty, "Qty should be displayed correct.");

        Allure.step("Step 46: Get the 'Qty' text, for second cart and verify the 'Qty' is displayed correct.");
        String secondQtyShoppingCart = shoppingCartPO.getQtyShoppingCart(secondRandomItemId);
        float secondQtyShoppingCartFormatted = Float.parseFloat(secondQtyShoppingCart);

        Allure.step("Step 47: Get the 'List Price' amount, for first cart and verify the 'List Price' amount is displayed correct.");
        String firstListPriceIn$ShoppingCart = shoppingCartPO.getListPriceIn$(randomItemId);
        Assert.assertEquals(firstListPriceIn$ShoppingCart, listPriceIn$, "First List price product should be displayed correct in shopping cart.");

        Allure.step("Step 48: Get the 'List Price' amount, for second cart and verify the 'List Price' amount is displayed correct.");
        String secondListPriceIn$ShoppingCart = shoppingCartPO.getListPriceIn$(secondRandomItemId);
        Assert.assertEquals(secondListPriceIn$ShoppingCart, secondListPriceIn$, "Second List price product should be displayed correct in shopping cart.");

        Allure.step("Step 49: Get the 'Total cost' amount, for first cart and verify the 'Total cost' amount is displayed correct.");
        String firstTotalCostIn$ShoppingCart = shoppingCartPO.getTotalCostIn$(randomItemId);
        Assert.assertEquals(firstTotalCostIn$ShoppingCart, totalCostIn$, "First List price product should be displayed correct in shopping cart.");
        String firstTotalCostShippingCartFormatting = firstTotalCostIn$ShoppingCart.replace("$", "").trim();
        //Converting into float
        float firstTotalCostShippingCartFormated = Float.parseFloat(firstTotalCostShippingCartFormatting);
        System.out.println("Total Cost in shopping cart: " + firstTotalCostShippingCartFormated);

        Allure.step("Step 50: Get the 'Total cost' amount, for second cart and verify the 'Total cost' amount is displayed correct.");
        String secondTotalCostIn$ShoppingCart = shoppingCartPO.getTotalCostIn$(secondRandomItemId);
        String secondTotalCostShippingCartFormatting = secondTotalCostIn$ShoppingCart.replace("$", "").trim();
        //Converting into float
        float secondTotalCostShippingCartFormated = Float.parseFloat(secondTotalCostShippingCartFormatting);
        System.out.println("Total Cost in shopping cart: " + secondTotalCostShippingCartFormated);

        Allure.step("Step 51: Verify that for first cart the total cost is displayed correct in shipping cart.");
        Assert.assertEquals(firstTotalCostShippingCartFormated, Float.parseFloat(listPriceFormated) * firstQtyShoppingCartFormatted, "Total Price for first cart is not displayed as per quantity in shopping page.");

        Allure.step("Step 52: Verify that for second cart the total cost is displayed correct in shipping cart.");
        Assert.assertEquals(secondTotalCostShippingCartFormated, Float.parseFloat(secondListPriceFormated) * secondQtyShoppingCartFormatted, "Total Price for second cart is not displayed as per quantity in shopping page.");

        float subTotalShoppingCartFormatted = shoppingCartPO.getSubTotal();

        Allure.step("Step 53: Verify that sub total amount in shopping cart is displayed correct.");
        Assert.assertEquals(shoppingCartPO.getAdditionOfTotalCost(), subTotalShoppingCartFormatted, "Total cost of shopping cart is not matched with Sub Total.");

        /*
        Allure.step("Step 33: Verify that 'Item Id' is displayed correctly when user is on shopping cart page.");
        Assert.assertEquals(shoppingCartPO.getTableDetails(3, 1), secondRandomItemId, "Item id is not displayed correctly");

        Allure.step("Step 34: Verify that 'Product Id' is displayed correctly when user is on shopping cart page.");
        Assert.assertEquals(shoppingCartPO.getTableDetails(3, 2), petProductIdSecond, "Item id is not displayed correctly");

        Allure.step("Step 35: Verify that 'Description' text is matched in shopping cart page with the sub category section.");
        Assert.assertEquals(shoppingCartPO.getTableDetails(3, 3), secondDescription, "Description is not correct in the Shopping cart page");

        Allure.step("Step 36: Verify that 'List price' amount is matched in shopping cart page with the sub category section.");
        Assert.assertEquals(shoppingCartPO.getTableDetails(3, 6), secondListPriceIn$, "Product Id is not correct in the Shopping cart page");

        Allure.step("Step 37: Get the 'Total Cost' price from the shopping cart.");
        String secondTotalCostIn$ = shoppingCartPO.getTableDetails(3, 7);
        String secondTotalCost = secondTotalCostIn$.replace("$", "").trim();

        //Converting into float
        float secondTotalCostFormated = Float.parseFloat(secondTotalCost);
        System.out.println("Total Cost in shopping cart: " + secondTotalCostFormated);

        Allure.step("Step 38: Get the 'Sub Total' price from the shopping cart.");
        String secondSubTotalIn$ = shoppingCartPO.getTableDetails(4, 1);

        Allure.step("Step 39: Format the 'Sub Total' price from the shopping cart.");
        String secondSubTotalFormated = secondSubTotalIn$.replace("Sub Total:", "").replace("$", "").trim();
        System.out.println("Sub total Formated:" + secondSubTotalFormated);

        Allure.step("Step 40: Get the quantity from the Shopping cart");
        String secondQty = shoppingCartPO.getQuantityDetails(3, 5);
        float secondQtyFormated = Float.parseFloat(secondQty);
        System.out.println("Quantity of shopping cart: " + secondQtyFormated);

        Allure.step("Step 41: Verify that 'Total Cost' is as per quantity displayed.");
        Assert.assertEquals(secondTotalCostFormated, Float.parseFloat(secondListPriceFormated) * secondQtyFormated, "Total Price is not displayed as per quantity in shopping page.");

        Allure.step("Step 42: Verify that total cost in shopping cart");
        Assert.assertEquals(shoppingCartPO.getAdditionOfTotalCost(), Float.parseFloat(secondSubTotalFormated), "Sub Total is not matched.");

         */
        Allure.step("Step 54: Click on 'Proceed To CheckOut' button.");
        shoppingCartPO.clickOnProceedToCheckoutButton();

        Allure.step("Step 55: Verify that user successfully navigated to Payment details page and click on continue button");
        String paymentDetailsHeader = "Payment Details";
        Assert.assertEquals(paymentDetailsPO.getHeaderText(), paymentDetailsHeader, "Header text is not matched");

        Allure.step("Step 56: Click on 'Continue' button.");
        paymentDetailsPO.clickOnContinueButton();

        Allure.step("Step 57: Verify that user successfully navigated to Billing summary page and click on confirm button");
        Assert.assertEquals(billingSummaryPO.getDate(), javaHelpers.getDateTimeFormatter("yyyy/MM/dd", LocalDate.now()), "Date is not matched");

        Allure.step("Step 58: Click on 'Confirm' button.");
        billingSummaryPO.clickOnConfirmButton();

        Allure.step("Step 59: Verify that confirmation message displayed when product is submitted.");
        String successMessage = "Thank you, your order has been submitted.";
        Assert.assertEquals(confirmationPO.getSuccessMessage(), successMessage, "The success message is not correct");

        Allure.step("Step 60: Verify that pet description is displayed correct for first cart.");
        Assert.assertEquals(confirmationPO.getDescriptionConfirmationPage(randomItemId), description, "Description is not matched.");
        Allure.step("Step 61: Verify that pet description is displayed correct for second cart.");
        Assert.assertEquals(confirmationPO.getDescriptionConfirmationPage(secondRandomItemId), secondDescription, "Description is not matched.");

        Allure.step("Step 62: Verify that 'item id' is displayed correct for first cart.");
        Assert.assertEquals(confirmationPO.getItemIdConfirmationPage(randomItemId), randomItemId, "Item id should be match with Shopping cart.");

        Allure.step("Step 63: Verify that 'item id' is displayed correct for second cart.");
        Assert.assertEquals(confirmationPO.getItemIdConfirmationPage(secondRandomItemId), secondRandomItemId, "Item id should be match with Shopping cart.");

        Allure.step("Step 64: Verify that 'list price' is displayed correct for first cart.");
        Assert.assertEquals(confirmationPO.getListPriceIn$ConfirmationPage(randomItemId), listPriceIn$, "List price should be match with Shopping cart.");

        Allure.step("Step 65: Verify that 'list price' is displayed correct for second cart.");
        Assert.assertEquals(confirmationPO.getListPriceIn$ConfirmationPage(secondRandomItemId), secondListPriceIn$, "List price should be match with Shopping cart.");

        Allure.step("Step 66: Verify that 'qty' is displayed correct for first cart.");
        Assert.assertEquals(confirmationPO.getQtyConfirmationPage(randomItemId), qty, "Quantity should be match with Shopping cart.");

        Allure.step("Step 67: Verify that 'qty' is displayed correct for second cart.");
        Assert.assertEquals(confirmationPO.getQtyConfirmationPage(secondRandomItemId), secondQtyShoppingCart, " Quantity should be match with Shopping cart.");

        Allure.step("Step 68: Verify that 'Total Cost' amount is displayed correct for first cart.");
        Assert.assertEquals(confirmationPO.getTotalCostIn$ConfirmationPage(randomItemId), totalCostIn$, "Total cost should be match with Shopping cart.");

        Allure.step("Step 69: Verify that 'Total Cost' amount is displayed correct for second cart.");
        Assert.assertEquals(confirmationPO.getTotalCostIn$ConfirmationPage(secondRandomItemId), secondTotalCostIn$ShoppingCart, "Total cost should be match with Shopping cart.");

        Allure.step("Step 70: Verify that 'Sub total' is displayed correct.");
        Assert.assertEquals(confirmationPO.getAdditionOfTotalCost(), confirmationPO.getSubTotal(), "SubTotal should be addition of 'Total Cost' item per product.");
        Thread.sleep(3000);
    }
}
