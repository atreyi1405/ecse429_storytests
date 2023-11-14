Feature: Adding a todo
    As a user,
    I want to add a todo,
    to keep track of my small tasks to complete.

    Background:
        Given the app is running

        #Normal Flow
        Scenario Outline: Create a new todo with title and description
            When a user adds a todo with title "<todoTitle>" and description "<todoDescription>"
            Then a new todo with title "<todoTitle>" and description "<todoDescription>"
            And a status code "201" with response phrase "Created" is returned
            Examples:
            |todoTitle  | todoDescription  |
            |Scan       | Scan paperwork   |

            #Alternative Flow
        Scenario Outline: Create a new todo with title
            When a user adds a todo with title "<todoTitle>"
            Then a new todo with title "<todoTitle>"
            And a status code "201" with response phrase "Created" is returned
            Examples:
                |todoTitle  |
                |Scan       |

            #Error Flow
        Scenario: Create a new todo with empty title
            When a user adds a todo with title ""
            Then the todo is not added
            And a status code "400" with response phrase "Bad Request" is returned




        


