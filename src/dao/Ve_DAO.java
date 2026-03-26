package dao;

import connectDB.ConnectDB;
import entity.Ve;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import dao.HoaDon_Dao;
import entity.*;
public class Ve_DAO {
    public boolean luuThongTinVe(String maVe, String maPhim, String tenPhim, String daoDien,
                                String theLoai, int thoiLuong, String choNgoi, Date ngayChieu,
                                Time thoiGianBatDau, Time thoiGianKetThuc, String phongChieu,
                                double gia, String tenKhachHang) {

        String sql = "INSERT INTO Ve (MaVe, MaPhim, TenPhim, DaoDien, TheLoai, ThoiLuong, "
                   + "ChoNgoi, NgayChieu, ThoiGianBatDau, ThoiGianKetThuc, PhongChieu, Gia, TenKhachHang, SDT, MaGhe) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maVe);           // VE176268781234 → 19 ký tự → OK
            ps.setString(2, maPhim);
            ps.setString(3, tenPhim);
            ps.setString(4, daoDien);
            ps.setString(5, theLoai);
            ps.setInt(6, thoiLuong);
            ps.setString(7, choNgoi);        // A1,A2,A3 → OK (VARCHAR(100))
            ps.setDate(8, ngayChieu);
            ps.setTime(9, thoiGianBatDau);
            ps.setTime(10, thoiGianKetThuc);
            ps.setString(11, phongChieu);
            ps.setDouble(12, gia);
            ps.setString(13, tenKhachHang);
            ps.setString(14, "");            // SDT
            ps.setString(15, choNgoi);       // MaGhe = ChoNgoi
            int rows = ps.executeUpdate();
            System.out.println("Lưu vé thành công: " + maVe + " | Ghế: " + choNgoi);
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("LỖI LƯU VÉ: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public boolean luuVeM(String maVe, String maLichChieu, String sdt, String maGhe, double gia) {
        String sql = "INSERT INTO VeM (MaVe, MaLichChieu, SDT, MaGhe, Gia) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maVe);
            ps.setString(2, maLichChieu);
            ps.setString(3, sdt);
            ps.setString(4, maGhe);
            ps.setDouble(5, gia);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Ve getVeByMaVe(String maVe) {
        String sql = "SELECT * FROM Ve WHERE MaVe = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, maVe);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Ve ve = new Ve();
                ve.setMaVe(rs.getString("MaVe"));
                ve.setTenPhim(rs.getString("TenPhim"));
                ve.setDaoDien(rs.getString("DaoDien"));
                ve.setTheLoai(rs.getString("TheLoai"));
                ve.setThoiLuong(rs.getInt("ThoiLuong"));
                ve.setChoNgoi(rs.getString("GheNgoi"));
                ve.setNgayChieu(rs.getDate("NgayChieu"));
                ve.setThoiGianBatDau(rs.getTime("GioBatDau"));
                ve.setThoiGianKetThuc(rs.getTime("GioKetThuc"));
                ve.setPhongChieu(rs.getString("MaPhong"));
                ve.setGia(rs.getDouble("TongTien"));
                ve.setTenKhachHang(rs.getString("TenKhachHang"));
                return ve;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}