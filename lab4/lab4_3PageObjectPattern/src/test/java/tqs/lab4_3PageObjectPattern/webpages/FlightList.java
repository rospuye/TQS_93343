package tqs.lab4_3PageObjectPattern.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FlightList {

    private WebDriver driver;

    // Locators
    @FindBy(xpath = "/html/body/div[2]/table/thead/tr/th[4]")
    private WebElement departsColumn;

    @FindBy(xpath = "/html/body/div[2]/table/thead/tr/th[5]")
    private WebElement arrivesColumn;

    @FindBy(xpath = "/html/body/div[2]/table/tbody/tr[2]/td[1]/input")
    private WebElement chooseThisFlightButton;

    // Constructor
    public FlightList(WebDriver driver){
        this.driver=driver;

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    // Assertion
    public boolean isDepartureCorrect() {
        return departsColumn.getText().toString().contains("Boston");
    }

    // Assertion
    public boolean isArrivalCorrect() {
        return arrivesColumn.getText().toString().contains("New York");
    }

    public void chooseThisFlightButtonClick() {
        chooseThisFlightButton.click();
    }

}
