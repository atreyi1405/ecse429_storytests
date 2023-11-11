Feature: As a user, I want to add a relationship todos with id of a category
    Background:
        Given the app is running

        #normal_flow
        Scenario: Add a relationship between a specific category and already defined todo
            #Given I have a project with ID "<category_id>"
            #And I have a category with ID "<todo_id>"
            When I request to add a relationship todos between categories "<category_id>" and todos "<todo_id>"
            Then the relationship between category "<category_id>" todo "<todo_id>" should be created

            Examples:
                | category_id  | todo_id     |
                | 5            | 1           |


        #alternative_flow
        Scenario: Add a relationship between a specific category and a created todo
            #And I have a category with ID "<category_id>"
            Given I create a todo with title "<title>", description "<description>"
            When I request to add a relationship todos between categories "<category_id>" and todos "<todo_id>"
            Then the relationship between category "<category_id>" todo "<todo_id>" should be created

            Examples:
                | category_id  | title   | description | todo_id |
                | 5            | testing | hello       | 1       |

        #error_flow
        Scenario: Add a relationship todos between a category and a non existent todo
            #And I have a category with ID "<category_id>"
            When I request to add a relationship todos between category "<category_id>" and a non existent todo with id "<todo_id>"
            Then I get an error code "404"

            Examples:
                | category_id | todo_id |
                | 1           | 999     |







        


