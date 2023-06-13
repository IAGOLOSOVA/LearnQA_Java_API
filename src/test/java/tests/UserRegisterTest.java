package tests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.BaseTestCase;
import lib.Assertions;
import lib.DataGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

public class UserRegisterTest extends BaseTestCase{
    @Test
    public void testCresteUserWithExistingEmail() {
        String email = "vinkotov@example.com";

        Map<String,String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreatAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.asserResponseCodeEquals(responseCreatAuth,400);
        Assertions.asserResponseTextEquals(responseCreatAuth, "Users with email '"+ email+ "' already exists");
    }

    @Test
    public void testCresteUserSuccessfully() {
        String email = DataGeneration.getRandomEmail();

        Map<String,String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreatAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.asserResponseCodeEquals(responseCreatAuth,200);
        Assertions.assertJsonHasKey(responseCreatAuth, "id");
    }

    @Test
    public void testCresteUserWithBadEmail() {
        String email = "vinkotovexample.com";

        Map<String,String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreatAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();
//        System.out.println(responseCreatAuth.asString());
//        System.out.println(responseCreatAuth.statusCode());   
        Assertions.asserResponseCodeEquals(responseCreatAuth,400);
        Assertions.asserResponseTextEquals(responseCreatAuth, "Invalid email format");  
    }    

    @ParameterizedTest
    @CsvSource ({'"email","})
    public void testCresteUserWithBadEmail() {
        String email = "vinkotovexample.com";

        Map<String,String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreatAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();
//        System.out.println(responseCreatAuth.asString());
//        System.out.println(responseCreatAuth.statusCode());   
        Assertions.asserResponseCodeEquals(responseCreatAuth,400);
        Assertions.asserResponseTextEquals(responseCreatAuth, "Invalid email format");  
    }    
    
    
}
