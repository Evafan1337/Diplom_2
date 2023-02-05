package tests;

import org.junit.Before;
import io.restassured.RestAssured;
import org.junit.Test;

public class CreateUserTest {

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void createUserCorrect(){

    }
}
