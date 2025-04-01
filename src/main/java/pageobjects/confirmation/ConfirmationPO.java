package pageobjects.confirmation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePo;

import java.util.List;

public class ConfirmationPO extends BasePo {
    public ConfirmationPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//ul[@class ='messages']/li")
    private WebElement headerText;

    @FindBy(xpath = "//tbody/tr/td/a")
    private WebElement itemId;

    @FindBy(xpath = "//th[contains(text(),'Total:')]")
    private WebElement subTotal;

    @FindBy(xpath = "//th[text()='Total Cost']/parent::tr/following-sibling::tr/td[5]")
    private List<WebElement> listPrice;

    @FindBy(xpath = "//tbody//tr/following::tr/td[1]/a")
    private List<WebElement> listItemId;

    public String getSuccessMessage() {
        return selenium.getText(headerText);
    }

    public String getTableData(int rowNumber, int columnNumber) {
        String tableData = "//th[text()='Description']/ancestor::tr[2]/td//tr[" + rowNumber + "]/td[" + columnNumber + "]";
        return selenium.getText(By.xpath(tableData));
    }

    /*
    public String getTableDetails(int rowNumber) {
//        String tableData = "(//td[@colspan='2'])[2]/child::table/tbody/tr[2]/td[" + rowNumber + "]";
        String tableData = "//th[text()='Description']/following::td[" + rowNumber + "]";
        return selenium.getText(By.xpath(tableData));
    }

    public String getProductDescription(int rowNumber) {
        return getTableDetails(rowNumber);
    }

    public float getPrice(int rowNumber) {
        String priceIn$ = getTableDetails(rowNumber);
        String price = priceIn$.replace("$", "").trim();
//        float price = Float.parseFloat(priceIn$);
        return Float.parseFloat(price);
    }

    public String getItemId() {
        return selenium.getText(itemId);
    }

    public int getQuantity(int rowNumber) {
        String quantityProduct = getTableDetails(rowNumber);
        return Integer.parseInt(quantityProduct);
    }
*/
    public String getItemIdConfirmationPage(String randomItemId) {
        String itemIdPath = "//a[text()='" + randomItemId + "']";
        return selenium.getText(By.xpath(itemIdPath));
    }

    public String getDescriptionConfirmationPage(String randomItemId) {
        String petDesc = "//a[text()='" + randomItemId + "']//following::td[1]";
        return selenium.getText(By.xpath(petDesc));
    }


    public String getListPriceIn$ConfirmationPage(String randomItemId) {
        String listPrice = "//a[text()='" + randomItemId + "']//following::td[3]";
        return selenium.getText(By.xpath(listPrice));
    }

    public String getTotalCostIn$ConfirmationPage(String randomItemId) {
        String totalCost = "//a[text()='" + randomItemId + "']//following::td[4]";
        return selenium.getText(By.xpath(totalCost));
    }

    public String getQtyConfirmationPage(String randomItemId) {
        String qty = "//a[text()='" + randomItemId + "']//following::td[2]";
        return selenium.getText(By.xpath(qty));
    }

    public Float getSubTotal() {
        String subTotalWith$ = selenium.getText(subTotal);
        String numericValue = subTotalWith$.replace("Total:", "").replace("$", "");
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
/*
    public List<String> getItemId(){
        List<String> itemId = new ArrayList<>();
        Assert.assertFalse(listItemId.isEmpty(), "Item Id should be present in Confirmation Page.");
        for(WebElement itemIdElement : listItemId){
            String listItemId = itemIdElement.getText().trim();
            itemId.add(listItemId);
        }
        return itemId;
    }

 */
}
