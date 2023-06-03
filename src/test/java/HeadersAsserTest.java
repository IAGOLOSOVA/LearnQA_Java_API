import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.Map;

public class HeadersAsserTest {
    @Test
    public void HeadersGet (){

        Response response = RestAssured
                .get(" https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        Headers headers= response.getHeaders();
//        System.out.println(headers);
        
        assertEquals(200, response.statusCode(), "Unexpected status code");
        assertTrue(headers.hasHeaderWithName("x-secret-homework-header"),"Response doesn't have secret header");
    }
}
