package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Ve;

public class HoaDon_Dao {

    private Connection con;

    public HoaDon_Dao() {
        this.con = ConnectDB.getConnection();
    }

    // Lấy tất cả hóa đơn
    public List<HoaDon> getAllHoaDon() {
        List<HoaDon> list = new ArrayList<>();
        String sql = """
                SELECT hd.MaHoaDon, hd.NgayLap, hd.Tong,
                       nv.TenNhanVien,
                       kh.TenKhachHang,
                       v.MaVe
                FROM HoaDon hd
                JOIN NhanVien nv ON hd.MaNhanVien = nv.MaNhanVien
                JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang
                LEFT JOIN VeM v ON hd.MaVe = v.MaVe
                ORDER BY hd.NgayLap DESC
                """;

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                HoaDon hd = new HoaDon();

                NhanVien nv = new NhanVien();
                nv.setTenNhanVien(rs.getString("TenNhanVien"));

                KhachHang kh = new KhachHang();
                kh.setTenKhachHang(rs.getString("TenKhachHang"));

                Ve ve = null;
                if (rs.getString("MaVe") != null) {
                    ve = new Ve();
                }

                hd.setMaHoaDon(rs.getString("MaHoaDon"));
                hd.setNgayLap(rs.getDate("NgayLap").toLocalDate());
                hd.setTongTien(rs.getDouble("Tong"));
                hd.setNhanVien(nv);
                hd.setKhachHang(kh);
                hd.setVe(ve);

                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy hóa đơn theo khoảng thời gian
    public List<HoaDon> getHoaDonTheoKhoangThoiGian1(LocalDate tuNgay, LocalDate denNgay) {
        List<HoaDon> list = new ArrayList<>();
        String sql = """
                SELECT hd.MaHoaDon, hd.NgayLap, hd.Tong,
                       nv.TenNhanVien,
                       kh.TenKhachHang,
                       v.MaVe
                FROM HoaDon hd
                JOIN NhanVien nv ON hd.MaNhanVien = nv.MaNhanVien
                JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang
                LEFT JOIN VeM v ON hd.MaVe = v.MaVe
                WHERE hd.NgayLap BETWEEN ? AND ?
                ORDER BY hd.NgayLap DESC
                """;

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setDate(1, Date.valueOf(tuNgay));
            pst.setDate(2, Date.valueOf(denNgay));

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();

                NhanVien nv = new NhanVien();
                nv.setTenNhanVien(rs.getString("TenNhanVien"));

                KhachHang kh = new KhachHang();
                kh.setTenKhachHang(rs.getString("TenKhachHang"));

                Ve ve = null;
                if (rs.getString("MaVe") != null) {
                    ve = new Ve();
                }

                hd.setMaHoaDon(rs.getString("MaHoaDon"));
                hd.setNgayLap(rs.getDate("NgayLap").toLocalDate());
                hd.setTongTien(rs.getDouble("Tong"));
                hd.setNhanVien(nv);
                hd.setKhachHang(kh);
                hd.setVe(ve);

                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<HoaDon> getHoaDonTheoKhoangThoiGian(LocalDate tuNgay, LocalDate denNgay) {
        List<HoaDon> ds = new ArrayList<>();
        String sql = "SELECT hd.MaHoaDon, hd.NgayLap, hd.Tong, " +
                     "kh.TenKhachHang, nv.TenNhanVien " +
                     "FROM HoaDon hd " +
                     "JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang " +
                     "LEFT JOIN NhanVien nv ON hd.MaNhanVien = nv.MaNhanVien " +
                     "WHERE hd.NgayLap BETWEEN ? AND ? " +
                     "ORDER BY hd.NgayLap DESC";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(tuNgay));
            ps.setDate(2, java.sql.Date.valueOf(denNgay));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDon hd = new HoaDon();
                    hd.setMaHoaDon(rs.getString("MaHoaDon"));

                    // Khách hàng
                    KhachHang kh = new KhachHang();
                    kh.setTenKhachHang(rs.getString("TenKhachHang"));
                    hd.setKhachHang(kh);

                    // Nhân viên (có thể null)
                    String tenNV = rs.getString("TenNhanVien");
                    if (tenNV != null) {
                        NhanVien nv = new NhanVien();
                        nv.setTenNhanVien(tenNV);
                        hd.setNhanVien(nv);
                    } else {
                        hd.setNhanVien(null);
                    }

                    // Ngày lập & tổng tiền
                    hd.setNgayLap(rs.getDate("NgayLap").toLocalDate());
                    hd.setTongTien(rs.getDouble("Tong"));

                    ds.add(hd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }




    // Tính tổng doanh thu từ danh sách hóa đơn
    public double tinhTongDoanhThu(List<HoaDon> dsHoaDon) {
        return dsHoaDon.stream().mapToDouble(HoaDon::getTongTien).sum();
    }

    // Đếm số lượng hóa đơn từ danh sách
    public int demSoHoaDon(List<HoaDon> dsHoaDon) {
        return dsHoaDon.size();
    }

    // Sắp xếp danh sách theo ngày lập
    public void sapXepTheoNgayLap(List<HoaDon> ds, boolean tangDan) {
        ds.sort(Comparator.comparing(HoaDon::getNgayLap));
        if (!tangDan) {
            java.util.Collections.reverse(ds);
        }
    }

    // Sắp xếp danh sách theo mã hóa đơn
    public void sapXepTheoMaHD(List<HoaDon> ds, boolean tangDan) {
        ds.sort(Comparator.comparing(HoaDon::getMaHoaDon));
        if (!tangDan) {
            java.util.Collections.reverse(ds);
        }
    }

    // Sắp xếp danh sách theo tổng tiền
    public void sapXepTheoTongTien(List<HoaDon> ds, boolean tangDan) {
        ds.sort(Comparator.comparing(HoaDon::getTongTien));
        if (!tangDan) {
            java.util.Collections.reverse(ds);
        }
    }
    public boolean insertHoaDon(HoaDon hd) {
        String sql = "INSERT INTO HoaDon (MaHoaDon, MaKhachHang, MaNhanVien, MaVe, NgayLap, Tong) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, hd.getMaHoaDon());
            pstmt.setString(2, hd.getKhachHang().getMaKhachHang());
            pstmt.setString(3, hd.getNhanVien().getMaNhanVien());
            pstmt.setString(4, hd.getVe().getMaVe());
            pstmt.setDate(5, Date.valueOf(hd.getNgayLap())); 
            pstmt.setDouble(6, hd.getTongTien());
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}


