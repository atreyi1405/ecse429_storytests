Feature: Adding a project instance to a category

//Background: User is logged into the application 

#normal_flow
Scenario: As a user, I want to class my project into a category
    Given an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
    And an existing project list with each item consisting of title <project_title>, description <project_description>, donestatus <completed> and id <project_id>
    When a user creates a relationship between the category with id <category_id> and the project with id <project_id>
    Then the relationship named "projects" should be established between them

    Examples:
      | category_id | category_title | category_description | project_id | project_title        | project_description            | completed |
      | 1           | engineering    | ecse429              | 2          | Final Project        | software Validation            | false     |


#alternative_flow
Scenario: As a user, I want to add a completed project item to a category so that I can view what has been done
    Given an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
    And an existing project list with each item consisting of title <project_title>, description <project_description>, donestatus <completed> and id <project_id>
    When a user creates a relationship between the category with id <category_id> and the project with id <project_id>
    Then the relationship named "projects" should be established between them
        
    Examples:
      | category_id | category_title | category_description | project_id | project_title        | project_description            | completed |
      | 3           | engineering    | ecse429              | 4          | Homework assignments | In class homeworks             | true      |

#error_flow
Scenario: As a user, I want to class my project into a category
    Given an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
    And an existing project list with each item consisting of title <project_title>, description <project_description>, donestatus <completed> and id <project_id>
    When a user creates a relationship with the category with id <project_id>
    Then this task fails since the project_id field is mandatory

    Examples:
    "Failed Validation: project_id: cannot be empty"



    



        


