package tqs.lab4_3PageObjectPattern.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class PersonalInfo {

    private WebDriver driver;

    @FindBy(id="inputName")
    WebElement inputName;

    @FindBy(id="address")
    WebElement address;

    @FindBy(id="city")
    WebElement city;

    @FindBy(id="state")
    WebElement state;

    @FindBy(id="zipCode")
    WebElement zipcode;

    @FindBy(xpath = "//*[@id=\"cardType\"]")
    private WebElement cardType;

    @FindBy(id="creditCardNumber")
    WebElement creditCardNumber;

    @FindBy(id="creditCardMonth")
    WebElement creditCardMonth;

    @FindBy(id="creditCardYear")
    WebElement creditCardYear;

    @FindBy(id="nameOnCard")
    WebElement nameOnCard;

    @FindBy(xpath = "/html/body/div[2]/form/div[11]/div/input")
    private WebElement purchaseFlightButton;

    @FindBy(xpath = "/html/body/div[2]/p[3]")
    private WebElement price;

    @FindBy(xpath = "/html/body/div[2]/p[2]")
    private WebElement airline;

    //Constructor
    public PersonalInfo(WebDriver driver){
        this.driver = driver;

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void setName(String name){
        inputName.clear();
        inputName.sendKeys(name);
    }

    public void setAddress(String address){
        this.address.clear();
        this.address.sendKeys(address);
    }

    public void setCity(String city){
        this.city.clear();
        this.city.sendKeys(city);
    }

    public void setState(String state){
        this.state.clear();
        this.state.sendKeys(state);
    }

    public void setZipcode(String zipcode){
        this.zipcode.clear();
        this.zipcode.sendKeys(zipcode);
    }

    public void chooseCardType() {
        cardType.click();
        Select visa = new Select(cardType);
        visa.selectByIndex(0);
    }

    public void setCreditCardNumber(String creditCardNumber){
        this.creditCardNumber.clear();
        this.creditCardNumber.sendKeys(creditCardNumber);
    }

    public void setCreditCardMonth(String creditCardMonth){
        this.creditCardMonth.clear();
        this.creditCardMonth.sendKeys(creditCardMonth);
    }

    public void setCreditCardYear(String creditCardYear){
        this.creditCardYear.clear();
        this.creditCardYear.sendKeys(creditCardYear);
    }

    public void setNameOnCard(String nameOnCard){
        this.nameOnCard.clear();
        this.nameOnCard.sendKeys(nameOnCard);
    }

    public void purchaseFlightButtonClick() {
        purchaseFlightButton.click();
    }

    // Assertion
    public boolean isPriceCorrect() {
        return price.getText().toString().contains("432.98");
    }

    // Assertion
    public boolean isAirlineCorrect() {
        return airline.getText().toString().contains("United Airlines");
    }

}
