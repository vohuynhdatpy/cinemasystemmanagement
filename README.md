# Hệ Thống Quản Lý Rạp Chiếu Phim - Cinema System Management

## Giới thiệu
**Cinema System Management** là dự án hệ thống quản lý rạp chiếu phim được phát triển bằng **Java**.  
Dự án hỗ trợ quản lý phim, lịch chiếu, đặt vé, in hóa đơn và một số chức năng quản trị cơ bản.

Đây là đồ án/bài tập lớn môn Lập trình Java (RCP - HuongSK_BTL).

## Tính năng chính

- Quản lý phim (thêm, sửa, xóa, xem danh sách phim)
- Quản lý lịch chiếu
- Đặt vé xem phim
- In hóa đơn vé (file `.txt`)
- Quản lý người dùng / nhân viên (cơ bản)
- Lưu trữ dữ liệu qua file hoặc kết nối Database (T-SQL)

## Công nghệ sử dụng

- **Ngôn ngữ**: Java
- **IDE**: IntelliJ IDEA / Eclipse (có file cấu hình `.idea`, `.project`)
- **Database**: SQL SERVER
- **Thư viện**: Có thư mục `Libraries`

## Cấu trúc thư mục
├── src/                    # Source code Java
├── data/                   # Dữ liệu (nếu có)
├── out/production/...      # File biên dịch
├── Libraries/              # Thư viện bên ngoài
├── hoa_don_*.txt           # Hóa đơn vé đã in
└── README.md
text## Hướng dẫn cài đặt & chạy

1. Clone project về máy:
   ```bash
   git clone https://github.com/vohuyndatpy/cinemasystemmanagement.git

Mở project bằng IntelliJ IDEA hoặc Eclipse
Thêm thư viện trong thư mục Libraries vào project (nếu có)
Chạy file Main hoặc class có hàm main() trong thư mục src

Tác giả

Họ và tên: Võ Huỳnh Đạt (vohuyndatpy)
