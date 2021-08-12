CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL           NOT NULL,
    username VARCHAR(50) UNIQUE  NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS products
(
    id          BIGSERIAL        NOT NULL,
    title       VARCHAR(50)      NOT NULL,
    price       DOUBLE PRECISION NOT NULL,
    description VARCHAR(255),
    user_id     BIGINT           NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)

);

CREATE TABLE IF NOT EXISTS rating
(
    user_id    BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    is_like    BOOLEAN DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    CONSTRAINT PK_user_product PRIMARY KEY (user_id, product_id)
);

CREATE TABLE IF NOT EXISTS refresh_token
(
    id          BIGSERIAL    NOT NULL,
    token       VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP    NOT NULL,
    user_id     BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)

);
