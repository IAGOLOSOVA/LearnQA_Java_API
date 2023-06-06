package tests_old;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.Map;

public class CookieAsserTest {
    @Test
    public void CookieGet (){

        Response response = RestAssured
                .get(" https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        Map<String,String> responseCookies = response.getCookies();
        System.out.println(responseCookies);
        
        assertEquals(200, response.statusCode(), "Unexpected status code");
        assertTrue(responseCookies.containsKey("HomeWork"),"Response doesn't have 'HomeWork' cookie");
    }
}
