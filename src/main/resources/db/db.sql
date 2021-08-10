CREATE DATABASE IF NOT EXISTS `dv-ds-perezmartin`;

USE `dv-ds-perezmartin`;

DROP TABLE IF EXISTS `clientes`;

CREATE TABLE `clientes` (
  `cli_id` int NOT NULL AUTO_INCREMENT,
  `cli_nombre` varchar(50) NOT NULL,
  `cli_apellido` varchar(50) NOT NULL,
  `cli_email` varchar(50) NOT NULL,
  `cli_password` varchar(50) NOT NULL,
  PRIMARY KEY (`cli_id`),
  UNIQUE KEY `cli_email_UNIQUE` (`cli_email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `productos`;

CREATE TABLE `productos` (
  `pro_id` int NOT NULL AUTO_INCREMENT,
  `pro_nombre` varchar(45) NOT NULL,
  `pro_precio` decimal(10,2) NOT NULL,
  `pro_stock` int NOT NULL,
  PRIMARY KEY (`pro_id`),
  UNIQUE KEY `pro_nombre_UNIQUE` (`pro_nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `ordenes`;

CREATE TABLE `ordenes` (
  `ord_id` int NOT NULL AUTO_INCREMENT,
  `ord_cli_id` int NOT NULL,
  `ord_fecha` datetime NOT NULL,
  PRIMARY KEY (`ord_id`),
  KEY `ord_cli_id_fk_idx` (`ord_cli_id`),
  CONSTRAINT `ord_cli_id_fk` FOREIGN KEY (`ord_cli_id`) REFERENCES `clientes` (`cli_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `orden_items`;

CREATE TABLE `orden_items` (
  `odi_id` int NOT NULL AUTO_INCREMENT,
  `odi_ord_id` int NOT NULL,
  `odi_pro_id` int NOT NULL,
  `odi_cantidad` int NOT NULL,
  PRIMARY KEY (`odi_id`),
  KEY `odi_ord_id_fk_idx` (`odi_ord_id`),
  KEY `odi_pro_id_fk_idx` (`odi_pro_id`),
  CONSTRAINT `odi_ord_id_fk` FOREIGN KEY (`odi_ord_id`) REFERENCES `ordenes` (`ord_id`),
  CONSTRAINT `odi_pro_id_fk` FOREIGN KEY (`odi_pro_id`) REFERENCES `productos` (`pro_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;