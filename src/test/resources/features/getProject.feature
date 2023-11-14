Feature: Get project by ID
    As a user,
    I want to view my projects,
    to assess the work I have to do.

	Background:
        Given the app is running

    # Normal Flow
    Scenario Outline: Get a project by its ID
        Given a project with title "<project_title>", description "<project_description>", completed status "<completed_status>", and active_status "<active_status>" exists
        When a user gets the project with title "<project_title>" by id
        Then the project with title "<project_title>" is returned
        And a status code "200" is returned
        Examples:
            | project_title | project_description | completed_status | active_status |
            | ecse429       | Fall 2023           | False            | True          |
            | ecse223       | Fall 2021           | False            | True          |
            | ecse415       | Fall 2023           | False            | True          |

    # Alternate Flow
    Scenario Outline: Get all projects
        Given there are a number of projects "<num_of_projects>" in the system
        When a user gets all of the the projects in the system
        Then "<num_of_projects>" projects are returned
        And a status code "200" is returned
        Examples:
            | num_of_projects |
            | 9               |
            | 2               |
            | 3               |

    # Error Flow
    Scenario Outline: Get a project by ID that does not exist
        Given a project with title "<project_title>", description "<project_description>", completed status "<completed_status>", and active_status "<active_status>" exists
        When a user gets the project with id "<attempted_project_id>"
        Then a status code "404" is returned
        Examples:
            | project_title | project_description | completed_status | active_status | attempted_project_id |
            | ecse429       | Fall 2023           | False            | True          | -1                   |
            | ecse223       | Fall 2021           | False            | True          | 0                    |
            | ecse415       | Fall 2022           | False            | True          | -999                 |