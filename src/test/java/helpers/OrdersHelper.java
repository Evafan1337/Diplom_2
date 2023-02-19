package helpers;

import data.CreateOrderRequest;
import io.restassured.internal.RestAssuredResponseImpl;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OrdersHelper {

    RestAssuredResponseImpl order;
    RestAssuredResponseImpl orders;

    RestAssuredResponseImpl loginUser = null;
    CreateOrderRequest createOrderRequest;

    public OrdersHelper(ArrayList<String> ingridients){

        this.createOrderRequest = new CreateOrderRequest(ingridients);
    };

    public void makeCreateOrderRequestWithoutLogin(){
        order = (RestAssuredResponseImpl) given()
                .header("Content-type", "application/json")
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

    public int getOrderRequestStatusCode(){
        return order.statusCode();
    }

    public int getGetOrdersStatusCode(){
        return orders.statusCode();
    }

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

    public void getOrdersWithLogin(String token){
        orders = (RestAssuredResponseImpl) given()
                .header("Content-type", "application/json")
                .header("Authorization",token)
                .and()
                .when()
                .get("/api/orders")
                .body();
    }

    public void getOrdersWithoutLogin(){
        orders = (RestAssuredResponseImpl) given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/api/orders")
                .body();
    }

}
