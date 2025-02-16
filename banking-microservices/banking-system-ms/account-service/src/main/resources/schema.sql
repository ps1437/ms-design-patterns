CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(50) UNIQUE NOT NULL,
    account_holder_name VARCHAR(100) NOT NULL,
    balance DOUBLE NOT NULL
);
