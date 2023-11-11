Feature:
  As a user,
  I want to delete a relationship between Todo and categories
  So that I can manage my todo category list. - Ankur

  Background:
    Given the service is running

  Scenario Outline: Remove relationship between a specific todo and project (Normal Flow)
    Given I have a todo with the ID "<todoId>"
    And I have a category related to todos "<todoId>"
    When I request to delete the relationship between todo "<todoId>" and category "<categoryId>" relationship
    Then the relationship between todo "<todoId>" and category "<categoryId>" should no longer exist
    Examples:
      | todoId | categoryId |
      | 1      | 1         |


