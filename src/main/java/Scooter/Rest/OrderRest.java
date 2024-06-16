package Scooter.Rest;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import Scooter.Object.Order;

import static io.restassured.RestAssured.given;
public class OrderRest extends RestUrl{
    private static final String ORDER_PATH = "api/v1/orders";

    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse allOrders() {
        return given()
                .spec(getBaseSpec())
                .get(ORDER_PATH)
                .then();
    }
}
