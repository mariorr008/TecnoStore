# TecnoStore

Sistema de consola desarrollado en **Java** para administrar las operaciones principales de una tienda minorista de celulares. El proyecto permite organizar el catálogo, los clientes, las ventas y los reportes, utilizando programación orientada a objetos, persistencia con JDBC y una base de datos MySQL.

## Objetivo

Reemplazar el registro manual de información en hojas de cálculo por una aplicación que centralice los datos de celulares, clientes, inventario y ventas.

## Funcionalidades

- **Celulares:** registrar, consultar, actualizar y eliminar celulares, con información de marca, modelo, sistema operativo, gama, precio y stock.
- **Clientes y usuarios:** gestionar la información de personas y distinguir entre clientes y administradores.
- **Ventas:** registrar ventas asociadas a un cliente, incluir los celulares vendidos y guardar la información en MySQL.
- **Inventario:** consultar existencias y generar un reporte de celulares con stock bajo.
- **Reportes:** consultar los celulares más vendidos y las ventas agrupadas por mes.
- **Archivo de ventas:** generar o actualizar el resumen de ventas en `reporte_ventas.txt`.
- **Validaciones y excepciones:** comprobar datos ingresados y manejar situaciones como datos inválidos, celulares o clientes inexistentes y stock insuficiente.

> Las opciones disponibles y el comportamiento exacto dependen de la implementación actual del proyecto.

## Tecnologías

- Java 21
- Maven
- MySQL
- JDBC y MySQL Connector/J 9.7.0
- Programación Orientada a Objetos
- Colecciones y Stream API
- Patrones de diseño: Factory, Strategy y Singleton
- Git para el control de versiones

## Arquitectura del proyecto

El código está organizado en paquetes según la responsabilidad de sus clases:

| Paquete | Responsabilidad |
| --- | --- |
| `Modelo` | Entidades del negocio, enumeraciones y excepciones personalizadas. |
| `Vista` | Menús de consola, entrada y presentación de información, validaciones de entrada y utilidades para archivos. |
| `Controlador` | Coordinación del flujo de la aplicación, gestores de operaciones, reportes y patrones de diseño. |
| `Dao` | Acceso a MySQL mediante JDBC y operaciones de persistencia. |
| `com.practica.tecnostore` | Punto de entrada de la aplicación (`TecnoStore.java`). |
| `DataBase` | Script de creación de la base de datos y procedimientos almacenados. |

### Patrones de diseño

- **Factory:** `FactoryCelular` centraliza la creación de celulares y algunas validaciones antes de construir el objeto.
- **Strategy:** `StrategyReporte` define una interfaz común para generar distintos tipos de reportes.
- **Singleton:** `ConexionDB` mantiene una instancia compartida de la clase encargada de proporcionar conexiones a la base de datos.

## Modelo de datos

El script `DataBase/BaseDatos.sql` crea la base de datos **`TecnoStoreMario`** y las tablas principales:

- `persona`: datos generales de personas.
- `cliente`: identifica las personas registradas como clientes.
- `administrador`: identifica las personas con rol de administrador.
- `marca`: catálogo de marcas de celulares.
- `celulares`: características, precio y existencias de cada celular.
- `ventas`: información general de cada venta.
- `detalle_ventas`: celulares, cantidades y valores asociados a cada venta.

También se incluyen scripts SQL para procedimientos almacenados relacionados con celulares, detalles de venta, marcas, personas y ventas.

## Requisitos

Antes de ejecutar la aplicación, asegúrate de tener instalado:

1. **JDK 21** o una versión compatible con la configuración Maven del proyecto.
2. **Apache Maven**.
3. **MySQL Server** y una herramienta para ejecutar scripts SQL, como MySQL Workbench.
4. Un editor o IDE compatible con proyectos Java y Maven, como IntelliJ IDEA, Eclipse o NetBeans.

## Configuración e instalación

### 1. Descargar o clonar el proyecto

```bash
git clone <URL_DEL_REPOSITORIO>
cd TecnoStore
```

Si ya tienes el proyecto descargado, abre la carpeta que contiene el archivo `pom.xml`.

### 2. Crear la base de datos

1. Abre MySQL Workbench o tu cliente MySQL.
2. Abre y ejecuta `DataBase/BaseDatos.sql`.
3. Si necesitas los procedimientos almacenados, ejecuta después los scripts SQL correspondientes de la carpeta `DataBase`.

**Importante:** `BaseDatos.sql` contiene `DROP DATABASE IF EXISTS TecnoStoreMario`, por lo que elimina una base de datos existente con ese nombre antes de crearla de nuevo. No lo ejecutes si necesitas conservar sus datos sin tener una copia de seguridad.

### 3. Configurar la conexión

La conexión está definida en `src/main/java/Dao/ConexionDB.java`. Comprueba que el servidor, puerto, nombre de la base de datos, usuario y contraseña coincidan con tu instalación local.

Por seguridad, no publiques contraseñas reales en GitHub. Para un uso fuera del entorno local, conviene cargar las credenciales desde variables de entorno o un archivo de configuración excluido del repositorio.

### 4. Compilar y ejecutar

Desde la carpeta raíz del proyecto, ejecuta:

```bash
mvn clean package
```

Para iniciar la aplicación con Maven:

```bash
mvn exec:java
```

La clase principal configurada en el proyecto es `com.practica.tecnostore.TecnoStore`.

También puedes abrir el proyecto desde tu IDE, esperar a que Maven descargue las dependencias y ejecutar el método `main` de `TecnoStore.java`.

## Reporte de ventas

El proyecto incluye el archivo `reporte_ventas.txt` para almacenar el resumen de ventas. La ubicación y el contenido del reporte dependen de cómo se ejecute la aplicación y de la implementación de las utilidades de archivo.

## Estructura general

```text
TecnoStore/
├── DataBase/
│   ├── BaseDatos.sql
│   ├── ProcedimientosCelular.sql
│   ├── ProcedimientosDV.sql
│   ├── ProcedimientosMarca.sql
│   ├── ProcedimientosPersonas.sql
│   └── ProcedimientosVentas.sql
├── src/
│   ├── main/java/
│   │   ├── com/practica/tecnostore/TecnoStore.java
│   │   ├── Controlador/
│   │   ├── Dao/
│   │   ├── Modelo/
│   │   └── Vista/
│   └── test/
├── reporte_ventas.txt
└── pom.xml
```

## Notas de seguridad

- La aplicación crea un administrador predeterminado si no encuentra administradores registrados. En la primera ejecución, revisa las credenciales predeterminadas que muestra la consola y cámbialas antes de utilizar el sistema con información real.
- No almacenes contraseñas reales ni credenciales de base de datos en repositorios públicos.
- Haz copias de seguridad de la base de datos antes de ejecutar scripts que eliminen o recreen tablas.

## Autor

Proyecto académico de práctica de desarrollo de software hecho por `MARIO ROJAS`.
