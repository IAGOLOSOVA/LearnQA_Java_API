package tests_old;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
public class GetTest {
    @Test
    public void testGet (){
        Response response = RestAssured
                .get(" https://playground.learnqa.ru/api/hello")
                .andReturn();
        response.prettyPrint();

    }
}
