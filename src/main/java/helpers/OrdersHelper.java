package helpers;

import data.CreateOrderRequest;
import io.qameta.allure.Step;
import io.restassured.internal.RestAssuredResponseImpl;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class OrdersHelper {

    private RestAssuredResponseImpl order;
    private RestAssuredResponseImpl orders;

    private RestAssuredResponseImpl loginUser = null;
    private CreateOrderRequest createOrderRequest;

    public OrdersHelper(ArrayList<String> ingridients){

        this.createOrderRequest = new CreateOrderRequest(ingridients);
    };


    @Step("Создание заказа без авторизации")
    public void makeCreateOrderRequestWithoutLogin(){
        order = (RestAssuredResponseImpl) given()
                .header("Content-type", "application/json")
                .and()
                .body(createOrderRequest)
                .when()
                .post("/api/orders")
                .body();
    }

    @Step("Создание заказа с логином")
    public void makeCreateOrderRequestWithLogin(String token){
        order = (RestAssuredResponseImpl) given()
                .header("Content-type", "application/json")
                .header("Authorization",token)
                .and()
                .body(createOrderRequest)
                .when()
                .post("/api/orders")
                .body();
    }

    public void setLoginUser(RestAssuredResponseImpl loginUser){
        this.loginUser = loginUser;
    }

    public RestAssuredResponseImpl getOrder(){
        return order;
    }

    public RestAssuredResponseImpl getOrders(){
        return orders;
    }

    @Step("Получение кода ответа при создании заказа")
    public int getOrderRequestStatusCode(){
        return order.statusCode();
    }

    @Step("Получение кода ответа при получении заказов")
    public int getGetOrdersStatusCode(){
        return orders.statusCode();
    }

    @Step("Получение заказов с логином")
    public void getOrdersWithLogin(String token){
        orders = (RestAssuredResponseImpl) given()
                .header("Content-type", "application/json")
                .header("Authorization",token)
                .and()
                .when()
                .get("/api/orders")
                .body();
    }

    @Step("Получение заказов без логина")
    public void getOrdersWithoutLogin(){
        orders = (RestAssuredResponseImpl) given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/api/orders")
                .body();
    }

}
