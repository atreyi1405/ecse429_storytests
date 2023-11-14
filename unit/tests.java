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


@TestMethodOrder(MethodOrderer.Random.class)
public class tests {

    @BeforeEach
    void ServerStart() throws IOException, InterruptedException {
        try {
            ProcessBuilder pb;
            // String currentdir = System.getProperty("user.dir");
            pb = new ProcessBuilder("java", "-jar", "runTodoManagerRestAPI-1.5.5.jar");
            pb.start();
            Thread.sleep(1000);
        }
        catch (IOException e) {
            System.out.println("Server not started");
        }
    }


    @AfterEach
    void Servershutdown() {
        try {
            RestAssured.get("http://localhost:4567/shutdown");
        }
        catch (Exception e) {
            System.out.println("Server resetting");
        }
    }


    @Test
    public void testServerStart() throws IOException, InterruptedException{
        String url_test = "http://localhost:4567";
        try {
            URL url = new URL(url_test);
            HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
            int status_code = connection_url.getResponseCode();
            assertEquals(HttpURLConnection.HTTP_OK, status_code);

        } catch (IOException e) {
            System.out.println("start server on terminal");
            e.printStackTrace();
        }

    }

    @Test
    public void testTodosGet() throws IOException, InterruptedException {
        URL url = new URL("http://localhost:4567/todos");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }


    @Test
    public void gettodos_head() throws IOException{
        URL url = new URL("http://localhost:4567/todos");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        connection_url.setRequestMethod("HEAD");
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }


    @Test
    public void gettodos_filter() throws IOException{
        URL url = new URL("http://localhost:4567/todos?doneStatus=false&title=filepaperwork");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void gettodoshead_filter() throws IOException {
        URL url = new URL("http://localhost:4567/todos?doneStatus=false&title=filepaperwork");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        connection_url.setRequestMethod("HEAD");
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void postTodos() throws IOException, InterruptedException{
        HttpClient posttodo = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos")).POST(HttpRequest.BodyPublishers.ofString("{" +
                "    \"title\": \"test known id 3\"," +
                "    \"doneStatus\": true," +
                "    \"description\": \"test description 2\"," +
                "    \"tasksof\": [" +
                "        {" +
                "            \"id\": \"1\"" +
                "        }" +
                "    ]," +
                "    \"categories\": [" +
                "        {" +
                "            \"id\": \"1\"" +
                "        }" +
                "    ]" +
                "}")).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
    }

    @Test
    public void postTodos_notformed() throws IOException, InterruptedException {
        HttpClient posttodo = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos")).POST(HttpRequest.BodyPublishers.ofString("{" +
                "    \"title\": \"test known id 3\\" +
                "    \"doneStatus\": true}")).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }



    /**
     * --------------------------------------------TODO - ID-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * @throws IOException
     */
    @Test
    public void gettodobyid() throws IOException{
        int id = 1;
        URL url = new URL("http://localhost:4567/todos/"+id);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void gettodosbyid_head() throws IOException{
        int id = 1;
        URL url = new URL("http://localhost:4567/todos/"+id);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        connection_url.setRequestMethod("HEAD");
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void postTodos_id() throws IOException, InterruptedException{
        int id = 2;
        HttpClient posttodo = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+id)).POST(HttpRequest.BodyPublishers.ofString("{" +
                "    \"title\": \"test\"," +
                "    \"doneStatus\": true," +
                "    \"description\": \"monsters inc\"," +
                "    \"tasksof\": [" +
                "        {" +
                "            \"id\": \"1\"" +
                "        }" +
                "    ]," +
                "    \"categories\": [" +
                "        {" +
                "            \"id\": \"1\"" +
                "        }" +
                "    ]" +
                "}")).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void putTodos_id() throws IOException, InterruptedException{
        int id = 2;
        HttpClient posttodo = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+id)).PUT(HttpRequest.BodyPublishers.ofString("{" +
                "    \"title\": \"test\"," +
                "    \"doneStatus\": true," +
                "    \"description\": \"monsters under the couch\"," +
                "    \"tasksof\": [" +
                "        {" +
                "            \"id\": \"1\"" +
                "        }" +
                "    ]," +
                "    \"categories\": [" +
                "        {" +
                "            \"id\": \"1\"" +
                "        }" +
                "    ]" +
                "}")).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void deleteTodos_id() throws IOException, InterruptedException{
        int id = 1;
        HttpClient posttodo = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+id)).DELETE().build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }


    /**
     * --------------------------------------------Todo-id-taskof------------------------------------------------------------------------------
     * @throws IOException
     */
    @Test
    public void gettodotaskof() throws IOException{
        int id = 1;
        URL url = new URL("http://localhost:4567/todos/"+id+"/tasksof");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void gettodotaskof_head() throws IOException{
        int id = 1;
        URL url = new URL("http://localhost:4567/todos/"+id+"/tasksof");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        connection_url.setRequestMethod("HEAD");
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void posttodos_idtasksof() throws IOException, InterruptedException{
        int id = 2;
        HttpClient posttodo = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+id+"/tasksof")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\"}")).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
    }

    @Test
    public void posttodos_idnotfound() throws IOException, InterruptedException{
        int id = 100;
        HttpClient posttodo = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+id+"/tasksof")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\"}")).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(HttpURLConnection.HTTP_NOT_FOUND, response.statusCode());
    }

    @Test
    public void posttodos_taofnotfound() throws IOException, InterruptedException{
        int id = 1;
        HttpClient posttodo = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+id+"/tasksof")).POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"4\"}")).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(HttpURLConnection.HTTP_NOT_FOUND, response.statusCode());
    }

    // bug - id does not exist so it outputs everything
    @Test
    public void gettodotasksof_notfound() throws IOException{
        int id = 10;
        URL url = new URL("http://localhost:4567/todos/"+id+"/tasksof");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void deleteTodos_idtasksof() throws IOException, InterruptedException{
        int id = 2;
        int tasksofid = 1;
        HttpClient posttodo = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+id+"/tasksof/"+tasksofid)).DELETE().build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    /**
     * -----------------------------------------TODO-id-categories--------------------------------------------------------------------------
     */

    @Test
    public void gettodocategories() throws IOException{
        int id = 1;
        URL url = new URL("http://localhost:4567/todos/"+id+"/categories");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void gettodocategories_head() throws IOException{
        int id = 1;
        URL url = new URL("http://localhost:4567/todos/"+id+"/categories");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        connection_url.setRequestMethod("HEAD");
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }


    @Test
    public void gettodocategories_notfound() throws IOException{
        int id = 10;
        URL url = new URL("http://localhost:4567/todos/"+id+"/categories");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void posttodos_idcategories() throws IOException, InterruptedException{
        int id = 1;
        HttpClient posttodo = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+id+"/categories")).POST(HttpRequest.BodyPublishers.ofString("{\n" +
                "    \"title\": \"test\",\n" +
                "    \"description\": \"montreal\"\n" +
                "}")).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
    }

    @Test
    public void deleteTodos_idcategories() throws IOException, InterruptedException{
        int id = 1;
        int tid = 1;
        HttpClient posttodo2 = HttpClient.newHttpClient();
        HttpRequest req2 = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+id+"/categories/"+tid)).DELETE().build();
        HttpResponse<String> response2 = posttodo2.send(req2, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response2.statusCode());

    }


    /**
     * ---------------------------------------------Categories-----------------------------------------------------------------------------------------------
     * @throws IOException
     */
    @Test
    public void getCategories() throws IOException{
        URL url = new URL("http://localhost:4567/categories");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void getCategories_Filter() throws IOException{
        URL url = new URL("http://localhost:4567/categories?id=2&title=Home");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void getCategories_Header() throws IOException{
        URL url = new URL("http://localhost:4567/categories");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        connection_url.setRequestMethod("HEAD");
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void getCategories_HeaderFilter() throws IOException{
        URL url = new URL("http://localhost:4567/categories?id=2&title=Home");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        connection_url.setRequestMethod("HEAD");
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void postcategories_wihtoutidreqbodynotformed() throws IOException, InterruptedException{
        HttpClient posttodo = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).POST(HttpRequest.BodyPublishers.ofString("{" +
                "    \"id\": \"3\"" +
                "}")).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode());
    }

    @Test
    public void post_categorieswithout_id() throws IOException, InterruptedException{
        HttpClient posttodo = HttpClient.newHttpClient();
        String requestBody = "{\n" +
                "    \"title\": \"test\",\n" +
                "    \"description\": \"quebec\"\n" +
                "}";
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
    }

    // cannot add category with an id - bug
    @Test
    public void post_categorieswith_id() throws IOException, InterruptedException{
        HttpClient posttodo = HttpClient.newHttpClient();
        String requestBody = "{\n" +
                " \"id\": \"4\",\n"  +
                "    \"title\": \"test\",\n" +
                "    \"description\": \"quebec\"\n" +
                "}";
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    /**
     * ----------------------------------------------------Categories-id------------------------------------------------------------------------------------------------
     * @throws IOException
     */
    @Test
    public void getCategoriesbyid() throws IOException{
        int id = 1;
        URL url = new URL("http://localhost:4567/categories/"+id);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }


    @Test
    public void getCategories_Headerbyid() throws IOException{
        int id = 1;
        URL url = new URL("http://localhost:4567/categories/"+id);
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        connection_url.setRequestMethod("HEAD");
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }


    @Test
    public void postcategories_byidreqbodynotformed() throws IOException, InterruptedException{
        HttpClient posttodo = HttpClient.newHttpClient();
        int id = 1;
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/"+id)).POST(HttpRequest.BodyPublishers.ofString("{\n" +
                "    \"title\\\": \"test\",\n" +
                "    \"description\": \"quebec\"\n" +
                "}")).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode());
    }

    @Test
    public void post_categoriesbyid() throws IOException, InterruptedException{
        HttpClient posttodo = HttpClient.newHttpClient();
        int id = 1;
        String requestBody = "{\n" +
                "    \"title\": \"test\",\n" +
                "    \"description\": \"alberta\"\n" +
                "}";
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/"+id)).POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
    }

    @Test
    public void putcategories_id() throws IOException, InterruptedException{
        int id = 1;
        HttpClient posttodo = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/"+id)).PUT(HttpRequest.BodyPublishers.ofString("{\n" +
                "    \"title\": \"test\",\n" +
                "    \"description\": \"mtl\"\n" +
                "}")).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void deletecategories_id() throws IOException, InterruptedException {
        int id = 1;
        HttpClient deletecat = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/"+id)).DELETE().build();
        HttpResponse<String> response = deletecat.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }


    /**
     * ----------------------------------------------------categories-id-todos------------------------------------------------------------------------------------------------
     * @throws IOException
     */

    @Test
    public void gettodosby_categoryid() throws IOException{
        int id = 1;
        URL url = new URL("http://localhost:4567/categories/"+id+"/todos");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }


    /**
     * if for an id which exists in the system, todos for that id does not exist. it still outputs status OK instead of not found. Do you guys think its a bug or a feature ?
     * @throws IOException
     */

    @Test
    public void gettodos_categoryID_head() throws IOException{
        int id = 1;
        URL url = new URL("http://localhost:4567/categories/"+id+"/todos");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        connection_url.setRequestMethod("HEAD");
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void postcategoryID_todos() throws IOException, InterruptedException{
        HttpClient posttodo = HttpClient.newHttpClient();
        int id = 1;
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/"+id+"/todos")).POST(HttpRequest.BodyPublishers.ofString("{\n" +
                "    \"id\": \"1\"\n" +
                "}")).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(HttpURLConnection.HTTP_CREATED, response.statusCode());
    }

    @Test
    public void deletetodoscategoryID() throws IOException, InterruptedException {
        HttpClient posttodo = HttpClient.newHttpClient();
        int id = 1;
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/"+id+"/todos")).POST(HttpRequest.BodyPublishers.ofString("{\n" +
                "    \"id\": \"1\"\n" +
                "}")).build();
        HttpResponse<String> response = posttodo.send(req, HttpResponse.BodyHandlers.ofString());
        int todoid = 1;
        HttpClient deletecat = HttpClient.newHttpClient();
        HttpRequest req2 = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/"+id+"/todos/"+todoid)).DELETE().build();
        HttpResponse<String> response2 = deletecat.send(req2, HttpResponse.BodyHandlers.ofString());
        assertEquals(HttpURLConnection.HTTP_OK, response2.statusCode());
    }


    /**
     * ----------------------------------------------------Projects------------------------------------------------------------------------------------------------
     * @throws IOException
     */
    // Projects
    @Test
    public void getProjects() throws IOException{
        URL url = new URL("http://localhost:4567/projects");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void getProjects_Filter() throws IOException{
        URL url = new URL("http://localhost:4567/projects?id=1&completed=false");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void getProjects_Header() throws IOException{
        URL url = new URL("http://localhost:4567/projects");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        connection_url.setRequestMethod("HEAD");
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void getProjects_HeaderFilter() throws IOException{
        URL url = new URL("http://localhost:4567/projects?id=1&completed=false");
        HttpURLConnection connection_url = (HttpURLConnection) url.openConnection();
        connection_url.setRequestMethod("HEAD");
        int status_code = connection_url.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, status_code);
    }

    @Test
    public void postcategories() throws IOException, InterruptedException {
        HttpClient postcategories = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).POST(HttpRequest.BodyPublishers.ofString("{\"title\":\"New Category Title\", \"description\":\"New category description\"}")).build();
        HttpResponse<String> response = postcategories.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
    }

    @Test
    public void postcategories_reqbodynotformed() throws IOException, InterruptedException {
        HttpClient postcategories = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).POST(HttpRequest.BodyPublishers.ofString("{\"title\":\"New Category Title\"\", \"description\":\"New category description\"}")).build();
        HttpResponse<String> response = postcategories.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

//    // have to check this test again. - not outputing the correct result
//    @Test
//    public void postprojects() throws IOException, InterruptedException {
//        HttpClient postprojects = HttpClient.newHttpClient();
////        String reqbody = "{"+"title"+":"+"Test"+","+"completed"+":"+"false"+","+"active"+":"+"false,"+"description:"+",tasks:[{"+"id:2"+"},{"+"id:1"+"}]}";
//        String reqbody = "{\"title\":\"Work\",\"completed\":\"false\",\"active\":\"false\",\"description\":\"test\"}";
//        String temp = removeQuotesAndUnescape(reqbody);
//        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects")).POST(HttpRequest.BodyPublishers.ofString(reqbody)).build();
//        HttpResponse<String> response = postprojects.send(req, HttpResponse.BodyHandlers.ofString());
//        assertEquals(400, response.statusCode());
//    }
//
//    private String removeQuotesAndUnescape(String uncleanJson) {
//        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
//        return StringEscapeUtils.unescapeJava(noQuotes);
//    }
}
