package tests_old;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import io.restassured.http.Headers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ParameterTest {
    @ParameterizedTest
    @ValueSource (strings = {"Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
                               "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1",
                               "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)",
                               "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0",
                               "Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1"})
    public void parTest(String useragent){
        Map<Headers,String> pars = new HashMap<>();
        if (useragent.length() > 0){
            pars.put(Headers.headers.("user_agent"), useragent);
        }
        JsonPath response1 = RestAssured
                .given()
                .header(pars)
                .when()
                .get("    https://playground.learnqa.ru/ajax/api/user_agent_check")
                .andReturn();

        String answer = response1.get("");
        System.out.println(answer);

//    Expected values:

//            'platform': 'Mobile', 'browser': 'No', 'device': 'Android'
//    @ParameterizedTest
//2 негативных Тест с параметрами, сервер проверяет авторизован пользователь или нет
//    @ValueSource(strings = {"platform", "browser","device"})
//    public void testNegativeAuthUser(String condition){

//    Response responseForCheck = spec.get().andReturn(); //сам запрос
//    Assertions.asserJsonByName(responseForCheck, "user_id", 0);
    }
}
