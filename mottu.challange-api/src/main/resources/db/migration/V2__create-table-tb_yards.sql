CREATE TABLE tb_yards (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    id_unit INTEGER NOT NULL,
    CONSTRAINT fk_yards_units FOREIGN KEY (id_unit) REFERENCES tb_units(id)
);
