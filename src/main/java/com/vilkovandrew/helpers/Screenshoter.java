package com.vilkovandrew.helpers;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.nio.file.Files;

/**
 * Вспомогательный класс для создания скриншотов.
 *
 * @author Вилков Андрей
 */
public class Screenshoter {
    /**
     * Создание скриншота страницы.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param driver экземпляр класса {@link WebDriver}
     */
    @Attachment
    public static byte[] getScreen(WebDriver driver) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            return Files.readAllBytes(screenshot.toPath());
        } catch (Exception e) {
            Assertions.fail("Не удалось получить скриншот", e);
        }
        return new byte[0];
    }

    /**
     * Создание скриншота элемента.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param driver  экземпляр класса {@link WebDriver}
     * @param element экземпляр класса {@link WebElement}, скриншот которого будет сделан
     */
    @Attachment
    public static byte[] getScreen(WebDriver driver, WebElement element) {
        new Actions(driver).moveToElement(element).perform();

        File screenshot = element.getScreenshotAs(OutputType.FILE);
        try {
            return Files.readAllBytes(screenshot.toPath());
        } catch (Exception e) {
            Assertions.fail("Не удалось получить скриншот", e);
        }
        return new byte[0];
    }
}
