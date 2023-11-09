Feature: Deleting an instance of the relationship named projects between categories and projects

//Background: User is logged into the application 

#normal_flow
Scenario: As a user, I want to delete a project from my category
    Given an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
    And an existing project list with each item consisting of title <project_title>, description <project_description>, donestatus <completed> and id <project_id>
    When a user deletes a relationship between the category with id <category_id> and the project with id <project_id>
    Then the category should be removed from the project

    Examples:
#dont know how to do yet 


#alternative_flow
Scenario: As a user, I want to delete a completed project from my category
    Given an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
    And an existing project list with each item consisting of title <project_title>, description <project_description>, donestatus <completed> and id <project_id>
    When a user deletes a relationship between the category with id <category_id> and the project with id <project_id>
    Then the category should be removed from the project
        
    Examples:
#dont know how to do yet 


#error_flow
Scenario: As a user, I want to delete a project from my category
    Given an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
    And an existing project list with each item consisting of title <project_title>, description <project_description>, donestatus <completed> and id <project_id>
    When a user deletes a relationship between the category with id <category_id> 
    Then the category should fail to be removed

    Examples:
#dont know how to do yet



    



        


