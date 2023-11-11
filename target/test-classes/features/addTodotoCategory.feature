Feature: Adding a todo instance to a category
    Background:
        Given the app is running

        #normal_flow
        Scenario: Create a relationship between todo and category
            //Given a category with title <category_title>, description <category_description>, and id <category_id>
            //And a todo list with title <todo_title>, description <todo_description>, donestatus <completed> and id <todo_id>
            When a user adds a relationship between a category with id <category_id> and a todo with id <todo_id>
            Then a status code "201" with response phrase "Created" is returned

            Examples:
            | category_id   | category_title | category_description | todo_id  | todo_title | todo_description            | completed |
            | 1           | engineering    | ecse429              | 2      | Part B     | complete gherkin scenarios  | false     |


        #alternative_flow
        Scenario: Create a relationship between a completed todo and category
            //Given an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
            //And an existing todo list with each item consisting of title <todo_title>, description <todo_description>, donestatus <completed> and id <todo_id>
            When a user adds a relationship between a category with id <category_id> and a todo with id <todo_id>
            Then a status code "201" with response phrase "Created" is returned

            Examples:
            | category_id | category_title  | category_description | todo_id | todo_title  | todo_description             | completed |
            | 1           | Work Projects   | Ongoing tasks        | 3       | Task 1      | Complete task for project A  | true      |
            | 2           | Personal Tasks  | Household chores     | 4       | Chores 1    | Grocery shopping             | true      |

        #error_flow
        Scenario: Create a relationship between todo and category without id
            //Given an existing category list with each item consisting of title <category_title>, description <category_description>, and id <category_id>
            //And an existing todo list with each item consisting of title <todo_title>, description <todo_description>, donestatus <completed> and id <todo_id>
            When a user creates a relationship with the category with id <category_id>
            Then the relationship is not added
            And a status code "400" with response phrase "Bad Request" is returned




    



        


