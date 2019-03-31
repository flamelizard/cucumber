Feature: Checkout

  Scenario Outline: Checkout a banana
    Given the price of a "banana" is 2Kc
    When I checkout <count> "banana"
    Then total price should be <total>Kc

    Examples:
      | count | total |
      | 1     | 2     |
      | 4     | 8     |

  Scenario: Banana and apple
    Given the price of a "banana" is 3Kc
    And the price of a "apple" is 5Kc
    When I checkout 3 "banana"
    And I checkout 2 "apple"
    Then total price should be 19Kc
