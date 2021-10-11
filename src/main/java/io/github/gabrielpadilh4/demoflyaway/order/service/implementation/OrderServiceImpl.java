package io.github.gabrielpadilh4.demoflyaway.order.service.implementation;

import com.google.common.hash.Hashing;
import io.github.gabrielpadilh4.demoflyaway.LineItem.LineItem;
import io.github.gabrielpadilh4.demoflyaway.LineItem.LineItemDataAccessService;
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
    private final LineItemDataAccessService lineItemDataAccessService;

    @Value("${internalConfig.orderAcronym}")
    private String ORDER_ACRONYM;

    public OrderServiceImpl(OrderDAO orderDAO, LineItemDataAccessService lineItemDataAccessService) {
        this.orderDAO = orderDAO;
        this.lineItemDataAccessService = lineItemDataAccessService;
    }

    @Override
    public List<Order> getOrders() {
        return orderDAO.selectOrders();
    }

    @Override
    public Order getOrder(int id) {
        return orderDAO.selectOrderById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Order with id %s not found", id)));
    }

    @Override
    public Order getOrderUsingInternalCode(String internalCode) {
        return orderDAO.selectOrderByInternalCode(internalCode).orElseThrow(() -> new NotFoundException(String.format("Order with internal code %s not found", internalCode)));
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

        Order orderInserted = orderDAO.selectOrderByInternalCode(orderToInsert.internalCode()).orElseThrow(
                () -> new NotFoundException(String.format("Order with internal code %s not found", orderToInsert.internalCode()))
        );

        lineItemDataAccessService.insertLineItems(orderInserted.lineItems(orderToInsert.lineItems()));

    }

    @Override
    public void deleteOrder(int id) {
        Optional<Order> orders = orderDAO.selectOrderById(id);

        orders.ifPresentOrElse(order -> {

            Order orderToDelete = order.lineItems(lineItemDataAccessService.selectItemLines(order));

            for (LineItem line : orderToDelete.lineItems()
            ) {
                lineItemDataAccessService.deleteLineItem(line);
            }

            int result = orderDAO.deleteOrder(id);

            if (result != 1) {
                throw new IllegalStateException("Could not delete order");
            }
        }, () -> {
            throw new NotFoundException(String.format("Order with id %s not found", id));
        });
    }

    @Override
    public Order getOrderWithLineItems(int id) {

        Order orderToGet = getOrder(id);

        return orderToGet.lineItems(lineItemDataAccessService.selectItemLines(orderToGet));
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
