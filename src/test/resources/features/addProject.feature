Feature: Adding Project
  As a user, I want to add categories so that I can group similar categories.

  Background:
    Given the app is running

    #Normal Flow
  Scenario Outline: Create a new project with title
    When a user adds a project with title "<projectTitle>", and description "<projectDescription>"
    Then a new project with title "<projectTitle>", and description "<projectDescription>" is added
    And a status code "201" with response phrase "Created" is returned
    Examples:
    |projectTitle  |  projectDescription  |
    |Office        |                      |

    #Alternative Flow
  Scenario Outline: Create a new project with title
    When a user adds a project with title "<projectTitle>"
    Then a new project with title "<projectTitle>" is added
    And a status code "201" with response phrase "Created" is returned
    Examples:
      | projectTitle    |
      | Office          |





    



        


