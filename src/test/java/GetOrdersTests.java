import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import Scooter.rest.OrderRest;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class GetOrdersTests {

    private OrderRest orderRest;

    @Before
    public void setUp() {
        orderRest  = new OrderRest();
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверяем, что в теле ответа возвращается список заказов")
    public void getListOrdersTest() {
        ValidatableResponse response = orderRest.allOrders();

        ArrayList<String> orderBody = response.extract().path("orders");
        boolean isNotEmpty = orderBody!=null && !orderBody.isEmpty();

        assertTrue("Orders is empty", isNotEmpty);
    }
}