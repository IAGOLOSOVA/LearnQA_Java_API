package tests_old;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class testRedirects1 {

    @Test
    public void testRedirects1() {
        int status = 0;
        String locationHeader = " https://playground.learnqa.ru/api/long_redirect";
        int i = 0;

        do {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(locationHeader)
                    .andReturn();
//            response.prettyPrint();
            status = response.getStatusCode();
            i++;

            System.out.println(status);
            locationHeader = response.getHeader("Location");
            System.out.println(locationHeader);
        } while (status != 200);
        System.out.println(i-1);
    }
}