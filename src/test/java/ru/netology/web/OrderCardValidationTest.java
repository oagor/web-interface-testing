package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderCardValidationTest {

    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void openForm() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @Test
    void shouldShowErrorWhenNameIsEmpty() {
        openForm();

        driver.findElement(By.cssSelector("form button")).click();

        String nameClass = driver.findElement(
                By.cssSelector("[data-test-id='name']")
        ).getAttribute("class");

        assertTrue(nameClass.contains("input_invalid"));
    }

    @Test
    void shouldShowErrorWhenNameIsInvalid() {
        openForm();

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Ivanova Olga");

        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79270000000");

        driver.findElement(By.cssSelector("form button")).click();

        String nameClass = driver.findElement(
                By.cssSelector("[data-test-id='name']")
        ).getAttribute("class");

        assertTrue(nameClass.contains("input_invalid"));
    }

    @Test
    void shouldShowErrorWhenPhoneIsEmpty() {
        openForm();

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Иванова Ольга");

        driver.findElement(By.cssSelector("form button")).click();

        String phoneClass = driver.findElement(
                By.cssSelector("[data-test-id='phone']")
        ).getAttribute("class");

        assertTrue(phoneClass.contains("input_invalid"));
    }

    @Test
    void shouldShowErrorWhenPhoneIsInvalid() {
        openForm();

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Иванова Ольга");

        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("12345");

        driver.findElement(By.cssSelector("form button")).click();

        String phoneClass = driver.findElement(
                By.cssSelector("[data-test-id='phone']")
        ).getAttribute("class");

        assertTrue(phoneClass.contains("input_invalid"));
    }

    @Test
    void shouldShowErrorWhenAgreementIsNotChecked() {
        openForm();

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Иванова Ольга");

        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79270000000");

        driver.findElement(By.cssSelector("form button")).click();

        String agreementClass = driver.findElement(
                By.cssSelector("[data-test-id='agreement']")
        ).getAttribute("class");

        assertTrue(agreementClass.contains("input_invalid"));
    }
}