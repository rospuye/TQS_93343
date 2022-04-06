package tqs.lab4_3PageObjectPattern.tests;

import tqs.lab4_3PageObjectPattern.webpages.HomePage;
import tqs.lab4_3PageObjectPattern.webpages.FlightList;
import tqs.lab4_3PageObjectPattern.webpages.PersonalInfo;
import tqs.lab4_3PageObjectPattern.webpages.PurchaseDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
public class ApplyTests {

    // WebDriver driver;

    // @BeforeEach
    // public void setup() {
    //     // use FF Driver
    //     driver = new FirefoxDriver();
    //     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    // }

    @Test
    public void applyTests(FirefoxDriver driver) {

        // HomePage
        HomePage home = new HomePage(driver);
        home.chooseDepartureCity();
        home.chooseDestinationCity();
        home.findFlightsButtonClick();

        // FlightList
        FlightList flightList = new FlightList(driver);
        assertTrue(flightList.isDepartureCorrect());
        assertTrue(flightList.isArrivalCorrect());
        flightList.chooseThisFlightButtonClick();

        /* THE COMMENTED ASSERTS ARE TESTS THAT ARE SUPPOSED TO FAIL ON PURPOSE */
        /* UNCOMMENT THEM IF YOU WANT TO SEE THEM FAIL */

        // PersonalInfo
        PersonalInfo personalInfo = new PersonalInfo(driver);
        // assertTrue(personalInfo.isPriceCorrect());
        // assertTrue(personalInfo.isAirlineCorrect());
        personalInfo.setName("Isabel Rosário");
        personalInfo.setAddress("123 Main Str");
        personalInfo.setCity("Anycity");
        personalInfo.setState("Anystate");
        personalInfo.setZipcode("12345");
        personalInfo.chooseCardType();
        personalInfo.setCreditCardNumber("2222");
        personalInfo.setCreditCardMonth("11");
        personalInfo.setCreditCardYear("2022");
        personalInfo.setNameOnCard("Isabel Rosário");
        personalInfo.purchaseFlightButtonClick();

        // PurchaseDetails
        PurchaseDetails purchaseDetails = new PurchaseDetails(driver);
        // assertTrue(purchaseDetails.isCreditCardNumberCorrect());
        // assertTrue(purchaseDetails.isTotalPriceCorrect());

    }

    // @AfterEach
    // public void close(){
    //     driver.close();
    // }

}
