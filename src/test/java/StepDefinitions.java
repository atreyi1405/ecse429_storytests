import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.Response;
import org.example.Api;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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

    int previousCategoryTodoCount;

    List<String> todoCategoriesIds;

    @Given("the app is running")
    public void the_app_is_running(){
        Api call = new Api();
        // Verify that the server is running
        response = call.checkService();
        assertEquals(200, response.code());

    }


    @Given("the category with id {string}> exists")
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

    @Given("the todo with id {string}> exists")
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

    @Given("the project with id {string}> exists")
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
    public void a_user_adds_a_category_with_title_and_description(String categoryTitle, String categoryDescription){
        Api call= new Api();
        JSONObject getCategoryResponseBody= null;
        Response getPreviousCategories=call.getRequest("categories", "json");
        try{
            getCategoryResponseBody= new JSONObject(getPreviousCategories.body().string());
            previousCategoriesCount=getCategoryResponseBody.getJSONArray("categories").length();
        }catch (IOException e){
            e.printStackTrace();
        }
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", categoryTitle);
        requestBody.put("description",categoryDescription);

        response= call.postRequest("categories","json", requestBody);
        try{
            responseBody= new JSONObject(response.body().string());
        }catch (IOException e){
            e.printStackTrace();
        }

        Response getCurrentCategories= call.getRequest("categories", "json");
        try{
            getCategoryResponseBody= new JSONObject(getCurrentCategories.body().string());
            currentCategoriesCount= getCategoryResponseBody.getJSONArray("categories").length();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @When("a user adds a todo with title {string} and description {string}")
    public void a_user_adds_a_todo_with_title_and_description(String todoTitle, String todoDescription) {
        Api call= new Api();
        JSONObject getTodoResponseBody= null;
        Response getPreviousTodos=call.getRequest("todos", "json");
        try{
            getTodoResponseBody= new JSONObject(getPreviousTodos.body().string());
            previousTodosCount=getTodoResponseBody.getJSONArray("todos").length();
        }catch (IOException e){
            e.printStackTrace();
        }
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", todoTitle);
        requestBody.put("description",todoDescription);

        response= call.postRequest("todos","json", requestBody);
        try{
            responseBody= new JSONObject(response.body().string());
        }catch (IOException e){
            e.printStackTrace();
        }

        Response getCurrentCategories= call.getRequest("todos", "json");
        try{
            getTodoResponseBody= new JSONObject(getCurrentCategories.body().string());
            currentTodosCount= getTodoResponseBody.getJSONArray("todos").length();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @When("a user adds a todo with title {string}")
    public void a_user_adds_a_todo_with_title(String todoTitle) {
        Api call= new Api();
        JSONObject getTodoResponseBody= null;
        Response getPreviousTodos=call.getRequest("todos", "json");
        try{
            getTodoResponseBody= new JSONObject(getPreviousTodos.body().string());
            previousTodosCount=getTodoResponseBody.getJSONArray("todos").length();
            System.out.print(previousTodosCount);
        }catch (IOException e){
            e.printStackTrace();
        }
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", todoTitle);
        response= call.postRequest("todos","json", requestBody);
        try{
            responseBody= new JSONObject(response.body().string());
        }catch (IOException e){
            e.printStackTrace();
        }

        Response getCurrentTodos= call.getRequest("todos", "json");
        try{
            getTodoResponseBody= new JSONObject(getCurrentTodos.body().string());
            currentTodosCount= getTodoResponseBody.getJSONArray("todos").length();
            System.out.print(currentTodosCount);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @When("a user adds a category with title {string}")
    public void a_user_adds_a_category_with_title(String categoryTitle) {
        Api call= new Api();
        JSONObject getCategoryResponseBody= null;
        Response getPreviousCategories=call.getRequest("categories", "json");
        try{
            getCategoryResponseBody= new JSONObject(getPreviousCategories.body().string());
            previousCategoriesCount=getCategoryResponseBody.getJSONArray("categories").length();
        }catch (IOException e){
            e.printStackTrace();
        }
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", categoryTitle);
        response= call.postRequest("categories","json", requestBody);
        try{
            responseBody= new JSONObject(response.body().string());
        }catch (IOException e){
            e.printStackTrace();
        }

        Response getCurrentCategories= call.getRequest("categories", "json");
        try{
            getCategoryResponseBody= new JSONObject(getCurrentCategories.body().string());
            currentCategoriesCount= getCategoryResponseBody.getJSONArray("categories").length();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @When("a user adds a project with title {string}, and description {string}")
    public void a_user_adds_a_project_with_title_and_description(String projectTitle, String projectDescription) {
        Api call= new Api();
        JSONObject getProjectResponseBody=null;
        Response getPreviousProjects=call.getRequest("projects", "json");
        try{
            getProjectResponseBody= new JSONObject(getPreviousProjects.body().string());
            previousProjectCount= getProjectResponseBody.getJSONArray("projects").length();
        }catch (IOException e){
            e.printStackTrace();
        }
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", projectTitle);
        requestBody.put("description", projectDescription);
        response=call.postRequest("projects", "json", requestBody);
        try{
            responseBody= new JSONObject(response.body().string());
        }catch (IOException e){
            e.printStackTrace();
        }
        Response getCurrentProjects=call.getRequest("projects", "json");
        try{
            getProjectResponseBody=new JSONObject(getCurrentProjects.body().string());
            currentProjectCount=getProjectResponseBody.getJSONArray("projects").length();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @When("a user adds a project with title {string}")
    public void a_user_adds_a_project_with_title(String projectTitle) {
        Api call= new Api();
        JSONObject getProjectResponseBody=null;
        Response getPreviousProjects=call.getRequest("projects", "json");
        try{
            getProjectResponseBody= new JSONObject(getPreviousProjects.body().string());
            previousProjectCount= getProjectResponseBody.getJSONArray("projects").length();
        }catch (IOException e){
            e.printStackTrace();
        }
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", projectTitle);

        response=call.postRequest("projects", "json", requestBody);
        try{
            responseBody= new JSONObject(response.body().string());
        }catch (IOException e){
            e.printStackTrace();
        }
        Response getCurrentProjects=call.getRequest("projects", "json");
        try{
            getProjectResponseBody=new JSONObject(getCurrentProjects.body().string());
            currentProjectCount=getProjectResponseBody.getJSONArray("projects").length();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @When("a user deletes the category with id {string}")
    public void a_user_deletes_the_category_with_id(String categoryId) {
        Api call= new Api();
        response=call.deleteRequest("categories?id=" + categoryId);
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @When("a user deletes the project with id {string}")
    public void a_user_deletes_the_project_with_id(String projectId) {
        Api call = new Api();
        response=call.deleteRequest("projects?id=" + projectId);
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("a user deletes the todo with id {string}")
    public void a_user_deletes_the_todo_with_id(String todoId) {
        Api call = new Api();
        response=call.deleteRequest("todos?id=" + todoId);
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @When("user filters the endpoint to delete todo with id {string}")
    public void user_filters_the_endpoint_to_delete_todo_with_id(String todoId){
        Api call = new Api();
        response=call.deleteRequest("todos?id=" + todoId);
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @When("user filters the endpoint to delete project with id {string}")
    public void user_filters_the_endpoint_to_delete_project_with_id(String projectId){
        Api call = new Api();
        response = call.getRequest("projects?id=" + projectId, "json");
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @When("user filters the endpoint to delete category with id {string}")
    public void user_filters_the_endpoint_to_delete_category_with_id(String categoryId){
        Api call = new Api();
        response = call.getRequest("categories?id=" + categoryId, "json");
        try {
            responseBody = new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Justin's part here

    @When("a user adds a relationship between a category with id {int} and a todo with id {int}")
    public void a_user_adds_a_relationship_between_a_category_with_id_and_a_todo_with_id(Integer category_id, Integer todo_id) {
        Api call= new Api();
        JSONObject getCategoryTodoResponseBody =null;
//        Response getPreviousCategoryTodo=call.getRequest("categories?id=" + category_id + "/todos", "json");
        Response getPreviousCategoryTodo=call.getRequest("todos", "json");

        try {
            getCategoryTodoResponseBody = new JSONObject(getPreviousCategoryTodo.body().string());
            //System.out.print(getCategoryTodoResponseBody);
            previousCategoryTodoCount= getCategoryTodoResponseBody.getJSONArray("todos").length();
            //System.out.print(previousCategoryTodoCount);
        }catch (IOException e){
            e.printStackTrace();
        }

        JSONObject requestBody = new JSONObject();
        requestBody.put("id", category_id);
        //System.out.print(requestBody);

        //response=call.postRequest("todos/" + todo_id + "/categories", "json", requestBody);
        //System.out.print(response);





        try{
            responseBody= new JSONObject(response.body().string());
        }catch (IOException e){
            e.printStackTrace();
        }
        Response getCurrentProjects=call.getRequest("todos/" + todo_id + "/categories", "json");
        ///System.out.print(getCurrentProjects);

        JSONObject getTodoResponseBody = new JSONObject(getCurrentProjects.body());
        //System.out.print(getTodoResponseBody);

        try{
            getCategoryTodoResponseBody=new JSONObject(getCurrentProjects.body().string());
            currentProjectCount=getCategoryTodoResponseBody.getJSONArray("categories?id=" + category_id + "/todos").length();
            //System.out.print(currentProjectCount);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @When("a user adds a relationship between project with id {int} and the category with id {int}")
    public void a_user_adds_a_relationship_between_project_with_id_and_the_category_with_id(Integer project_id, Integer category_id) {

        Api call= new Api();
        JSONObject getCategoryTodoResponseBody =null;
//        Response getPreviousCategoryTodo=call.getRequest("categories?id=" + category_id + "/todos", "json");
        //Response getPreviousCategoryTodo=call.getRequest("todos", "json");

//        try {
//            getCategoryTodoResponseBody = new JSONObject(getPreviousCategoryTodo.body().string());
//            //System.out.print(getCategoryTodoResponseBody);
//            previousCategoryTodoCount= getCategoryTodoResponseBody.getJSONArray("todos").length();
//            //System.out.print(previousCategoryTodoCount);
//        }catch (IOException e){
//            e.printStackTrace();
//        }

//        JSONObject requestBody = new JSONObject();
//        requestBody.put("id", category_id);
//        //System.out.print(requestBody);
//
//        response=call.postRequest("projects?id=" + project_id + "/categories", "json", requestBody);
//        //System.out.print(response);
//
//        try{
//            URL url = new URL("http://localhost:4567/project/"+project_id);
//            HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
//            int status_code = connection_url.getResponseCode();
//            System.out.print(status_code);
//            assertEquals(HttpURLConnection.HTTP_OK, status_code);
//        }catch (IOException e){
//            e.printStackTrace();
//        }

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






        try{
            responseBody= new JSONObject(response.body().string());
        }catch (IOException e){
            e.printStackTrace();
        }
        Response getCurrentProjects=call.getRequest("projects?id=" + project_id + "/categories", "json");
        System.out.print(getCurrentProjects);

        JSONObject getTodoResponseBody = new JSONObject(getCurrentProjects.body());
        //System.out.print(getTodoResponseBody);

        try{
            getCategoryTodoResponseBody=new JSONObject(getCurrentProjects.body().string());
            currentProjectCount=getCategoryTodoResponseBody.getJSONArray("projects?id=" + project_id + "/categories").length();
            //System.out.print(currentProjectCount);
        }catch (IOException e){
            e.printStackTrace();
        }



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
            System.out.println("Status code: " + add_status_code);

            // Optionally print the response body
            System.out.println("Response body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }












//    @When("a user adds a project with title {string}")
//    public void a_user_adds_a_project_with_title(String projectTitle) {
//        Api call= new Api();
//        JSONObject getProjectResponseBody=null;
//        Response getPreviousProjects=call.getRequest("projects", "json");
//        try{
//            getProjectResponseBody= new JSONObject(getPreviousProjects.body().string());
//            previousProjectCount= getProjectResponseBody.getJSONArray("projects").length();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("title", projectTitle);
//
//        response=call.postRequest("projects", "json", requestBody);
//        try{
//            responseBody= new JSONObject(response.body().string());
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        Response getCurrentProjects=call.getRequest("projects", "json");
//        try{
//            getProjectResponseBody=new JSONObject(getCurrentProjects.body().string());
//            currentProjectCount=getProjectResponseBody.getJSONArray("projects").length();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//    }














    @Then("user should not see response of todo with id {string}")
    public void user_should_not_see_response_of_todo_with_id(String todoId){
        assertEquals(0, responseBody.getJSONArray("todos").length(), "ERROR: Only one todo should be returned");
        JSONArray todosArray = responseBody.getJSONArray("todos");
        JSONObject returnedTodo = todosArray.getJSONObject(0);
        String returnedId = returnedTodo.getString("id");
        assertEquals(todoId, returnedId, "ERROR: the returned todo does not have the expected id.");
    }

    @Then("user should not see response of project with id {string}")
    public void user_should_not_see_response_of_project_with_id(String projectId){
        assertEquals(0, responseBody.getJSONArray("projects").length(), "ERROR: Only one project should be returned");
        JSONArray projectsArray = responseBody.getJSONArray("projects");
        JSONObject returnedProject = projectsArray.getJSONObject(0);
        String returnedId = returnedProject.getString("id");
        assertEquals(projectId, returnedId, "ERROR: the returned project does not have the expected id.");
    }

    @Then("user should not see response of category with id {string}")
    public void user_should_not_see_response_of_category_with_id(String categoryId){
        assertEquals(0, responseBody.getJSONArray("categories").length(), "ERROR: Only one category should be returned");
        JSONArray categoriesArray = responseBody.getJSONArray("categories");
        JSONObject returnedCategory = categoriesArray.getJSONObject(0);
        String returnedId = returnedCategory.getString("id");
        assertEquals(categoryId, returnedId, "ERROR: the returned category does not have the expected id.");
    }



    @Then("a new category with title {string} is added")
    public void a_new_category_with_title_is_added(String categoryTitle) {
        assertEquals(1, currentCategoriesCount-previousCategoriesCount);
        assertEquals(categoryTitle, responseBody.getString("title"));

    }

    @Then("a new todo with title {string}")
    public void a_new_todo_with_title(String todoTitle) {
        assertEquals(1, currentTodosCount-previousTodosCount);
        assertEquals(todoTitle, responseBody.getString("title"));
    }

    @Then("a new project with title {string}, and description {string} is added")
    public void a_new_project_with_title_and_description_is_added(String projectTitle, String projectDescription) {
        assertEquals(1, currentProjectCount-previousProjectCount);
        assertEquals(projectTitle, responseBody.getString("title"));
        assertEquals(projectDescription, responseBody.getString("description"));
    }

    @Then("a new todo with title {string} and description {string}")
    public void a_new_todo_with_title_and_description(String todoTitle, String todoDescription) {
        assertEquals(1, currentTodosCount-previousTodosCount);
        assertEquals(todoTitle, responseBody.getString("title"));
        assertEquals(todoDescription, responseBody.getString("description"));

    }

    @Then("a new project with title {string} is added")
    public void a_new_project_with_title_is_added(String projectTitle) {
        assertEquals(1, currentProjectCount-previousProjectCount);
        assertEquals(projectTitle, responseBody.getString("title"));
    }

    @Then("a new category with title {string} and description {string} is added")
    public void a_new_category_with_title_and_description_is_added(String categoryTitle, String categoryDescription) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(1, currentCategoriesCount-previousCategoriesCount);
        assertEquals(categoryTitle, responseBody.getString("title"));
        assertEquals(categoryDescription, responseBody.getString("description"));
    }

    @Then("the category is not added")
    public void the_category_is_not_added() {
        assertEquals(0,currentCategoriesCount-previousCategoriesCount);
    }

    @Then("the category is removed")
    public void the_category_is_removed() {
        assertEquals(0,currentCategoriesCount-previousCategoriesCount);
    }

    @Then("a status code {string} with response phrase {string} is returned")
    public void a_status_code_with_response_phrase_is_returned(String statusCode, String responsePhrase) {
        assertEquals(Integer.parseInt(statusCode), response.code(),
                "ERROR: The response phrase is: " + response.message() +
                        "\n instead of : " + responsePhrase );
        assertEquals(responsePhrase, response.message() , "ERROR: The actual response phrase does not match the expected response phrase.");
    }

    @Then("the project is not added")
    public void the_project_is_not_added() {
        assertEquals(0,currentProjectCount-previousProjectCount);
    }

    @Then("the todo is not added")
    public void the_todo_is_not_added(){
        assertEquals(0, currentTodosCount- previousTodosCount);
    }





}
