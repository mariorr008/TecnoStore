USE TecnoStoreMario;
DELIMITER //

--SON 9 PROCEDIMIENTOS

-- Guardar persona (insert o update)
CREATE PROCEDURE guardar_persona(
    IN p_id INT,
    IN p_nombre VARCHAR(100),
    IN p_identificacion VARCHAR(20),
    IN p_correo VARCHAR(100),
    IN p_telefono VARCHAR(20),
    IN p_password VARCHAR(100)
)
BEGIN
    INSERT INTO persona (id, nombre, identificacion, correo, telefono, password)
    VALUES (p_id, p_nombre, p_identificacion, p_correo, p_telefono, p_password)
    ON DUPLICATE KEY UPDATE
        nombre = p_nombre,
        correo = p_correo,
        telefono = p_telefono,
        password = p_password;
END //



-- Guardar cliente (asocia persona con rol cliente)
CREATE PROCEDURE guardar_cliente(IN p_id_persona INT)
BEGIN
    INSERT IGNORE INTO cliente (id_persona) VALUES (p_id_persona);
END //



-- Guardar administrador
CREATE PROCEDURE guardar_administrador(IN p_id_persona INT)
BEGIN
    INSERT IGNORE INTO administrador (id_persona) VALUES (p_id_persona);
END //



-- Buscar persona por ID
CREATE PROCEDURE buscar_persona_por_id(IN p_id INT)
BEGIN
    SELECT p.*,
           CASE
               WHEN c.id_persona IS NOT NULL THEN 'CLIENTE'
               WHEN a.id_persona IS NOT NULL THEN 'ADMIN'
           END AS rol
    FROM persona p
    LEFT JOIN cliente c ON p.id = c.id_persona
    LEFT JOIN administrador a ON p.id = a.id_persona
    WHERE p.id = p_id;
END //



-- Buscar persona por correo (para login)
CREATE PROCEDURE buscar_persona_por_correo(IN p_correo VARCHAR(100))
BEGIN
    SELECT p.*,
           CASE
               WHEN c.id_persona IS NOT NULL THEN 'CLIENTE'
               WHEN a.id_persona IS NOT NULL THEN 'ADMIN'
           END AS rol
    FROM persona p
    LEFT JOIN cliente c ON p.id = c.id_persona
    LEFT JOIN administrador a ON p.id = a.id_persona
    WHERE p.correo = p_correo;
END //



-- Listar todos los clientes
--inner join recorre las filas de las dos tablas al coincidir
CREATE PROCEDURE listar_clientes()
BEGIN
    SELECT p.*
    FROM persona p
    INNER JOIN cliente c ON p.id = c.id_persona;
END //



-- Verificar si existe persona por id
CREATE PROCEDURE existe_persona(IN p_id INT)
BEGIN
    SELECT COUNT(*) AS total FROM persona WHERE id = p_id;
END //



-- Verificar si existe identificación
CREATE PROCEDURE existe_identificacion(IN p_identificacion VARCHAR(20))
BEGIN
    SELECT COUNT(*) AS total FROM persona WHERE identificacion = p_identificacion;
END //



-- Verificar si existe correo
CREATE PROCEDURE existe_correo(IN p_correo VARCHAR(100))
BEGIN
    SELECT COUNT(*) AS total FROM persona WHERE correo = p_correo;
END //



-- Eliminar persona (cascade manual)
CREATE PROCEDURE eliminar_persona(IN p_id INT)
BEGIN
    DELETE FROM cliente WHERE id_persona = p_id;
    DELETE FROM administrador WHERE id_persona = p_id;
    DELETE FROM persona WHERE id = p_id;
END //

DELIMITER ;