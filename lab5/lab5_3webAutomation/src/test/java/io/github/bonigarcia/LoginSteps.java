/*
 * (C) Copyright 2020 Boni Garcia (https://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginSteps {

    private WebDriver driver;

    @FindBy(xpath = "/html/body/div[3]/form/select[1]")
    private WebElement departureCitySelect;

    @FindBy(xpath = "/html/body/div[3]/form/select[2]")
    private WebElement destinationCitySelect;

    @FindBy(xpath = "/html/body/div[3]/form/div/input")
    private WebElement findFlightsButton;

    @FindBy(xpath = "/html/body/div[2]/table/thead/tr/th[4]")
    private WebElement departsColumn;

    @FindBy(xpath = "/html/body/div[2]/table/thead/tr/th[5]")
    private WebElement arrivesColumn;

    @FindBy(xpath = "/html/body/div[2]/table/tbody/tr[2]/td[1]/input")
    private WebElement chooseThisFlightButton;

    @FindBy(xpath = "/html/body/div[2]/p[2]")
    private WebElement flightNumberLine;

    @When("I navigate to {string}")
    public void i_navigate_to(String url) {
        driver = WebDriverManager.firefoxdriver().create();
        driver.get(url);
    }

    @When("I choose {string} as my departure city")
    public void i_choose_as_my_departure_city(String departure) {
        departureCitySelect.click();
        Select boston = new Select(departureCitySelect);
        boston.selectByValue(departure);
    }

    @When("I choose {string} as my destination city")
    public void i_choose_as_my_destination_city(String destination) {
        destinationCitySelect.click();
        Select boston = new Select(destinationCitySelect);
        boston.selectByValue(destination);
    }

    @When("I click Find Flights")
    public void i_click_find_flights() {
        findFlightsButton.click();
    }

    @Then("I should be redirected to {string}")
    public void i_should_be_redirected_to(String url) {
        assertEquals(url, driver.getCurrentUrl());
    }

    @Then("The departure city should be {string}")
    public void the_departure_city_should_be(String departure) {
        assertTrue(departsColumn.getText().toString().contains(departure));
    }

    @Then("The arrival city should be {string}")
    public void the_arrival_city_should_be(String destination) {
        assertTrue(arrivesColumn.getText().toString().contains(destination));
    }

    @When("I am at {string}")
    public void i_am_at(String url) {
        assertEquals(url, driver.getCurrentUrl());
    }

    @When("I click Choose This Flight on flight number {int}")
    public void i_click_choose_this_flight_on_flight_number(Integer int1) {
        chooseThisFlightButton.click();
    }

    @Then("The flight number should be {int}")
    public void the_flight_number_should_be(Integer flightNumber) {
        assertTrue(flightNumberLine.getText().toString().contains(String.valueOf(flightNumber)));
    }

}
