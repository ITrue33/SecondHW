package com.vilkovandrew;

import com.vilkovandrew.helpers.Properties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * Базовый класс для тестов.
 *
 * @author Вилков Андрей
 */
public class BaseTest {
    /**
     * Содержит экземпляр {@link WebDriver}.
     *
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    protected WebDriver driver;

    /**
     * Конфигурирование драйвера перед тестами.
     *
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    @BeforeEach
    void beforeTest() {
        System.setProperty("webdriver.chrome.driver", Properties.appProperties.getDriverPath());

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--remote-allow-origins=*",
                "--incognito",
                "--disable-blink-features=AutomationControlled",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-infobars"
        );

        options.addArguments("user-agent=\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36\"");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setPageLoadTimeout(Duration.ofSeconds(120));

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.manage().window().maximize();

    }

    /**
     * Закрытие драйвера после тестов.
     *
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    @AfterEach
    void afterTest() {
        driver.quit();
    }
}
