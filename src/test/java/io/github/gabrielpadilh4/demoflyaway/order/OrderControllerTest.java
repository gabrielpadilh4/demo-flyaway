package io.github.gabrielpadilh4.demoflyaway.order;

import io.github.gabrielpadilh4.demoflyaway.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    private OrderController underTest;

    @BeforeEach
    void setUp(){
        underTest = new OrderController(orderService);
    }
}
