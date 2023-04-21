package com.vilkovandrew.yandex.market.pages;

import com.vilkovandrew.helpers.Assertions;
import com.vilkovandrew.yandex.market.helpers.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Класс представляющий Page Object для страницы 'Результаты поиска в каталоге Яндекс Маркет'.
 *
 * @author Vilkov Andrew
 */
public class CatalogListPage {
    /**
     * Локатор для списка товаров.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    public static final By LOCATOR_VIRTUOSO_ITEM_LIST = By.xpath("//*[@data-test-id='virtuoso-item-list']");
    /**
     * Локатор для прелоадера в блоке товаров.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    public static final By LOCATOR_PRELOADER = By.xpath("//*[@id='searchResults']/../div[@data-auto='preloader']");
    /**
     * Локатор для поля ввода.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    public static final By LOCATOR_INPUT_TEXT = By.xpath(".//input[@type='text']");
    /**
     * Локатор для значений в блоке фильтра.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    public static final By LOCATOR_DATA_FILTER_VALUE = By.xpath(".//*[@data-filter-value-id and .//span[text()!='']]/label");

    /**
     * Локатор для блока прокрутки.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    public static final By LOCATOR_VIRTUOSO_SCROLLER = By.xpath("//div[@data-virtuoso-scroller]");

    /**
     * Локатор для блока товара.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    public static final By LOCATOR_PRODUCT_BLOCK = By.xpath("./ancestor::article");

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
     * Локатор для блоков фильтров.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_FILTER_BLOCK = By.xpath("//*[@data-grabber='SearchFilters']//*[@data-filter-id]");

    /**
     * Поле минимального значения фильтра диапазона
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By MIN_VALUE_RANGE_FILTER = By.xpath(".//*[@data-auto=\"filter-range-min\"]");

    /**
     * Поле максимального значения фильтра диапазона
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By MAX_VALUE_RANGE_FILTER = By.xpath(".//*[@data-auto=\"filter-range-max\"]");

    /**
     * Кнопка 'Показать всё'/'Ещё' в блоке фильтра
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By MORE_VALUE_BUTTON = By.xpath(".//button[@aria-expanded]");

    /**
     * Локатор кнопки 'Вперёд', для перехода на следующую страницу.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_NEXT_PAGE_BUTTON = By.xpath("//*[@data-auto='pagination-next']");

    /**
     * Локатор для списка товаров.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_PRODUCT_ITEM = By.xpath("//article//h3[@data-zone-name='title']/a[@href]");

    /**
     * Локатор для цены товаров.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_PRICE_ITEM = By.xpath("./ancestor::article//*[@data-zone-name='price']//span[count(@*)=0]");

    /**
     * Локатор для определения загрузки страницы.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By PAGE_LOAD_LOCATOR = By.id("greed");

    /**
     * Локатор для кнопки найти.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_SEARCH_BUTTON = By.xpath("//button[@data-r='search-button']");

    /**
     * Локатор для поля поиска.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final By LOCATOR_SEARCH_INPUT = By.xpath("//input[@id='header-search']");

    /**
     * Конструктор класса {@link CatalogListPage}.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param driver экземпляр класса {@link WebDriver}
     */
    public CatalogListPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    /**
     * Установка диапазона значений для фильтра
     *
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param filterName имя фильтра
     * @param minValue   минимальное значение диапазона
     * @param maxValue   максимальное значение диапазона
     */
    public void setRangeFilter(String filterName, int minValue, int maxValue) {
        Optional<WebElement> filterBlock = getFilterBlockElement(filterName);

        Assertions.assertTrue(filterBlock.isPresent(), format("Фильтр с именем '%s' не найден.", filterName));

        WebElement filterElement = filterBlock.get();
        WebElement minValueField = filterElement.findElement(MIN_VALUE_RANGE_FILTER);
        WebElement maxValueField = filterElement.findElement(MAX_VALUE_RANGE_FILTER);

        new Actions(driver)
                .scrollToElement(minValueField)
                .sendKeys(minValueField, String.valueOf(minValue))
                .scrollToElement(maxValueField)
                .sendKeys(maxValueField, String.valueOf(maxValue))
                .perform();

        List<WebElement> preloader = driver.findElements(LOCATOR_PRELOADER);
        if (preloader.size() > 0)
            wait.withTimeout(Duration.ofSeconds(20)).until(ExpectedConditions.stalenessOf(preloader.get(0)));
    }

    /**
     * Установка значения для фильтра с несколькими возможными значениями
     *
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param filterName имя фильтра
     * @param values     значения фильтра
     */
    public void setManyValueFilter(String filterName, List<String> values) {
        Optional<WebElement> filterBlock = getFilterBlockElement(filterName);

        Assertions.assertTrue(filterBlock.isPresent(), format("Фильтр с именем '%s' не найден.", filterName));

        WebElement filterElement = filterBlock.get();

        if (filterElement.findElements(MORE_VALUE_BUTTON).size() > 0) {
            WebElement moreButton = filterElement.findElement(MORE_VALUE_BUTTON);
            if (!Boolean.parseBoolean(moreButton.getDomAttribute("aria-expanded")))
                new Actions(driver).scrollToElement(moreButton).click(moreButton).perform();
        }

        if (filterElement.findElements(LOCATOR_INPUT_TEXT).size() > 0) {
            for (String value : values) {
                WebElement inputField = filterElement.findElement(LOCATOR_INPUT_TEXT);
                List<WebElement> presenceValue = filterElement.findElements(LOCATOR_DATA_FILTER_VALUE);

                inputField.clear();
                new Actions(driver).scrollToElement(inputField).sendKeys(inputField, value).perform();

                if (presenceValue.size() > 0)
                    wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(presenceValue.get(0))));

                presenceValue = filterElement.findElements(LOCATOR_DATA_FILTER_VALUE);

                Assertions.assertNotEquals(0, presenceValue.size(),
                        format("Ожидаем что для фильтра '%s' есть значение '%s', совпадений не найдено.", filterName, value));


                Optional<WebElement> filterValue = presenceValue.stream().filter(e -> e.getText().equalsIgnoreCase(value)).findAny();

                Assertions.assertTrue(filterValue.isPresent(), "Пункт фильтра '" + value + "' не найден.");
                WebElement filterValueElement = filterValue.get();
                filterValueElement.click();
                List<WebElement> preloader = driver.findElements(LOCATOR_PRELOADER);
                if (preloader.size() > 0)
                    wait.withTimeout(Duration.ofSeconds(20)).until(ExpectedConditions.stalenessOf(preloader.get(0)));
            }
        }

    }

    /**
     * Вспомогательный метод для получения блока фильтра по его имени
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param filterName имя фильтра
     * @return {@link Optional<WebElement>} возвращает результат поиска блока фильтра по его имени
     */
    private Optional<WebElement> getFilterBlockElement(String filterName) {
        List<WebElement> filtersList = driver.findElements(LOCATOR_FILTER_BLOCK);

        Optional<WebElement> filterBlock = filtersList.stream()
                .filter(e -> {
                    new Actions(driver).scrollToElement(e).perform();
                    String elementName = e.getText().toUpperCase();
                    return elementName.contains(filterName.toUpperCase());
                })
                .findAny();
        return filterBlock;
    }

    /**
     * Получение товаров со страницы поиска.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return {@link List} содержащий товары
     * @see Product
     */
    public List<Product> getProductOnPage() {
        wait.until(ExpectedConditions.presenceOfElementLocated(PAGE_LOAD_LOCATOR));
        new Actions(driver).scrollToElement(driver.findElement(LOCATOR_SEARCH_INPUT)).perform();

        scrollToBottom();

        Duration oldDuration = driver.manage().timeouts().getImplicitWaitTimeout();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        List<WebElement> preloader = driver.findElements(LOCATOR_PRELOADER);
        if (preloader.size() > 0)
            wait.withTimeout(Duration.ofSeconds(20)).until(ExpectedConditions.stalenessOf(preloader.get(0)));
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(LOCATOR_VIRTUOSO_ITEM_LIST, LOCATOR_PRODUCT_ITEM));
        List<Product> result = elements.stream()
                .map(e -> {
                    String header = e.getText();
                    String link = e.getAttribute("href");
                    String priceString = e.findElement(LOCATOR_PRICE_ITEM).getText().replaceAll("\\s", "");
                    int price = Integer.MIN_VALUE;
                    if (Pattern.matches("\\d+", priceString)) price = Integer.parseInt(priceString);
                    return new Product(header, link, price);
                })
                .collect(Collectors.toList());
        driver.manage().timeouts().implicitlyWait(oldDuration);
        return result;
    }

    /**
     * Переход на конкретную страницу по номеру.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param pageNumber номер страницы
     */
    public void goToPage(int pageNumber) {
        String currentUrl = driver.getCurrentUrl();
        Pattern pageCurrentNumberPattern = Pattern.compile(".*(page=\\d+).?");
        Matcher matcher = pageCurrentNumberPattern.matcher(currentUrl);
        if (matcher.find()) {
            currentUrl = currentUrl.replace(matcher.group(1), format("page=%d", pageNumber));
        } else {
            currentUrl = format("%s&page=%d", currentUrl, pageNumber);
        }
        driver.get(currentUrl);
    }

    /**
     * Прокрутка страницы вниз на высоту элемента содержащего список товаров
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    public void scrollToBottom() {
        WebElement scroller = driver.findElement(LOCATOR_VIRTUOSO_SCROLLER);
        String hight = scroller.getAttribute("clientHeight");
        new Actions(driver).scrollFromOrigin(WheelInput.ScrollOrigin.fromElement(scroller), 0, Integer.parseInt(hight)).perform();
    }

    /**
     * Ввод текста в поле поиска на странице и нажатие кнопки 'Найти'.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param searchText текст для ввода в строку поиска
     */
    public void search(String searchText) {
        WebElement searchInput = driver.findElement(LOCATOR_SEARCH_INPUT);
        new Actions(driver).scrollToElement(searchInput).perform();
        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys(searchText);

        driver.findElement(LOCATOR_SEARCH_BUTTON).click();
    }

    /**
     * Переход на следующую страницу.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return true если есть кнопка перехода на следующую страницу и false если кнопки перехода на следующую страницу нет.
     */
    public boolean goToNextPage() {
        Duration oldTimeout = driver.manage().timeouts().getImplicitWaitTimeout();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        if (driver.findElements(LOCATOR_NEXT_PAGE_BUTTON).size() > 0) {
            WebElement nextPageButton = wait.until(ExpectedConditions.visibilityOfElementLocated(LOCATOR_NEXT_PAGE_BUTTON));
            new Actions(driver).scrollToElement(nextPageButton).click(nextPageButton).perform();
            driver.manage().timeouts().implicitlyWait(oldTimeout);
            return true;
        }
        driver.manage().timeouts().implicitlyWait(oldTimeout);
        return false;
    }

    /**
     * Получение корневого {@link WebElement} для конкретного товара
     *
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param product экземпляр товара
     * @return {@link WebElement} указывающий на корневой блок товара
     */
    public WebElement getElementByProduct(Product product) {
        wait.until(ExpectedConditions.presenceOfElementLocated(PAGE_LOAD_LOCATOR));
        new Actions(driver).scrollToElement(driver.findElement(LOCATOR_SEARCH_INPUT)).perform();

        scrollToBottom();

        Duration oldDuration = driver.manage().timeouts().getImplicitWaitTimeout();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        List<WebElement> preloader = driver.findElements(LOCATOR_PRELOADER);
        if (preloader.size() > 0)
            wait.withTimeout(Duration.ofSeconds(20)).until(ExpectedConditions.stalenessOf(preloader.get(0)));
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(LOCATOR_VIRTUOSO_ITEM_LIST, LOCATOR_PRODUCT_ITEM));
        Optional<WebElement> result = elements.stream()
                .filter(e -> e.getText().equalsIgnoreCase(product.getHeader()))
                .findAny();
        driver.manage().timeouts().implicitlyWait(oldDuration);
        if (!result.isPresent())
            Assertions.fail(format("Элемент для продукта '%s' не найден на странице", product.getHeader()));
        return result.get().findElement(LOCATOR_PRODUCT_BLOCK);
    }
}
