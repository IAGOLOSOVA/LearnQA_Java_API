import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class PwTest {
    @Test
    public void testPW (){
        Map<String,String> data = new HashMap<>();
        data.put("login","super_admin");
        data.put("password","111111");

        Response response = RestAssured
                .given()
                .body(data)
                .when()
                .post("  https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                .andReturn();
        response.prettyPrint();
//        Map<String,String> responseCookies = response.getCookies();
//        System.out.println(responseCookies);
        String responseCookie = response.getCookie("auth_cookie");
        Map<String,String> cookies = new HashMap<>();
        if(responseCookie != null){
            cookies.put("auth_cookie",responseCookie);
        }
        Response response1 = RestAssured
                .given()
                .body(data)
                .cookies(cookies)
                .when()
                .post("  https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                .andReturn();
        response1.prettyPrint();
    }

}
