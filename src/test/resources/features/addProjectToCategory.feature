Feature:  Adding Project To Category
  As a user,
  I want to add a relationship projects with id of a category,
  so that I can organizing and link related projects.

  Background:
      Given the app is running

      #normal_flow
  Scenario Outline: Add a relationship between a specific category and already defined project
    Given I have a category with ID "<category_id>"
    And I have a project with ID "<project_id>"
    When I request to add a relationship projects between categories "<category_id>" and projects "<project_id>"
    Then the relationship between category "<category_id>" project "<project_id>" should be created

    Examples:
      | category_id | project_id |
      | 1           | 1          |

      #alternative_flow
  Scenario Outline: Add a relationship between a specific category and a created project
    Given I have a category with ID "<category_id>"
    And I create a project with title "<title>", completed "<completed>", description "<description>", active "<active>"
    When I request to add a relationship projects between categories "<category_id>" and projects "<project_id>"
    Then the relationship between category "<category_id>" project "<project_id>" should be created

    Examples:
      | category_id | title | completed | description | active | project_id |
      | 2           | title | true      | hero        | false  | 2          |



      #error_flow
  Scenario Outline: Add a relationship between a specific category and a non existent project
    Given I have a category with ID "<category_id>"
    When I request to add a relationship projects between category "<category_id>" and a non existent project with id "<projects_id>"
    Then I get an error code "404"

    Examples:
      | category_id | projects_id |
      | 1           | 999         |



    



        


