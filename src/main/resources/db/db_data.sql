CREATE DATABASE  IF NOT EXISTS `dv-ds-perezmartin`;
USE `dv-ds-perezmartin`;

LOCK TABLES `clientes` WRITE;

INSERT INTO `clientes` 
VALUES (1,'Juan','Perez','juan@pp.com','1234')
,(2,'Toto','Mengano','toto.mengano@pp.com','1234')
,(4,'Fulano','De Tal','fulanito@pp.com','1234')
,(9,'Pirulo','Mengano','pirulo.mengano@pp.com','1234')
,(15,'Fulanito','Black','fulanito.black@fulanito.com','1234')
,(16,'Fulanito','Gregory','fulanito.gregory@pp.com','1234');