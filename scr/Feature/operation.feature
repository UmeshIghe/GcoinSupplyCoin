Feature: Title of your feature
  I want to use this template for my feature file

  @tagOpe1
  Scenario: Title of your scenario
    Given user wants to do addition of "4" and "8"
    Then addition should be "11"

  @tagOpe2
  Scenario: Title of your scenario
    Given user wants to do addition of "5" and "8"
    Then addition should not be "13"

  @tagOpe
  Scenario Outline: Title of your scenario outline
    Given user wants to do addition of <a> and <b>
    Then addition should be <c>

    Examples: 
      | a | b | c  |
      | 4 | 5 |  9 |
      | 3 | 7 | 10 |
      | 4 | 7 | 12 |
      | 3 | 6 |  9 |
