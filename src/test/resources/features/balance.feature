@atm-web
Feature: Account balance

  @smoke @bug-xy
  Scenario: User can check its account balance
    Given my account has been credited with $15
    When I check my balance
    Then I should see that my balance is $15