-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 10, 2020 lúc 11:48 AM
-- Phiên bản máy phục vụ: 10.1.38-MariaDB
-- Phiên bản PHP: 7.2.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `small_project_aht`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `confirmation_token`
--

CREATE TABLE `confirmation_token` (
  `ID` int(100) NOT NULL,
  `confirmation_token` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `confirmation_token`
--

INSERT INTO `confirmation_token` (`ID`, `confirmation_token`, `created_date`, `user_id`) VALUES
(15, '29ab6860-876e-4b2a-bb0e-5d39734ae5ed', '2020-04-08 06:12:07', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_function`
--

CREATE TABLE `tbl_function` (
  `ID` int(10) NOT NULL,
  `STATUS` int(10) NOT NULL,
  `FUNCTION_ORDER` int(10) NOT NULL,
  `FUNCTION_URL` varchar(255) NOT NULL,
  `FUNCTION_NAME` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(255) NOT NULL,
  `FUNCTION_CODE` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `tbl_function`
--

INSERT INTO `tbl_function` (`ID`, `STATUS`, `FUNCTION_ORDER`, `FUNCTION_URL`, `FUNCTION_NAME`, `DESCRIPTION`, `FUNCTION_CODE`) VALUES
(1, 0, 0, 'function_manager', 'function_manager', 'function_manager', 'function_manager'),
(2, 0, 2, 'role_manager', 'role_manager', 'role_manager', 'role_manager'),
(3, 0, 0, 'user_manager', 'user_manager', 'user_manager', 'user_manager');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_role`
--

CREATE TABLE `tbl_role` (
  `ID` int(10) NOT NULL,
  `STATUS` int(10) NOT NULL,
  `ROLE_NAME` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(255) NOT NULL,
  `ROLE_CODE` varchar(255) NOT NULL,
  `ROLE_ORDER` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `tbl_role`
--

INSERT INTO `tbl_role` (`ID`, `STATUS`, `ROLE_NAME`, `DESCRIPTION`, `ROLE_CODE`, `ROLE_ORDER`) VALUES
(1, 0, 'ROLE_ADMIN', 'ROLE_ADMIN', 'ROLE_ADMIN', 0),
(2, 0, 'ROLE_USER', 'ROLE_USER', 'ROLE_USER', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_role_function`
--

CREATE TABLE `tbl_role_function` (
  `ID` int(10) NOT NULL,
  `FUNCTION_ID` int(10) NOT NULL,
  `ROLE_ID` int(10) NOT NULL,
  `IS_ACTIVE` int(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `tbl_role_function`
--

INSERT INTO `tbl_role_function` (`ID`, `FUNCTION_ID`, `ROLE_ID`, `IS_ACTIVE`) VALUES
(1, 1, 1, 0),
(2, 2, 1, 0),
(3, 3, 1, 0),
(4, 3, 2, 0),
(5, 2, 2, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_role_user`
--

CREATE TABLE `tbl_role_user` (
  `ID` int(10) NOT NULL,
  `ROLE_ID` int(10) NOT NULL,
  `USER_ID` int(10) NOT NULL,
  `IS_ACTIVE` int(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `tbl_role_user`
--

INSERT INTO `tbl_role_user` (`ID`, `ROLE_ID`, `USER_ID`, `IS_ACTIVE`) VALUES
(1, 1, 1, 0),
(3, 2, 2, 0),
(11, 2, 1, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_user`
--

CREATE TABLE `tbl_user` (
  `ID` int(10) NOT NULL,
  `USER_NAME` varchar(255) NOT NULL,
  `FULL_NAME` varchar(255) NOT NULL,
  `GENDER` int(10) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `STATUS` int(10) NOT NULL,
  `EMAIL` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `tbl_user`
--

INSERT INTO `tbl_user` (`ID`, `USER_NAME`, `FULL_NAME`, `GENDER`, `PASSWORD`, `STATUS`, `EMAIL`) VALUES
(1, 'admin', 'admin', 1, '$2a$12$scW2DaB91qbdBWpDxGWBReGprVI2iW.SFGWNRvY9nEG4.h8sbRUoS', 0, 'xuanhopit98@gmail.com'),
(2, 'user', 'user', 1, '$2a$04$yti68IGITZlsvjCd4crzeu.Mn7FZjmzQn0OTWG6O.mdjRgdKtmGjm', 0, 'nghiemxuanhop98@gmail.com');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `confirmation_token`
--
ALTER TABLE `confirmation_token`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `tbl_function`
--
ALTER TABLE `tbl_function`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `tbl_role`
--
ALTER TABLE `tbl_role`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `tbl_role_function`
--
ALTER TABLE `tbl_role_function`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `tbl_role_user`
--
ALTER TABLE `tbl_role_user`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `confirmation_token`
--
ALTER TABLE `confirmation_token`
  MODIFY `ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `tbl_function`
--
ALTER TABLE `tbl_function`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `tbl_role`
--
ALTER TABLE `tbl_role`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `tbl_role_function`
--
ALTER TABLE `tbl_role_function`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `tbl_role_user`
--
ALTER TABLE `tbl_role_user`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
