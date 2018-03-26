CREATE SCHEMA ntwentysix;
ALTER SCHEMA ntwentysix OWNER TO postgres;

SET search_path = ntwentysix, pg_catalog;

DROP TABLE IF EXISTS transaction;

CREATE TABLE transaction (
    transaction_id serial primary key,
    amount decimal,
    transaction_time bigint
);