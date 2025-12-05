📦 Stellar Burgers API — Автоматизация тестирования

Проект содержит автотесты для проверки API сервиса Stellar Burgers.
Тесты написаны на Java 11, JUnit 4, RestAssured 5, с использованием Allure Reports.

🗂 Структура проекта
src
└── test
└── java
├── api
│   ├── orders
│   │    ├── OrderCreateTest.java
│   │    └── OrderListTest.java
│   └── user
│        ├── UserCreateTest.java
│        └── UserLoginTest.java
├── helpers
│    ├── Endpoints.java
│    └── TestBase.java
└── models
├── Order.java
├── User.java
└── UserCredentials.java

Основные директории:

api/ — тесты, сгруппированные по сущностям API

helpers/ — базовые классы, общие методы, конфигурация

models/ — POJO-модели, передаваемые в запросах

⚙️ Технологии
Компонент	Версия	Описание
Java	11	язык разработки
Maven	3.x	управление зависимостями
JUnit	4.13.2	тестовый фреймворк
RestAssured	5.4.0	библиотека для API-тестов
Jackson	2.17.2	сериализация JSON
Allure	2.27.0	отчёты
🚀 Как запустить тесты
1. Клонировать проект
   git clone <repo>
   cd Task_2

2. Запустить тесты
   mvn clean test


После выполнения увидите:

BUILD SUCCESS
Tests run: 8, Failures: 0

📊 Генерация Allure отчёта
Сформировать и открыть отчёт:
mvn allure:serve


Команда:

генерирует отчёт

запускает локальный веб-сервер

автоматически открывает браузер

Отчёт содержит:

Suites (по группам тестов)

Steps (шаги @Step из TestBase)

Duration

Timeline

🧪 Покрытие тестами
### 1. Пользователь
✔️ UserCreateTest

userCanBeCreated() — успешная регистрация нового пользователя

userCannotBeCreatedTwiceWithSameCredentials() — попытка зарегистрировать уже существующего пользователя

✔️ UserLoginTest

userCanLoginWithValidCredentials()

userCannotLoginWithInvalidPassword()

### 2. Заказы
✔️ OrderCreateTest

authorisedUserCanCreateOrderWithIngredients() — создание заказа авторизованным пользователем

userCannotCreateOrderWithoutIngredients() — негативный тест (ожидаем только 400, без проверки тела)

✔️ OrderListTest

authorisedUserCanGetOrders() — получение списка заказов авторизованным пользователем

unauthorisedUserCannotGetOrders() — запрос без токена

🔧 Endpoints

Базовый URL:

https://stellarburgers.education-services.ru


API-пути:

Назначение	Endpoint
Регистрация	/api/auth/register
Логин	/api/auth/login
Ингредиенты	/api/ingredients
Заказы	/api/orders
🧱 TestBase — общая логика тестов

Вынесено в отдельный класс:

✔️ регистрация пользователей
✔️ авторизация
✔️ получение токена
✔️ выбор ингредиентов
✔️ создание заказа
✔️ получение заказов

Все методы аннотированы @Step — отображаются в Allure.

📝 Особенности реализации

Проект валиден для сдачи в Практикум

Структура соответствует требованиям ревью

Негативный тест заказа проверяет только статус 400 (как требует ТЗ)

Все тесты независимы — каждый создаёт собственный тестовый аккаунт

Allure-шаги включены автоматически через listener