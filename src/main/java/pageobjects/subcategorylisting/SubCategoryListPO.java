package pageobjects.subcategorylisting;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import pageobjects.base.BasePo;
import utilities.JavaHelpers;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryListPO extends BasePo {
    public SubCategoryListPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[text()='Add to Cart']")
    private WebElement addToCartButton;

    @FindBy(xpath = "//tbody/tr/td[2]")
    private List<WebElement> listOfProductID;

    @FindBy(xpath = "//tbody/tr/td[5]")
    private List<WebElement> listAddToCartButton;

    @FindBy(xpath = "//table//tbody//tr/td[1]")
    private List<WebElement> listOfItemIds;

    public ArrayList<String> getProductID() {
        ArrayList<String> listOfProductId = new ArrayList<>();
        for (WebElement productId : listOfProductID) {
            if (productId != null) {
                String petProductId = selenium.getText(productId).trim();
                listOfProductId.add(petProductId);
            }
        }
        return listOfProductId;
    }

    /*
        public String getPetItemId(int rowNumber) {
            String itemId = "//tbody/tr[" + rowNumber + "]/td[1]/a";
            return selenium.getText(By.xpath(itemId));
        }
    */
    public String getTableDetails(int rowNumber, int columnNumber) {
        String tableId = "//tbody/tr[" + rowNumber + "]/td[" + columnNumber + "]";
        return selenium.getText(By.xpath(tableId));
    }

    public boolean checkTableDetailsPresent(int rowNumber, int columnNumber) {
        String tableId = "//tbody/tr[" + rowNumber + "]/td[" + columnNumber + "]";
        WebElement cell = driver.findElement(By.xpath(tableId));
        return cell.isDisplayed();
    }

    public void clickOnAddToCartButton(int rowNumber, int columnNumber) throws InterruptedException {
        String tableId = "//tbody/tr[" + rowNumber + "]/td[" + columnNumber + "]";
        selenium.clickOn(By.xpath(tableId));
    }


    private ArrayList<String> getItemId() {
        ArrayList<String> listOfItemId = new ArrayList<>();
        for (WebElement itemId : listOfItemIds) {
            String itemIds = itemId.getText().trim();
            listOfItemId.add(itemIds);
        }
        return listOfItemId;
    }

    public String getRandomItemId() {
        JavaHelpers javaHelpers = new JavaHelpers();
        ArrayList<String> listOfItemId = getItemId();
        int randomIndex = javaHelpers.getRandomNumber(listOfItemId.size() - 1, 0);
        return listOfItemId.get(randomIndex);
    }

    public String getDescription(String randomId) {
        String tableDescription = "//a[text()='" + randomId + "']/ancestor::tr/td[3]";
        return selenium.getText(By.xpath(tableDescription));
    }

    public String getListPrice(String randomId) {
        String tableListPrice = "//a[text()='" + randomId + "']/ancestor::tr/td[4]";
        return selenium.getText(By.xpath(tableListPrice));
    }

    public void clickOnAddToCartButton(String randomId) {
        Assert.assertFalse(listAddToCartButton.isEmpty(), "Add to Cart Button is not Present");
        String tableAddToCart = "//a[text()='" + randomId + "']/ancestor::tr/td[5]";
        WebElement clickOnAddToCart = driver.findElement(By.xpath(tableAddToCart));
        clickOnAddToCart.click();
    }


}
