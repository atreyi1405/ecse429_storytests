Feature:
  As a user,
  I want to add a relationship tasksof with id of a todo

  Background:
    Given the service is running


  Scenario Outline: Add a relationship between a specific todo and project
    Given I have a todo with ID "<todoID>"
#    And a project with the ID "<projectID>"
    When I request to add a relationship between todo "<todoID>" and project "<projectID>"
    Then the relationship between todo "<todoID>" and project "<projectID>" should be created
    And I should receive a status code "200" with the message "Relationship added successfully"

    Examples:
      | todoID  | projectID |
      | 1       | 2         |




