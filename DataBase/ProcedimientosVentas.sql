USE TecnoStoreMario;
DELIMITER //


CREATE PROCEDURE guardar_venta(
    IN p_id INT,
    IN p_total DOUBLE,
    IN p_id_cliente INT
)
BEGIN
    INSERT INTO ventas (id, total, id_cliente)
    VALUES (p_id, p_total, p_id_cliente)
    ON DUPLICATE KEY UPDATE
        total = p_total;
END //



CREATE PROCEDURE buscar_venta_por_id(IN p_id INT)
BEGIN
    SELECT * FROM ventas WHERE id = p_id;
END //



CREATE PROCEDURE listar_ventas()
BEGIN
    SELECT * FROM ventas;
END //



CREATE PROCEDURE listar_ventas_por_cliente(IN p_id_cliente INT)
BEGIN
    SELECT * FROM ventas WHERE id_cliente = p_id_cliente;
END //



CREATE PROCEDURE existe_venta(IN p_id INT)
BEGIN
    SELECT COUNT(*) AS total FROM ventas WHERE id = p_id;
END //