Feature: Delete a project
  As a user,
  I want to delete a project,
  to keep an updated record of relevant projects.

  Background:
    Given the app is running

    #Normal Flow
  Scenario Outline: Delete a project by id
    Given the project with id "<projectId>" exists
    When a user deletes the project with id "<projectId>"
    And a status code "200" with response phrase "OK" is returned

    Examples:
      |projectId|
      |1     |


      #Alternative Flow
  Scenario Outline: Delete a project by filtering endpoint with id
    Given the project with id "<projectId>" exists
    When user filters the endpoint to delete project with id "<projectId>"
    And a status code "200" with response phrase "OK" is returned
    Examples:
      |projectId|
      |1     |

        #Error Flow
  Scenario: Delete a project that does not exist
    Given the project with id "<projectId>" does not exist
    When a user deletes the project with id "<projectId>"
    Then a status code "404" with response phrase "Not Found" is returned

