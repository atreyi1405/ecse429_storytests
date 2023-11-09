Feature: Adding a category instance to a project

//Background: User is logged into the application 

#normal_flow
Scenario: As a user, I want to class my category into a project
    Given an existing project list with each item consisting of title <project_title>, description <project_description>, donestatus <completed> and id <project_id>
    And an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
    When a user creates a relationship between the category with id <project_id> and the category with id <category_id>
    Then the relationship named "categories" should be established between them

    Examples:
      | project_id  | project_title     | project_description   | completed | category_id | category_title | category_description      | 
      | 6           | Final Project     | software Validation   | false     | 7           | Part B         | Story Testing of Rest API |



#alternative_flow
Scenario: As a user, I want to add a category to a completed project so that I can categorize what has been done  
    Given an existing project list with each item consisting of title <project_title>, description <project_description>, donestatus <completed> and id <project_id>
    And an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
    When a user creates a relationship between the category with id <project_id> and the category with id <category_id>
    Then the relationship named "categories" should be established between them
        
     Examples:
      | project_id  | project_title     | project_description   | completed | category_id | category_title | category_description            | 
      | 8           | Final Project     | Software Validation   | true      | 9           | Part A         | Exploratory testing of rest API |

#error_flow
Scenario: As a user, I want to class my category into a project
    Given an existing project list with each item consisting of title <project_title>, description <project_description>, donestatus <completed> and id <project_id>
    And an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
    When a user creates a relationship between the category with id <project_id> 
    Then this task fails since the category_id field is mandatory

    Examples:
    "Failed Validation: category_id: cannot be empty"



    



        


