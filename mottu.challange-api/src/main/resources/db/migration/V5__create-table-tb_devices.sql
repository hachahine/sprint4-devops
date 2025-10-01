CREATE TABLE tb_devices (

    id NUMBER PRIMARY KEY,
    code VARCHAR2(100) NOT NULL,
    status_color VARCHAR2(50) CHECK (status_color IN ('YELLOW','BLUE','ORANGE','RED','GRAY','DARK_GREEN','PINK','LIGHT_GREEN')),
    active NUMBER(1) CHECK (active IN (0,1)),
    id_yard NUMBER NOT NULL,
    id_motorcycle NUMBER UNIQUE,
    CONSTRAINT fk_devices_yards FOREIGN KEY (id_yard) REFERENCES tb_yards(id),
    CONSTRAINT fk_devices_motorcycles FOREIGN KEY (id_motorcycle) REFERENCES tb_motorcycles(id)

);
