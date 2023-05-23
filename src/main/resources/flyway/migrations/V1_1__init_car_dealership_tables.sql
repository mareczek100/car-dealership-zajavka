CREATE TABLE salesman
(
    salesman_id SERIAL      NOT NULL,
    name        VARCHAR(32) NOT NULL,
    surname     VARCHAR(32) NOT NULL,
    pesel       VARCHAR(20) 	NOT NULL,
    PRIMARY KEY (salesman_id),
    UNIQUE (pesel)
);
CREATE TABLE address
(
    address_id  SERIAL      NOT NULL,
    country     VARCHAR(32) NOT NULL,
    city        VARCHAR(32) NOT NULL,
    postal_code VARCHAR(32) NOT NULL,
    street      VARCHAR(32) NOT NULL,
    building_flat_number     VARCHAR(32),
    PRIMARY KEY (address_id)
);
CREATE TABLE customer
(
    customer_id SERIAL      NOT NULL,
    name        VARCHAR(32) NOT NULL,
    surname     VARCHAR(32) NOT NULL,
    phone       VARCHAR(20) NOT NULL,
    email       VARCHAR(32) NOT NULL,
    address_id  INT         NOT NULL,
    PRIMARY KEY (customer_id),
    UNIQUE (email),
    CONSTRAINT fk_customer_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id)
);
CREATE TABLE car_to_sell
(
    car_to_sell_id   SERIAL          NOT NULL,
    vin             VARCHAR(20)     NOT NULL,
    brand           VARCHAR(32)     NOT NULL,
    model           VARCHAR(32)     NOT NULL,
    year            SMALLINT        NOT NULL,
    color           VARCHAR(32) 	   NOT NULL,
    price           NUMERIC(19, 2)  NOT NULL,
    PRIMARY KEY (car_to_sell_id),
    UNIQUE (vin)
);
CREATE TABLE car_to_sell_temp_storage
(
    car_to_sell_temp_storage_id   SERIAL          NOT NULL,
    vin                           VARCHAR(20)     NOT NULL,
    brand                         VARCHAR(32)     NOT NULL,
    model                         VARCHAR(32)     NOT NULL,
    year                          SMALLINT        NOT NULL,
    color                         VARCHAR(32) 	  NOT NULL,
    price                         NUMERIC(19, 2)  NOT NULL,
    PRIMARY KEY (car_to_sell_temp_storage_id),
    UNIQUE (vin)
);
CREATE TABLE car_to_service
(
    car_to_service_id SERIAL    NOT NULL,
    vin    VARCHAR(20)          NOT NULL,
    brand  VARCHAR(32)          NOT NULL,
    model  VARCHAR(32)          NOT NULL,
    year   SMALLINT             NOT NULL,
    PRIMARY KEY (car_to_service_id),
    UNIQUE (vin)
);
CREATE TABLE service
(
    service_id   SERIAL         NOT NULL,
    service_code VARCHAR(32)    NOT NULL,
    description  TEXT		    NOT NULL,
    price        NUMERIC(19, 2) NOT NULL,
    PRIMARY KEY (service_id),
    UNIQUE (service_code)
);
CREATE TABLE part
(
    part_id       SERIAL            NOT NULL,
    serial_number VARCHAR(32)       NOT NULL,
    description   VARCHAR(64)       NOT NULL,
    price         NUMERIC(19, 2)    NOT NULL,
    PRIMARY KEY (part_id),
    UNIQUE (serial_number)
);
CREATE TABLE mechanic
(
    mechanic_id SERIAL      NOT NULL,
    name        VARCHAR(32) NOT NULL,
    surname     VARCHAR(32) NOT NULL,
    pesel       VARCHAR(20)	NOT NULL,
    PRIMARY KEY (mechanic_id),
    UNIQUE (pesel)
);
CREATE TABLE invoice
(
    invoice_id     SERIAL                   NOT NULL,
    invoice_number VARCHAR(40)              NOT NULL,
    date_time      TIMESTAMP WITH TIME ZONE NOT NULL,
    car_to_sell_id  INT                      NOT NULL,
    customer_id    INT                      NOT NULL,
    salesman_id    INT                      NOT NULL,
    PRIMARY KEY (invoice_id),
    UNIQUE (invoice_number),
    CONSTRAINT fk_invoice_car
        FOREIGN KEY (car_to_sell_id)
            REFERENCES car_to_sell (car_to_sell_id),
    CONSTRAINT fk_invoice_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id),
    CONSTRAINT fk_invoice_salesman
        FOREIGN KEY (salesman_id)
            REFERENCES salesman (salesman_id)
);
CREATE TABLE car_service_request
(
    car_service_request_id      SERIAL                      NOT NULL,
    car_service_request_number  VARCHAR(60)                 NOT NULL,
    received_date_time          TIMESTAMP WITH TIME ZONE    NOT NULL,
    completed_date_time         TIMESTAMP WITH TIME ZONE,
    customer_comment            TEXT                        NOT NULL,
    customer_id                 INT                         NOT NULL,
    car_to_service_id           INT                         NOT NULL,
    PRIMARY KEY (car_service_request_id),
    UNIQUE (car_service_request_number),
    CONSTRAINT fk_car_service_request_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id),
    CONSTRAINT fk_car_service_request_car
        FOREIGN KEY (car_to_service_id)
            REFERENCES car_to_service (car_to_service_id)
);
CREATE TABLE car_service_parts
(
    car_service_parts_id         SERIAL      NOT NULL,
    quantity                     SMALLINT,
    car_service_request_id       INT         NOT NULL,
    part_id                      INT,
    PRIMARY KEY (car_service_parts_id),
    CONSTRAINT fk_car_service_parts_service_request
        FOREIGN KEY (car_service_request_id)
            REFERENCES car_service_request (car_service_request_id),
    CONSTRAINT fk_car_service_parts_part
        FOREIGN KEY (part_id)
            REFERENCES part (part_id)
);
CREATE TABLE car_service_handling
(
    car_service_handling_id 	SERIAL      NOT NULL,
    hours                   SMALLINT    NOT NULL,
    comment                 TEXT		  NOT NULL,
    car_service_request_id  INT         NOT NULL,
    mechanic_id             INT         NOT NULL,
    service_id              INT         NOT NULL,
    PRIMARY KEY (car_service_handling_id),
    CONSTRAINT fk_car_service_handling_car_service_request
        FOREIGN KEY (car_service_request_id)
            REFERENCES car_service_request (car_service_request_id),
    CONSTRAINT fk_car_service_handling_mechanic
        FOREIGN KEY (mechanic_id)
            REFERENCES mechanic (mechanic_id),
    CONSTRAINT fk_car_service_handling_service
        FOREIGN KEY (service_id)
            REFERENCES service (service_id)
);