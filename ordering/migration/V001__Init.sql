START TRANSACTION;
CREATE TABLE orders (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time` datetime,
    `last_modified` datetime,
    `service_provider_id` bigint,
    `deleted` bit,
    `test` int,
    `status` int
);

CREATE TABLE bought_item (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time` datetime,
    `last_modified` datetime,
    `deleted` bit,
    `status` int,
    `order_id` bigint,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);
COMMIT;