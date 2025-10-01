ALTER TABLE tb_motorcycles
    ADD CONSTRAINT fk_motorcycles_devices FOREIGN KEY (id_device) REFERENCES tb_devices(id);
