import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.Response;
import org.example.Api;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StepDefinitions {
    Response response;
    JSONObject responseBody;
    int previousCategoriesCount;
    int previousProjectCount;
    int currentCategoriesCount;
    int currentProjectCount;
    int currentTodosCount;
    int previousTodosCount;

    List<String> todoCategoriesIds;

    @Given("the app is running")
    public void the_app_is_running() {
        Api call = new Api();
        // Verify that the server is running
        response = call.checkService();
        assertEquals(200, response.code());

    }

    // Given

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

    // When

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

}