Feature: Get Todo By Id
  As a user,
  I want to get a todo by id, so I can edit it, or assign it to projects and categories

  Background:
    Given the app is running


  Scenario Outline: Retrieve a specific todo item by its identifier (Normal Procedure)
    Given a todo item exists with identifier "<todoId>"
    When I request the todo item with identifier "<todoId>"
    Then the response should include a status code of "200" and title "<title>"
    Examples:
      | todoId | title |
      | 1      | scan paperwork |
      | 2      | file paperwork |


  Scenario Outline: Get a todo by filtering todos list with id (Alternate Flow)
    Given the todo with id "<todoId>" exists
    When I filter the todos list to get the todo with id "<todoId>"
    Then a status code "200" and title "<title>" is returned
    Examples:
      | todoId | title |
      | 1      | scan paperwork |
      | 2      | file paperwork |



  Scenario Outline: Get a nonexistent todo (Error Flow)
    Given the todos list does not contain id "<todoId>"
    When I request the todo item with id "<todoId>"
    Then a status code of "404" is returned
    Examples:
                  | todoId | errorMessage                              |
                  | 0      | Could not find an instance with todos/0   |
                  | 100    | Could not find an instance with todos/100 |




