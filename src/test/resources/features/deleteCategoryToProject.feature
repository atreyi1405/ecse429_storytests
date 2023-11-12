Feature:   As a user, I want to delete a relationship between project and categories


    Background:
        Given the app is running

    #normal_flow
    Scenario: Remove relationship between a specific category and project
        Given I have a project with ID "<project_id>"
        #And I have a category with ID "<category_id>"
        When I request to delete the relationship between projects "<project_id>" and categories "<categories_id>" relationship
        Then the relationship between projects "<project_id>" and categories "<categories_id>" should no longer exist

        Examples:
            | project_id  | categories_id  |
            | 3           | 1             |


    #alternative_flow
    Scenario: As a user, I want to delete a category from a completed project
        Given an existing project list with each item consisting of title <project_title>, description <project_description>, donestatus <completed> and id <project_id>
        And an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
        When a user creates a relationship between the category with id <project_id> and the category with id <category_id>
        Then the category should be removed from the project

        Examples:
    #dont know how to do yet


    #error_flow
    Scenario: As a user, I want to delete a category from my project
        Given an existing project list with each item consisting of title <project_title>, description <project_description>, donestatus <completed> and id <project_id>
        And an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
        When a user deletes the relationship between the category with id <project_id>
        Then the category should fail to be removed from the project

        Examples:
    #dont know how to do yet



    



        


