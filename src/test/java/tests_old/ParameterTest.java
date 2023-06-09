package tests_old;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParameterTest {
    @ParameterizedTest
    @CsvSource ({"'Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30', 'Mobile', 'No', 'Android'",
                "'Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1', Mobile, Chrome, iOS",
                "'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', Googlebot, Unknown, Unknown",
                "'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0', Web, Chrome, No",
                "'Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1', Mobile, No, iPhone"})
        public void parTest(String useragent, String platform, String browser, String device){
        Map<String,String> headers = new HashMap<>();
        if (useragent.length() > 0){
            headers.put("user-agent", useragent);

        }

//        String platform = new String("Mobile");
        JsonPath response1 = RestAssured
                .given()
                .headers(headers)
                .when()
                .get("    https://playground.learnqa.ru/ajax/api/user_agent_check")
                .jsonPath();
//        response1.prettyPrint();
        String answ1 = response1.getString("platform");
        String answ2 = response1.getString("browser");
        String answ3 = response1.getString("device");
        String expectPlatform = (platform.length()>0) ? platform : "NoN";
        String expectBrowser = (browser.length()>0) ? browser : "NoN";
        String expectDevice = (device.length()>0) ? device : "NoN";
        assertTrue(answ1.length() > 0, "Response doesn't have" + platform);
        assertEquals(expectPlatform,answ1,"The platform is not expected");

        assertTrue(answ2.length() > 0, "Response doesn't have" + browser);
        assertEquals(expectBrowser,answ2,"The browser is not expected");
        assertTrue(answ3.length() > 0, "Response doesn't have" + device);
        assertEquals(expectDevice,answ3,"The "+"device"+" is not expected");

    }
}

