-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 02, 2022 at 03:23 PM
-- Server version: 5.5.16
-- PHP Version: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `f1`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `userId` varchar(12) NOT NULL,
  `customerName` varchar(30) NOT NULL,
  `phoneNumber` varchar(14) NOT NULL,
  `address` varchar(50) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`userId`, `customerName`, `phoneNumber`, `address`) VALUES
('c001', 'Customer', '+8801234567890', 'banani'),
('deba', 'Debashish', '+8801763923789', 'Kuril'),
('e004', 'Japheth', '+880741660161', '1460 40200'),
('e005', 'wanna', '+254792244908', '432445yyuu');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE IF NOT EXISTS `employee` (
  `userId` varchar(12) NOT NULL,
  `employeeName` varchar(50) NOT NULL,
  `phoneNumber` varchar(14) NOT NULL,
  `role` varchar(8) NOT NULL,
  `salary` double(8,2) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`userId`, `employeeName`, `phoneNumber`, `role`, `salary`) VALUES
('e001', 'Employee1', '+2541234567890', 'Manager', 50000.00),
('e002', 'Employee2', '+2541234567891', 'General', 30000.00);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
  `userId` varchar(12) NOT NULL,
  `password` varchar(12) NOT NULL,
  `status` int(1) NOT NULL,
  UNIQUE KEY `userId` (`userId`),
  UNIQUE KEY `userId_2` (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`userId`, `password`, `status`) VALUES
('e001', 'e001', 0),
('e002', 'e002', 0),
('c001', 'c001', 1),
('deba', 'aaaa', 1),
('e004', '12345', 1),
('e005', '12345', 1);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE IF NOT EXISTS `product` (
  `productId` int(5) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `productName` varchar(50) NOT NULL,
  `price` double(8,2) NOT NULL,
  `quantity` int(8) NOT NULL,
  PRIMARY KEY (`productId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`productId`, `productName`, `price`, `quantity`) VALUES
(00001, 'Juice 1L', 66.00, 90),
(00002, 'Mi Band', 2000.00, 5),
(00003, 'Frutika', 55.00, 21),
(00004, 'Samsung S9', 89999.00, 5),
(00005, 'Bagpack XL', 2500.00, 6);

-- --------------------------------------------------------

--
-- Table structure for table `purchaseinfo`
--

CREATE TABLE IF NOT EXISTS `purchaseinfo` (
  `purchaseId` int(5) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `userId` varchar(12) NOT NULL,
  `productId` varchar(12) NOT NULL,
  `cost` double(12,2) unsigned NOT NULL,
  `amount` int(8) unsigned NOT NULL,
  `date` varchar(18) NOT NULL,
  PRIMARY KEY (`purchaseId`),
  KEY `userId` (`userId`),
  KEY `userId_2` (`userId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `purchaseinfo`
--

INSERT INTO `purchaseinfo` (`purchaseId`, `userId`, `productId`, `cost`, `amount`, `date`) VALUES
(00001, 'deba', '00003', 55.00, 1, '2018-09-26'),
(00002, 'c001', '00005', 2500.00, 1, '2018-09-28'),
(00006, 'c001', '00003', 110.00, 2, '2018-09-28'),
(00007, 'e004', '00001', 330.00, 5, '2022-03-02');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `purchaseinfo`
--
ALTER TABLE `purchaseinfo`
  ADD CONSTRAINT `FK_PUR_CUS` FOREIGN KEY (`userId`) REFERENCES `purchaseinfo` (`userId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
