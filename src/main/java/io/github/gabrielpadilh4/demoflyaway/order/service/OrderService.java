package io.github.gabrielpadilh4.demoflyaway.order.service;

import io.github.gabrielpadilh4.demoflyaway.order.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Order> getOrders();

    void addNewOrder(Order order);

    void deleteOrder(int id);

    Order getOrder(int id);
}
