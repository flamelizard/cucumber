# Start atm web interface instead of regular
@atm-web
Feature: Cash Withdrawal

  Scenario: Successful withdrawal from an account
    Given my account has been credited with $500
    When I withdraw $100
    Then $100 should be dispensed
    And the balance of my account should be $400

  Scenario: Withdraw remaining large balance
    Given my account has been credited with $1000000
    When I withdraw $1000000
    Then $1000000 should be dispensed

  Scenario: Unsuccessful withdrawal due to technical fault
    Given my account has been credited with $100
    But the cash slot has developed a fault
    When I withdraw $50
    Then I should see an out-of-order message
    And $0 should be dispensed
    And the balance of my account should be $100

  Scenario: Ask to withdraw less before submit
    Given my account is in credit
    And ATM contains $100
    When I type $120
    Then I should see ask-for-less message