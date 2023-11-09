Feature: Deleting an instance of the relationship named categories between project and category

//Background: User is logged into the application 

#normal_flow
Scenario: As a user, I want to delete a category from my project
    Given an existing project list with each item consisting of title <project_title>, description <project_description>, donestatus <completed> and id <project_id>
    And an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
    When a user deletes the relationship between the category with id <project_id> and the category with id <category_id>
    Then the category should be removed from the project

    Examples:
#dont know how to do yet 


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



    



        


