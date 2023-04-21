package com.vilkovandrew.yandex.market.helpers;

import java.util.Arrays;
import java.util.List;

/**
 * Класс представляющий фильтр с несколькими возможными вариантами значений.
 *
 * @author Vilkov Andrew
 */
public class CheckBoxFilter extends Filter {
    /**
     * Список параметров фильтра
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private final List<String> values;

    /**
     * Конструктор класса {@link CheckBoxFilter}.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param filterName имя фильтра
     * @param values     список параметров фильтра
     */
    public CheckBoxFilter(String filterName, String... values) {
        super(FilterType.CHECKBOX, filterName);
        this.values = Arrays.asList(values);
    }

    /**
     * Получение списка параметров фильтра.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return список параметров фильтра
     */
    public List<String> getValues() {
        return values;
    }

    /**
     * Проверка соответствия товара фильтру.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param product проверяемый товар
     * @return true если товар соответствует фильтру, в противном случае false
     */
    @Override
    public boolean isMatches(Product product) {
        for (String value : values) {
            if (product.getHeader().toLowerCase().contains(value.toLowerCase())) return true;
        }
        return false;
    }

    /**
     * Получение строкового представления фильтра.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return строковое представление фильтра.
     */
    @Override
    public String toString() {
        return "Filter{" +
                "filterName='" + getFilterName() + '\'' +
                ", values=" + String.join(",", values) + '}';
    }
}
