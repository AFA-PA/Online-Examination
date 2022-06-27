-- MariaDB dump 10.19  Distrib 10.9.4-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: ExamManDb
-- ------------------------------------------------------
-- Server version	10.9.4-MariaDB

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
-- Table structure for table `ADMIN`
--

/-- DROP  TABLE IF EXISTS `ADMIN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ADMIN` (
  `USER_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  CONSTRAINT `FK_ADMIN_USER_ID` FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ADMIN`
--

LOCK TABLES `ADMIN` WRITE;
/*!40000 ALTER TABLE `ADMIN` DISABLE KEYS */;
INSERT INTO `ADMIN` VALUES
(1),
(2);
/*!40000 ALTER TABLE `ADMIN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ANSWER`
--

/-- DROP  TABLE IF EXISTS `ANSWER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANSWER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CHOOSED_ID` bigint(20) DEFAULT NULL,
  `FORQUESTION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ANSWER_FORQUESTION_ID` (`FORQUESTION_ID`),
  KEY `FK_ANSWER_CHOOSED_ID` (`CHOOSED_ID`),
  CONSTRAINT `FK_ANSWER_CHOOSED_ID` FOREIGN KEY (`CHOOSED_ID`) REFERENCES `CHOICE` (`ID`),
  CONSTRAINT `FK_ANSWER_FORQUESTION_ID` FOREIGN KEY (`FORQUESTION_ID`) REFERENCES `QUESTION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANSWER`
--

LOCK TABLES `ANSWER` WRITE;
/*!40000 ALTER TABLE `ANSWER` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANSWER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CHOICE`
--

/-- DROP  TABLE IF EXISTS `CHOICE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CHOICE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CHOISE` varchar(255) DEFAULT NULL,
  `CORRECT` tinyint(1) DEFAULT 0,
  `QUESTION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CHOICE_QUESTION_ID` (`QUESTION_ID`),
  CONSTRAINT `FK_CHOICE_QUESTION_ID` FOREIGN KEY (`QUESTION_ID`) REFERENCES `QUESTION` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CHOICE`
--

LOCK TABLES `CHOICE` WRITE;
/*!40000 ALTER TABLE `CHOICE` DISABLE KEYS */;
INSERT INTO `CHOICE` VALUES
(1,'REMOTE METHOD INVOCATION',1,1),
(2,'REMOTE PROCEDURE CALL',0,1),
(3,'NONE',0,1),
(4,'SERVER TECH',1,2),
(5,'REMOTE METHOD INVOCATION',0,2),
(6,'REMOTE F*',0,2),
(7,'YOU ARE DEAD',1,3),
(8,'YOU ARE ALIVE',0,3),
(9,'YOU ARE SHIT',1,3);
/*!40000 ALTER TABLE `CHOICE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COURSE`
--

/-- DROP  TABLE IF EXISTS `COURSE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COURSE` (
  `ID` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DEPARTMENT_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_COURSE_DEPARTMENT_ID` (`DEPARTMENT_ID`),
  CONSTRAINT `FK_COURSE_DEPARTMENT_ID` FOREIGN KEY (`DEPARTMENT_ID`) REFERENCES `ORGANIZATIONALUNIT` (`ID`),
  CONSTRAINT `FK_COURSE_ID` FOREIGN KEY (`ID`) REFERENCES `ORGANIZATIONALUNIT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COURSE`
--

LOCK TABLES `COURSE` WRITE;
/*!40000 ALTER TABLE `COURSE` DISABLE KEYS */;
INSERT INTO `COURSE` VALUES
(3,'CSE3201','ADVANCED PROGRAMMING',2),
(11,'CSE3220','System Programming using Rust',2);
/*!40000 ALTER TABLE `COURSE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CourseRegistration`
--

/-- DROP  TABLE IF EXISTS `CourseRegistration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CourseRegistration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `semester` varchar(255) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `course_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_CourseRegistration_course_id` (`course_id`),
  CONSTRAINT `FK_CourseRegistration_course_id` FOREIGN KEY (`course_id`) REFERENCES `ORGANIZATIONALUNIT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CourseRegistration`
--

LOCK TABLES `CourseRegistration` WRITE;
/*!40000 ALTER TABLE `CourseRegistration` DISABLE KEYS */;
INSERT INTO `CourseRegistration` VALUES
(1,'First',2022,3),
(2,'First',2023,3);
/*!40000 ALTER TABLE `CourseRegistration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DEPARTMENT`
--

/-- DROP  TABLE IF EXISTS `DEPARTMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DEPARTMENT` (
  `ID` bigint(20) NOT NULL,
  `CREATEDODATE` date DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `ORGANIZATION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_DEPARTMENT_ORGANIZATION_ID` (`ORGANIZATION_ID`),
  CONSTRAINT `FK_DEPARTMENT_ID` FOREIGN KEY (`ID`) REFERENCES `ORGANIZATIONALUNIT` (`ID`),
  CONSTRAINT `FK_DEPARTMENT_ORGANIZATION_ID` FOREIGN KEY (`ORGANIZATION_ID`) REFERENCES `ORGANIZATIONALUNIT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DEPARTMENT`
--

LOCK TABLES `DEPARTMENT` WRITE;
/*!40000 ALTER TABLE `DEPARTMENT` DISABLE KEYS */;
INSERT INTO `DEPARTMENT` VALUES
(2,'2023-01-21','ACADEMICS',1);
/*!40000 ALTER TABLE `DEPARTMENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EXAM`
--

/-- DROP  TABLE IF EXISTS `EXAM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EXAM` (
  `ID` bigint(20) NOT NULL,
  `MINUTESALLOWED` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `STARTTIME` datetime DEFAULT NULL,
  `COURSE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_EXAM_COURSE_ID` (`COURSE_ID`),
  CONSTRAINT `FK_EXAM_COURSE_ID` FOREIGN KEY (`COURSE_ID`) REFERENCES `ORGANIZATIONALUNIT` (`ID`),
  CONSTRAINT `FK_EXAM_ID` FOREIGN KEY (`ID`) REFERENCES `ORGANIZATIONALUNIT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EXAM`
--

LOCK TABLES `EXAM` WRITE;
/*!40000 ALTER TABLE `EXAM` DISABLE KEYS */;
INSERT INTO `EXAM` VALUES
(1,60,'adavanced programming final exam','2023-02-13 08:30:00',3);
/*!40000 ALTER TABLE `EXAM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EXAMTAKEN`
--

/-- DROP  TABLE IF EXISTS `EXAMTAKEN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EXAMTAKEN` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `EXAM_ID` bigint(20) DEFAULT NULL,
  `TAKER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_EXAMTAKEN_EXAM_ID` (`EXAM_ID`),
  KEY `FK_EXAMTAKEN_TAKER_ID` (`TAKER_ID`),
  CONSTRAINT `FK_EXAMTAKEN_EXAM_ID` FOREIGN KEY (`EXAM_ID`) REFERENCES `ORGANIZATIONALUNIT` (`ID`),
  CONSTRAINT `FK_EXAMTAKEN_TAKER_ID` FOREIGN KEY (`TAKER_ID`) REFERENCES `USER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EXAMTAKEN`
--

LOCK TABLES `EXAMTAKEN` WRITE;
/*!40000 ALTER TABLE `EXAMTAKEN` DISABLE KEYS */;
/*!40000 ALTER TABLE `EXAMTAKEN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EXAMTAKEN_ANSWER`
--

/-- DROP  TABLE IF EXISTS `EXAMTAKEN_ANSWER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EXAMTAKEN_ANSWER` (
  `ExamTaken_ID` bigint(20) NOT NULL,
  `answers_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ExamTaken_ID`,`answers_ID`),
  KEY `FK_EXAMTAKEN_ANSWER_answers_ID` (`answers_ID`),
  CONSTRAINT `FK_EXAMTAKEN_ANSWER_ExamTaken_ID` FOREIGN KEY (`ExamTaken_ID`) REFERENCES `EXAMTAKEN` (`ID`),
  CONSTRAINT `FK_EXAMTAKEN_ANSWER_answers_ID` FOREIGN KEY (`answers_ID`) REFERENCES `ANSWER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EXAMTAKEN_ANSWER`
--

LOCK TABLES `EXAMTAKEN_ANSWER` WRITE;
/*!40000 ALTER TABLE `EXAMTAKEN_ANSWER` DISABLE KEYS */;
/*!40000 ALTER TABLE `EXAMTAKEN_ANSWER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ORGANIZATION`
--

/-- DROP  TABLE IF EXISTS `ORGANIZATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ORGANIZATION` (
  `ID` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `CREATEDBY_ID` bigint(20) DEFAULT NULL,
  `CREATEDONDATE` date NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`ID`),
  KEY `FK_ORGANIZATION_CREATEDBY_ID` (`CREATEDBY_ID`),
  CONSTRAINT `FK_ORGANIZATION_CREATEDBY_ID` FOREIGN KEY (`CREATEDBY_ID`) REFERENCES `USER` (`ID`),
  CONSTRAINT `FK_ORGANIZATION_ID` FOREIGN KEY (`ID`) REFERENCES `ORGANIZATIONALUNIT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ORGANIZATION`
--

LOCK TABLES `ORGANIZATION` WRITE;
/*!40000 ALTER TABLE `ORGANIZATION` DISABLE KEYS */;
INSERT INTO `ORGANIZATION` VALUES
(1,'AFAPA',1,'2023-02-13'),
(4,'ASTU',4,'2023-02-13'),
(5,'AASTU',2,'2023-02-13'),
(10,'Harambe University College',2,'2023-02-13');
/*!40000 ALTER TABLE `ORGANIZATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ORGANIZATIONALUNIT`
--

/-- DROP  TABLE IF EXISTS `ORGANIZATIONALUNIT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ORGANIZATIONALUNIT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `unitType` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ORGANIZATIONALUNIT`
--

LOCK TABLES `ORGANIZATIONALUNIT` WRITE;
/*!40000 ALTER TABLE `ORGANIZATIONALUNIT` DISABLE KEYS */;
INSERT INTO `ORGANIZATIONALUNIT` VALUES
(1,'org'),
(2,'dep'),
(3,'crs'),
(4,'org'),
(5,'org'),
(10,'org'),
(11,'crs');
/*!40000 ALTER TABLE `ORGANIZATIONALUNIT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PRIVILEGE`
--

/-- DROP  TABLE IF EXISTS `PRIVILEGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIVILEGE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PERMISSION` smallint(6) DEFAULT NULL,
  `GRANTEDON_ID` bigint(20) DEFAULT NULL,
  `GRANTEDTO_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PRIVILEGE_GRANTEDTO_ID` (`GRANTEDTO_ID`),
  KEY `FK_PRIVILEGE_GRANTEDON_ID` (`GRANTEDON_ID`),
  CONSTRAINT `FK_PRIVILEGE_GRANTEDON_ID` FOREIGN KEY (`GRANTEDON_ID`) REFERENCES `ORGANIZATIONALUNIT` (`ID`),
  CONSTRAINT `FK_PRIVILEGE_GRANTEDTO_ID` FOREIGN KEY (`GRANTEDTO_ID`) REFERENCES `USER` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRIVILEGE`
--

LOCK TABLES `PRIVILEGE` WRITE;
/*!40000 ALTER TABLE `PRIVILEGE` DISABLE KEYS */;
INSERT INTO `PRIVILEGE` VALUES
(1,64,4,4),
(2,127,5,2),
(3,127,10,2);
/*!40000 ALTER TABLE `PRIVILEGE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUESTION`
--

/-- DROP  TABLE IF EXISTS `QUESTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUESTION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `QUESTION` varchar(255) DEFAULT NULL,
  `EXAM_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_QUESTION_EXAM_ID` (`EXAM_ID`),
  CONSTRAINT `FK_QUESTION_EXAM_ID` FOREIGN KEY (`EXAM_ID`) REFERENCES `ORGANIZATIONALUNIT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUESTION`
--

LOCK TABLES `QUESTION` WRITE;
/*!40000 ALTER TABLE `QUESTION` DISABLE KEYS */;
INSERT INTO `QUESTION` VALUES
(1,'what is rmi?',1),
(2,'what is servelet?',1),
(3,'which of the following is correct?',1);
/*!40000 ALTER TABLE `QUESTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQUENCE`
--

/-- DROP  TABLE IF EXISTS `SEQUENCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SEQUENCE` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQUENCE`
--

LOCK TABLES `SEQUENCE` WRITE;
/*!40000 ALTER TABLE `SEQUENCE` DISABLE KEYS */;
INSERT INTO `SEQUENCE` VALUES
('SEQ_GEN',0);
/*!40000 ALTER TABLE `SEQUENCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TOKEN`
--

/-- DROP  TABLE IF EXISTS `TOKEN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TOKEN` (
  `EXPIRETIME` datetime DEFAULT NULL,
  `MODIFIEDAT` datetime DEFAULT NULL ON UPDATE current_timestamp(),
  `USER_ID` bigint(20) NOT NULL,
  `TOKEN` varchar(255) NOT NULL,
  PRIMARY KEY (`USER_ID`,`TOKEN`),
  CONSTRAINT `FK_TOKEN_USER_ID` FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TOKEN`
--

LOCK TABLES `TOKEN` WRITE;
/*!40000 ALTER TABLE `TOKEN` DISABLE KEYS */;
INSERT INTO `TOKEN` VALUES
('2023-02-14 11:09:12','2023-02-13 11:09:12',1,'iuBBOm+mrmIpIpaJCj8Rg/C8O3amtkUcGQrT1xlI47lCuzJdwAhDAivzcMz3V9bVV86gL4HAwKi0xtMcA8PqVw=='),
('2023-02-13 02:22:18','2023-02-12 02:22:18',1,'w8R5DTcqC4MwP+JiMrW4QVuslRvJJuwXKhmRFgMXbE7X0lFXWAizOjwus+8lPimPrGatUZrznB+6w/d71k/psQ=='),
('2023-02-14 14:25:36','2023-02-13 14:25:36',2,'cWj10v2E6oVrzp0B6WtcxaFl10cQCFLuh3p424O2lWLXs4n+7OsUt/XyvdeSRpgmAFMgPqUuRrUMqsnv2wcOGA=='),
('2023-02-14 14:21:37','2023-02-13 14:21:37',2,'wdVjDM26Lg97MGKGhCjA56iNMWNgowTNmXM0uit6RHXwlBiv5bEifqshtPJnptvqBABvBiDaGWf2NdXkMgqAGw=='),
('2023-02-12 21:49:45','2023-02-11 21:49:45',4,'3+blEc8R0xQ4k3qsufZibcgjj1gJZYXyCXvXd2niGTz1ASvlOJrGf7TbCauFyt4zeuslWrLO9k/ZPmbIR9HO5g=='),
('2023-02-14 11:23:06','2023-02-13 11:23:06',4,'3QohYu+e1vH5vANGJWjb6QJL5GWKTmjdWxyVeCF5RvaXWJ3N4FX6HjUiFWony1g+YROK9xxZW5APbtZn3Gy25w=='),
('2023-02-12 21:01:00','2023-02-11 21:01:00',4,'F+xVlI4oBY+40QOS1R7FiSweLhKdxFjNl1D6QW3VS7NsPnCtF1WLuo8YfxEg1V/+xSVpPY01fE2szu+Fgfxmjg=='),
('2023-02-12 21:49:54','2023-02-11 21:49:54',4,'jJuAPZvrRKO+jxiUhjFwTR3FpU/H6ywVSf0pCKW1ZV9OKaqCE0kM5vwBsUst6M/bVJw+oieK5Vxzz4acVrV45g==');
/*!40000 ALTER TABLE `TOKEN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER`
--

/-- DROP  TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FIRSTNAME` varchar(255) DEFAULT NULL,
  `LASTNAME` varchar(255) DEFAULT NULL,
  `PHONENUMBER` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `email_ix` (`EMAIL`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES
(1,'abdi@afapa.org','abdi','taju','+251927257963'),
(2,'keti@afapa.org','keti','jundi','+251927253463'),
(3,'abuz@afapa.org','KETI','JUNDI','+251927257963'),
(4,'peter@afapa.org','peter','laki','+251927253463'),
(5,'abdisa@afapa.org','asndnj','agtsdn','6541320'),
(6,'abdis@afapa.org','asdsf','adsfdgf','asadsfag');
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USERCREDENTIAL`
--

/-- /-- DROP   TABLE IF EXISTS `USERCREDENTIAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERCREDENTIAL` (
  `HASHEDPASSWORD` varchar(255) DEFAULT NULL,
  `USER_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  CONSTRAINT `FK_USERCREDENTIAL_USER_ID` FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERCREDENTIAL`
--

LOCK TABLES `USERCREDENTIAL` WRITE;
/*!40000 ALTER TABLE `USERCREDENTIAL` DISABLE KEYS */;
INSERT INTO `USERCREDENTIAL` VALUES
('PBKDF2WithHmacSHA256:2048:c7Y2OCjjCTfDf1Y5AAfdm1EshnsSHWcsKEON/gNVcaY=:Q2a1YFXl7MVTbD7f4T4uL7dzKKKLw+hCNAsgXZFgdY4=',1),
('PBKDF2WithHmacSHA256:2048:oUlKBpgzwpCGZbG/QAzJj/Q/ho375BVOkD1tR9S9M24=:gNxLBT2a5/mSUKtuLPzvGwTjhRxB8Z4Gj/+dyOAG5C0=',2),
('PBKDF2WithHmacSHA256:2048:QdPSKaqsTm1/tdbH+Nx5xSedkjFBwEhAA5W2p9BXFME=:wu9YnBKMiwm4p5pvoFlSAulk91153WTrSxJIHCIYthk=',4),
('PBKDF2WithHmacSHA256:2048:G2LxqlWb22bGhiB7D1tkF6MCrComjqd8p3R7YGuniRY=:Z56z23xS/AufzY7Vo1JrunqCupso9hF/3K277RCgq9s=',5),
('PBKDF2WithHmacSHA256:2048:UDoyvpo8wp//H7QK6si9iZw2NbxfMzNGxscPZDDsST0=:boes552wDTzBWTbD42WAqcHgwdl6l/6WJnuSKPXlAg4=',6);
/*!40000 ALTER TABLE `USERCREDENTIAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USERCREDENTIAL_TOKEN`
--

/-- DROP  TABLE IF EXISTS `USERCREDENTIAL_TOKEN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERCREDENTIAL_TOKEN` (
  `UserCredential_USER_ID` bigint(20) NOT NULL,
  `USER_ID` bigint(20) NOT NULL,
  `TOKEN` varchar(255) NOT NULL,
  PRIMARY KEY (`UserCredential_USER_ID`,`USER_ID`,`TOKEN`),
  KEY `FK_USERCREDENTIAL_TOKEN_USER_ID` (`USER_ID`,`TOKEN`),
  CONSTRAINT `FK_USERCREDENTIAL_TOKEN_USER_ID` FOREIGN KEY (`USER_ID`, `TOKEN`) REFERENCES `TOKEN` (`USER_ID`, `TOKEN`),
  CONSTRAINT `FK_USERCREDENTIAL_TOKEN_UserCredential_USER_ID` FOREIGN KEY (`UserCredential_USER_ID`) REFERENCES `USERCREDENTIAL` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERCREDENTIAL_TOKEN`
--

LOCK TABLES `USERCREDENTIAL_TOKEN` WRITE;
/*!40000 ALTER TABLE `USERCREDENTIAL_TOKEN` DISABLE KEYS */;
/*!40000 ALTER TABLE `USERCREDENTIAL_TOKEN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserRegistration`
--

/-- /-- DROP   TABLE IF EXISTS `UserRegistration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserRegistration` (
  `registeredUsers_ID` bigint(20) NOT NULL,
  `reg_id` bigint(20) NOT NULL,
  PRIMARY KEY (`registeredUsers_ID`,`reg_id`),
  KEY `FK_UserRegistration_reg_id` (`reg_id`),
  CONSTRAINT `FK_UserRegistration_reg_id` FOREIGN KEY (`reg_id`) REFERENCES `CourseRegistration` (`id`),
  CONSTRAINT `FK_UserRegistration_registeredUsers_ID` FOREIGN KEY (`registeredUsers_ID`) REFERENCES `USER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserRegistration`
--

LOCK TABLES `UserRegistration` WRITE;
/*!40000 ALTER TABLE `UserRegistration` DISABLE KEYS */;
INSERT INTO `UserRegistration` VALUES
(1,1),
(2,1),
(3,2),
(4,2);
/*!40000 ALTER TABLE `UserRegistration` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-17  2:06:28
