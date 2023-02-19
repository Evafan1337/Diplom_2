package tests;

import data.LoginUserResponse;
import helpers.UserHelper;
import org.junit.After;
import org.junit.Before;
import io.restassured.RestAssured;
import org.junit.Test;

import java.util.ArrayList;

public class UserTest {

    ArrayList <UserHelper> createdUsers = new ArrayList<>();;

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void createUserCorrect(){
        UserHelper user = new UserHelper("cr-test-data-2@yandex.ru", "password", "cr-username-1");
        user.makeCreateUserRequest();
        createdUsers.add(user);
        user.makeLoginUserRequest();


        assert user.getUserCreateStatusCode() == 200;
    }

    @Test
    public void failCreatingTwoSimilarUsersTest(){
        UserHelper user = new UserHelper("cr-s-test-data-2@yandex.ru", "password", "cr-s-username-1");
        user.makeCreateUserRequest();
        createdUsers.add(user);
        user.makeLoginUserRequest();
        assert user.getUserCreateStatusCode() == 200;

        UserHelper user2 = new UserHelper("cr-s-test-data-2@yandex.ru", "password", "cr-s-username-1");
        user2.makeCreateUserRequest();
        assert user2.getUserCreateStatusCode() == 403;
    }

    @Test
    public void loginUserCorrect(){
        UserHelper user = new UserHelper("as007ershov@gmail.com", "as007ershov@gmail.com", "as007ershov@gmail.com");
        user.makeLoginUserRequest();
        assert user.getUserLoginStatusCode() == 200;
    }

    @Test
    public void loginUnregisteredUserFail(){
        UserHelper user = new UserHelper("11nonregister11@yandex.ru", "password", "11nonregister11");
        user.makeLoginUserRequest();
        assert user.getUserLoginStatusCode() == 401;
    }

    @Test
    public void failCreateUserWithoutEmail(){
        UserHelper user = new UserHelper("", "password", "username-1");
        user.makeCreateUserRequest();
        assert user.getUserCreateStatusCode() == 403;
    }

    @Test
    public void failCreateUserWithoutPassword(){
        UserHelper user = new UserHelper("p-test-data-2@yandex.ru", "", "p-username-1");
        user.makeCreateUserRequest();
        assert user.getUserCreateStatusCode() == 403;
    }

    @Test
    public void failCreateUserWithoutName(){
        UserHelper user = new UserHelper("n-test-data-2@yandex.ru", "password", "");
        user.makeCreateUserRequest();

        assert user.getUserCreateStatusCode() == 403;
    }

    //  TO-DO
    @Test
    public void correctEditUserWithLogin(){
        int expected = 200;
        UserHelper user = new UserHelper("4-test@yandex.ru", "password", "test-login-4");
        user.makeCreateUserRequest();
        createdUsers.add(user);
        user.makeLoginUserRequest();

        user.updateUser("4-test@yandex.ru","e-test-login-4");


        assert user.getUserEditStatusCode() == expected;

        //Rollback edits
        user.updateUser("4-test@yandex.ru","test-login-4");
    }

    // TO-DO
    @Test
    public void failWithTryToEditUserWithoutLogin(){
        int expected = 401;

        UserHelper user = new UserHelper("4-test@yandex.ru", "password", "test-login-4");
        user.updateUserWithoutLogin("4-test@yandex.ru","e-test-login-4");
        assert user.getUserEditStatusCode() == expected;
    }

    @After
    public void teardown(){

        for(UserHelper elem : createdUsers){
            elem.deleteUser();
        }
    }
}
