USE TecnoStoreMario;
DELIMITER //



CREATE PROCEDURE guardar_detalle_venta(
    IN p_id INT,
    IN p_id_venta INT,
    IN p_id_celular INT,
    IN p_cantidad INT,
    IN p_precio_unitario DOUBLE,
    IN p_subtotal DOUBLE
)
BEGIN
    INSERT INTO detalle_ventas (id, id_venta, id_celular, cantidad, precio_unitario, subtotal)
    VALUES (p_id, p_id_venta, p_id_celular, p_cantidad, p_precio_unitario, p_subtotal)
    ON DUPLICATE KEY UPDATE
        cantidad = p_cantidad,
        precio_unitario = p_precio_unitario,
        subtotal = p_subtotal;
END //




CREATE PROCEDURE listar_detalle_por_venta(IN p_id_venta INT)
BEGIN
    SELECT dv.*, c.modelo, m.nombre_marca
    FROM detalle_ventas dv
    INNER JOIN celulares c ON dv.id_celular = c.id
    INNER JOIN marca m ON c.id_marca = m.id
    WHERE dv.id_venta = p_id_venta;
END //




CREATE PROCEDURE existe_detalle_venta(IN p_id INT)
BEGIN
    SELECT COUNT(*) AS total FROM detalle_ventas WHERE id = p_id;
END //

DELIMITER ;