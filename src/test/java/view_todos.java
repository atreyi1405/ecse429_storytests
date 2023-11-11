//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import okhttp3.Response;
//import org.example.Api;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
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
//
//public class view_todos {
//
//    String view_todos_status_code;
//    String view_todos_last_status;
//    Response response;
//    String view_todos_string;
//    @Given("the service is running")
//    public void the_service_is_running() {
//        // Write code here that turns the phrase above into concrete actions
//        Api call = new Api();
//        // Verify that the server is running
//        response = call.checkService();
//        assertEquals(200, response.code());
//    }
//    @Given("a todo item exists with identifier {string}")
//    public void a_todo_item_exists_with_identifier(String string) throws IOException {
//        // Write code here that turns the phrase above into concrete actions
//        URL url = new URL("http://localhost:4567/todos/"+string);
//        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
//        int view_todos_status_code = connection_url.getResponseCode();
//        assertEquals(HttpURLConnection.HTTP_OK, view_todos_status_code);
//    }
//    @When("I request the todo item with identifier {string}")
//    public void i_request_the_todo_item_with_identifier(String string) throws IOException {
//        // Write code here that turns the phrase above into concrete actions
//
//        try {
//            String id = "123"; // Replace with your actual ID
//            URL url = new URL("http://localhost:4567/todos/" + string);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//
//            int responseCode = connection.getResponseCode();
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//
//                // Convert response to JSON object
//                JSONObject jsonResponse = new JSONObject(response.toString());
//
//                JSONArray todosArray = jsonResponse.getJSONArray("todos");
//                String[] titleJson = new String[2];
//// Assuming you want the first item in the array
//                if (todosArray.length() > 0) {
//                    JSONObject firstTodo = todosArray.getJSONObject(0);
//
//                    // Check if the firstTodo has a "title" key
//                    if (firstTodo.has("title")) {
//                        // Extract the title
//                        String title = firstTodo.getString("title");
//                        titleJson[0] = title;
//                        // Output the title
//                        System.out.println("Title: " + title);
//                    } else {
//                        // Handle case where the "title" key doesn't exist
//                        System.out.println("Key 'title' does not exist in the JSON response.");
//                    }
//                } else {
//                    // Handle case where the 'todos' array is empty
//                    System.out.println("'todos' array is empty.");
//                }
//
//
//                view_todos_string = titleJson[0];
//                // Output the title in JSON format
//            } else {
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//
//
//
//        URL url = new URL("http://localhost:4567/todos/"+string);
//        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
//        int temp = connection_url.getResponseCode();
//        view_todos_status_code = Integer.toString(temp);
//    }
//
//    @Then("the response should include a status code of {string} and title {string}")
//    public void the_response_should_include_a_view_todos_status_code_of(String string, String string2) {
//        // Write code here that turns the phrase above into concrete actions
//        assertEquals(string, view_todos_status_code);
//        assertEquals(view_todos_string, string2);
//    }
//
//
//
//    @Given("the todo with id {string} exists")
//    public void the_todo_with_id_exists(String string) throws IOException {
//        // Write code here that turns the phrase above into concrete actions
//        URL url = new URL("http://localhost:4567/todos/"+string);
//        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
//        int view_todos_status_code = connection_url.getResponseCode();
//        assertEquals(HttpURLConnection.HTTP_OK, view_todos_status_code);
//    }
//    @When("I filter the todos list to get the todo with id {string}")
//    public void i_filter_the_todos_list_to_get_the_todo_with_id(String string) throws IOException {
//        // Write code here that turns the phrase above into concrete actions
//        try {
//            String id = "123"; // Replace with your actual ID
//            URL url = new URL("http://localhost:4567/todos?id=" + string);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//
//
//            int responseCode = connection.getResponseCode();
//            view_todos_status_code = Integer.toString(responseCode);
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//
//                // Convert response to JSON object
//                JSONObject jsonResponse = new JSONObject(response.toString());
//
//                JSONArray todosArray = jsonResponse.getJSONArray("todos");
//                String[] titleJson = new String[2];
//// Assuming you want the first item in the array
//                if (todosArray.length() > 0) {
//                    JSONObject firstTodo = todosArray.getJSONObject(0);
//
//                    // Check if the firstTodo has a "title" key
//                    if (firstTodo.has("title")) {
//                        // Extract the title
//                        String title = firstTodo.getString("title");
//                        titleJson[0] = title;
//                        // Output the title
//                        System.out.println("Title: " + title);
//                    } else {
//                        // Handle case where the "title" key doesn't exist
//                        System.out.println("Key 'title' does not exist in the JSON response.");
//                    }
//                } else {
//                    // Handle case where the 'todos' array is empty
//                    System.out.println("'todos' array is empty.");
//                }
//
//
//                view_todos_string = titleJson[0];
//                // Output the title in JSON format
//            } else {
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//
//    }
//    @Then("a status code {string} and title {string} is returned")
//    public void a_view_todos_status_code_is_returned(String string, String string2) {
//        // Write code here that turns the phrase above into concrete actions
//        assertEquals(string, view_todos_status_code);
//        assertEquals(string2, view_todos_string);
//    }
//
//    @Given("the todos list does not contain id {string}")
//    public void the_todos_list_does_not_contain_id(String string) throws IOException {
//        // Write code here that turns the phrase above into concrete actions
//        URL url = new URL("http://localhost:4567/todos/"+string);
//        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
//
//    }
//    @When("I request the todo item with id {string}")
//    public void i_request_the_todo_item_with_id(String string) throws IOException {
//        // Write code here that turns the phrase above into concrete actions
//        URL url = new URL("http://localhost:4567/todos/"+string);
//        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
//        int temp = connection_url.getResponseCode();
//        view_todos_last_status = Integer.toString(temp);
//    }
//
//    @Then("a status code of {string} is returned")
//    public void a_view_todos_status_code_of_is_returned(String string) {
//        // Write code here that turns the phrase above into concrete actions
//        assertEquals(view_todos_last_status,string);
//    }
//
//}
