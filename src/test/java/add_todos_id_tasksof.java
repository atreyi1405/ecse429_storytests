//
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
//import java.net.URL;
//import java.net.HttpURLConnection;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import org.json.JSONObject;
//
//public class add_todos_id_tasksof {
//    String temp;
//    String add_status_code;
//    @Given("I have a todo with ID {string}")
//    public void i_have_a_todo_with_id(String todoId) throws IOException {
//        // Write code here that turns the phrase above into concrete actions
//        URL url = new URL("http://localhost:4567/todos/"+todoId);
//        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
//        int status_code = connection_url.getResponseCode();
//        assertEquals(HttpURLConnection.HTTP_OK, status_code);
//    }
//
//    @Given("I have a project with ID {string}")
//    public void i_have_a_project_with_id(String string) throws IOException {
//        // Write code here that turns the phrase above into concrete actions
//        URL url = new URL("http://localhost:4567/projects/"+string);
//        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
//        int status_code = connection_url.getResponseCode();
//        assertEquals(HttpURLConnection.HTTP_OK, status_code);
//    }
//    @Given("I create a project with title {string}, completed {string}, description {string}, active {string}")
//    public void i_create_a_project_with_title_completed_description_active(String string, String string2, String string3, String string4) {
//        // Write code here that turns the phrase above into concrete actions
//
//        HttpClient posttodo = HttpClient.newHttpClient();
//
//        // XML data that we want to send
//        String xmlData = """
//            <project>
//                <active>""" + string4 + """
//                </active>
//                <description> """ + string3 + """
//                </description>
//                <completed>""" + string2 + """
//                </completed>
//                <title>""" + string + """
//            </title>
//            </project>""";
//
//        HttpRequest req = HttpRequest.newBuilder()
//                .uri(URI.create("http://localhost:4567/projects")) // Use the correct URL here
//                .header("Content-Type", "application/xml") // Set the header to accept XML
//                .POST(HttpRequest.BodyPublishers.ofString(xmlData)) // Use the XML data as the body of the POST request
//                .build();
//
//        try {
//            // Send the request and receive the response
//            HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
//
//            // Get the status code from the response
//            int temp = response.statusCode();
//
//            // Convert the status code to String if needed
//            String add_status_code = Integer.toString(temp);
//
//            // Print out the status code
//            System.out.println("Status code: " + add_status_code);
//
//            // Optionally print the response body
//            System.out.println("Response body: " + response.body());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    @When("I request to add a relationship tasksof between todo {string} and projects {string}")
//    public void i_request_to_add_a_relationship_between_todo_and_tasksof(String string, String string2) throws IOException, InterruptedException {
//        // Write code here that turns the phrase above into concrete actions
//        HttpClient posttodo = HttpClient.newHttpClient();
//        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+string+"/tasksof")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\""+string2+"\"}")).build();
//        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
//    }
//    @Then("the relationship between todo {string} and project should be created")
//    public void the_relationship_between_todo_and_tasksof_should_be_created(String string) throws IOException {
//        // Write code here that turns the phrase above into concrete actions
//        URL url = new URL("http://localhost:4567/todos/"+string+"/tasksof");
//        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
//        int status_code = connection_url.getResponseCode();
//        assertEquals(HttpURLConnection.HTTP_OK, status_code);
//
//
//
//    }
//
//    @Then("the relationship between todo {string} and project {string} should be created")
//    public void the_relationship_between_todo_and_tasksof_should_be_created1(String string, String string2) throws IOException {
//        // Write code here that turns the phrase above into concrete actions
//
//        try {
//            URL url = new URL("http://localhost:4567/todos/" +string + "/tasksof");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//
//            int status_code = connection.getResponseCode();
//            if (status_code == HttpURLConnection.HTTP_OK) {
//                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String inputLine;
//                StringBuffer content = new StringBuffer();
//                while ((inputLine = in.readLine()) != null) {
//                    content.append(inputLine);
//                }
//                in.close();
//
//                JSONObject jsonResponse = new JSONObject(content.toString());
//                // Assuming the JSON object has a key called "id" for the ID
//
//// Get the JSON array associated with the key "projects"
//                JSONArray projects = jsonResponse.getJSONArray("projects");
//                String[] id = new String[1];
//// Loop through each object in the JSON array
//                for (int i = 0; i < projects.length(); i++) {
//                    // Get the individual project as a JSONObject
//                    JSONObject project = projects.getJSONObject(i);
//
//                    // Check if the project has an "id" key
//                    if (project.has("id")) {
//                        // Get the value of the "id" field
//                        id[0] = project.getString("id");
//
//                        // Now you have the ID, you can do something with it
////                        System.out.println("Project ID: " + id);
//                    }
//                }
//
//                // Now you have the ID as a string. To convert it into a JSON object:
//                temp= id[0];
//
//            } else {
//                // Handle error...
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if(temp==string2){
//            assertEquals(200, HttpURLConnection.HTTP_OK);
//        }
//
//    }
//
//
//    @When("I request to add a relationship tasksof between todo {string} and a non existent project with id {string}")
//    public void i_request_to_add_a_relationship_tasksof_between_todo_and_a_non_existent_project_with_id(String string, String string2) throws IOException, InterruptedException {
//        // Write code here that turns the phrase above into concrete actions
//        HttpClient posttodo = HttpClient.newHttpClient();
//        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+string+"/tasksof")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\""+string2+"\"}")).build();
//        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
//        int temp = response.statusCode();
//        add_status_code = Integer.toString(temp);
//    }
//    @Then("I get an error code {string}")
//    public void i_get_an_error_code(String string) {
//        // Write code here that turns the phrase above into concrete actions
//        assertEquals(add_status_code,string);
//    }
//
//
//}
