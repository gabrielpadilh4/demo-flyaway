package io.github.gabrielpadilh4.demoflyaway.order;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Order(
                resultSet.getInt("id"),
                resultSet.getString("internal_code"),
                resultSet.getString("store_name"),
                List.of(),
                LocalDate.parse(resultSet.getString("order_date"))
        );
    }
}
