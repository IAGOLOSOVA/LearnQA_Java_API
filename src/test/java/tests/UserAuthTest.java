package tests;


import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.Assertions;
import lib.BaseTestCase;
import lib.ApiCoreRequests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.restassured.http.Headers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Links;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.junit.jupiter.api.DisplayName;

@Epic("Authorisation cases")
@Feature("Autorization")
@Link(name = "API methods", url = "https://playground.learnqa.ru/api/map")                
public class UserAuthTest extends BaseTestCase {

    String cookie;
    String header;
    int userIdOnAuth;

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @BeforeEach
    public void loginUser(){
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");
        //Создаем запрос с авторизованным пользователем
        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        this.cookie = this.getCookie(responseGetAuth,"auth_sid");
        this.header = this.getHeader(responseGetAuth,"x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseGetAuth, "user_id");
    }

    @Test //Позитивный авторизованный тест
    @Description("This test successfully authorize user by email and password")
    @DisplayName("Test positive auth user")
    @Owner("learnqa.example")
    @Severity(SeverityLevel.BLOCKER)
    public void testAuthUser(){

//Отправляем гет запрос на метод, который проверяет авторизованного пользователя
        Response responseCheckAuth = apiCoreRequests
                .makeGetRequest(
                    "https://playground.learnqa.ru/api/user/auth",
                    this.header,
                    this.cookie
                );

        Assertions.asserJsonByName(responseCheckAuth, "user_id", this.userIdOnAuth);
    }

    @Description("This test checks authrisation status w/o sending auth cookie or token")
    @DisplayName("Test negative auth user")
    @Owner("learnqa.example")
    @Severity(SeverityLevel.BLOCKER)
    @ParameterizedTest //2 негативных Тест с параметрами, сервер проверяет авторизован пользователь или нет
    @ValueSource (strings = {"cookie", "headers"})
    public void testNegativeAuthUser(String condition){

//        RequestSpecification spec = RestAssured.given(); //функция которая отправляет запрос по частям
//        spec.baseUri("https://playground.learnqa.ru/api/user/auth"); //эта часть отправляется всегда
//        if (condition.equals("cookie")) {
//            spec.cookie("auth_sid", this.cookie);
//        } else if (condition.equals("headers")) {
//            spec.header("x-csrf-token", this.header);
//        } else {
//            throw new IllegalArgumentException("Condition value is known: "+ condition);
//        }
//        Response responseForCheck = spec.get().andReturn(); //сам запрос
//        Assertions.asserJsonByName(responseForCheck, "user_id", 0);
     
        if (condition.equals("cookie")) {
            Response responseForCheck = apiCoreRequests.makeGetRequestwithCookie(
                     "https://playground.learnqa.ru/api/user/auth",
                     this.cookie              
            );
            Assertions.asserJsonByName(responseForCheck, "user_id", 0);
        } else if (condition.equals("headers")) {
            Response responseForCheck = apiCoreRequests.makeGetRequestWithToken(
                     "https://playground.learnqa.ru/api/user/auth",
                     this.header    
            );    
            Assertions.asserJsonByName(responseForCheck, "user_id", 0);     
        } else {
            throw new IllegalArgumentException("Condition value is known: "+ condition);
        }

    }
}
