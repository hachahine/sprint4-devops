CREATE TABLE tb_employees (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    role VARCHAR(100),
    id_unit INTEGER NOT NULL,
    CONSTRAINT fk_employees_units FOREIGN KEY (id_unit) REFERENCES tb_units(id)
);
