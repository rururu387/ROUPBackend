-- MySQL dump 10.13  Distrib 8.0.24, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	8.0.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `form`
--

DROP TABLE IF EXISTS `form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `form` (
  `forms_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `form_name` varchar(50) NOT NULL,
  `settings` varchar(1000) NOT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  `date_last_use` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`forms_id`),
  KEY `form_user_index` (`user_id`),
  CONSTRAINT `form_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form`
--

LOCK TABLES `form` WRITE;
/*!40000 ALTER TABLE `form` DISABLE KEYS */;
/*!40000 ALTER TABLE `form` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupmembers`
--

DROP TABLE IF EXISTS `groupmembers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groupmembers` (
  `group_members_id` int NOT NULL AUTO_INCREMENT,
  `group_id` int NOT NULL,
  `user_id` int NOT NULL,
  `privelege` int NOT NULL,
  `date_last_use` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`group_members_id`),
  KEY `group_id` (`group_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `groupmembers_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `usergroup` (`group_id`),
  CONSTRAINT `groupmembers_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupmembers`
--

LOCK TABLES `groupmembers` WRITE;
/*!40000 ALTER TABLE `groupmembers` DISABLE KEYS */;
/*!40000 ALTER TABLE `groupmembers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hourinfo`
--

DROP TABLE IF EXISTS `hourinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hourinfo` (
  `resource_id` int NOT NULL AUTO_INCREMENT,
  `cpu_usage` float DEFAULT NULL,
  `ram_usage` bigint unsigned DEFAULT NULL,
  `program_id` int DEFAULT NULL,
  `thread_amount` int DEFAULT NULL,
  `time_act_sum` int DEFAULT NULL,
  `time_sum` int DEFAULT NULL,
  `data_pack_count` int DEFAULT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`resource_id`),
  KEY `program_id` (`program_id`),
  CONSTRAINT `hourinfo_ibfk_1` FOREIGN KEY (`program_id`) REFERENCES `program` (`program_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=672 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hourinfo`
--

LOCK TABLES `hourinfo` WRITE;
/*!40000 ALTER TABLE `hourinfo` DISABLE KEYS */;
INSERT INTO `hourinfo` VALUES (362,0.0475961,59,215,39,0,245000,49,'2021-03-16 19:00:00'),(363,0.00721137,25,216,9,0,245000,49,'2021-03-16 19:00:00'),(364,0.00456558,91,217,21,0,245000,49,'2021-03-16 19:00:00'),(365,0,16,218,8,0,245000,49,'2021-03-16 19:00:00'),(366,0.193661,162,219,80,5000,245000,49,'2021-03-16 19:00:00'),(367,0,3,220,15,0,245000,49,'2021-03-16 19:00:00'),(368,0.00719442,62,221,13,0,245000,49,'2021-03-16 19:00:00'),(369,0.035916,150,222,24,0,245000,49,'2021-03-16 19:00:00'),(370,0,46,223,22,0,245000,49,'2021-03-16 19:00:00'),(371,0,9,224,1,0,245000,49,'2021-03-16 19:00:00'),(372,0,13,225,7,0,245000,49,'2021-03-16 19:00:00'),(373,0.0150911,37,226,10,0,245000,49,'2021-03-16 19:00:00'),(374,3.93755,3175,227,585,105000,245000,49,'2021-03-16 19:00:00'),(375,0.0882958,21,228,3,0,245000,49,'2021-03-16 19:00:00'),(376,0.00212585,5,229,1,0,245000,49,'2021-03-16 19:00:00'),(377,0,7,230,1,0,245000,49,'2021-03-16 19:00:00'),(378,0,39,231,44,0,245000,49,'2021-03-16 19:00:00'),(379,0,48,232,14,0,245000,49,'2021-03-16 19:00:00'),(380,0,6,233,92,0,245000,49,'2021-03-16 19:00:00'),(381,0.0144004,133,234,60,0,245000,49,'2021-03-16 19:00:00'),(382,0,8,235,1,0,245000,49,'2021-03-16 19:00:00'),(383,0,56,236,18,0,245000,49,'2021-03-16 19:00:00'),(384,0,9,237,2,0,245000,49,'2021-03-16 19:00:00'),(385,0,3,238,25,0,245000,49,'2021-03-16 19:00:00'),(386,0,29,239,4,0,245000,49,'2021-03-16 19:00:00'),(387,0,3,240,21,0,245000,49,'2021-03-16 19:00:00'),(388,0,42,241,22,0,245000,49,'2021-03-16 19:00:00'),(389,1.03653,1287,242,86,130000,245000,49,'2021-03-16 19:00:00'),(390,0.00406304,2,243,2,0,245000,49,'2021-03-16 19:00:00'),(391,0,50,244,10,0,245000,49,'2021-03-16 19:00:00'),(392,0,20,245,2,0,245000,49,'2021-03-16 19:00:00'),(393,0,1,246,24,0,245000,49,'2021-03-16 19:00:00'),(394,0.0103321,87,247,13,0,245000,49,'2021-03-16 19:00:00'),(395,0.00212807,167,248,56,0,245000,49,'2021-03-16 19:00:00'),(396,0.196992,1132,249,145,5000,245000,49,'2021-03-16 19:00:00'),(397,1.28655,31,250,20,0,5000,1,'2021-03-16 19:00:00'),(398,0,82,215,41,0,5000,1,'2021-03-16 21:00:00'),(399,0,27,216,7,0,5000,1,'2021-03-16 21:00:00'),(400,0,98,217,29,0,5000,1,'2021-03-16 21:00:00'),(401,0,16,218,8,0,5000,1,'2021-03-16 21:00:00'),(402,0.0999001,170,219,89,0,5000,1,'2021-03-16 21:00:00'),(403,0,4,220,15,0,5000,1,'2021-03-16 21:00:00'),(404,0,72,221,12,0,5000,1,'2021-03-16 21:00:00'),(405,0,147,222,20,0,5000,1,'2021-03-16 21:00:00'),(406,0,45,223,17,0,5000,1,'2021-03-16 21:00:00'),(407,0,9,224,1,0,5000,1,'2021-03-16 21:00:00'),(408,0,21,225,6,0,5000,1,'2021-03-16 21:00:00'),(409,0,38,226,11,0,5000,1,'2021-03-16 21:00:00'),(410,0.296835,3813,227,581,0,5000,1,'2021-03-16 21:00:00'),(411,0.0988142,25,228,4,0,5000,1,'2021-03-16 21:00:00'),(412,0.197628,5,229,1,0,5000,1,'2021-03-16 21:00:00'),(413,0,7,230,1,0,5000,1,'2021-03-16 21:00:00'),(414,0,41,231,42,0,5000,1,'2021-03-16 21:00:00'),(415,0,48,232,13,0,5000,1,'2021-03-16 21:00:00'),(416,0,26,233,94,0,5000,1,'2021-03-16 21:00:00'),(417,0,171,234,73,0,5000,1,'2021-03-16 21:00:00'),(418,0,9,235,1,0,5000,1,'2021-03-16 21:00:00'),(419,0,66,236,17,0,5000,1,'2021-03-16 21:00:00'),(420,0,9,237,1,0,5000,1,'2021-03-16 21:00:00'),(421,0,3,238,25,0,5000,1,'2021-03-16 21:00:00'),(422,0,31,239,4,0,5000,1,'2021-03-16 21:00:00'),(423,0,3,240,21,0,5000,1,'2021-03-16 21:00:00'),(424,0,3,241,22,0,5000,1,'2021-03-16 21:00:00'),(425,1.88119,1394,242,98,5000,5000,1,'2021-03-16 21:00:00'),(426,0,2,243,2,0,5000,1,'2021-03-16 21:00:00'),(427,0,49,244,16,0,5000,1,'2021-03-16 21:00:00'),(428,0,21,245,2,0,5000,1,'2021-03-16 21:00:00'),(429,0,2,246,24,0,5000,1,'2021-03-16 21:00:00'),(430,0,75,247,13,0,5000,1,'2021-03-16 21:00:00'),(431,0,102,251,63,0,5000,1,'2021-03-16 21:00:00'),(432,0,71,252,25,0,5000,1,'2021-03-16 21:00:00'),(433,0.39604,492,249,182,0,5000,1,'2021-03-16 21:00:00'),(434,0,275,253,74,0,5000,1,'2021-03-16 21:00:00'),(470,2.52747,12,257,8,0,10000,1,'2021-04-07 19:00:00'),(471,0,28,258,4,0,30000,3,'2021-04-07 19:00:00'),(472,0,64,259,42,0,280000,28,'2021-04-07 19:00:00'),(473,0,120,260,21,0,280000,28,'2021-04-07 19:00:00'),(474,0,26,261,9,0,280000,28,'2021-04-07 19:00:00'),(475,0,16,262,6,0,280000,28,'2021-04-07 19:00:00'),(476,0.0150067,156,263,76,0,280000,28,'2021-04-07 19:00:00'),(477,0,3,264,15,0,280000,28,'2021-04-07 19:00:00'),(478,0,61,265,12,0,280000,28,'2021-04-07 19:00:00'),(479,0,139,266,24,0,280000,28,'2021-04-07 19:00:00'),(480,0,42,267,21,0,280000,28,'2021-04-07 19:00:00'),(481,0,8,268,1,0,280000,28,'2021-04-07 19:00:00'),(482,0,13,269,6,0,280000,28,'2021-04-07 19:00:00'),(483,0,38,270,11,0,280000,28,'2021-04-07 19:00:00'),(484,0,21,271,3,0,280000,28,'2021-04-07 19:00:00'),(485,0,14,272,2,0,280000,28,'2021-04-07 19:00:00'),(486,0,4,273,1,0,280000,28,'2021-04-07 19:00:00'),(487,0,38,274,48,0,280000,28,'2021-04-07 19:00:00'),(488,0,6,275,92,0,280000,28,'2021-04-07 19:00:00'),(489,0,147,276,58,0,280000,28,'2021-04-07 19:00:00'),(490,0.00421159,42,277,19,0,280000,28,'2021-04-07 19:00:00'),(491,0,2,278,24,0,280000,28,'2021-04-07 19:00:00'),(492,0,25,279,2,0,280000,28,'2021-04-07 19:00:00'),(493,0,8,280,2,0,280000,28,'2021-04-07 19:00:00'),(494,0.0563214,3986,281,677,0,280000,28,'2021-04-07 19:00:00'),(495,0,922,282,74,0,280000,28,'2021-04-07 19:00:00'),(496,0,2,283,2,0,280000,28,'2021-04-07 19:00:00'),(497,0,45,284,10,0,280000,28,'2021-04-07 19:00:00'),(498,0,8,285,1,0,280000,28,'2021-04-07 19:00:00'),(499,0,45,286,13,0,280000,28,'2021-04-07 19:00:00'),(500,0.00396385,76,287,55,0,280000,28,'2021-04-07 19:00:00'),(501,0,60,288,33,0,280000,28,'2021-04-07 19:00:00'),(502,0,16,289,1,0,280000,28,'2021-04-07 19:00:00'),(503,0.0647353,413,290,143,0,280000,28,'2021-04-07 19:00:00'),(504,0.0266878,13,291,5,0,280000,28,'2021-04-07 19:00:00'),(505,0,35,292,19,0,280000,28,'2021-04-07 19:00:00'),(506,7.01845,1047,293,156,280000,280000,28,'2021-04-07 19:00:00'),(507,0.81754,29,294,19,0,30000,3,'2021-04-07 19:00:00'),(508,0,62,215,43,0,320000,32,'2021-04-07 19:00:00'),(509,0,120,217,26,0,320000,32,'2021-04-07 19:00:00'),(510,0,26,216,8,0,320000,32,'2021-04-07 19:00:00'),(511,0,16,218,6,0,320000,32,'2021-04-07 19:00:00'),(512,0.0940064,157,219,82,0,320000,32,'2021-04-07 19:00:00'),(513,0,3,220,15,0,320000,32,'2021-04-07 19:00:00'),(514,0,67,221,11,0,320000,32,'2021-04-07 19:00:00'),(515,0,138,222,25,0,320000,32,'2021-04-07 19:00:00'),(516,0,42,223,21,0,320000,32,'2021-04-07 19:00:00'),(517,0,8,224,1,0,320000,32,'2021-04-07 19:00:00'),(518,0,13,225,6,0,320000,32,'2021-04-07 19:00:00'),(519,0.00897718,38,226,10,0,320000,32,'2021-04-07 19:00:00'),(520,0.0450786,21,228,3,0,320000,32,'2021-04-07 19:00:00'),(521,0,14,230,2,0,320000,32,'2021-04-07 19:00:00'),(522,0.00648957,4,229,1,0,320000,32,'2021-04-07 19:00:00'),(523,0,38,231,48,0,320000,32,'2021-04-07 19:00:00'),(524,0,8,233,92,0,320000,32,'2021-04-07 19:00:00'),(525,0.00649882,147,234,59,0,320000,32,'2021-04-07 19:00:00'),(526,0,38,241,19,0,320000,32,'2021-04-07 19:00:00'),(527,0,1,246,24,0,320000,32,'2021-04-07 19:00:00'),(528,0,25,239,2,0,320000,32,'2021-04-07 19:00:00'),(529,0,8,237,1,0,320000,32,'2021-04-07 19:00:00'),(530,0.238687,4116,227,683,0,320000,32,'2021-04-07 19:00:00'),(531,0.360842,1052,242,79,40000,320000,32,'2021-04-07 19:00:00'),(532,0,2,243,2,0,320000,32,'2021-04-07 19:00:00'),(533,0,45,244,10,0,320000,32,'2021-04-07 19:00:00'),(534,0,8,235,1,0,320000,32,'2021-04-07 19:00:00'),(535,0,45,232,13,0,320000,32,'2021-04-07 19:00:00'),(536,0,75,248,55,0,320000,32,'2021-04-07 19:00:00'),(537,0,60,236,33,0,320000,32,'2021-04-07 19:00:00'),(538,0,17,254,2,0,320000,32,'2021-04-07 19:00:00'),(539,0.185247,413,255,143,0,320000,32,'2021-04-07 19:00:00'),(540,0.0393764,13,256,5,0,320000,32,'2021-04-07 19:00:00'),(541,0,35,240,19,0,320000,32,'2021-04-07 19:00:00'),(542,0.295666,536,249,161,280000,320000,32,'2021-04-07 19:00:00'),(638,0,7,328,7,0,40000,4,'2021-04-20 09:00:00'),(640,0,76,295,41,0,120000,12,'2021-04-20 09:00:00'),(641,0,25,296,8,0,120000,12,'2021-04-20 09:00:00'),(642,0.0371802,99,297,21,0,120000,12,'2021-04-20 09:00:00'),(643,0.163722,191,298,83,0,120000,12,'2021-04-20 09:00:00'),(644,0.00945895,15,299,8,0,120000,12,'2021-04-20 09:00:00'),(645,0.00862665,3,300,16,0,120000,12,'2021-04-20 09:00:00'),(646,0,78,301,12,0,120000,12,'2021-04-20 09:00:00'),(647,0,100,302,11,0,120000,12,'2021-04-20 09:00:00'),(648,0,56,303,18,0,120000,12,'2021-04-20 09:00:00'),(649,0,9,304,1,0,120000,12,'2021-04-20 09:00:00'),(650,0,19,305,6,0,120000,12,'2021-04-20 09:00:00'),(651,0,72,306,20,0,120000,12,'2021-04-20 09:00:00'),(652,0,42,307,10,0,120000,12,'2021-04-20 09:00:00'),(653,0.0941892,24,308,3,0,120000,12,'2021-04-20 09:00:00'),(654,0,4,309,1,0,120000,12,'2021-04-20 09:00:00'),(655,0,8,310,1,0,120000,12,'2021-04-20 09:00:00'),(656,0,105,311,44,0,120000,12,'2021-04-20 09:00:00'),(657,0.347091,2407,312,480,0,120000,12,'2021-04-20 09:00:00'),(658,0,9,313,7,0,120000,12,'2021-04-20 09:00:00'),(659,0.00948047,51,314,13,0,120000,12,'2021-04-20 09:00:00'),(660,0,17,315,1,0,120000,12,'2021-04-20 09:00:00'),(661,0.444648,1435,316,85,20000,120000,12,'2021-04-20 09:00:00'),(662,0,8,317,92,0,120000,12,'2021-04-20 09:00:00'),(663,0,2,318,2,0,120000,12,'2021-04-20 09:00:00'),(664,0,50,319,12,0,120000,12,'2021-04-20 09:00:00'),(665,0.0172116,202,320,89,0,120000,12,'2021-04-20 09:00:00'),(666,0,223,321,17,0,120000,12,'2021-04-20 09:00:00'),(667,0,8,322,2,0,120000,12,'2021-04-20 09:00:00'),(668,0,1,323,24,0,120000,12,'2021-04-20 09:00:00'),(669,0,27,324,2,0,120000,12,'2021-04-20 09:00:00'),(670,0,22,325,9,0,90000,9,'2021-04-20 09:00:00'),(671,3.67485,890,326,176,100000,120000,12,'2021-04-20 09:00:00');
/*!40000 ALTER TABLE `hourinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `program`
--

DROP TABLE IF EXISTS `program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program` (
  `program_id` int NOT NULL AUTO_INCREMENT,
  `program_name` varchar(50) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`program_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `program_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=332 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program`
--

LOCK TABLES `program` WRITE;
/*!40000 ALTER TABLE `program` DISABLE KEYS */;
INSERT INTO `program` VALUES (215,'nvcontainer.exe',87),(216,'sihost.exe',87),(217,'svchost.exe',87),(218,'taskhostw.exe',87),(219,'explorer.exe',87),(220,'avpui.exe',87),(221,'StartMenuExperienceHost.exe',87),(222,'RuntimeBroker.exe',87),(223,'YourPhone.exe',87),(224,'SecurityHealthSystray.exe',87),(225,'RtkNGUI64.exe',87),(226,'SeriousBit.NetBalancer.Tray.exe',87),(227,'chrome.exe',87),(228,'punto.exe',87),(229,'ps64ldr.exe',87),(230,'jusched.exe',87),(231,'SearchApp.exe',87),(232,'TextInputHost.exe',87),(233,'NVIDIA Web Helper.exe',87),(234,'NVIDIA Share.exe',87),(235,'CompPkgSrv.exe',87),(236,'ShellExperienceHost.exe',87),(237,'UserOOBEBroker.exe',87),(238,'WinStore.App.exe',87),(239,'ApplicationFrameHost.exe',87),(240,'Video.UI.exe',87),(241,'Microsoft.Photos.exe',87),(242,'idea64.exe',87),(243,'fsnotifier64.exe',87),(244,'conhost.exe',87),(245,'notepad++.exe',87),(246,'SystemSettings.exe',87),(247,'MySQLWorkbench.exe',87),(248,'SceneBuilder.exe',87),(249,'java.exe',87),(250,'backgroundTaskHost.exe',87),(251,'EpicGamesLauncher.exe',87),(252,'EpicWebHelper.exe',87),(253,'NVIDIA GeForce Experience.exe',87),(254,'SystemSettingsBroker.exe',87),(255,'Discord.exe',87),(256,'WOMicClient.exe',87),(257,'NvSHIM.exe',87),(258,'NvOAWrapperCache.exe',87),(259,'nvcontainer.exe',88),(260,'svchost.exe',88),(261,'sihost.exe',88),(262,'taskhostw.exe',88),(263,'explorer.exe',88),(264,'avpui.exe',88),(265,'StartMenuExperienceHost.exe',88),(266,'RuntimeBroker.exe',88),(267,'YourPhone.exe',88),(268,'SecurityHealthSystray.exe',88),(269,'RtkNGUI64.exe',88),(270,'SeriousBit.NetBalancer.Tray.exe',88),(271,'punto.exe',88),(272,'jusched.exe',88),(273,'ps64ldr.exe',88),(274,'SearchApp.exe',88),(275,'NVIDIA Web Helper.exe',88),(276,'NVIDIA Share.exe',88),(277,'Microsoft.Photos.exe',88),(278,'SystemSettings.exe',88),(279,'ApplicationFrameHost.exe',88),(280,'UserOOBEBroker.exe',88),(281,'chrome.exe',88),(282,'idea64.exe',88),(283,'fsnotifier64.exe',88),(284,'conhost.exe',88),(285,'CompPkgSrv.exe',88),(286,'TextInputHost.exe',88),(287,'SceneBuilder.exe',88),(288,'ShellExperienceHost.exe',88),(289,'SystemSettingsBroker.exe',88),(290,'Discord.exe',88),(291,'WOMicClient.exe',88),(292,'Video.UI.exe',88),(293,'java.exe',88),(294,'BackgroundDownload.exe',88),(295,'nvcontainer.exe',92),(296,'sihost.exe',92),(297,'svchost.exe',92),(298,'explorer.exe',92),(299,'taskhostw.exe',92),(300,'avpui.exe',92),(301,'StartMenuExperienceHost.exe',92),(302,'RuntimeBroker.exe',92),(303,'YourPhone.exe',92),(304,'SecurityHealthSystray.exe',92),(305,'RtkNGUI64.exe',92),(306,'ShellExperienceHost.exe',92),(307,'SeriousBit.NetBalancer.Tray.exe',92),(308,'punto.exe',92),(309,'ps64ldr.exe',92),(310,'jusched.exe',92),(311,'SearchApp.exe',92),(312,'opera.exe',92),(313,'opera_crashreporter.exe',92),(314,'TextInputHost.exe',92),(315,'SystemSettingsBroker.exe',92),(316,'idea64.exe',92),(317,'NVIDIA Web Helper.exe',92),(318,'fsnotifier64.exe',92),(319,'conhost.exe',92),(320,'NVIDIA Share.exe',92),(321,'MySQLWorkbench.exe',92),(322,'UserOOBEBroker.exe',92),(323,'SystemSettings.exe',92),(324,'ApplicationFrameHost.exe',92),(325,'smartscreen.exe',92),(326,'java.exe',92),(327,'HxTsr.exe',92),(328,'SearchProtocolHost.exe',92),(329,'runnerw64.exe',92),(330,'git.exe',92),(331,'git-remote-https.exe',92);
/*!40000 ALTER TABLE `program` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usergroup`
--

DROP TABLE IF EXISTS `usergroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usergroup` (
  `group_id` int NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) NOT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  `gruop_status` int NOT NULL,
  `group_description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usergroup`
--

LOCK TABLES `usergroup` WRITE;
/*!40000 ALTER TABLE `usergroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `usergroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL,
  `password` varbinary(128) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (86,'',_binary '╨н╨ФтХи╤В╨┐┬дтФРтФ╝(╨╛тХХSg3╨О'),(87,'Goose',_binary '╨ДтЦАтХХF<╨Э~yтХб╤МтХЯ╨к╨н'),(88,'Goose1',_binary '╨ЩтХдтЦа<╤Ж╨╗,k╤М╨ктИЩ╨в╨З4'),(89,'Goose3',_binary 'lp╨л╨гNтФ╝|FmтФА`╨Щ╨ЦтХРтФФтХС'),(90,'Goose4',_binary 'тХеwтЦДw╨Ш╨Ы┬дтХСiтФ┤r╨З╨е╨зl'),(91,'Goose5',_binary '╨н╨й╤Д╤И?w(╨мтЦС@тХЩgjтЦТтИЪ'),(92,'Geese are cool!',_binary 'тФЬ╨отХЭ╨ЯтФЬ╨нтХР0^;╤Л)тЦИs'),(93,'Geese are acool!',_binary '3╨ТтХЬтФм╨╣тЦДтХЮтИЪ╨пZ╨бF╨╝'),(94,'New Goose',_binary '1T\ы7█л|DqЮЫE');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-05 17:27:46
