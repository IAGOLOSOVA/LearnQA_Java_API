package tests_old;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class PwTest {
    /**
     * 
     */
    @Test
    public void testPW() {
        String[] pwtop25 = { "password", "123456", "123456789", "12345678", "12345", "qwerty", "abc123", "football",
                "1234567", "monkey",
                "111111", "1234567", "letmein", "1234", "1234567890", "dragon", "baseball", "sunshine", "iloveyou",
                "trustno1",
                "princess", "adobe123", "123123", "welcome", "login", "admin", "trustno1", "monkey", "qwerty123",
                "solo",
                "1q2w3e4r", "master", "666666", "photoshop", "1qaz2wsx", "qwertyuiop", "ashley", "mustang", "121212",
                "123qwe",
                "starwars", "654321", "bailey", "access", "flower", "555555", "passw0rd", "shadow", "lovely", "7777777",
                "michael", "!@#$%^&*", "jesus", "password1", "superman", "hello", "charlie", "888888", "696969",
                "hottie",
                "freedom", "aa123456", "qazwsx", "ninja", "azerty", "solo", "loveme", "whatever", "donald", "batman",
                "zaq1zaq1", "Football", "000000" };
        int i = 0;
        int stat1 = 0;
        do {
            Map<String, String> data = new HashMap<>();
            data.put("login", "super_admin");
            data.put("password", pwtop25[i]);
            String pwout = pwtop25[i];
            Response response = RestAssured
                    .given()
                    .body(data)
                    .when()
                    .post("  https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();
        //    response.prettyPrint();
            // Map<String,String> responseCookies = response.getCookies();
            // System.out.println(responseCookies);
            String responseCookie = response.getCookie("auth_cookie");
            Map<String, String> cookies = new HashMap<>();
            if (responseCookie != null) {
                cookies.put("auth_cookie", responseCookie);
            }
            Response response1 = RestAssured
                    .given()
                    .body(data)
                    .cookies(cookies)
                    .when()
                    .post("  https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();
            // response1.prettyPrint();
            String stat = response1.asString();
//            System.out.print(stat);
            String not = "You are NOT authorized";
//            answ = System.out.println(stat.equals(not));
            if (stat.equals(not)) {
                stat1 = 0;
//                System.out.println(stat);
//                System.out.println(pwout);
            } else {
                stat1 = 1;
                System.out.println(stat);
                System.out.println("password: "+ pwout);
            }
            i++;
        } while (stat1==0);

    }

}
