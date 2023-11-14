Feature: Delete the relationship tasksof between a todo and a project
  As a user,
  I want to delete a tasksof relationship between a todo and project,
  so that I can manage my projects task list.

  Background:
    Given the service is running

  Scenario Outline: Delete a tasksof relationship between todo and project by id  (Normal Flow)
    Given the todo with id "<todoId>" exists
    And the project with id "<projectId>" exists
    And there is a tasksof relationship between todo with id "<todoId>" and project with id "<projectId>"
    When I request to delete the tasksof relationship between todo with id "<todoId>" and project with id "<projectId>"
    Then the tasksof relationship between todo with id "<todoId>" and project with id "<projectId>" should be deleted
    And a status code "<code>" is returned
    Examples:
      | todoId | projectId | code |
      | 2      | 1         | 200  |
      | 1      | 1         | 200  |


  Scenario Outline: Delete a tasksof relationship between todo and project using put method (Alternate Flow)
    Given the todo with id "<todoId>" exists
    And the project with id "<projectId>" exists
    And there is a tasksof relationship between todo with id "<todoId>" and project with id "<projectId>"
    When I delete the tasksof relationship between todo with id "<todoId>" and project with id "<projectId>" using put method
    Then the tasksof relationship between todo with id "<todoId>" and project with id "<projectId>" should be deleted
    And a status code "<code>" is returned
    Examples:
      | todoId | projectId | code |
      | 2      | 1         | 200  |
      | 1      | 1         | 200  |


  Scenario Outline: Delete a tasksof relationship between todo and nonexistent project by id in endpoint (Error Flow)
    Given the todo with id "<todoId>" exists
    And the project with id "<projectId>" does not exist
    When I delete the nonexistent tasksof relationship between todo with id "<todoId>" and project with id "<projectId>" using id in endpoint
    Then the response body has the error message "<errorMessage>"
    And a status code "<code>" is returned
    Examples:
      | todoId | projectId | errorMessage                                          | code |
      | 1      | 50       | Could not find any instances with todos/1/tasksof/50 | 404  |
      | 2      | 70         | Could not find any instances with todos/2/tasksof/70   | 404  |