Feature: Adding a category
  As a user,
  I want to add a category,
  to group similar or related todos and projects.

  Background:
    Given the app is running

    #Normal Flow
  Scenario Outline: Create a new category with title and description
    When a user adds a category with title "<categoryTitle>", and description "<categoryDescription>"
    Then a new category with title "<categoryTitle>" and description "<categoryDescription>" is added
    And a status code "201" with response phrase "Created" is returned

    Examples:
    |categoryTitle| categoryDescription|
    |Office       |  Meeting           |

    #Alternative Flow
  Scenario Outline: Create a new category with title
    When a user adds a category with title "<categoryTitle>"
    Then a new category with title "<categoryTitle>" is added
    And a status code "201" with response phrase "Created" is returned

    Examples:
    |categoryTitle|  categoryDescription  |
    |Office       |                       |

    #Error Flow

  Scenario: Create a new category with empty title
    When a user adds a category with title ""
    Then the category is not added
    And a status code "400" with response phrase "Bad Request" is returned










    



        


