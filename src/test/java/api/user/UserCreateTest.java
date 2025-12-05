package api.user;

import helpers.TestBase;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.User;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserCreateTest extends TestBase {

    @Test
    @Step("Пользователь может быть создан")
    public void userCanBeCreated() {
        User user = createRandomUser();

        Response response = registerUser(user);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Test
    @Step("Нельзя создать двух пользователей с одинаковыми учётными данными")
    public void userCannotBeCreatedTwiceWithSameCredentials() {
        User user = createRandomUser();

        // первый раз — успешно
        registerUser(user)
                .then()
                .statusCode(200);

        // второй раз — уже ошибка
        Response second = registerUser(user);

        second.then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", notNullValue());
    }
}
