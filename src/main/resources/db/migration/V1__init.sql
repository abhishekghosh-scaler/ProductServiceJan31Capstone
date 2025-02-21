CREATE TABLE category
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NULL,
    created_at    datetime NULL,
    last_modified datetime NULL,
    is_deleted    BIT(1) NOT NULL,
    `description` VARCHAR(255) NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE product
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NULL,
    created_at    datetime NULL,
    last_modified datetime NULL,
    is_deleted    BIT(1) NOT NULL,
    `description` VARCHAR(255) NULL,
    image_url     VARCHAR(255) NULL,
    price DOUBLE NOT NULL,
    category_id   BIGINT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);