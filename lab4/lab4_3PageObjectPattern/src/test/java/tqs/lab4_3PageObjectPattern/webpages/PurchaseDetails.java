package tqs.lab4_3PageObjectPattern.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PurchaseDetails {

    private WebDriver driver;

    // Locators
    @FindBy(xpath = "/html/body/div[2]/div/table/tbody/tr[4]/td[2]")
    private WebElement creditCardNumber;

    @FindBy(xpath = "/html/body/div[2]/div/table/tbody/tr[3]/td[2]")
    private WebElement totalPrice;

    // Constructor
    public PurchaseDetails(WebDriver driver){
        this.driver=driver;

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    // Assertion
    public boolean isCreditCardNumberCorrect() {
        return creditCardNumber.getText().toString().contains("2222");
    }

    // Assertion
    public boolean isTotalPriceCorrect() {
        return totalPrice.getText().toString().contains("914.76");
    }

}
