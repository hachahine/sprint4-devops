CREATE TABLE tb_motorcycles (
    id SERIAL PRIMARY KEY,
    license VARCHAR(50) NOT NULL,
    chassis VARCHAR(100),
    engine VARCHAR(100),
    brand VARCHAR(100),
    model VARCHAR(100),
    id_device INTEGER UNIQUE
);
