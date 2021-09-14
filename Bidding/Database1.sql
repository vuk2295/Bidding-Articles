-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.2.24-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for database
CREATE DATABASE IF NOT EXISTS `database` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `database`;

-- Dumping structure for table database.articles
CREATE TABLE IF NOT EXISTS `articles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` tinyint(4) NOT NULL DEFAULT 0,
  `category` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `model` varchar(50) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  `dateOfCreate` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

-- Dumping data for table database.articles: ~12 rows (approximately)
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
REPLACE INTO `articles` (`id`, `status`, `category`, `name`, `model`, `price`, `description`, `time`, `dateOfCreate`) VALUES
	(19, 1, 'Phones', 'tesla', 'tesla', 100, 'tesla5', '1', '15/06/2019 22:20:00'),
	(20, 1, 'Phones', 'tesla2', 'tesla2', 100, 'tesla2', '1', '14/06/2019 21:16:25'),
	(21, 0, 'Phones', 'st', 'st', 2, 'tesla3', '1', '14/06/2019 17:28:00'),
	(22, 1, 'Phones', 'a', 'a', 11111, 'tesla4', '10', '14/06/2019 17:28:00'),
	(24, 1, 'Phones', 'rrr', 'rrr', 23, 'tesla1', '1', '14/06/2019 21:29:50'),
	(25, 1, 'Phones', 'qqq', 'qqq', 21313, 'qqq', '1', '14/06/2019 22:38:00'),
	(26, 0, 'Phones', 'OnePlus', '5T 128GB', 320, 'Very good mobile phone', '1', '14/06/2019 22:14:46'),
	(27, 0, 'Tablets', 'samsung', 'tx1', 35000, 'tesla', '1', '14/06/2019 21:20:31'),
	(28, 0, 'Phones', 'One Plus', '5t', 200, 'nije bitno', '1', '16/06/2019 18:40:31'),
	(29, 0, 'Phones', 'artice1', 'artice1', 12, 'artice1', '2', '18/06/2019 22:50:40'),
	(30, 1, 'Tablets', 'artice2', 'artice2', 12, 'artice2', '11', '18/06/2019 21:47:01'),
	(31, 1, 'Tablets', 'rr', 'rr', 12, 'rrr', '12', '18/06/2019 21:50:37');
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;

-- Dumping structure for table database.bidarticle
CREATE TABLE IF NOT EXISTS `bidarticle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idArticle` bigint(20) DEFAULT NULL,
  `price` bigint(20) DEFAULT NULL,
  `idUlogovani` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- Dumping data for table database.bidarticle: ~2 rows (approximately)
/*!40000 ALTER TABLE `bidarticle` DISABLE KEYS */;
REPLACE INTO `bidarticle` (`id`, `idArticle`, `price`, `idUlogovani`) VALUES
	(19, 29, 6, 16),
	(20, 22, 10, 17);
/*!40000 ALTER TABLE `bidarticle` ENABLE KEYS */;

-- Dumping structure for table database.money
CREATE TABLE IF NOT EXISTS `money` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idUlogovani` bigint(20) NOT NULL DEFAULT 0,
  `numberCard` bigint(20) DEFAULT NULL,
  `moneyCard` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=latin1;

-- Dumping data for table database.money: ~4 rows (approximately)
/*!40000 ALTER TABLE `money` DISABLE KEYS */;
REPLACE INTO `money` (`id`, `idUlogovani`, `numberCard`, `moneyCard`) VALUES
	(1, 1, 312311, 9879787879048),
	(113, 17, 1234567, 92110),
	(114, 18, 585858, 4600),
	(117, 16, 1234123, 21988);
/*!40000 ALTER TABLE `money` ENABLE KEYS */;

-- Dumping structure for table database.myarticles
CREATE TABLE IF NOT EXISTS `myarticles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idUlogovani` bigint(20) DEFAULT NULL,
  `idArticles` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=latin1;

-- Dumping data for table database.myarticles: ~6 rows (approximately)
/*!40000 ALTER TABLE `myarticles` DISABLE KEYS */;
REPLACE INTO `myarticles` (`id`, `idUlogovani`, `idArticles`) VALUES
	(44, 16, 25),
	(45, 16, 19),
	(46, 18, 28),
	(47, 18, 28),
	(48, 17, 29),
	(49, 16, 29);
/*!40000 ALTER TABLE `myarticles` ENABLE KEYS */;

-- Dumping structure for table database.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `passwordconfirm` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

-- Dumping data for table database.user: ~5 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
REPLACE INTO `user` (`id`, `username`, `password`, `passwordconfirm`) VALUES
	(1, 'Miroslav', '123', '123'),
	(16, 'stefo', 'stefo', 'stefo'),
	(17, 'vuk', 'vuk', 'vuk'),
	(18, 'ivo', 'ivo', 'ivo'),
	(19, 'op', 'oppp', 'oppp');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
