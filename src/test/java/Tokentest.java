import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import java.lang.Thread;

import org.junit.jupiter.api.Test;
public class Tokentest {
    @Test
// Не знаю как сделать без добавления throws InterruptedException {
    public void testToken () throws InterruptedException {
        int timesec = 0;
        JsonPath response = RestAssured
                .get(" https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        response.prettyPrint();

        String token1 = response.get("token");
//        System.out.println(token1);
        timesec = response.get("seconds");
//        System.out.println(timesec);

        JsonPath response1 = RestAssured
                .given()
                .queryParams("token",token1)
                .get (" https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String status = response1.get("status");
        System.out.println(status);

        long delay = (timesec * 1000);
        Thread.sleep(delay);

        JsonPath response2 = RestAssured
                .given()
                .queryParams("token",token1)
                .get (" https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String status2 = response2.get("status");
        String result = response2.get("result");

        System.out.println(status2);
        System.out.println(result);

    }
}