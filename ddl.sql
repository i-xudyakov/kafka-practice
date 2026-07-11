CREATE DATABASE IF NOT EXISTS demo;

USE demo;

CREATE TABLE users_kafka
(
    id Int64,
    name String,
    age Int32
)
ENGINE = Kafka
SETTINGS
    kafka_broker_list = 'kafka:29092',
    kafka_topic_list = 'users',
    kafka_group_name = 'clickhouse-group',
    kafka_format = 'AvroConfluent',
    format_avro_schema_registry_url = 'http://schema-registry:8081',
    kafka_num_consumers = 1;

CREATE TABLE users
(
    id Int64,
    name String,
    age Int32
)
ENGINE = MergeTree
ORDER BY id;

CREATE MATERIALIZED VIEW users_mv
TO users
AS
SELECT *
FROM users_kafka;