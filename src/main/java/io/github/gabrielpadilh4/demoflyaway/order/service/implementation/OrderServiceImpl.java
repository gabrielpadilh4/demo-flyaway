package io.github.gabrielpadilh4.demoflyaway.order.service.implementation;

import com.google.common.hash.Hashing;
import io.github.gabrielpadilh4.demoflyaway.exception.NotFoundException;
import io.github.gabrielpadilh4.demoflyaway.order.Order;
import io.github.gabrielpadilh4.demoflyaway.order.OrderDAO;
import io.github.gabrielpadilh4.demoflyaway.order.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    @Value("${internalConfig.orderAcronym}")
    private String ORDER_ACRONYM;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public List<Order> getOrders() {
        return orderDAO.selectOrders();
    }

    @Override
    public void addNewOrder(Order order) {
        // TODO - Check if order exists

        String internalCode = generateInternalCode();

        Order orderToInsert = order.internalCode(ORDER_ACRONYM + internalCode);

        int result = orderDAO.insertOrder(orderToInsert);
        if (result != 1) {
            throw new IllegalStateException("Something went wrong");
        }
    }

    @Override
    public void deleteOrder(int id) {
        Optional<Order> orders = orderDAO.selectOrderById(id);

        orders.ifPresentOrElse(order -> {
            int result = orderDAO.deleteOrder(id);

            if (result != 1) {
                throw new IllegalStateException("Could not delete order");
            }
        }, () -> {
            throw new NotFoundException(String.format("Order with id %s not found", id));
        });
    }

    @Override
    public Order getOrder(int id) {
        return orderDAO.selectOrderById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Order with id %s not found", id)));
    }

    private String generateInternalCode() {
        LocalDateTime time = LocalDateTime.now();

        return Hashing
                .murmur3_32_fixed()
                .hashString(ORDER_ACRONYM.concat(time.toString()), StandardCharsets.UTF_8)
                .toString()
                .toUpperCase();
    }
}
