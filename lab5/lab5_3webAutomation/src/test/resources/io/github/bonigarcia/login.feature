Feature: Login in practice site

  Scenario: Choose departure and destination cities
    When I navigate to "https://blazedemo.com/"
    And I choose 'Boston' as my departure city
    And I choose 'New York' as my destination city
    And I click Find Flights
    Then I should be redirected to "https://blazedemo.com/reserve.php"
    And The departure city should be 'Boston'
    And The arrival city should be 'New York'

  Scenario: Choose flight
    When I am at "https://blazedemo.com/reserve.php"
    And I click Choose This Flight on flight number 234
    Then I should be redirected to "https://blazedemo.com/purchase.php"
    And The flight number should be 234