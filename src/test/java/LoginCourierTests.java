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
    @DisplayName("������ ����� ��������������")
    @Description("������ ���������� id � ������ ��� 200")
    public void courierCanLogin() {
        //��������� ��� ������������������ ��������
        ValidatableResponse loginResponse = courierRest
                .loginCourier(CourierLogin.from(CourierData.getRegisteredCourier()));

        //���������� id �������
        courierId = loginResponse.extract().path("id");
        //���������, ��� id ��� �� ����� ����
        assertNotNull("Id is null", courierId);
    }

    @Test
    @DisplayName("��������� �� ��� ������������ ���� ��� �����������")
    @Description("������ ���������� ������ 400")
    public void loginCourierEmptyPassword() {
        //��������� ��� ������
        ValidatableResponse loginResponse = courierRest
                .loginCourier(CourierLogin.from(CourierData.getDefaultWithoutPassword()));

        int loginStatusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        //�������� ������ �� ������
        assertEquals("������������ ������ ���", SC_BAD_REQUEST, loginStatusCode);
        assertEquals("������������ ������ ��� �����", message);
    }

    @Test
    @DisplayName("����������� ��� �������������� �������������")
    @Description("������ ���������� ������ 404")
    public void wrongLoginCourier() {
        //��������� ��� �������������� �������������
        ValidatableResponse loginResponse = courierRest
                .loginCourier(CourierLogin.from(CourierData.getWrong()));
        int loginStatusCode = loginResponse.extract().statusCode();

        String message = loginResponse.extract().path("message");
        //�������� ������ �� ������
        assertEquals("������������ ������ ���", SC_NOT_FOUND, loginStatusCode);
        assertEquals("������� ������ �� �������", message);
    }
}
