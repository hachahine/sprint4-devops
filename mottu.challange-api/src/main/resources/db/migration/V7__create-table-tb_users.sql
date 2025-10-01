CREATE TABLE tb_users (

    id NUMBER PRIMARY KEY,
    login VARCHAR2(255) NOT NULL UNIQUE,
    password VARCHAR2(255) NOT NULL

);
