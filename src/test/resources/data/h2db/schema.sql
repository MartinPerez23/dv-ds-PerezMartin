DROP TABLE IF EXISTS clientes;
  
CREATE TABLE clientes (
  cli_id INT AUTO_INCREMENT  PRIMARY KEY,
  cli_nombre VARCHAR(250) NOT NULL,
  cli_apellido VARCHAR(250) NOT NULL,
  cli_email VARCHAR(250) DEFAULT NOT NULL,
  cli_password VARCHAR(250) DEFAULT NOT NULL
);

DROP TABLE IF EXISTS ordenes;

CREATE TABLE ordenes (
  ord_id int NOT NULL,
  ord_cli_id int NOT NULL,
  ord_fecha datetime NOT NULL
);


DROP TABLE IF EXISTS productos;

CREATE TABLE productos (
  pro_id int NOT NULL,
  pro_nombre varchar(45) NOT NULL,
  pro_precio decimal(8,2) NOT NULL
);

DROP TABLE IF EXISTS orden_items;

CREATE TABLE orden_items (
  odi_id int AUTO_INCREMENT NOT NULL,
  odi_ord_id int NOT NULL,
  odi_prd_id int NOT NULL,
  odi_cantidad decimal(8,2) NOT NULL
);
