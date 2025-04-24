CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    order_type VARCHAR(50),
    description TEXT,
    price NUMERIC,
    status VARCHAR(50),
    customer_id BIGINT,
    assigned_confectioner_id BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    location TEXT,
    push_notification_deadline TIMESTAMP
);
