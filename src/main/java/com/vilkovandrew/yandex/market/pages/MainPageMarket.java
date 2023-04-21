package com.vilkovandrew.yandex.market.pages;

import com.vilkovandrew.helpers.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;

/**
 * Класс представляющий Page Object для главной страницы 'Яндекс Маркет'.
 *
 * @author Vilkov Andrew
 */
public class MainPageMarket {
    /**
     * Переменная для хранения экземпляра класса {@link WebDriver}.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private final WebDriver driver;

    /**
     * Переменная для хранения экземпляра класса {@link WebDriverWait}.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private final WebDriverWait wait;

    /**
     * Локатор кнопки 'Каталог'.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_CATALOG_BUTTON = By.id("catalogPopupButton");

    /**
     * Локатор вкладки с категориями.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_TAB_LIST_CATEGORY = By.xpath("//li[@role=\"tab\"]/a");

    /**
     * Локатор разделов категории каталога.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_ITEMS = By.xpath("//div[@role='tabpanel']/div//ul[@data-autotest-id='subItems']/li");

    /**
     * Конструктор класса {@link MainPageMarket}.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param driver экземпляр класса {@link WebDriver}
     */
    public MainPageMarket(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    /**
     * Открытие каталога, если он еще не открыт
     * <p>
     * Автор: Вилков Андрей
     * </p>     *
     */
    public void openCatalog() {
        WebElement catalogButton = driver.findElement(LOCATOR_CATALOG_BUTTON);
        boolean isExpanded = Boolean.getBoolean(catalogButton.getDomAttribute("aria-expanded"));
        if (!isExpanded) {
            catalogButton.click();
        }
    }

    /**
     * Наведение курсора на категорию товара в каталоге
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param sectionName имя категории в каталоге
     */
    public void moveCursorToSection(String sectionName) {
        List<WebElement> categories = wait.until(visibilityOfAllElementsLocatedBy(LOCATOR_TAB_LIST_CATEGORY));
        Optional<WebElement> itemCategory = categories.stream()
                .filter(e -> e.getText().equalsIgnoreCase(sectionName))
                .findAny();

        Assertions.assertTrue(itemCategory.isPresent(),
                String.format("Пункт меню \"%s\" не найден в каталоге", sectionName));

        WebElement sectionItem = itemCategory.get();
        new Actions(driver)
                .moveToElement(sectionItem)
                .perform();
    }

    /**
     * Открытие раздела из категории каталога
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param itemName имя раздела в категории каталога
     */
    public void openSectionItem(String itemName) {
        List<WebElement> itemList = driver.findElements(LOCATOR_ITEMS);
        Optional<WebElement> item = itemList.stream()
                .filter(e -> itemName.equalsIgnoreCase(e.getText()))
                .findAny();

        Assertions.assertTrue(item.isPresent(),
                String.format("Раздел \"%s\" не найден в текущей категории", itemName));

        WebElement itemElement = item.get();
        new Actions(driver)
                .moveByOffset(itemElement.getLocation().getX(), itemElement.getLocation().getY())
                .moveToElement(itemElement)
                .click()
                .perform();
        wait.until(ExpectedConditions.titleContains(itemName));
    }
}
