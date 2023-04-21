package com.vilkovandrew.helpers;

import com.vilkovandrew.yandex.market.helpers.CheckBoxFilter;
import com.vilkovandrew.yandex.market.helpers.Filter;
import com.vilkovandrew.yandex.market.helpers.RangeFilter;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Класс предоставления данных для тестов.
 *
 * @author Вилков Андрей
 */
public class TestDataProvider {

    /**
     * Поставщик тестовых данных для yaMarketProductTest.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return массив тестовых данных
     */
    public static Stream<Arguments> yaMarketProductTest() {
        List<Filter> notebookFilters = new ArrayList<>();
        notebookFilters.add(new RangeFilter("Цена", 10000, 90000));
        notebookFilters.add(new CheckBoxFilter("Производитель", "HUAWEI", "Lenovo"));

        List<Filter> smartFilters = new ArrayList<>();
        smartFilters.add(new RangeFilter("Цена", 10000, 90000));
        smartFilters.add(new CheckBoxFilter("Производитель", "Apple"));
        return Stream.of(
                Arguments.of(Properties.appProperties.getYaMarketUrl(), "Яндекс Маркет", "Ноутбуки и компьютеры", "Ноутбуки", notebookFilters),
                Arguments.of(Properties.appProperties.getYaMarketUrl(), "Яндекс Маркет", "Электроника", "Смартфоны", smartFilters)
        );
    }
}
