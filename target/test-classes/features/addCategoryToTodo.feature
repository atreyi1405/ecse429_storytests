Feature: As a user, I want to add a relationship categories with id of a todo
  Background:
    Given the app is running

        #normal_flow
  Scenario Outline: Add a relationship between a specific todo and already defined category
    Given I have a todo with ID "<todo_id>"
    And I have a category with ID "<category_id>"
    When I request to add a relationship categories between todos "<todo_id>" and categories "<category_id>"
    Then the relationship between todo "<todo_id>" category "<category_id>" should be created

    Examples:
      | todo_id | category_id |
      | 1       | 1           |


        #alternative_flow
  Scenario Outline: Add a relationship between a specific todo and a created category
    Given I have a todo with ID "<todo_id>"
    Given I create a category with title "<title>", description "<description>"
    When I request to add a relationship categories between todos "<todo_id>" and categories "<category_id>"
    Then the relationship between todo "<todo_id>" category "<category_id>" should be created

    Examples:
      | todo_id | title   | description | category_id |
      | 1       | testing | hello       | 1           |

        #error_flow
  Scenario Outline: Add a relationship categories between a todo and a non existent category
    Given I have a todo with ID "<category_id>"
    When I request to add a relationship categories between todos "<todo_id>" and a non existent category with id "<category_id>"
    Then I get an error code "404"

    Examples:
      | category_id | todo_id |
      | 1           | 999     |







        


