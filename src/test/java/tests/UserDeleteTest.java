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

@Epic("User delete test")
@Feature("Delete User")
@Owner("Golosova I.A.")
@Link(name = "API methods", url = "https://playground.learnqa.ru/api/map")   
public class UserDeleteTest extends BaseTestCase{
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
     
    @Test
    @Description("This test unsuccessfully delete of authorized user with id2")
    @DisplayName("Test negative delete auth user with id2") 
    @Severity(SeverityLevel.BLOCKER)
    public void testDeleteAdminUserTest() {
        
        //Login admin user with id=2
        Map<String,String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password","1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        String header = this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        //Delete Admin User with id=2
        Response responseDeleteUser = apiCoreRequests
                .makeDeleteUser(("https://playground.learnqa.ru/api/user/2"), header, cookie);   
       
        Assertions.asserResponseCodeEquals(responseDeleteUser, 400);
        Assertions.asserResponseTextEquals(responseDeleteUser, "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");
    }

    @Test
    @Description("This test successfully registers a new user and delete of this authorized user")
    @DisplayName("Test positive register and delete new user") 
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteAuthUserTest() {
        //Generate new user
        Map<String,String> userData = DataGeneration.getRegistrationData();
        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJson("https://playground.learnqa.ru/api/user/",userData);
        
        String userId = responseCreateAuth.getString("id");
        System.out.println(userId);
        //Login new user
        Map<String,String> authData = new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        String header = this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        //Delete User
               
        Response responseDeleteUser = apiCoreRequests
                .makeDeleteUser(("https://playground.learnqa.ru/api/user/"+userId), header, cookie);
                
        Assertions.asserResponseCodeEquals(responseDeleteUser, 200);

        //Get deleted user
        Response responseUserData =apiCoreRequests
                .makeGetRequest(("https://playground.learnqa.ru/api/user/" + userId), header, cookie);

        Assertions.asserResponseTextEquals(responseUserData, "User not found");
        Assertions.assertJsonHasNotField(responseUserData, "id");
    }
    
    @Test
    @Description("This test successfully registers a new user and unsuccessfully delete of another user")
    @DisplayName("Test negative delete another user") 
    @Severity(SeverityLevel.BLOCKER)
    public void testDeleteAnotherUserTest() {
        //Generate new user
        Map<String,String> userData = DataGeneration.getRegistrationData();
        JsonPath responseCreateAuth = apiCoreRequests
                .makePostJson("https://playground.learnqa.ru/api/user/",userData);
        
        String userId = responseCreateAuth.getString("id");
        System.out.println(userId);
        //Login new user
        Map<String,String> authData = new HashMap<>();
        authData.put("email",userData.get("email"));
        authData.put("password",userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        String header = this.getHeader(responseGetAuth,"x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");
        
        //Generate new user with username Irina
        String username = "Irina";
        Map<String, String> anotheruserData = new HashMap<>();
        anotheruserData.put("username", username);
        anotheruserData = DataGeneration.getRegistrationData(anotheruserData);

        JsonPath responseCreate2Auth = apiCoreRequests
                .makePostJson("https://playground.learnqa.ru/api/user/",anotheruserData);
        
        String anotheruserId = responseCreate2Auth.getString("id");
                
        //Delete User with username Irina
        Response responseDeleteUser = apiCoreRequests
                .makeDeleteUser(("https://playground.learnqa.ru/api/user/"+anotheruserId), header, cookie);
                
        Assertions.asserResponseCodeEquals(responseDeleteUser, 200);
        //System.out.println(responseDeleteUser);

        //Get deleted Irina user data
        Response responseUserData =apiCoreRequests
                .makeGetRequest(("https://playground.learnqa.ru/api/user/" + anotheruserId), header, cookie);
        
        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.asserJsonByName(responseUserData, "username", username);
        String[] expectedFields = {"firstName", "lastName","email"};
        Assertions.assertJsonHasNotFields(responseUserData, expectedFields);        
    }
    
}
