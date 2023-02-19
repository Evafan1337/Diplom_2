package helpers;
import data.CreateUserRequest;
import data.LoginUserRequest;
import data.LoginUserResponse;
import data.UserRequest;
import io.restassured.internal.RestAssuredResponseImpl;

import static io.restassured.RestAssured.given;

public class UserHelper {

    RestAssuredResponseImpl user;
    RestAssuredResponseImpl editUser;

    RestAssuredResponseImpl loginUser = null;

    CreateUserRequest createUserData;
    LoginUserRequest  loginUserData;

    public UserHelper(String email, String password, String name){

        createUserData = new CreateUserRequest(email, password, name);
        loginUserData = new LoginUserRequest(email, password);

    }

    public void makeCreateUserRequest(){
        user = (RestAssuredResponseImpl) given()
                .header("Content-type", "application/json")
                .and()
                .body(createUserData)
                .when()
                .post("/api/auth/register")
                .body();
    }

    public void makeLoginUserRequest(){
        loginUser = (RestAssuredResponseImpl) given()
                .header("Content-type", "application/json")
                .and()
                .body(loginUserData)
                .when()
                .post("/api/auth/login")
                .body();
    }

    public RestAssuredResponseImpl getUser(){
        return user;
    }

    public RestAssuredResponseImpl getLoginUser(){
        return loginUser;
    }

    public int getUserCreateStatusCode(){
        return user.statusCode();
    }

    public int getUserLoginStatusCode(){
        return loginUser.statusCode();
    }

    public void deleteUser(){

        //Need login and check for relogin
        //this.makeLoginUserRequest();

        //LoginUserResponse resp = this.getUser().as(LoginUserResponse.class);
        LoginUserResponse resp = this.getLoginUser().as(LoginUserResponse.class);

        given()
            .header("Content-type", "application/json")
            .header("Authorization",resp.getAccessToken())
            .and()
            .delete("/api/auth/user")
            .then().statusCode(202);

    }

    public void updateUser(String email, String name){


        LoginUserResponse resp = this.getLoginUser().as(LoginUserResponse.class);
        UserRequest newUserData = new UserRequest(email, name);

        editUser = (RestAssuredResponseImpl) given()
                .header("Content-type", "application/json")
                .header("Authorization",resp.getAccessToken())
                .and()
                .body(newUserData)
                .when()
                .patch("/api/auth/user")
                .body();
    }

    public void updateUserWithoutLogin(String email, String name){

        UserRequest newUserData = new UserRequest(email, name);
        editUser = (RestAssuredResponseImpl) given()
                .header("Content-type", "application/json")
                .and()
                .body(newUserData)
                .when()
                .patch("/api/auth/user")
                .body();
    }

    public int getUserEditStatusCode(){
        return editUser.statusCode();
    }
}
