-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 02, 2023 lúc 10:52 AM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `thietbididong`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loaisanpham`
--

CREATE TABLE `loaisanpham` (
  `Id` int(11) NOT NULL,
  `tenloaisanpham` varchar(255) NOT NULL,
  `hinhanhloaisanpham` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `loaisanpham`
--

INSERT INTO `loaisanpham` (`Id`, `tenloaisanpham`, `hinhanhloaisanpham`) VALUES
(1, 'Điện thoại', 'https://didongviet.vn/dchannel/wp-content/uploads/2023/09/5-hinh-anh-iphone-15-pro-max-didongviet.jpg'),
(2, 'Airpod', 'https://cdn.tgdd.vn/Files/2021/12/12/1403912/airpods-la-gi-tim-hieu-ve-cac-loai-airpods-moi-nha-5.jpg'),
(5, 'Tai nghe', 'https://image-us.24h.com.vn/upload/3-2019/images/2019-07-11/1562813652-516-galaxy-note-10-se-la-phien-ban-co-thiet-ke-hoan-thien-nhat-dong-note-samsung-galaxy-note-10-2-1562812702-width660height440.jpg'),
(6, 'Cáp sạc', 'https://hanoicomputercdn.com/media/product/70891_cap_sac_da_nang_aohi_magline_aoc_l010_.png'),
(7, 'Laptop', 'https://cdn-icons-png.flaticon.com/512/5957/5957235.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `Id` int(11) NOT NULL,
  `tensanpham` varchar(255) NOT NULL,
  `giasanpham` float NOT NULL,
  `hinhanhsanpham` varchar(255) NOT NULL,
  `mota` varchar(10000) NOT NULL,
  `idsanpham` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`Id`, `tensanpham`, `giasanpham`, `hinhanhsanpham`, `mota`, `idsanpham`) VALUES
(1, 'Điện thoại iPhone 15 Pro Max 256GB', 33990000, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fm.viettelstore.vn%2Fdien-thoai%2Fiphone-15-pro-max-pid317067.html&psig=AOvVaw3YUCtA7EtXsfxXkiu-l7G6&ust=1697293866351000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCOjp6_Ke84EDFQAAAAAdAAAAABAE', 'iPhone 15 Pro Max là một chiếc điện thoại thông minh cao cấp được mong đợi nhất năm 2023. Với nhiều tính năng mới và cải tiến, iPhone 15 Pro Max chắc chắn sẽ là một lựa chọn tuyệt vời cho những người dùng đang tìm kiếm một chiếc điện thoại có hiệu năng mạnh mẽ, camera chất lượng và thiết kế sang trọng.', 1),
(2, 'Điện thoại iPhone 15 Pro Max 256GB', 33990000, 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fm.viettelstore.vn%2Fdien-thoai%2Fiphone-15-pro-max-pid317067.html&psig=AOvVaw3YUCtA7EtXsfxXkiu-l7G6&ust=1697293866351000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCOjp6_Ke84EDFQAAAAAdAAAAABAE', 'iPhone 15 Pro Max là một chiếc điện thoại thông minh cao cấp được mong đợi nhất năm 2023. Với nhiều tính năng mới và cải tiến, iPhone 15 Pro Max chắc chắn sẽ là một lựa chọn tuyệt vời cho những người dùng đang tìm kiếm một chiếc điện thoại có hiệu năng mạnh mẽ, camera chất lượng và thiết kế sang trọng.', 1),
(3, 'Adapter Sạc 2 cổng Type C PD GaN 35W Mazer GAN35US', 120000, 'https://cdn.tgdd.vn/Products/Images/9499/313911/adapter-sac-2-cong-type-c-pd-gan-35w-mazer-gan35us-trang-6.jpg', 'Adapter Sạc 2 cổng Type C PD GaN 35W Mazer GAN35US sở hữu kiểu dáng sang trọng, gam màu thanh lịch, cung cấp năng lượng cho các thiết bị nhanh chóng, tương thích đa dạng với các thiết bị điện tử hiện nay, tiện lợi mang theo bất cứ đâu, mang đến cho bạn những trải nghiệm hoàn hảo nhất.', 4),
(4, 'Cáp Type C - Lightning 0.2m Xmobile L2203', 120000, 'https://cdn.tgdd.vn/Products/Images/58/311309/cap-type-c-lightning-0-2m-xmobile-l2203-4.jpg', 'Cáp Type C - Lightning 0.2m Xmobile L2203 sở hữu diện mạo thanh lịch, gam màu dễ sử dụng, tiện lợi tương thích với mọi thiết bị giúp bạn truyền tải năng lượng và cung cấp năng lượng nhanh chóng, độ dài đến 0.2 m sẽ đem đến cho bạn những cảm nhận hoàn hảo.', 4),
(5, 'Adapter Sạc 3 cổng USB Type C PD QC3.0 GaN 66W HyperJuice HJ265 ', 1160000, 'https://cdn.tgdd.vn/Products/Images/9499/310634/adapter-sac-3-cong-usb-type-c-pd-qc3-0-gan-66w-hyperjuice-hj265-1.jpg', 'Adapter Sạc 3 cổng USB Type C PD QC3.0 GaN 66W HyperJuice HJ265 sở hữu kiểu dáng thanh lịch, màu sắc sang trọng, tổng công suất sạc lên đến 66 W giúp sạc nhanh chóng, hứa hẹn mang đến cho người dùng những trải nghiệm tuyệt vời.', 4),
(6, 'Adapter Sạc 2 cổng USB Type C PD QC3.0 20W HyperJuice HJ205EU', 365000, 'https://cdn.tgdd.vn/Products/Images/9499/310631/adapter-sac-2-cong-usb-type-c-pd-qc3-0-20w-hyperjuice-hj205eu-1.jpg', 'Adapter sạc 2 cổng USB Type C PD QC3.0 20W HyperJuice HJ205EU sở hữu diện mạo bên ngoài thanh lịch, tương thích với nhiều thiết bị điện tử và tốc độ sạc nhanh chóng, hứa hẹn mang đến cho bạn những trải nghiệm tối ưu nhất.', 4);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  ADD PRIMARY KEY (`Id`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
