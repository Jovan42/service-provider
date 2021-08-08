START TRANSACTION;

CREATE TABLE account (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time` datetime,
    `deleted` bit,
    `last_modified` datetime,
    `account_number` varchar(255),
    `status` varchar(255),
    `user_id` varchar(255),
    `balance` double DEFAULT 0
);

CREATE TABLE transaction (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time` datetime,
    `deleted` bit,
    `last_modified` datetime,
    `amount`double,
    `order_id` varchar(255),
    `account_id` bigint,
    FOREIGN KEY (account_id) REFERENCES account(id)
);
COMMIT;
