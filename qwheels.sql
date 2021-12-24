-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 07, 2021 at 08:19 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `qwheels`
--
CREATE DATABASE IF NOT EXISTS `qwheels` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `qwheels`;

-- --------------------------------------------------------

--
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
CREATE TABLE `cars` (
  `car_id` int(11) NOT NULL,
  `car_brand` text NOT NULL,
  `car_model` text NOT NULL,
  `car_color` text NOT NULL,
  `car_year` year(4) NOT NULL,
  `type` text NOT NULL,
  `description` text DEFAULT NULL,
  `filename` varchar(150) DEFAULT NULL,
  `availability` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cars`
--

INSERT INTO `cars` (`car_id`, `car_brand`, `car_model`, `car_color`, `car_year`, `type`, `description`, `filename`, `availability`) VALUES
(2, 'Ford', 'Focus', 'red', 2018, 'gas', 'best car ever', 'https://www.motortrend.com/uploads/sites/10/2017/10/2018-ford-focus-se-sedan-angular-front.png', 1),
(3, 'Tesla', 'Model X', 'green', 2021, 'electric', 'cool car', NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
CREATE TABLE `locations` (
  `location_id` int(11) NOT NULL,
  `location_address` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `fullname` text NOT NULL,
  `username` varchar(150) NOT NULL,
  `password` text NOT NULL,
  `email` varchar(300) NOT NULL,
  `role` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `fullname`, `username`, `password`, `email`, `role`) VALUES
(1, 'Fname Lname', 'testmail', '$2y$10$A7Phzktw1wy0anYKFswNGeOMAUAwh9d5dP1Cf32Ghr0od9T6yZMaW', 'testmail@mail.com', NULL),
(2, 'Test User', 'go', '$2y$10$kZBwb0vbYrOB1Kvh4/QlO.EKE/dI6faZfwxicqw4CF.69nNEDg1wW', 'go@mail.com', NULL),
(3, 'Admin User', 'admin', 'admin', 'admin@mail.com', 'admin'),
(4, 'newuser newuser', 'newuser', '$2y$10$6oM.SMaE78n8GcZBOJHQROY6D/YnwQnf6wyuYcpUSTwrLVtEwmtB2', 'newuser@email.com', NULL),
(5, 'another user', 'username', '$2y$10$6mBKppUwrFuFeVfqSt4w9OnBxKpsQhkHjdtjpPMhXdoTQx19aMiyC', 'username', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_faves`
--

DROP TABLE IF EXISTS `user_faves`;
CREATE TABLE `user_faves` (
  `user_fave_id` int(11) NOT NULL,
  `username` varchar(150) NOT NULL,
  `car_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `user_reservations`
--

DROP TABLE IF EXISTS `user_reservations`;
CREATE TABLE `user_reservations` (
  `reservation_id` int(11) NOT NULL,
  `username` varchar(150) NOT NULL,
  `car_id` int(11) NOT NULL,
  `pickup_location` text NOT NULL,
  `dropoff_location` text NOT NULL,
  `pickup_date` datetime NOT NULL,
  `dropoff_date` datetime NOT NULL,
  `total_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cars`
--
ALTER TABLE `cars`
  ADD PRIMARY KEY (`car_id`);

--
-- Indexes for table `locations`
--
ALTER TABLE `locations`
  ADD PRIMARY KEY (`location_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `user_faves`
--
ALTER TABLE `user_faves`
  ADD PRIMARY KEY (`user_fave_id`),
  ADD KEY `user_faves_to_user` (`username`);

--
-- Indexes for table `user_reservations`
--
ALTER TABLE `user_reservations`
  ADD PRIMARY KEY (`reservation_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cars`
--
ALTER TABLE `cars`
  MODIFY `car_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `locations`
--
ALTER TABLE `locations`
  MODIFY `location_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `user_faves`
--
ALTER TABLE `user_faves`
  MODIFY `user_fave_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `user_reservations`
--
ALTER TABLE `user_reservations`
  MODIFY `reservation_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `user_faves`
--
ALTER TABLE `user_faves`
  ADD CONSTRAINT `user_faves_to_user` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
