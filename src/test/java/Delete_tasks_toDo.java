//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import okhttp3.Response;
//import org.example.Api;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import io.restassured.RestAssured;
//import org.junit.jupiter.api.*;
//
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URI;
//import java.net.URL;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class Delete_tasks_toDo {
//
//    HttpResponse<String> response1;
//    @Given("the service is running")
//    public void the_service_is_running() {
//        // Write code here that turns the phrase above into concrete actions
//        Api call = new Api();
//        // Verify that the server is running
//        Response response = call.checkService();
//        assertEquals(200, response.code());
//
//    }
//    @Given("I have a todo with the ID {string}")
//    public void i_have_a_todo_with_the_id(String todoId) throws IOException {
//        // Write code here that turns the phrase above into concrete actions
//        URL url = new URL("http://localhost:4567/todos/"+todoId);
//        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
//        int status_code = connection_url.getResponseCode();
//        assertEquals(HttpURLConnection.HTTP_OK, status_code);
//
//    }
//    @Given("I have a tasksof with the ID {string}")
//    public void i_have_a_tasksof_with_the_id(String projectId) throws IOException, InterruptedException {
//        int id = 2;
//        HttpClient posttodo = HttpClient.newHttpClient();
//        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+id+"/tasksof")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\"}")).build();
//        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
//        assertEquals(201, response.statusCode());
//    }
//    @When("I request to delete the relationship between todo {string} and tasksof {string} relationship")
//    public void i_request_to_delete_the_relationship_between_todo_and_tasksof_relationship(String string, String string2) throws IOException, InterruptedException {
//        // Write code here that turns the phrase above into concrete actions
//        int id = 2;
//        int tasksofid = 1;
//        HttpClient posttodo = HttpClient.newHttpClient();
//        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+string+"/tasksof/"+string2)).DELETE().build();
//        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
//        response1 = response;
//        assertEquals(200, response.statusCode());
//    }
//    @Then("the relationship between todo {string} and tasksof {string} should no longer exist")
//    public void the_relationship_between_todo_and_tasksof_should_no_longer_exist(String string, String string2) throws IOException, InterruptedException {
//        // Write code here that turns the phrase above into concrete actions
//        int id = 2;
//        int tasksofid = 1;
//        HttpClient posttodo = HttpClient.newHttpClient();
//        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+string+"/tasksof/"+string2)).DELETE().build();
//        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
//        assertEquals(HttpURLConnection.HTTP_NOT_FOUND, response.statusCode());
//    }
//
//    @When("I request to delete the relationship between todo {string} and tasksof {string} relationship using filter")
//    public void i_request_to_delete_the_relationship_between_todo_and_tasksof_relationship_using_filter(String string, String string2) throws IOException, InterruptedException {
//        // Write code here that turns the phrase above into concrete actions
//        HttpClient posttodo = HttpClient.newHttpClient();
//        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+string+"/tasksof/"+string2)).DELETE().build();
//        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
//        response1 = response;
//        assertEquals(200, response.statusCode());
//    }
//}
