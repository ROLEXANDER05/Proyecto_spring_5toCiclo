-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 04-05-2025 a las 21:00:41
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdvibav.3`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_cargo`
--

CREATE TABLE `tbl_cargo` (
  `idcargo` bigint(20) NOT NULL,
  `nombrecargo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tbl_cargo`
--

INSERT INTO `tbl_cargo` (`idcargo`, `nombrecargo`) VALUES
(1, 'ADMINISTRADOR'),
(2, 'USUARIO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_cliente`
--

CREATE TABLE `tbl_cliente` (
  `id` int(11) NOT NULL,
  `dni` varchar(8) NOT NULL,
  `telefono` varchar(9) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tbl_cliente`
--

INSERT INTO `tbl_cliente` (`id`, `dni`, `telefono`, `apellido`, `email`, `nombre`) VALUES
(1, '78901234', '987654321', 'Pérez', 'ana.perez@example.com', 'Ana'),
(2, '45678901', '912345678', 'López', 'carlos.lopez@example.net', 'Carlos'),
(3, '12345678', '923456789', 'Martínez', 'sofia.martinez@example.org', 'Sofía'),
(4, '90123456', '934567890', 'Gonzales', 'mateo.gonzales@example.info', 'Mateo'),
(5, '67890123', '945678901', 'Rodríguez', 'camila.rodriguez@example.biz', 'Camila'),
(6, '70000005', '915200000', 'Gonzales', 'ari@gmai.com', 'Ariana '),
(7, '84561515', '922221110', 'Paredes', 'karim@gmai.com', 'Karim');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_detalle_venta`
--

CREATE TABLE `tbl_detalle_venta` (
  `cantidad_det_venta` int(11) DEFAULT NULL,
  `monto_det_venta` double DEFAULT NULL,
  `precio_det_venta` double DEFAULT NULL,
  `tbl_producto_idproducto` int(11) DEFAULT NULL,
  `codigo_detalle_venta` bigint(20) NOT NULL,
  `tbl_venta_codigo_venta` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tbl_detalle_venta`
--

INSERT INTO `tbl_detalle_venta` (`cantidad_det_venta`, `monto_det_venta`, `precio_det_venta`, `tbl_producto_idproducto`, `codigo_detalle_venta`, `tbl_venta_codigo_venta`) VALUES
(3, 77.97, 25.99, 1, 1, 1),
(2, 119.98, 59.99, 2, 2, 1),
(1, 39.99, 39.99, 3, 3, 1),
(3, 239.96999999999997, 79.99, 5, 4, 2),
(1, 39.99, 39.99, 3, 5, 2),
(3, 136.5, 45.5, 4, 6, 2),
(4, 100, 25, 6, 7, 3),
(2, 159.98, 79.99, 5, 8, 4),
(1, 59.99, 59.99, 2, 9, 4),
(1, 25.99, 25.99, 1, 10, 5),
(3, 136.5, 45.5, 4, 11, 5),
(1, 59.99, 59.99, 2, 12, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_empleado`
--

CREATE TABLE `tbl_empleado` (
  `dni` varchar(8) NOT NULL,
  `id` bigint(20) NOT NULL,
  `idcargo` bigint(20) NOT NULL,
  `telefono` varchar(9) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tbl_empleado`
--

INSERT INTO `tbl_empleado` (`dni`, `id`, `idcargo`, `telefono`, `apellido`, `email`, `nombre`, `password`, `username`) VALUES
('12345678', 1, 1, '963852741', 'Bazán Rivera', 'rosa@gmail.com', 'Rosa Georgina', '$2a$10$ehLiOGmN0.//cWKn5xxDZu8y9Id2P7hlex27HNvOHTI0tIrqtJDgq', 'admin'),
('75554012', 2, 2, '963025874', 'Villanueva', 'rol@gmail.com', 'Alexander', '$2a$10$3dUZDAdFM7K98YkTLcfPVuBqq3GbKe.0KfNXrg61brSAqKpnDSqAa', 'alex');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_producto`
--

CREATE TABLE `tbl_producto` (
  `idproducto` int(11) NOT NULL,
  `preproducto` double NOT NULL,
  `stockproducto` int(11) NOT NULL,
  `catproducto` varchar(20) NOT NULL,
  `nomproducto` varchar(50) NOT NULL,
  `desproducto` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tbl_producto`
--

INSERT INTO `tbl_producto` (`idproducto`, `preproducto`, `stockproducto`, `catproducto`, `nomproducto`, `desproducto`) VALUES
(1, 25.99, 96, 'Ropa Hombre', 'Camiseta Algodón Lisa', 'Camiseta básica de algodón suave y cómoda.'),
(2, 59.99, 71, 'Ropa Hombre', 'Pantalón Jean Recto', 'Pantalón de mezclilla corte recto clásico.'),
(3, 39.99, 48, 'Ropa Mujer', 'Blusa Floral Estampada', 'Blusa de tela ligera con estampado de flores.'),
(4, 45.5, 54, 'Ropa Mujer', 'Falda Midi Plisada', 'Falda de largo midi con pliegues elegantes.'),
(5, 79.99, 35, 'Ropa Unisex', 'Casaca Deportiva', 'Casaca ligera con cierre para hacer ejercicio.'),
(6, 25, 26, 'Ropa Hombre', 'Polo Camisero', 'Polo Camisero Talla S extra Ligera');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_venta`
--

CREATE TABLE `tbl_venta` (
  `monto` double DEFAULT NULL,
  `tbl_cliente_id` int(11) DEFAULT NULL,
  `codigo_venta` bigint(20) NOT NULL,
  `fecha_venta` datetime(6) DEFAULT NULL,
  `tbl_empleado_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tbl_venta`
--

INSERT INTO `tbl_venta` (`monto`, `tbl_cliente_id`, `codigo_venta`, `fecha_venta`, `tbl_empleado_id`) VALUES
(237.94, 1, 1, '2025-05-04 00:34:51.000000', 1),
(416.46, 2, 2, '2025-05-04 00:37:51.000000', 2),
(100, 6, 3, '2025-05-04 13:04:27.000000', 1),
(219.97, 4, 4, '2025-05-04 13:05:16.000000', 1),
(162.49, 3, 5, '2025-05-04 13:05:37.000000', 1),
(59.99, 2, 6, '2025-05-04 13:05:54.000000', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tbl_cargo`
--
ALTER TABLE `tbl_cargo`
  ADD PRIMARY KEY (`idcargo`);

--
-- Indices de la tabla `tbl_cliente`
--
ALTER TABLE `tbl_cliente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKtejkupvjxtfm3np2324thtxc4` (`telefono`),
  ADD UNIQUE KEY `UK60sdd7i2fhb6mwyrmkk2d6u5k` (`email`);

--
-- Indices de la tabla `tbl_detalle_venta`
--
ALTER TABLE `tbl_detalle_venta`
  ADD PRIMARY KEY (`codigo_detalle_venta`),
  ADD KEY `FKnvrekd7x91wyixli4oagkil0u` (`tbl_producto_idproducto`),
  ADD KEY `FK1sf0oxbovm0kwa2ar6yel49wb` (`tbl_venta_codigo_venta`);

--
-- Indices de la tabla `tbl_empleado`
--
ALTER TABLE `tbl_empleado`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKjyk6c5ugyuwtfdoj08hfbucml` (`telefono`),
  ADD UNIQUE KEY `UKp27f9y9tnt772ox6a6dambgu4` (`email`),
  ADD UNIQUE KEY `UK4l4x7g0xyju19grr8by6bxrhm` (`username`),
  ADD KEY `FK4ygk4jfunp4amu9t7kcxh5q29` (`idcargo`);

--
-- Indices de la tabla `tbl_producto`
--
ALTER TABLE `tbl_producto`
  ADD PRIMARY KEY (`idproducto`);

--
-- Indices de la tabla `tbl_venta`
--
ALTER TABLE `tbl_venta`
  ADD PRIMARY KEY (`codigo_venta`),
  ADD KEY `FKse4ide26m0tnjg5ii9okvhvc0` (`tbl_cliente_id`),
  ADD KEY `FKb6m5f5wyqdrs3ge1bktp5ubgv` (`tbl_empleado_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tbl_cargo`
--
ALTER TABLE `tbl_cargo`
  MODIFY `idcargo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `tbl_cliente`
--
ALTER TABLE `tbl_cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `tbl_detalle_venta`
--
ALTER TABLE `tbl_detalle_venta`
  MODIFY `codigo_detalle_venta` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `tbl_empleado`
--
ALTER TABLE `tbl_empleado`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `tbl_producto`
--
ALTER TABLE `tbl_producto`
  MODIFY `idproducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `tbl_venta`
--
ALTER TABLE `tbl_venta`
  MODIFY `codigo_venta` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tbl_detalle_venta`
--
ALTER TABLE `tbl_detalle_venta`
  ADD CONSTRAINT `FK1sf0oxbovm0kwa2ar6yel49wb` FOREIGN KEY (`tbl_venta_codigo_venta`) REFERENCES `tbl_venta` (`codigo_venta`),
  ADD CONSTRAINT `FKnvrekd7x91wyixli4oagkil0u` FOREIGN KEY (`tbl_producto_idproducto`) REFERENCES `tbl_producto` (`idproducto`);

--
-- Filtros para la tabla `tbl_empleado`
--
ALTER TABLE `tbl_empleado`
  ADD CONSTRAINT `FK4ygk4jfunp4amu9t7kcxh5q29` FOREIGN KEY (`idcargo`) REFERENCES `tbl_cargo` (`idcargo`);

--
-- Filtros para la tabla `tbl_venta`
--
ALTER TABLE `tbl_venta`
  ADD CONSTRAINT `FKb6m5f5wyqdrs3ge1bktp5ubgv` FOREIGN KEY (`tbl_empleado_id`) REFERENCES `tbl_empleado` (`id`),
  ADD CONSTRAINT `FKse4ide26m0tnjg5ii9okvhvc0` FOREIGN KEY (`tbl_cliente_id`) REFERENCES `tbl_cliente` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
