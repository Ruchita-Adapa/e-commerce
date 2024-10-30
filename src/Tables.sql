-> create a table which have id,name,email,address

CREATE TABLE Ecommerce (
    ID varchar(255) PRIMARY KEY,
    name varchar(255),
    email varchar(255),
    address varchar(255)
)

CREATE TABLE Product (
    id VARCHAR(100) PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    cost DECIMAL(10, 2) NOT NULL,
    available_count INT NOT NULL,
    created_at bigint,
    updated_at bigint
);

CREATE TABLE orders (
    order_id VARCHAR(100) PRIMARY KEY
    transaction_id VARCHAR(100),
    product_id VARCHAR(100),
    quantity BIGINT,
    user_id VARCHAR(100),
    status VARCHAR(100) NOT NULL,
    placed_from VARCHAR(100),
    created_at BIGINT,
    updated_at BIGINT,
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id)
);

#Insert an order
#Update an order by Id

CREATE TABLE transactions (
    transaction_id VARCHAR(100) PRIMARY KEY,
    user_id VARCHAR(100),
    cost DECIMAL(10, 2) NOT NULL,
    status VARCHAR(100) NOT NULL,
    created_at BIGINT,
    updated_at BIGINT,
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

