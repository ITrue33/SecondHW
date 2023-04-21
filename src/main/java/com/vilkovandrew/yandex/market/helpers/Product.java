package com.vilkovandrew.yandex.market.helpers;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс представляющий товар.
 *
 * @author Vilkov Andrew
 */
public class Product {
    /**
     * Заголовок товара
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private final String header;
    /**
     * Ссылка на товар
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private final String link;
    /**
     * Цена товара
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private final int price;
    /**
     * Паттерн для отделение ссылки на товар от параметров
     * <p>
     * Автор: Вилков Андрей
     * </p>
     */
    private static final Pattern URL_PATTERN = Pattern.compile("^(.[^?]+)[?]?.*$");

    /**
     * Конструктор класса {@link Product}.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param header заголовок фильтра
     * @param link   ссылка на товар
     * @param price  цена на товар
     */
    public Product(String header, String link, int price) {
        this.header = header;
        this.link = link;
        this.price = price;
    }

    /**
     * Получение заголовка товара.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return заголовок товара
     */
    public String getHeader() {
        return header;
    }

    /**
     * Получение ссылки на товар.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return ссылка на товар
     */
    public String getLink() {
        return link;
    }

    /**
     * Получение строкового представления товара.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return строковое представление товара.
     */
    @Override
    public String toString() {
        return "Product{" +
                "header='" + header + '\'' +
                ", link='" + link + '\'' +
                ", price=" + price +
                '}';
    }

    /**
     * Получение цены товара.
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return цена товара
     */
    public int getPrice() {
        return price;
    }

    /**
     * Метод сравнения товаров
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @param o эталонный объект для сравнения.
     * @return true - если этот товар совпадает с эталонным объектом, в противном случае false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        Matcher matcher = URL_PATTERN.matcher(getLink());
        String currentUrl = matcher.find() ? matcher.group(1) : getLink();
        matcher = URL_PATTERN.matcher(product.getLink());
        String targetUrl = matcher.find() ? matcher.group(1) : product.getLink();
        return Objects.equals(getHeader(), product.getHeader()) && Objects.equals(currentUrl, targetUrl);
    }

    /**
     * Возвращает значение хэш-кода для товара
     * <p>
     * Автор: Вилков Андрей
     * </p>
     *
     * @return значение хеш-кода для этого товара
     */
    @Override
    public int hashCode() {
        Matcher matcher = URL_PATTERN.matcher(getLink());
        String currentUrl = matcher.find() ? matcher.group(1) : getLink();
        return Objects.hash(getHeader(), currentUrl);
    }
}
