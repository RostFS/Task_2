package api.orders;

import helpers.TestBase;
import models.Order;
import models.User;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class OrderCreateTest extends TestBase {

    @Test
    public void authorisedUserCanCreateOrderWithIngredients() {
        // Создаём и регистрируем пользователя
        User user = createRandomUser();
        String token = registerAndGetToken(user);

        // Получаем список доступных ингредиентов
        List<String> ingredients = getRandomIngredientIds();

        // Можно взять несколько первых, но подойдёт и весь список
        Order order = new Order(ingredients);

        // Создаём заказ с авторизацией
        createOrder(order, token)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    public void userCannotCreateOrderWithoutIngredients() {
        // Пользователь авторизован, но список ингредиентов пустой
        User user = createRandomUser();
        String token = registerAndGetToken(user);

        Order orderWithoutIngredients = new Order(Collections.emptyList());

        // Сервер вернёт 400 и HTML, поэтому проверяем только статус
        createOrder(orderWithoutIngredients, token)
                .then()
                .statusCode(400);
    }
}
