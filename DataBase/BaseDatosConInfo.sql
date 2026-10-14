
DROP DATABASE IF EXISTS TecnoStoreMario;
CREATE DATABASE TecnoStoreMario;
USE TecnoStoreMario;

-- =========================================
-- 1. PERSONA
-- =========================================
CREATE TABLE persona (
    id INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    identificacion VARCHAR(20) NOT NULL UNIQUE,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    password VARCHAR(100) NOT NULL
);

-- =========================================
-- 2. CLIENTE
-- =========================================
CREATE TABLE cliente (
    id_persona INT PRIMARY KEY,
    FOREIGN KEY (id_persona) REFERENCES persona(id)
);

-- =========================================
-- 3. ADMINISTRADOR (SIN DATOS)
-- =========================================
CREATE TABLE administrador (
    id_persona INT PRIMARY KEY,
    FOREIGN KEY (id_persona) REFERENCES persona(id)
);

-- =========================================
-- 4. MARCA
-- =========================================
CREATE TABLE marca (
    id INT PRIMARY KEY,
    nombre_marca VARCHAR(50) NOT NULL UNIQUE
);

-- =========================================
-- 5. CELULARES
-- =========================================
CREATE TABLE celulares (
    id INT PRIMARY KEY,
    id_marca INT NOT NULL,
    modelo VARCHAR(80) NOT NULL,
    sistema_operativo ENUM('iOS','Android','KaiOS') NOT NULL,
    gama ENUM('ALTA','MEDIA','BAJA') NOT NULL,
    precio DOUBLE NOT NULL CHECK (precio > 0),
    stock INT NOT NULL CHECK (stock >= 0),
    FOREIGN KEY (id_marca) REFERENCES marca(id)
);

-- =========================================
-- 6. VENTAS
-- =========================================
CREATE TABLE ventas (
    id INT PRIMARY KEY,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DOUBLE NOT NULL,
    id_cliente INT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_persona)
);

-- =========================================
-- 7. DETALLE DE VENTAS
-- =========================================
CREATE TABLE detalle_ventas (
    id INT PRIMARY KEY,
    id_venta INT NOT NULL,
    id_celular INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DOUBLE NOT NULL,
    subtotal DOUBLE NOT NULL,
    FOREIGN KEY (id_venta) REFERENCES ventas(id),
    FOREIGN KEY (id_celular) REFERENCES celulares(id)
);

-- =========================================
-- 8. PERSONAS (10 CLIENTES)
-- =========================================
INSERT INTO persona
(id, nombre, identificacion, correo, telefono, password)
VALUES
(1, 'Carlos Ramirez', '1001001001', 'carlos@gmail.com', '3001112233', 'clave123'),
(2, 'Laura Gomez', '1001001002', 'laura@gmail.com', '3012223344', 'clave123'),
(3, 'Andres Martinez', '1001001003', 'andres@gmail.com', '3023334455', 'clave123'),
(4, 'Sofia Rodriguez', '1001001004', 'sofia@gmail.com', '3034445566', 'clave123'),
(5, 'Daniel Perez', '1001001005', 'daniel@gmail.com', '3045556677', 'clave123'),
(6, 'Valentina Torres', '1001001006', 'valentina@gmail.com', '3056667788', 'clave123'),
(7, 'Juan Herrera', '1001001007', 'juan@gmail.com', '3067778899', 'clave123'),
(8, 'Camila Castro', '1001001008', 'camila@gmail.com', '3078889900', 'clave123'),
(9, 'Samuel Rojas', '1001001009', 'samuel@gmail.com', '3089990011', 'clave123'),
(10, 'Isabella Diaz', '1001001010', 'isabella@gmail.com', '3091110022', 'clave123');

INSERT INTO cliente (id_persona)
VALUES (1), (2), (3), (4), (5),
       (6), (7), (8), (9), (10);

-- La tabla administrador queda vacía.

-- =========================================
-- 9. MARCAS
-- =========================================
INSERT INTO marca (id, nombre_marca)
VALUES
(1, 'Samsung'),
(2, 'Apple'),
(3, 'Xiaomi'),
(4, 'Motorola'),
(5, 'Huawei');

-- =========================================
-- 10. CELULARES
-- Stock menor a 5, igual a 5 y mayor a 5
-- =========================================
INSERT INTO celulares
(id, id_marca, modelo, sistema_operativo, gama, precio, stock)
VALUES
(1, 1, 'Galaxy A55', 'Android', 'MEDIA', 1500000, 8),
(2, 1, 'Galaxy S24', 'Android', 'ALTA', 3200000, 3),
(3, 2, 'iPhone 15', 'iOS', 'ALTA', 3800000, 2),
(4, 2, 'iPhone 13', 'iOS', 'ALTA', 2400000, 7),
(5, 3, 'Redmi Note 13', 'Android', 'MEDIA', 900000, 12),
(6, 3, 'Poco X6', 'Android', 'MEDIA', 1200000, 4),
(7, 4, 'Moto G54', 'Android', 'BAJA', 750000, 5),
(8, 4, 'Moto E14', 'Android', 'BAJA', 450000, 20),
(9, 5, 'Nova 12', 'Android', 'ALTA', 1800000, 0),
(10, 1, 'Galaxy A15', 'Android', 'BAJA', 650000, 1);

-- =========================================
-- 11. VENTAS
-- Tres ventas por mes: enero a abril 2026
-- =========================================
INSERT INTO ventas (id, fecha, total, id_cliente)
VALUES
(1,  '2026-01-05 10:30:00', 1740000, 1),
(2,  '2026-01-12 14:15:00', 3800000, 2),
(3,  '2026-01-20 16:45:00', 2450000, 3),

(4,  '2026-02-02 09:20:00', 3320000, 4),
(5,  '2026-02-08 11:10:00', 2400000, 5),
(6,  '2026-02-19 15:30:00', 2400000, 1),

(7,  '2026-03-03 10:00:00', 2250000, 6),
(8,  '2026-03-11 13:40:00', 4700000, 7),
(9,  '2026-03-22 17:20:00', 1300000, 8),

(10, '2026-04-04 08:50:00', 3200000, 9),
(11, '2026-04-14 12:25:00', 2760000, 10),
(12, '2026-04-25 16:10:00', 2550000, 2);

-- =========================================
-- 12. DETALLE DE VENTAS
-- =========================================
INSERT INTO detalle_ventas
(id, id_venta, id_celular, cantidad, precio_unitario, subtotal)
VALUES
-- ENERO
(1, 1, 1, 1, 1500000, 1500000),
(2, 1, 8, 2, 120000, 240000),
(3, 2, 3, 1, 3800000, 3800000),
(4, 3, 5, 2, 900000, 1800000),
(5, 3, 10, 1, 650000, 650000),

-- FEBRERO
(6, 4, 2, 1, 3200000, 3200000),
(7, 4, 8, 1, 120000, 120000),
(8, 5, 4, 1, 2400000, 2400000),
(9, 6, 6, 2, 1200000, 2400000),

-- MARZO
(10, 7, 1, 1, 1500000, 1500000),
(11, 7, 7, 1, 750000, 750000),
(12, 8, 3, 1, 3800000, 3800000),
(13, 8, 5, 1, 900000, 900000),
(14, 9, 10, 2, 650000, 1300000),

-- ABRIL
(15, 10, 2, 1, 3200000, 3200000),
(16, 11, 4, 1, 2400000, 2400000),
(17, 11, 8, 3, 120000, 360000),
(18, 12, 9, 1, 1800000, 1800000),
(19, 12, 7, 1, 750000, 750000);

-- =========================================
-- 13. CONSULTAS DE VERIFICACIÓN
-- =========================================

-- Ver clientes
SELECT p.*
FROM persona p
INNER JOIN cliente c ON p.id = c.id_persona;

-- Verificar que no hay administradores registrados
SELECT * FROM administrador;

-- Celulares con stock menor a 5
SELECT modelo, precio, stock
FROM celulares
WHERE stock < 5;

-- Celulares con stock igual o mayor a 5
SELECT modelo, precio, stock
FROM celulares
WHERE stock >= 5;

-- Resumen de ventas por mes
SELECT
    DATE_FORMAT(fecha, '%Y-%m') AS mes,
    COUNT(*) AS cantidad_ventas,
    SUM(total) AS total_vendido
FROM ventas
GROUP BY DATE_FORMAT(fecha, '%Y-%m')
ORDER BY mes;

-- Productos más vendidos
SELECT
    c.modelo,
    SUM(dv.cantidad) AS unidades_vendidas
FROM detalle_ventas dv
INNER JOIN celulares c ON c.id = dv.id_celular
GROUP BY c.id, c.modelo
ORDER BY unidades_vendidas DESC;

-- Clientes con más de una compra
SELECT
    p.nombre,
    COUNT(v.id) AS cantidad_compras
FROM persona p
INNER JOIN cliente c ON c.id_persona = p.id
INNER JOIN ventas v ON v.id_cliente = c.id_persona
GROUP BY p.id, p.nombre
HAVING COUNT(v.id) > 1;
