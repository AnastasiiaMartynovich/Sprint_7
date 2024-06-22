package Scooter.rest;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import Scooter.object.Courier;
import Scooter.object.CourierLogin;

import static io.restassured.RestAssured.given;

public class CourierRest extends RestUrl {

    private static final String COURIER_PATH = "api/v1/courier";
    private static final String COURIER_LOGIN = "api/v1/courier/login";
    private static final String COURIER_DELETE = "api/v1/courier/";

    @Step("Создание нового курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Логин курьера в системе")
    public ValidatableResponse loginCourier(CourierLogin login ) {
        return given()
                .spec(getBaseSpec())
                .body(login)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int id) {
        return given()
                .spec(getBaseSpec())
                .pathParam("id", id)
                .when()
                .delete(COURIER_DELETE + "{id}")
                .then();
    }
}