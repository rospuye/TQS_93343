Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.

  Scenario: Search books by publication year
    Given a book with the title 'One good book', written by 'Anonymous', published in 2013-3-14, in the category 'Horror'
    And another book with the title 'Some other book', written by 'Tim Tomson', published in 2014-8-23, in the category 'Adventure'
    And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 2012-1-1, in the category 'Comedy'
    When the customer searches for books published between 2013 and 2014
    Then 2 books should have been found
    And Book 1 should have the title 'Some other book'
    And Book 2 should have the title 'One good book'

  Scenario: Search books by author
    Given a book with the title 'One good book', written by 'Anonymous', published in 2013-3-14, in the category 'Horror'
    And another book with the title 'Some other book', written by 'Tim Tomson', published in 2014-8-23, in the category 'Adventure'
    And another book with the title 'Two good books', written by 'Anonymous', published in 2012-6-3, in the category 'Sci-fi'
    When the customer searches for books published by 'Anonymous'
    Then 2 books should have been found
    And Book 1 should have the title 'One good book'
    And Book 2 should have the title 'Two good books'

  Scenario: Search books by publication year
    Given a book with the title 'One good book', written by 'Anonymous', published in 2013-3-14, in the category 'Horror'
    And another book with the title 'Some other book', written by 'Tim Tomson', published in 2014-8-23, in the category 'Adventure'
    And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 2012-1-1, in the category 'Comedy'
    When the customer searches for books published in the category of 'Adventure'
    Then 1 books should have been found
    And Book 1 should have the title 'Some other book'