USE TecnoStoreMario;
DELIMITER //



CREATE PROCEDURE guardar_marca(
    IN p_id INT,
    IN p_nombre VARCHAR(50)
)
BEGIN
    INSERT INTO marca (id, nombre_marca)
    VALUES (p_id, p_nombre)
    ON DUPLICATE KEY UPDATE nombre_marca = p_nombre;
END //



CREATE PROCEDURE buscar_marca_por_id(IN p_id INT)
BEGIN
    SELECT * FROM marca WHERE id = p_id;
END //




CREATE PROCEDURE buscar_marca_por_nombre(IN p_nombre VARCHAR(50))
BEGIN
    SELECT * FROM marca WHERE nombre_marca = p_nombre;
END //




CREATE PROCEDURE listar_marcas()
BEGIN
    SELECT * FROM marca;
END //




CREATE PROCEDURE existe_marca(IN p_id INT)
BEGIN
    SELECT COUNT(*) AS total FROM marca WHERE id = p_id;
END //

DELIMITER ;