Feature:
  As a user,
  I want to delete a tasksof relationship between a todo and project
  So that I can manage my projects task list. - Ankur

  Background:
    Given the service is running

  Scenario Outline: Remove relationship between a specific todo and project
    Given I have a todo with the ID "<todoId>"
    And I have a project with the ID "<projectId>"
    And a relationship exists between todo "<todoId>" and project "<projectId>"
    When I request to delete the relationship between todo "<todoId>" and project "<projectId>" through the API endpoint
    Then the relationship between todo "<todoId>" and project "<projectId>" should no longer exist
    And I should receive a status code "200" with the message "Relationship removed successfully."
    Examples:
      | todoId | projectId |
      | 2      | 1         |
      | 1      | 1         |


  Scenario Outline: Delete a tasksof relationship between todo and project using put method (Alternate Flow)
    Given I have a todo with id "<todoId>"
    And I have a project with id "<projectId>"
    And a tasksof relationship exists between todo with id "<todoId>" and project with id "<projectId>"
    When I use the put method to delete the tasksof relationship for todo id "<todoId>" and project id "<projectId>"
    Then the tasksof relationship for todo id "<todoId>" and project id "<projectId>" should be removed
    And I should receive a status code "200" with a response message "OK"
    Examples:
      | todoId | projectId |
      |--------|-----------|
      | 2      | 1         |
      | 1      | 1         |


  Scenario Outline: Delete a relationship between a todo and a project

    Given the project with id "<projectId>" exists
    And the todo with id "<todoId>" exists
    And a tasksOf relationship between todo with id "<todoId>" and project with id "<projectId>" is present
    When I delete the tasksOf relationship between todo with id "<todoId>" and project with id "<projectId>" using the put method
    Then a status code "200" with response phrase "OK" is returned
    And the tasksOf relationship between todo with id "<todoId>" and project with id "<projectId>" no longer exists
    When I attempt to delete the tasksOf relationship between todo with id "<todoId>" and project with id "<projectId>" again
    Then the response body contains the error message "<errorMessage>"
    And a status code "404" with response phrase "Not Found" is returned

    Examples:
      | todoId | projectId | errorMessage                                              |
      | 1      | 1         | Could not find any instances with todos/1/tasksOf/1      |
      | 2      | 1         | Could not find any instances with todos/2/tasksOf/1      |


  Scenario Outline: Delete a tasksof relationship between nonexistent todo and project by id in endpoint (Error Flow)
    Given the todo with id "<todoId>" does not exist
    And the project with id "<projectId>" exists
    When I delete the nonexistent tasksof relationship between todo with id "<todoId>" and project with id "<projectId>" using id in endpoint
    Then the response body has the error message "Cannot invoke \"uk.co.compendiumdev.thingifier.core.domain.instances.ThingInstance.getRelationships()\" because \"parent\" is null"
    And a status code "400" with response phrase "Bad Request" is returned
    Examples:
      | todoId | projectId |
      | 0      | 1         |
      | 100    | 1         |

  Scenario Outline: Delete a tasksof relationship between todo and nonexistent project by id in endpoint (Error Flow)
    Given the todo with id "<todoId>" exists
    And the project with id "<projectId>" does not exist
    When I delete the nonexistent tasksof relationship between todo with id "<todoId>" and project with id "<projectId>" using id in endpoint
    Then the response body has the error message "<errorMessage>"
    And a status code "404" with response phrase "Not Found" is returned
    Examples:
      | todoId | projectId | errorMessage                                          |
      | 1      | 100       | Could not find any instances with todos/1/tasksof/100 |
      | 2      | 0         | Could not find any instances with todos/2/tasksof/0   |