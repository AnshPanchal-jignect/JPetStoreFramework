package pageobjects.shoppingcart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import pageobjects.base.BasePo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartPO extends BasePo {

    @FindBy(name = "updateCartQuantities")
    private WebElement updateCartButton;

    @FindBy(xpath = "//input[@name='updateCartQuantities']/parent::td")
    private WebElement subTotal;

    @FindBy(xpath = "//b[text()='Total Cost']/ancestor::tr/following-sibling::tr/td[7]")
    private List<WebElement> listPrice;

    @FindBy(xpath = "//a[text()='Proceed to Checkout']")
    private WebElement proceedToCheckoutButton;

    @FindBy(xpath = "//h2[text()='Shopping Cart']/following::tr[2]/td/b")
    private WebElement cartEmptyText;

    @FindBy(xpath = "//tbody//tr/following::tr/td[1]/a")
    private List<WebElement> listItemIdShoppingCart;

    public ShoppingCartPO(WebDriver driver) {
        super(driver);
    }

    public String getTableDetails(int rowNumber, int columnNumber) {
        String tableId = "//tbody/tr[" + rowNumber + "]/td[" + columnNumber + "]";
        return selenium.getText(By.xpath(tableId));
    }

    public boolean checkTableDetailsPresent(int rowNumber, int columnNumber) {
        String tableId = "//tbody/tr[" + rowNumber + "]/td[" + columnNumber + "]";
        WebElement cell = driver.findElement(By.xpath(tableId));
        return cell.isDisplayed();
    }

    public void clickOnRemoveButton(int rowNumber, int columnNumber) throws InterruptedException {
        String tableId = "//tbody/tr[" + rowNumber + "]/td[" + columnNumber + "]";
        selenium.clickOn(By.xpath(tableId));
    }


    public String getPetTableData(String itemId, int columnNumber) throws InterruptedException {
        if (columnNumber == 7) {
            String tableData = "//a[text()='" + itemId + "']/parent::td/following-sibling::td[7]/a";
            selenium.clickOn(By.xpath(tableData));
            return null;
        } else {
            String tableData = "//a[text()='" + itemId + "']/parent::td/following-sibling::td[" + columnNumber + "]";
            return selenium.getText(By.xpath(tableData));
        }
    }

    public String getProductID(String itemId) throws InterruptedException {
        return getPetTableData(itemId, 1);
    }

    public String getDescription(String itemId) throws InterruptedException {
        return getPetTableData(itemId, 2);
    }

    public String getListPrice(String itemId) throws InterruptedException {
        return getPetTableData(itemId, 5);
    }

    public String getItemId(int rowNumber) {
        String itemId = "//tbody/tr[" + rowNumber + "]/td[1]/a";
        return selenium.getText(By.xpath(itemId));
    }

    public void enterQuantity(String itemID, int qty) {
        String inputQtyColumn = "//a[text()='" + itemID + "']//following::td//input[@name='" + itemID + "']";
        selenium.enterText(By.xpath(inputQtyColumn), String.valueOf(qty), true);
    }

    public void clickOnUpdateButton() throws InterruptedException {
        selenium.clickOn(updateCartButton);
    }

    public Float getSubTotal() {
        String subTotalWith$ = selenium.getText(subTotal);
        String numericValue = subTotalWith$.replace("Sub Total:", "").replace("$", "").trim();
        return Float.parseFloat(numericValue);
    }

    public float getAdditionOfTotalCost() {
        float total = 0.0f;
        for (WebElement listP : listPrice) {
            String costIn$ = selenium.getText(listP);
            String cost = costIn$.replace("$", "").trim();
//            System.out.println("Actual Cost of Product: " + cost);
            total = total + Float.parseFloat(cost);
//            System.out.println("Total cost of product :" + total);
        }
        return total;
    }

    public void clickOnProceedToCheckoutButton() throws InterruptedException {
        selenium.click(proceedToCheckoutButton);
    }

    public boolean removeButtonEnabled(String itemId) throws InterruptedException {

        String tableData = "(//a[text()='Remove']/ancestor::tr//a[contains(@href, '" + itemId + "')])[2]";
        WebElement removeButton = driver.findElement(By.xpath(tableData));
        return removeButton.isDisplayed() && removeButton.isEnabled();
    }

    public void clickOnRemoveButton(String itemId) {
        String tableData = "(//a[text()='Remove']/ancestor::tr//a[contains(@href, '" + itemId + "')])[2]";
        WebElement removeButton = driver.findElement(By.xpath(tableData));
        removeButton.click();
    }

    public String getEmptyCartText() {
        return selenium.getText(cartEmptyText);
    }

    public String getQuantityDetails(int rowNumber, int columnNumber) {
        String tableId = "//tbody/tr[" + rowNumber + "]/td[" + columnNumber + "]/input";
        WebElement quantity = driver.findElement(By.xpath(tableId));
        return selenium.getElementAttributeValue(quantity, "value");
    }
/*
    public List<String> getListItemIdShoppingCart() {
        List<String> listItemId = new ArrayList<>();
        Assert.assertFalse(listItemIdShoppingCart.isEmpty(), "Item Id is not present.");
        for (WebElement listId : listItemIdShoppingCart) {

            String itemId = listId.getText().trim();
            listItemId.add(itemId);
        }
        return listItemId;
    }
*/
    public String getItemIdShoppingCart(String randomItemId) {
        String itemIdPath = "//a[text()='" + randomItemId + "']";
        return selenium.getText(By.xpath(itemIdPath));
    }

    public String getProductIDShoppingCart(String randomItemId) {
        String petProductPath = "//a[text()='" + randomItemId + "']//following::td[1]";
        return selenium.getText(By.xpath(petProductPath));
    }

    public String getDescriptionShoppingCart(String randomItemId) {
        String petDesc = "//a[text()='" + randomItemId + "']//following::td[2]";
        return selenium.getText(By.xpath(petDesc));
    }


    public String getListPriceIn$(String randomItemId) {
        String listPrice = "//a[text()='" + randomItemId + "']//following::td[5]";
        return selenium.getText(By.xpath(listPrice));
    }

    public String getTotalCostIn$(String randomItemId) {
        String totalCost = "//a[text()='" + randomItemId + "']//following::td[6]";
        return selenium.getText(By.xpath(totalCost));
    }

    public String getQtyShoppingCart(String randomItemId) {
        String qty = "//a[text()='" + randomItemId + "']//following::td[4]/input";
        WebElement quantity = driver.findElement(By.xpath(qty));
        return selenium.getElementAttributeValue(quantity, "value");
    }
}
