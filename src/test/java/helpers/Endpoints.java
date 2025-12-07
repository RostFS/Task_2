package helpers;

public class Endpoints {

    public static final String BASE_URL = "https://stellarburgers.education-services.ru";

    // ---------- Auth ----------
    public static final String REGISTER = "/api/auth/register";
    public static final String LOGIN = "/api/auth/login";
    public static final String USER = "/api/auth/user"; // <-- Добавили для удаления пользователя

    // ---------- Ingredients ----------
    public static final String INGREDIENTS = "/api/ingredients";

    // ---------- Orders ----------
    public static final String ORDERS = "/api/orders";
}
