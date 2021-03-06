@bypass-ui
Feature: Select tests using direct teller interface
  ATM application server is not running

#  TODO failing when runs as all tests
  Scenario: Successful withdrawal from an account
    Given my account has been credited with $500
    When I withdraw $100
    Then $100 should be dispensed
    And the balance of my account should be $400

  Scenario: Withdraw remaining large balance
    Given my account has been credited with $1000000
    When I withdraw $1000000
    Then $1000000 should be dispensed