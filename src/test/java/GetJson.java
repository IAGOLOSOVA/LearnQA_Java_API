import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class GetJson {
    @Test
    public void testGetJson (){
//        Map<String,String> params1 = new HashMap<>();
//        params1.get("message");
        JsonPath response = RestAssured
//                .given()
//                .queryParams(params1)
                .get(" https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        String message = response.get("messages[1].message");
        System.out.println(message);

    }
}
