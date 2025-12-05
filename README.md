# Диплом. Задание 2 — API Stellar Burgers

Автоматизированные API-тесты для сервиса **Stellar Burgers**.  
Проект проверяет регистрацию и авторизацию пользователей, создание заказов и получение списка заказов.

---

## Стек

| Компонент    | Версия   | Назначение                         |
|-------------|----------|------------------------------------|
| Java        | 11       | язык разработки                    |
| Maven       | 3.x      | управление зависимостями           |
| JUnit       | 4.13.2   | тестовый фреймворк                 |
| RestAssured | 5.4.0    | API-клиент                         |
| Jackson     | 2.17.2   | сериализация / десериализация JSON |
| Allure      | 2.27.0   | отчёты по автотестам               |

---

## Структура проекта

```text
src
└── test
    └── java
        ├── api
        │   ├── orders
        │   │   ├── OrderCreateTest.java
        │   │   └── OrderListTest.java
        │   └── user
        │       ├── UserCreateTest.java
        │       └── UserLoginTest.java
        ├── helpers
        │   ├── Endpoints.java
        │   └── TestBase.java
        └── models
            ├── Order.java
            ├── User.java
            └── UserCredentials.java

Update for PR creation
