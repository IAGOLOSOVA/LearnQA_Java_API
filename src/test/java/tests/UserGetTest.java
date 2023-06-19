package tests;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGeneration;
import lib.ApiCoreRequests;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Links;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;

import org.junit.jupiter.api.DisplayName;

@Epic("Get User Data test")
@Feature("User Data")
@Link(name = "API methods", url = "https://playground.learnqa.ru/api/map")

public class UserGetTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("This test unsuccessfully to get data from an unauthorized user")
    @DisplayName("Test negative get data unauthorised user")
    @Owner("learnqa.example")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetUserDataNotAuth() {
        Response responseUserData = RestAssured
        .get ("https://playground.learnqa.ru/api/user/2")
        .andReturn();
        String[] expectedFields = {"firstName", "lastName","email"};
        Assertions.assertJsonHasNotFields(responseUserData, expectedFields);
        Assertions.assertJsonHasField(responseUserData, "username");
    }

    @Test
    @Description("This test successfully logs in an authorised user and gets this user details")
    @DisplayName("Test positive get data authorise user")
    @Owner("learnqa.example")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetUserDetailsAuthAsSameUser() {
        Map<String,String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password","1234");
        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);      
       
        String header = this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");
        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/2", header, cookie);  
        String[] expectedFields = {"username", "firstName", "lastName","email"};
        Assertions.assertJsonHasFields(responseUserData, expectedFields);
              
    }

    @Test
    @Description("This test successfully logs in an authorised user and does not get another user details")
    @DisplayName("Test negative get data another authorise user")
    @Owner("Golosova I.A.")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAnotherUserDetailsAuthAsSameUser() {
        //Generate user
        Map<String,String> userData = DataGeneration.getRegistrationData();
        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJson("https://playground.learnqa.ru/api/user/", userData);
        
        String userId = responseCreateAuth.getString("id");
        //System.out.println(userId);

        //Login 
        Map<String,String> authData = new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));
        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);          
        
        String header = this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");
        //Get another user
        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/2", header, cookie);
        
        //responseUserData.prettyPrint();
        String[] expectedFields = {"firstName", "lastName","email"};
        Assertions.assertJsonHasNotFields(responseUserData, expectedFields);
        Assertions.assertJsonHasField(responseUserData, "username");
    }    
}
