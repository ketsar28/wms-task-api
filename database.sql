SHOW DATABASES;

CREATE DATABASE new_wms_api_db;
USE new_wms_api_db;

SHOW TABLES;

DESC m_branch;
SELECT * FROM m_branch;
DELETE FROM m_branch;
DROP TABLE m_branch;

DESC m_product;
SELECT * FROM m_product;
DELETE FROM m_product;
DROP TABLE  m_product;

SELECT * FROM m_products WHERE price BETWEEN 1000 AND 10000;
SELECT * FROM m_products WHERE price <= 100000;

DESC m_transactions;
SELECT * FROM m_transactions;
DELETE FROM m_transactions;

DESC m_bill_detail;
SELECT * FROM  m_order_detail;
DELETE FROM m_bill_detail;
DROP TABLE m_bill_detail;

DESC m_sequence_number;
SELECT * FROM m_sequence_number;
DELETE FROM m_sequence_number;



