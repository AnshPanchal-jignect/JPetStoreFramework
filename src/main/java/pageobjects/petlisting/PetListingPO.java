package pageobjects.petlisting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import pageobjects.base.BasePo;
import utilities.JavaHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PetListingPO extends BasePo {
    public PetListingPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@id='QuickLinks']/child::a")
    private List<WebElement> navbarLinks;

    @FindBy(xpath = "//div/table//tr/td/b")
    private List<WebElement> listOfPetIdsInputSearch;

    @FindBy(xpath = "//td[1]")
    List<WebElement> listProductId;


    public List<String> listOfActualPets() {
        List<String> actualPetItems = new ArrayList<>();
        // Check if listOfProductNamesList is not empty
        if (listOfPetIdsInputSearch != null && !listOfPetIdsInputSearch.isEmpty()) {
            System.out.println("Number of product names found: " + listOfPetIdsInputSearch.size());
            for (WebElement element : listOfPetIdsInputSearch) {
                actualPetItems.add(element.getText());
            }
        }
        return actualPetItems;
    }

    public String clickOnPetAndGetProductId(String subCategory) throws InterruptedException {
        String petName = "//td[text()='" + subCategory + "']//parent::tr/td/a";
        String petProductId = selenium.getText(By.xpath(petName));
        selenium.clickOn(By.xpath(petName));
        return petProductId;
    }

    public String clickOnRandomPetAndGetProductId() {
        JavaHelpers javaHelpers = new JavaHelpers();
        int randomIndex = javaHelpers.getRandomNumber(listProductId.size() - 1, 0);
        System.out.println("Size of List: " + listProductId.size());
        Assert.assertFalse(listProductId.isEmpty(), "Product List is empty.");
        WebElement randomPet = listProductId.get(randomIndex);
        String petProductId = randomPet.getText().trim();
        randomPet.click();
        return petProductId;
    }
}
