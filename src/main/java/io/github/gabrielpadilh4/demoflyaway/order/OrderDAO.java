package io.github.gabrielpadilh4.demoflyaway.order;

import io.github.gabrielpadilh4.demoflyaway.product.Product;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {
    List<Order> selectOrders();

    int insertOrder(Order order);

    int deleteOrder(int id);

    Optional<Order> selectOrderById(int id);

    // TODO - update order
}
