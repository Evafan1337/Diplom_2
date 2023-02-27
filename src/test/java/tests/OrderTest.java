package tests;

import data.*;
import helpers.OrdersHelper;
import helpers.UserHelper;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.internal.RestAssuredResponseImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OrderTest {


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

        RestAssuredResponseImpl resp = order.getOrder();
        System.out.println(resp.body().asString());
        resp.then().assertThat().statusCode(200).extract().body().as(CreateOrderResponseSuccess.class);
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

        LoginUserResponseSuccess resp = user.getLoginUser().as(LoginUserResponseSuccess.class);
        order.makeCreateOrderRequestWithLogin(resp.getAccessToken());
        int code = order.getOrderRequestStatusCode();

        CreateOrderResponseSuccess respData = order.getOrder().as(CreateOrderResponseSuccess.class);
        System.out.println(respData.getName());


//        try {
//        } catch (Exception exception) {
//            fail("Пришедший ответ не соответствует документации API");
//        }
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
        LoginUserResponseSuccess resp = user.getLoginUser().as(LoginUserResponseSuccess.class);
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
