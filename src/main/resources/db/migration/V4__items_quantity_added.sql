ALTER TABLE items
ADD COLUMN quantity INT NOT NULL DEFAULT 0;

ALTER TABLE items
ADD CONSTRAINT quantity_check CHECK (quantity >= 0);