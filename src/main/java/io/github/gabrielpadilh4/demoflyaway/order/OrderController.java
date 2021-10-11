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
    public Order getOrderId(@PathVariable("id") Integer id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/search/{internalCode}")
    public Order getOrderByInternalCode(@PathVariable("internalCode") String internalCode) {
        return orderService.getOrderUsingInternalCode(internalCode);
    }

    @GetMapping("/details/{id}")
    public Order getOrderWithLines(@PathVariable("id") Integer id) {
        return orderService.getOrderWithLineItems(id);
    }

    @PostMapping
    public void addOrder(@RequestBody Order order) {
        orderService.addNewOrder(order);
    }

    @DeleteMapping("{id}")
    public void deleteOrder(@PathVariable("id") Integer id) {
        orderService.deleteOrder(id);
    }

    // TODO - Update order

}
