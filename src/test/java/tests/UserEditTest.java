package tests;

import lib.BaseTestCase;
import lib.DataGeneration;
import lib.Assertions;
import lib.ApiCoreRequests;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
//import io.restassured.internal.common.assertion.Assertion;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserEditTest extends BaseTestCase{
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    /**
     * 
     */
    @Test
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
    
}
