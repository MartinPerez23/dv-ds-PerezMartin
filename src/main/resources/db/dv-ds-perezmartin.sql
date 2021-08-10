-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-08-2021 a las 14:54:38
-- Versión del servidor: 10.4.20-MariaDB
-- Versión de PHP: 8.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `dv-ds-perezmartin`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `cli_id` bigint(20) NOT NULL,
  `cli_email` varchar(255) DEFAULT NULL,
  `cli_apellido` varchar(30) NOT NULL,
  `cli_nombre` varchar(30) NOT NULL,
  `cli_password` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`cli_id`, `cli_email`, `cli_apellido`, `cli_nombre`, `cli_password`) VALUES
(2, 'juanjuan@davinci.edu.ar', 'juan', 'juanas', '$2a$10$msbnBWfcwepl80Nwu2PQ.u47H2aQT0i2OiIyMXA6dHABGifg078Q6'),
(3, 'juanjuan1@davinci.edu.ar', 'asdasdadsad', 'juana', '$2a$10$msbnBWfcwepl80Nwu2PQ.u47H2aQT0i2OiIyMXA6dHABGifg078Q6'),
(4, 'admin@admin.com', 'admin', 'admin', '$2a$10$msbnBWfcwepl80Nwu2PQ.u47H2aQT0i2OiIyMXA6dHABGifg078Q6');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes_roles`
--

CREATE TABLE `clientes_roles` (
  `users_cli_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `clientes_roles`
--

INSERT INTO `clientes_roles` (`users_cli_id`, `roles_id`) VALUES
(1, 1),
(1, 2),
(2, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordenes`
--

CREATE TABLE `ordenes` (
  `ord_id` bigint(20) NOT NULL,
  `ord_fecha` date NOT NULL,
  `ord_cli_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `ordenes`
--

INSERT INTO `ordenes` (`ord_id`, `ord_fecha`, `ord_cli_id`) VALUES
(1, '2020-02-10', 2),
(2, '2020-02-10', 2),
(3, '2020-02-11', 3),
(4, '2020-12-01', 3),
(5, '2020-02-11', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_items`
--

CREATE TABLE `orden_items` (
  `odi_id` bigint(20) NOT NULL,
  `odi_cantidad` int(11) NOT NULL,
  `odi_pro_id` bigint(20) DEFAULT NULL,
  `odi_ord_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `orden_items`
--

INSERT INTO `orden_items` (`odi_id`, `odi_cantidad`, `odi_pro_id`, `odi_ord_id`) VALUES
(1, 1, 1, 1),
(2, 1, 2, 1),
(3, 2, 6, 1),
(4, 1, 10, 2),
(5, 1, 7, 2),
(6, 1, 5, 3),
(7, 3, 8, 4),
(8, 1, 8, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `pro_id` bigint(20) NOT NULL,
  `pro_nombre` varchar(45) NOT NULL,
  `pro_precio` decimal(10,2) NOT NULL,
  `pro_stock` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`pro_id`, `pro_nombre`, `pro_precio`, `pro_stock`) VALUES
(1, 'teclado', '20000.00', 2),
(2, 'mouse', '5000.00', 20),
(3, 'monitor', '60000.00', 5),
(4, 'microfono', '10000.00', 10),
(5, 'auriculares', '6000.00', 1),
(6, 'placa de video', '100000.00', 15),
(7, 'procesador', '30000.00', 30),
(8, 'placa mother', '10000.00', 4),
(9, 'RAM', '5000.00', 50),
(10, 'gabinete', '15000.00', 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id`, `name`) VALUES
(1, 'comun'),
(2, 'admin');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`cli_id`),
  ADD UNIQUE KEY `UK7b87nbrm1jck1x3q1mj1nhj7b` (`cli_email`) USING HASH;

--
-- Indices de la tabla `clientes_roles`
--
ALTER TABLE `clientes_roles`
  ADD PRIMARY KEY (`users_cli_id`,`roles_id`),
  ADD KEY `FKex2ymrn0ctiba8x993buo66tp` (`roles_id`);

--
-- Indices de la tabla `ordenes`
--
ALTER TABLE `ordenes`
  ADD PRIMARY KEY (`ord_id`),
  ADD KEY `FK7y5pdvi44a762psyo9xs61q53` (`ord_cli_id`);

--
-- Indices de la tabla `orden_items`
--
ALTER TABLE `orden_items`
  ADD PRIMARY KEY (`odi_id`),
  ADD KEY `FKfrrxyiyy2sxkffpjwtullsvs` (`odi_pro_id`),
  ADD KEY `FKepwp0obj7hdxu5j0h543wd9ru` (`odi_ord_id`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`pro_id`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `cli_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `ordenes`
--
ALTER TABLE `ordenes`
  MODIFY `ord_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `orden_items`
--
ALTER TABLE `orden_items`
  MODIFY `odi_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `pro_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
