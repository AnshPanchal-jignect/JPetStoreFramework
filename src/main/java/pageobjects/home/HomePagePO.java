package pageobjects.home;

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

public class HomePagePO extends BasePo {
    public HomePagePO(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div#MainImageContent img")
    private WebElement image;

    @FindBy(xpath = "//div[@id='QuickLinks']/a")
    private List<WebElement> petNameNavbarLinks;

    @FindBy(xpath = "//a[contains(@href, 'categoryId=')][not(@href = preceding::a/@href)]")
    private List<WebElement> listOfPetNameNavbar;


    public boolean isImageDisplayed() {
        return selenium.isElementPresent(image);
    }

    public void clickOnNavbarLinks(String linkName) throws InterruptedException {
        String petNameLink = "//div[@id='QuickLinks']/a[contains(@href,'" + linkName.toUpperCase() + "')]";
        selenium.clickOn(By.xpath(petNameLink));
    }

    private List<String> getListOfPetNameNavbar() {
        List<String> listOfPets = new ArrayList<>();
        for (WebElement link : listOfPetNameNavbar) {
            String hrefValue = link.getDomAttribute("href");
//            System.out.println("Href: " + hrefValue);
            if (hrefValue != null && hrefValue.contains("categoryId=")) {
                // Extract categoryId value
                String categoryId = hrefValue.split("categoryId=")[1];
//                System.out.println("Extracted Category ID: " + categoryId);
                listOfPets.add(categoryId);
            }
        }
        return listOfPets;
    }

    public String getRandomPetCategory() {
        JavaHelpers javaHelpers = new JavaHelpers();
        // Get the list of unique pet categories
        List<String> listOfPetCategories = getListOfPetNameNavbar();
        if (listOfPetCategories.isEmpty()) {
            throw new RuntimeException("No pet categories found in the navbar!");
        }
        // Convert each category name to Title Case (First letter uppercase, rest lowercase)
        List<String> formattedCategories = listOfPetCategories.stream()
                .map(category -> category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase())
                .toList();
        // Generate a random index
        int randomIndex = javaHelpers.getRandomNumber(formattedCategories.size() - 1, 0);
        return formattedCategories.get(randomIndex);
    }

    public void clickOnRandomNavbarLinks(String randomPetName) {
        Assert.assertFalse(listOfPetNameNavbar.isEmpty(), "NavBar pet link is not Present");
        String tableAddToCart = "//div[@id='QuickLinks']/a[contains(@href,'" + randomPetName.toUpperCase() + "')]";
        WebElement clickOnAddToCart = driver.findElement(By.xpath(tableAddToCart));
        clickOnAddToCart.click();
    }


}
