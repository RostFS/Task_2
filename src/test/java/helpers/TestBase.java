package helpers;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Order;
import models.User;
import models.UserCredentials;
import org.junit.Before;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class TestBase {

    // БАЗОВАЯ спецификация, от неё будем делать КОПИИ
    protected RequestSpecification requestSpec;

    @Before
    public void setup() {
        RestAssured.baseURI = Endpoints.BASE_URL;

        requestSpec = given()
                .baseUri(Endpoints.BASE_URL)
                .contentType(ContentType.JSON);
    }

    // ---------- Пользователь ----------

    @Step("Создать случайного пользователя")
    protected User createRandomUser() {
        String random = UUID.randomUUID().toString().substring(0, 8);
        String email = "user_" + random + "@yandex.ru";
        String password = "password_" + random;
        String name = "Name_" + random;
        return new User(email, password, name);
    }

    @Step("Зарегистрировать пользователя")
    protected Response registerUser(User user) {
        return given()
                .spec(requestSpec)          // берём КОПИЮ базовой спеки
                .body(user)
                .when()
                .post(Endpoints.REGISTER);
    }

    @Step("Зарегистрировать пользователя и получить токен")
    protected String registerAndGetToken(User user) {
        // 1. Регистрируем
        registerUser(user)
                .then()
                .statusCode(200)
                .body("accessToken", notNullValue());

        // 2. Логинимся теми же данными
        UserCredentials creds = new UserCredentials(user.getEmail(), user.getPassword());
        Response loginResponse = loginUser(creds);

        // 3. Берём accessToken из логина
        return loginResponse.then()
                .statusCode(200)
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");
    }

    @Step("Авторизовать пользователя")
    protected Response loginUser(UserCredentials creds) {
        return given()
                .spec(requestSpec)
                .body(creds)
                .when()
                .post(Endpoints.LOGIN);
    }

    // ---------- Ингредиенты и заказы ----------

    @Step("Получить список id доступных ингредиентов")
    protected List<String> getRandomIngredientIds() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(Endpoints.INGREDIENTS);

        return response.then()
                .statusCode(200)
                .extract()
                .path("data._id");
    }

    @Step("Создать заказ")
    protected Response createOrder(Order order, String token) {
        RequestSpecification spec = given()
                .spec(requestSpec)   // копия, а не тот же объект
                .body(order);

        if (token != null && !token.isEmpty()) {
            spec.header("Authorization", token);
            // если нужно по “решению” — можно так:
            // spec.header("Authorization", "Bearer " + token);
        }

        return spec
                .when()
                .post(Endpoints.ORDERS);
    }

    @Step("Получить список заказов пользователя")
    protected Response getUserOrders(String token) {
        RequestSpecification spec = given()
                .spec(requestSpec);  // НОВЫЙ spec без body

        if (token != null && !token.isEmpty()) {
            spec.header("Authorization", token);
        }

        return spec
                .when()
                .get(Endpoints.ORDERS);
    }

    @Step("Получить список заказов без авторизации")
    protected Response getUserOrdersWithoutAuth() {
        return getUserOrders(null);
    }
}
