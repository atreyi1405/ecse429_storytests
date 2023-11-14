Feature: Get Todo connected to a project
  As a user,
  I want to get todos related to a project,
  so that I can edit it, or assign it to projects and categories

  Background:
    Given the app is running


  Scenario Outline: Retrieve tasks by project identifier (Normal Flow)
    Given a project item exists with identifier "<projectid>"
    When I request tasks assigned to project with identifier "<projectid>"
    Then the response should include a status code of "200"
    Examples:
      | projectid |
      | 1       |


  Scenario Outline: Get a specific tasks by filtering tasks list with id (Alternate Flow)
    Given a project item exists with identifier "<projectid>"
    When I request a specific task assigned to project by filtering with the requested task id "<taskid>"
    Then the response should include a status code of "200"
    Examples:
      | projectid | taskid |
      | 1       | 1        |



  Scenario Outline: Get a nonexistent task (Error Flow)
    Given the project list does not contain id needed to check for tasks "<projectId>"
    When I request the project item with id "<projectId>"
    Then the response should include a status code of "404"
    Examples:
      | projectId | errorMessage                              |
      | 0      | Could not find an instance with todos/0   |
      | 100    | Could not find an instance with todos/100 |




