package io.github.gabrielpadilh4.demoflyaway.LineItem;

import io.github.gabrielpadilh4.demoflyaway.order.Order;

import java.util.List;

public interface LineItemDAO {
    List<LineItem> selectItemLines(Order order);

    int insertLineItems(Order order);

    int deleteLineItem(LineItem lineItem);
}
