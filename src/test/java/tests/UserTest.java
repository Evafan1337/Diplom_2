package tests;

import helpers.UserHelper;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import io.restassured.RestAssured;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserTest {

    ArrayList<UserHelper> createdUsers = new ArrayList<>();
    UserHelper editedUser = null;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Создание пользователя")
    public void createUserCorrect() {
        UserHelper user = new UserHelper("cr-test-data-2@yandex.ru", "password", "cr-username-1");
        user.makeCreateUserRequest();
        createdUsers.add(user);
        user.makeLoginUserRequest();

        assertEquals(200, user.getUserCreateStatusCode());
    }

    @Test
    @DisplayName("Попытка создания двух одинаковых пользователей")
    public void failCreatingTwoSimilarUsersTest() {
        UserHelper user = new UserHelper("cr-s-test-data-2@yandex.ru", "password", "cr-s-username-1");
        user.makeCreateUserRequest();
        createdUsers.add(user);
        user.makeLoginUserRequest();
        assertEquals(200, user.getUserCreateStatusCode());


        UserHelper user2 = new UserHelper("cr-s-test-data-2@yandex.ru", "password", "cr-s-username-1");
        user2.makeCreateUserRequest();
        assertEquals(403, user2.getUserCreateStatusCode());

    }

    @Test
    @DisplayName("Корректный логин пользователя")
    public void loginUserCorrect() {
        UserHelper user = new UserHelper("as007ershov@gmail.com", "as007ershov@gmail.com", "as007ershov@gmail.com");
        user.makeLoginUserRequest();
        assertEquals(user.getUserLoginStatusCode(), 200);

    }

    @Test
    @DisplayName("Попытка логина для несуществующего пользователя")
    public void loginUnregisteredUserFail() {
        UserHelper user = new UserHelper("11nonregister11@yandex.ru", "password", "11nonregister11");
        user.makeLoginUserRequest();
        assertEquals(200, user.getUserLoginStatusCode());
    }

    @Test
    @DisplayName("Попытка создания пользователя без эл.почты")
    public void failCreateUserWithoutEmail() {
        UserHelper user = new UserHelper("", "password", "username-1");
        user.makeCreateUserRequest();
        assertEquals(403, user.getUserCreateStatusCode());
    }

    @Test
    @DisplayName("Попытка создания пользователя без пароля")
    public void failCreateUserWithoutPassword() {
        UserHelper user = new UserHelper("p-test-data-2@yandex.ru", "", "p-username-1");
        user.makeCreateUserRequest();
        assertEquals(403, user.getUserCreateStatusCode());
    }

    @Test
    @DisplayName("Попытка создания пользователя без логина")
    public void failCreateUserWithoutName() {
        UserHelper user = new UserHelper("n-test-data-2@yandex.ru", "password", "");
        user.makeCreateUserRequest();

        assertEquals(403, user.getUserCreateStatusCode());
    }

    //  TO-DO
    @Test
    @DisplayName("Успешное изменение данных пользователя")
    public void correctEditUserWithLogin() {
        int expected = 200;
        UserHelper user = new UserHelper("4-test@yandex.ru", "password", "test-login-4");
        user.makeCreateUserRequest();
        createdUsers.add(user);
        user.makeLoginUserRequest();

        user.updateUser("4-test@yandex.ru", "e-test-login-4");

        editedUser = user;
        assertEquals(expected, user.getUserEditStatusCode());

    }

    // TO-DO
    @Test
    @DisplayName("Попытка редактирования пользователя без логина")
    public void failWithTryToEditUserWithoutLogin() {
        int expected = 401;

        UserHelper user = new UserHelper("4-test@yandex.ru", "password", "test-login-4");
        user.updateUserWithoutLogin("4-test@yandex.ru", "e-test-login-4");
        assertEquals(expected, user.getUserEditStatusCode());

    }

    @After
    public void teardown() {

        if (editedUser != null) {
            editedUser.updateUser("4-test@yandex.ru", "test-login-4");
        }

        for (UserHelper elem : createdUsers) {
            elem.deleteUser();
        }
    }
}
