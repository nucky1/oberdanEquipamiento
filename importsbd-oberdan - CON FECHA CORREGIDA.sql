-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 30, 2021 at 05:23 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: bd-oberdan
--

-- --------------------------------------------------------

--
-- Table structure for table aprobaciones
--

DROP TABLE IF EXISTS aprobaciones;
CREATE TABLE IF NOT EXISTS aprobaciones (
  id int(11) NOT NULL AUTO_INCREMENT,
  credito_id int(11) NOT NULL,
  empleado_id int(11) NOT NULL,
  fecha timestamp NULL DEFAULT NULL,
  estado tinyint(1) DEFAULT 1,
  PRIMARY KEY (id),
  KEY credito_id (credito_id),
  KEY empleado_id (empleado_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table aprobaciones
--

INSERT INTO aprobaciones (id, credito_id, empleado_id, fecha, estado) VALUES
(2, 4, 5, '2021-04-04 07:42:23', 1),
(3, 4, 4, '2021-04-04 07:54:49', 1),
(4, 4, 2, '2021-04-04 07:56:56', 1),
(6, 5, 4, '2021-04-06 05:32:25', 1),
(7, 5, 1, '2021-04-06 23:45:35', 1),
(8, 5, 8, '2021-06-12 02:36:25', 1),
(9, 5, 8, '2021-06-12 02:36:26', 1),
(10, 5, 8, '2021-06-12 02:36:27', 1),
(11, 5, 8, '2021-06-12 02:36:27', 1),
(12, 5, 8, '2021-06-12 02:36:31', 1),
(13, 5, 8, '2021-06-12 02:36:31', 1),
(14, 5, 8, '2021-06-12 02:36:31', 1),
(15, 5, 8, '2021-06-12 02:36:32', 1),
(16, 5, 8, '2021-06-12 02:36:32', 1),
(17, 5, 8, '2021-06-12 02:36:32', 1),
(18, 5, 8, '2021-06-30 01:34:54', 1),
(19, 5, 8, '2021-06-30 01:35:11', 1);

-- --------------------------------------------------------

--
-- Table structure for table articulos
--
-- tipo 1 existente, 2 pedido 3 usado
-- Freezer Inelro Mod. FIH-350  325lts. Full -Dual-Interior Pintado 
-- SELECT precio_compra, producto_id FROM art_stock WHERE updated_at = (SELECT MAX(updated_at) FROM art_stock GROUP BY producto_id)
DROP TABLE IF EXISTS articulos;
CREATE TABLE IF NOT EXISTS articulos (
  id int(11) NOT NULL AUTO_INCREMENT,
  cod int(11) NOT NULL,
  nombre varchar(70) NOT NULL,
  codigo_ean varchar(50) DEFAULT NULL,
  codigo_barra varchar(50) DEFAULT NULL,
  rubro_id int(11) DEFAULT NULL,
  stock_existente float DEFAULT NULL,
  stock_minimo float DEFAULT NULL,
  proveedor_id int(11) NOT NULL,
  iva float DEFAULT 0,
  observaciones varchar(60) DEFAULT NULL,
  costo_flete float DEFAULT 0,
  sobretasa_iva float DEFAULT 0,
  impuesto_interno float DEFAULT 0,
  impuesto_int_fijo float DEFAULT 0,
  precio_venta float DEFAULT 0,
  tipo tinyint(1) DEFAULT 0,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  precio_compra float NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  UNIQUE KEY cod (cod),
  KEY rubro_id (rubro_id),
  KEY proveedor_id (proveedor_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `articulos`
--


--
-- Disparadores `articulos`
--
DELIMITER $$
CREATE TRIGGER `articulos_create` BEFORE INSERT ON `articulos` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `articulos_update` BEFORE UPDATE ON `articulos` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;
--
-- Dumping data for table articulos
--

INSERT INTO articulos (id, cod, nombre, codigo_ean, codigo_barra, rubro_id, stock_existente, stock_minimo, proveedor_id, iva, observaciones, costo_flete, sobretasa_iva, impuesto_interno, impuesto_int_fijo, precio_venta, tipo, created_at, updated_at, state, precio_compra) VALUES
(1, 1010, 'UNIDAD CONDENSADORA 1 HP', 'EA1234676', '123456789', 2, 25, 1, 1, 15, 'probando yop!', 15, 0.5, 0.1, 0.1, 3750, 0, '2021-02-09 13:03:24', '2021-06-27 00:36:01', 'ACTIVO', 2000),
(2, 950, 'ANAFE SOL REAL 2H.AC.430 B. REK. H. REDONDO', 'AS12351612', '21344635', 1, 0, 1, 1, 15, 'probando', 15, 0.5, 0.1, 0.1, 900, 0, '2021-02-09 13:03:24', '2021-02-10 21:12:30', 'ACTIVO', 351),
(3, 2020, 'AIRE CONDICIONAD WHIRPOOL 3150 WTS. FRIO', 'AC1234534', '12355465', 3, 0, 1, 1, 15, 'probando', 15, 0.5, 0.1, 0.1, 4230, 0, '2021-02-09 13:03:24', '2021-02-10 21:12:30', 'ACTIVO', 3650),
(4, 2025, 'A-A CARRIER LINEA TEMPSTAR SPLIT F.SOLO 3', 'AA2135123', '878784657', 3, 0, 1, 1, 15, 'probando', 15, 0.5, 0.1, 0.1, 5000, 0, '2021-02-09 13:03:24', '2021-02-10 21:12:30', 'ACTIVO', 4320),
(5, 6, 'articulo de Enken', '213132313', '12313131', 4, 4, 2, 4, 5, 'observaciones ', 54, 4, 4, 0, 213, 0, '2021-06-19 15:15:38', '2021-06-19 15:17:33', 'ACTIVO', 54),
(6, 546, 'soy el nuevo de enken', '3333333334', '3333333333', 5, 4, 3, 4, 10.5, 'soy un producto a borrar', 10, 0, 0, 0, 600, 0, '2021-06-23 23:49:41', '2021-06-24 00:27:41', 'ACTIVO', 100);

-- --------------------------------------------------------

--
-- Table structure for table articulo_cuota
--

DROP TABLE IF EXISTS articulo_cuota;
CREATE TABLE IF NOT EXISTS articulo_cuota (
  id int(11) NOT NULL AUTO_INCREMENT,
  articulos_id int(11) NOT NULL,
  cuota_id int(11) NOT NULL,
  state varchar(50) DEFAULT 'ACTIVO',
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  KEY articulos_id (articulos_id),
  KEY cuota_id (cuota_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Disparadores `articulo_cuota`
--
DELIMITER $$
CREATE TRIGGER `articulo_cuota_create` BEFORE INSERT ON `articulo_cuota` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `articulo_cuota_update` BEFORE UPDATE ON `articulo_cuota` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;
--
-- Dumping data for table articulo_cuota
--

INSERT INTO articulo_cuota (id, articulos_id, cuota_id, state, created_at, updated_at) VALUES
(3, 1, 2, 'ACTIVO', '2021-05-11 12:11:17', '2021-05-11 12:11:17'),
(4, 6, 2, 'ACTIVO', '2021-06-23 23:50:13', '2021-06-23 23:50:13');

-- --------------------------------------------------------

--
-- Table structure for table art_rubro
--

DROP TABLE IF EXISTS art_rubro;
CREATE TABLE IF NOT EXISTS art_rubro (
  id int(11) NOT NULL AUTO_INCREMENT,
  nombre varchar(50) NOT NULL,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Disparadores `art_rubro`
--
DELIMITER $$
CREATE TRIGGER `art_rubro_create` BEFORE INSERT ON `art_rubro` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `art_rubro_update` BEFORE UPDATE ON `art_rubro` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;
--
-- Dumping data for table art_rubro
--

INSERT INTO art_rubro (id, nombre, created_at, updated_at, state) VALUES
(1, 'ANAFES', '2021-02-09 13:21:43', '2021-02-09 13:21:43', 'ACTIVO'),
(2, 'OTRO', '2021-02-09 13:21:43', '2021-02-09 13:21:43', 'ACTIVO'),
(3, 'AIRE ACODICIONADO', '2021-02-09 13:21:43', '2021-02-09 13:21:43', 'ACTIVO'),
(4, 'nuevo rubro', '2021-06-19 15:15:37', '2021-06-19 15:15:37', 'ACTIVO'),
(5, 'rubroenken', '2021-06-23 23:49:40', '2021-06-23 23:49:40', 'ACTIVO');

-- --------------------------------------------------------

--
-- Table structure for table art_stock
--

DROP TABLE IF EXISTS art_stock;
CREATE TABLE IF NOT EXISTS art_stock (
  id int(11) NOT NULL AUTO_INCREMENT,
  producto_id int(11) NOT NULL,
  stock_actual float DEFAULT 0,
  stock_ingresado float DEFAULT 0,
  stock_pedido float DEFAULT 0,
  stock_reservado float DEFAULT 0,
  precio_compra float NOT NULL DEFAULT 0,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  fecha_ult_compra timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  KEY producto_id (producto_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Disparadores `art_stock`
--
DELIMITER $$
CREATE TRIGGER `actualizar_stock_delete` AFTER DELETE ON `art_stock` FOR EACH ROW UPDATE articulos SET articulos.stock_existente = (SELECT SUM(stock_actual) FROM art_stock WHERE art_stock.producto_id = OLD.producto_id) WHERE articulos.id = OLD.producto_id
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `actualizar_stock_insert` AFTER INSERT ON `art_stock` FOR EACH ROW UPDATE articulos SET articulos.stock_existente = (SELECT SUM(stock_actual) FROM art_stock WHERE art_stock.producto_id = NEW.producto_id) WHERE articulos.id = NEW.producto_id
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `actualizar_stock_update` AFTER UPDATE ON `art_stock` FOR EACH ROW UPDATE articulos SET articulos.stock_existente = (SELECT SUM(stock_actual) FROM art_stock WHERE art_stock.producto_id = NEW.producto_id) WHERE articulos.id = NEW.producto_id
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `art_stock_create` BEFORE INSERT ON `art_stock` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `art_stock_update` BEFORE UPDATE ON `art_stock` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;

--
-- Dumping data for table art_stock
--

INSERT INTO art_stock (id, producto_id, stock_actual, stock_ingresado, stock_pedido, stock_reservado, precio_compra, created_at, updated_at, state, fecha_ult_compra) VALUES
(1, 1, 10, 0, 0, 0, 2400, '2021-03-29 13:16:17', '2021-03-29 13:16:17', 'ACTIVO', '2021-03-29 13:13:16'),
(2, 1, 15, 0, 0, 0, 2000, '2021-03-29 13:31:38', '2021-03-30 13:48:20', 'ACTIVO', '2021-03-29 03:00:00'),
(3, 5, 4, 4, 23, 0, 312, '2021-06-19 15:16:08', '2021-06-19 15:17:33', 'ACTIVO', '2021-06-19 15:15:51'),
(4, 6, 4, 3, 2, 0, 100, '2021-06-23 23:53:13', '2021-06-24 00:27:41', 'ACTIVO', '2021-06-23 23:52:26');

-- --------------------------------------------------------

--
-- Table structure for table art_venta_part
--

DROP TABLE IF EXISTS art_venta_part;
CREATE TABLE IF NOT EXISTS art_venta_part (
  id int(11) NOT NULL AUTO_INCREMENT,
  articulos_id int(11) NOT NULL,
  venta_particular_id int(11) NOT NULL,
  cantidad int(11) NOT NULL,
  nro_serie text NOT NULL,
  state varchar(50) NOT NULL DEFAULT 'ACTIVO',
  created_at timestamp NOT NULL DEFAULT current_timestamp(),
  updated_at timestamp NOT NULL DEFAULT '1970-01-01 00:00:01',
  PRIMARY KEY (id),
  KEY articulos_id (articulos_id),
  KEY venta_particular_id (venta_particular_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Disparadores `art_venta_part`
--
DELIMITER $$
CREATE TRIGGER `art_venta_part_create` BEFORE INSERT ON `art_venta_part` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `art_venta_part_update` BEFORE UPDATE ON `art_venta_part` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table banco_proveedor
--

DROP TABLE IF EXISTS banco_proveedor;
CREATE TABLE IF NOT EXISTS banco_proveedor (
  id int(11) NOT NULL AUTO_INCREMENT,
  nro_cuenta varchar(50) DEFAULT NULL,
  banco varchar(50) DEFAULT NULL,
  cbu varchar(50) DEFAULT NULL,
  alias varchar(50) DEFAULT NULL,
  tipo_cuenta varchar(50) DEFAULT NULL,
  proveedor_id int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY proveedor_id (proveedor_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table banco_proveedor
--

INSERT INTO banco_proveedor (id, nro_cuenta, banco, cbu, alias, tipo_cuenta, proveedor_id) VALUES
(2, '', '', '376109406181971', '', 'CUENTA CORRIENTE', 4);

-- --------------------------------------------------------

--
-- Table structure for table barrio
--

DROP TABLE IF EXISTS barrio;
CREATE TABLE IF NOT EXISTS barrio (
  id int(11) NOT NULL AUTO_INCREMENT,
  nombre varchar(50) NOT NULL,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  localidad_id int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY localidad_id (localidad_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Table structure for table carton
--

DROP TABLE IF EXISTS carton;
CREATE TABLE IF NOT EXISTS carton (
  id int(11) NOT NULL AUTO_INCREMENT,
  credito_id int(11) NOT NULL,
  nro_planilla int(11) NOT NULL,
  cliente_id int(11) NOT NULL,
  cliente_nombre varchar(50) NOT NULL,
  ultimo_pago timestamp NULL DEFAULT NULL,
  cuotas_adeudadas int(11) NOT NULL,
  cuotas_aCobrar int(11) NOT NULL,
  cant_cuotas_credito int(11) NOT NULL,
  importe_cancelado float NOT NULL DEFAULT 0,
  importe_ingresado float NOT NULL DEFAULT 0,
  estado varchar(50) NOT NULL DEFAULT 'CUOTA NO PAGADA',
  deuda float NOT NULL DEFAULT 0,
  importe_cuota float NOT NULL DEFAULT 0,
  nro_cuota int(11) NOT NULL,
  vencimiento timestamp NULL DEFAULT NULL,
  state varchar(50) NOT NULL DEFAULT 'ACTIVO',
  created_at timestamp NOT NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  KEY credito_id (credito_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Disparadores `carton`
--
DELIMITER $$
CREATE TRIGGER `carton_create` BEFORE INSERT ON `carton` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `carton_update` BEFORE UPDATE ON `carton` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table cliente
--

DROP TABLE IF EXISTS cliente;
CREATE TABLE IF NOT EXISTS cliente (
  id int(11) NOT NULL AUTO_INCREMENT,
  nombre varchar(50) DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  dni int(11) DEFAULT NULL,
  fechaNacimiento date DEFAULT NULL,
  esSolicitante bit(1) DEFAULT NULL,
  documentacion varchar(20) DEFAULT NULL,
  limite_credito float DEFAULT NULL,
  referencia varchar(100) DEFAULT NULL,
  numero int(11) DEFAULT NULL,
  direccion_id int(11) DEFAULT NULL,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  tipo_dni varchar(20) DEFAULT NULL,
  observaciones varchar(50) DEFAULT NULL,
  estadoCivil varchar(50) DEFAULT NULL,
  nacionalidad varchar(50) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY direccion_id (direccion_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table cliente
--

INSERT INTO cliente (id, nombre, state, dni, fechaNacimiento, esSolicitante, documentacion, limite_credito, referencia, numero, direccion_id, created_at, updated_at, tipo_dni, observaciones, estadoCivil, nacionalidad) VALUES
(1, 'CLIENTE11', 'ACTIVO', 40979512, '2000-03-30', b'1', 'OTROS', NULL, 'ASD', 123, 13, '2021-03-30 13:08:22', NULL, 'DNI', '', 'CASADO', 'ARGENTINA'),
(2, 'CONYUGUEC11', 'ACTIVO', 512312351, '2000-04-30', b'0', 'null', NULL, 'NULL', 123, 1, '2021-03-30 13:08:22', NULL, 'DNI', 'ES UN CONYUGUE', 'CASADO', 'ARGENTINA'),
(3, 'SAA JUAN', 'ACTIVO', 33223, '2021-05-28', b'1', 'OTROS', NULL, 'DEBERIA SER MAYUS', 3213, 11, '2021-05-28 03:34:56', NULL, 'DNI', 'ADASDADÁ', 'SOLTERO', 'ARGENTINA');

-- --------------------------------------------------------

--
-- Table structure for table comercio
--

DROP TABLE IF EXISTS comercio;
CREATE TABLE IF NOT EXISTS comercio (
  id int(11) NOT NULL AUTO_INCREMENT,
  cliente_id int(11) DEFAULT NULL,
  direccion_id int(11) DEFAULT NULL,
  rubro_id int(11) DEFAULT NULL,
  nombre varchar(50) DEFAULT NULL,
  referencia varchar(100) DEFAULT NULL,
  numero int(11) DEFAULT NULL,
  esPropietario tinyint(1) DEFAULT '0',
  direIdemProp tinyint(1) DEFAULT 0,
  zona varchar(20) DEFAULT NULL,
  cuit varchar(20) DEFAULT NULL,
  tipo_iva varchar(50) DEFAULT 'CONSUMIDOR FINAL',
  inicio_actividades timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  KEY cliente_id (cliente_id),
  KEY rubro_id (rubro_id),
  KEY direccion_id (direccion_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Disparadores `comercio`
--
DELIMITER $$
CREATE TRIGGER `comercio_create` BEFORE INSERT ON `comercio` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `comercio_update` BEFORE UPDATE ON `comercio` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;
--
-- Dumping data for table comercio
--

INSERT INTO comercio (id, cliente_id, direccion_id, rubro_id, nombre, referencia, numero, esPropietario, zona, cuit, tipo_iva, inicio_actividades, state, created_at, updated_at) VALUES
(1, 1, 1, 1, 'c1c1', 'asd', 123, 1, 'zona 2', '123213123', 'Consumidor Final', '2021-04-01 03:00:00', 'ACTIVO', '2021-04-02 20:11:04', '2021-04-02 20:11:04'),
(2, 1, 1, 1, 'c1c1', 'asd', 123, 1, 'zona 2', '123213123', 'Consumidor Final', '2021-04-01 03:00:00', 'ACTIVO', '2021-04-02 20:12:14', '2021-04-02 20:12:14'),
(4, 1, 13, 1, '', 'ASD', 123, 1, 'zona 1', '13313123', 'Consumidor Final', '2021-06-30 01:34:27', 'ACTIVO', '2021-06-30 01:34:54', '2021-06-30 01:35:11');

-- --------------------------------------------------------

--
-- Table structure for table contactos
--

DROP TABLE IF EXISTS contactos;
CREATE TABLE IF NOT EXISTS contactos (
  id int(11) NOT NULL AUTO_INCREMENT,
  id_persona int(11) NOT NULL,
  contacto varchar(50) NOT NULL,
  nombre varchar(50) DEFAULT NULL,
  cargo varchar(50) NULL DEFAULT NULL,
  tipo varchar(20) DEFAULT NULL,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  tipo_persona varchar(50) NOT NULL DEFAULT 'CLIENTE',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Disparadores `contactos`
--
DELIMITER $$
CREATE TRIGGER `contactos_create` BEFORE INSERT ON `contactos` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `contactos_update` BEFORE UPDATE ON `contactos` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;

--
-- Dumping data for table contactos
--

INSERT INTO contactos (id, id_persona, contacto, nombre, cargo, tipo, created_at, updated_at, state, tipo_persona) VALUES
(1, 1, '12345678', NULL, '', 'Telefono', '2021-02-02 17:36:28', '2021-02-02 17:36:28', 'ACTIVO', 'PROVEEDOR'),
(3, 6, '321313312', NULL, '', 'CELULAR', '2021-05-19 12:55:03', '2021-05-19 12:55:03', 'ACTIVO', 'EMPLEADO'),
(4, 7, '1231231', NULL, '', 'CELULAR', '2021-05-19 13:10:10', '2021-05-19 13:10:10', 'ACTIVO', 'EMPLEADO'),
(5, 8, '1223233', NULL, '', 'CELULAR', '2021-05-19 13:11:10', '2021-05-19 13:11:10', 'ACTIVO', 'EMPLEADO'),
(6, 9, '3123132', NULL, '', 'CELULAR', '2021-05-22 15:22:43', '2021-05-22 15:22:43', 'ACTIVO', 'EMPLEADO'),
(16, 3, '6868678', 'promotor1', 'promotor', 'Telefono', '2021-06-12 02:25:51', '2021-06-12 02:25:51', 'ACTIVO', 'PROVEEDOR'),
(17, 3, '313', 'Nombre de Gerente', 'gerente', 'Telefono', '2021-06-12 02:25:51', '2021-06-12 02:25:51', 'ACTIVO', 'PROVEEDOR'),
(20, 4, '2613749295', 'Luis resentera', 'viajante', 'Telefono', '2021-06-23 23:43:16', '2021-06-23 23:43:16', 'ACTIVO', 'PROVEEDOR'),
(21, 4, 'luisresentera@otanohnos.com.ar', 'Luis resentera	', 'viajante', 'Correo', '2021-06-23 23:43:16', '2021-06-23 23:43:16', 'ACTIVO', 'PROVEEDOR');

-- --------------------------------------------------------

--
-- Table structure for table credito
--

DROP TABLE IF EXISTS credito;
CREATE TABLE IF NOT EXISTS credito (
  id int(11) NOT NULL AUTO_INCREMENT,
  cliente_id int(11) NOT NULL,
  fecha_aprobacion timestamp NULL DEFAULT NULL,
  estado varchar(50) DEFAULT 'PENDIENTE',
  cant_cuotas int(11) NOT NULL DEFAULT 0,
  importe_total float NOT NULL DEFAULT 0,
  importe_cuota float NOT NULL DEFAULT 0,
  importe_pri_cuota float NOT NULL DEFAULT 0,
  importe_deuda float NOT NULL DEFAULT 0,
  importe_credito float NOT NULL DEFAULT 0,
  saldo_anterior float NOT NULL DEFAULT 0,
  anticipo float NOT NULL DEFAULT 0,
  importe_ult_cuota float NOT NULL DEFAULT 0,
  comision float NOT NULL DEFAULT 0,
  tipo varchar(50) NOT NULL DEFAULT 'NORMAL',
  observacion text NOT NULL,
  venc_pri_cuota timestamp NULL DEFAULT NULL,
  venc_credito timestamp NULL DEFAULT NULL,
  state varchar(50) NOT NULL DEFAULT 'ACTIVO',
  created_at timestamp NOT NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  zona int(11) DEFAULT NULL,
  comercio_id int(11) NOT NULL,
  conyugue_id int(11) NOT NULL,
  direccionActual_id int(11) NOT NULL,
  mercaderia_entregada tinyint(1) NOT NULL DEFAULT 0,
  fecha_solicitud timestamp NOT NULL DEFAULT '1970-01-01 00:00:01',
  fecha_credito timestamp NOT NULL DEFAULT '1970-01-01 00:00:01',
  nro_solicitud int(11) NOT NULL,
  cuota_id int(11) DEFAULT -1,
  cobrador_id INT NOT NULL DEFAULT -1,
  PRIMARY KEY (id),
  KEY credito_ibfk_1 (cliente_id),
  KEY credito_ibfk_2 (comercio_id),
  KEY credito_ibfk_3 (cobrador_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Disparadores `credito`
--
DELIMITER $$
CREATE TRIGGER `credito_create` BEFORE INSERT ON `credito` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `credito_update` BEFORE UPDATE ON `credito` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;


--
-- Dumping data for table credito
--

INSERT INTO credito (id, cliente_id, fecha_aprobacion, estado, cant_cuotas, importe_total, importe_cuota, importe_pri_cuota, importe_deuda, importe_credito, saldo_anterior, anticipo, importe_ult_cuota, comision, tipo, observacion, venc_pri_cuota, venc_credito, state, created_at, updated_at, zona, comercio_id, conyugue_id, direccionActual_id, mercaderia_entregada, fecha_solicitud, fecha_credito, nro_solicitud, cuota_id) VALUES
(3, 1, NULL, 'EMITIDA', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'SOLICITUD', '12313', NULL, NULL, 'ACTIVO', '2021-04-02 20:13:47', '2021-04-04 18:16:51', NULL, 2, -1, 1, 0, '2021-04-02 20:12:14', '1970-01-01 00:00:01', 1, -1),
(4, 1, NULL, 'PENDIENTE', 150, 15000, 100, 100, 15000, 15000, 0, 0, 100, 10, 'CREDITO', '12313', NULL, NULL, 'ACTIVO', '2021-04-02 20:14:09', '2021-04-03 23:10:35', NULL, 2, -1, 1, 0, '2021-04-02 20:12:14', '2021-04-02 03:00:00', 1, 2),
(5, 1, NULL, 'PENDIENTE', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'SOLICITUD', 'asd', NULL, NULL, 'ACTIVO', '2021-04-06 05:32:25', '2021-04-10 20:12:24', NULL, 2, -1, 1, 0, '2021-04-06 05:32:25', '1970-01-01 00:00:01', 2, -1);

-- --------------------------------------------------------

--
-- Table structure for table cuota
--

DROP TABLE IF EXISTS cuota;
CREATE TABLE IF NOT EXISTS cuota (
  id int(11) NOT NULL AUTO_INCREMENT,
  tipo varchar(20) DEFAULT 'diario',
  cantidad int(11) DEFAULT NULL,
  dia int(11) NOT NULL DEFAULT 1,
  mes int(11) NOT NULL DEFAULT 0,
  porcentaje_extra float DEFAULT 0,
  state varchar(20) NOT NULL DEFAULT 'ACTIVO',
  created_at timestamp NOT NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Disparadores `cuota`
--
DELIMITER $$
CREATE TRIGGER `cuota_create` BEFORE INSERT ON `cuota` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `cuota_update` BEFORE UPDATE ON `cuota` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;
--
-- Dumping data for table cuota
--

INSERT INTO cuota (id, tipo, cantidad, dia, mes, porcentaje_extra, state, created_at, updated_at) VALUES
(2, 'DIARIA', 150, 1, 0, 0, 'ACTIVO', '2021-02-09 14:24:01', '2021-04-02 20:49:32');

-- --------------------------------------------------------

--
-- Table structure for table devolucion
--

DROP TABLE IF EXISTS devolucion;
CREATE TABLE IF NOT EXISTS devolucion (
  id int(11) NOT NULL AUTO_INCREMENT,
  art_venta_part_id int(11) NOT NULL,
  nro_serie text NOT NULL,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  PRIMARY KEY (id),
  KEY art_venta_part_id (art_venta_part_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Disparadores `devolucion`
--
DELIMITER $$
CREATE TRIGGER `devolucion_create` BEFORE INSERT ON `devolucion` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `devolucion_update` BEFORE UPDATE ON `devolucion` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table devolucion_cred
--

DROP TABLE IF EXISTS devolucion_cred;
CREATE TABLE IF NOT EXISTS devolucion_cred (
  id int(11) NOT NULL AUTO_INCREMENT,
  art_cred_id int(11) NOT NULL,
  nro_serie text NOT NULL,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  PRIMARY KEY (id),
  KEY art_cred_id (art_cred_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Disparadores `devolucion_cred`
--
DELIMITER $$
CREATE TRIGGER `devolucion_cred_create` BEFORE INSERT ON `devolucion_cred` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `devolucion_cred_update` BEFORE UPDATE ON `devolucion_cred` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;


-- --------------------------------------------------------

--
-- Table structure for table direccion
--

DROP TABLE IF EXISTS direccion;
CREATE TABLE IF NOT EXISTS direccion (
  id int(11) NOT NULL AUTO_INCREMENT,
  nombre varchar(50) NOT NULL,
  barrio_id int(11) DEFAULT NULL,
  created_at timestamp NOT NULL DEFAULT current_timestamp(),
  updated_at timestamp NOT NULL DEFAULT '1970-01-01 00:00:01',
  state varchar(20) NOT NULL DEFAULT 'ACTIVO',
  PRIMARY KEY (id),
  KEY barrio_id (barrio_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Disparadores `direccion`
--
DELIMITER $$
CREATE TRIGGER `direccion_create` BEFORE INSERT ON `direccion` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `direccion_update` BEFORE UPDATE ON `direccion` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;

--
-- Dumping data for table direccion
--

INSERT INTO direccion (id, nombre, barrio_id, created_at, updated_at, state) VALUES
(1, 'No definido', 1, '2021-02-02 17:36:01', '2021-02-02 17:36:01', 'ACTIVO'),
(2, 'Calle siempre viva', 1, '2021-02-02 17:36:01', '2021-02-02 17:36:01', 'ACTIVO'),
(3, 'No definido', 2, '2021-03-30 19:11:17', '2021-03-30 19:11:17', 'ACTIVO'),
(4, 'No definido', 3, '2021-03-30 19:11:26', '2021-03-30 19:11:26', 'ACTIVO'),
(5, 'No definido', 4, '2021-03-30 19:11:33', '2021-03-30 19:11:33', 'ACTIVO'),
(6, 'esteban adaro', 4, '2021-03-30 19:11:41', '2021-03-30 19:11:41', 'ACTIVO'),
(7, 'No definido', 5, '2021-05-19 12:53:24', '2021-05-19 12:53:24', 'ACTIVO'),
(8, 'No definido', 6, '2021-05-19 12:53:35', '2021-05-19 12:53:35', 'ACTIVO'),
(9, 'No definido', 7, '2021-05-19 12:53:52', '2021-05-19 12:53:52', 'ACTIVO'),
(10, 'No definido', 8, '2021-05-19 12:53:59', '2021-05-19 12:53:59', 'ACTIVO'),
(11, 'santa fe', 8, '2021-05-19 12:54:06', '2021-05-19 12:54:06', 'ACTIVO'),
(12, 'BUENOS AIRES', 8, '2021-06-05 02:04:27', '2021-06-05 02:04:27', 'ACTIVO'),
(13, 'BUENOS AIRES', 8, '2021-06-05 02:05:17', '2021-06-05 02:05:17', 'ACTIVO'),
(14, 'No definido', 9, '2021-06-12 02:16:53', '2021-06-12 02:16:53', 'ACTIVO'),
(15, 'No definido', 10, '2021-06-12 02:17:15', '2021-06-12 02:17:15', 'ACTIVO'),
(16, 'No definido', 11, '2021-06-12 02:17:28', '2021-06-12 02:17:28', 'ACTIVO'),
(17, 'SAN MARTIN ', 11, '2021-06-12 02:17:38', '2021-06-12 02:17:38', 'ACTIVO'),
(18, 'No definido', 12, '2021-06-19 14:01:27', '2021-06-19 14:01:27', 'ACTIVO'),
(19, 'No definido', 13, '2021-06-19 14:01:58', '2021-06-19 14:01:58', 'ACTIVO'),
(20, 'AV LAS PALMERAS', 13, '2021-06-19 14:02:14', '2021-06-19 14:02:14', 'ACTIVO');

-- --------------------------------------------------------

--
-- Table structure for table empleado
--

DROP TABLE IF EXISTS empleado;
CREATE TABLE IF NOT EXISTS empleado (
  id int(11) NOT NULL AUTO_INCREMENT,
  nombre varchar(100) DEFAULT NULL,
  direccion_id int(11) DEFAULT NULL,
  numero_domicilio int(11) DEFAULT NULL,
  referencia varchar(100) DEFAULT NULL,
  tipo_doc varchar(50) DEFAULT NULL,
  numero_doc bigint(20) DEFAULT NULL,
  fechaNacimiento date DEFAULT NULL,
  cuil varchar(50) DEFAULT NULL,
  estado_civil varchar(50) DEFAULT NULL,
  categoria varchar(50) DEFAULT NULL,
  convenio varchar(50) DEFAULT NULL,
  aporte_os varchar(50) DEFAULT NULL,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  user varchar(20) DEFAULT NULL,
  pass varchar(20) DEFAULT NULL,
  tipo varchar(50) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY direccion_id (direccion_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Disparadores `empleado`
--
DELIMITER $$
CREATE TRIGGER `empleado_create` BEFORE INSERT ON `empleado` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `empleado_update` BEFORE UPDATE ON `empleado` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;


--
-- Dumping data for table empleado
--

INSERT INTO empleado (id, nombre, direccion_id, numero_domicilio, referencia, tipo_doc, numero_doc, fechaNacimiento, cuil, estado_civil, categoria, convenio, aporte_os, created_at, updated_at, state, `user`, pass, tipo) VALUES
(1, 'ADMIN1', NULL, NULL, NULL, 'DNI', 1111111, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-04 05:36:53', '2021-05-19 13:02:18', 'ACTIVO', 'admin1', 'admin1', 'ADMINISTRADOR'),
(2, 'COBRADOR1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-04 05:36:53', '2021-05-19 13:02:28', 'ACTIVO', 'cob1', 'cob1', 'COBRADOR'),
(4, 'VENDEDOR1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-04 05:37:44', '2021-05-19 13:02:36', 'ACTIVO', 'ven1', 'ven1', 'VENDEDOR'),
(5, 'GERENTE1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-04 05:37:44', '2021-05-19 13:02:45', 'ACTIVO', 'gen1', 'gen1', 'GERENTE'),
(6, 'SOY UN EMPLEADO', 11, 88, 'alguna referencia', 'DNI', 11122, '1990-05-19', '2131131231', 'CASADO', 'ASDSA', 'DADS', 'ADASDA', '2021-05-19 12:55:03', '2021-05-19 12:55:03', 'ACTIVO', 'soyunemp123', '123', 'VENDEDOR'),
(7, 'SOY EMPLEADO 2', 6, 321, '', 'DNI', 22222, '2021-05-19', '1234567', 'CASADO', '', '', '', '2021-05-19 13:10:10', '2021-05-19 13:10:10', 'ACTIVO', 'emp2-123', '123', 'VENDEDOR'),
(8, 'FRANCISCO', 6, 321231, '', 'DNI', 3333, '2021-05-19', '123546', 'CASADO', 'ASDADA', 'ADSADA', 'ASDA', '2021-05-19 13:11:10', '2021-05-19 13:11:10', 'ACTIVO', 'francisco-123', '123', 'VENDEDOR'),
(9, 'ESTE NO DEBE JODER', 11, 2131, 'wqeqewqeweqeqwewqeq		', 'DNI', 132213, '2021-05-22', '3465464565', 'CASADO', '', 'DASDSA', '', '2021-05-22 15:22:43', '2021-05-22 15:22:43', 'ACTIVO', 'algunno-123', '123', 'COBRADOR');

-- --------------------------------------------------------

--
-- Table structure for table factura_proveedor
--

DROP TABLE IF EXISTS factura_proveedor;
CREATE TABLE IF NOT EXISTS factura_proveedor (
  id int(11) NOT NULL AUTO_INCREMENT,
  nro_pedido int(50) DEFAULT NULL,
  nro_factura int(50) NOT NULL UNIQUE,
  total int(50) NOT NULL,
  costo_flete float NOT NULL DEFAULT 0,
  forma_pago varchar(20) DEFAULT 'EFECTIVO',
  fecha_pago date NULL DEFAULT NULL,
  fecha_factura date NULL DEFAULT NULL,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  estado varchar(50) NOT NULL DEFAULT 'PENDIENTE',
  proveedor_id int(11) DEFAULT NULL,
  tipo_factura varchar(50) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Disparadores `factura_proveedor`
--
DELIMITER $$
CREATE TRIGGER `factura_proveedor_create` BEFORE INSERT ON `factura_proveedor` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `factura_proveedor_update` BEFORE UPDATE ON `factura_proveedor` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;


-- --------------------------------------------------------

--
-- Table structure for table historial_precio_venta
--

DROP TABLE IF EXISTS historial_precio_venta;
CREATE TABLE IF NOT EXISTS historial_precio_venta (
  id int(11) NOT NULL AUTO_INCREMENT,
  stock_id int(11) NOT NULL,
  cuota_id int(11) NOT NULL,
  precio_venta int(11) NOT NULL,
  iva float DEFAULT NULL,
  observacion text DEFAULT NULL,
  created_at int(11) DEFAULT NULL,
  updated_at int(11) DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  PRIMARY KEY (id),
  KEY stock_id (stock_id),
  KEY cuota_id (cuota_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Disparadores `historial_precio_venta`
--
DELIMITER $$
CREATE TRIGGER `historial_precio_venta_create` BEFORE INSERT ON `historial_precio_venta` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `historial_precio_venta_update` BEFORE UPDATE ON `historial_precio_venta` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table localidad
--

DROP TABLE IF EXISTS localidad;
CREATE TABLE IF NOT EXISTS localidad (
  id int(11) NOT NULL AUTO_INCREMENT,
  nombre varchar(50) NOT NULL,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  provincia_id int(11) NOT NULL,
  codPostal varchar(20) DEFAULT NULL UNIQUE,
  PRIMARY KEY (id),
  KEY provincia_id (provincia_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Disparadores `localidad`
--
DELIMITER $$
CREATE TRIGGER `barrio_vacio` AFTER INSERT ON `localidad` FOR EACH ROW BEGIN 
INSERT INTO barrio(nombre,localidad_id) VALUES ("No definido",NEW.id);
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `localidad_create` BEFORE INSERT ON `localidad` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `localidad_update` BEFORE UPDATE ON `localidad` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;

--
-- Dumping data for table localidad
--

INSERT INTO localidad (id, nombre, created_at, updated_at, state, provincia_id, codPostal) VALUES
(1, 'No definido', '2021-02-02 17:36:01', '2021-02-02 17:36:01', 'ACTIVO', 1, NULL),
(2, 'No definido', '2021-03-30 19:11:17', '2021-03-30 19:11:17', 'ACTIVO', 2, NULL),
(3, 'ROSARIO', '2021-03-30 19:11:26', '2021-03-30 19:11:26', 'ACTIVO', 4, '2000'),
(4, 'No definido', '2021-05-19 12:53:24', '2021-05-19 12:53:24', 'ACTIVO', 3, NULL),
(5, 'No definido', '2021-05-19 12:53:35', '2021-05-19 12:53:35', 'ACTIVO', 4, NULL),
(6, 'SAN LUIS', '2021-05-19 12:53:52', '2021-05-31 00:31:25', 'ACTIVO', 2, '5700'),
(7, 'JUSTO DARACT ', '2021-06-12 02:16:53', '2021-06-12 02:16:53', 'ACTIVO', 2, '5738'),
(8, 'LA PUNTA', '2021-06-12 02:17:15', '2021-06-12 02:17:15', 'ACTIVO', 2, 'D5710');


-- --------------------------------------------------------

--
-- Table structure for table nota_pedido
--

DROP TABLE IF EXISTS nota_pedido;
CREATE TABLE IF NOT EXISTS nota_pedido (
  nro_pedido int(11) NOT NULL AUTO_INCREMENT,
  total int(50) DEFAULT 0,
  costo_flete float NOT NULL DEFAULT 0,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  estado varchar(50) NOT NULL DEFAULT 'PENDIENTE',
  proveedor_id int(11) DEFAULT NULL,
  PRIMARY KEY (nro_pedido)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Disparadores `nota_pedido`
--
DELIMITER $$
CREATE TRIGGER `nota_pedido_create` BEFORE INSERT ON `nota_pedido` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `nota_pedido_update` BEFORE UPDATE ON `nota_pedido` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;


--
-- Dumping data for table nota_pedido
--

INSERT INTO nota_pedido (nro_pedido, total, costo_flete, created_at, updated_at, state, estado, proveedor_id) VALUES
(1, 7622, 0, '2021-06-19 15:17:12', '2021-06-19 15:17:33', 'ACTIVO', 'PENDIENTE', 4),
(2, 8640, 0, '2021-06-19 15:19:27', '2021-06-19 15:19:45', 'ACTIVO', 'PENDIENTE', 4),
(3, 0, 0, '2021-06-19 15:21:07', '2021-06-19 15:21:07', 'ACTIVO', 'PENDIENTE', 4),
(4, 0, 0, '2021-06-24 00:12:18', '2021-06-24 00:12:18', 'ACTIVO', 'PENDIENTE', 4),
(5, 0, 0, '2021-06-24 00:18:55', '2021-06-24 00:18:55', 'ACTIVO', 'PENDIENTE', 4),
(6, 0, 0, '2021-06-24 00:18:58', '2021-06-24 00:18:58', 'ACTIVO', 'PENDIENTE', 4),
(7, 0, 0, '2021-06-24 00:25:18', '2021-06-24 00:25:18', 'ACTIVO', 'PENDIENTE', 4),
(8, 0, 0, '2021-06-24 00:25:32', '2021-06-24 00:25:32', 'ACTIVO', 'PENDIENTE', 4),
(9, 0, 0, '2021-06-24 00:25:35', '2021-06-24 00:25:35', 'ACTIVO', 'PENDIENTE', 4),
(10, 0, 0, '2021-06-24 00:25:55', '2021-06-24 00:25:55', 'ACTIVO', 'PENDIENTE', 4),
(11, 200, 0, '2021-06-24 00:27:31', '2021-06-24 00:27:41', 'ACTIVO', 'PENDIENTE', 4),
(12, 0, 0, '2021-06-24 00:32:34', '2021-06-24 00:32:34', 'ACTIVO', 'PENDIENTE', 4);

-- --------------------------------------------------------

--
-- Table structure for table pagos_planilla
--

DROP TABLE IF EXISTS pagos_planilla;
CREATE TABLE IF NOT EXISTS pagos_planilla (
  id int(15) NOT NULL AUTO_INCREMENT,
  id_planilla int(15) NOT NULL,
  id_tipo_pago int(15) NOT NULL,
  monto int(15) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table pais
--

DROP TABLE IF EXISTS pais;
CREATE TABLE IF NOT EXISTS pais (
  id int(11) NOT NULL AUTO_INCREMENT,
  nombre varchar(50) NOT NULL,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table planilla
--

DROP TABLE IF EXISTS planilla;
CREATE TABLE IF NOT EXISTS planilla (
  id int(11) NOT NULL AUTO_INCREMENT,
  cobrador_id int(11) NOT NULL,
  efectivo float NOT NULL DEFAULT 0,
  gastos float NOT NULL DEFAULT 0,
  total_rendicion float NOT NULL DEFAULT 0,
  cobranza_s_planilla float NOT NULL DEFAULT 0,
  importe_ingresado float NOT NULL DEFAULT 0,
  diferencia float NOT NULL DEFAULT 0,
  rendicion_s_planilla float NOT NULL DEFAULT 0,
  valores_entregados float NOT NULL DEFAULT 0,
  saldo float NOT NULL DEFAULT 0,
  cuotas_aCobrar int(11) NOT NULL DEFAULT 0,
  cant_cuotas_pagadas int(11) NOT NULL DEFAULT 0,
  cant_cuotas_adelantadas int(11) NOT NULL DEFAULT 0,
  observacion text DEFAULT NULL,
  venc_pri_cuota timestamp NULL DEFAULT NULL,
  ingresada tinyint(1) NOT NULL DEFAULT 0,
  state varchar(50) NOT NULL DEFAULT 'ACTIVO',
  created_at timestamp NOT NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  KEY cobrador_id (cobrador_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Disparadores `planilla`
--
DELIMITER $$
CREATE TRIGGER `planilla_create` BEFORE INSERT ON `planilla` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `planilla_update` BEFORE UPDATE ON `planilla` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table proveedores
--

DROP TABLE IF EXISTS proveedores;
CREATE TABLE IF NOT EXISTS proveedores (
  id int(11) NOT NULL AUTO_INCREMENT,
  proveedor varchar(50) DEFAULT NULL,
  direccion_id int(11) DEFAULT NULL,
  iva varchar(50) DEFAULT NULL,
  cuit bigint(20) DEFAULT NULL,
  ingreso_bruto varchar(50) DEFAULT NULL,
  saldo float DEFAULT 0,
  ultimo_pago float DEFAULT NULL,
  ultima_compra float DEFAULT NULL,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  observaciones text DEFAULT NULL,
  numero_domicilio int(11) DEFAULT NULL,
  referencia varchar(100) DEFAULT NULL,
  cbu varchar(50) DEFAULT NULL,
  numero varchar(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY direccion_id (direccion_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Disparadores `proveedores`
--
DELIMITER $$
CREATE TRIGGER `proveedores_create` BEFORE INSERT ON `proveedores` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `proveedores_update` BEFORE UPDATE ON `proveedores` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;
--
-- Dumping data for table proveedores
--

INSERT INTO proveedores (id, proveedor, direccion_id, iva, cuit, ingreso_bruto, saldo, ultimo_pago, ultima_compra, created_at, updated_at, state, observaciones, numero_domicilio, referencia, cbu, numero) VALUES
(1, 'hogarcito SA', 1, 'Responsable Inscripto', 204050609050, '0.0', 0, NULL, NULL, '2021-02-02 17:36:28', '2021-02-02 17:36:28', 'ACTIVO', 'Esto es una prueba', NULL, 'que se yo', '1231549798425198', '123'),
(2, 'Distribuidora Fresh', 2, 'Responsable Inscripto', 123554664831, '0.0', 0, NULL, NULL, '2021-02-02 17:36:28', '2021-02-02 17:36:28', 'ACTIVO', 'Esto es una segunda prueba', NULL, 'cerca de ahi', '0976543234125246', '456'),
(3, 'proveedor merino clauditooo', 16, 'Monotributo', 12321313213, '0.0', 0, NULL, NULL, '2021-06-12 02:23:43', '2021-06-12 02:25:51', 'ACTIVO', '', NULL, 'soy la referencia	', NULL, '3123'),
(4, 'enkem s.a.', 20, 'Responsable Inscripto', 30711190542, '0.0', 0, NULL, NULL, '2021-06-19 15:14:00', '2021-06-23 23:43:16', 'ACTIVO', 'Por compra contado 8 o 6 % de descuento-\nCheques 30 60 90 ', NULL, 'Lote 83 PIM', NULL, '1452');

-- --------------------------------------------------------

--
-- Table structure for table provincia
--

DROP TABLE IF EXISTS provincia;
CREATE TABLE IF NOT EXISTS provincia (
  id int(11) NOT NULL AUTO_INCREMENT,
  nombre varchar(50) NOT NULL,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  pais_id int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY pais_id (pais_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Disparadores `provincia`
--
DELIMITER $$
CREATE TRIGGER `localidad_vacio` AFTER INSERT ON `provincia` FOR EACH ROW BEGIN 
INSERT INTO localidad(nombre,provincia_id) VALUES ("No definido",NEW.id);
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `provincia_create` BEFORE INSERT ON `provincia` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `provincia_update` BEFORE UPDATE ON `provincia` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;


--
-- Dumping data for table provincia
--

INSERT INTO provincia (id, nombre, created_at, updated_at, state, pais_id) VALUES
(1, 'No definido', '2021-02-02 17:36:01', '2021-02-02 17:36:01', 'ACTIVO', 1),
(2, 'SAN LUIS', '2021-03-30 19:11:17', '2021-03-30 19:11:17', 'ACTIVO', 1),
(3, 'MENDOZA', '2021-05-19 12:53:24', '2021-05-19 12:53:24', 'ACTIVO', 1),
(4, 'SANTA FE', '2021-06-19 14:01:27', '2021-06-19 14:01:27', 'ACTIVO', 1);

-- --------------------------------------------------------

--
-- Table structure for table relacion
--

DROP TABLE IF EXISTS relacion;
CREATE TABLE IF NOT EXISTS relacion (
  id int(11) NOT NULL AUTO_INCREMENT,
  cliente1_id int(11) DEFAULT NULL,
  cliente2_id int(11) DEFAULT NULL,
  estadoCivil varchar(50) DEFAULT NULL,
  tipo varchar(50) DEFAULT NULL,
  state varchar(20) DEFAULT NULL,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  KEY cliente1_id (cliente1_id),
  KEY cliente2_id (cliente2_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table relacion
--

INSERT INTO relacion (id, cliente1_id, cliente2_id, estadoCivil, tipo, state, created_at, updated_at) VALUES
(1, 1, 2, 'CASADO', 'CONYUGE', 'ACTIVO', '2021-05-28 01:27:25', NULL);

-- --------------------------------------------------------

--
-- Table structure for table remito
--

DROP TABLE IF EXISTS remito;
CREATE TABLE IF NOT EXISTS remito (
  id int(11) NOT NULL AUTO_INCREMENT,
  fecha_emision timestamp NOT NULL DEFAULT current_timestamp(),
  fecha_entrega timestamp NULL DEFAULT NULL,
  credito_id int(11) NOT NULL,
  entregado tinyint(1) NOT NULL DEFAULT 0,
  anulado tinyint(1) NOT NULL DEFAULT 0,
  observacion int(11) DEFAULT NULL,
  nro_remito int(11) NOT NULL,
  fletero varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table renglon_credito
--

DROP TABLE IF EXISTS renglon_credito;
CREATE TABLE IF NOT EXISTS renglon_credito (
  id int(11) NOT NULL AUTO_INCREMENT,
  cantidad float NOT NULL DEFAULT 0,
  costo_prod float NOT NULL DEFAULT 0,
  importe_cuota float NOT NULL DEFAULT 0,
  sub_total float NOT NULL DEFAULT 0,
  credito_id int(11) NOT NULL,
  producto_id int(11) NOT NULL,
  nroSerie varchar(50) DEFAULT '',
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  PRIMARY KEY (id),
  KEY producto_id (producto_id),
  KEY credito_id (credito_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Disparadores `renglon_credito`
--
DELIMITER $$
CREATE TRIGGER `renglon_credito_create` BEFORE INSERT ON `renglon_credito` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `renglon_credito_update` BEFORE UPDATE ON `renglon_credito` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;
--
-- Dumping data for table renglon_credito
--

INSERT INTO renglon_credito (id, cantidad, costo_prod, importe_cuota, sub_total, credito_id, producto_id, nroSerie, created_at, updated_at, state) VALUES
(1, 3, 5000, 100, 15000, 4, 4, '', '2021-04-02 20:53:12', '2021-04-02 20:53:12', 'ACTIVO'),
(2, 5, 2000, 125.5, 18825, 5, 1, '', '2021-04-06 05:50:53', '2021-04-06 05:50:53', 'ACTIVO'),
(3, 1, 351, 6.1, 915, 5, 2, '', '2021-04-06 23:45:21', '2021-04-06 23:45:21', 'ACTIVO'),
(4, 5, 2000, 125.5, 18825, 5, 1, '', '2021-04-06 23:45:21', '2021-04-06 23:45:21', 'ACTIVO');

-- --------------------------------------------------------

--
-- Table structure for table renglon_factura
--

DROP TABLE IF EXISTS renglon_factura;
CREATE TABLE IF NOT EXISTS renglon_factura (
  id int(11) NOT NULL AUTO_INCREMENT,
  sub_total int(50) NOT NULL DEFAULT 0,
  nro_factura int(11) NOT NULL,
  producto_id int(11) NOT NULL,
  costo_prod int(50) NOT NULL DEFAULT 0,
  cantidad float NOT NULL DEFAULT 1,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  PRIMARY KEY (id),
  KEY producto_id (producto_id),
  KEY nro_factura (nro_factura)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Disparadores `renglon_factura`
--
DELIMITER $$
CREATE TRIGGER `renglon_factura_create` BEFORE INSERT ON `renglon_factura` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `renglon_factura_update` BEFORE UPDATE ON `renglon_factura` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;


-- --------------------------------------------------------

--
-- Table structure for table renglon_nota
--

DROP TABLE IF EXISTS renglon_nota;
CREATE TABLE IF NOT EXISTS renglon_nota (
  id int(11) NOT NULL AUTO_INCREMENT,
  sub_total float NOT NULL DEFAULT 0,
  nota_pedido_id int(11) NOT NULL,
  producto_id int(11) NOT NULL,
  costo_prod float NOT NULL DEFAULT 0,
  cantidad float DEFAULT NULL,
  created_at timestamp NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  state varchar(20) DEFAULT 'ACTIVO',
  cant_faltante int(50) NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  KEY producto_id (producto_id),
  KEY nota_pedido_id (nota_pedido_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Disparadores `renglon_nota`
--
DELIMITER $$
CREATE TRIGGER `renglon_nota_create` BEFORE INSERT ON `renglon_nota` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `renglon_nota_update` BEFORE UPDATE ON `renglon_nota` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;

--
-- Dumping data for table renglon_nota
--

INSERT INTO renglon_nota (id, sub_total, nota_pedido_id, producto_id, costo_prod, cantidad, created_at, updated_at, state, cant_faltante) VALUES
(1, 7176, 1, 5, 312, 23, '2021-06-19 15:17:33', '2021-06-19 15:17:33', 'ACTIVO', 23),
(2, 8640, 2, 4, 4320, 2, '2021-06-19 15:19:45', '2021-06-19 15:19:45', 'ACTIVO', 2),
(3, 200, 11, 6, 100, 2, '2021-06-24 00:27:41', '2021-06-24 00:27:41', 'ACTIVO', 2);

-- --------------------------------------------------------

--
-- Table structure for table renglon_remito
--

DROP TABLE IF EXISTS renglon_remito;
CREATE TABLE IF NOT EXISTS renglon_remito  (
  id int(11) NOT NULL AUTO_INCREMENT,
  renglon_id int(11) NOT NULL,
  remito_id int(11) NOT NULL,
  cantidad float NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table tipo_pago
--

DROP TABLE IF EXISTS tipo_pago;
CREATE TABLE IF NOT EXISTS tipo_pago (
  id int(11) NOT NULL AUTO_INCREMENT,
  nombre varchar(50) DEFAULT NULL,
  state varchar(50) NOT NULL DEFAULT 'ACTIVO',
  created_at timestamp NOT NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Disparadores `tipo_pago`
--
DELIMITER $$
CREATE TRIGGER `tipo_pago_create` BEFORE INSERT ON `tipo_pago` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `tipo_pago_update` BEFORE UPDATE ON `tipo_pago` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;
-- --------------------------------------------------------

--
-- Table structure for table unificacion
--

DROP TABLE IF EXISTS unificacion;
CREATE TABLE IF NOT EXISTS unificacion (
  id int(11) NOT NULL AUTO_INCREMENT,
  credito_id int(11) NOT NULL,
  unificado_id int(11) DEFAULT NULL,
  state varchar(20) NOT NULL DEFAULT 'ACTIVO',
  created_at timestamp NOT NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  KEY credito_id (credito_id),
  KEY unificado_id (unificado_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Disparadores `unificacion`
--
DELIMITER $$
CREATE TRIGGER `unificacion_create` BEFORE INSERT ON `unificacion` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table venta_particular
--

DROP TABLE IF EXISTS venta_particular;
CREATE TABLE IF NOT EXISTS venta_particular (
  id int(11) NOT NULL AUTO_INCREMENT,
  cliente_id int(11) NOT NULL,
  vendedor_id int(11) NOT NULL,
  tipo_vendedor int(11) NOT NULL,
  importe_total float NOT NULL DEFAULT 0,
  comision float NOT NULL DEFAULT 0,
  observacion text NOT NULL,
  venc_pri_cuota timestamp NULL DEFAULT NULL,
  state varchar(50) NOT NULL DEFAULT 'ACTIVO',
  created_at timestamp NOT NULL DEFAULT current_timestamp(),
  updated_at timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Disparadores `venta_particular`
--
DELIMITER $$
CREATE TRIGGER `venta_particular_create` BEFORE INSERT ON `venta_particular` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `venta_particular_update` BEFORE UPDATE ON `venta_particular` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table aprobaciones
--
ALTER TABLE aprobaciones
  ADD CONSTRAINT aprobaciones_ibfk_1 FOREIGN KEY (credito_id) REFERENCES credito (id),
  ADD CONSTRAINT aprobaciones_ibfk_2 FOREIGN KEY (empleado_id) REFERENCES empleado (id);

--
-- Constraints for table articulos
--
ALTER TABLE articulos
  ADD CONSTRAINT articulos_ibfk_1 FOREIGN KEY (rubro_id) REFERENCES art_rubro (id),
  ADD CONSTRAINT articulos_ibfk_2 FOREIGN KEY (proveedor_id) REFERENCES proveedores (id);

--
-- Constraints for table articulo_cuota
--
ALTER TABLE articulo_cuota
  ADD CONSTRAINT articulo_cuota_ibfk_1 FOREIGN KEY (articulos_id) REFERENCES articulos (id),
  ADD CONSTRAINT articulo_cuota_ibfk_2 FOREIGN KEY (cuota_id) REFERENCES cuota (id);

--
-- Constraints for table art_stock
--
ALTER TABLE art_stock
  ADD CONSTRAINT art_stock_ibfk_1 FOREIGN KEY (producto_id) REFERENCES articulos (id);

--
-- Constraints for table art_venta_part
--
ALTER TABLE art_venta_part
  ADD CONSTRAINT art_venta_part_ibfk_1 FOREIGN KEY (articulos_id) REFERENCES articulos (id),
  ADD CONSTRAINT art_venta_part_ibfk_2 FOREIGN KEY (venta_particular_id) REFERENCES venta_particular (id);

--
-- Constraints for table banco_proveedor
--
ALTER TABLE banco_proveedor
  ADD CONSTRAINT banco_proveedor_ibfk_1 FOREIGN KEY (proveedor_id) REFERENCES proveedores (id);

--
-- Constraints for table barrio
--
ALTER TABLE barrio
  ADD CONSTRAINT barrio_ibfk_1 FOREIGN KEY (localidad_id) REFERENCES localidad (id);

--
-- Constraints for table carton
--
ALTER TABLE carton
  ADD CONSTRAINT carton_ibfk_1 FOREIGN KEY (credito_id) REFERENCES credito (id);

--
-- Constraints for table cliente
--
ALTER TABLE cliente
  ADD CONSTRAINT cliente_ibfk_1 FOREIGN KEY (direccion_id) REFERENCES direccion (id);

--
-- Constraints for table comercio
--
ALTER TABLE comercio
  ADD CONSTRAINT comercio_ibfk_1 FOREIGN KEY (cliente_id) REFERENCES cliente (id),
  ADD CONSTRAINT comercio_ibfk_2 FOREIGN KEY (rubro_id) REFERENCES art_rubro (id),
  ADD CONSTRAINT comercio_ibfk_3 FOREIGN KEY (direccion_id) REFERENCES direccion (id);

--
-- Constraints for table credito
--
ALTER TABLE credito
  ADD CONSTRAINT credito_ibfk_1 FOREIGN KEY (cliente_id) REFERENCES cliente (id),
  ADD CONSTRAINT credito_ibfk_2 FOREIGN KEY (comercio_id) REFERENCES comercio (id),
  ADD CONSTRAINT credito_ibfk_3 FOREIGN KEY (cobrador_id) REFERENCES empleado (id);

--
-- Constraints for table devolucion
--
ALTER TABLE devolucion
  ADD CONSTRAINT devolucion_ibfk_1 FOREIGN KEY (art_venta_part_id) REFERENCES art_venta_part (id);

--
-- Constraints for table devolucion_cred
--
ALTER TABLE devolucion_cred
  ADD CONSTRAINT devolucion_cred_ibfk_1 FOREIGN KEY (art_cred_id) REFERENCES renglon_credito (id);

--
-- Constraints for table direccion
--
ALTER TABLE direccion
  ADD CONSTRAINT direccion_ibfk_1 FOREIGN KEY (barrio_id) REFERENCES barrio (id);

--
-- Constraints for table empleado
--
ALTER TABLE empleado
  ADD CONSTRAINT empleado_ibfk_1 FOREIGN KEY (direccion_id) REFERENCES direccion (id);

--
-- Constraints for table historial_precio_venta
--
ALTER TABLE historial_precio_venta
  ADD CONSTRAINT historial_precio_venta_ibfk_1 FOREIGN KEY (stock_id) REFERENCES art_stock (id),
  ADD CONSTRAINT historial_precio_venta_ibfk_2 FOREIGN KEY (cuota_id) REFERENCES cuota (id);

--
-- Constraints for table localidad
--
ALTER TABLE localidad
  ADD CONSTRAINT localidad_ibfk_1 FOREIGN KEY (provincia_id) REFERENCES provincia (id);

--
-- Constraints for table planilla
--
ALTER TABLE planilla
  ADD CONSTRAINT planilla_ibfk_1 FOREIGN KEY (cobrador_id) REFERENCES empleado (id);

--
-- Constraints for table proveedores
--
ALTER TABLE proveedores
  ADD CONSTRAINT proveedores_ibfk_1 FOREIGN KEY (direccion_id) REFERENCES direccion (id);

--
-- Constraints for table provincia
--
ALTER TABLE provincia
  ADD CONSTRAINT provincia_ibfk_1 FOREIGN KEY (pais_id) REFERENCES pais (id);

--
-- Constraints for table relacion
--
ALTER TABLE relacion
  ADD CONSTRAINT relacion_ibfk_1 FOREIGN KEY (cliente1_id) REFERENCES `cliente` (id),
  ADD CONSTRAINT relacion_ibfk_2 FOREIGN KEY (cliente2_id) REFERENCES `cliente` (id);
--
-- Filtros para la tabla `remito`
--
ALTER TABLE remito
  ADD CONSTRAINT remito_ibfk_1 FOREIGN KEY (credito_id) REFERENCES credito (id);

--
-- Filtros para la tabla `renglon_credito`
--
ALTER TABLE renglon_credito
  ADD CONSTRAINT renglon_credito_ibfk_1 FOREIGN KEY (producto_id) REFERENCES articulos (id),
  ADD CONSTRAINT renglon_credito_ibfk_2 FOREIGN KEY (credito_id) REFERENCES credito (id);

--
-- Constraints for table renglon_factura
--
ALTER TABLE renglon_factura
  ADD CONSTRAINT renglon_factura_ibfk_1 FOREIGN KEY (producto_id) REFERENCES articulos (id),
  ADD CONSTRAINT renglon_factura_ibfk_2 FOREIGN KEY (nro_factura) REFERENCES factura_proveedor (nro_factura);

--
-- Constraints for table renglon_nota
--
ALTER TABLE renglon_nota
  ADD CONSTRAINT renglon_nota_ibfk_1 FOREIGN KEY (producto_id) REFERENCES articulos (id),
  ADD CONSTRAINT renglon_nota_ibfk_2 FOREIGN KEY (nota_pedido_id) REFERENCES nota_pedido (nro_pedido);
COMMIT;
--
-- Filtros para la tabla `renglon_remito`
--
ALTER TABLE renglon_remito
  ADD CONSTRAINT renglon_remito_ibfk_1 FOREIGN KEY (renglon_id) REFERENCES renglon_credito (id),
  ADD CONSTRAINT renglon_remito_ibfk_2 FOREIGN KEY (remito_id) REFERENCES remito (id);



--
-- Disparadores `barrio`
--
DELIMITER $$
CREATE TRIGGER `barrio_create` BEFORE INSERT ON `barrio` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `barrio_update` BEFORE UPDATE ON `barrio` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `direccion_vacio` AFTER INSERT ON `barrio` FOR EACH ROW BEGIN 
INSERT INTO direccion(nombre,barrio_id) VALUES ("No definido",NEW.id);
END
$$
DELIMITER ;

--
-- Dumping data for table barrio
--



--
-- Disparadores `pais`
--
DELIMITER $$
CREATE TRIGGER `pais_create` BEFORE INSERT ON `pais` FOR EACH ROW SET NEW.created_at = NOW(), NEW.updated_at = NOW()
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `pais_update` BEFORE UPDATE ON `pais` FOR EACH ROW SET NEW.updated_at = NOW(), NEW.created_at = OLD.created_at
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `provincia_vacio` AFTER INSERT ON `pais` FOR EACH ROW BEGIN 
INSERT INTO provincia(nombre,pais_id) VALUES ("No definido",NEW.id);
END
$$
DELIMITER ;

--
-- Dumping data for table pais
--

INSERT INTO pais (id, nombre, created_at, updated_at, state) VALUES
(1, 'ARGENTINA', '2021-02-02 17:36:01', '2021-02-02 17:36:01', 'ACTIVO');

ALTER TABLE `factura_proveedor` CHANGE `total` `total` FLOAT NOT NULL;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
