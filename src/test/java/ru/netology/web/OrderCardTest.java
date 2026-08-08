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

class OrderCardTest {

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

    @Test
    void shouldOrderCardSuccessfully() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);

        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Иванова Ольга");

        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79270000000");

        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();

        driver.findElement(By.cssSelector("form button"))
                .click();

        assertTrue(
                driver.getPageSource().contains("Ваша заявка успешно отправлена!")
        );
    }
}