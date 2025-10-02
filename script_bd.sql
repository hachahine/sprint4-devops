-- create tables

CREATE TABLE tb_units (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    neighborhood VARCHAR(255)
);

-- yards
CREATE TABLE tb_yards (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    id_unit INTEGER NOT NULL,
    CONSTRAINT fk_yards_units FOREIGN KEY (id_unit) REFERENCES tb_units(id)
);

-- employees
CREATE TABLE tb_employees (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    role VARCHAR(100),
    id_unit INTEGER NOT NULL,
    CONSTRAINT fk_employees_units FOREIGN KEY (id_unit) REFERENCES tb_units(id)
);

-- motorcycles
CREATE TABLE tb_motorcycles (
    id SERIAL PRIMARY KEY,
    license VARCHAR(50) NOT NULL UNIQUE,
    chassis VARCHAR(100),
    engine VARCHAR(100),
    brand VARCHAR(100),
    model VARCHAR(100),
    id_device INTEGER UNIQUE
);

-- devices
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


ALTER TABLE tb_motorcycles
    ADD CONSTRAINT fk_motorcycles_devices FOREIGN KEY (id_device) REFERENCES tb_devices(id);

-- users
CREATE TABLE tb_users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

