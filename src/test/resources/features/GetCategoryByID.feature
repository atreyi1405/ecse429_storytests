Feature: Get Categories By Id
  As a user,
  I want to get a category by id to view,
  so that I can assign todos and projects to categories.

  Background:
    Given the app is running


  Scenario Outline: Retrieve a specific category item by its identifier (Standard Procedure)
    Given a category item exists with identifier "<categoryId>"
    When I request the category item with identifier "<categoryId>"
    Then the output should include a status code of "200"
    Examples:
      | categoryId |
      | 1      |
      | 2      |


  Scenario Outline: Get a category by filtering category list with id (Alternate Flow)
    Given the category with id "<categoryId>" exists
    When I filter the category list to get the todo with id "<categoryId>"
    Then a output code "200" is returned
    Examples:
      | categoryId  |
      | 1       |
      | 2       |


  Scenario Outline: Get a nonexistent category (Error Flow)
    Given the category list does not contain id "<categoryId>"
    When I request the category item with id "<categoryId>"
    Then a output code of "404" is returned
    Examples:
      | categoryId | errorMessage                              |
      | 0      | Could not find an instance with todos/0   |
      | 100    | Could not find an instance with todos/100 |




