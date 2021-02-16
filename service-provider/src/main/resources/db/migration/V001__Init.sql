START TRANSACTION;
CREATE TABLE organisation
(
    `id`            bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time`  datetime,
    `deleted`       bit,
    `last_modified` datetime,
    `address`       varchar(255),
    `description`   varchar(255),
    `email`         varchar(255),
    `name`          varchar(255),
    `password`      varchar(255)
);

CREATE TABLE service_provider
(
    `id`                     bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time`           datetime,
    `deleted`                bit,
    `last_modified`          datetime,
    `address`                varchar(255),
    `description`            varchar(255),
    `name`                   varchar(255),
    `organisation_id`        bigint,
    `manualApprovalRequired` bit,
    FOREIGN KEY (organisation_id) REFERENCES organisation (id)
);

CREATE TABLE menu_part
(
    `id`                  bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time`        datetime,
    `deleted`             bit,
    `last_modified`       datetime,
    `description`         varchar(255),
    `name`                varchar(255),
    `service_provider_id` bigint,
    FOREIGN KEY (service_provider_id) REFERENCES service_provider (id)
);

CREATE TABLE menu_item
(
    `id`                         bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time`               datetime,
    `deleted`                    bit,
    `last_modified`              datetime,
    `description`                varchar(255),
    `name`                       varchar(255),
    `price`                      double,
    `showSpecification`          bit,
    `showAdditionalRequirements` bit,
    `menu_part_id`               bigint,
    FOREIGN KEY (menu_part_id) REFERENCES menu_part (id)
);

CREATE TABLE specification
(
    `id`            bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time`  datetime,
    `deleted`       bit,
    `last_modified` datetime,
    `description`   varchar(255),
    `name`          varchar(255),
    `menu_item_id`  bigint,
    FOREIGN KEY (menu_item_id) REFERENCES menu_item (id)
);
COMMIT;

CREATE TABLE additional_requirement_part
(
    `id`            bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time`  datetime,
    `deleted`       bit,
    `last_modified` datetime,
    `description`   varchar(255),
    `name`          varchar(255),
    `min`           integer,
    `max`           integer,
    `menu_item_id`  bigint,
    FOREIGN KEY (menu_item_id) REFERENCES menu_item (id)
);

CREATE TABLE additional_requirement_tem
(
    `id`                          bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `created_time`                datetime,
    `deleted`                     bit,
    `last_modified`               datetime,
    `name`                        varchar(255),
    `price`                       double,
    `additional_requirement_part` bigint,
    FOREIGN KEY (additional_requirement_part) REFERENCES additional_requirement_part (id)
);
