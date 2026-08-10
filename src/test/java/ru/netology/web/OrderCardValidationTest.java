package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderCardValidationTest {

    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldShowErrorWhenNameIsEmpty() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79270000000");

        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();

        driver.findElement(By.cssSelector("form button"))
                .click();

        var error = driver.findElement(
                By.cssSelector("[data-test-id='name'].input_invalid .input__sub")
        );

        assertTrue(error.isDisplayed());
        assertEquals(
                "Поле обязательно для заполнения",
                error.getText().trim()
        );
    }

    @Test
    void shouldShowErrorWhenNameIsInvalid() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Ivanova Olga");

        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79270000000");

        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();

        driver.findElement(By.cssSelector("form button"))
                .click();

        var error = driver.findElement(
                By.cssSelector("[data-test-id='name'].input_invalid .input__sub")
        );

        assertTrue(error.isDisplayed());
        assertEquals(
                "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                error.getText().trim()
        );
    }

    @Test
    void shouldShowErrorWhenPhoneIsEmpty() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Иванова Ольга");

        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();

        driver.findElement(By.cssSelector("form button"))
                .click();

        var error = driver.findElement(
                By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")
        );

        assertTrue(error.isDisplayed());
        assertEquals(
                "Поле обязательно для заполнения",
                error.getText().trim()
        );
    }

    @Test
    void shouldShowErrorWhenPhoneIsInvalid() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Иванова Ольга");

        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("12345");

        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();

        driver.findElement(By.cssSelector("form button"))
                .click();

        var error = driver.findElement(
                By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")
        );

        assertTrue(error.isDisplayed());
        assertEquals(
                "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                error.getText().trim()
        );
    }

    @Test
    void shouldShowErrorWhenAgreementIsNotChecked() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Иванова Ольга");

        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79270000000");

        driver.findElement(By.cssSelector("form button"))
                .click();

        var error = driver.findElement(
                By.cssSelector("[data-test-id='agreement'].input_invalid")
        );

        assertTrue(error.isDisplayed());
        assertEquals(
                "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй",
                error.getText().trim()
        );
    }
}