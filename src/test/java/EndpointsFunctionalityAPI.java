import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class EndpointsFunctionalityAPI {
    private static Response response;

    @Test
    public void checkAllLiveCurrencyRequest() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.LIVE_ENDPOINT + Consts.APP_KEY + Consts.TOKEN);
        response.then().body("success", notNullValue());
        response.then().body("success", equalTo(true));
    }

    @Test
    public void checkValidCurrencyTest_EUR() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.LIVE_ENDPOINT + Consts.APP_KEY + Consts.TOKEN + "&source=USD&currencies=CAD");
        response.then().statusCode(200);
        response.then().body("success", equalTo(true));
    }

    @Test
    public void checkValidCurrencyTest_CAD() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.LIVE_ENDPOINT + Consts.APP_KEY + Consts.TOKEN + "&source=USD&currencies=EUR");
        response.then().statusCode(200);
        response.then().body("success", equalTo(true));
    }

    @Test
    public void checkValidCurrencyTest_CDF() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.LIVE_ENDPOINT + Consts.APP_KEY + Consts.TOKEN + "&source=USD&currencies=CDF");
        response.then().statusCode(200);
        response.then().body("success", equalTo(true));
    }

    @Test
    public void checkInvalidCurrencyTest_GSG() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.LIVE_ENDPOINT + Consts.APP_KEY + Consts.TOKEN + "&source=USD&currencies=GSG");
        response.then().body("error.code", equalTo(202));
        response.then().body("error.info", containsString("You have provided one or more invalid Currency Codes."));
    }

    @Test
    public void checkInvalidCurrencyTest_XPX() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.LIVE_ENDPOINT + Consts.APP_KEY + Consts.TOKEN + "&source=USD&currencies=XPX");
        response.then().body("error.code", equalTo(202));
        response.then().body("error.info", containsString("You have provided one or more invalid Currency Codes."));
    }

    @Test
    public void checkSeveralCurrenciesTest() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.LIVE_ENDPOINT + Consts.APP_KEY + Consts.TOKEN + "&source=USD&currencies=BGN%2XAG%2ZWL");
        response.then().statusCode(200);
    }

    @Test
    public void checkSeveralInvalidCurrenciesTest() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.LIVE_ENDPOINT + Consts.APP_KEY + Consts.TOKEN + "&source=USD&currencies=KJD%2FDS%2KFJ");
        response.then().body("error.info", containsString("You have provided one"));
    }
}

