package lib;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Assertions {
    public static void asserJsonByName(Response Response, String name, int expectedValue) {
        Response.then().assertThat().body("$",hasKey(name));

        int value = Response.jsonPath().getInt(name);
        assertEquals(expectedValue,value,"JSON value is not equal to expected value");
    }

    public static void asserResponseTextEquals(Response Response, String expectedAnswer) {
        assertEquals(
                expectedAnswer,
                Response.asString(),
                "Response text is not as expected"
        );
    }
    public static void asserResponseCodeEquals(Response Response, int expectedStatusCode) {
        assertEquals(
                expectedStatusCode,
                Response.statusCode(),
                "Response status code is not as expected"
        );
    }

    public static void assertJsonHasField(Response Response, String expectedFildName) {
        Response.then().assertThat().body("$",hasKey(expectedFildName));

    }
    public static void assertJsonHasNotField(Response Response, String unexpectedFildName) {
        Response.then().assertThat().body("$",not(hasKey(unexpectedFildName)));
    }    
}
