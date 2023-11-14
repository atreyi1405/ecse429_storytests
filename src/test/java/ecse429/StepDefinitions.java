package ecse429;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.Response;
import org.example.Api;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StepDefinitions {
    Response response;
    JSONObject responseBody;
    int previousCategoriesCount;
    int previousProjectCount;
    int currentCategoriesCount;
    int currentProjectCount;
    int currentTodosCount;
    int previousTodosCount;

    String status_code;
    String last_status;

    String view_todos_status_code;
    String view_todos_last_status;

    String view_todos_string;
    List<String> todoCategoriesIds;
    String temp;
    String add_status_code;
    @Given("the app is running")
    public void the_app_is_running() {
        Api call = new Api();
        // Verify that the server is running
        response = call.checkService();
        assertEquals(200, response.code());

    }


    @Given("the category with id {string} exists")
    public void the_category_with_id_exists(String categoryId) {
        Api call = new Api();
        boolean categoryExists = false;
        Response getCategories = call.getRequest("categories", "json");

        try {
            JSONObject getCategoriesResponseBody = new JSONObject(getCategories.body().string());
            JSONArray categoriesArray = getCategoriesResponseBody.getJSONArray("categories");
            for (Object obj : categoriesArray) {
                JSONObject category = (JSONObject) obj;
                String categoryIdStr = category.getString("id");
                if (categoryIdStr.equals(categoryId)) {
                    categoryExists = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Assert that the specified category exists
        assertTrue(categoryExists, "ERROR: The Category with id " + categoryId + " does not exist.");

    }


    @Given("the todo with id {string} exists")
    public void the_todo_with_id_exists(String todoId) {
        Api call = new Api();
        boolean todoExists = false;
        Response getTodos = call.getRequest("todos", "json");

        try {
            JSONObject getTodosResponseBody = new JSONObject(getTodos.body().string());
            JSONArray todosArray = getTodosResponseBody.getJSONArray("todos");
            for (Object obj : todosArray) {
                JSONObject todo = (JSONObject) obj;
                String todoIdStr = todo.getString("id");
                if (todoIdStr.equals(todoId)) {
                    todoExists = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(todoExists, "ERROR: The todo does not exist in the system.");

    }

    @Given("the project with id {string} exists")
    public void the_project_with_id_exists(String projectId) {
        Api call = new Api();
        boolean projectExists = false;
        Response getProjects = call.getRequest("projects", "json");

        try {
            JSONObject getProjectsResponseBody = new JSONObject(getProjects.body().string());
            JSONArray projectsArray = getProjectsResponseBody.getJSONArray("projects");
            for (Object obj : projectsArray) {
                JSONObject project = (JSONObject) obj;
                String projectIdStr = project.getString("id");
                if (projectIdStr.equals(projectId)) {
                    projectExists = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(projectExists, "ERROR: The project does not exist in the system.");

    }


    @Given("the todo with id {string} does not exist")
    public void the_todo_with_id_does_not_exist(String todoId) {
        Api call = new Api();
        boolean todoExists = false;
        Response getTodos = call.getRequest("todos", "json");

        try {
            JSONObject getTodosResponseBody = new JSONObject(getTodos.body().string());
            JSONArray todosArray = getTodosResponseBody.getJSONArray("todos");
            for (Object obj : todosArray) {
                JSONObject todo = (JSONObject) obj;
                String todoIdStr = todo.getString("id");
                if (todoIdStr.equals(todoId)) {
                    todoExists = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Assert that the specified todos does not exist
        assertFalse(todoExists, "ERROR: The todo exists in the system");
    }

    @Given("the project with id {string} does not exist")
    public void the_project_with_id_does_not_exist(String projectId) {
        Api call = new Api();
        boolean projectExists = false;
        Response getProjects = call.getRequest("projects", "json");

        try {
            JSONObject getTodosResponseBody = new JSONObject(getProjects.body().string());
            JSONArray projectsArray = getTodosResponseBody.getJSONArray("projects");
            for (Object obj : projectsArray) {
                JSONObject project = (JSONObject) obj;
                String projectIdStr = project.getString("id");
                if (projectIdStr.equals(projectId)) {
                    projectExists = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Assert that the specified todos does not exist
        assertFalse(projectExists, "ERROR: The project exists in the system");
    }

    @Given("the category with id {string} does not exist")
    public void the_category_with_id_does_not_exist(String categoryId) {
        Api call = new Api();
        boolean categoryExists = false;
        Response getCategories = call.getRequest("categories", "json");

        try {
            JSONObject getCategoriesResponseBody = new JSONObject(getCategories.body().string());
            JSONArray categoriesArray = getCategoriesResponseBody.getJSONArray("categories");
            for (Object obj : categoriesArray) {
                JSONObject category = (JSONObject) obj;
                String categoryIdStr = category.getString("id");
                if (categoryIdStr.equals(categoryId)) {
                    categoryExists = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Assert that the specified todos does not exist
        assertFalse(categoryExists, "ERROR: The category exists in the system");
    }

    @When("a user adds a category with title {string}, and description {string}")
    public void a_user_adds_a_category_with_title_and_description(String categoryTitle, String categoryDescription) {
        Api call = new Api();
        JSONObject getCategoryResponseBody = null;
        Response getPreviousCategories = call.getRequest("categories", "json");
        try {
            getCategoryResponseBody = new JSONObject(getPreviousCategories.body().string());
            previousCategoriesCount = getCategoryResponseBody.getJSONArray("categories").length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", categoryTitle);
        requestBody.put("description", categoryDescription);

        response = call.postRequest("categories", "json", requestBody);
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response getCurrentCategories = call.getRequest("categories", "json");
        try {
            getCategoryResponseBody = new JSONObject(getCurrentCategories.body().string());
            currentCategoriesCount = getCategoryResponseBody.getJSONArray("categories").length();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @When("a user adds a todo with title {string} and description {string}")
    public void a_user_adds_a_todo_with_title_and_description(String todoTitle, String todoDescription) {
        Api call = new Api();
        JSONObject getTodoResponseBody = null;
        Response getPreviousTodos = call.getRequest("todos", "json");
        try {
            getTodoResponseBody = new JSONObject(getPreviousTodos.body().string());
            previousTodosCount = getTodoResponseBody.getJSONArray("todos").length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", todoTitle);
        requestBody.put("description", todoDescription);

        response = call.postRequest("todos", "json", requestBody);
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response getCurrentCategories = call.getRequest("todos", "json");
        try {
            getTodoResponseBody = new JSONObject(getCurrentCategories.body().string());
            currentTodosCount = getTodoResponseBody.getJSONArray("todos").length();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @When("a user adds a todo with title {string}")
    public void a_user_adds_a_todo_with_title(String todoTitle) {
        Api call = new Api();
        JSONObject getTodoResponseBody = null;
        Response getPreviousTodos = call.getRequest("todos", "json");
        try {
            getTodoResponseBody = new JSONObject(getPreviousTodos.body().string());
            previousTodosCount = getTodoResponseBody.getJSONArray("todos").length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", todoTitle);
        response = call.postRequest("todos", "json", requestBody);
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response getCurrentTodos = call.getRequest("todos", "json");
        try {
            getTodoResponseBody = new JSONObject(getCurrentTodos.body().string());
            currentTodosCount = getTodoResponseBody.getJSONArray("todos").length();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @When("a user adds a category with title {string}")
    public void a_user_adds_a_category_with_title(String categoryTitle) {
        Api call = new Api();
        JSONObject getCategoryResponseBody = null;
        Response getPreviousCategories = call.getRequest("categories", "json");
        try {
            getCategoryResponseBody = new JSONObject(getPreviousCategories.body().string());
            previousCategoriesCount = getCategoryResponseBody.getJSONArray("categories").length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", categoryTitle);
        response = call.postRequest("categories", "json", requestBody);
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response getCurrentCategories = call.getRequest("categories", "json");
        try {
            getCategoryResponseBody = new JSONObject(getCurrentCategories.body().string());
            currentCategoriesCount = getCategoryResponseBody.getJSONArray("categories").length();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @When("a user adds a project with title {string}, and description {string}")
    public void a_user_adds_a_project_with_title_and_description(String projectTitle, String projectDescription) {
        Api call = new Api();
        JSONObject getProjectResponseBody = null;
        Response getPreviousProjects = call.getRequest("projects", "json");
        try {
            getProjectResponseBody = new JSONObject(getPreviousProjects.body().string());
            previousProjectCount = getProjectResponseBody.getJSONArray("projects").length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", projectTitle);
        requestBody.put("description", projectDescription);
        response = call.postRequest("projects", "json", requestBody);
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Response getCurrentProjects = call.getRequest("projects", "json");
        try {
            getProjectResponseBody = new JSONObject(getCurrentProjects.body().string());
            currentProjectCount = getProjectResponseBody.getJSONArray("projects").length();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @When("a user adds a project with title {string}")
    public void a_user_adds_a_project_with_title(String projectTitle) {
        Api call = new Api();
        JSONObject getProjectResponseBody = null;
        Response getPreviousProjects = call.getRequest("projects", "json");
        try {
            getProjectResponseBody = new JSONObject(getPreviousProjects.body().string());
            previousProjectCount = getProjectResponseBody.getJSONArray("projects").length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", projectTitle);

        response = call.postRequest("projects", "json", requestBody);
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Response getCurrentProjects = call.getRequest("projects", "json");
        try {
            getProjectResponseBody = new JSONObject(getCurrentProjects.body().string());
            currentProjectCount = getProjectResponseBody.getJSONArray("projects").length();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @When("a user deletes the category with id {string}")
    public void a_user_deletes_the_category_with_id(String categoryId) {
        Api call = new Api();
        response = call.deleteRequest("categories/" + categoryId);
    }

    @When("a user deletes the project with id {string}")
    public void a_user_deletes_the_project_with_id(String projectId) {
        Api call = new Api();
        response = call.deleteRequest("projects/" + projectId);
    }

    @When("a user deletes the todo with id {string}")
    public void a_user_deletes_the_todo_with_id(String todoId) {
        Api call = new Api();
        response = call.deleteRequest("todos/" + todoId);
    }


    @When("user filters the endpoint to delete todo with id {string}")
    public void user_filters_the_endpoint_to_delete_todo_with_id(String todoId) {
        Api call = new Api();
        response = call.getRequest("todos?id=" + todoId, "json");
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @When("user filters the endpoint to delete project with id {string}")
    public void user_filters_the_endpoint_to_delete_project_with_id(String projectId) {
        Api call = new Api();
        response = call.getRequest("projects?id=" + projectId, "json");
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @When("user filters the endpoint to delete category with id {string}")
    public void user_filters_the_endpoint_to_delete_category_with_id(String categoryId) {
        Api call = new Api();
        response = call.getRequest("categories?id=" + categoryId, "json");
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Then("a new category with title {string} is added")
    public void a_new_category_with_title_is_added(String categoryTitle) {
        assertEquals(1, currentCategoriesCount - previousCategoriesCount);
        assertEquals(categoryTitle, responseBody.getString("title"));

    }

    @Then("a new todo with title {string}")
    public void a_new_todo_with_title(String todoTitle) {
        assertEquals(1, currentTodosCount - previousTodosCount);
        assertEquals(todoTitle, responseBody.getString("title"));
    }

    @Then("a new project with title {string}, and description {string} is added")
    public void a_new_project_with_title_and_description_is_added(String projectTitle, String projectDescription) {
        assertEquals(1, currentProjectCount - previousProjectCount);
        assertEquals(projectTitle, responseBody.getString("title"));
        assertEquals(projectDescription, responseBody.getString("description"));
    }

    @Then("a new todo with title {string} and description {string}")
    public void a_new_todo_with_title_and_description(String todoTitle, String todoDescription) {
        assertEquals(1, currentTodosCount - previousTodosCount);
        assertEquals(todoTitle, responseBody.getString("title"));
        assertEquals(todoDescription, responseBody.getString("description"));

    }

    @Then("a new project with title {string} is added")
    public void a_new_project_with_title_is_added(String projectTitle) {
        assertEquals(1, currentProjectCount - previousProjectCount);
        assertEquals(projectTitle, responseBody.getString("title"));
    }

    @Then("a new category with title {string} and description {string} is added")
    public void a_new_category_with_title_and_description_is_added(String categoryTitle, String categoryDescription) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(1, currentCategoriesCount - previousCategoriesCount);
        assertEquals(categoryTitle, responseBody.getString("title"));
        assertEquals(categoryDescription, responseBody.getString("description"));
    }

    @Then("the project is not added")
    public void the_project_is_not_added() {
        assertEquals(0,currentProjectCount-previousProjectCount);
    }

    @Then("the todo is not added")
    public void the_todo_is_not_added(){
        assertEquals(0, currentTodosCount- previousTodosCount);
    }

    @Then("the category is not added")
    public void the_category_is_not_added() {
        assertEquals(0,currentCategoriesCount-previousCategoriesCount);
    }


    @Then("a status code {string} with response phrase {string} is returned")
    public void a_status_code_with_response_phrase_is_returned(String statusCode, String responsePhrase) {
        assertEquals(Integer.parseInt(statusCode), response.code(),
                "ERROR: The response phrase is: " + response.message() +
                        "\n instead of : " + responsePhrase);
        assertEquals(responsePhrase, response.message(), "ERROR: The actual response phrase does not match the expected response phrase.");
    }

    @Given("a category item exists with identifier {string}")
    public void a_category_item_exists_with_identifier(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/categories/"+string);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }
    @When("I request the category item with identifier {string}")
    public void i_request_the_category_item_with_identifier(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/categories/"+string);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int temp = connection_url.getResponseCode();
        status_code = Integer.toString(temp);
    }
    @Then("the output should include a status code of {string}")
    public void the_output_should_include_a_status_code_of(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(string, status_code);
    }

//    @Given("the category with id {string} exists")
//    public void the_category_with_id_exists1(String string) throws IOException {
//        // Write code here that turns the phrase above into concrete actions
//        URL url = new URL("http://localhost:4567/categories/"+string);
//        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
//        int status_code = connection_url.getResponseCode();
//        assertEquals(HttpURLConnection.HTTP_OK, status_code);
//    }
    @When("I filter the category list to get the todo with id {string}")
    public void i_filter_the_category_list_to_get_the_todo_with_id(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/categories?id="+string);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int temp = connection_url.getResponseCode();
        status_code = Integer.toString(temp);
    }
    @Then("a output code {string} is returned")
    public void a_output_code_is_returned(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(string, status_code);
    }

    @Given("the category list does not contain id {string}")
    public void the_category_list_does_not_contain_id(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/categories/"+string);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();

    }
    @When("I request the category item with id {string}")
    public void i_request_the_category_item_with_id(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/categories/"+string);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int temp = connection_url.getResponseCode();
        last_status = Integer.toString(temp);
    }
    @Then("a output code of {string} is returned")
    public void a_output_code_of_is_returned(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(last_status,string);
    }


    @Given("the service is running")
    public void the_service_is_running() {
        // Write code here that turns the phrase above into concrete actions
        Api call = new Api();
        // Verify that the server is running
        response = call.checkService();
        assertEquals(200, response.code());
    }
    @Given("a todo item exists with identifier {string}")
    public void a_todo_item_exists_with_identifier(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/todos/"+string);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int view_todos_status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, view_todos_status_code);
    }
    @When("I request the todo item with identifier {string}")
    public void i_request_the_todo_item_with_identifier(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions

        try {
            String id = "123"; // Replace with your actual ID
            URL url = new URL("http://localhost:4567/todos/" + string);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Convert response to JSON object
                JSONObject jsonResponse = new JSONObject(response.toString());

                JSONArray todosArray = jsonResponse.getJSONArray("todos");
                String[] titleJson = new String[2];
// Assuming you want the first item in the array
                if (todosArray.length() > 0) {
                    JSONObject firstTodo = todosArray.getJSONObject(0);

                    // Check if the firstTodo has a "title" key
                    if (firstTodo.has("title")) {
                        // Extract the title
                        String title = firstTodo.getString("title");
                        titleJson[0] = title;
                        // Output the title
                        //System.out.println("Title: " + title);
                    } else {
                        // Handle case where the "title" key doesn't exist
                        //System.out.println("Key 'title' does not exist in the JSON response.");
                    }
                } else {
                    // Handle case where the 'todos' array is empty
                    //System.out.println("'todos' array is empty.");
                }


                view_todos_string = titleJson[0];
                // Output the title in JSON format
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }





        URL url = new URL("http://localhost:4567/todos/"+string);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int temp = connection_url.getResponseCode();
        view_todos_status_code = Integer.toString(temp);
    }

    @Then("the response should include a status code of {string} and title {string}")
    public void the_response_should_include_a_view_todos_status_code_of(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(string, view_todos_status_code);
        assertEquals(view_todos_string, string2);
    }



//    @Given("the todo with id {string} exists")
//    public void the_todo_with_id_exists(String string) throws IOException {
//        // Write code here that turns the phrase above into concrete actions
//        URL url = new URL("http://localhost:4567/todos/"+string);
//        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
//        int view_todos_status_code = connection_url.getResponseCode();
//        assertEquals(HttpURLConnection.HTTP_OK, view_todos_status_code);
//    }
    @When("I filter the todos list to get the todo with id {string}")
    public void i_filter_the_todos_list_to_get_the_todo_with_id(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        try {
            String id = "123"; // Replace with your actual ID
            URL url = new URL("http://localhost:4567/todos?id=" + string);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");


            int responseCode = connection.getResponseCode();
            view_todos_status_code = Integer.toString(responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Convert response to JSON object
                JSONObject jsonResponse = new JSONObject(response.toString());

                JSONArray todosArray = jsonResponse.getJSONArray("todos");
                String[] titleJson = new String[2];
// Assuming you want the first item in the array
                if (todosArray.length() > 0) {
                    JSONObject firstTodo = todosArray.getJSONObject(0);

                    // Check if the firstTodo has a "title" key
                    if (firstTodo.has("title")) {
                        // Extract the title
                        String title = firstTodo.getString("title");
                        titleJson[0] = title;
                        // Output the title
                        //System.out.println("Title: " + title);
                    } else {
                        // Handle case where the "title" key doesn't exist
                        //System.out.println("Key 'title' does not exist in the JSON response.");
                    }
                } else {
                    // Handle case where the 'todos' array is empty
                    //System.out.println("'todos' array is empty.");
                }


                view_todos_string = titleJson[0];
                // Output the title in JSON format
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    @Then("a status code {string} and title {string} is returned")
    public void a_view_todos_status_code_is_returned(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(string, view_todos_status_code);
        assertEquals(string2, view_todos_string);
    }

    @Given("the todos list does not contain id {string}")
    public void the_todos_list_does_not_contain_id(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/todos/"+string);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();

    }
    @When("I request the todo item with id {string}")
    public void i_request_the_todo_item_with_id(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/todos/"+string);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int temp = connection_url.getResponseCode();
        view_todos_last_status = Integer.toString(temp);
    }

    @Then("a status code of {string} is returned")
    public void a_view_todos_status_code_of_is_returned(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(view_todos_last_status,string);
    }


    @Given("I have a todo with ID {string}")
    public void i_have_a_todo_with_id(String todoId) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/todos/"+todoId);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Given("I have a project with ID {string}")
    public void i_have_a_project_with_id(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/projects/"+string);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }
    @Given("I create a project with title {string}, completed {string}, description {string}, active {string}")
    public void i_create_a_project_with_title_completed_description_active(String string, String string2, String string3, String string4) {
        // Write code here that turns the phrase above into concrete actions

        HttpClient posttodo = HttpClient.newHttpClient();

        // XML data that we want to send
        String xmlData = """
            <project>
                <active>""" + string4 + """
                </active>
                <description> """ + string3 + """
                </description>
                <completed>""" + string2 + """
                </completed>
                <title>""" + string + """
            </title>
            </project>""";

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/projects")) // Use the correct URL here
                .header("Content-Type", "application/xml") // Set the header to accept XML
                .POST(HttpRequest.BodyPublishers.ofString(xmlData)) // Use the XML data as the body of the POST request
                .build();

        try {
            // Send the request and receive the response
            HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());

            // Get the status code from the response
            int temp = response.statusCode();

            // Convert the status code to String if needed
            String add_status_code = Integer.toString(temp);

            // Print out the status code
            //System.out.println("Status code: " + add_status_code);

            // Optionally print the response body
            //System.out.println("Response body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @When("I request to add a relationship tasksof between todo {string} and projects {string}")
    public void i_request_to_add_a_relationship_between_todo_and_tasksof(String string, String string2) throws IOException, InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        HttpClient posttodo = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+string+"/tasksof")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\""+string2+"\"}")).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
    }
    @Then("the relationship between todo {string} and project should be created")
    public void the_relationship_between_todo_and_tasksof_should_be_created(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/todos/"+string+"/tasksof");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);



    }

    @Then("the relationship between todo {string} and project {string} should be created")
    public void the_relationship_between_todo_and_tasksof_should_be_created1(String string, String string2) throws IOException {
        // Write code here that turns the phrase above into concrete actions

        try {
            URL url = new URL("http://localhost:4567/todos/" +string + "/tasksof");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int status_code = connection.getResponseCode();
            if (status_code == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(content.toString());
                // Assuming the JSON object has a key called "id" for the ID

// Get the JSON array associated with the key "projects"
                JSONArray projects = jsonResponse.getJSONArray("projects");
                String[] id = new String[1];
// Loop through each object in the JSON array
                for (int i = 0; i < projects.length(); i++) {
                    // Get the individual project as a JSONObject
                    JSONObject project = projects.getJSONObject(i);

                    // Check if the project has an "id" key
                    if (project.has("id")) {
                        // Get the value of the "id" field
                        id[0] = project.getString("id");

                        // Now you have the ID, you can do something with it
//                        System.out.println("Project ID: " + id);
                    }
                }

                // Now you have the ID as a string. To convert it into a JSON object:
                temp= id[0];

            } else {
                // Handle error...
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(temp==string2){
            assertEquals(200, HttpURLConnection.HTTP_OK);
        }

    }


    @When("I request to add a relationship tasksof between todo {string} and a non existent project with id {string}")
    public void i_request_to_add_a_relationship_tasksof_between_todo_and_a_non_existent_project_with_id(String string, String string2) throws IOException, InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        HttpClient posttodo = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+string+"/tasksof")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\""+string2+"\"}")).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        int temp = response.statusCode();
        add_status_code = Integer.toString(temp);
    }

    @Given("I have a category with ID {string}")
    public void i_have_a_category_with_id(String string) {
        try {
            URL url = new URL("http://localhost:4567/categories/" + string);
            HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
            int status_code = connection_url.getResponseCode();
            assertEquals(HttpURLConnection.HTTP_OK, status_code);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("I get an error code {string}")
    public void i_get_an_error_code(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(add_status_code,string);
    }

    @When("I request to add a relationship categories between project {string} and categories {string}")
    public void i_request_to_add_a_relationship_categories_between_project_and_categories(String string, String string2) {
        HttpClient poster = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/"+string+"/categories")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\""+string2+"\"}")).build();
        try{
            HttpResponse<String> response = poster.send(req, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("the relationship between project {string} category {string} should be created")
    public void the_relationship_between_project_category_should_be_created1(String string, String string2) throws IOException {
        // Write code here that turns the phrase above into concrete actions

        try {
            URL url = new URL("http://localhost:4567/projects/" +string + "/categories");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int status_code = connection.getResponseCode();
            if (status_code == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(content.toString());
                // Assuming the JSON object has a key called "id" for the ID

// Get the JSON array associated with the key "categories"
                JSONArray projects = jsonResponse.getJSONArray("categories");
                String[] id = new String[1];
// Loop through each object in the JSON array
                for (int i = 0; i < projects.length(); i++) {
                    // Get the individual project as a JSONObject
                    JSONObject project = projects.getJSONObject(i);

                    // Check if the project has an "id" key
                    if (project.has("id")) {
                        // Get the value of the "id" field
                        id[0] = project.getString("id");

                    }
                }

                // Now you have the ID as a string. To convert it into a JSON object:
                temp= id[0];

            } else {
                // Handle error...
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(temp==string2){
            assertEquals(200, HttpURLConnection.HTTP_OK);
        }

    }

    @Given("I create a category with title {string}, description {string}")
    public void i_create_a_category_with_title_description(String string, String string2) {
        HttpClient poster = HttpClient.newHttpClient();

        // XML data that we want to send
        String xmlData = """
            <category>
                <description> """ + string2 + """
                </description>
                <title>""" + string + """
            </title>
            </category>""";

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/categories")) // Use the correct URL here
                .header("Content-Type", "application/xml") // Set the header to accept XML
                .POST(HttpRequest.BodyPublishers.ofString(xmlData)) // Use the XML data as the body of the POST request
                .build();

        try {
            // Send the request and receive the response
            HttpResponse<String> response = poster.send(req, HttpResponse.BodyHandlers.ofString());

            // Get the status code from the response
            int temp = response.statusCode();

            // Convert the status code to String if needed
            String add_status_code = Integer.toString(temp);

            // Print out the status code
            //System.out.println("Status code: " + add_status_code);

            // Optionally print the response body
            //System.out.println("Response body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String temp1;

    @When("I request to add a relationship categories between project {string} and a non existent category with id {string}")
    public void i_request_to_add_a_relationship_categories_between_project_and_a_non_existent_category_with_id(String string, String string2) {
        //        // Write code here that turns the phrase above into concrete actions
        try{
            HttpClient poster = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/"+string+"/categories")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\""+string2+"\"}")).build();
            HttpResponse<String> response = poster.send(req, HttpResponse.BodyHandlers.ofString());
            int temp1 = response.statusCode();
            add_status_code = Integer.toString(temp1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @When("I request to add a relationship categories between todos {string} and categories {string}")
    public void i_request_to_add_a_relationship_categories_between_todos_and_categories(String string, String string2) {
        HttpClient poster = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+string+"/categories")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\""+string2+"\"}")).build();
        try{
            HttpResponse<String> response = poster.send(req, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("the relationship between todo {string} category {string} should be created")
    public void the_relationship_between_todo_category_should_be_created(String string, String string2) {

        try {
            URL url = new URL("http://localhost:4567/todos/" +string + "/categories");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int status_code = connection.getResponseCode();
            if (status_code == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(content.toString());
                // Assuming the JSON object has a key called "id" for the ID

// Get the JSON array associated with the key "todos"
                JSONArray projects = jsonResponse.getJSONArray("categories");
                String[] id = new String[1];
// Loop through each object in the JSON array
                for (int i = 0; i < projects.length(); i++) {
                    // Get the individual project as a JSONObject
                    JSONObject project = projects.getJSONObject(i);

                    // Check if the project has an "id" key
                    if (project.has("id")) {
                        // Get the value of the "id" field
                        id[0] = project.getString("id");

                    }
                }

                // Now you have the ID as a string. To convert it into a JSON object:
                temp= id[0];

            } else {
                //System.out.print("failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(temp==string2){
            assertEquals(200, HttpURLConnection.HTTP_OK);
        }
    }



    @When("I request to add a relationship categories between todos {string} and a non existent category with id {string}")
    public void i_request_to_add_a_relationship_categories_between_todos_and_a_non_existent_category_with_id(String string, String string2) {

        try{
            HttpClient poster = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+string+"/categories")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\""+string2+"\"}")).build();
            HttpResponse<String> response = poster.send(req, HttpResponse.BodyHandlers.ofString());
            int temp = response.statusCode();
            add_status_code = Integer.toString(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @When("I request to add a relationship projects between categories {string} and projects {string}")
    public void i_request_to_add_a_relationship_projects_between_categories_and_projects(String string, String string2) {
        HttpClient poster = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/"+string+"/projects")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\""+string2+"\"}")).build();
        try{
            HttpResponse<String> response = poster.send(req, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Then("the relationship between category {string} project {string} should be created")
    public void the_relationship_between_category_project_should_be_created(String string, String string2) {
        try {
            URL url = new URL("http://localhost:4567/categories/" +string + "/projects");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int status_code = connection.getResponseCode();
            if (status_code == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(content.toString());
                // Assuming the JSON object has a key called "id" for the ID

// Get the JSON array associated with the key "projects"
                JSONArray projects = jsonResponse.getJSONArray("projects");
                String[] id = new String[1];
// Loop through each object in the JSON array
                for (int i = 0; i < projects.length(); i++) {
                    // Get the individual project as a JSONObject
                    JSONObject project = projects.getJSONObject(i);

                    // Check if the project has an "id" key
                    if (project.has("id")) {
                        // Get the value of the "id" field
                        id[0] = project.getString("id");

                    }
                }

                // Now you have the ID as a string. To convert it into a JSON object:
                temp= id[0];

            } else {
                //System.out.print("failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(temp==string2){
            assertEquals(200, HttpURLConnection.HTTP_OK);
        }

    }

    @When("I request to add a relationship projects between category {string} and a non existent project with id {string}")
    public void i_request_to_add_a_relationship_projects_between_category_and_a_non_existent_project_with_id(String string, String string2) {
        //        // Write code here that turns the phrase above into concrete actions
        try{
            HttpClient poster = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/"+string+"/projects")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\""+string2+"\"}")).build();
            HttpResponse<String> response = poster.send(req, HttpResponse.BodyHandlers.ofString());
            int temp = response.statusCode();
            add_status_code = Integer.toString(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("I request to add a relationship todos between categories {string} and todos {string}")
    public void i_request_to_add_a_relationship_todos_between_categories_and_todos(String string, String string2) {
        HttpClient poster = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/"+string+"/todos")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\""+string2+"\"}")).build();
        try{
            HttpResponse<String> response = poster.send(req, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("the relationship between category {string} todo {string} should be created")
    public void the_relationship_between_category_todo_should_be_created(String string, String string2) {

        try {
            URL url = new URL("http://localhost:4567/categories/" +string + "/todos");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int status_code = connection.getResponseCode();
            if (status_code == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(content.toString());
                // Assuming the JSON object has a key called "id" for the ID

// Get the JSON array associated with the key "todos"
                JSONArray projects = jsonResponse.getJSONArray("todos");
                String[] id = new String[1];
// Loop through each object in the JSON array
                for (int i = 0; i < projects.length(); i++) {
                    // Get the individual project as a JSONObject
                    JSONObject project = projects.getJSONObject(i);

                    // Check if the project has an "id" key
                    if (project.has("id")) {
                        // Get the value of the "id" field
                        id[0] = project.getString("id");

                    }
                }

                // Now you have the ID as a string. To convert it into a JSON object:
                temp= id[0];

            } else {
                //System.out.print("failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(temp==string2){
            assertEquals(200, HttpURLConnection.HTTP_OK);
        }
    }


    @Given("I create a todo with title {string}, description {string}")
    public void i_create_a_todo_with_title_description(String string, String string2) {
        HttpClient poster = HttpClient.newHttpClient();

        // XML data that we want to send
        String xmlData = """
            <todo>
                <description> """ + string2 + """
                </description>
                <title>""" + string + """
            </title>
            </todo>""";

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/todos")) // Use the correct URL here
                .header("Content-Type", "application/xml") // Set the header to accept XML
                .POST(HttpRequest.BodyPublishers.ofString(xmlData)) // Use the XML data as the body of the POST request
                .build();

        try {
            // Send the request and receive the response
            HttpResponse<String> response = poster.send(req, HttpResponse.BodyHandlers.ofString());

            // Get the status code from the response
            int temp = response.statusCode();

            // Convert the status code to String if needed
            String add_status_code = Integer.toString(temp);

            // Print out the status code
            //System.out.println("Status code: " + add_status_code);

            // Optionally print the response body
            //System.out.println("Response body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @When("I request to add a relationship todos between category {string} and a non existent todo with id {string}")
    public void i_request_to_add_a_relationship_todos_between_category_and_a_non_existent_todo_with_id(String string, String string2) {
        //        // Write code here that turns the phrase above into concrete actions
        try{
            HttpClient poster = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/"+string+"/todos")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\""+string2+"\"}")).build();
            HttpResponse<String> response = poster.send(req, HttpResponse.BodyHandlers.ofString());
            int temp = response.statusCode();
            add_status_code = Integer.toString(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // DELETE TASKSOF TODOS

    @Given("there is a tasksof relationship between todo with id {string} and project with id {string}")
    public void there_is_a_tasksof_relationship_between_todo_with_id_and_project_with_id(String todoId, String projectId) {
        Api call = new Api();

        // Send a GET request to retrieve todos with todoId
        Response getTodo = call.getRequest("todos/" + todoId, "json");
        boolean tasksofExists = false;
        try {
            // Get the response body as a JSON object
            JSONObject getTodoResponseBody = new JSONObject(getTodo.body().string());
            // Get the "todos" array from the response body
            JSONArray todosArray = getTodoResponseBody.getJSONArray("todos");
            // Get the first element of the "todos" array
            JSONObject todoObject = todosArray.getJSONObject(0);
            // Get the "tasksof" array from the todos object
            JSONArray tasksofArray = todoObject.getJSONArray("tasksof");

            // Loop through the tasksof in the array and check if the specified project exists
            for (Object obj : tasksofArray) {
                JSONObject tasksof = (JSONObject) obj;
                String tasksofIdStr = tasksof.getString("id");
                if (tasksofIdStr.equals(projectId)) {
                    // If the todos with the specified id is found, set the flag to true and exit the loop
                    tasksofExists = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Assert that the specified tasksof exists for given todoId
        assertTrue( tasksofExists, "ERROR: There are no tasksof relationship for the todo with id " + todoId
                + " and the project with id " + projectId);
    }


    @When("I request to delete the tasksof relationship between todo with id {string} and project with id {string}")
    public void i_delete_the_tasksof_relationship_between_todo_with_id_and_project_with_id_using_id_in_endpoint(String todoId, String projectId) {
        Api call = new Api();
        response = call.deleteRequest("todos/" + todoId + "/tasksof/" + projectId);
    }

    @When("I delete the tasksof relationship between todo with id {string} and project with id {string} using put method")
    public void i_delete_the_tasksof_relationship_between_todo_with_id_and_project_with_id_using_put_method(String todoId, String projectId) {
        // Create a Api object to handle the API request
        Api call = new Api();

        // Create a JSON object with an "id" field containing the category id
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", "updated todo");

        // Create relationship categories between todos and catagories using a PUT request
        response = call.putRequest("todos/" + todoId, "json", requestBody);
    }

    @When("I delete the nonexistent tasksof relationship between todo with id {string} and project with id {string} using id in endpoint")
    public void i_delete_the_nonexistent_tasksof_relationship_between_todo_with_id_and_project_with_id_using_id_in_endpoint(String todoId, String projectId) {
        Api call = new Api();
        response = call.deleteRequest("todos/" + todoId + "/tasksof/" + projectId);
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Then("the tasksof relationship between todo with id {string} and project with id {string} should be deleted")
    public void the_tasksof_relationship_between_todo_with_id_and_project_with_id_is_deleted(String todoId, String projectId) {
        Api call = new Api();
        // Send a GET request to retrieve todos with todoId
        Response getTodo = call.getRequest("todos/" + todoId, "json");
        boolean tasksofExists = false;
        try {
            // Get the response body as a JSON object
            JSONObject getTodoResponseBody = new JSONObject(getTodo.body().string());
            if(!getTodoResponseBody.isNull("tasksof")) {
                // Get the "tasksof" array from the response body
                JSONArray tasksofArray = getTodoResponseBody.getJSONArray("tasksof");
                // Loop through the projects in the array and check if the specified project exists
                for (Object obj : tasksofArray) {
                    JSONObject tasksof = (JSONObject) obj;
                    String tasksofIdStr = tasksof.getString("id");
                    if (tasksofIdStr.equals(projectId)) {
                        // If the todos with the specified id is found, set the flag to true and exit the loop
                        tasksofExists = true;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Assert that the specified tasksof does not exist
        assertFalse( tasksofExists, "ERROR: The tasksof relationship exists between todo with id " + todoId + " and project with id" + projectId);
    }

    @Then("the response body has the error message {string}")
    public void the_response_body_has_the_error_message(String errorMessage) {
        //Check that the first key of the response body is "errorMessages"
        assertEquals("errorMessages", responseBody.keySet().iterator().next(), "\n ERROR: The response body does not have a key \"errorMessage\". \n The response body is : " + responseBody + "\n And should return error message: \n" + errorMessage);

        JSONArray errorMessages = responseBody.getJSONArray("errorMessages");
        String responseErrorMessage = errorMessages.getString(0);
        assertEquals(errorMessage, responseErrorMessage, "ERROR: The actual error message does not match the expected error message.");

    }

    @Then("a status code {string} is returned")
    public void a_status_code_with_response_phrase_is_returned1(String statusCode) {
        assertEquals(Integer.parseInt(statusCode), response.code());
    }

    @Given("a project item exists with identifier {string}")
    public void a_project_item_exists_with_identifier(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/projects/"+string);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int view_todos_status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, view_todos_status_code);
    }
    @When("I request tasks assigned to project with identifier {string}")
    public void i_request_tasks_assigned_to_project_with_identifier(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/projects/"+string+"/tasks");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int temp = connection_url.getResponseCode();
        view_todos_last_status = Integer.toString(temp);
    }

    @Given("the project list does not contain id needed to check for tasks {string}")
    public void the_project_list_does_not_contain_id_needed_to_check_for_tasks(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/todos/"+string);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
    }
    @When("I request the project item with id {string}")
    public void i_request_the_project_item_with_id(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/todos/"+string);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int temp = connection_url.getResponseCode();
        view_todos_last_status = Integer.toString(temp);
    }

    @When("I request a specific task assigned to project by filtering with the requested task id {string}")
    public void i_request_tasks_assigned_to_project_by_filtering_with_the_requested_id(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/projects/"+string+"/tasks?id=1");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int temp = connection_url.getResponseCode();
        view_todos_last_status = Integer.toString(temp);
    }

    @Then("the response should include a status code of {string}")
    public void the_response_should_include_a_view_todos_status_code_of1(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(string, view_todos_last_status);

    }

    private String getPluralElement(Element element){
        String pluralElement = "";
        switch (element) {
            case CATEGORY:
                pluralElement = "categories";
                break;
            case TODO:
                pluralElement = "todos";
                break;
            case PROJECT:
                pluralElement = "projects";
                break;
        }
        return pluralElement;
    }
    private JSONArray getJSONArray(Response r, String group){
        JSONObject obj = null;
        try {
            obj = new JSONObject(r.body().string());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return obj.getJSONArray(group);
    }

    private boolean elementExistsByTitle(Element element, String targetTitle){
        Api call = new Api();
        String group = getPluralElement(element);
        Response r = call.getRequest(group, "json");
        JSONArray allElementsJSONArray = getJSONArray(r, group);
        for (Object obj : allElementsJSONArray) {
            String title = ((JSONObject) obj).getString("title");
            if(title.equals(targetTitle)){
                return true;
            }
        }
        return false;
    }
    private boolean isMatch(Element element, String targetTitle, String targetField, String targetContent ){
        Api call = new Api();
        String group = getPluralElement(element);
        Response r = call.getRequest(group, "json");
        JSONArray allElementsJSONArray = getJSONArray(r, group);
        for (Object obj : allElementsJSONArray) {
            String title = ((JSONObject) obj).getString("title");
            if(title.equals(targetTitle)){
                switch (element) {
                    case CATEGORY:
                        String field = ((JSONObject) obj).getString(targetField);
                        if(field.toString().equals(targetContent)) {
                            return true;
                        }
                        break;
                    case PROJECT:
                        boolean field2 = ((JSONObject) obj).getBoolean(targetField);
                        if(field2) {
                            return true;
                        }
                        break;
                    case TODO:
                        boolean field3 = ((JSONObject) obj).getBoolean(targetField);
                        if(field3) {
                            return true;
                        }
                        break;

                }

            }
        }
        return false;
    }
    private String getElementIdByTitle(Element element, String targetTitle){
        Api call = new Api();
        String group = getPluralElement(element);
        Response r = call.getRequest(group, "json");
        JSONArray allElementsJSONArray = getJSONArray(r, group);
        for (Object obj : allElementsJSONArray) {
            String title = ((JSONObject) obj).getString("title");
            if(title.equals(targetTitle)){
                return ((JSONObject) obj).getString("id");
            }
        }
        return "";
    }

    // Amend Category

    @Given("a category with title {string}, and description {string}")
    public void aCategoryWithTitleAndDescription(String title, String description) {
        // TODO: Create a category with title, description
        // TODO: Assert that the category is added correctly
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("description", description);
        Response r = call.postRequest("categories", "json", requestBody);
        assertTrue(elementExistsByTitle(Element.CATEGORY, title));
    }
    @When("a user changes the description of category with title {string} to {string} by using the PUT API call")
    public void aUserChangesTheDescriptionOfCategoryWithTitleToByUsingThePUTAPICall(String title, String description) {
        // TODO: Get the id of the category with title
        // TODO: Change description by doing a PUT for that ID
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("description", description);
        String id = getElementIdByTitle(Element.CATEGORY, title);
        Response r = call.putRequest("categories/" + id, "json", requestBody);
    }
    @Then("the category with title {string} should have a description of {string}")
    public void theCategoryWithTitleShouldHaveADescriptionOf(String title, String description) {
        assertTrue(isMatch(Element.CATEGORY, title, "description", description));
    }
    @When("a user changes the description of category with title {string} to {string} by using the POST API call")
    public void aUserChangesTheDescriptionOfCategoryWithTitleToByUsingThePOSTAPICall(String title, String description) {
        // TODO: Get the id of the category with title
        // TODO: Change description by doing a POST for that ID
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("description", description);
        String id = getElementIdByTitle(Element.CATEGORY, title);
        Response r = call.postRequest("categories/" + id, "json", requestBody);
    }
    @When("a user changes the description of category {string}")
    public void aUserChangesTheDescriptionOfCategory(String id) {
        // TODO attempt to change the doneStatus of the todo with id
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("description", "new description");
        Response r = call.postRequest("categories/" + id, "json", requestBody);
        response = r;
    }

    @Then("the status code {string} is returned")
    public void theStatusCodeIsReturned(String code) {
        assertEquals(Integer.parseInt(code), response.code());
    }


    // Amend Project

    @Given("a project with title {string}, description {string}, completed status {string}, and active_status {string} exists")
    public void aProjectWithTitleDescriptionCompletedStatusAndActive_statusExists(String title, String description, String completed, String active) {
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("description", description);
        requestBody.put("completed", Boolean.valueOf(completed));
        requestBody.put("active", Boolean.valueOf(active));
        Response r = call.postRequest("projects", "json", requestBody);
        assertTrue(elementExistsByTitle(Element.PROJECT, title));
    }

    @When("a user marks the project {string} as complete by using the PUT API call")
    public void aUserMarksTheProjectAsCompleteByUsingThePUTAPICall(String title) {
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("completed", true);
        String id = getElementIdByTitle(Element.PROJECT, title);
        Response r = call.putRequest("projects/" + id, "json", requestBody);
    }

    @Then("the project with title {string} should have a completed status of {string}")
    public void theProjectWithTitleShouldHaveACompletedStatusOf(String title, String completed) {
        assertTrue(isMatch(Element.PROJECT, title, "completed", completed));
    }

    @When("a user marks the project {string} as complete by using the POST API call")
    public void aUserMarksTheProjectAsCompleteByUsingThePOSTAPICall(String title) {
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("completed", true);
        String id = getElementIdByTitle(Element.PROJECT, title);
        Response r = call.postRequest("projects/" + id, "json", requestBody);
    }

    @When("a user marks the project {string} as complete")
    public void aUserMarksTheProjectAsComplete(String id) {
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("completed", true);
        Response r = call.postRequest("projects/" + id, "json", requestBody);
        response = r;
    }

    // Amend Todo

    @Given("a todo with title {string}, description {string} and doneStatus {string} exists")
    public void aTodoWithTitleDescriptionAndDoneStatusExists(String title, String description, String doneStatus) {
        // TODO: Create a todo with title, description and doneStatus
        // TODO: Assert that the todo is added correctly
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("description", description);
        requestBody.put("doneStatus", Boolean.valueOf(doneStatus));
        Response r = call.postRequest("todos", "json", requestBody);
        assertTrue(elementExistsByTitle(Element.TODO, title));
    }

    @When("a user marks the todo with title {string} as done by using the PUT API call")
    public void aUserMarksTheTodoWithTitleAsDoneByUsingThePUTAPICall(String title) {
        // TODO: Get the id of the todo with title
        // TODO: Change doneStatus by doing a put for that ID
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("doneStatus", true);
        String id = getElementIdByTitle(Element.TODO, title);
        Response r = call.putRequest("todos/" + id, "json", requestBody);
    }

    @Then("the todo with title {string} should have a doneStatus of {string}")
    public void theTodoWithTitleShouldHaveADoneStatusOf(String title, String doneStatus) {
        // TODO: Get the id of the todo with title
        // TODO: Get the todo by id
        // TODO: Check that the todo has the right doneStatus
        assertTrue(isMatch(Element.TODO, title, "doneStatus", doneStatus));
    }

    @When("a user marks the todo with title {string} as done by using the POST API call")
    public void aUserMarksTheTodoWithTitleAsDoneByUsingThePOSTAPICall(String title) {
        // Get the id of the todo with title
        // Change doneStatus by doing a put for that ID
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("doneStatus", true);
        String id = getElementIdByTitle(Element.TODO, title);
        Response r = call.postRequest("todos/" + id, "json", requestBody);
    }

    @When("a user marks the todo {string} as done")
    public void aUserMarksTheTodoAsDone(String id) {
        // attempt to change the doneStatus of the todo with id
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("doneStatus", true);
        Response r = call.postRequest("todos/" + id, "json", requestBody);
        response = r;
    }

    // Get Project

    String currentID;
    @When("a user gets the project with title {string} by id")
    public void aUserGetsTheProjectWithTitleById(String title) {
        currentID = getElementIdByTitle(Element.PROJECT, title);
        Api call = new Api();
        response = call.getRequest("projects/" + currentID, "json");
    }

    @Then("the project with title {string} is returned")
    public void theProjectWithTitleIsReturned(String title) {
        JSONArray array = getJSONArray(response, "projects");
        array.getJSONObject(0).getString("title");
        assertTrue(title.equals(array.getJSONObject(0).getString("title")));
    }

    @Given("there are a number of projects {string} in the system")
    public void thereAreANumberOfProjectsInTheSystem(String x) {
        int numOfProjects = Integer.parseInt(x);
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        Response r;
        r = call.getRequest("projects", "json");
        JSONArray array = getJSONArray(r, "projects");
        for(int i = 0; i < numOfProjects- array.length(); i++){
            requestBody.put("title", "test title");
            requestBody.put("description", "test description");
            requestBody.put("completed", Boolean.valueOf("true"));
            requestBody.put("active", Boolean.valueOf("false"));
            r = call.postRequest("projects", "json", requestBody);
        }
    }

    @When("a user gets all of the the projects in the system")
    public void aUserGetsAllOfTheTheProjectsInTheSystem() {
        Api call = new Api();
        response = call.getRequest("projects", "json");
    }

    @Then("{string} projects are returned")
    public void projectsAreReturned(String x) {
        JSONArray array = getJSONArray(response, "projects");
        assertEquals(Integer.parseInt(x), array.length());
    }

    @When("a user gets the project with id {string}")
    public void aUserGetsTheProjectWithId(String id) {
        Api call = new Api();
        response = call.getRequest("projects/" + id, "json");
    }

    // Delete Project TODO Relationship

    @And("there is a tasks relationship between the todo with title {string} and the project with title {string}")
    public void thereIsATasksRelationshipBetweenTheTodoWithTitleAndTheProjectWithTitle(String todoTitle, String projectTitle) {
        Api call = new Api();
        String todoId = getElementIdByTitle(Element.TODO, todoTitle);
        String projectId = getElementIdByTitle(Element.PROJECT, projectTitle);

        JSONObject requestBody = new JSONObject();
        requestBody.put("id", todoId);
        Response r = call.postRequest("projects/" + projectId + "/tasks", "json", requestBody);
    }

    @When("a user deletes the tasks relationship between the todo with title {string} and the project with title {string} with the todo API")
    public void aUserDeletesTheTasksRelationshipBetweenTheTodoWithTitleAndTheProjectWithTitleWithTheTodoAPI(String todoTitle, String projectTitle) {
        Api call = new Api();
        String todoId = getElementIdByTitle(Element.TODO, todoTitle);
        String projectId = getElementIdByTitle(Element.PROJECT, projectTitle);

        Response r = call.deleteRequest("todos/" + todoId + "/tasksof/" + projectId);
    }

    @Then("the todo with title {string} and the project with title {string} do not have a relationship")
    public void theTodoWithTitleAndTheProjectWithTitleDoNotHaveARelationship(String todoTitle, String projectTitle) {
        Api call = new Api();
        String todoId = getElementIdByTitle(Element.TODO, todoTitle);
        Response r = call.getRequest("todos/" + todoId + "/tasksof", "json");
        JSONArray array = getJSONArray(r, "projects");
        assertEquals(0, array.length());
    }

    @When("a user deletes the tasks relationship between the todo with title {string} and the project with title {string} with the project API")
    public void aUserDeletesTheTasksRelationshipBetweenTheTodoWithTitleAndTheProjectWithTitleWithTheProjectAPI(String todoTitle, String projectTitle) {
        Api call = new Api();
        String todoId = getElementIdByTitle(Element.TODO, todoTitle);
        String projectId = getElementIdByTitle(Element.PROJECT, projectTitle);

        response = call.deleteRequest("projects/" + projectId + "/tasks/" + todoId);
    }
}