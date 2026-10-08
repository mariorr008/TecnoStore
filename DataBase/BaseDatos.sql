DROP DATABASE IF EXISTS TecnoStoreMario;
CREATE DATABASE TecnoStoreMario;
USE TecnoStoreMario;

CREATE TABLE persona (
    id INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    identificacion VARCHAR(20) NOT NULL UNIQUE,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    password VARCHAR(100) NOT NULL
);

CREATE TABLE cliente (
    id_persona INT PRIMARY KEY,
    FOREIGN KEY (id_persona) REFERENCES persona(id)
);

CREATE TABLE administrador (
    id_persona INT PRIMARY KEY,
    FOREIGN KEY (id_persona) REFERENCES persona(id)
);

CREATE TABLE marca (
    id INT PRIMARY KEY,
    nombre_marca VARCHAR(50) NOT NULL UNIQUE
);

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

CREATE TABLE ventas (
    id INT PRIMARY KEY,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DOUBLE NOT NULL,
    id_cliente INT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_persona)
);

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