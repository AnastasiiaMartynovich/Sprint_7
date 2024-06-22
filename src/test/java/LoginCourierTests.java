import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import Scooter.rest.CourierRest;
import Scooter.object.CourierLogin;
import Scooter.data.CourierData;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoginCourierTests {

    private CourierRest courierRest;
    private int courierId;

    @Before
    public void setUp() {
        courierRest = new CourierRest();
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    @Description("Запрос возвращает id и статус код 200")
    public void courierCanLogin() {
        //логинимся под зарегистрированным курьером
        ValidatableResponse loginResponse = courierRest
                .loginCourier(CourierLogin.from(CourierData.getRegisteredCourier()));

        //записываем id курьера
        courierId = loginResponse.extract().path("id");
        //проверяем, что id что не равен нулю
        assertNotNull("Id is null", courierId);
    }

    @Test
    @DisplayName("Заполнены не все обязательные поля при авторизации")
    @Description("Запрос возвращает ошибку 400")
    public void loginCourierEmptyPassword() {
        //логинимся без пароля
        ValidatableResponse loginResponse = courierRest
                .loginCourier(CourierLogin.from(CourierData.getDefaultWithoutPassword()));

        int loginStatusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        //проверка ответа на запрос
        assertEquals("Некорректный статус код", SC_BAD_REQUEST, loginStatusCode);
        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    @DisplayName("Авторизация под несуществующим пользователем")
    @Description("Запрос возвращает ошибку 404")
    public void wrongLoginCourier() {
        //логинимся под несуществующим пользователем
        ValidatableResponse loginResponse = courierRest
                .loginCourier(CourierLogin.from(CourierData.getWrong()));
        int loginStatusCode = loginResponse.extract().statusCode();

        String message = loginResponse.extract().path("message");
        //проверка ответа на запрос
        assertEquals("Некорректный статус код", SC_NOT_FOUND, loginStatusCode);
        assertEquals("Учетная запись не найдена", message);
    }
}
