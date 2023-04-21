package com.vilkovandrew;

import com.vilkovandrew.yandex.market.helpers.Filter;
import com.vilkovandrew.yandex.market.helpers.Product;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static com.vilkovandrew.steps.StepsAll.*;

/**
 * Класс содержащий тесты.
 *
 * @author Вилков Андрей
 * @see BaseTest
 */
public class Tests extends BaseTest {

    /**
     * Тест для Яндекс Маркета
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param url            URL который будет открыть
     * @param title          ожидаемый заголовок страницы или часть заголовка
     * @param catalogSection имя категории каталога
     * @param itemSection    имя раздела категории
     * @param filters        фильтры
     */
    @Feature("Проверка YandexMarket")
    @DisplayName("Проверка работы фильтров в каталоге")
    @ParameterizedTest(name = "{displayName}")
    @MethodSource("com.vilkovandrew.helpers.TestDataProvider#yaMarketProductTest")
    void yaMarketProductTest(String url, String title, String catalogSection, String itemSection, List<Filter> filters) {
        openSite(url, title, driver);
        openCatalog();
        moveCursorToSection(catalogSection);
        openSectionItem(itemSection);
        setFilters(filters);
        numberItemsOnPageMoreThan(12);
        isAllProductsMatchFilters(filters);
        goToPage(1);
        Product firstProduct = getProductInOrderOnPage(1);
        search(firstProduct.getHeader());
        containsOnPage(firstProduct);

    }

}
