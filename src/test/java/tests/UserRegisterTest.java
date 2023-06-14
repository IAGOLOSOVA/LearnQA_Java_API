package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.BaseTestCase;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.DataGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;

@Epic("Registration test")
@Feature("Registration")

public class UserRegisterTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("This test not successfully to registrate authorize user with existing email")
    @DisplayName("Test negative create user")
    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreatAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        Assertions.asserResponseCodeEquals(responseCreatAuth, 400);
        Assertions.asserResponseTextEquals(responseCreatAuth, "Users with email '" + email + "' already exists");
    }

    @Test
    @Description("This test successfully to registrate user with new email")
    @DisplayName("Test positive create user")
    public void testCresteUserSuccessfully() {
        String email = DataGeneration.getRandomEmail();

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreatAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        Assertions.asserResponseCodeEquals(responseCreatAuth, 200);
        Assertions.assertJsonHasField(responseCreatAuth, "id");
    }

    @Test
    @Description("This test not successfully to registrate user with email without @")
    @DisplayName("Test negative create user with invalid email")
    public void testCresteUserWithInvalidEmail() {
        String email = "vinkotovexample.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreatAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        Assertions.asserResponseCodeEquals(responseCreatAuth, 400);
        Assertions.asserResponseTextEquals(responseCreatAuth, "Invalid email format");
    }

    @ParameterizedTest
    @Description("This test not successfully to registrate user without any parametr")
    @DisplayName("Test negative create user with invalid email")
    @ValueSource(strings = { "email", "password", "username", "firstName", "lastName" })
    public void testCresteUserWithoutAnyParam(String param) {
        String email = DataGeneration.getRandomEmail();

        Map<String, String> userData = new HashMap<>();
        if (!param.equals("email")) {
            userData.put("email", email);
        }
        if (!param.equals("password")) {
            userData.put("password", "123");
        }
        if (!param.equals("username")) {
            userData.put("username", "learnqa");
        }
        if (!param.equals("firstName")) {
            userData.put("firstName", "learnqa");
        }
        if (!param.equals("lastName")) {
            userData.put("lastName", "learnqa");
        }

        // userData.put(param,"");
        // System.out.print(userData);
        Response responseCreatAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        // responseCreatAuth.prettyPrint();
        Assertions.asserResponseCodeEquals(responseCreatAuth, 400);
        Assertions.asserResponseTextEquals(responseCreatAuth, "The following required params are missed: " + param);
    }

    @Test
    @Description("This test not successfully to registrate user with short name")
    @DisplayName("Test negative create user with short name")
    public void testCresteUserWithShortName() {
        //String name = " ";
        String name = DataGeneration.getRandomName("1");
        System.out.println(name);
        Map<String, String> userData = new HashMap<>();
        userData.put("email", "vinkotov@example.com");
        userData.put("password", "123");
        userData.put("username", name);
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreatAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        //responseCreatAuth.prettyPrint();        
        Assertions.asserResponseCodeEquals(responseCreatAuth, 400);
        Assertions.asserResponseTextEquals(responseCreatAuth, "The value of 'username' field is too short");
    }
    
    @Test
    @Description("This test not successfully to registrate user with name longer 250")
    @DisplayName("Test negative create user with name longer 250")
    public void testCresteUserWithLongName() {
        
        String name = DataGeneration.getRandomName("251");
        System.out.println(name);
        Map<String, String> userData = new HashMap<>();
        userData.put("email", "vinkotov@example.com");
        userData.put("password", "123");
        userData.put("username", name);
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreatAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        //responseCreatAuth.prettyPrint();        
        Assertions.asserResponseCodeEquals(responseCreatAuth, 400);
        Assertions.asserResponseTextEquals(responseCreatAuth, "The value of 'username' field is too long");
    }
}
