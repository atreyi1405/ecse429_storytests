Feature: Adding a todo instance to a category

//Background: User is logged into the application 

#normal_flow
Scenario: As a user, I want to class my todo into a category
    Given an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
    And an existing todo list with each item consisting of title <todo_title>, description <todo_description>, donestatus <completed> and id <todo_id>
    When a user creates a relationship between the category with id <category_id> and the todo with id <todo_id>
    Then the relationship named "todos" should be established between them

    Examples:
      | category_id | category_title | category_description | todo_id | todo_title | todo_description            | completed |
      | 1           | engineering    | ecse429              | 2       | Part B     | complete gherkin scenarios  | false     |


#alternative_flow
Scenario: As a user, I want to add a completed todo item to a category so that I can view what has been done
    Given an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
    And an existing todo list with each item consisting of title <todo_title>, description <todo_description>, donestatus <completed> and id <todo_id>
    When a user creates a relationship between the category with id <category_id> and the todo with id <todo_id>
    Then the relationship named "todos" should be established between them
        
    Examples:
        | category_id | category_title  | category_description | todo_id | todo_title  | todo_description             | completed |
        | 1           | Work Projects   | Ongoing tasks        | 3       | Task 1      | Complete task for project A  | true      |
        | 2           | Personal Tasks  | Household chores     | 4       | Chores 1    | Grocery shopping             | true      |

#error_flow
Scenario: As a user, I want to class my todo into a category
    Given an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
    And an existing todo list with each item consisting of title <todo_title>, description <todo_description>, donestatus <completed> and id <todo_id>
    When a user creates a relationship with the category with id <category_id>
    Then this task fails since the todo_id field is mandatory

    Examples:
    "Failed Validation: todo_id: cannot be empty"



    



        


