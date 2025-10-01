CREATE TABLE tb_yards (

    id NUMBER PRIMARY KEY,
    name VARCHAR2(255) NOT NULL,
    id_unit NUMBER NOT NULL,
    CONSTRAINT fk_yards_units FOREIGN KEY (id_unit) REFERENCES tb_units(id)

);
