package io.github.gabrielpadilh4.demoflyaway.LineItem;

import io.github.gabrielpadilh4.demoflyaway.order.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LineItemDataAccessService implements LineItemDAO {

    private JdbcTemplate jdbcTemplate;

    public LineItemDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LineItem> selectItemLines(Order order) {
        var sql = """
                SELECT id, name, quantity
                FROM order_lines
                WHERE order_id =  ?;
                """;

        return jdbcTemplate.query(sql, new LineItemRowMapper(), order.id());
    }

    @Override
    public int insertLineItems(Order order) {
        var sql = """
                INSERT INTO order_lines(name, quantity, order_id)
                VALUES(?, ?, ?)
                """;

        int i = 0;


        for (LineItem item : order.lineItems()
        ) {
            i = i + jdbcTemplate.update(sql, item.name(), item.quantity(), order.id());
        }

        return i;
    }

    @Override
    public int deleteLineItem(LineItem lineItem) {
        var sql = """
                DELETE FROM order_lines WHERE id = ?;
                """;

        return jdbcTemplate.update(sql, lineItem.id());
    }
}
