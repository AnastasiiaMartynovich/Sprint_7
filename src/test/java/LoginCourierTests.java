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
    @DisplayName(" урьер может авторизоватьс€")
    @Description("«апрос возвращает id и статус код 200")
    public void courierCanLogin() {
        //логинимс€ под зарегистрированным курьером
        ValidatableResponse loginResponse = courierRest
                .loginCourier(CourierLogin.from(CourierData.getRegisteredCourier()));

        //записываем id курьера
        courierId = loginResponse.extract().path("id");
        //провер€ем, что id что не равен нулю
        assertNotNull("Id is null", courierId);
    }

    @Test
    @DisplayName("«аполнены не все об€зательные пол€ при авторизации")
    @Description("«апрос возвращает ошибку 400")
    public void loginCourierEmptyPassword() {
        //логинимс€ без парол€
        ValidatableResponse loginResponse = courierRest
                .loginCourier(CourierLogin.from(CourierData.getDefaultWithoutPassword()));

        int loginStatusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        //проверка ответа на запрос
        assertEquals("Ќекорректный статус код", SC_BAD_REQUEST, loginStatusCode);
        assertEquals("Ќедостаточно данных дл€ входа", message);
    }

    @Test
    @DisplayName("јвторизаци€ под несуществующим пользователем")
    @Description("«апрос возвращает ошибку 404")
    public void wrongLoginCourier() {
        //логинимс€ под несуществующим пользователем
        ValidatableResponse loginResponse = courierRest
                .loginCourier(CourierLogin.from(CourierData.getWrong()));
        int loginStatusCode = loginResponse.extract().statusCode();

        String message = loginResponse.extract().path("message");
        //проверка ответа на запрос
        assertEquals("Ќекорректный статус код", SC_NOT_FOUND, loginStatusCode);
        assertEquals("”четна€ запись не найдена", message);
    }
}
