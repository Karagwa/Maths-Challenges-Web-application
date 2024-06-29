-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 28, 2024 at 09:58 PM
-- Server version: 8.2.0
-- PHP Version: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `math_challenges`
--

-- --------------------------------------------------------

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
CREATE TABLE IF NOT EXISTS `administrator` (
  `Username` varchar(15) NOT NULL,
  `Password` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `analysisreport`
--

DROP TABLE IF EXISTS `analysisreport`;
CREATE TABLE IF NOT EXISTS `analysisreport` (
  `AnalysisReportId` varchar(20) NOT NULL,
  `PortNo` int DEFAULT NULL,
  `Most_attempted_question` int DEFAULT NULL,
  `School_Ranking` int DEFAULT NULL,
  `Performance_over_years` int DEFAULT NULL,
  `Percentage_Completion` int DEFAULT NULL,
  PRIMARY KEY (`AnalysisReportId`),
  KEY `PortNo` (`PortNo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
CREATE TABLE IF NOT EXISTS `answer` (
  `AnswerId` varchar(15) NOT NULL,
  `QuestionNo` int DEFAULT NULL,
  PRIMARY KEY (`AnswerId`),
  KEY `QuestionNo` (`QuestionNo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `applicant`
--

DROP TABLE IF EXISTS `applicant`;
CREATE TABLE IF NOT EXISTS `applicant` (
  `Username` varchar(15) NOT NULL,
  `PortNo` int DEFAULT NULL,
  `Image` blob,
  `Firstname` varchar(15) DEFAULT NULL,
  `Lastname` varchar(15) DEFAULT NULL,
  `EmailAddress` varchar(15) DEFAULT NULL,
  `Date_of_birth` date DEFAULT NULL,
  `School_Registration_Number` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`Username`),
  KEY `PortNo` (`PortNo`),
  KEY `School_Registration_Number` (`School_Registration_Number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `challenge`
--

DROP TABLE IF EXISTS `challenge`;
CREATE TABLE IF NOT EXISTS `challenge` (
  `ChallengeNumber` int NOT NULL,
  `ChallengeName` varchar(15) DEFAULT NULL,
  `OpeningDate` date DEFAULT NULL,
  `ClosingDate` date DEFAULT NULL,
  `ChallengeDuration` int DEFAULT NULL,
  `Number_of_Presented_Questions` int DEFAULT NULL,
  PRIMARY KEY (`ChallengeNumber`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `databaseinterface`
--

DROP TABLE IF EXISTS `databaseinterface`;
CREATE TABLE IF NOT EXISTS `databaseinterface` (
  `URL` varchar(80) NOT NULL,
  `PortNo` int DEFAULT NULL,
  PRIMARY KEY (`URL`),
  KEY `PortNo` (`PortNo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `email`
--

DROP TABLE IF EXISTS `email`;
CREATE TABLE IF NOT EXISTS `email` (
  `Username` varchar(15) DEFAULT NULL,
  `School_Registration_Number` varchar(15) DEFAULT NULL,
  `EmailId` varchar(15) NOT NULL,
  `Time` time DEFAULT NULL,
  `Type` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`EmailId`),
  KEY `Username` (`Username`),
  KEY `School_Registration_Number` (`School_Registration_Number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `javaserver`
--

DROP TABLE IF EXISTS `javaserver`;
CREATE TABLE IF NOT EXISTS `javaserver` (
  `PortNo` int NOT NULL,
  PRIMARY KEY (`PortNo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `participant`
--

DROP TABLE IF EXISTS `participant`;
CREATE TABLE IF NOT EXISTS `participant` (
  `Username` varchar(15) DEFAULT NULL,
  `Image` blob,
  `ParticipantId` varchar(15) NOT NULL,
  `Firstname` varchar(15) DEFAULT NULL,
  `Lastname` varchar(15) DEFAULT NULL,
  `EmailAddress` varchar(15) DEFAULT NULL,
  `Date_of_birth` date DEFAULT NULL,
  `School_Registration_Number` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`ParticipantId`),
  KEY `School_Registration_Number` (`School_Registration_Number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
CREATE TABLE IF NOT EXISTS `question` (
  `QuestionNo` int NOT NULL,
  `ChallengeNumber` int NOT NULL,
  `ParticipantId` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`QuestionNo`,`ChallengeNumber`),
  KEY `ChallengeNumber` (`ChallengeNumber`),
  KEY `ParticipantId` (`ParticipantId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `rejected`
--

DROP TABLE IF EXISTS `rejected`;
CREATE TABLE IF NOT EXISTS `rejected` (
  `Username` varchar(15) NOT NULL,
  `Image` blob,
  `Firstname` varchar(15) DEFAULT NULL,
  `Lastname` varchar(15) DEFAULT NULL,
  `EmailAddress` varchar(15) DEFAULT NULL,
  `Date_of_birth` date DEFAULT NULL,
  `School_Registration_Number` varchar(15) DEFAULT NULL,
  `RejectedId` varchar(15) NOT NULL,
  PRIMARY KEY (`Username`,`RejectedId`),
  KEY `School_Registration_Number` (`School_Registration_Number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
CREATE TABLE IF NOT EXISTS `report` (
  `ReportId` varchar(15) NOT NULL,
  `ChallengeNumber` int DEFAULT NULL,
  `ParticipantId` varchar(15) DEFAULT NULL,
  `Score` int DEFAULT NULL,
  `Time_for_each_Question` time DEFAULT NULL,
  `Total_time_for_challenge` time DEFAULT NULL,
  PRIMARY KEY (`ReportId`),
  KEY `ChallengeNumber` (`ChallengeNumber`),
  KEY `ParticipantId` (`ParticipantId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `school`
--

DROP TABLE IF EXISTS `school`;
CREATE TABLE IF NOT EXISTS `school` (
  `Name` varchar(30) DEFAULT NULL,
  `District` varchar(20) DEFAULT NULL,
  `School_Registration_Number` varchar(15) NOT NULL,
  PRIMARY KEY (`School_Registration_Number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `schoolrepresentative`
--

DROP TABLE IF EXISTS `schoolrepresentative`;
CREATE TABLE IF NOT EXISTS `schoolrepresentative` (
  `RepresentativeId` varchar(15) NOT NULL,
  `School_Registration_Number` varchar(15) DEFAULT NULL,
  `PortNo` int DEFAULT NULL,
  `Username` varchar(15) DEFAULT NULL,
  `Email_of_Representative` varchar(30) DEFAULT NULL,
  `Name_of_Representative` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`RepresentativeId`),
  KEY `School_Registration_Number` (`School_Registration_Number`),
  KEY `PortNo` (`PortNo`),
  KEY `Username` (`Username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
