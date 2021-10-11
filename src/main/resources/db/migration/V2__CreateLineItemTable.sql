CREATE TABLE order_lines (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT,
    name TEXT NOT NULL,
    quantity INT,
    CONSTRAINT fk_order
          FOREIGN KEY(order_id)
    	  REFERENCES orders(id)
)