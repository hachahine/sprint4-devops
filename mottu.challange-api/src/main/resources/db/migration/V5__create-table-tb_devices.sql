CREATE TABLE tb_devices (
    id SERIAL PRIMARY KEY,
    code VARCHAR(100) NOT NULL,
    status_color VARCHAR(50) CHECK (status_color IN ('YELLOW','BLUE','ORANGE','RED','GRAY','DARK_GREEN','PINK','LIGHT_GREEN')),
    active BOOLEAN,
    id_yard INTEGER NOT NULL,
    id_motorcycle INTEGER UNIQUE,
    CONSTRAINT fk_devices_yards FOREIGN KEY (id_yard) REFERENCES tb_yards(id),
    CONSTRAINT fk_devices_motorcycles FOREIGN KEY (id_motorcycle) REFERENCES tb_motorcycles(id)
);
