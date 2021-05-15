START TRANSACTION;
CREATE TABLE user (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time` datetime,
    `deleted` bit,
    `last_modified` datetime,
    `fist_name` varchar(255),
    `last_name` varchar(255),
    `password` varchar(255),
    `user_name` varchar(255) UNIQUE,
    `email` varchar(255)
);

CREATE TABLE account (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time` datetime,
    `deleted` bit,
    `last_modified` datetime,
    `account_number` varchar(255),
    `status` varchar(255),
    `user_id` bigint,
    `balance` double DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES user(id)
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
