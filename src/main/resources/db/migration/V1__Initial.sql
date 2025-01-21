CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT CHECK (length(description) <= 4096),
    price DECIMAL(10, 2) CHECK (price >= 0) DEFAULT 0,
    instock BOOLEAN DEFAULT FALSE
);

CREATE SEQUENCE item_id START WITH 1 INCREMENT BY 1;
ALTER TABLE items ALTER COLUMN id SET DEFAULT nextval('item_id');