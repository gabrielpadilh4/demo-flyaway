package io.github.gabrielpadilh4.demoflyaway.order;

import io.github.gabrielpadilh4.demoflyaway.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> listOrders() {
        return orderService.getOrders();
    }

    @GetMapping("{id}")
    public Order getOrderId(@RequestParam("id") Integer id) {
        return orderService.getOrder(id);
    }

    @PostMapping
    public void addOrder(@RequestBody Order order) {
        orderService.addNewOrder(order);
    }

    @DeleteMapping("{id}")
    public void deleteOrder(@RequestParam("id") Integer id) {
        orderService.deleteOrder(id);
    }

    // TODO - Update order

}
