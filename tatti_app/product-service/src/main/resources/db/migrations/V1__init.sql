CREATE TABLE categories (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            name VARCHAR(255) NOT NULL
);
CREATE TABLE products (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          name TEXT NOT NULL,
                          description TEXT,
                          price DOUBLE PRECISION NOT NULL,
                          cpfc TEXT,
                          photo TEXT,
                          weight DOUBLE PRECISION,
                          category_id UUID NOT NULL, --needs fk --
                          created_at TIMESTAMP NOT NULL,
                          updated_at TIMESTAMP NOT NULL,
                          created_by UUID,
                          creator_role TEXT,
                          is_customizable BOOLEAN NOT NULL,
                          status TEXT
);


CREATE TABLE shop_products (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               shop_id UUID NOT NULL,
                               product_id UUID NOT NULL, --needs fk --?
                               available_now BOOLEAN NOT NULL,
                               custom_price DOUBLE PRECISION,
                               delivery_terms TEXT,
                               added_at TIMESTAMP
);



