package api.orders;

import helpers.TestBase;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Order;
import models.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest extends TestBase {

    @Test
    @Step("Авторизованный пользователь может получить список своих заказов")
    public void authorisedUserCanGetOrders() {
        User user = createRandomUser();
        String token = registerAndGetToken(user);

        // создаём хотя бы один заказ, чтобы список не был пустым
        List<String> ingredientIds = getRandomIngredientIds();
        Order order = new Order(Arrays.asList(
                ingredientIds.get(0),
                ingredientIds.get(1),
                ingredientIds.get(2)
        ));
        createOrder(order, token);

        Response response = getUserOrders(token);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("orders", notNullValue());
    }

    @Test
    @Step("Неавторизованный пользователь не может получить список заказов")
    public void unauthorisedUserCannotGetOrders() {
        Response response = getUserOrdersWithoutAuth();

        response.then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", notNullValue());
    }
}
