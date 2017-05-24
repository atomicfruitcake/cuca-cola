@Tests
Feature: Google Search Test

  Scenario: User can search on google
    Given I navigate to the google home page
    And I search for "Foo"
    When I click the search button
    Then I see my search results