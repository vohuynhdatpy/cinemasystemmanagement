-- Xóa cơ sở dữ liệu QLRCP nếu đã tồn tại (SQL Server syntax)
IF EXISTS (SELECT name FROM sys.databases WHERE name = 'QLRCP')
BEGIN
    ALTER DATABASE QLRCP SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE QLRCP;
END
GO

-- Tạo cơ sở dữ liệu QLRCP
CREATE DATABASE QLRCP;
GO

-- Sử dụng cơ sở dữ liệu QLRCP
USE QLRCP;
GO

-- Tạo bảng RapChieuPhim
CREATE TABLE RapChieuPhim (
    MaRapChieu VARCHAR(10) PRIMARY KEY,
    TenRapChieu NVARCHAR(100),
    DiaDiem NVARCHAR(100)
);
GO

-- Tạo bảng PhongChieuPhim
CREATE TABLE PhongChieuPhim (
    SHPC VARCHAR(10) PRIMARY KEY,
    MaRapChieu VARCHAR(10),
    HangGhe VARCHAR(5),
    SoGhe INT,
    FOREIGN KEY (MaRapChieu) REFERENCES RapChieuPhim(MaRapChieu)
);
GO

-- Tạo bảng Ghe
CREATE TABLE Ghe (
    MaGhe VARCHAR(10),
    SHPC VARCHAR(10),
    TrangThai NVARCHAR(20) DEFAULT 'Trong',
    ThoiGianDat DATETIME,
    PRIMARY KEY (MaGhe, SHPC),
    FOREIGN KEY (SHPC) REFERENCES PhongChieuPhim(SHPC)
);
GO

-- Tạo bảng Phim
CREATE TABLE Phim (
    MaPhim VARCHAR(10) PRIMARY KEY,
    TenPhim NVARCHAR(100),
    DaoDien NVARCHAR(100),
    TheLoai NVARCHAR(50),
    ThoiLuong INT,
    ImageLink NVARCHAR(255)
);
GO

-- Tạo bảng SuatChieu
CREATE TABLE SuatChieu (
    MaSuatChieu VARCHAR(10) PRIMARY KEY,
    ThoiGianBatDau TIME,
    ThoiGianKetThuc TIME
);
GO

-- Tạo bảng LichChieu
CREATE TABLE LichChieu (
    MaLichChieu VARCHAR(10) PRIMARY KEY,
    SHPC VARCHAR(10),
    MaPhim VARCHAR(10),
    NgayChieu DATE,
    GioChieu TIME,
    TrangThai INT DEFAULT 0,
    FOREIGN KEY (SHPC) REFERENCES PhongChieuPhim(SHPC),
    FOREIGN KEY (MaPhim) REFERENCES Phim(MaPhim)
);
GO

-- Tạo bảng LichChieuM (bảng mapping)
CREATE TABLE LichChieuM (
    MaLichChieu VARCHAR(10) PRIMARY KEY,
    MaPhim VARCHAR(10),
    NgayChieu DATE,
    GioChieu TIME,
    SHPC VARCHAR(10),
    TrangThai INT DEFAULT 0,
    FOREIGN KEY (MaPhim) REFERENCES Phim(MaPhim),
    FOREIGN KEY (SHPC) REFERENCES PhongChieuPhim(SHPC)
);
GO

-- Tạo bảng Phim_LichChieu (bảng trung gian)
CREATE TABLE Phim_LichChieu (
    MaPhim VARCHAR(10),
    MaLichChieu VARCHAR(10),
    PRIMARY KEY (MaPhim, MaLichChieu),
    FOREIGN KEY (MaPhim) REFERENCES Phim(MaPhim),
    FOREIGN KEY (MaLichChieu) REFERENCES LichChieu(MaLichChieu)
);
GO

-- Tạo bảng LichChieu_SuatChieu (bảng trung gian)
CREATE TABLE LichChieu_SuatChieu (
    MaLichChieu VARCHAR(10),
    MaSuatChieu VARCHAR(10),
    PRIMARY KEY (MaLichChieu, MaSuatChieu),
    FOREIGN KEY (MaLichChieu) REFERENCES LichChieu(MaLichChieu),
    FOREIGN KEY (MaSuatChieu) REFERENCES SuatChieu(MaSuatChieu)
);
GO

-- Tạo bảng KhachHang
CREATE TABLE KhachHang (
    MaKhachHang VARCHAR(10) PRIMARY KEY,
    SDT VARCHAR(15),
    TenDangNhap VARCHAR(50),
    TenKhachHang NVARCHAR(100),
    Email VARCHAR(100),
    NgaySinh DATE
);
GO

-- Tạo bảng NhanVien
CREATE TABLE NhanVien (
    MaNhanVien VARCHAR(10) PRIMARY KEY,
    SDT VARCHAR(15),
    TenNhanVien NVARCHAR(100),
    TenDangNhap VARCHAR(50),
    Luong INT,
    CMND VARCHAR(20),
    ChucVu NVARCHAR(50)
);
GO

-- Tạo bảng TaiKhoan
CREATE TABLE TaiKhoan (
    TenDangNhap VARCHAR(50) PRIMARY KEY,
    MatKhau VARCHAR(50),
    LoaiTaiKhoan NVARCHAR(20)
);
GO

-- Tạo bảng VeM
CREATE TABLE VeM (
    MaVe VARCHAR(10) PRIMARY KEY,
    MaLichChieu VARCHAR(10),
    TenKhachHang NVARCHAR(100),
    SDT VARCHAR(15),
    MaGhe VARCHAR(10),
    Gia DECIMAL(18,2),
    FOREIGN KEY (MaLichChieu) REFERENCES LichChieuM(MaLichChieu)
);
GO

-- Tạo bảng Ve (bảng chi tiết vé)
CREATE TABLE Ve (
    MaVe VARCHAR(10) PRIMARY KEY,
    MaPhim VARCHAR(10),
    TenPhim NVARCHAR(100),
    DaoDien NVARCHAR(100),
    TheLoai NVARCHAR(50),
    ThoiLuong INT,
    ChoNgoi VARCHAR(10),
    NgayChieu DATE,
    ThoiGianBatDau TIME,
    ThoiGianKetThuc TIME,
    PhongChieu VARCHAR(10),
    Gia DECIMAL(18,2),
    TenKhachHang NVARCHAR(100),
    MaLichChieu VARCHAR(10),
    SDT VARCHAR(15),
    MaGhe VARCHAR(10),
    FOREIGN KEY (MaPhim) REFERENCES Phim(MaPhim),
    FOREIGN KEY (PhongChieu) REFERENCES PhongChieuPhim(SHPC),
    FOREIGN KEY (MaLichChieu) REFERENCES LichChieu(MaLichChieu)
);
GO

-- Tạo bảng HoaDon
CREATE TABLE HoaDon (
    MaHoaDon VARCHAR(10) PRIMARY KEY,
    MaKhachHang VARCHAR(10),
    MaNhanVien VARCHAR(10),
    MaVe VARCHAR(10),
    NgayLap DATE,
    Tong DECIMAL(18,2),
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKhachHang),
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (MaVe) REFERENCES VeM(MaVe)
);
GO

-- Thêm dữ liệu mẫu
INSERT INTO RapChieuPhim (MaRapChieu, TenRapChieu, DiaDiem) VALUES
('RC001', N'Rạp 1', N'Hà Nội'),
('RC002', N'Rạp 2', N'Hồ Chí Minh');
GO

INSERT INTO PhongChieuPhim (SHPC, MaRapChieu, HangGhe, SoGhe) VALUES
('HPC01', 'RC001', 'A', 50),
('HPC02', 'RC001', 'B', 100),
('HPC03', 'RC002', 'A', 60);
GO

-- Thêm dữ liệu ghế mẫu cho HPC01
INSERT INTO Ghe (MaGhe, SHPC, TrangThai) VALUES
('A1', 'HPC01', 'Trong'),
('A2', 'HPC01', 'Trong'),
('A3', 'HPC01', 'Trong'),
('B1', 'HPC01', 'Trong'),
('B2', 'HPC01', 'Trong');
GO

INSERT INTO Phim (MaPhim, TenPhim, DaoDien, TheLoai, ThoiLuong, ImageLink) VALUES
('PHIM01', N'Phim Hành Động', N'Đạo diễn A', N'Hành Động', 120, 'poster1.jfif'),
('PHIM02', N'Phim Tình Cảm', N'Đạo diễn B', N'Tình Cảm', 90, 'poster2.jfif');
GO

INSERT INTO SuatChieu (MaSuatChieu, ThoiGianBatDau, ThoiGianKetThuc) VALUES
('SC001', '18:00:00', '20:00:00'),
('SC002', '20:00:00', '22:00:00'),
('SC003', '14:00:00', '16:00:00');
GO

INSERT INTO LichChieu (MaLichChieu, SHPC, MaPhim, NgayChieu, GioChieu, TrangThai) VALUES
('LC001', 'HPC01', 'PHIM01', '2024-11-01', '18:00:00', 1),
('LC002', 'HPC02', 'PHIM02', '2024-11-01', '20:00:00', 0);
GO

INSERT INTO LichChieuM (MaLichChieu, MaPhim, NgayChieu, GioChieu, SHPC, TrangThai) VALUES
('LC001', 'PHIM01', '2024-11-01', '18:00:00', 'HPC01', 1),
('LC002', 'PHIM02', '2024-11-01', '20:00:00', 'HPC02', 0);
GO

INSERT INTO Phim_LichChieu (MaPhim, MaLichChieu) VALUES
('PHIM01', 'LC001'),
('PHIM02', 'LC002');
GO

INSERT INTO LichChieu_SuatChieu (MaLichChieu, MaSuatChieu) VALUES
('LC001', 'SC001'),
('LC002', 'SC002');
GO

INSERT INTO KhachHang (MaKhachHang, SDT, TenDangNhap, TenKhachHang, Email, NgaySinh) VALUES
('KH001', '0123456789', 'user1', N'Nguyễn Văn A', 'user1@example.com', '1990-01-01'),
('KH002', '0987654321', 'user2', N'Trần Thị B', 'user2@example.com', '1992-02-02');
GO

INSERT INTO NhanVien (MaNhanVien, SDT, TenNhanVien, TenDangNhap, Luong, CMND, ChucVu) VALUES
('NV001', '0123456788', N'Lê Văn C', 'nv1', 5000000, '123456789', N'Nhân viên'),
('NV002', '0987654320', N'Nguyễn Thị D', 'nv2', 6000000, '987654321', N'Quản lý');
GO

INSERT INTO TaiKhoan (TenDangNhap, MatKhau, LoaiTaiKhoan) VALUES
('user1', 'password1', N'Khách hàng'),
('admin', 'adminpass', N'Nhân viên'),
('nv1', 'nv1pass', N'Nhân viên'),
('nv2', 'nv2pass', N'Nhân viên');
GO

INSERT INTO VeM (MaVe, MaLichChieu, TenKhachHang, SDT, MaGhe, Gia) VALUES
('VE001', 'LC001', N'Nguyễn Văn A', '0123456789', 'A1', 100000),
('VE002', 'LC002', N'Trần Thị B', '0987654321', 'B1', 100000);
GO

INSERT INTO Ve (MaVe, MaPhim, TenPhim, DaoDien, TheLoai, ThoiLuong, ChoNgoi, NgayChieu, ThoiGianBatDau, ThoiGianKetThuc, PhongChieu, Gia, TenKhachHang, MaLichChieu, SDT, MaGhe) VALUES
('VE001', 'PHIM01', N'Phim Hành Động', N'Đạo diễn A', N'Hành Động', 120, 'A1', '2024-11-01', '18:00:00', '20:00:00', 'HPC01', 100000, N'Nguyễn Văn A', 'LC001', '0123456789', 'A1'),
('VE002', 'PHIM02', N'Phim Tình Cảm', N'Đạo diễn B', N'Tình Cảm', 90, 'B1', '2024-11-01', '20:00:00', '21:30:00', 'HPC02', 100000, N'Trần Thị B', 'LC002', '0987654321', 'B1');
GO

INSERT INTO HoaDon (MaHoaDon, MaKhachHang, MaNhanVien, MaVe, NgayLap, Tong) VALUES
('HD001', 'KH001', 'NV001', 'VE001', '2024-11-01', 100000),
('HD002', 'KH002', 'NV002', 'VE002', '2024-11-01', 100000);
GO

-- Tạo các stored procedures
-- Stored procedure: InsertKhachHang
CREATE PROCEDURE InsertKhachHang
    @MaKhachHang VARCHAR(10),
    @SDT VARCHAR(15),
    @TenDangNhap VARCHAR(50),
    @TenKhachHang NVARCHAR(100),
    @Email VARCHAR(100),
    @NgaySinh DATE
AS
BEGIN
    INSERT INTO KhachHang (MaKhachHang, SDT, TenDangNhap, TenKhachHang, Email, NgaySinh)
    VALUES (@MaKhachHang, @SDT, @TenDangNhap, @TenKhachHang, @Email, @NgaySinh);
END
GO

-- Stored procedure: DeleteKhachHang
CREATE PROCEDURE DeleteKhachHang
    @MaKhachHang VARCHAR(10)
AS
BEGIN
    DELETE FROM KhachHang WHERE MaKhachHang = @MaKhachHang;
END
GO

-- Stored procedure: GetKhachHangByMaKhachHang
CREATE PROCEDURE GetKhachHangByMaKhachHang
    @MaKhachHang VARCHAR(10)
AS
BEGIN
    SELECT MaKhachHang, SDT, TenDangNhap, TenKhachHang, Email, NgaySinh
    FROM KhachHang
    WHERE MaKhachHang = @MaKhachHang;
END
GO

-- Stored procedure: GetAllKhachHang
CREATE PROCEDURE GetAllKhachHang
AS
BEGIN
    SELECT MaKhachHang, SDT, TenDangNhap, TenKhachHang, Email, NgaySinh
    FROM KhachHang;
END
GO

-- Stored procedure: GetTop5KhachHangByRevenue
CREATE PROCEDURE GetTop5KhachHangByRevenue
    @TuNgay DATE,
    @DenNgay DATE
AS
BEGIN
    SELECT TOP 5
        kh.MaKhachHang,
        kh.TenKhachHang,
        kh.SDT,
        kh.Email,
        kh.NgaySinh,
        COUNT(DISTINCT hd.MaHoaDon) as SoLuongDon,
        ISNULL(SUM(hd.Tong), 0) as TongTien
    FROM KhachHang kh
    LEFT JOIN HoaDon hd ON kh.MaKhachHang = hd.MaKhachHang
        AND hd.NgayLap BETWEEN @TuNgay AND @DenNgay
    GROUP BY kh.MaKhachHang, kh.TenKhachHang, kh.SDT, kh.Email, kh.NgaySinh
    ORDER BY TongTien DESC;
END
GO

-- Stored procedure: GetTopKhachHangByOrderCount
CREATE PROCEDURE GetTopKhachHangByOrderCount
    @TuNgay DATE,
    @DenNgay DATE
AS
BEGIN
    SELECT TOP 10
        kh.MaKhachHang,
        kh.TenKhachHang,
        kh.SDT,
        kh.Email,
        kh.NgaySinh,
        COUNT(DISTINCT hd.MaHoaDon) as SoLuongDon,
        ISNULL(SUM(hd.Tong), 0) as TongTien
    FROM KhachHang kh
    LEFT JOIN HoaDon hd ON kh.MaKhachHang = hd.MaKhachHang
        AND hd.NgayLap BETWEEN @TuNgay AND @DenNgay
    GROUP BY kh.MaKhachHang, kh.TenKhachHang, kh.SDT, kh.Email, kh.NgaySinh
    ORDER BY SoLuongDon DESC;
END
GO

-- Stored procedure: getAllKhachHangThongKe
CREATE PROCEDURE getAllKhachHangThongKe
    @TuNgay DATE,
    @DenNgay DATE
AS
BEGIN
    SELECT
        kh.MaKhachHang,
        kh.TenKhachHang,
        kh.SDT,
        kh.Email,
        kh.NgaySinh,
        COUNT(DISTINCT hd.MaHoaDon) as SoLuongDon,
        ISNULL(SUM(hd.Tong), 0) as TongTien
    FROM KhachHang kh
    LEFT JOIN HoaDon hd ON kh.MaKhachHang = hd.MaKhachHang
        AND hd.NgayLap BETWEEN @TuNgay AND @DenNgay
    GROUP BY kh.MaKhachHang, kh.TenKhachHang, kh.SDT, kh.Email, kh.NgaySinh
    ORDER BY kh.MaKhachHang;
END
GO

-- Stored procedure: GetHoaDonDetails
CREATE PROCEDURE GetHoaDonDetails
AS
BEGIN
    SELECT 
        hd.MaHoaDon,
        hd.NgayLap,
        hd.Tong,
        nv.MaNhanVien,
        nv.TenNhanVien,
        kh.MaKhachHang,
        kh.TenKhachHang,
        v.MaVe
    FROM HoaDon hd
    JOIN NhanVien nv ON hd.MaNhanVien = nv.MaNhanVien
    JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang
    LEFT JOIN VeM v ON hd.MaVe = v.MaVe
    ORDER BY hd.NgayLap DESC;
END
GO

-- Stored procedure: InsertTaiKhoan
CREATE PROCEDURE InsertTaiKhoan
    @TenDangNhap VARCHAR(50),
    @MatKhau VARCHAR(50),
    @LoaiTaiKhoan NVARCHAR(20)
AS
BEGIN
    INSERT INTO TaiKhoan (TenDangNhap, MatKhau, LoaiTaiKhoan)
    VALUES (@TenDangNhap, @MatKhau, @LoaiTaiKhoan);
END
GO

-- Stored procedure: GetAllTaiKhoan
CREATE PROCEDURE GetAllTaiKhoan
AS
BEGIN
    SELECT TenDangNhap, MatKhau, LoaiTaiKhoan
    FROM TaiKhoan;
END
GO

-- Stored procedure: UpdateTaiKhoan
CREATE PROCEDURE UpdateTaiKhoan
    @MatKhau VARCHAR(50),
    @LoaiTaiKhoan NVARCHAR(20),
    @TenDangNhap VARCHAR(50)
AS
BEGIN
    UPDATE TaiKhoan
    SET MatKhau = @MatKhau, LoaiTaiKhoan = @LoaiTaiKhoan
    WHERE TenDangNhap = @TenDangNhap;
END
GO

-- Stored procedure: DeleteTaiKhoan
CREATE PROCEDURE DeleteTaiKhoan
    @TenDangNhap VARCHAR(50)
AS
BEGIN
    DELETE FROM TaiKhoan WHERE TenDangNhap = @TenDangNhap;
END
GO

-- Stored procedure: GetTaiKhoanByCredentials
CREATE PROCEDURE GetTaiKhoanByCredentials
    @TenDangNhap VARCHAR(50),
    @MatKhau VARCHAR(50)
AS
BEGIN
    SELECT TenDangNhap, MatKhau, LoaiTaiKhoan
    FROM TaiKhoan
    WHERE TenDangNhap = @TenDangNhap AND MatKhau = @MatKhau;
END
GO
