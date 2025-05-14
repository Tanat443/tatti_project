CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       email VARCHAR(255) NOT NULL,
                       username VARCHAR(255) NOT NULL,
                       role VARCHAR(100) NOT NULL
);
CREATE TABLE shop_owners (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             user_id UUID NOT NULL,
                             shop_name VARCHAR(255) NOT NULL,
                             location VARCHAR(255),
                             rating FLOAT DEFAULT 0,
                             description TEXT,
                             photo_url VARCHAR(500),
                             verified BOOLEAN DEFAULT FALSE,

                             CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
