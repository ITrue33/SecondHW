package com.vilkovandrew.steps;

import com.vilkovandrew.helpers.Assertions;
import com.vilkovandrew.helpers.Properties;
import com.vilkovandrew.helpers.Screenshoter;
import com.vilkovandrew.yandex.market.helpers.CheckBoxFilter;
import com.vilkovandrew.yandex.market.helpers.Filter;
import com.vilkovandrew.yandex.market.helpers.Product;
import com.vilkovandrew.yandex.market.helpers.RangeFilter;
import com.vilkovandrew.yandex.market.pages.CatalogListPage;
import com.vilkovandrew.yandex.market.pages.MainPageMarket;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

/**
 * Класс предоставляющий шаги для тестов.
 *
 * @author Вилков Андрей
 */
public class StepsAll {
    /**
     * Переменная для хранения экземпляра класса {@link WebDriver}.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static WebDriver driver;

    /**
     * Переменная для хранения экземпляра класса {@link WebDriverWait}.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static WebDriverWait wait;

    /**
     * Открытие конкретное страницы.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param url           URL страницы
     * @param title         ожидаемый заголовок или часть заголовка страницы
     * @param currentDriver экземпляра класса {@link WebDriver}
     */
    @Step("Переходим на сайт: {url}")
    public static void openSite(String url, String title, WebDriver currentDriver) {
        driver = currentDriver;
        driver.get(url);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.titleContains(title));
    }

    /**
     * Открытие каталога
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    @Step("Открытие каталога")
    public static void openCatalog() {
        MainPageMarket mainPageMarket = new MainPageMarket(driver);
        mainPageMarket.openCatalog();
    }

    /**
     * Наведение курсора на категорию товара в каталоге
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param sectionName имя категории в каталоге
     */
    @Step("Наведение курсора на раздел {sectionName}")
    public static void moveCursorToSection(String sectionName) {
        MainPageMarket mainPageMarket = new MainPageMarket(driver);
        mainPageMarket.moveCursorToSection(sectionName);
    }

    /**
     * Открытие раздела из категории каталога
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param itemName имя раздела в категории каталога
     */
    @Step("Переходим в раздел {itemName}")
    public static void openSectionItem(String itemName) {
        MainPageMarket mainPageMarket = new MainPageMarket(driver);
        mainPageMarket.openSectionItem(itemName);

    }

    /**
     * Установка значений диапазона фильтра
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param filterName имя фильтра
     * @param minValue   минимальное значение диапазона
     * @param maxValue   максимальное значение диапазона
     */
    @Step("Установка фильтру {filterName} значений: от {minValue} до {maxValue} ")
    public static void setRangeFilter(String filterName, int minValue, int maxValue) {
        CatalogListPage catalogListPage = new CatalogListPage(driver);
        catalogListPage.setRangeFilter(filterName, minValue, maxValue);
    }

    /**
     * Установка значений фильтра с возможностью выбора нескольких вариантов значений
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param filterName имя фильтра
     * @param values     список значений фильтра
     */
    @Step("Установка фильтру {filterName} значений: {values} ")
    public static void setManyValueFilter(String filterName, List<String> values) {
        CatalogListPage catalogListPage = new CatalogListPage(driver);
        catalogListPage.setManyValueFilter(filterName, values);
    }

    /**
     * Проверка что на странице отображается товаров больше чем переданное значение
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param number ожидаемое количество элементов на странице
     */
    @Step("Проверяем что на странице более {number} элементов")
    public static void numberItemsOnPageMoreThan(int number) {
        CatalogListPage catalogListPage = new CatalogListPage(driver);
        int actualSize = catalogListPage.getProductOnPage().size();
        Assertions.assertTrue(actualSize > number,
                "Ожидали что количестов элементов на странице более " + number +
                        ", количество элементов на странице: " + actualSize);
    }

    /**
     * Переход на страницу по номеру
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param pageNumber номер страницы
     */
    @Step("Переход на страницу №{pageNumber}")
    public static void goToPage(int pageNumber) {
        CatalogListPage catalogListPage = new CatalogListPage(driver);
        catalogListPage.goToPage(pageNumber);
    }

    /**
     * Поиск в каталоге по тексту
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param searchText искомый текст
     */
    @Step("Вводим в поисковую строку '{searchText}' и нажимаем поиск")
    public static void search(String searchText) {
        CatalogListPage catalogListPage = new CatalogListPage(driver);
        catalogListPage.search(searchText);
    }

    /**
     * Получение товара со страницы поиска по номеру товара по порядку
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param number номер по порядку
     * @return экземпляр класса {@link Product} содержащий информацию о товаре
     */
    @Step("Получаем {number} по порядку товар на странице")
    public static Product getProductInOrderOnPage(int number) {
        CatalogListPage catalogListPage = new CatalogListPage(driver);
        List<Product> productOnPage = catalogListPage.getProductOnPage();
        Assertions.assertTrue(productOnPage.size() >= number,
                "Ожидали что количестов элементов на странице более " + number +
                        ", количество элементов на странице: " + productOnPage.size());
        return productOnPage.get(number - 1);
    }

    /**
     * Проверка наличия конкретного товара на странице.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param product товар
     */
    @Step("Проверяем наличие '{product.header}' на странице")
    public static void containsOnPage(Product product) {
        CatalogListPage catalogListPage = new CatalogListPage(driver);
        List<Product> productOnPage = catalogListPage.getProductOnPage();
        Assertions.assertTrue(productOnPage.contains(product), "Ожидали наличие товара с наименованием '" +
                product.getHeader() + "', товар отсутствует на странице.");
    }

    /**
     * Установка значений фильтров
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param filters список фильтров
     * @see Filter
     */
    @Step("Установка значений для фильтров {filters}")
    public static void setFilters(List<Filter> filters) {
        filters.forEach(f -> {
            switch (f.getType()) {
                case RANGE: {
                    RangeFilter rfilter = (RangeFilter) f;
                    setRangeFilter(rfilter.getFilterName(), rfilter.getMinValue(), rfilter.getMaxValue());
                }
                break;
                case CHECKBOX: {
                    CheckBoxFilter chfilter = (CheckBoxFilter) f;
                    setManyValueFilter(chfilter.getFilterName(), chfilter.getValues());
                }
                break;
            }
        });
    }

    /**
     * Проверка соответствия всех товаров переданным фильтрам
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param filters список фильтров
     * @see Filter
     */
    @Step("Проверка соответствия товаров фильтрам")
    public static void isAllProductsMatchFilters(List<Filter> filters) {
        final String parentUUID = Allure.getLifecycle().getCurrentTestCaseOrStep().get();
        CatalogListPage catalogListPage = new CatalogListPage(driver);
        LocalTime startTime = LocalTime.now();
        do {
            List<Product> productOnPage = catalogListPage.getProductOnPage();
            for (Product product : productOnPage) {
                UUID uuid = UUID.randomUUID();
                Allure.getLifecycle().startStep(parentUUID, uuid.toString(), new StepResult()
                        .setName(format("Тестируем %s", product.getHeader()))
                        .setStatus(Status.PASSED)
                );
                filters.forEach(f -> {
                    String currentUUID = UUID.randomUUID().toString();
                    StepResult stepResult = new StepResult()
                            .setName(format("Проверка соответствия фильтру %s", f.getFilterName()))
                            .setStatus(Status.PASSED);
                    Allure.getLifecycle().startStep(uuid.toString(), currentUUID, stepResult);
                    boolean condition = f.isMatches(product);
                    if (!condition) {
                        WebElement currentProduct = catalogListPage.getElementByProduct(product);
                        Screenshoter.getScreen(driver, currentProduct);
                        Allure.getLifecycle().updateStep(uuid.toString(), mainStepResult -> mainStepResult.setStatus(Status.FAILED));
                    }
                    Assertions.assertTrue(condition,
                            format("Товар '%s' не соответствует фильтру %s\n", product.getHeader(), f));
                    Allure.getLifecycle().stopStep(currentUUID);
                });
                Allure.getLifecycle().stopStep(uuid.toString());
            }
        } while (catalogListPage.goToNextPage() && ChronoUnit.MINUTES.between(startTime, LocalTime.now()) < Properties.appProperties.getTimeoutNextPageLoop());
    }
}
