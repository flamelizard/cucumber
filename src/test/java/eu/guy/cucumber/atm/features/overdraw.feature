@atm-web
Feature: Prevent overdraw

  Scenario: Account balance cannot be overdrawn
    Given my account has been credited with $20
    When I withdraw $50
    Then $0 should be dispensed
    And I should be told that I have insufficient funds

  Scenario: Cannot withdraw more than ATM funds
    Given my account is in credit
    When ATM contains $10
    When I withdraw $50
    Then I should see ask-for-less message
    And $0 should be dispensed
    And my balance is unchanged