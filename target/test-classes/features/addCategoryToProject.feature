Feature: As a user, I want to add a relationship categories with id of a project

  Background:
      Given the app is running


    #normal_flow
    Scenario: Add a relationship between a specific project and already defined category
        #Given I have a project with ID "<project_id>"
        #And I have a category with ID "<category_id>"
        When I request to add a relationship categories between project "<project_id>" and categories "<category_id>"
        Then the relationship between project "<project_id>" category "<category_id>" should be created

        Examples:
          | project_id  | category_id |
          | 50          | 51          |




    #alternative_flow
    Scenario: Add a relationship between a specific project and a created category
        //Given an existing project list with each item consisting of title <project_title>, description <project_description>, donestatus <completed> and id <project_id>
        Given I create a category with title "<title>", description "<description>"
        When a user adds a relationship between project with id <project_id> and the category with id <category_id>
        Then the relationship named "categories" should be established between them

         Examples:
          | project_id  | project_title     | project_description   | completed | category_id | category_title | category_description            |
          | 8           | Final Project     | Software Validation   | true      | 9           | Part A         | Exploratory testing of rest API |

    #error_flow
    Scenario: As a user, I want to class my category into a project
        //Given an existing project list with each item consisting of title <project_title>, description <project_description>, donestatus <completed> and id <project_id>
        //And an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
        When a user creates a relationship between project with id <project_id>
        Then this task fails since the category_id field is mandatory




    



        


