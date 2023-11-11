import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.Response;
import org.example.Api;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class View_categories {

    String status_code;
    String last_status;

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

    @Given("the category with id {string} exists")
    public void the_category_with_id_exists(String string) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        URL url = new URL("http://localhost:4567/categories/"+string);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }
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


}
