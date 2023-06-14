package tests;

import lib.BaseTestCase;
import lib.DataGeneration;
import lib.Assertions;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
//import io.restassured.internal.common.assertion.Assertion;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserEditTest extends BaseTestCase{
    /**
     * 
     */
    @Test
    public void testEditJustCreatedTest() {
        //Generate user
        Map<String,String> userData = DataGeneration.getRegistrationData();
        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        
        String userId = responseCreateAuth.getString("id");
        System.out.println(userId);
        //Login 
        Map<String,String> authData = new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();
        String header = this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        //Edit
        String newName = "Changed Name";
        Map<String,String> editData = new HashMap<>();
        editData.put("firstName", newName);
        
        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token",header)
                .cookie("auth_sid", cookie)
                .body(editData)
                .get("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        //Get
        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token",this.getHeader(responseGetAuth,"x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .get("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        Assertions.asserJsonByName(responseUserData, "username", newName);
    }
    
}
