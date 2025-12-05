package api.user;

import helpers.TestBase;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.User;
import models.UserCredentials;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserLoginTest extends TestBase {

    @Test
    @Step("Пользователь может залогиниться с валидными учётными данными")
    public void userCanLoginWithValidCredentials() {
        User user = createRandomUser();
        // сначала регистрируем пользователя
        registerUser(user);

        UserCredentials creds = new UserCredentials(user.getEmail(), user.getPassword());
        Response loginResponse = loginUser(creds);

        loginResponse.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Test
    @Step("Пользователь не может залогиниться с неверным паролем")
    public void userCannotLoginWithInvalidPassword() {
        User user = createRandomUser();
        registerUser(user);

        // портим пароль
        UserCredentials wrongCreds = new UserCredentials(user.getEmail(), user.getPassword() + "1");

        Response loginResponse = loginUser(wrongCreds);

        loginResponse.then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", notNullValue());
    }
}
