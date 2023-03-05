-- MariaDB dump 10.19  Distrib 10.4.24-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: sistemaacomsal
-- ------------------------------------------------------
-- Server version	10.4.24-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria` (
  `idcategoria` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idcategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'GALLETA ');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientec`
--

DROP TABLE IF EXISTS `clientec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientec` (
  `noRegistro` varchar(30) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `dui` varchar(30) NOT NULL,
  `nit` varchar(40) NOT NULL,
  `nombreNegocio` varchar(250) NOT NULL,
  `direccion` varchar(250) NOT NULL,
  `giro` varchar(250) NOT NULL,
  `papelera` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`noRegistro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientec`
--

LOCK TABLES `clientec` WRITE;
/*!40000 ALTER TABLE `clientec` DISABLE KEYS */;
INSERT INTO `clientec` VALUES (' ',' ',' ',' ',' ',' ',' ',0),('1','empresa CON UN NOMBRE BASTANTE LARGO','1','1','1','1','1',0),('6456465-4','los mpresores s.a. de c.v. ','','4545-545454-544-4','los mpresores s.a. de c.v. ','san salvador c 2','venta de mauqinaria industrial al por menor ',0),('ANULADO','ANULADO','','ANULADO','','','',0);
/*!40000 ALTER TABLE `clientec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientef`
--

DROP TABLE IF EXISTS `clientef`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientef` (
  `dui` varchar(30) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `nombreNegocio` varchar(100) NOT NULL,
  `direccion` varchar(250) NOT NULL,
  `nit` varchar(40) DEFAULT NULL,
  `papelera` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`dui`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientef`
--

LOCK TABLES `clientef` WRITE;
/*!40000 ALTER TABLE `clientef` DISABLE KEYS */;
INSERT INTO `clientef` VALUES (' ',' ',' ',' ',' ',0),('1145121-0','cliente final ','1','1',NULL,0),('3465','emperatriz cliente','sdasad','asdsa',NULL,0);
/*!40000 ALTER TABLE `clientef` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `condicionpago`
--

DROP TABLE IF EXISTS `condicionpago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `condicionpago` (
  `idcondicionPago` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idcondicionPago`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condicionpago`
--

LOCK TABLES `condicionpago` WRITE;
/*!40000 ALTER TABLE `condicionpago` DISABLE KEYS */;
INSERT INTO `condicionpago` VALUES (1,'CONTADO '),(2,'CREDITO ');
/*!40000 ALTER TABLE `condicionpago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detallecompra`
--

DROP TABLE IF EXISTS `detallecompra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detallecompra` (
  `serie` varchar(50) NOT NULL,
  `noDcoumento` int(11) NOT NULL,
  `noLinea` int(11) NOT NULL,
  `idproducto` varchar(50) NOT NULL,
  `cantidad` double NOT NULL,
  `precioUnitario` double NOT NULL,
  `totalProd` double NOT NULL,
  `anulado` tinyint(1) NOT NULL DEFAULT 0,
  `borrado` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`serie`,`noDcoumento`,`noLinea`),
  KEY `fk_detallecompra_encabezadocompra2_idx` (`noDcoumento`),
  KEY `idproducto` (`idproducto`),
  KEY `serie` (`serie`),
  KEY `noDcoumento` (`noDcoumento`),
  CONSTRAINT `fk_detallecompra_producto1` FOREIGN KEY (`idproducto`) REFERENCES `producto` (`idproducto`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detallecompra`
--

LOCK TABLES `detallecompra` WRITE;
/*!40000 ALTER TABLE `detallecompra` DISABLE KEYS */;
INSERT INTO `detallecompra` VALUES ('1',1,0,'qqq',500,1,100,0,0),('1',1,2,'WFTX12aa',1000,1.2,1200,0,0);
/*!40000 ALTER TABLE `detallecompra` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_detallecompra_insert` AFTER INSERT ON `detallecompra` FOR EACH ROW BEGIN

  

 INSERT INTO `kardex`(`serie`, `noDcoumento`, `noLinea`, `idproducto`, `idtipotransaccion`, `fecha`, `entrada`, `salida`, costoTotalProd) VALUES  (new.serie,new.noDcoumento,new.noLinea, new.idproducto  ,2,now(), new.cantidad, 0, new.totalProd); 

   

   

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_detallecompra_update` AFTER UPDATE ON `detallecompra` FOR EACH ROW  BEGIN



    if new.anulado = true then 

   

    DELETE FROM kardex WHERE serie = new.serie AND noDcoumento = new.noDcoumento and idproducto = old.idproducto; 

    END IF; 



 CALL update_kardex_1 (new.serie, new.noDcoumento, old.serie, old.noDcoumento, new.cantidad, old.idproducto,0, new.totalProd); 

 

 

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `detalleventa`
--

DROP TABLE IF EXISTS `detalleventa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalleventa` (
  `serie` varchar(50) NOT NULL,
  `noDcoumento` int(11) NOT NULL,
  `noLinea` int(11) NOT NULL,
  `idproducto` varchar(50) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `cantidadimpresa` double NOT NULL,
  `unidadmedida` varchar(5) NOT NULL,
  `precioUnitario` double NOT NULL,
  `totalProd` double NOT NULL,
  `anulado` tinyint(1) NOT NULL DEFAULT 0,
  `borrado` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`noLinea`,`noDcoumento`,`serie`),
  KEY `fk_detalleventa_encabezadoventa_idx` (`serie`),
  KEY `fk_detalleventa_encabezadoventa1_idx` (`noDcoumento`),
  KEY `fk_detalleventa_producto1` (`idproducto`),
  CONSTRAINT `fk_detalleventa_producto1` FOREIGN KEY (`idproducto`) REFERENCES `producto` (`idproducto`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalleventa`
--

LOCK TABLES `detalleventa` WRITE;
/*!40000 ALTER TABLE `detalleventa` DISABLE KEYS */;
INSERT INTO `detalleventa` VALUES ('170DS000C',3,0,'WFTX12aa',18,1,'MASTE',26.548672566371685,26.55,0,0),('21DS000F',3,0,'qqq',45,1,'MASTE',30,30,0,0),('170DS000C',5,0,'qqq',45,1,'MASTE',26.548672566371685,26.55,0,0),('21DS000F',5,0,'WFTX12aa',1,1,'UNIDA',3.25,3.25,0,0),('170DS000C',6,0,'qqq',180,4,'MASTE',26.548672566371685,106.19,0,0),('21DS000F',6,0,'WFTX12aa',189,10.5,'MASTE',30,315,0,0),('170DS000C',7,0,'qqq',135,3,'MASTE',26.548672566371685,79.65,0,0),('21DS000F',7,0,'qqq',45,1,'MASTE',30,30,0,0),('21DS000F',8,0,'qqq',45,1,'MASTE',30,30,0,0),('21DS000F',3,1,'WFTX12aa',18,1,'MASTE',30,30,0,0),('21DS000F',7,1,'WFTX12aa',18,1,'MASTE',30,30,0,0);
/*!40000 ALTER TABLE `detalleventa` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_detalleventa_insert` AFTER INSERT ON `detalleventa` FOR EACH ROW BEGIN

    INSERT INTO `kardex`(`serie`, `noDcoumento`, `noLinea`, `idproducto`, `idtipotransaccion`, `fecha`, `entrada`, `salida`)

 VALUES  (new.serie,new.noDcoumento,new.noLinea, new.idproducto  ,1,now(), 0, new.cantidad); 

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_detalleventa_update` AFTER UPDATE ON `detalleventa` FOR EACH ROW BEGIN



    if new.anulado = true THEN 

   

        DELETE FROM kardex 

        WHERE serie = new.serie AND noDcoumento = new.noDcoumento and idproducto = old.idproducto; 

    END IF; 



 CALL update_kardex_1 (new.serie, new.noDcoumento, old.serie, old.noDcoumento, 0 , old.idproducto,new.cantidad, new.totalProd); 

 

 

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `documento`
--

DROP TABLE IF EXISTS `documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documento` (
  `iddocumento` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`iddocumento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documento`
--

LOCK TABLES `documento` WRITE;
/*!40000 ALTER TABLE `documento` DISABLE KEYS */;
INSERT INTO `documento` VALUES (1,'COMPROBANTE DE CREDITO FISCAL '),(2,'FACTURA DE CONSUMIDOR FINAL ');
/*!40000 ALTER TABLE `documento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `encabezadocompra`
--

DROP TABLE IF EXISTS `encabezadocompra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `encabezadocompra` (
  `serie` varchar(50) NOT NULL,
  `noDcoumento` int(11) NOT NULL,
  `iddocumento` int(11) NOT NULL,
  `idcondicionPago` int(11) NOT NULL,
  `noRgistroP` varchar(30) NOT NULL,
  `suma` double NOT NULL,
  `descuento` double NOT NULL,
  `iva` double NOT NULL,
  `percepcion` double NOT NULL,
  `total` double NOT NULL,
  `fecha` datetime NOT NULL,
  `anulado` tinyint(1) NOT NULL DEFAULT 0,
  `borrado` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`serie`,`noDcoumento`),
  KEY `iddocumento` (`iddocumento`),
  KEY `idcondicionPago` (`idcondicionPago`),
  KEY `noRgistoP` (`noRgistroP`),
  CONSTRAINT `fk_encabezadocompra_condicionpago1` FOREIGN KEY (`idcondicionPago`) REFERENCES `condicionpago` (`idcondicionPago`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_encabezadocompra_documento1` FOREIGN KEY (`iddocumento`) REFERENCES `documento` (`iddocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_encabezadocompra_proveedor` FOREIGN KEY (`noRgistroP`) REFERENCES `proveedor` (`noRgistroP`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encabezadocompra`
--

LOCK TABLES `encabezadocompra` WRITE;
/*!40000 ALTER TABLE `encabezadocompra` DISABLE KEYS */;
INSERT INTO `encabezadocompra` VALUES ('1',1,1,1,'1',100,0,13,1,114,'2022-11-04 19:29:08',0,0);
/*!40000 ALTER TABLE `encabezadocompra` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_encabezadocompra_update` AFTER UPDATE ON `encabezadocompra` FOR EACH ROW BEGIN



 CALL actualizar_detallecompra (new.serie, new.noDcoumento,old.serie, old.noDcoumento);

                                

    if new.anulado = true THEN

    CALL anular_detallecompra(old.serie, old.noDcoumento);

  

    END IF; 

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_encabezadocompra_delete` AFTER DELETE ON `encabezadocompra` FOR EACH ROW BEGIN



UPDATE detallecompra

 set detallecompra.anulado = 1 

 WHERE 

 detallecompra.serie = old.serie AND

detallecompra.noDcoumento = old.noDcoumento ;





DELETE FROM detallecompra WHERE 

detallecompra.serie = old.serie AND

detallecompra.noDcoumento = old.noDcoumento ; 

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `encabezadoventa`
--

DROP TABLE IF EXISTS `encabezadoventa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `encabezadoventa` (
  `serie` varchar(50) NOT NULL,
  `noDcoumento` int(11) NOT NULL,
  `dui` varchar(30) NOT NULL,
  `noRegistro` varchar(30) NOT NULL,
  `idusuario` int(11) NOT NULL,
  `idcondicionPago` int(11) NOT NULL DEFAULT 1,
  `iddocumento` int(11) NOT NULL,
  `suma` double NOT NULL,
  `iva` double NOT NULL,
  `total` double NOT NULL,
  `fecha` date NOT NULL,
  `anulado` tinyint(1) NOT NULL DEFAULT 0,
  `borrado` tinyint(1) NOT NULL DEFAULT 0,
  `dui_no_registrado` varchar(30) DEFAULT NULL,
  `nombre_no_registrado` varchar(150) DEFAULT NULL,
  `ordenRegistro` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`serie`,`noDcoumento`),
  KEY `dui` (`dui`),
  KEY `noRegistro` (`noRegistro`),
  KEY `encabezadoventa_ibfk_2` (`idusuario`),
  KEY `idcondicionPago_fk` (`idcondicionPago`),
  KEY `iddocumento_fk` (`iddocumento`),
  CONSTRAINT `encabezadoventa_ibfk_1` FOREIGN KEY (`dui`) REFERENCES `clientef` (`dui`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `encabezadoventa_ibfk_2` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `encabezadoventa_ibfk_3` FOREIGN KEY (`idcondicionPago`) REFERENCES `condicionpago` (`idcondicionPago`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `encabezadoventa_ibfk_4` FOREIGN KEY (`iddocumento`) REFERENCES `documento` (`iddocumento`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_encabezadoventa_clientec1` FOREIGN KEY (`noRegistro`) REFERENCES `clientec` (`noRegistro`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encabezadoventa`
--

LOCK TABLES `encabezadoventa` WRITE;
/*!40000 ALTER TABLE `encabezadoventa` DISABLE KEYS */;
INSERT INTO `encabezadoventa` VALUES ('170DS000C',1,' ','6456465-4',1,1,1,0,0,0,'2022-11-28',0,0,NULL,NULL,'2022-11-25 12:03:46'),('170DS000C',2,' ','6456465-4',1,1,1,0,0,0,'2022-11-25',0,0,NULL,NULL,'2022-11-25 12:03:52'),('170DS000C',3,' ','1',1,1,1,26.55,3.45,30,'2022-11-25',0,0,' ',' ','2022-11-25 12:04:23'),('170DS000C',4,' ','6456465-4',1,1,1,0,0,0,'2022-11-25',0,0,NULL,NULL,'2022-11-25 12:08:28'),('170DS000C',5,' ','6456465-4',1,1,1,26.55,3.45,30,'2022-11-25',0,0,NULL,NULL,'2022-11-25 12:13:33'),('170DS000C',6,' ','6456465-4',1,1,1,106.19,13.81,120,'2022-11-25',0,0,NULL,NULL,'2022-11-25 12:13:58'),('170DS000C',7,' ','6456465-4',1,1,1,79.65,10.35,90,'2022-12-07',0,0,'4545-545454-544-4','los mpresores s.a. de c.v. ','2022-12-07 16:53:13'),('21DS000F',1,'','ANULADO',1,1,2,0,0,0,'2022-11-25',1,0,'1','empresa CON UN NOMBRE BASTANTE LARGO','2022-11-25 12:04:09'),('21DS000F',3,'3465',' ',1,1,2,60,0,60,'2021-11-25',0,0,NULL,NULL,'2022-11-25 14:52:07'),('21DS000F',4,'','ANULADO',1,1,2,0,0,0,'2022-12-02',1,0,NULL,NULL,'2022-12-02 15:10:54'),('21DS000F',5,' ',' ',1,1,2,3.25,0,3.25,'2021-12-01',0,0,NULL,NULL,'2022-12-02 13:53:14'),('21DS000F',6,'3465',' ',1,1,2,315,0,315,'2022-12-01',0,0,NULL,NULL,'2022-12-07 16:43:40'),('21DS000F',7,' ',' ',1,1,2,60,0,60,'2022-12-07',0,0,NULL,NULL,'2022-12-07 16:43:21'),('21DS000F',8,' ',' ',1,1,2,30,0,30,'2022-12-07',0,0,'4545-545454-544-4','los mpresores s.a. de c.v. ','2022-12-07 16:52:49');
/*!40000 ALTER TABLE `encabezadoventa` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_encabezadoventa_update` AFTER UPDATE ON `encabezadoventa` FOR EACH ROW BEGIN

    

     CALL actualizar_detalleventa (new.serie, new.noDcoumento,old.serie, old.noDcoumento);

                                

    if new.anulado = true THEN



    CALL anular_detalleventa(old.serie, old.noDcoumento);

  

    END IF; 

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_encabezadoventa_delete` AFTER DELETE ON `encabezadoventa` FOR EACH ROW BEGIN



 update detalleventa

 set detalleventa.anulado = 1 

 WHERE 

 detalleventa.serie = old.serie AND

detalleventa.noDcoumento = old.noDcoumento ; 

    DELETE FROM detalleventa WHERE 

detalleventa.serie = old.serie AND

detalleventa.noDcoumento = old.noDcoumento ; 

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `inventario`
--

DROP TABLE IF EXISTS `inventario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventario` (
  `idinventario` int(11) NOT NULL AUTO_INCREMENT,
  `idproducto` varchar(50) NOT NULL,
  `existencia` double NOT NULL,
  `papelera` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`idinventario`),
  KEY `idproducto` (`idproducto`),
  CONSTRAINT `fk_inventario_producto1` FOREIGN KEY (`idproducto`) REFERENCES `producto` (`idproducto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventario`
--

LOCK TABLES `inventario` WRITE;
/*!40000 ALTER TABLE `inventario` DISABLE KEYS */;
INSERT INTO `inventario` VALUES (1,'WFTX12aa',756,0),(3,'39084',0,0),(4,'qqq',5,0);
/*!40000 ALTER TABLE `inventario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kardex`
--

DROP TABLE IF EXISTS `kardex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kardex` (
  `idkardex` int(11) NOT NULL AUTO_INCREMENT,
  `serie` varchar(50) NOT NULL,
  `noDcoumento` int(11) NOT NULL,
  `noLinea` int(11) NOT NULL,
  `idproducto` varchar(50) NOT NULL,
  `idtipotransaccion` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `entrada` double NOT NULL,
  `salida` double NOT NULL,
  `saldoAlmacen` double NOT NULL,
  `costoprom` double NOT NULL,
  `costoTotalProd` double NOT NULL,
  PRIMARY KEY (`idkardex`),
  KEY `noLinea` (`noLinea`),
  KEY `fk_kardex_tipotransaccion1_idx` (`idtipotransaccion`),
  KEY `fk_kardex_producto1_idx` (`idproducto`),
  KEY `serie` (`serie`),
  CONSTRAINT `fk_kardex_producto1` FOREIGN KEY (`idproducto`) REFERENCES `producto` (`idproducto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_kardex_tipotransaccion1` FOREIGN KEY (`idtipotransaccion`) REFERENCES `tipotransaccion` (`idtipotransaccion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=309 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kardex`
--

LOCK TABLES `kardex` WRITE;
/*!40000 ALTER TABLE `kardex` DISABLE KEYS */;
INSERT INTO `kardex` VALUES (188,'1',1,0,'qqq',2,'2022-11-04',500,0,500,0.2,100),(282,'1',1,2,'WFTX12aa',2,'2022-11-23',1000,0,1000,1.2,1200),(289,'170DS000C',3,0,'WFTX12aa',1,'2022-11-25',0,18,982,1.2,0),(290,'170DS000C',5,0,'qqq',1,'2022-11-25',0,45,455,0.2,0),(291,'170DS000C',6,0,'qqq',1,'2022-11-25',0,180,275,0.2,0),(292,'21DS000F',3,0,'qqq',1,'2022-11-25',0,45,230,0.2,30),(293,'21DS000F',3,1,'WFTX12aa',1,'2022-11-25',0,18,964,1.2,30),(295,'21DS000F',5,0,'WFTX12aa',1,'2022-12-02',0,1,0,0,3.25),(304,'21DS000F',7,0,'qqq',1,'2022-12-07',0,45,0,0,0),(305,'21DS000F',7,1,'WFTX12aa',1,'2022-12-07',0,18,0,0,0),(306,'21DS000F',6,0,'WFTX12aa',1,'2022-12-07',0,189,0,0,315),(307,'21DS000F',8,0,'qqq',1,'2022-12-07',0,45,0,0,0),(308,'170DS000C',7,0,'qqq',1,'2022-12-07',0,135,0,0,0);
/*!40000 ALTER TABLE `kardex` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_kardex_insert` AFTER INSERT ON `kardex` FOR EACH ROW BEGIN



if new.idtipotransaccion = 2 THEN

                UPDATE inventario 

        SET 

            inventario.existencia =  inventario.existencia + new.entrada 

        WHERE

            inventario.idproducto = new.idproducto	; 



 ELSEIF new.idtipotransaccion = 1 THEN

 	    UPDATE inventario 

        SET 

            inventario.existencia =  inventario.existencia - new.salida 

        WHERE

            inventario.idproducto = new.idproducto	; 

 	

 

 END IF; 

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_kardex_update` AFTER UPDATE ON `kardex` FOR EACH ROW BEGIN

DECLARE diferencia double ;



IF old.idtipotransaccion = 2 THEN



SET diferencia = NEW.entrada - OLD.entrada; 

        	IF diferencia < 0 THEN 

   

        	UPDATE inventario SET 

         existencia =

          existencia - (diferencia * -1)

            WHERE idproducto = old.idproducto;

            END IF; 

       

           IF diferencia > 0 THEN 

             	UPDATE inventario SET 

           existencia =  existencia + diferencia

            WHERE idproducto = old.idproducto;

            end if ; 





ELSEIF old.idtipotransaccion = 1   THEN



SET diferencia = NEW.salida - OLD.salida; 

			IF diferencia < 0 THEN 

   

        	UPDATE inventario SET 

           existencia =

           existencia + (diferencia * -1)

            WHERE idproducto = old.idproducto;

            

            END IF; 

       

           IF diferencia > 0 THEN 

             	UPDATE inventario SET 

           existencia =  existencia - diferencia

            WHERE idproducto = old.idproducto;

            END IF; 



end if ; 

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_kardex_delete` AFTER DELETE ON `kardex` FOR EACH ROW BEGIN

if old.idtipotransaccion = 2 THEN

UPDATE inventario 

SET inventario.existencia =  inventario.existencia- old.entrada where inventario.idproducto = old.idproducto; 



ELSEIF old.idtipotransaccion = 1 THEN

UPDATE inventario 

SET inventario.existencia =  inventario.existencia + old.salida where inventario.idproducto = old.idproducto; 

END IF; 

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `producto` (
  `idproducto` varchar(50) NOT NULL,
  `idcategoria` int(11) NOT NULL,
  `idunidadMedida` int(11) NOT NULL,
  `nombre` varchar(250) NOT NULL,
  `precioCaja` double NOT NULL DEFAULT 0,
  `precioUnidad` double NOT NULL DEFAULT 0,
  `precioDocena` double NOT NULL DEFAULT 0,
  `precioProm` double NOT NULL DEFAULT 0,
  `cantCaja` int(11) NOT NULL DEFAULT 0,
  `cantUnidad` int(11) NOT NULL DEFAULT 0,
  `cantDocena` int(11) NOT NULL DEFAULT 0,
  `cantProm` int(11) NOT NULL DEFAULT 0,
  `papelera` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`idproducto`),
  KEY `fk_producto_categoria1_idx` (`idcategoria`),
  KEY `fk_producto_unidadmedida1_idx` (`idunidadMedida`),
  CONSTRAINT `fk_producto_categoria1` FOREIGN KEY (`idcategoria`) REFERENCES `categoria` (`idcategoria`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_unidadmedida1` FOREIGN KEY (`idunidadMedida`) REFERENCES `unidadmedida` (`idunidadmedida`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES ('39084',1,2,'GALLETA SALADA /24',24,1,10,0,24,1,12,0,0),('qqq',1,2,'PRODUCTOCON UN NOMBRE EXTENSO',30,3,12.5,42,45,1,12,0,0),('WFTX12aa',1,1,'la galleta',30,3.25,12.5,0,18,1,12,0,0);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_producto_insert` AFTER INSERT ON `producto` FOR EACH ROW BEGIN 

   INSERT INTO inventario (idproducto)

   VALUES (new.idproducto); 

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_producto_update` AFTER UPDATE ON `producto` FOR EACH ROW BEGIN

    IF new.papelera = true THEN

    UPDATE inventario set 

    papelera = true; 

    ELSE

      UPDATE inventario set 

    papelera = false; 

      

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proveedor` (
  `noRgistroP` varchar(30) NOT NULL,
  `nombre` int(11) NOT NULL,
  `nit` varchar(40) NOT NULL,
  `giro` varchar(250) NOT NULL,
  `direccion` varchar(250) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  PRIMARY KEY (`noRgistroP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES ('1',1,'1','PROVEEDOR ','1','1');
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `serie`
--

DROP TABLE IF EXISTS `serie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `serie` (
  `idserie` int(11) NOT NULL AUTO_INCREMENT,
  `iddocumento` int(11) NOT NULL,
  `empresa` varchar(100) NOT NULL,
  `numerodeserie` varchar(50) NOT NULL,
  `ancho` int(11) NOT NULL DEFAULT 70,
  PRIMARY KEY (`idserie`),
  UNIQUE KEY `iddocumento_fk` (`iddocumento`),
  CONSTRAINT `serie_ibfk_1` FOREIGN KEY (`iddocumento`) REFERENCES `documento` (`iddocumento`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serie`
--

LOCK TABLES `serie` WRITE;
/*!40000 ALTER TABLE `serie` DISABLE KEYS */;
INSERT INTO `serie` VALUES (1,2,'Acomsal s.a. de c.v. ','21DS000F ',70),(2,1,'Acomsal s.a. de c.v. ','170DS000C',70);
/*!40000 ALTER TABLE `serie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipotransaccion`
--

DROP TABLE IF EXISTS `tipotransaccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipotransaccion` (
  `idtipotransaccion` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idtipotransaccion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipotransaccion`
--

LOCK TABLES `tipotransaccion` WRITE;
/*!40000 ALTER TABLE `tipotransaccion` DISABLE KEYS */;
INSERT INTO `tipotransaccion` VALUES (1,'VENTA '),(2,'COMPRA'),(3,'INVENTARIO INICIAL'),(4,'ENTRADA DE PRODUCTO'),(5,'SALIDA DE PRODUCTO ');
/*!40000 ALTER TABLE `tipotransaccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidadmedida`
--

DROP TABLE IF EXISTS `unidadmedida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidadmedida` (
  `idunidadmedida` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`idunidadmedida`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidadmedida`
--

LOCK TABLES `unidadmedida` WRITE;
/*!40000 ALTER TABLE `unidadmedida` DISABLE KEYS */;
INSERT INTO `unidadmedida` VALUES (1,'CAJA'),(2,'UNIDAD');
/*!40000 ALTER TABLE `unidadmedida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idusuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `clave` varchar(50) NOT NULL,
  PRIMARY KEY (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'CAJERO','123'),(2,'CONTABILIDAD','123');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `v_amdventa`
--

DROP TABLE IF EXISTS `v_amdventa`;
/*!50001 DROP VIEW IF EXISTS `v_amdventa`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `v_amdventa` (
  `ordenRegistro` tinyint NOT NULL,
  `fecha` tinyint NOT NULL,
  `documento` tinyint NOT NULL,
  `tipo documento` tinyint NOT NULL,
  `serie` tinyint NOT NULL,
  `noDcoumento` tinyint NOT NULL,
  `Cliente` tinyint NOT NULL,
  `total` tinyint NOT NULL,
  `usuario` tinyint NOT NULL,
  `condicionPago` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `v_detalleventacosto`
--

DROP TABLE IF EXISTS `v_detalleventacosto`;
/*!50001 DROP VIEW IF EXISTS `v_detalleventacosto`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `v_detalleventacosto` (
  `fecha` tinyint NOT NULL,
  `serie` tinyint NOT NULL,
  `noDcoumento` tinyint NOT NULL,
  `nombre` tinyint NOT NULL,
  `cantidad` tinyint NOT NULL,
  `precioPorUnidad` tinyint NOT NULL,
  `totalProd` tinyint NOT NULL,
  `costoprom` tinyint NOT NULL,
  `totalCosto` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `v_seleccionarprod`
--

DROP TABLE IF EXISTS `v_seleccionarprod`;
/*!50001 DROP VIEW IF EXISTS `v_seleccionarprod`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `v_seleccionarprod` (
  `idproducto` tinyint NOT NULL,
  `nombre` tinyint NOT NULL,
  `precioCaja` tinyint NOT NULL,
  `PrecioUnidad` tinyint NOT NULL,
  `existencia` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `v_todocliente`
--

DROP TABLE IF EXISTS `v_todocliente`;
/*!50001 DROP VIEW IF EXISTS `v_todocliente`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `v_todocliente` (
  `dui` tinyint NOT NULL,
  `nombre` tinyint NOT NULL,
  `nombreNegocio` tinyint NOT NULL,
  `direccion` tinyint NOT NULL,
  `nit` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'sistemaacomsal'
--
/*!50003 DROP PROCEDURE IF EXISTS `actualizar_detallecompra` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizar_detallecompra`(IN `p_serie_nuevo` VARCHAR(50), IN `p_noDocumento_nuevo` INT, IN `p_serie_old` VARCHAR(50), IN `p_noDocumento_old` INT)
    COMMENT 'este procedimiento existe para actualizar la serie y noDocumento'
BEGIN



UPDATE detallecompra set detallecompra.serie = p_serie_nuevo, 

detallecompra.noDcoumento = p_noDocumento_nuevo 

WHERE

detallecompra.serie = p_serie_old and detallecompra.noDcoumento = p_noDocumento_old; 



END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `actualizar_detalleventa` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizar_detalleventa`(IN `p_serie_nuevo` VARCHAR(50), IN `p_noDocumento_nuevo` INT, IN `p_serie_old` VARCHAR(50), IN `p_noDocumento_old` INT)
    COMMENT 'este procedimiento existe para actualizar la serie y noDocumento'
BEGIN



UPDATE detalleventa set detalleventa.serie = p_serie_nuevo, 

detalleventa.noDcoumento = p_noDocumento_nuevo 

WHERE

detalleventa.serie = p_serie_old and detalleventa.noDcoumento = p_noDocumento_old; 



END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `anular_detallecompra` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `anular_detallecompra`(IN `p_serie_old` VARCHAR(50), IN `p_noDocumento_old` INT)
    COMMENT 'anula el detallecompra si el encabezadocompra es anulado '
BEGIN



UPDATE detallecompra 

SET

detallecompra.anulado = true

WHERE

detallecompra.serie = p_serie_old

AND detallecompra.noDcoumento = p_noDocumento_old; 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `anular_detalleventa` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `anular_detalleventa`(IN `p_serie_old` VARCHAR(50), IN `p_noDocumento_old` INT)
    COMMENT 'anula el detalle venta  si el encabezadoventa es anulado '
BEGIN



UPDATE detalleventa 

SET

detalleventa.anulado = true, totalProd = 0.00, cantidad = 0.00

WHERE

detalleventa.serie = p_serie_old

AND detalleventa.noDcoumento = p_noDocumento_old; 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `buscaPrecio` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `buscaPrecio`(IN `p_idproductro` VARCHAR(50))
BEGIN

        SELECT

       p.precioCaja, p.precioUnidad, p.precioDocena, p.precioProm, p.cantCaja, p.cantUnidad, p.cantDocena, p.cantProm, i.existencia 

    FROM

        producto p INNER JOIN inventario i 

on p.idproducto = i.idproducto and p.papelera = 0 

where p.idproducto = p_idproductro ; 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `buscarclientesNombresimilar` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `buscarclientesNombresimilar`(`p_nombre` VARCHAR(150))
BEGIN

	

    SELECT * FROM v_todocliente WHERE nombre LIKE(concat('%',p_nombre,'%')); 



    

    

    

   

    

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `buscarclientesNombresimilarCC` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `buscarclientesNombresimilarCC`(`p_nombre` VARCHAR(150))
BEGIN

 SELECT * FROM clientec WHERE nombre LIKE(concat('%',p_nombre,'%')); 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `buscarProductoSimilar` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `buscarProductoSimilar`(`p_nombre` VARCHAR(250))
BEGIN

	SELECT *  FROM v_seleccionarprod WHERE nombre LIKE(concat('%',p_nombre,'%')); 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminar_encabezadocompra` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminar_encabezadocompra`(IN `p_serie` VARCHAR(50), IN `p_noDocumento` INT)
    COMMENT 'elimina el registro de encabezadocompra si y solo si el registro de encabezadocompra esta anulado   '
BEGIN

DELETE FROM encabezadocompra 

WHERE  serie = p_serie 

AND  noDcoumento = p_noDocumento

AND anulado = true; 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertarDetalleVenta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarDetalleVenta`(IN `p_serie` VARCHAR(50), IN `p_noDcoumento` INT(11), IN `p_noLinea` INT(11), IN `p_idproducto` VARCHAR(50), IN `p_cantidad` INT(11), IN `P_cantidadimpresa` DOUBLE, IN `P_unidadmedida` VARCHAR(5), IN `p_precioUnitario` DOUBLE, IN `p_totalProd` DOUBLE, IN `p_anulado` TINYINT(1), IN `p_borrado` TINYINT(1))
BEGIN

INSERT INTO `detalleventa`(`serie`, 

`noDcoumento`,

 `noLinea`,

 `idproducto`,

 `cantidad`,

 `cantidadimpresa`,

 `unidadmedida`, 

 `precioUnitario`, 

 `totalProd`, 

 `anulado`, 

 `borrado`)

	VALUES (

	p_serie,

    p_noDcoumento,

    p_noLinea,

	p_idproducto,

	p_cantidad,

	P_cantidadimpresa,

	P_unidadmedida,

	p_precioUnitario,

	p_totalProd,

	p_anulado,

	p_borrado  

	); 

	

	

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertarVenta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarVenta`(IN `p_serie` VARCHAR(50), IN `p_noDcoumento` INT(11), IN `p_dui` VARCHAR(30), IN `p_noRegistro` VARCHAR(30), IN `p_idusuario` INT(11), IN `p_idcondicionPago` INT(11), IN `p_suma` DOUBLE, IN `p_iva` DOUBLE, IN `p_total` DOUBLE, IN `p_tipodoc` INT, IN `p_fecha` DATETIME, IN `p_anulado` TINYINT(1), IN `p_borrado` TINYINT(1), IN `p_dui_no_registrado` VARCHAR(30), IN `p_nombre_no_registrado` VARCHAR(150))
BEGIN

	INSERT INTO `encabezadoventa`(`serie`, `noDcoumento`, `dui`, 

	`noRegistro`, `idusuario`,idcondicionPago,iddocumento, `suma`, `iva`, `total`, `fecha`,

	`anulado`, `borrado`, `dui_no_registrado`, `nombre_no_registrado`)

	VALUES (

	

	p_serie,

p_noDcoumento ,

p_dui ,

p_noRegistro,

p_idusuario, 

p_idcondicionPago,

p_tipodoc,

p_suma ,

p_iva ,

p_total,

p_fecha,

p_anulado,

p_borrado,

p_dui_no_registrado ,

p_nombre_no_registrado

);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_AnularVenta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_AnularVenta`(IN `p_serie` VARCHAR(50), IN `p_noDocumento` INT)
BEGIN

update encabezadoventa set suma = 0.00, iva = 0.00, total = 0.00,

 dui = "", noRegistro = "ANULADO", anulado = 1

 WHERE serie = p_serie and noDcoumento  = p_noDocumento; 



END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_costopromedio` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_costopromedio`(IN `p_idproducto` VARCHAR(30))
BEGIN

    /*para que el cursor funcione correctamente cuando se hace un registro en el kardex 

    cuando sea compra se debe registrar el costo total de producto y logicamente la cantidad que se esta comprando 

    en el caso de ventas solo es necesario la cantidad que se esta vendiendo

    

    recordar que este cursor hace el trabajo de calcular el costo promedio y para hacer eso hace una lectura de los saldos 

    en bodega (calcula los saldos a partir de las entradas y salidas), esa informacion la encuentra en la misma tabla kardex, 

    para el calculo de la existencia existe una tabla llamada inventario la cual a travez de disparadores calcula la existencia

    de los productos en el momentpo en que se realiza una transaccion ya sea compra, venta, modificacion de compra, modificacion

     de venta. */



    -- DECLARAR VARIABLES de la tabla 

        DECLARE vidkardex int (11) ; 

        DECLARE vserie varchar (50) ; 

        DECLARE vnoDocumentto int (11); 

        DECLARE vnoLinea int(11); 

        DECLARE vidproducto varchar(30); 

        DECLARE vidtipotransaccion int(11); 

        DECLARE vfecha datetime ; 

        DECLARE ventrada Double ; 

        DECLARE vsalida Double ; 

        DECLARE vsaldoAlmacen Double ; 

        DECLARE vcostoprom Double ; 

        DECLARE vCostoTotalProd  Double ; 

    -- variables para la logica 

        DECLARE vVuelta int; 

        DECLARE vfinished INTEGER DEFAULT 0;

        DECLARE vExistenciaAnterior Double ; 

            DECLARE  vsaldoBodegaUpdate Double ; 

        DECLARE  vUltimoCostoPromedio Double ; 





        -- declarar el cursor 

        DECLARE Temp_Cursor CURSOR FOR



        SELECT

            *

        FROM

            kardex	

        WHERE 

            idproducto = p_idproducto

        ORDER BY 

            fecha; 



        -- declarar handler 

        DECLARE CONTINUE HANDLER 

            FOR NOT FOUND SET vfinished = 1;





        OPEN Temp_Cursor; 

        SET vVuelta = 0;

        set vsaldoBodegaUpdate = 0; 



        GETTemp_Cursor: LOOP

            FETCH Temp_Cursor INTO 

        vidkardex ,

        vserie ,

        vnoDocumentto, 

        vnoLinea,

        vidproducto, 

        vidtipotransaccion,

        vfecha, 

        ventrada,  

        vsalida  ,

        vsaldoAlmacen , 

        vcostoprom ,

        vCostoTotalProd ; 



        

            IF vfinished = 1 THEN 

                LEAVE GETTemp_Cursor;

            END IF;

            

        -- DO WHEREVER YOU WANT TO DO 

        IF vidtipotransaccion = 2 THEN 

            

            IF vVuelta = 0 THEN

                    -- entrara aqui cuando este leyendo la primera compra 

                    SET vcostoprom = vCostoTotalProd / ventrada;

                   set vsaldoBodegaUpdate = vsaldoBodegaUpdate + ventrada ; 

                    UPDATE kardex SET costoprom = vcostoprom, saldoAlmacen = vsaldoBodegaUpdate WHERE idkardex = vidkardex	; 	

                     SET vUltimoCostoPromedio = vcostoprom;		

            

            ELSE	

                    -- entrara aqui cuando sea una compra pero no loa primera 

                   set vsaldoBodegaUpdate = vsaldoBodegaUpdate + ventrada ;

                    set vcostoprom = (vUltimoCostoPromedio * vExistenciaAnterior + vCostoTotalProd	) 

                    / (ventrada + vExistenciaAnterior); 

                    -- actualiza kardex 

                    UPDATE kardex set costoprom = vcostoprom, saldoAlmacen = vsaldoBodegaUpdate WHERE idkardex = vidkardex; 

                     SET vUltimoCostoPromedio = vcostoprom;

                

            END if; -- fin del if para compras 

                 SET vVuelta = vVuelta + 1;

      	

            elseif vidtipotransaccion = 1 THEN 

            -- aca entra si es una venta 

                  set vsaldoBodegaUpdate = vsaldoBodegaUpdate - vsalida ; 

                    UPDATE kardex SET costoprom = vUltimoCostoPromedio, saldoAlmacen = vsaldoBodegaUpdate WHERE idkardex = vidkardex; 

                  

                

            end if ; 



           

            if vidtipotransaccion = 3 || vidtipotransaccion = 4 || vidtipotransaccion = 5 then

                -- esta porcion de codigo es para controlar el inventario si se trata de 

                -- inventario inicial, entrada de producto, salida de producto 

                set vsaldoBodegaUpdate = vsaldoBodegaUpdate - vsalida + ventrada ; 

                

                 UPDATE kardex SET saldoAlmacen = vsaldoBodegaUpdate WHERE idkardex = vidkardex; 

                

            end if ; 







            -- luego asignamos las siguientes variables sin importar si es venta o compra  

              



              --  set vExistenciaAnterior = vsaldoAlmacen;

                set vExistenciaAnterior = vsaldoBodegaUpdate;

        

            

        -- END -  DO WHEREVER YOU WANT TO DO 

        END LOOP GETTemp_Cursor;

        CLOSE Temp_Cursor; 

    END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_costopromedioAll` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_costopromedioAll`()
BEGIN

	-- variables para el cursor externo

DECLARE vfinished1 INTEGER DEFAULT 0;

DECLARE  vidproducto1 varchar(50); 



DECLARE Temp_Art CURSOR FOR

SELECT 

	idproducto

FROM

	producto;

	

	DECLARE CONTINUE HANDLER 

        FOR NOT FOUND SET vfinished1 = 1;

	

OPEN Temp_Art;



	   GETTemp_Art: LOOP

		FETCH Temp_Art INTO vidproducto1;

		IF vfinished1 = 1 THEN 

			LEAVE GETTemp_Art;

		END IF;

	-- DO WHEREVER YOU WANT TO DO 



    call sp_costopromedio(vidproducto1); 



		

	-- END -  DO WHEREVER YOU WANT TO DO 

	END LOOP GETTemp_Art;

   CLOSE Temp_Art; 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_EliminarVenta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_EliminarVenta`(IN `p_serie` VARCHAR(50), IN `p_noDocumento` INT)
BEGIN

delete from encabezadoventa

WHERE serie = p_serie and noDcoumento = p_noDocumento; 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_filtroAdmVenta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_filtroAdmVenta`(IN `p_anio` YEAR(4), IN `p_idDocumento` INT)
BEGIN

	if p_idDocumento = 0 THEN

    -- si el parametro de documento es cero entonces muestra todos los tipos

    SELECT * from v_amdventa WHERE year(fecha) = p_anio order by  ordenRegistro asc ; 

    ELSE

    -- si el tipo documento es diferente de cero entonces filtra segun el valor del parametro

       SELECT * from v_amdventa WHERE year(fecha) = p_anio AND documento = p_idDocumento order by ordenRegistro asc ; 

   END IF;

    

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_filtroAvanzado_admVenta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_filtroAvanzado_admVenta`(IN `p_noDocumento` INT(11), IN `p_Cliente` VARCHAR(150), IN `p_fecha` VARCHAR(50))
BEGIN



-- si los parametros se llenaron 

-- y y y 

        if p_noDocumento != 0 and p_Cliente != "null" and p_fecha != "null" then 

            SELECT *  FROM v_amdventa where noDcoumento LIKE(concat('%',p_noDocumento,'%'))

    and Cliente LIKE(concat('%',p_Cliente,'%'))  and fecha = p_fecha;	

    end if; 

-- empieza las combinaciones x y y 

    if p_noDocumento = 0 and p_Cliente != "null" and p_fecha != "null" then 

            	SELECT *  FROM v_amdventa where Cliente LIKE(concat('%',p_Cliente,'%'))  and fecha = p_fecha;

    end if; 

--  y x x 

       if p_noDocumento != 0 and p_Cliente = "null" and p_fecha = "null" then 

            	SELECT *  FROM v_amdventa where noDcoumento LIKE(concat('%',p_noDocumento,'%')); 

    end if; 

    -- x x y 

          if p_noDocumento = 0 and p_Cliente = "null" and p_fecha != "null" then 

            	SELECT *  FROM v_amdventa where fecha = p_fecha;

    end if; 



    -- y x y 

    if p_noDocumento != 0 and p_Cliente = "null" and p_fecha != "null" then 

            SELECT *  FROM v_amdventa where noDcoumento LIKE(concat('%',p_noDocumento,'%')) 

            and fecha = p_fecha ; 

    end if; 



    -- x y x 

        if p_noDocumento = 0 and p_Cliente != "null" and p_fecha = "null" then 

            SELECT *  FROM v_amdventa where  Cliente LIKE(concat('%',p_Cliente,'%')) ; 

    end if; 



    -- y y x 

            if p_noDocumento != 0 and p_Cliente != "null" and p_fecha = "null" then 

            SELECT *  FROM v_amdventa where noDcoumento LIKE(concat('%',p_noDocumento,'%'))

    and Cliente LIKE(concat('%',p_Cliente,'%')); 

    end if; 



 -- x x x para este caso se validara en java ya que ninguno de los parametros esta listo 











END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_modificarFactura` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_modificarFactura`(IN `p_serieOld` VARCHAR(50), IN `p_noDocumentoOld` INT(11), IN `p_serie` VARCHAR(50), IN `p_noDcoumento` INT(11), IN `p_fecha` DATE)
BEGIN

    UPDATE

        encabezadoventa

    SET

        serie = p_serie,

        noDcoumento = p_noDcoumento,

        fecha = p_fecha

    WHERE

        serie = p_serieOld AND noDcoumento = p_noDocumentoOld ;

        END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_ultimoRegistroVenta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ultimoRegistroVenta`(IN `p_serie` VARCHAR(30))
BEGIN

	SELECT max(noDcoumento) as ultimoRegistro FROM encabezadoventa where serie = p_serie;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_updateAnchoOnSerie` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_updateAnchoOnSerie`(IN `p_ancho` INT, IN `p_idserie` INT)
BEGIN

	 UPDATE `serie` SET `ancho`=  p_ancho WHERE idserie = p_idserie;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_verancho` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_verancho`()
BEGIN

	SELECT ancho  FROM serie;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_vercondiciondepago` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_vercondiciondepago`()
BEGIN

	SELECT * from condicionpago; 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_verDetalle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_verDetalle`(IN `p_serie` VARCHAR(50), IN `p_noDoc` INT)
BEGIN

	SELECT v.noLinea, v.idproducto, p.nombre, v.cantidadimpresa, v.unidadmedida, v.precioUnitario, v.totalProd   FROM detalleventa v inner JOIN producto p on v.idproducto = p.idproducto

    where v.serie = p_serie and v.noDcoumento = p_noDoc; 

    

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_verEncabezadoVenta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_verEncabezadoVenta`(IN `p_serie` VARCHAR(50), IN `p_noDcoumento` INT)
BEGIN

	

SELECT c.nombre, c.direccion, c.dui, v.fecha,v.serie,v.noDcoumento,

REPLACE(replace(v.anulado,0,'ACTIVO'),1,'ANULADO' ) as 'estado' from encabezadoventa v  

INNER join   clientef c on v.dui = c.dui where v.serie = p_serie and v.noDcoumento = p_noDcoumento; 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_veridcondicionpago` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_veridcondicionpago`(pnombre varchar (50))
BEGIN

	SELECT idcondicionpago  FROM condicionpago where nombre = pnombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_vertodoClienteCC` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_vertodoClienteCC`(IN `p_noRegistro` VARCHAR(30))
BEGIN

	SELECT *  FROM clientec WHERE noRegistro = p_noRegistro ; 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_kardex_1` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_kardex_1`(IN `p_serie_nuevo` VARCHAR(50), IN `p_noDocumento_nuevo` INT, IN `p_serie_old` VARCHAR(50), IN `p_noDocumento_old` INT, IN `p_newCantidad` DOUBLE, IN `p_idproducto` VARCHAR(50), IN `p_newSalida` DOUBLE, IN p_totalProd DOUBLE)
BEGIN

if p_newCantidad > p_newSalida then 

	UPDATE kardex set kardex.serie = p_serie_nuevo, 

	kardex.noDcoumento = p_noDocumento_nuevo,

	 kardex.entrada =  p_newCantidad , kardex.costoTotalProd = p_totalProd

	WHERE

	kardex.serie = p_serie_old and 

	kardex.noDcoumento = p_noDocumento_old AND

	kardex.idproducto = p_idproducto ; 



ELSEIF p_newCantidad < p_newSalida  THEN



    UPDATE kardex set kardex.serie = p_serie_nuevo, 

    kardex.noDcoumento = p_noDocumento_nuevo,

    kardex.salida =  p_newSalida,  kardex.costoTotalProd = p_totalProd

    WHERE

    kardex.serie = p_serie_old and 

    kardex.noDcoumento = p_noDocumento_old AND

    kardex.idproducto = p_idproducto ; 



	

END IF; 

 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `vertodoCliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `vertodoCliente`(IN `p_nombre` VARCHAR(150))
BEGIN

	SELECT *  FROM v_todocliente where nombre = p_nombre ; 

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `verUltimoRegistroKardexSegunProd` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `verUltimoRegistroKardexSegunProd`(pidproducto varchar(50))
BEGIN

	

    SELECT * FROM kardex WHERE idkardex=



(SELECT

    MAX(idkardex)

FROM

    kardex

    WHERE idproducto = pidproducto);

    

    

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `v_amdventa`
--

/*!50001 DROP TABLE IF EXISTS `v_amdventa`*/;
/*!50001 DROP VIEW IF EXISTS `v_amdventa`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_amdventa` AS select `v`.`ordenRegistro` AS `ordenRegistro`,`v`.`fecha` AS `fecha`,`d`.`iddocumento` AS `documento`,`d`.`nombre` AS `tipo documento`,`v`.`serie` AS `serie`,`v`.`noDcoumento` AS `noDcoumento`,replace(`cf`.`nombre`,' ',`cc`.`nombre`) AS `Cliente`,`v`.`total` AS `total`,`u`.`nombre` AS `usuario`,`cp`.`nombre` AS `condicionPago` from (((((`encabezadoventa` `v` join `documento` `d` on(`d`.`iddocumento` = `v`.`iddocumento`)) join `clientef` `cf` on(`cf`.`dui` = `v`.`dui`)) join `clientec` `cc` on(`cc`.`noRegistro` = `v`.`noRegistro`)) join `usuario` `u` on(`v`.`idusuario` = `u`.`idusuario`)) join `condicionpago` `cp` on(`cp`.`idcondicionPago` = `v`.`idcondicionPago`)) order by `v`.`ordenRegistro` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_detalleventacosto`
--

/*!50001 DROP TABLE IF EXISTS `v_detalleventacosto`*/;
/*!50001 DROP VIEW IF EXISTS `v_detalleventacosto`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_detalleventacosto` AS select `k`.`fecha` AS `fecha`,`v`.`serie` AS `serie`,`v`.`noDcoumento` AS `noDcoumento`,`p`.`nombre` AS `nombre`,`v`.`cantidad` AS `cantidad`,`v`.`totalProd` / `v`.`cantidad` AS `precioPorUnidad`,`v`.`totalProd` AS `totalProd`,`k`.`costoprom` AS `costoprom`,`k`.`salida` * `k`.`costoprom` AS `totalCosto` from ((`detalleventa` `v` join `producto` `p` on(`v`.`idproducto` = `p`.`idproducto`)) join `kardex` `k` on(`v`.`serie` = `k`.`serie` and `v`.`noDcoumento` = `k`.`noDcoumento` and `v`.`noLinea` = `k`.`noLinea`)) order by `k`.`idkardex` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_seleccionarprod`
--

/*!50001 DROP TABLE IF EXISTS `v_seleccionarprod`*/;
/*!50001 DROP VIEW IF EXISTS `v_seleccionarprod`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_seleccionarprod` AS select `p`.`idproducto` AS `idproducto`,`p`.`nombre` AS `nombre`,`p`.`precioCaja` AS `precioCaja`,`p`.`precioUnidad` AS `PrecioUnidad`,`i`.`existencia` AS `existencia` from (`producto` `p` join `inventario` `i` on(`p`.`idproducto` = `i`.`idproducto`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_todocliente`
--

/*!50001 DROP TABLE IF EXISTS `v_todocliente`*/;
/*!50001 DROP VIEW IF EXISTS `v_todocliente`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_todocliente` AS select `clientef`.`dui` AS `dui`,`clientef`.`nombre` AS `nombre`,`clientef`.`nombreNegocio` AS `nombreNegocio`,`clientef`.`direccion` AS `direccion`,`clientef`.`nit` AS `nit` from `clientef` where `clientef`.`papelera` = 0 union select `clientec`.`dui` AS `dui`,`clientec`.`nombre` AS `nombre`,`clientec`.`nombreNegocio` AS `nombreNegocio`,`clientec`.`direccion` AS `direccion`,`clientec`.`nit` AS `nit` from `clientec` where `clientec`.`papelera` = 0 order by `nombre` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-07 16:54:40
