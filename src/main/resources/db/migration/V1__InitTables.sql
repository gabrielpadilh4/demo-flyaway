CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    internal_code TEXT NOT NULL,
    store_name TEXT NOT NULL,
    order_date DATE NOT NULL,
    unique (internal_code)
)