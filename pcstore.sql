-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 29, 2016 at 10:52 PM
-- Server version: 5.6.26
-- PHP Version: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pcstore`
--

-- --------------------------------------------------------

--
-- Table structure for table `cpu`
--

CREATE TABLE IF NOT EXISTS `cpu` (
  `SerialNo` int(20) NOT NULL,
  `Manufacturer` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `MHz` decimal(30,2) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cpu`
--

INSERT INTO `cpu` (`SerialNo`, `Manufacturer`, `MHz`) VALUES
(1, 'Intel', '2.23'),
(2, 'NVIDIA', '1.20'),
(3, 'IBM', '3.35'),
(4, 'NVIDIA', '1.20'),
(5, 'Motorola', '2.33'),
(6, 'NVIDIA', '1.20'),
(7, 'NVIDIA', '3.20');

-- --------------------------------------------------------

--
-- Table structure for table `includes_cpu`
--

CREATE TABLE IF NOT EXISTS `includes_cpu` (
  `PCSystem_SystemID` int(20) NOT NULL,
  `CPU_SerialNo` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `includes_cpu`
--

INSERT INTO `includes_cpu` (`PCSystem_SystemID`, `CPU_SerialNo`) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(4, 5),
(4, 6),
(4, 7);

-- --------------------------------------------------------

--
-- Table structure for table `includes_ram`
--

CREATE TABLE IF NOT EXISTS `includes_ram` (
  `PCSystem_SystemID` int(20) NOT NULL,
  `RAM_SerialNo` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `includes_ram`
--

INSERT INTO `includes_ram` (`PCSystem_SystemID`, `RAM_SerialNo`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 4),
(2, 5),
(3, 6),
(4, 7);

-- --------------------------------------------------------

--
-- Table structure for table `pcsystem`
--

CREATE TABLE IF NOT EXISTS `pcsystem` (
  `SystemID` int(20) NOT NULL,
  `Labeling` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `Manufacturer` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pcsystem`
--

INSERT INTO `pcsystem` (`SystemID`, `Labeling`, `Manufacturer`) VALUES
(1, 'G50', 'Lenovo'),
(2, 'N100SP', 'Samsung'),
(3, 'Air', 'Macbook'),
(4, 'D500', 'Dell');

-- --------------------------------------------------------

--
-- Table structure for table `ram`
--

CREATE TABLE IF NOT EXISTS `ram` (
  `SerialNo` int(20) NOT NULL,
  `Manufacturer` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `MB` int(30) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ram`
--

INSERT INTO `ram` (`SerialNo`, `Manufacturer`, `MB`) VALUES
(1, 'A-DATA', 256),
(2, 'Transcend', 64),
(3, 'Kingston Technology Corp.', 512),
(4, 'Kingston Technology Corp.', 512),
(5, 'GeIL', 1024),
(6, 'Transcend', 64),
(7, 'GeIL', 1024);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cpu`
--
ALTER TABLE `cpu`
  ADD PRIMARY KEY (`SerialNo`);

--
-- Indexes for table `pcsystem`
--
ALTER TABLE `pcsystem`
  ADD PRIMARY KEY (`SystemID`);

--
-- Indexes for table `ram`
--
ALTER TABLE `ram`
  ADD PRIMARY KEY (`SerialNo`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cpu`
--
ALTER TABLE `cpu`
  MODIFY `SerialNo` int(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `pcsystem`
--
ALTER TABLE `pcsystem`
  MODIFY `SystemID` int(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `ram`
--
ALTER TABLE `ram`
  MODIFY `SerialNo` int(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
