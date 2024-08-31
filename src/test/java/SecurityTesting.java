import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;


public class SecurityTesting {
    private static Response response;

    @Test
    public void authorizedLoginValidStatusCode() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.LIVE_ENDPOINT + Consts.APP_KEY +  Consts.TOKEN );
        response.then().statusCode(200);
    }
    @Test
    public void authorizedLoginInvalidStatusCode() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.LIVE_ENDPOINT + Consts.APP_KEY +  Consts.TOKEN );
        response.then().statusCode(400);
    }
    @Test
    public void checkErrorMessageForAuthorizedLogin() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.LIVE_ENDPOINT + Consts.APP_KEY + Consts.TOKEN );
        response.then().body("message", nullValue());
    }
    @Test
    public void unauthorizedLogin() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.LIVE_ENDPOINT + Consts.APP_KEY + Consts.INVALID_TOKEN );
        response.then().statusCode(401);
    }
    @Test
    public void unauthorizedLoginInvalidStatusCode() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.LIVE_ENDPOINT + Consts.APP_KEY + Consts.INVALID_TOKEN );
        response.then().statusCode(200);
    }
    @Test
    public void checkErrorMessageForUnauthorizedLogin() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.LIVE_ENDPOINT + Consts.APP_KEY + Consts.INVALID_TOKEN );
        response.then().body("message", notNullValue());
        response.then().body("message", equalTo("Invalid authentication credentials"));
    }


}