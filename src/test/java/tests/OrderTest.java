package tests;

import data.*;
import helpers.OrdersHelper;
import helpers.UserHelper;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OrderTest {

    // TO-DO: read docs about unlogin

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Успешное создание заказа без авторизации")
    public void successCreateOrderWithoutLogin() {
        ArrayList<String> data = new ArrayList<String>();
        data.add("61c0c5a71d1f82001bdaaa6d");
        data.add("61c0c5a71d1f82001bdaaa6f");

        OrdersHelper order = new OrdersHelper(data);
        order.makeCreateOrderRequestWithoutLogin();
        int code = order.getOrderRequestStatusCode();

        try {
            CreateOrderResponseSuccess respData = order.getOrder().as(CreateOrderResponseSuccess.class);
        } catch (Exception exception) {
            fail("Пришедший ответ не соответствует документации API");
        }
        assertEquals(200, code);
    }

    @Test
    @DisplayName("Успешное создание заказа с авторизацией")
    public void successCreateOrderWithLogin() {

        ArrayList<String> data = new ArrayList<String>();
        data.add("61c0c5a71d1f82001bdaaa6d");
        data.add("61c0c5a71d1f82001bdaaa6f");

        OrdersHelper order = new OrdersHelper(data);

        UserHelper user = new UserHelper("as007ershov@gmail.com", "as007ershov@gmail.com", "as007ershov@gmail.com");
        user.makeLoginUserRequest();

        LoginUserResponse resp = user.getLoginUser().as(LoginUserResponse.class);
        order.makeCreateOrderRequestWithLogin(resp.getAccessToken());
        int code = order.getOrderRequestStatusCode();

        try {
            CreateOrderResponseSuccess respData = order.getOrder().as(CreateOrderResponseSuccess.class);
        } catch (Exception exception) {
            fail("Пришедший ответ не соответствует документации API");
        }
        assertEquals(200, code);
    }

    @Test
    @DisplayName("Попытка создания заказа без ингридиентов")
    public void createOrderWithoutIngridients() {
        ArrayList<String> data = new ArrayList<String>();
        OrdersHelper order = new OrdersHelper(data);
        order.makeCreateOrderRequestWithoutLogin();
        int code = order.getOrderRequestStatusCode();

        try {
            CreateOrderResponse respData = order.getOrder().as(CreateOrderResponse.class);
        } catch (Exception exception) {
            fail("Пришедший ответ не соответствует документации API");
        }
        assertEquals(400, code);
    }

    @Test
    @DisplayName("Попытка создания заказа с некорректными ингридиентами")
    public void createOrderWithIncorrectIngredients() {

        ArrayList<String> data = new ArrayList<String>();
        data.add("00061c0c5a71d1f82001bdaaa6d");
        data.add("00061c0c5a71d1f82001bdaaa6f");

        OrdersHelper order = new OrdersHelper(data);
        order.makeCreateOrderRequestWithoutLogin();
        int code = order.getOrderRequestStatusCode();

        assertEquals(500, code);
    }

    @Test
    @DisplayName("Получение заказов для пользователя")
    public void getOrdersForUser() {

        ArrayList<String> data = new ArrayList<String>();
        OrdersHelper order = new OrdersHelper(data);

        UserHelper user = new UserHelper("as007ershov@gmail.com", "as007ershov@gmail.com", "as007ershov@gmail.com");
        user.makeLoginUserRequest();
        LoginUserResponse resp = user.getLoginUser().as(LoginUserResponse.class);
        order.makeCreateOrderRequestWithLogin(resp.getAccessToken());


        order.getOrdersWithLogin(resp.getAccessToken());

        int code = order.getGetOrdersStatusCode();

        try {
            GetOrdersResponseSuccess respData = order.getOrders().as(GetOrdersResponseSuccess.class);
        } catch (Exception exception) {
            fail("Пришедший ответ не соответствует документации API");
        }
        assertEquals(200, code);

    }

    @Test
    @DisplayName("Получение заказов при отсутствии логина")
    public void getOrdersWithoutLogin() {

        ArrayList<String> data = new ArrayList<String>();
        OrdersHelper order = new OrdersHelper(data);

        order.getOrdersWithoutLogin();

        int code = order.getGetOrdersStatusCode();

        try {
            GetOrdersResponse respData = order.getOrders().as(GetOrdersResponse.class);
        } catch (Exception exception) {
            fail("Пришедший ответ не соответствует документации API");
        }
        assertEquals(401, code);

    }

}
