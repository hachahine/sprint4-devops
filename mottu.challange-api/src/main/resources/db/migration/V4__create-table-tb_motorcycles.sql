CREATE TABLE tb_motorcycles (

    id NUMBER PRIMARY KEY,
    license VARCHAR2(50) NOT NULL,
    chassis VARCHAR2(100),
    engine VARCHAR2(100),
    brand VARCHAR2(100),
    model VARCHAR2(100),
    id_device NUMBER UNIQUE

);
