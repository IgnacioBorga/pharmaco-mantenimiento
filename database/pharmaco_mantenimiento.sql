CREATE DATABASE  IF NOT EXISTS `pharmaco_mantenimiento` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pharmaco_mantenimiento`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pharmaco_mantenimiento
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `equipo`
--

DROP TABLE IF EXISTS `equipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipo` (
  `tag` varchar(50) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  `id_sector` int NOT NULL,
  PRIMARY KEY (`tag`),
  KEY `fk_equipo_sector` (`id_sector`),
  CONSTRAINT `fk_equipo_sector` FOREIGN KEY (`id_sector`) REFERENCES `sector` (`id_sector`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipo`
--

LOCK TABLES `equipo` WRITE;
/*!40000 ALTER TABLE `equipo` DISABLE KEYS */;
INSERT INTO `equipo` VALUES ('EQ-001','Bomba centrifuga','Bomba de proceso principal',1,1),('EQ-002','Balanza analitica','Equipo de pesaje de precision',1,2),('EQ-003','Compresor de aire','Compresor para linea de produccion',1,1);
/*!40000 ALTER TABLE `equipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `falla`
--

DROP TABLE IF EXISTS `falla`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `falla` (
  `id_falla` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(500) NOT NULL,
  `id_intervencion` int NOT NULL,
  PRIMARY KEY (`id_falla`),
  KEY `fk_falla_intervencion` (`id_intervencion`),
  CONSTRAINT `fk_falla_intervencion` FOREIGN KEY (`id_intervencion`) REFERENCES `intervencion` (`id_intervencion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `falla`
--

LOCK TABLES `falla` WRITE;
/*!40000 ALTER TABLE `falla` DISABLE KEYS */;
/*!40000 ALTER TABLE `falla` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `intervencion`
--

DROP TABLE IF EXISTS `intervencion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `intervencion` (
  `id_intervencion` int NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tareas_realizadas` varchar(500) NOT NULL,
  `observaciones` varchar(500) DEFAULT NULL,
  `numero_orden` int NOT NULL,
  `id_tecnico` int NOT NULL,
  PRIMARY KEY (`id_intervencion`),
  KEY `fk_intervencion_orden` (`numero_orden`),
  KEY `fk_intervencion_tecnico` (`id_tecnico`),
  CONSTRAINT `fk_intervencion_orden` FOREIGN KEY (`numero_orden`) REFERENCES `orden_trabajo` (`numero`),
  CONSTRAINT `fk_intervencion_tecnico` FOREIGN KEY (`id_tecnico`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intervencion`
--

LOCK TABLES `intervencion` WRITE;
/*!40000 ALTER TABLE `intervencion` DISABLE KEYS */;
INSERT INTO `intervencion` VALUES (1,'2026-11-09 00:41:16','Se reemplaz� el sello mec�nico y se verific� el funcionamiento de la bomba','Equipo operativo luego de la intervenci�n',1,2),(2,'2026-11-09 00:44:07','Se realiz� inspecci�n general y control del nivel de aceite','Se recomienda continuar con el seguimiento del equipo',2,2),(3,'2026-11-09 11:31:28','Se enciende el equipo','Equipo apagado por falla en el suministro electrico',3,2);
/*!40000 ALTER TABLE `intervencion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orden_trabajo`
--

DROP TABLE IF EXISTS `orden_trabajo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orden_trabajo` (
  `numero` int NOT NULL AUTO_INCREMENT,
  `fecha_creacion` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `prioridad` varchar(20) NOT NULL,
  `estado` varchar(30) NOT NULL DEFAULT 'Asignada',
  `seguimiento` text,
  `fecha_cierre` datetime DEFAULT NULL,
  `id_solicitud` int NOT NULL,
  `id_tecnico` int NOT NULL,
  PRIMARY KEY (`numero`),
  UNIQUE KEY `id_solicitud` (`id_solicitud`),
  KEY `fk_orden_tecnico` (`id_tecnico`),
  CONSTRAINT `fk_orden_solicitud` FOREIGN KEY (`id_solicitud`) REFERENCES `solicitud_mantenimiento` (`id_solicitud`),
  CONSTRAINT `fk_orden_tecnico` FOREIGN KEY (`id_tecnico`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orden_trabajo`
--

LOCK TABLES `orden_trabajo` WRITE;
/*!40000 ALTER TABLE `orden_trabajo` DISABLE KEYS */;
INSERT INTO `orden_trabajo` VALUES (1,'2026-11-09 00:40:10','Alta','Cerrada',NULL,'2026-11-09 00:41:45',1,2),(2,'2026-11-09 00:42:54','Media','En ejecución',NULL,NULL,2,2),(3,'2026-11-09 11:29:49','Alta','Cerrada',NULL,'2026-11-09 11:32:55',3,2);
/*!40000 ALTER TABLE `orden_trabajo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repuesto`
--

DROP TABLE IF EXISTS `repuesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repuesto` (
  `id_repuesto` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(150) NOT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_repuesto`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repuesto`
--

LOCK TABLES `repuesto` WRITE;
/*!40000 ALTER TABLE `repuesto` DISABLE KEYS */;
INSERT INTO `repuesto` VALUES (1,'Sello mecanico',1),(2,'Rodamiento',1),(3,'Correa de transmision',1),(4,'Repuesto temporal de prueba',1);
/*!40000 ALTER TABLE `repuesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repuesto_utilizado`
--

DROP TABLE IF EXISTS `repuesto_utilizado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repuesto_utilizado` (
  `id_repuesto_utilizado` int NOT NULL AUTO_INCREMENT,
  `cantidad` int NOT NULL,
  `descripcion_alternativa` varchar(150) DEFAULT NULL,
  `id_intervencion` int NOT NULL,
  `id_repuesto` int DEFAULT NULL,
  PRIMARY KEY (`id_repuesto_utilizado`),
  KEY `fk_repuesto_utilizado_intervencion` (`id_intervencion`),
  KEY `fk_repuesto_utilizado_repuesto` (`id_repuesto`),
  CONSTRAINT `fk_repuesto_utilizado_intervencion` FOREIGN KEY (`id_intervencion`) REFERENCES `intervencion` (`id_intervencion`),
  CONSTRAINT `fk_repuesto_utilizado_repuesto` FOREIGN KEY (`id_repuesto`) REFERENCES `repuesto` (`id_repuesto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repuesto_utilizado`
--

LOCK TABLES `repuesto_utilizado` WRITE;
/*!40000 ALTER TABLE `repuesto_utilizado` DISABLE KEYS */;
/*!40000 ALTER TABLE `repuesto_utilizado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `id_rol` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id_rol`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (3,'Coordinador'),(1,'Solicitante'),(2,'Tecnico');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sector`
--

DROP TABLE IF EXISTS `sector`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sector` (
  `id_sector` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_sector`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sector`
--

LOCK TABLES `sector` WRITE;
/*!40000 ALTER TABLE `sector` DISABLE KEYS */;
INSERT INTO `sector` VALUES (1,'Produccion','Sector de produccion de la planta',1),(2,'Control de Calidad','Laboratorio de control de calidad',1),(3,'Deposito','Sector de almacenamiento',1);
/*!40000 ALTER TABLE `sector` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitud_mantenimiento`
--

DROP TABLE IF EXISTS `solicitud_mantenimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitud_mantenimiento` (
  `id_solicitud` int NOT NULL AUTO_INCREMENT,
  `fecha_creacion` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `descripcion` varchar(500) NOT NULL,
  `estado` varchar(30) NOT NULL DEFAULT 'Registrada',
  `motivo_rechazo` varchar(255) DEFAULT NULL,
  `id_usuario` int NOT NULL,
  `tag_equipo` varchar(50) NOT NULL,
  PRIMARY KEY (`id_solicitud`),
  KEY `fk_solicitud_usuario` (`id_usuario`),
  KEY `fk_solicitud_equipo` (`tag_equipo`),
  CONSTRAINT `fk_solicitud_equipo` FOREIGN KEY (`tag_equipo`) REFERENCES `equipo` (`tag`),
  CONSTRAINT `fk_solicitud_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitud_mantenimiento`
--

LOCK TABLES `solicitud_mantenimiento` WRITE;
/*!40000 ALTER TABLE `solicitud_mantenimiento` DISABLE KEYS */;
INSERT INTO `solicitud_mantenimiento` VALUES (1,'2026-11-09 00:39:34','La bomba presenta una p�rdida de producto en el sello mec�nico','Aceptada',NULL,1,'EQ-001'),(2,'2026-11-09 00:42:35','El compresor presenta ruidos anormales durante el funcionamiento','Aceptada',NULL,1,'EQ-003'),(3,'2026-11-09 11:29:24','Equipo apagado','Aceptada',NULL,1,'EQ-001');
/*!40000 ALTER TABLE `solicitud_mantenimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  `id_rol` int NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `nombre_usuario` (`nombre_usuario`),
  KEY `fk_usuario_rol` (`id_rol`),
  CONSTRAINT `fk_usuario_rol` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Juan','Perez','jperez','clave123',1,1),(2,'Carlos','Lopez','clopez','clave123',1,2),(3,'Maria','Gonzalez','mgonzalez','clave123',1,3);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-09 18:32:57
