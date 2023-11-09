package org.example;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
public class Api {
    //public static void main(String[] args) {
        //System.out.println("Hello world!");
    //}
    String baseUrl = "http://localhost:4567/";

    public Response checkService() {
        OkHttpClient client = new OkHttpClient();
        Response response = null;
        Request request = new Request.Builder()
                .url(baseUrl)
                .get()
                .build();
        try {
            // Send the HTTP request and store the server response in the 'response' variable
            response = client.newCall(request).execute();
        } catch (IOException e) {
            // In case of an exception, return null to indicate failure
            System.out.println("ERROR: The server is not running");
            return null;
        }
        return response;
    }

    public Response getRequest(String url, String contentType){
        OkHttpClient client = new OkHttpClient();
        Response response = null;
        // Construct the full URL by appending the base URL to the endpoint URL
        url = baseUrl + url;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("content-type", "application/" + contentType + "; charset=utf-8")
                .get()
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            return null;
        }
        return response;
    }

    public Response postRequest(String url, String contentType, JSONObject jsonBody){
        OkHttpClient client = new OkHttpClient();
        Response response = null;
        // Construct the full URL by appending the base URL to the endpoint URL
        url = baseUrl + url;

        // Create the request body using the JSON body and content type
        MediaType JSON = MediaType.parse("application/" + contentType + "; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonBody.toString());

        Request request = new Request.Builder()
                .url(url)
                .addHeader("content-type", "application/" + contentType + "; charset=utf-8")
                .post(body)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            return null;
        }
        return response;
    }

    public Response postRequestString(String url, String contentType, String requestBody) {
        OkHttpClient client = new OkHttpClient();
        Response response = null;
        // Construct the full URL by appending the base URL to the endpoint URL
        url = baseUrl + url;

        // Create the request body using the JSON body and content type
        MediaType mediaType = MediaType.parse("application/" + contentType + "; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, requestBody);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("content-type", "application/" + contentType + "; charset=utf-8")
                .post(body)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            return null;
        }
        return response;
    }

    public Response putRequest(String url, String contentType, JSONObject jsonBody){
        OkHttpClient client = new OkHttpClient();
        Response response = null;
        // Construct the full URL by appending the base URL to the endpoint URL
        url = baseUrl + url;

        // Create the request body using the JSON body and content type
        MediaType JSON = MediaType.parse("application/" + contentType + "; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonBody.toString());

        Request request = new Request.Builder()
                .url(url)
                .addHeader("content-type", "application/" + contentType + "; charset=utf-8")
                .put(body)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            return null;
        }
        return response;
    }

    public Response deleteRequest(String url){
        OkHttpClient client = new OkHttpClient();
        Response response = null;
        // Construct the full URL by appending the base URL to the endpoint URL
        url = baseUrl + url;

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            return null;
        }
        return response;
    }
}