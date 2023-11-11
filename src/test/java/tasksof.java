//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import okhttp3.Response;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import okhttp3.Response;
//import org.example.Api;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//public class tasksof {
//
//    Response response;
//    JSONObject responseBody;
//    int previousTodosCount;
//    int currentTodosCount;
//
//    List<String> todoCategoriesIds;
//
//    // DELETE TASKSOF TODOS
//    @Given("the project with id {string} exists")
//    public void the_project_with_id_exists(String projectId) {
//        // Create a Api object to make API requests
//        Api call = new Api();
//
//        // Set a flag to keep track of whether the specified todos exists
//        boolean projectExists = false;
//
//        // Send a GET request to retrieve all todos
//        Response getProjects = call.getRequest("projects", "json");
//
//        try {
//            // Get the response body as a JSON object
//            JSONObject getProjectsResponseBody = new JSONObject(getProjects.body().string());
//            // Get the "projects" array from the response body
//            JSONArray projectsArray = getProjectsResponseBody.getJSONArray("projects");
//            // Loop through the projects in the array and check if the specified project exists
//            for (Object obj : projectsArray) {
//                JSONObject project = (JSONObject) obj;
//                String projectIdStr = project.getString("id");
//                if (projectIdStr.equals(projectId)) {
//                    // If the project with the specified id is found, set the flag to true and exit the loop
//                    projectExists = true;
//                    break;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // Assert that the specified project exists
//        assertTrue( projectExists, "ERROR: The project with id " + projectId + " does not exist in the system.");
//    }
//
//    @Given("there is a tasksof relationship between todo with id {string} and project with id {string}")
//    public void there_is_a_tasksof_relationship_between_todo_with_id_and_project_with_id(String todoId, String projectId) {
//        Api call = new Api();
//
//        // Send a GET request to retrieve todos with todoId
//        Response getTodo = call.getRequest("todos/" + todoId, "json");
//        boolean tasksofExists = false;
//        try {
//            // Get the response body as a JSON object
//            JSONObject getTodoResponseBody = new JSONObject(getTodo.body().string());
//            // Get the "todos" array from the response body
//            JSONArray todosArray = getTodoResponseBody.getJSONArray("todos");
//            // Get the first element of the "todos" array
//            JSONObject todoObject = todosArray.getJSONObject(0);
//            // Get the "tasksof" array from the todos object
//            JSONArray tasksofArray = todoObject.getJSONArray("tasksof");
//
//            // Loop through the tasksof in the array and check if the specified project exists
//            for (Object obj : tasksofArray) {
//                JSONObject tasksof = (JSONObject) obj;
//                String tasksofIdStr = tasksof.getString("id");
//                if (tasksofIdStr.equals(projectId)) {
//                    // If the todos with the specified id is found, set the flag to true and exit the loop
//                    tasksofExists = true;
//                    break;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // Assert that the specified tasksof exists for given todoId
//        assertTrue( tasksofExists, "ERROR: There are no tasksof relationship for the todo with id " + todoId
//                + " and the project with id " + projectId);
//    }
//
//    @Given("the project with id {string} does not exist")
//    public void the_project_with_id_does_not_exist(String projectId) {
//        // Create a Api object to make API requests
//        Api call = new Api();
//
//        // Set a flag to keep track of whether the specified todos exists
//        boolean projectExists = false;
//
//        // Send a GET request to retrieve all todos
//        Response getProjects = call.getRequest("projects", "json");
//
//        try {
//            // Get the response body as a JSON object
//            JSONObject getProjectsResponseBody = new JSONObject(getProjects.body().string());
//            // Get the "projects" array from the response body
//            JSONArray projectsArray = getProjectsResponseBody.getJSONArray("projects");
//            // Loop through the projects in the array and check if the specified project exists
//            for (Object obj : projectsArray) {
//                JSONObject project = (JSONObject) obj;
//                String projectIdStr = project.getString("id");
//                if (projectIdStr.equals(projectId)) {
//                    // If the project with the specified id is found, set the flag to true and exit the loop
//                    projectExists = true;
//                    break;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // Assert that the specified project does not exists
//        assertFalse( projectExists, "ERROR: The project with id "+ projectId + " exists in the system.");
//    }
//
//    @When("I delete the tasksof relationship between todo with id {string} and project with id {string} using id in endpoint")
//    public void i_delete_the_tasksof_relationship_between_todo_with_id_and_project_with_id_using_id_in_endpoint(String todoId, String projectId) {
//        Api call = new Api();
//        response = call.deleteRequest("todos/" + todoId + "/tasksof/" + projectId);
//    }
//
//    @When("I delete the tasksof relationship between todo with id {string} and project with id {string} using put method")
//    public void i_delete_the_tasksof_relationship_between_todo_with_id_and_project_with_id_using_put_method(String todoId, String projectId) {
//        // Create a Api object to handle the API request
//        Api call = new Api();
//
//        // Create a JSON object with an "id" field containing the category id
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("title", "updated todo");
//
//        // Create relationship categories between todos and catagories using a PUT request
//        response = call.putRequest("todos/" + todoId, "json", requestBody);
//    }
//
//    @When("I delete the nonexistent tasksof relationship between todo with id {string} and project with id {string} using id in endpoint")
//    public void i_delete_the_nonexistent_tasksof_relationship_between_todo_with_id_and_project_with_id_using_id_in_endpoint(String todoId, String projectId) {
//        Api call = new Api();
//        response = call.deleteRequest("todos/" + todoId + "/tasksof/" + projectId);
//        try {
//            responseBody = new JSONObject(response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    @Then("the tasksof relationship between todo with id {string} and project with id {string} is deleted")
//    public void the_tasksof_relationship_between_todo_with_id_and_project_with_id_is_deleted(String todoId, String projectId) {
//        Api call = new Api();
//        // Send a GET request to retrieve todos with todoId
//        Response getTodo = call.getRequest("todos/" + todoId, "json");
//        boolean tasksofExists = false;
//        try {
//            // Get the response body as a JSON object
//            JSONObject getTodoResponseBody = new JSONObject(getTodo.body().string());
//            if(!getTodoResponseBody.isNull("tasksof")) {
//                // Get the "tasksof" array from the response body
//                JSONArray tasksofArray = getTodoResponseBody.getJSONArray("tasksof");
//                // Loop through the projects in the array and check if the specified project exists
//                for (Object obj : tasksofArray) {
//                    JSONObject tasksof = (JSONObject) obj;
//                    String tasksofIdStr = tasksof.getString("id");
//                    if (tasksofIdStr.equals(projectId)) {
//                        // If the todos with the specified id is found, set the flag to true and exit the loop
//                        tasksofExists = true;
//                        break;
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // Assert that the specified tasksof does not exist
//        assertFalse( tasksofExists, "ERROR: The tasksof relationship exists between todo with id " + todoId + " and project with id" + projectId);
//    }
//
//}
