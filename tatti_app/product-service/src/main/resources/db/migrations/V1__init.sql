CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);
CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          price DOUBLE PRECISION NOT NULL,
                          cpfc VARCHAR(255),
                          photo VARCHAR(255),
                          weight DOUBLE PRECISION,
                          category_id BIGINT REFERENCES categories(id),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);