package io.github.gabrielpadilh4.demoflyaway.LineItem;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LineItemRowMapper implements RowMapper<LineItem> {
    @Override
    public LineItem mapRow(ResultSet resultSet, int i) throws SQLException {
        return new LineItem(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("quantity")
        );
    }
}
