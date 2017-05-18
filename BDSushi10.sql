--Drops
DROP TABLE Producto;
DROP TABLE Pedido;
DROP TABLE Direccion;
DROP TABLE Usuario;
DROP TABLE NivelUsuario;
DROP TABLE Inventario;
DROP TABLE Cliente;
DROP TABLE NivelUsuario;

CREATE
  TABLE Usuario
  (
    idUsuario                  INTEGER NOT NULL,
    rut                 VARCHAR2 (45) NOT NULL UNIQUE,
    nombre              VARCHAR2 (15) NOT NULL ,
    apellidoPaterno     VARCHAR2 (15),
    pass                VARCHAR2 (45) NOT NULL ,
    email               VARCHAR2 (45) NOT NULL ,
    activado            VARCHAR2 (20) NOT NULL,
    NivelUsuario_idNivelUsuario INTEGER NOT NULL
  ) ;
ALTER TABLE Usuario ADD CONSTRAINT Usuario_PK PRIMARY KEY ( idUsuario ) ;


CREATE
  TABLE Direccion
  (
    idDireccion       INTEGER NOT NULL,
    comuna            VARCHAR2 (15) NOT NULL ,
    provincia         VARCHAR2 (15) NOT NULL ,
    region            VARCHAR2 (15) NOT NULL ,
    calle    VARCHAR2 (45) ,
    numero   VARCHAR2 (5) ,
    depto    VARCHAR2 (5) ,
    detalleDireccion  VARCHAR2 (45) ,
    Usuario_idUsuario INTEGER
  ) ;
ALTER TABLE Direccion ADD CONSTRAINT Direccion_PK PRIMARY KEY ( idDireccion ) ;


CREATE
  TABLE NivelUsuario
  (
    idNivelUsuario          INTEGER NOT NULL,
    nombreNivelUsuario      VARCHAR2 (45) NOT NULL ,
    descripcionNivelUsuario VARCHAR2 (45)
  ) ;
ALTER TABLE NivelUsuario ADD CONSTRAINT NivelUsuario_PK PRIMARY KEY (
idNivelUsuario ) ;


CREATE
  TABLE Pedido
  (
    idPedido          INTEGER NOT NULL,
    valor       INTEGER NOT NULL ,
    fecha      DATE NOT NULL ,
    estado      VARCHAR2 (45) NOT NULL ,
    detalle VARCHAR2(255) NOT NULL,
    Direccion_idDireccion INTEGER,
    Usuario_idUsuario INTEGER NOT NULL
  ) ;
ALTER TABLE Pedido ADD CONSTRAINT Pedido_PK PRIMARY KEY ( idPedido ) ;


CREATE
  TABLE Producto
  (
    idProducto       INTEGER NOT NULL,
    nombre   VARCHAR2 (45) NOT NULL ,
    cantidad INTEGER NOT NULL ,
    valor    INTEGER NOT NULL ,
    Pedido_idPedido  INTEGER,
    Inventario_idInventario  INTEGER
  ) ;
ALTER TABLE Producto ADD CONSTRAINT Producto_PK PRIMARY KEY ( idProducto ) ;


CREATE
  TABLE Inventario
  (
    idInventario INTEGER NOT NULL,
    fecha        DATE
  ) ;
ALTER TABLE Inventario ADD CONSTRAINT Inventario_PK PRIMARY KEY ( idInventario ) ;


ALTER TABLE Usuario ADD CONSTRAINT Usuario_NivelUsuario_FK FOREIGN KEY (
NivelUsuario_idNivelUsuario ) REFERENCES NivelUsuario ( idNivelUsuario ) ON
DELETE CASCADE ;

ALTER TABLE Direccion ADD CONSTRAINT Direccion_Usuario_FK FOREIGN KEY (
Usuario_idUsuario ) REFERENCES Usuario ( idUsuario ) ON
DELETE CASCADE ;

ALTER TABLE Pedido ADD CONSTRAINT Pedido_Usuario_FK FOREIGN KEY (
Usuario_idUsuario ) REFERENCES Usuario ( idUsuario ) ON
DELETE CASCADE ;

--Nueva FK de Direccion-Pedido
ALTER TABLE Pedido ADD CONSTRAINT Pedido_Direccion_FK FOREIGN KEY (
Direccion_idDireccion ) REFERENCES Direccion ( idDireccion ) ON
DELETE CASCADE ;


ALTER TABLE Producto ADD CONSTRAINT Producto_Pedido_FK FOREIGN KEY (
Pedido_idPedido ) REFERENCES Pedido ( idPedido ) ON
DELETE CASCADE ;


ALTER TABLE Producto ADD CONSTRAINT Producto_Inventario_FK FOREIGN KEY (
Inventario_idInventario ) REFERENCES Inventario ( idInventario ) ON
DELETE CASCADE ;

INSERT INTO NivelUsuario VALUES(1,'Administrador','Administrador');
INSERT INTO NivelUsuario VALUES(2,'Usuario','Usuario');
INSERT INTO NivelUsuario VALUES(3,'Encargado','Encargado');
INSERT INTO NivelUsuario VALUES(4,'Dueño','Dueño');
INSERT INTO NivelUsuario VALUES(5,'Cajero','Cajero');


--secuencias
CREATE SEQUENCE  "ADMINSUSHI"."SECUENCIA_USUARIO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE SEQUENCE  "ADMINSUSHI"."SECUENCIA_PRODUCTO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE SEQUENCE  "ADMINSUSHI"."SECUENCIA_DIRECCION"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE SEQUENCE  "ADMINSUSHI"."SECUENCIA_PEDIDO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
CREATE SEQUENCE  "ADMINSUSHI"."SECUENCIA_INVENTARIO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;

--Usuario Trigger
CREATE OR REPLACE TRIGGER usuario_auto
BEFORE INSERT ON Usuario
FOR EACH ROW

BEGIN
  SELECT SECUENCIA_USUARIO.NEXTVAL
  INTO   :new.idUsuario
  FROM   dual;
END;

--Producto Trigger
CREATE OR REPLACE TRIGGER producto_auto
BEFORE INSERT ON Producto
FOR EACH ROW

BEGIN
  SELECT SECUENCIA_PRODUCTO.NEXTVAL
  INTO   :new.idProducto
  FROM   dual;
END;

--Direccion Trigger
CREATE OR REPLACE TRIGGER direccion_auto
BEFORE INSERT ON Direccion
FOR EACH ROW

BEGIN
  SELECT SECUENCIA_DIRECCION.NEXTVAL
  INTO   :new.idDireccion
  FROM   dual;
END;

--Pedido Trigger
CREATE OR REPLACE TRIGGER pedido_auto
BEFORE INSERT ON Pedido
FOR EACH ROW

BEGIN
  SELECT SECUENCIA_PEDIDO.NEXTVAL
  INTO   :new.idPedido
  FROM   dual;
END;

--Inventario Trigger
CREATE OR REPLACE TRIGGER inventario_auto
BEFORE INSERT ON Inventario
FOR EACH ROW

BEGIN
  SELECT SECUENCIA_INVENTARIO.NEXTVAL
  INTO   :new.idInventario
  FROM   dual;
END;

--testing
insert into INVENTARIO values(0,sysdate);
