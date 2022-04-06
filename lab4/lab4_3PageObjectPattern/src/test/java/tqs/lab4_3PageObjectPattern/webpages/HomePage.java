package tqs.lab4_3PageObjectPattern.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {

    private WebDriver driver;

    // Page URL
    private static String PAGE_URL = "https://blazedemo.com/";

    // Locators
    @FindBy(xpath = "/html/body/div[3]/form/select[1]")
    private WebElement departureCitySelect;

    @FindBy(xpath = "/html/body/div[3]/form/select[2]")
    private WebElement destinationCitySelect;

    @FindBy(xpath = "/html/body/div[3]/form/div/input")
    private WebElement findFlightsButton;

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        // Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void chooseDepartureCity() {
        departureCitySelect.click();
        Select boston = new Select(departureCitySelect);
        boston.selectByIndex(2);
    }

    public void chooseDestinationCity() {
        destinationCitySelect.click();
        Select newYork = new Select(destinationCitySelect);
        newYork.selectByIndex(4);
    }

    public void findFlightsButtonClick() {
        findFlightsButton.click();
    }

}
