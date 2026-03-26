package dao;

import connectDB.ConnectDB;
import entity.TaiKhoan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class TaiKhoan_DAO {

    public boolean themTaiKhoan(TaiKhoan taiKhoan) {
        String sql = "{CALL InsertTaiKhoan(?, ?, ?)}";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến database", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            pstmt.setString(1, taiKhoan.getTenDangNhap());
            pstmt.setString(2, taiKhoan.getMatKhau());
            pstmt.setString(3, taiKhoan.getLoaiTaiKhoan());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm tài khoản: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        return false;
    }

    public List<TaiKhoan> getAllTaiKhoan() {
        List<TaiKhoan> taiKhoanList = new ArrayList<>();
        String sql = "{CALL GetAllTaiKhoan}";
        try (Connection con = ConnectDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến database", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return taiKhoanList;
            }

            while (rs.next()) {
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");
                String loaiTaiKhoan = rs.getString("loaiTaiKhoan");
                taiKhoanList.add(new TaiKhoan(tenDangNhap, matKhau, loaiTaiKhoan));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách tài khoản: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        return taiKhoanList;
    }

    public boolean capNhatTaiKhoan(TaiKhoan taiKhoan) {
        String sql = "{CALL UpdateTaiKhoan(?, ?, ?)}";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến database", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            pstmt.setString(1, taiKhoan.getMatKhau());
            pstmt.setString(2, taiKhoan.getLoaiTaiKhoan());
            pstmt.setString(3, taiKhoan.getTenDangNhap());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật tài khoản: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        return false;
    }

    public boolean xoaTaiKhoan(String tenDangNhap) {
        String sql = "{CALL DeleteTaiKhoan(?)}";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến database", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            pstmt.setString(1, tenDangNhap);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa tài khoản: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        return false;
    }

    public boolean xacThucTaiKhoan(String tenDangNhap, String matKhau) {
        String sql = "{CALL GetTaiKhoanByCredentials(?, ?)}";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến database", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            pstmt.setString(1, tenDangNhap);
            pstmt.setString(2, matKhau);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xác thực tài khoản: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        return false;
    }

}
