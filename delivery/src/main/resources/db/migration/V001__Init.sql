START TRANSACTION;

CREATE TABLE contact_information (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time` datetime,
    `last_modified` datetime,
    `deleted` bit,
    `email_address` varchar(255),
    `phone_number` varchar(255)
);

CREATE TABLE delivery_man (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time` datetime,
    `last_modified` datetime,
    `deleted` bit,
    `first_name` varchar(255),
    `last_name` varchar(255),
    `service_provider_id` bigint,
    `status` int,
    `contact_information_id` bigint default 0,
    FOREIGN KEY (contact_information_id) REFERENCES contact_information(id)

);

CREATE TABLE delivery (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time` datetime,
    `last_modified` datetime,
    `deleted` bit,
    `status` int,
    `delivery_man_id` bigint,
    `service_provider_id` bigint,
    `preparation_time_in_minutes` bigint default 0,
    FOREIGN KEY (delivery_man_id) REFERENCES delivery_man(id)
);

COMMIT;
