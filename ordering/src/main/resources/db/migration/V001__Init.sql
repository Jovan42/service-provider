START TRANSACTION;
CREATE TABLE orders (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time` datetime,
    `last_modified` datetime,
    `service_provider_id` bigint,
    `deleted` bit,
    `test` int,
    `status` int,
    `cancel_reason` varchar(255),
    `user_id` varchar(255),
    `account_id` bigint,
    `preparation_time_in_minutes` bigint default 0,
    `delivered_time` datetime
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
