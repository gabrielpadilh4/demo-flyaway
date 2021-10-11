package io.github.gabrielpadilh4.demoflyaway.order;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderDataAccessService implements OrderDAO {

    private JdbcTemplate jdbcTemplate;

    public OrderDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Order> selectOrders() {
        var sql = """
                SELECT id, internal_code, store_name, order_date
                FROM orders
                LIMIT 100
                """;

        return jdbcTemplate.query(sql, new OrderRowMapper());
    }

    @Override
    public int insertOrder(Order order) {
        var sql = """
                INSERT INTO orders(internal_code, store_name, order_date)
                values(?, ?, ?);
                """;

        return jdbcTemplate.update(sql, order.internalCode(), order.storeName(), order.orderDate());
    }

    @Override
    public int deleteOrder(int id) {
        var sql = """
                DELETE FROM orders WHERE id = ?;
                """;

        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Order> selectOrderById(int id) {
        var sql = """
                SELECT id, internal_code, store_name, order_date
                FROM orders
                WHERE id = ?;
                """;

        return jdbcTemplate.query(sql, new OrderRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Order> selectOrderByInternalCode(String internalCode) {
        var sql = """
                SELECT id, internal_code, store_name, order_date
                FROM orders
                WHERE internal_code = ?;
                """;

        return jdbcTemplate.query(sql, new OrderRowMapper(), internalCode)
                .stream()
                .findFirst();
    }
}
