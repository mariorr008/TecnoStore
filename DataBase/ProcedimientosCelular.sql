USE TecnologiaStorePablo;
DELIMITER //



CREATE PROCEDURE guardar_celular(
    IN p_id INT,
    IN p_id_marca INT,
    IN p_modelo VARCHAR(80),
    IN p_so VARCHAR(20),
    IN p_gama VARCHAR(10),
    IN p_precio DOUBLE,
    IN p_stock INT
)
BEGIN
    INSERT INTO celulares (id, id_marca, modelo, sistema_operativo, gama, precio, stock)
    VALUES (p_id, p_id_marca, p_modelo, p_so, p_gama, p_precio, p_stock)
    ON DUPLICATE KEY UPDATE
        id_marca = p_id_marca,
        modelo = p_modelo,
        sistema_operativo = p_so,
        gama = p_gama,
        precio = p_precio,
        stock = p_stock;
END //




CREATE PROCEDURE buscar_celular_por_id(IN p_id INT)
BEGIN
    SELECT c.*, m.nombre_marca
    FROM celulares c
    INNER JOIN marca m ON c.id_marca = m.id
    WHERE c.id = p_id;
END //



CREATE PROCEDURE listar_celulares()
BEGIN
    SELECT c.*, m.nombre_marca
    FROM celulares c
    INNER JOIN marca m ON c.id_marca = m.id;
END //



CREATE PROCEDURE listar_celulares_por_gama(IN p_gama VARCHAR(10))
BEGIN
    SELECT c.*, m.nombre_marca as marca
    FROM celulares c
    INNER JOIN marca m ON c.id_marca = m.id
    WHERE c.gama = p_gama;
END //



CREATE PROCEDURE eliminar_celular(IN p_id INT)
BEGIN
    DELETE FROM celulares WHERE id = p_id;
END //



CREATE PROCEDURE existe_celular(IN p_id INT)
BEGIN
    SELECT COUNT(*) AS total FROM celulares WHERE id = p_id;
END //




CREATE PROCEDURE actualizar_stock_celular(IN p_id INT, IN p_nuevo_stock INT)
BEGIN
    UPDATE celulares SET stock = p_nuevo_stock WHERE id = p_id;
END //



CREATE PROCEDURE listar_celulares_stock_bajo()
BEGIN
    SELECT c.*, m.nombre_marca
    FROM celulares c
    INNER JOIN marca m ON c.id_marca = m.id
    WHERE c.stock < 5;
END //

DELIMITER ;