import io.cucumber.java.en.*;
import okhttp3.Response;
import org.example.Api;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AmendStepDefs {

    /* HELPER VARIABLES */

    Response responseTracker;

    String currentID;

    /* HELPER METHODS */

    private void reset(){
        for(Element e: Element.values()){
            resetElement(e);
        }
    }
    private String getPluralElement(Element element){
        String pluralElement = "";
        switch (element) {
            case CATEGORY:
                pluralElement = "categories";
                break;
            case TODO:
                pluralElement = "todos";
                break;
            case PROJECT:
                pluralElement = "projects";
                break;
        }
        return pluralElement;
    }
    private JSONArray getJSONArray(Response r, String group){
        JSONObject obj = null;
        try {
            obj = new JSONObject(r.body().string());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return obj.getJSONArray(group);
    }
    private void resetElement(Element element){
        Api call = new Api();
        String group = getPluralElement(element);
        Response r = call.getRequest(group, "json");
        JSONArray allElementsJSONArray = getJSONArray(r, group);

        for (Object obj : allElementsJSONArray) {
            JSONObject objJSON = (JSONObject) obj;
            String id = objJSON.getString("id");
            switch (element){
                case CATEGORY:
                case TODO:
                    if (!id.equals("1") && !id.equals("2")) {
                        r = call.deleteRequest(group + "/" + id);
                    }
                    break;
                case PROJECT:
                    if (!id.equals("1")) {
                        r = call.deleteRequest(group + "/" + id);
                    }
                    break;
            }
        }
    }
    private boolean elementExistsByTitle(Element element, String targetTitle){
        Api call = new Api();
        String group = getPluralElement(element);
        Response r = call.getRequest(group, "json");
        JSONArray allElementsJSONArray = getJSONArray(r, group);
        for (Object obj : allElementsJSONArray) {
            String title = ((JSONObject) obj).getString("title");
            if(title.equals(targetTitle)){
                return true;
            }
        }
        return false;
    }
    private boolean isMatch(Element element, String targetTitle, String targetField, String targetContent ){
        Api call = new Api();
        String group = getPluralElement(element);
        Response r = call.getRequest(group, "json");
        JSONArray allElementsJSONArray = getJSONArray(r, group);
        for (Object obj : allElementsJSONArray) {
            String title = ((JSONObject) obj).getString("title");
            if(title.equals(targetTitle)){
                switch (element) {
                    case CATEGORY:
                        String field = ((JSONObject) obj).getString(targetField);
                        if(field.toString().equals(targetContent)) {
                            return true;
                        }
                        break;
                    case PROJECT:
                        boolean field2 = ((JSONObject) obj).getBoolean(targetField);
                        if(field2) {
                            return true;
                        }
                        break;
                    case TODO:
                        boolean field3 = ((JSONObject) obj).getBoolean(targetField);
                        if(field3) {
                            return true;
                        }
                        break;

                }

            }
        }
        return false;
    }
    private String getElementIdByTitle(Element element, String targetTitle){
        Api call = new Api();
        String group = getPluralElement(element);
        Response r = call.getRequest(group, "json");
        JSONArray allElementsJSONArray = getJSONArray(r, group);
        for (Object obj : allElementsJSONArray) {
            String title = ((JSONObject) obj).getString("title");
            if(title.equals(targetTitle)){
                return ((JSONObject) obj).getString("id");
            }
        }
        return "";
    }

    /* STEP DEFINITIONS*/

    // Amend Category

    @Given("a category with title {string}, and description {string}")
    public void aCategoryWithTitleAndDescription(String title, String description) {
        // TODO: Create a category with title, description
        // TODO: Assert that the category is added correctly
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("description", description);
        Response response = call.postRequest("categories", "json", requestBody);
        assertTrue(elementExistsByTitle(Element.CATEGORY, title));
    }
    @When("a user changes the description of category with title {string} to {string} by using the PUT API call")
    public void aUserChangesTheDescriptionOfCategoryWithTitleToByUsingThePUTAPICall(String title, String description) {
        // TODO: Get the id of the category with title
        // TODO: Change description by doing a PUT for that ID
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("description", description);
        String id = getElementIdByTitle(Element.CATEGORY, title);
        Response response = call.putRequest("categories/" + id, "json", requestBody);
    }
    @Then("the category with title {string} should have a description of {string}")
    public void theCategoryWithTitleShouldHaveADescriptionOf(String title, String description) {
        assertTrue(isMatch(Element.CATEGORY, title, "description", description));
        reset();
    }
    @When("a user changes the description of category with title {string} to {string} by using the POST API call")
    public void aUserChangesTheDescriptionOfCategoryWithTitleToByUsingThePOSTAPICall(String title, String description) {
        // TODO: Get the id of the category with title
        // TODO: Change description by doing a POST for that ID
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("description", description);
        String id = getElementIdByTitle(Element.CATEGORY, title);
        Response response = call.postRequest("categories/" + id, "json", requestBody);
    }
    @When("a user changes the description of category {string}")
    public void aUserChangesTheDescriptionOfCategory(String id) {
        // TODO attempt to change the doneStatus of the todo with id
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("description", "new description");
        Response response = call.postRequest("categories/" + id, "json", requestBody);
        responseTracker = response;
    }
    @Then("a status code {string} is returned")
    public void aStatusCodeIsReturned(String code) {
        assertEquals(Integer.parseInt(code), responseTracker.code());
        reset();
    }

    // Amend Project

    @Given("a project with title {string}, description {string}, completed status {string}, and active_status {string} exists")
    public void aProjectWithTitleDescriptionCompletedStatusAndActive_statusExists(String title, String description, String completed, String active) {
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("description", description);
        requestBody.put("completed", Boolean.valueOf(completed));
        requestBody.put("active", Boolean.valueOf(active));
        Response response = call.postRequest("projects", "json", requestBody);
        assertTrue(elementExistsByTitle(Element.PROJECT, title));
    }

    @When("a user marks the project {string} as complete by using the PUT API call")
    public void aUserMarksTheProjectAsCompleteByUsingThePUTAPICall(String title) {
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("completed", true);
        String id = getElementIdByTitle(Element.PROJECT, title);
        Response response = call.putRequest("projects/" + id, "json", requestBody);
    }

    @Then("the project with title {string} should have a completed status of {string}")
    public void theProjectWithTitleShouldHaveACompletedStatusOf(String title, String completed) {
        assertTrue(isMatch(Element.PROJECT, title, "completed", completed));
        reset();
    }

    @When("a user marks the project {string} as complete by using the POST API call")
    public void aUserMarksTheProjectAsCompleteByUsingThePOSTAPICall(String title) {
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("completed", true);
        String id = getElementIdByTitle(Element.PROJECT, title);
        Response response = call.postRequest("projects/" + id, "json", requestBody);
    }

    @When("a user marks the project {string} as complete")
    public void aUserMarksTheProjectAsComplete(String id) {
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("completed", true);
        Response response = call.postRequest("projects/" + id, "json", requestBody);
        responseTracker = response;
    }

    // Amend TODO

    @Given("a todo with title {string}, description {string} and doneStatus {string} exists")
    public void aTodoWithTitleDescriptionAndDoneStatusExists(String title, String description, String doneStatus) {
        // TODO: Create a todo with title, description and doneStatus
        // TODO: Assert that the todo is added correctly
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("description", description);
        requestBody.put("doneStatus", Boolean.valueOf(doneStatus));
        Response response = call.postRequest("todos", "json", requestBody);
        assertTrue(elementExistsByTitle(Element.TODO, title));
    }

    @When("a user marks the todo with title {string} as done by using the PUT API call")
    public void aUserMarksTheTodoWithTitleAsDoneByUsingThePUTAPICall(String title) {
        // TODO: Get the id of the todo with title
        // TODO: Change doneStatus by doing a put for that ID
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("doneStatus", true);
        String id = getElementIdByTitle(Element.TODO, title);
        Response response = call.putRequest("todos/" + id, "json", requestBody);
    }

    @Then("the todo with title {string} should have a doneStatus of {string}")
    public void theTodoWithTitleShouldHaveADoneStatusOf(String title, String doneStatus) {
        // TODO: Get the id of the todo with title
        // TODO: Get the todo by id
        // TODO: Check that the todo has the right doneStatus
        assertTrue(isMatch(Element.TODO, title, "doneStatus", doneStatus));
        reset();
    }

    @When("a user marks the todo with title {string} as done by using the POST API call")
    public void aUserMarksTheTodoWithTitleAsDoneByUsingThePOSTAPICall(String title) {
        // Get the id of the todo with title
        // Change doneStatus by doing a put for that ID
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", title);
        requestBody.put("doneStatus", true);
        String id = getElementIdByTitle(Element.TODO, title);
        Response response = call.postRequest("todos/" + id, "json", requestBody);
    }

    @When("a user marks the todo {string} as done")
    public void aUserMarksTheTodoAsDone(String id) {
        // attempt to change the doneStatus of the todo with id
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        requestBody.put("doneStatus", true);
        Response response = call.postRequest("todos/" + id, "json", requestBody);
        responseTracker = response;
    }

    // TODO

    @When("a user gets the project with title {string} by id")
    public void aUserGetsTheProjectWithTitleById(String title) {
        currentID = getElementIdByTitle(Element.PROJECT, title);
        Api call = new Api();
        responseTracker = call.getRequest("projects/" + currentID, "json");
    }

    @Then("the project with title {string} is returned")
    public void theProjectWithTitleIsReturned(String title) {
        JSONArray array = getJSONArray(responseTracker, "projects");
        array.getJSONObject(0).getString("title");
        assertTrue(title.equals(array.getJSONObject(0).getString("title")));
        reset();
    }

    @Given("there are a number of projects {string} in the system")
    public void thereAreANumberOfProjectsInTheSystem(String x) {
        int numOfProjects = Integer.parseInt(x);
        Api call = new Api();
        JSONObject requestBody = new JSONObject();
        Response response;
        response = call.getRequest("projects", "json");
        JSONArray array = getJSONArray(response, "projects");
        for(int i = 0; i < numOfProjects- array.length(); i++){
            requestBody.put("title", "test title");
            requestBody.put("description", "test description");
            requestBody.put("completed", Boolean.valueOf("true"));
            requestBody.put("active", Boolean.valueOf("false"));
            response = call.postRequest("projects", "json", requestBody);
        }
    }

    @When("a user gets all of the the projects in the system")
    public void aUserGetsAllOfTheTheProjectsInTheSystem() {
        Api call = new Api();
        responseTracker = call.getRequest("projects", "json");
    }

    @Then("{string} projects are returned")
    public void projectsAreReturned(String x) {
        JSONArray array = getJSONArray(responseTracker, "projects");
        assertEquals(Integer.parseInt(x), array.length());
        reset();
    }

    @When("a user gets the project with id {string}")
    public void aUserGetsTheProjectWithId(String id) {
        Api call = new Api();
        responseTracker = call.getRequest("projects/" + id, "json");
    }

    // delete relationship todo project
    @And("there is a tasks relationship between the todo with title {string} and the project with title {string}")
    public void thereIsATasksRelationshipBetweenTheTodoWithTitleAndTheProjectWithTitle(String todoTitle, String projectTitle) {
        Api call = new Api();
        String todoId = getElementIdByTitle(Element.TODO, todoTitle);
        String projectId = getElementIdByTitle(Element.PROJECT, projectTitle);

        JSONObject requestBody = new JSONObject();
        requestBody.put("id", todoId);
        Response response = call.postRequest("projects/" + projectId + "/tasks", "json", requestBody);

        //System.out.println(response);
        //reset();
    }

    @When("a user deletes the tasks relationship between the todo with title {string} and the project with title {string} with the todo API")
    public void aUserDeletesTheTasksRelationshipBetweenTheTodoWithTitleAndTheProjectWithTitleWithTheTodoAPI(String todoTitle, String projectTitle) {
        Api call = new Api();
        String todoId = getElementIdByTitle(Element.TODO, todoTitle);
        String projectId = getElementIdByTitle(Element.PROJECT, projectTitle);

        Response response = call.deleteRequest("todos/" + todoId + "/tasksof/" + projectId);
    }

    @Then("the todo with title {string} and the project with title {string} do not have a relationship")
    public void theTodoWithTitleAndTheProjectWithTitleDoNotHaveARelationship(String todoTitle, String projectTitle) {
        Api call = new Api();
        String todoId = getElementIdByTitle(Element.TODO, todoTitle);
        Response response = call.getRequest("todos/" + todoId + "/tasksof", "json");
        JSONArray array = getJSONArray(response, "projects");
        assertEquals(0, array.length());
        reset();
    }

    @When("a user deletes the tasks relationship between the todo with title {string} and the project with title {string} with the project API")
    public void aUserDeletesTheTasksRelationshipBetweenTheTodoWithTitleAndTheProjectWithTitleWithTheProjectAPI(String todoTitle, String projectTitle) {
        Api call = new Api();
        String todoId = getElementIdByTitle(Element.TODO, todoTitle);
        String projectId = getElementIdByTitle(Element.PROJECT, projectTitle);

        responseTracker = call.deleteRequest("projects/" + projectId + "/tasks/" + todoId);
    }
}
