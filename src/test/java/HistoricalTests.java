import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class HistoricalTests {
    private static Response response;

    @Test
    public void checkPastDates_2018_Test() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.HISTORICAL_ENDPOINT + "date=2018-01-01" + "&" + Consts.APP_KEY + Consts.TOKEN);
        response.then().statusCode(200);
        response.then().body("date", equalTo("2018-01-01"));
    }

    @Test
    public void checkPastDates_2023_Test() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.HISTORICAL_ENDPOINT + "date=2023-12-03" + "&" + Consts.APP_KEY + Consts.TOKEN);
        response.then().statusCode(200);
        response.then().body("date", equalTo("2023-12-03"));
    }

    @Test
    public void checkFutureDates_2028_Test() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.HISTORICAL_ENDPOINT + "date=2028-10-07" + "&" + Consts.APP_KEY + Consts.TOKEN);
        response.then().body("success", equalTo(false));
        response.then().body("error.info", containsString("You have entered an invalid date"));
        response.then().body("error.code", equalTo(302));
    }

    @Test
    public void checkHistoricalWithoutDates() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.HISTORICAL_ENDPOINT + "" + "&" + Consts.APP_KEY + Consts.TOKEN);
        response.then().body("success", equalTo(false));
        response.then().body("error.info", containsString("You have not specified a date"));
        response.then().body("error.code", equalTo(301));
    }

    @Test
    public void checkHistoricalOfSeveralCurrenciesTest() {
        response = given().get(Consts.URL + Consts.CURRENCY_ENDPOINT + Consts.HISTORICAL_ENDPOINT + "date=2024-08-31" + Consts.APP_KEY + Consts.TOKEN + "&source=USD&currencies=EUR%2CGBP%2CJPY");
        response.then().body("success", equalTo(true));
        response.then().body("code", notNullValue());
    }
}
