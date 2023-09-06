import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class scopeB {
    private static String authToken;
    @Test
    public void firstCase()
    // Post Request and check status code + get token to use it in another case
    {
        JSONObject request = new JSONObject();
        request.put("email","merchant@foodics.com");
        request.put("password","123456");
        request.put("token", "Lyz22cfYKMetFhKQybx5HAmVimF1i0xO");

        Response response = RestAssured.given().
                contentType(ContentType.JSON).
                body(request.toJSONString()).
                post("https://pay2.foodics.dev/cp_internal/login");

        authToken = response.jsonPath().get("token");

        response.then().statusCode(200);
        System.out.println("The first test case is done and the token is created");
    }
    @Test
    public void secondCase ()
    // Get Request using valid token
    {
        Response response = RestAssured.given()
                .header("Authorization","Bearer"+authToken)
                .get("https://pay2.foodics.dev/cp_internal/whoami");

        response.then().statusCode(200);
        System.out.println("The second test case is done using the token from the first test case");
    }

    @Test
    public void thirdCase()
    // Post Request using invalid Body (invalid Password)
    {
        JSONObject request = new JSONObject();
        request.put("email","merchant@foodics.com");
        request.put("password","INVALID PASSWORD");
        request.put("token","Lyz22cfYKMetFhKQybx5HAmVimF1i0xO");

        Response response = RestAssured.given().
                contentType(ContentType.JSON).
                body(request.toJSONString())
                .post("https://pay2.foodics.dev/cp_internal/login");

        response.then().statusCode(500);
        System.out.println("The third test case is done But an invalid Password with status code 500");
    }
}


