package io.github.gabrielpadilh4.demoflyaway.order;

import io.github.gabrielpadilh4.demoflyaway.LineItem.LineItem;

import java.time.LocalDate;
import java.util.List;

public record Order(Integer id,
                    String internalCode,
                    String storeName,
                    List<LineItem> lineItems,
                    LocalDate orderDate) {

    public Order internalCode(String internalCode) {
        return new Order(id(), internalCode, storeName(), lineItems(), orderDate());
    }

    public Order lineItems(List<LineItem> lineItems) {
        return new Order(id(), internalCode(), storeName(), lineItems, orderDate());
    }
}
