@atm-web
Feature: Prevent overdraw

  @debug
  Scenario: Account balance cannot be overdrawn
    Given my account has been credited with $20
    When I withdraw $50
    Then $0 should be dispensed
    And I should be told that I have insufficient funds