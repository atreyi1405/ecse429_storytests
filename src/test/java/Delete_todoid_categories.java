//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import okhttp3.Response;
//import org.example.Api;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
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
//
//public class Delete_todoid_categories {
//    HttpResponse<String> response3;
//
//    @Given("I have a category related to todos {string}")
//    public void i_have_a_category_related_to_todos(String string) throws IOException {
//        // Write code here that turns the phrase above into concrete actions
//
//        URL url = new URL("http://localhost:4567/todos/"+string+"/categories");
//        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
//        int status_code = connection_url.getResponseCode();
//        assertEquals(HttpURLConnection.HTTP_OK, status_code);
//    }
//
//    @When("I request to delete the relationship between todo {string} and category {string} relationship")
//    public void i_request_to_delete_the_relationship_between_todo_and_category_relationship(String string, String string2) throws IOException, InterruptedException {
//        // Write code here that turns the phrase above into concrete actions
//        HttpClient posttodo2 = HttpClient.newHttpClient();
//        HttpRequest req2 = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+string+"/categories/"+string2)).DELETE().build();
//        HttpResponse<String> response2 = posttodo2.send(req2, HttpResponse.BodyHandlers.ofString());
//        response3 = response2;
//        assertEquals(200, response2.statusCode());
//    }
//    @Then("the relationship between todo {string} and category {string} should no longer exist")
//    public void the_relationship_between_todo_and_category_should_no_longer_exist(String string, String string2) throws IOException, InterruptedException {
//        // Write code here that turns the phrase above into concrete actions
//        URL url = new URL("http://localhost:4567/todos/"+string+"/categories/"+string2);
//        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
//        int status_code = connection_url.getResponseCode();
//        assertEquals(HttpURLConnection.HTTP_NOT_FOUND, status_code);
//    }
//
//}
