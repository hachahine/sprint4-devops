CREATE TABLE tb_employees (

    id NUMBER PRIMARY KEY,
    name VARCHAR2(255) NOT NULL,
    email VARCHAR2(255) UNIQUE,
    role VARCHAR2(100),
    id_unit NUMBER NOT NULL,
    CONSTRAINT fk_employees_units FOREIGN KEY (id_unit) REFERENCES tb_units(id)

);
