package tests;

import lib.BaseTestCase;
import lib.DataGeneration;
import lib.Assertions;
import lib.ApiCoreRequests;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Links;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.junit.jupiter.api.DisplayName;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@Epic("User edit test")
@Feature("Edit")
@Link(name = "API methods", url = "https://playground.learnqa.ru/api/map")
public class UserEditTest extends BaseTestCase{
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    
    @Test
    @Description("This test successfully registers a new user and changes the name of this authorized user")
    @DisplayName("Test positive edit user name") 
    @Owner("learnqa.example")
    @Severity(SeverityLevel.CRITICAL)
    public void testEditJustCreatedTest() {
        //Generate user
        Map<String,String> userData = DataGeneration.getRegistrationData();
        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJson("https://playground.learnqa.ru/api/user/",userData);
        
        String userId = responseCreateAuth.getString("id");
        System.out.println(userId);
        //Login 
        Map<String,String> authData = new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        String header = this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        //Edit
        String newName = "Changed Name";
        Map<String,String> editData = new HashMap<>();
        editData.put("firstName", newName);
        
        Response responseEditUser = apiCoreRequests
                .makePutRequest(("https://playground.learnqa.ru/api/user/" + userId), header, cookie, editData);

        //Get
        Response responseUserData =apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, header, cookie);

                //System.out.println(responseUserData.asString());
        Assertions.asserJsonByName(responseUserData, "firstName", newName);
    }
    
    @Test
    @Description("This test successfully registers a new user and does not change the name of this unauthorized user")
    @DisplayName("Test negative edit not auth user data") 
    @Owner("Golosova I.A.")
    @Severity(SeverityLevel.BLOCKER)
    public void testEditTestNotAuthUser() {
        //Generate new user
        Map<String,String> userData = DataGeneration.getRegistrationData();
        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJson("https://playground.learnqa.ru/api/user/",userData);
        
        String userId = responseCreateAuth.getString("id");
        System.out.println(userId);
        //Edit without auth
        String newName = "Changed Name";
        Map<String,String> editData = new HashMap<>();
        editData.put("firstName", newName);
        
        Response responseEditUser = apiCoreRequests
                .makePutRequestOnlyData(("https://playground.learnqa.ru/api/user/" + userId), editData);
        Assertions.asserResponseCodeEquals(responseEditUser, 400);
        Assertions.asserResponseTextEquals(responseEditUser, "Auth token not supplied");

    }    

    @Test
    @Description("This test successfully authorises a new user and does not change the name of another user")
    @DisplayName("Test negative edit another user data") 
    @Owner("Golosova I.A.")
    @Severity(SeverityLevel.BLOCKER)
    public void testEditAnotherUser() {
        //Generate new user
        Map<String,String> userData = DataGeneration.getRegistrationData();
        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJson("https://playground.learnqa.ru/api/user/",userData);
        
        String userId = responseCreateAuth.getString("id");
        System.out.println(userId);
        //Login this user
        Map<String,String> authData = new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        String header = this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        //Edit username another user(ID=2)
        String newName = "Changed Name";
        Map<String,String> editData = new HashMap<>();
        editData.put("username", newName);
        
        Response responseEditUser = apiCoreRequests
                .makePutRequest(("https://playground.learnqa.ru/api/user/2"), header, cookie, editData);
        Assertions.asserResponseCodeEquals(responseEditUser, 200);      
        //Get anoter user data
        Response responseUserData =apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/2", header, cookie);

        Assertions.asserNotJsonByName(responseUserData,"username",newName );
        String[] expectedFields = {"firstName", "lastName","email"};
        Assertions.assertJsonHasNotFields(responseUserData, expectedFields);
        Assertions.assertJsonHasField(responseUserData, "username");
    }
        
    @Test
    @Description("This test does not change the email without @ of authorized user")
    @DisplayName("Test negative edit invalid email without @") 
    @Owner("Golosova I.A.")
    @Severity(SeverityLevel.CRITICAL)
    public void testEditBadEmailTest() {
        //Generate user
        Map<String,String> userData = DataGeneration.getRegistrationData();
        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJson("https://playground.learnqa.ru/api/user/",userData);
        
        String userId = responseCreateAuth.getString("id");
        System.out.println(userId);
        //Login 
        Map<String,String> authData = new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        String header = this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        //Edit email without @
        String newemail= DataGeneration.getBadRandomEmail();
        Map<String,String> editData = new HashMap<>();
        editData.put("email", newemail);
        
        Response responseEditUser = apiCoreRequests
                .makePutRequest(("https://playground.learnqa.ru/api/user/" + userId), header, cookie, editData);

        Assertions.asserResponseCodeEquals(responseEditUser, 400);
        Assertions.asserResponseTextEquals(responseEditUser, "Invalid email format");
        
    }

    @Test
    @Description("This test does not change firstName of authorized user to a very short")
    @DisplayName("Test negative edit name to a very short") 
    @Owner("Golosova I.A.")
    @Severity(SeverityLevel.NORMAL)
    public void testEditVeryShortNameTest() {
        //Generate user
        Map<String,String> userData = DataGeneration.getRegistrationData();
        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJson("https://playground.learnqa.ru/api/user/",userData);
        
        String userId = responseCreateAuth.getString("id");
        System.out.println(userId);
        //Login 
        Map<String,String> authData = new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        String header = this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        //Edit email without @
        String newName = DataGeneration.getRandomName("1");
        Map<String,String> editData = new HashMap<>();
        editData.put("firstName", newName);
        
        Response responseEditUser = apiCoreRequests
                .makePutRequest(("https://playground.learnqa.ru/api/user/" + userId), header, cookie, editData);
        
        Assertions.asserResponseCodeEquals(responseEditUser, 400);
        Assertions.assertJsonHasField(responseEditUser, "error");
        Assertions.asserJsonByName(responseEditUser, "error", "Too short value for field firstName");


    }
}
