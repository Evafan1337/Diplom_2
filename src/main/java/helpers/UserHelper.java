package helpers;

import data.CreateUserRequest;
import data.LoginUserRequest;
import data.LoginUserResponseSuccess;
import data.UserRequest;
import io.qameta.allure.Step;
import io.restassured.internal.RestAssuredResponseImpl;

import static io.restassured.RestAssured.given;

public class UserHelper {

    private RestAssuredResponseImpl user;
    private RestAssuredResponseImpl editUser;

    private RestAssuredResponseImpl loginUser = null;

    private CreateUserRequest createUserData;
    private LoginUserRequest loginUserData;

    public UserHelper(String email, String password, String name) {

        createUserData = new CreateUserRequest(email, password, name);
        loginUserData = new LoginUserRequest(email, password);

    }

    @Step("Запрос создания пользователя")
    public void makeCreateUserRequest() {
        user = (RestAssuredResponseImpl) given()
                .header("Content-type", "application/json")
                .and()
                .body(createUserData)
                .when()
                .post("/api/auth/register")
                .body();
    }

    @Step("Запрос авторизации")
    public void makeLoginUserRequest() {
        loginUser = (RestAssuredResponseImpl) given()
                .header("Content-type", "application/json")
                .and()
                .body(loginUserData)
                .when()
                .post("/api/auth/login")
                .body();
    }

    public RestAssuredResponseImpl getUser() {
        return user;
    }

    public RestAssuredResponseImpl getLoginUser() {
        return loginUser;
    }

    public RestAssuredResponseImpl getEditUser(){
        return editUser;
    }

    @Step("Получение кода ответа от запроса создания пользователя")
    public int getUserCreateStatusCode() {
        return user.statusCode();
    }

    @Step("Получение кода ответа от запроса авторизации пользователя")
    public int getUserLoginStatusCode() {
        return loginUser.statusCode();
    }

    @Step("Запрос удаления пользователя")
    public void deleteUser() {

        //Need login and check for relogin
        //this.makeLoginUserRequest();

        //LoginUserResponse resp = this.getUser().as(LoginUserResponse.class);
        LoginUserResponseSuccess resp = this.getLoginUser().as(LoginUserResponseSuccess.class);

        given()
                .header("Content-type", "application/json")
                .header("Authorization", resp.getAccessToken())
                .and()
                .delete("/api/auth/user")
                .then().statusCode(202);

    }

    @Step("Запрос редактирования пользователя")
    public void updateUser(String email, String name) {


        LoginUserResponseSuccess resp = this.getLoginUser().as(LoginUserResponseSuccess.class);
        UserRequest newUserData = new UserRequest(email, name);

        editUser = (RestAssuredResponseImpl) given()
                .header("Content-type", "application/json")
                .header("Authorization", resp.getAccessToken())
                .and()
                .body(newUserData)
                .when()
                .patch("/api/auth/user")
                .body();
    }

    @Step("Запрос редактирования пользователя без логина")
    public void updateUserWithoutLogin(String email, String name) {

        UserRequest newUserData = new UserRequest(email, name);
        editUser = (RestAssuredResponseImpl) given()
                .header("Content-type", "application/json")
                .and()
                .body(newUserData)
                .when()
                .patch("/api/auth/user")
                .body();
    }

    @Step("Получение кода ответа от запроса редактирования пользователя")
    public int getUserEditStatusCode() {
        return editUser.statusCode();
    }
}
