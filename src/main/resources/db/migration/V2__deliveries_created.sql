create table deliveries (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    item_id INTEGER NOT NULL REFERENCES items(id) ON DELETE CASCADE,
    quantity INTEGER NOT NULL CHECK (quantity > 0)
);

CREATE SEQUENCE delivery_id START WITH 1 INCREMENT BY 1;
ALTER TABLE deliveries ALTER COLUMN id SET DEFAULT nextval('delivery_id');