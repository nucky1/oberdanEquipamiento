-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 18-05-2021 a las 14:06:24
-- Versión del servidor: 10.1.10-MariaDB
-- Versión de PHP: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bd-oberdan`
--
CREATE DATABASE IF NOT EXISTS `bd-oberdan` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `bd-oberdan`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aprobaciones`
--

CREATE TABLE `aprobaciones` (
  `id` int(11) NOT NULL,
  `credito_id` int(11) NOT NULL,
  `empleado_id` int(11) NOT NULL,
  `fecha` timestamp NULL DEFAULT NULL,
  `estado` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `art_rubro`
--

CREATE TABLE `art_rubro` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `art_rubro`
--

INSERT INTO `art_rubro` (`id`, `nombre`, `created_at`, `updated_at`, `state`) VALUES
(1, 'rubro_atr', '2021-05-14 17:15:38', '2021-05-14 17:15:38', 'ACTIVO');

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `art_stock`
--
-- ESTO ESTA MAL !
CREATE TABLE `art_stock` (
  `id` int(11) NOT NULL,
  `producto_id` int(11) NOT NULL,
  `stock_actual` float DEFAULT NULL,
  `stock_ingresado` float DEFAULT NULL,
  -- HAY QUE HACER DEFAULT 0
  `stock_pedido` float DEFAULT NULL,
  `stock_reservado` float DEFAULT NULL,
  `precio_compra` float NOT NULL DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO',
  `fecha_ult_compra` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `art_stock`
--

INSERT INTO `art_stock` (`id`, `producto_id`, `stock_actual`, `stock_ingresado`, `stock_pedido`, `stock_reservado`, `precio_compra`, `created_at`, `updated_at`, `state`, `fecha_ult_compra`) VALUES
(1, 1, 0, 0, 0, 0, 0, '2021-05-11 19:43:06', '2021-05-11 19:43:06', 'ACTIVO', '2021-05-11 19:43:02');

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `art_venta_part`
--

CREATE TABLE `art_venta_part` (
  `id` int(11) NOT NULL,
  `articulos_id` int(11) NOT NULL,
  `venta_particular_id` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `nro_serie` text NOT NULL,
  `state` varchar(50) NOT NULL DEFAULT 'ACTIVO',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '1970-01-01 00:00:01'
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
-- Estructura de tabla para la tabla `articulo_cuota`
--

CREATE TABLE `articulo_cuota` (
  `id` int(11) NOT NULL,
  `articulos_id` int(11) NOT NULL,
  `cuota_id` int(11) NOT NULL,
  `state` varchar(50) DEFAULT 'ACTIVO',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulos`
--
-- tipo 1 existente, 2 pedido 3 usado
CREATE TABLE `articulos` (
  `id` int(11) NOT NULL,
  `cod` int(11) NOT NULL DEFAULT '0',
  `nombre` varchar(50) NOT NULL DEFAULT '',
  `codigo_ean` varchar(50) DEFAULT NULL,
  `codigo_barra` varchar(50) DEFAULT NULL,
  `rubro_id` int(11) DEFAULT NULL,
  `stock_existente` float DEFAULT NULL,
  `stock_minimo` float DEFAULT NULL,
  `proveedor_id` int(11) NOT NULL DEFAULT '0',
  `iva` float DEFAULT '0',
  `observaciones` varchar(50) DEFAULT NULL,
  `costo_flete` float DEFAULT '0',
  `sobretasa_iva` float DEFAULT '0',
  `impuesto_interno` float DEFAULT '0',
  `impuesto_int_fijo` float DEFAULT '0',
  `precio_venta` float DEFAULT '0',
  `tipo` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO',
  `precio_compra` float NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `articulos`
--

INSERT INTO `articulos` (`id`, `cod`, `nombre`, `codigo_ean`, `codigo_barra`, `rubro_id`, `stock_existente`, `stock_minimo`, `proveedor_id`, `iva`, `observaciones`, `costo_flete`, `sobretasa_iva`, `impuesto_interno`, `impuesto_int_fijo`, `precio_venta`, `tipo`, `created_at`, `updated_at`, `state`, `precio_compra`) VALUES
(1, 1010, 'prueba update', NULL, NULL, NULL, 0, 10, 1, 0, '	', 0, 0, 0, 0, 0, 0, '2021-05-11 19:42:39', '2021-05-14 20:13:51', 'ACTIVO', 0),
(3, 5, 'producto intento 5', 'ean-1293129312', '12903210983', 1, 0, 1, 1, 1, 'observando cosas ', 1, 1, 1, 1, 150, 0, '2021-05-14 20:10:31', '2021-05-14 20:10:31', 'ACTIVO', 100);

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `barrio`
--

CREATE TABLE `barrio` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO',
  `localidad_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `barrio`
--

INSERT INTO `barrio` (`id`, `nombre`, `created_at`, `updated_at`, `state`, `localidad_id`) VALUES
(1, 'No definido', '2021-05-11 19:41:26', '2021-05-11 19:41:26', 'ACTIVO', 1);

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carton`
--

CREATE TABLE `carton` (
  `id` int(11) NOT NULL,
  `credito_id` int(11) NOT NULL,
  `nro_planilla` int(11) NOT NULL,
  `cliente_id` int(11) NOT NULL,
  `cliente_nombre` varchar(50) NOT NULL,
  `ultimo_pago` timestamp NULL DEFAULT NULL,
  `cuotas_adeudadas` int(11) NOT NULL,
  `cuotas_aCobrar` int(11) NOT NULL,
  `cant_cuotas_credito` int(11) NOT NULL,
  `importe_cancelado` float NOT NULL DEFAULT '0',
  `importe_ingresado` float NOT NULL DEFAULT '0',
  `estado` varchar(50) NOT NULL DEFAULT 'CUOTA NO PAGADA',
  `deuda` float NOT NULL DEFAULT '0',
  `importe_cuota` float NOT NULL DEFAULT '0',
  `nro_cuota` int(11) NOT NULL,
  `vencimiento` timestamp NULL DEFAULT NULL,
  `state` varchar(50) NOT NULL DEFAULT 'ACTIVO',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL
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
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO',
  `dni` int(11) DEFAULT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `esSolicitante` bit(1) DEFAULT NULL,
  `codPostal` int(11) DEFAULT NULL,
  `documentacion` varchar(20) DEFAULT NULL,
  `limite_credito` float DEFAULT NULL,
  `referencia` varchar(100) DEFAULT NULL,
  `numero` int(11) DEFAULT NULL,
  `direccion_id` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `tipo_dni` varchar(20) DEFAULT NULL,
  `observaciones` varchar(50) DEFAULT NULL,
  `estadoCivil` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comercio`
--

CREATE TABLE `comercio` (
  `id` int(11) NOT NULL,
  `cliente_id` int(11) DEFAULT NULL,
  `direccion_id` int(11) DEFAULT NULL,
  `rubro_id` int(11) DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `referencia` varchar(100) DEFAULT NULL,
  `numero` int(11) DEFAULT NULL,
  `esPropietario` tinyint(1) DEFAULT '0',
  `direIdemProp` tinyint(1) DEFAULT 0,
  `zona` varchar(20) DEFAULT NULL,
  `cuit` varchar(20) DEFAULT NULL,
  `tipo_iva` varchar(50) DEFAULT 'CONSUMIDOR FINAL',
  `inicio_actividades` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `codPostal` int(11) NOT NULL
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contactos`
--

CREATE TABLE `contactos` (
  `id` int(11) NOT NULL,
  `id_persona` int(11) NOT NULL,
  `contacto` varchar(50) NOT NULL,
   `nombre` varchar(50) DEFAULT NULL,
   `cargo` varchar(50) NOT NULL,
  `tipo` varchar(20) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO',
  `tipo_persona` varchar(50) NOT NULL DEFAULT 'CLIENTE'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `contactos`
--

INSERT INTO `contactos` (`id`, `id_persona`, `contacto`, `tipo`, `created_at`, `updated_at`, `state`, `tipo_persona`) VALUES
(1, 1, '123123', 'Telefono', '2021-05-11 19:41:44', '2021-05-11 19:41:44', 'ACTIVO', 'PROVEEDOR');

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credito`
--

CREATE TABLE `credito` (
  `id` int(11) NOT NULL,
  `cliente_id` int(11) NOT NULL,
  `fecha_aprobacion` timestamp NULL DEFAULT NULL,
  `estado` varchar(50) DEFAULT 'PENDIENTE',
  `cant_cuotas` int(11) NOT NULL DEFAULT '0',
  `importe_total` float NOT NULL DEFAULT '0',
  `importe_cuota` float NOT NULL DEFAULT '0',
  `importe_pri_cuota` float NOT NULL DEFAULT '0',
  `importe_deuda` float NOT NULL DEFAULT '0',
  `importe_credito` float NOT NULL DEFAULT '0',
  `saldo_anterior` float NOT NULL DEFAULT '0',
  `anticipo` float NOT NULL DEFAULT '0',
  `importe_ult_cuota` float NOT NULL DEFAULT '0',
  `comision` float NOT NULL DEFAULT '0',
  `tipo` varchar(50) NOT NULL DEFAULT 'NORMAL',
  `observacion` text NOT NULL,
  `venc_pri_cuota` timestamp NULL DEFAULT NULL,
  `state` varchar(50) NOT NULL DEFAULT 'ACTIVO',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `zona` int(11) DEFAULT NULL,
  `comercio_id` int(11) NOT NULL,
  `conyugue_id` int(11) NOT NULL,
  `direccionActual_id` int(11) NOT NULL,
  `mercaderia_entregada` tinyint(1) NOT NULL DEFAULT '0',
  `fecha_solicitud` timestamp NOT NULL DEFAULT '1970-01-01 00:00:01',
  `fecha_credito` timestamp NOT NULL DEFAULT '1970-01-01 00:00:01',
  `nro_solicitud` int(11) NOT NULL,
  `cuota_id` int(11) DEFAULT '-1',
  `venc_credito` timestamp NULL DEFAULT NULL
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuota`
--

CREATE TABLE `cuota` (
  `id` int(11) NOT NULL,
  `tipo` varchar(20) DEFAULT 'diario',
  `cantidad` int(11) DEFAULT NULL,
  `dia` int(11) NOT NULL DEFAULT '1',
  `mes` int(11) NOT NULL DEFAULT '0',
  `porcentaje_extra` float DEFAULT '0',
  `state` varchar(20) NOT NULL DEFAULT 'ACTIVO',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cuota`
--

INSERT INTO `cuota` (`id`, `tipo`, `cantidad`, `dia`, `mes`, `porcentaje_extra`, `state`, `created_at`, `updated_at`) VALUES
(1, 'DIARIA', 150, 1, 0, 15, 'ACTIVO', '2021-05-14 18:51:58', '2021-05-14 18:51:58'),
(2, 'DIARIA', 150, 1, 0, 15, 'ACTIVO', '2021-05-14 19:15:12', '2021-05-14 19:15:12'),
(3, 'DIARIA', 150, 1, 0, 30, 'ACTIVO', '2021-05-14 20:06:11', '2021-05-14 20:06:11'),
(4, 'SEMANAL', 60, 7, 0, 15, 'ACTIVO', '2021-05-14 20:08:44', '2021-05-14 20:08:44');

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `devolucion`
--

CREATE TABLE `devolucion` (
  `id` int(11) NOT NULL,
  `art_venta_part_id` int(11) NOT NULL,
  `nro_serie` text NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO'
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
-- Estructura de tabla para la tabla `devolucion_cred`
--

CREATE TABLE `devolucion_cred` (
  `id` int(11) NOT NULL,
  `art_cred_id` int(11) NOT NULL,
  `nro_serie` text NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO'
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
-- Estructura de tabla para la tabla `direccion`
--

CREATE TABLE `direccion` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `barrio_id` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '1970-01-01 00:00:01',
  `state` varchar(20) NOT NULL DEFAULT 'ACTIVO'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `direccion`
--

INSERT INTO `direccion` (`id`, `nombre`, `barrio_id`, `created_at`, `updated_at`, `state`) VALUES
(1, 'No definido', 1, '2021-05-11 19:41:26', '2021-05-11 19:41:26', 'ACTIVO');

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `direccion_id` int(11) DEFAULT NULL,
  `codPostal` varchar(20) DEFAULT NULL,
  `numero_domicilio` int(11) DEFAULT NULL,
  `referencia` varchar(100) DEFAULT NULL,
  `tipo_doc` varchar(50) DEFAULT NULL,
  `numero_doc` bigint(20) DEFAULT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `cuil` varchar(50) DEFAULT NULL,
  `estado_civil` varchar(50) DEFAULT NULL,
  `categoria` varchar(50) DEFAULT NULL,
  `convenio` varchar(50) DEFAULT NULL,
  `aporte_os` varchar(50) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO',
  `user` varchar(20) DEFAULT NULL,
  `pass` varchar(20) DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`id`, `nombre`, `direccion_id`, `codPostal`, `numero_domicilio`, `referencia`, `tipo_doc`, `numero_doc`, `fechaNacimiento`, `cuil`, `estado_civil`, `categoria`, `convenio`, `aporte_os`, `created_at`, `updated_at`, `state`, `user`, `pass`, `tipo`) VALUES
(1, 'Nicolas De Miguel', NULL, NULL, NULL, NULL, 'DNI', 4050123, NULL, '12345123451', NULL, NULL, NULL, NULL, '2021-05-11 19:39:08', '2021-05-11 19:39:08', 'ACTIVO', 'g1', 'g1', 'GERENTE');

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura_proveedor`
--

CREATE TABLE `factura_proveedor` (
  `id` int(11) NOT NULL,
  `nro_pedido` int(50) DEFAULT NULL,
  `nro_factura` int(50) NOT NULL,
  `total` int(50) NOT NULL,
  `costo_flete` float NOT NULL DEFAULT '0',
  `forma_pago` varchar(20) DEFAULT 'EFECTIVO',
  `fecha_pago` timestamp NULL DEFAULT NULL,
  `fecha_factura` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO',
  `estado` varchar(50) NOT NULL DEFAULT 'PENDIENTE',
  `proveedor_id` int(11) DEFAULT NULL,
  `tipo_factura` varchar(50) NOT NULL
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
-- Estructura de tabla para la tabla `historial_precio_venta`
--

CREATE TABLE `historial_precio_venta` (
  `id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  `cuota_id` int(11) NOT NULL,
  `precio_venta` int(11) NOT NULL,
  `iva` float DEFAULT NULL,
  `observacion` text,
  `created_at` int(11) DEFAULT NULL,
  `updated_at` int(11) DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO'
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
-- Estructura de tabla para la tabla `localidad`
--

CREATE TABLE `localidad` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO',
  `provincia_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `localidad`
--

INSERT INTO `localidad` (`id`, `nombre`, `created_at`, `updated_at`, `state`, `provincia_id`) VALUES
(1, 'No definido', '2021-05-11 19:41:26', '2021-05-11 19:41:26', 'ACTIVO', 1);

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nota_pedido`
--

CREATE TABLE `nota_pedido` (
  `nro_pedido` int(11) NOT NULL,
  `total` int(50) DEFAULT '0',
  `costo_flete` float NOT NULL DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO',
  `estado` varchar(50) NOT NULL DEFAULT 'PENDIENTE',
  `proveedor_id` int(11) DEFAULT NULL
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagos_planilla`
--

CREATE TABLE `pagos_planilla` (
  `id` int(15) NOT NULL,
  `id_planilla` int(15) NOT NULL,
  `id_tipo_pago` int(15) NOT NULL,
  `monto` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pais`
--

CREATE TABLE `pais` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pais`
--

INSERT INTO `pais` (`id`, `nombre`, `created_at`, `updated_at`, `state`) VALUES
(1, 'argentina', '2021-05-11 19:41:26', '2021-05-11 19:41:26', 'ACTIVO');

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `planilla`
--

CREATE TABLE `planilla` (
  `id` int(11) NOT NULL,
  `cobrador_id` int(11) NOT NULL,
  `nro_planilla` int(11) NOT NULL,
  `efectivo` float NOT NULL DEFAULT '0',
  `gastos` float NOT NULL DEFAULT '0',
  `total_rendicion` float NOT NULL DEFAULT '0',
  `cobranza_s_planilla` float NOT NULL DEFAULT '0',
  `importe_ingresado` float NOT NULL DEFAULT '0',
  `diferencia` float NOT NULL DEFAULT '0',
  `rendicion_s_planilla` float NOT NULL DEFAULT '0',
  `valores_entregados` float NOT NULL DEFAULT '0',
  `saldo` float NOT NULL DEFAULT '0',
  `cuotas_aCobrar` int(11) NOT NULL DEFAULT '0',
  `cant_cuotas_pagadas` int(11) NOT NULL,
  `cant_cuotas_adelantadas` int(11) NOT NULL,
  `observacion` text NOT NULL,
  `venc_pri_cuota` timestamp NULL DEFAULT NULL,
  `ingresada` tinyint(1) NOT NULL DEFAULT '0',
  `state` varchar(50) NOT NULL DEFAULT 'ACTIVO',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL
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
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `id` int(11) NOT NULL,
  `proveedor` varchar(50) DEFAULT NULL,
  `direccion_id` int(11) DEFAULT NULL,
  `codPostal` varchar(20) DEFAULT NULL,
  `iva` varchar(50) DEFAULT NULL,
  `cuit` bigint(20) DEFAULT NULL,
  `ingreso_bruto` varchar(50) DEFAULT NULL,
  `saldo` float DEFAULT '0',
  `ultimo_pago` float DEFAULT NULL,
  `ultima_compra` float DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO',
  `observaciones` text,
  `numero_domicilio` int(11) DEFAULT NULL,
  `referencia` varchar(100) DEFAULT NULL,
  `cbu` varchar(50) DEFAULT NULL,
  `numero` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `proveedores`
--

INSERT INTO `proveedores` (`id`, `proveedor`, `direccion_id`, `codPostal`, `iva`, `cuit`, `ingreso_bruto`, `saldo`, `ultimo_pago`, `ultima_compra`, `created_at`, `updated_at`, `state`, `observaciones`, `numero_domicilio`, `referencia`, `cbu`, `numero`) VALUES
(1, 'Proveedor 1', 1, '123', 'Responsable Inscripto', 123, '0.0', 0, NULL, NULL, '2021-05-11 19:41:44', '2021-05-11 19:41:44', 'ACTIVO', '', NULL, '123', '55123', '123');

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `provincia`
--

CREATE TABLE `provincia` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO',
  `pais_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `provincia`
--

INSERT INTO `provincia` (`id`, `nombre`, `created_at`, `updated_at`, `state`, `pais_id`) VALUES
(1, 'No definido', '2021-05-11 19:41:26', '2021-05-11 19:41:26', 'ACTIVO', 1);

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `relacion`
--

CREATE TABLE `relacion` (
  `id` int(11) NOT NULL,
  `cliente1_id` int(11) DEFAULT NULL,
  `cliente2_id` int(11) DEFAULT NULL,
  `estadoCivil` varchar(50) DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `remito`
--

CREATE TABLE `remito` (
  `id` int(11) NOT NULL,
  `fecha_emision` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_entrega` timestamp NULL DEFAULT NULL,
  `credito_id` int(11) NOT NULL,
  `entregado` tinyint(1) NOT NULL DEFAULT '0',
  `anulado` tinyint(1) NOT NULL DEFAULT '0',
  `observacion` text,
  `fletero` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `renglon_credito`
--

CREATE TABLE `renglon_credito` (
  `id` int(11) NOT NULL,
  `cantidad` float NOT NULL DEFAULT '0',
  `costo_prod` float NOT NULL DEFAULT '0',
  `importe_cuota` float NOT NULL DEFAULT '0',
  `sub_total` float NOT NULL DEFAULT '0',
  `credito_id` int(11) NOT NULL,
  `producto_id` int(11) NOT NULL,
  `nroSerie` varchar(50) DEFAULT '',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO'
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `renglon_factura`
--

CREATE TABLE `renglon_factura` (
  `id` int(11) NOT NULL,
  `sub_total` int(50) NOT NULL DEFAULT '0',
  `nro_factura` int(11) NOT NULL,
  `producto_id` int(11) NOT NULL,
  `costo_prod` int(50) NOT NULL DEFAULT '0',
  `cantidad` float NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO'
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
-- Estructura de tabla para la tabla `renglon_nota`
--

CREATE TABLE `renglon_nota` (
  `id` int(11) NOT NULL,
  `sub_total` float NOT NULL DEFAULT '0',
  `nota_pedido_id` int(11) NOT NULL,
  `producto_id` int(11) NOT NULL,
  `costo_prod` float NOT NULL DEFAULT '0',
  `cantidad` float DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `state` varchar(20) DEFAULT 'ACTIVO',
  `cant_faltante` int(50) NOT NULL DEFAULT '0'
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `renglon_remito`
--

CREATE TABLE `renglon_remito` (
  `id` int(11) NOT NULL,
  `renglon_id` int(11) NOT NULL,
  `remito_id` int(11) NOT NULL,
  `cantidad` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_pago`
--

CREATE TABLE `tipo_pago` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `state` varchar(50) NOT NULL DEFAULT 'ACTIVO',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL
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
-- Estructura de tabla para la tabla `unificacion`
--

CREATE TABLE `unificacion` (
  `id` int(11) NOT NULL,
  `credito_id` int(11) NOT NULL,
  `unificado_id` int(11) DEFAULT NULL,
  `state` varchar(20) NOT NULL DEFAULT 'ACTIVO',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL
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
-- Estructura de tabla para la tabla `venta_particular`
--

CREATE TABLE `venta_particular` (
  `id` int(11) NOT NULL,
  `cliente_id` int(11) NOT NULL,
  `vendedor_id` int(11) NOT NULL,
  `tipo_vendedor` int(11) NOT NULL,
  `importe_total` float NOT NULL DEFAULT '0',
  `comision` float NOT NULL DEFAULT '0',
  `observacion` text NOT NULL,
  `venc_pri_cuota` timestamp NULL DEFAULT NULL,
  `state` varchar(50) NOT NULL DEFAULT 'ACTIVO',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL
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
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `aprobaciones`
--
ALTER TABLE `aprobaciones`
  ADD PRIMARY KEY (`id`),
  ADD KEY `credito_id` (`credito_id`),
  ADD KEY `empleado_id` (`empleado_id`);

--
-- Indices de la tabla `art_rubro`
--
ALTER TABLE `art_rubro`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `art_stock`
--
ALTER TABLE `art_stock`
  ADD PRIMARY KEY (`id`),
  ADD KEY `producto_id` (`producto_id`);

--
-- Indices de la tabla `art_venta_part`
--
ALTER TABLE `art_venta_part`
  ADD PRIMARY KEY (`id`),
  ADD KEY `articulos_id` (`articulos_id`),
  ADD KEY `venta_particular_id` (`venta_particular_id`);

--
-- Indices de la tabla `articulo_cuota`
--
ALTER TABLE `articulo_cuota`
  ADD PRIMARY KEY (`id`),
  ADD KEY `articulos_id` (`articulos_id`),
  ADD KEY `cuota_id` (`cuota_id`);

--
-- Indices de la tabla `articulos`
--
ALTER TABLE `articulos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cod` (`cod`),
  ADD KEY `rubro_id` (`rubro_id`),
  ADD KEY `proveedor_id` (`proveedor_id`);

--
-- Indices de la tabla `barrio`
--
ALTER TABLE `barrio`
  ADD PRIMARY KEY (`id`),
  ADD KEY `localidad_id` (`localidad_id`);

--
-- Indices de la tabla `carton`
--
ALTER TABLE `carton`
  ADD PRIMARY KEY (`id`),
  ADD KEY `credito_id` (`credito_id`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`),
  ADD KEY `direccion_id` (`direccion_id`);

--
-- Indices de la tabla `comercio`
--
ALTER TABLE `comercio`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cliente_id` (`cliente_id`),
  ADD KEY `rubro_id` (`rubro_id`),
  ADD KEY `direccion_id` (`direccion_id`);

--
-- Indices de la tabla `contactos`
--
ALTER TABLE `contactos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `credito`
--
ALTER TABLE `credito`
  ADD PRIMARY KEY (`id`),
  ADD KEY `credito_ibfk_1` (`cliente_id`),
  ADD KEY `credito_ibfk_2` (`comercio_id`);

--
-- Indices de la tabla `cuota`
--
ALTER TABLE `cuota`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `devolucion`
--
ALTER TABLE `devolucion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `art_venta_part_id` (`art_venta_part_id`);

--
-- Indices de la tabla `devolucion_cred`
--
ALTER TABLE `devolucion_cred`
  ADD PRIMARY KEY (`id`),
  ADD KEY `art_cred_id` (`art_cred_id`);

--
-- Indices de la tabla `direccion`
--
ALTER TABLE `direccion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `barrio_id` (`barrio_id`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`id`),
  ADD KEY `direccion_id` (`direccion_id`);

--
-- Indices de la tabla `factura_proveedor`
--
ALTER TABLE `factura_proveedor`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `historial_precio_venta`
--
ALTER TABLE `historial_precio_venta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `stock_id` (`stock_id`),
  ADD KEY `cuota_id` (`cuota_id`);

--
-- Indices de la tabla `localidad`
--
ALTER TABLE `localidad`
  ADD PRIMARY KEY (`id`),
  ADD KEY `provincia_id` (`provincia_id`);

--
-- Indices de la tabla `nota_pedido`
--
ALTER TABLE `nota_pedido`
  ADD PRIMARY KEY (`nro_pedido`);

--
-- Indices de la tabla `pagos_planilla`
--
ALTER TABLE `pagos_planilla`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `pais`
--
ALTER TABLE `pais`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `planilla`
--
ALTER TABLE `planilla`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cobrador_id` (`cobrador_id`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`id`),
  ADD KEY `direccion_id` (`direccion_id`);

--
-- Indices de la tabla `provincia`
--
ALTER TABLE `provincia`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pais_id` (`pais_id`);

--
-- Indices de la tabla `relacion`
--
ALTER TABLE `relacion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cliente1_id` (`cliente1_id`),
  ADD KEY `cliente2_id` (`cliente2_id`);

--
-- Indices de la tabla `remito`
--
ALTER TABLE `remito`
  ADD PRIMARY KEY (`id`),
  ADD KEY `credito_id` (`credito_id`);

--
-- Indices de la tabla `renglon_credito`
--
ALTER TABLE `renglon_credito`
  ADD PRIMARY KEY (`id`),
  ADD KEY `producto_id` (`producto_id`),
  ADD KEY `credito_id` (`credito_id`);

--
-- Indices de la tabla `renglon_factura`
--
ALTER TABLE `renglon_factura`
  ADD PRIMARY KEY (`id`),
  ADD KEY `producto_id` (`producto_id`),
  ADD KEY `nro_factura` (`nro_factura`);

--
-- Indices de la tabla `renglon_nota`
--
ALTER TABLE `renglon_nota`
  ADD PRIMARY KEY (`id`),
  ADD KEY `producto_id` (`producto_id`),
  ADD KEY `nota_pedido_id` (`nota_pedido_id`);

--
-- Indices de la tabla `renglon_remito`
--
ALTER TABLE `renglon_remito`
  ADD PRIMARY KEY (`id`),
  ADD KEY `renglon_id` (`renglon_id`),
  ADD KEY `remito_id` (`remito_id`);

--
-- Indices de la tabla `tipo_pago`
--
ALTER TABLE `tipo_pago`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `unificacion`
--
ALTER TABLE `unificacion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `credito_id` (`credito_id`),
  ADD KEY `unificado_id` (`unificado_id`);

--
-- Indices de la tabla `venta_particular`
--
ALTER TABLE `venta_particular`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `aprobaciones`
--
ALTER TABLE `aprobaciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `art_rubro`
--
ALTER TABLE `art_rubro`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `art_stock`
--
ALTER TABLE `art_stock`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `art_venta_part`
--
ALTER TABLE `art_venta_part`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `articulo_cuota`
--
ALTER TABLE `articulo_cuota`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `articulos`
--
ALTER TABLE `articulos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `barrio`
--
ALTER TABLE `barrio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `carton`
--
ALTER TABLE `carton`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `comercio`
--
ALTER TABLE `comercio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `contactos`
--
ALTER TABLE `contactos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `credito`
--
ALTER TABLE `credito`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `cuota`
--
ALTER TABLE `cuota`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `devolucion`
--
ALTER TABLE `devolucion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `devolucion_cred`
--
ALTER TABLE `devolucion_cred`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `direccion`
--
ALTER TABLE `direccion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `empleado`
--
ALTER TABLE `empleado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `factura_proveedor`
--
ALTER TABLE `factura_proveedor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `historial_precio_venta`
--
ALTER TABLE `historial_precio_venta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `localidad`
--
ALTER TABLE `localidad`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `nota_pedido`
--
ALTER TABLE `nota_pedido`
  MODIFY `nro_pedido` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `pagos_planilla`
--
ALTER TABLE `pagos_planilla`
  MODIFY `id` int(15) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `pais`
--
ALTER TABLE `pais`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `planilla`
--
ALTER TABLE `planilla`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `provincia`
--
ALTER TABLE `provincia`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `relacion`
--
ALTER TABLE `relacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `remito`
--
ALTER TABLE `remito`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `renglon_credito`
--
ALTER TABLE `renglon_credito`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `renglon_factura`
--
ALTER TABLE `renglon_factura`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `renglon_nota`
--
ALTER TABLE `renglon_nota`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `renglon_remito`
--
ALTER TABLE `renglon_remito`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `tipo_pago`
--
ALTER TABLE `tipo_pago`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `unificacion`
--
ALTER TABLE `unificacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `venta_particular`
--
ALTER TABLE `venta_particular`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `aprobaciones`
--
ALTER TABLE `aprobaciones`
  ADD CONSTRAINT `aprobaciones_ibfk_1` FOREIGN KEY (`credito_id`) REFERENCES `credito` (`id`),
  ADD CONSTRAINT `aprobaciones_ibfk_2` FOREIGN KEY (`empleado_id`) REFERENCES `empleado` (`id`);

--
-- Filtros para la tabla `art_stock`
--
ALTER TABLE `art_stock`
  ADD CONSTRAINT `art_stock_ibfk_1` FOREIGN KEY (`producto_id`) REFERENCES `articulos` (`id`);

--
-- Filtros para la tabla `art_venta_part`
--
ALTER TABLE `art_venta_part`
  ADD CONSTRAINT `art_venta_part_ibfk_1` FOREIGN KEY (`articulos_id`) REFERENCES `articulos` (`id`),
  ADD CONSTRAINT `art_venta_part_ibfk_2` FOREIGN KEY (`venta_particular_id`) REFERENCES `venta_particular` (`id`);

--
-- Filtros para la tabla `articulo_cuota`
--
ALTER TABLE `articulo_cuota`
  ADD CONSTRAINT `articulo_cuota_ibfk_1` FOREIGN KEY (`articulos_id`) REFERENCES `articulos` (`id`),
  ADD CONSTRAINT `articulo_cuota_ibfk_2` FOREIGN KEY (`cuota_id`) REFERENCES `cuota` (`id`);

--
-- Filtros para la tabla `articulos`
--
ALTER TABLE `articulos`
  ADD CONSTRAINT `articulos_ibfk_1` FOREIGN KEY (`rubro_id`) REFERENCES `art_rubro` (`id`),
  ADD CONSTRAINT `articulos_ibfk_2` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedores` (`id`);

--
-- Filtros para la tabla `barrio`
--
ALTER TABLE `barrio`
  ADD CONSTRAINT `barrio_ibfk_1` FOREIGN KEY (`localidad_id`) REFERENCES `localidad` (`id`);

--
-- Filtros para la tabla `carton`
--
ALTER TABLE `carton`
  ADD CONSTRAINT `carton_ibfk_1` FOREIGN KEY (`credito_id`) REFERENCES `credito` (`id`);

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`direccion_id`) REFERENCES `direccion` (`id`);

--
-- Filtros para la tabla `comercio`
--
ALTER TABLE `comercio`
  ADD CONSTRAINT `comercio_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  ADD CONSTRAINT `comercio_ibfk_2` FOREIGN KEY (`rubro_id`) REFERENCES `art_rubro` (`id`),
  ADD CONSTRAINT `comercio_ibfk_3` FOREIGN KEY (`direccion_id`) REFERENCES `direccion` (`id`),
  ADD CONSTRAINT `comercio_ibfk_4` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`);

--
-- Filtros para la tabla `credito`
--
ALTER TABLE `credito`
  ADD CONSTRAINT `credito_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  ADD CONSTRAINT `credito_ibfk_2` FOREIGN KEY (`comercio_id`) REFERENCES `comercio` (`id`);

--
-- Filtros para la tabla `devolucion`
--
ALTER TABLE `devolucion`
  ADD CONSTRAINT `devolucion_ibfk_1` FOREIGN KEY (`art_venta_part_id`) REFERENCES `art_venta_part` (`id`);

--
-- Filtros para la tabla `devolucion_cred`
--
ALTER TABLE `devolucion_cred`
  ADD CONSTRAINT `devolucion_cred_ibfk_1` FOREIGN KEY (`art_cred_id`) REFERENCES `renglon_credito` (`id`);

--
-- Filtros para la tabla `direccion`
--
ALTER TABLE `direccion`
  ADD CONSTRAINT `direccion_ibfk_1` FOREIGN KEY (`barrio_id`) REFERENCES `barrio` (`id`);

--
-- Filtros para la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD CONSTRAINT `empleado_ibfk_1` FOREIGN KEY (`direccion_id`) REFERENCES `direccion` (`id`);

--
-- Filtros para la tabla `historial_precio_venta`
--
ALTER TABLE `historial_precio_venta`
  ADD CONSTRAINT `historial_precio_venta_ibfk_1` FOREIGN KEY (`stock_id`) REFERENCES `art_stock` (`id`),
  ADD CONSTRAINT `historial_precio_venta_ibfk_2` FOREIGN KEY (`cuota_id`) REFERENCES `cuota` (`id`);

--
-- Filtros para la tabla `localidad`
--
ALTER TABLE `localidad`
  ADD CONSTRAINT `localidad_ibfk_1` FOREIGN KEY (`provincia_id`) REFERENCES `provincia` (`id`);

--
-- Filtros para la tabla `planilla`
--
ALTER TABLE `planilla`
  ADD CONSTRAINT `planilla_ibfk_1` FOREIGN KEY (`cobrador_id`) REFERENCES `empleado` (`id`);

--
-- Filtros para la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD CONSTRAINT `proveedores_ibfk_1` FOREIGN KEY (`direccion_id`) REFERENCES `direccion` (`id`);

--
-- Filtros para la tabla `provincia`
--
ALTER TABLE `provincia`
  ADD CONSTRAINT `provincia_ibfk_1` FOREIGN KEY (`pais_id`) REFERENCES `pais` (`id`);

--
-- Filtros para la tabla `relacion`
--
ALTER TABLE `relacion`
  ADD CONSTRAINT `relacion_ibfk_1` FOREIGN KEY (`cliente1_id`) REFERENCES `cliente` (`id`),
  ADD CONSTRAINT `relacion_ibfk_2` FOREIGN KEY (`cliente2_id`) REFERENCES `cliente` (`id`);

--
-- Filtros para la tabla `remito`
--
ALTER TABLE `remito`
  ADD CONSTRAINT `remito_ibfk_1` FOREIGN KEY (`credito_id`) REFERENCES `credito` (`id`);

--
-- Filtros para la tabla `renglon_credito`
--
ALTER TABLE `renglon_credito`
  ADD CONSTRAINT `renglon_credito_ibfk_1` FOREIGN KEY (`producto_id`) REFERENCES `articulos` (`id`),
  ADD CONSTRAINT `renglon_credito_ibfk_2` FOREIGN KEY (`credito_id`) REFERENCES `credito` (`id`);

--
-- Filtros para la tabla `renglon_factura`
--
ALTER TABLE `renglon_factura`
  ADD CONSTRAINT `renglon_factura_ibfk_1` FOREIGN KEY (`producto_id`) REFERENCES `articulos` (`id`),
  ADD CONSTRAINT `renglon_factura_ibfk_2` FOREIGN KEY (`nro_factura`) REFERENCES `factura_proveedor` (`id`);

--
-- Filtros para la tabla `renglon_nota`
--
ALTER TABLE `renglon_nota`
  ADD CONSTRAINT `renglon_nota_ibfk_1` FOREIGN KEY (`producto_id`) REFERENCES `articulos` (`id`),
  ADD CONSTRAINT `renglon_nota_ibfk_2` FOREIGN KEY (`nota_pedido_id`) REFERENCES `nota_pedido` (`nro_pedido`);

--
-- Filtros para la tabla `renglon_remito`
--
ALTER TABLE `renglon_remito`
  ADD CONSTRAINT `renglon_remito_ibfk_1` FOREIGN KEY (`renglon_id`) REFERENCES `renglon_credito` (`id`),
  ADD CONSTRAINT `renglon_remito_ibfk_2` FOREIGN KEY (`remito_id`) REFERENCES `remito` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
