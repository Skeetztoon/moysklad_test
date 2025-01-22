create table sales (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    item_id INTEGER NOT NULL REFERENCES items(id) ON DELETE CASCADE,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    price DECIMAL(10, 2) CHECK (price >= 0)
);

CREATE SEQUENCE sale_id START WITH 1 INCREMENT BY 1;
ALTER TABLE sales ALTER COLUMN id SET DEFAULT nextval('sale_id');