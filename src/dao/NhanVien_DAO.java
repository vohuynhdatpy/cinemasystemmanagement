package dao;

import connectDB.ConnectDB;
import entity.NhanVien;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhanVien_DAO {

    public boolean insertNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (maNhanVien, SDT, TenNhanVien, TenDangNhap, Luong, CMND, ChucVu) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, nv.getMaNhanVien());
            pstmt.setString(2, nv.getSDT());
            pstmt.setString(3, nv.getTenNhanVien());
            pstmt.setString(4, nv.getTenDangNhap());
            pstmt.setInt(5, nv.getLuong());
            pstmt.setString(6, nv.getCmnd());
            pstmt.setString(7, nv.getChucVu());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            Logger.getLogger(NhanVien_DAO.class.getName()).log(Level.SEVERE, "Lỗi khi thêm nhân viên", ex);
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhân viên: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // Update employee information
    public boolean updateNhanVien(NhanVien nv) {
        String sql = "UPDATE NhanVien SET TenNhanVien = ?, TenDangNhap = ?, Luong = ?, CMND = ?, ChucVu = ? WHERE maNhanVien = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, nv.getTenNhanVien());
            pstmt.setString(2, nv.getTenDangNhap());
            pstmt.setInt(3, nv.getLuong());
            pstmt.setString(4, nv.getCmnd());
            pstmt.setString(5, nv.getChucVu());
            pstmt.setString(6, nv.getMaNhanVien());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            Logger.getLogger(NhanVien_DAO.class.getName()).log(Level.SEVERE, "Error updating employee", ex);
        }
        return false;
    }

    // Get employee by maNhanVien
    public NhanVien getNhanVienByMa(String maNhanVien) {
        String sql = "SELECT * FROM NhanVien WHERE maNhanVien = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, maNhanVien);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new NhanVien(
                            rs.getString("maNhanVien"),
                            rs.getString("SDT"),
                            rs.getString("TenNhanVien"),
                            rs.getString("TenDangNhap"),
                            rs.getInt("Luong"),
                            rs.getString("CMND"),
                            rs.getString("ChucVu")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien_DAO.class.getName()).log(Level.SEVERE, "Error fetching employee by ID", ex);
        }
        return null;
    }

    // Retrieve all employees
    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> nhanVienList = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Connection con = ConnectDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getString("maNhanVien"),
                        rs.getString("SDT"),
                        rs.getString("TenNhanVien"),
                        rs.getString("TenDangNhap"),
                        rs.getInt("Luong"),
                        rs.getString("CMND"),
                        rs.getString("ChucVu")
                );
                nhanVienList.add(nv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien_DAO.class.getName()).log(Level.SEVERE, "Error fetching all employees", ex);
        }
        return nhanVienList;
    }

    // Update the search method similarly
    public List<NhanVien> searchNhanVien(String maNhanVien, String sdt, String tenNhanVien, String tenDangNhap, Integer luong, String cmnd, String chucVu) {
        List<NhanVien> nhanVienList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM NhanVien WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (maNhanVien != null && !maNhanVien.isEmpty()) {
            sql.append(" AND maNhanVien LIKE ?");
            params.add("%" + maNhanVien + "%");
        }
        if (sdt != null && !sdt.isEmpty()) {
            sql.append(" AND SDT LIKE ?");
            params.add("%" + sdt + "%");
        }
        if (tenNhanVien != null && !tenNhanVien.isEmpty()) {
            sql.append(" AND TenNhanVien LIKE ?");
            params.add("%" + tenNhanVien + "%");
        }
        if (tenDangNhap != null && !tenDangNhap.isEmpty()) {
            sql.append(" AND TenDangNhap LIKE ?");
            params.add("%" + tenDangNhap + "%");
        }
        if (luong != null) {
            sql.append(" AND Luong = ?");
            params.add(luong);
        }
        if (cmnd != null && !cmnd.isEmpty()) {
            sql.append(" AND CMND LIKE ?");
            params.add("%" + cmnd + "%");
        }
        if (chucVu != null && !chucVu.isEmpty()) {
            sql.append(" AND ChucVu LIKE ?");
            params.add("%" + chucVu + "%");
        }

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    NhanVien nv = new NhanVien(
                            rs.getString("maNhanVien"),
                            rs.getString("SDT"),
                            rs.getString("TenNhanVien"),
                            rs.getString("TenDangNhap"),
                            rs.getInt("Luong"),
                            rs.getString("CMND"),
                            rs.getString("ChucVu")
                    );
                    nhanVienList.add(nv);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(NhanVien_DAO.class.getName()).log(Level.SEVERE, "Error searching employees", e);
        }

        return nhanVienList;
    }

    public boolean deleteNhanVien(String maNhanVien) {
        String sql = "DELETE FROM NhanVien WHERE maNhanVien = ?"; // SQL query to delete based on Employee ID (maNhanVien)
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maNhanVien); // Set the maNhanVien parameter in the query

            int rowsAffected = pstmt.executeUpdate(); // Execute the query

            return rowsAffected > 0; // Return true if a row was deleted
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception for debugging
            return false; // Return false if there was an error
        }
    }

    public boolean isMaNhanVienExist(String maNhanVien) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE maNhanVien = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, maNhanVien);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien_DAO.class.getName()).log(Level.SEVERE, "Lỗi khi kiểm tra mã nhân viên", ex);
        }
        return false;
    }

    public boolean updateChucVuNhanVien(String maNhanVien, String tenNhanVien, String chucVu) {
        String sql = "UPDATE NhanVien SET TenNhanVien = ?, ChucVu = ? WHERE maNhanVien = ?";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, tenNhanVien);
            pstmt.setString(2, chucVu);
            pstmt.setString(3, maNhanVien);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            Logger.getLogger(NhanVien_DAO.class.getName()).log(Level.SEVERE, "Lỗi khi cập nhật chức vụ nhân viên", ex);
            return false;
        }
    }


}
