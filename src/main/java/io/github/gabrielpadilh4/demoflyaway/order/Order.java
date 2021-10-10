package io.github.gabrielpadilh4.demoflyaway.order;

import io.github.gabrielpadilh4.demoflyaway.product.Product;

import java.time.LocalDate;
import java.util.List;

public record Order(Integer id,
                    String internalCode,
                    String storeName,
                    List<Product> products,
                    LocalDate orderDate) {

    public Order internalCode(String internalCode) {
        return new Order(id(), internalCode, storeName(), products(), orderDate());
    }
}
