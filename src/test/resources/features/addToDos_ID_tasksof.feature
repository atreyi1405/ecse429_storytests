Feature:
  As a user,
  I want to add a relationship tasksof with id of a todo

  Background:
    Given the service is running


  Scenario Outline: Add a relationship tasksof between a specific todo and already defined project
    Given I have a todo with ID "<todoID>"
    And I have a project with ID "<projectID>"
    When I request to add a relationship tasksof between todo "<todoID>" and projects "<projectID>"
    Then the relationship between todo "<todoID>" and project "<projectID>" should be created

    Examples:
      | todoID  | projectID |
      | 2       | 1         |

  Scenario Outline: Add a relationship tasksof between a specific todo and a created project
    Given I have a todo with ID "<todoID>"
    And I create a project with title "<title>", completed "<completed>", description "<description>", active "<active>"
    When I request to add a relationship tasksof between todo "<todoID>" and projects "<projectID>"
    Then the relationship between todo "<todoID>" and project "<projectID>" should be created

    Examples:
      | todoID  | title | completed | description | active | projectID |
      | 2       | title | true      | hero        | false  | 2         |


  Scenario Outline: Add a relationship tasks of between a todo and a non existent project
    Given I have a todo with ID "<todoID>"
    When I request to add a relationship tasksof between todo "<todoID>" and a non existent project with id "<projID>"
    Then I get an error code "404"
    Examples:
      | todoID | projID |
      | 1      |  1233  |
