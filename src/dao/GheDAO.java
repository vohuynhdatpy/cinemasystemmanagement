//package dao;
//
//import connectDB.ConnectDB;
//import entity.Ghe;
//import entity.PhongChieuPhim;
//import entity.TrangThaiGhe;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class GheDAO {
//    // Phương thức lấy danh sách ghế từ cơ sở dữ liệu với SHPC cố định
//    public List<Ghe> layDanhSachGhe(String SHPC) {
//        List<Ghe> danhSachGhe = new ArrayList<>();
//        String sql = "SELECT maGhe, trangThai FROM Ghe WHERE SHPC = ? " +
//                     "ORDER BY LEFT(maGhe, 1), CAST(SUBSTRING(maGhe, 2, LEN(maGhe) - 1) AS INT)";
//        try (Connection conn = ConnectDB.getConnection();
//             PreparedStatement statement = conn.prepareStatement(sql)) {
//            
//            statement.setString(1, SHPC);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    String maGhe = resultSet.getString("maGhe");
//                    String trangThai = resultSet.getString("trangThai"); // dùng tên cột "trangThai"
//                    TrangThaiGhe trangThaiGhe;
//                    if (trangThai != null) {
//                        String norm = trangThai.trim().toLowerCase();
//                        if (norm.equals("trong") || norm.equals("trống") || norm.equals("trong")) {
//                            trangThaiGhe = TrangThaiGhe.TRONG;
//                        } else if (norm.equals("da_dat") || norm.equals("đã đặt") || norm.equals("da dat") || norm.equals("dadat")) {
//                            trangThaiGhe = TrangThaiGhe.DA_DAT;
//                        } else {
//                            trangThaiGhe = TrangThaiGhe.TRONG; // Default fallback
//                        }
//                    } else {
//                        trangThaiGhe = TrangThaiGhe.TRONG;
//                    }
//                    danhSachGhe.add(new Ghe(maGhe, trangThaiGhe, new PhongChieuPhim(SHPC)));
//                }
//            }
//            System.out.println("GheDAO: Loaded " + danhSachGhe.size() + " seats for SHPC=" + SHPC);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return danhSachGhe;
//    }
//    
//    /**
//     * Cập nhật trạng thái ghế. Trả về true nếu update thành công (số hàng bị ảnh hưởng > 0).
//     */
//    public boolean capNhatTrangThaiGhe(String maGhe, String trangThai, String SHPC) {
//        String sql = "UPDATE Ghe SET trangThai = ?, thoiGianDat = SYSDATETIME() WHERE maGhe = ? AND SHPC = ?";
//        try (Connection conn = ConnectDB.getConnection();
//             PreparedStatement statement = conn.prepareStatement(sql)) {
//            statement.setString(1, trangThai);
//            statement.setString(2, maGhe);
//            statement.setString(3, SHPC);
//            int updated = statement.executeUpdate();
//            System.out.println("GheDAO: update result for " + maGhe + " -> " + updated + " row(s).");
//            return updated > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public List<String> getSelectedSeats(String roomCode) {
//        List<String> selectedSeats = new ArrayList<>();
//        String sql = "SELECT maGhe FROM Ghe WHERE trangThai = 'Da_Dat' AND SHPC = ?";
//        try (Connection conn = ConnectDB.getConnection();
//             PreparedStatement statement = conn.prepareStatement(sql)) {
//            
//            statement.setString(1, roomCode);
//            try (ResultSet rs = statement.executeQuery()) {
//                while (rs.next()) {
//                    selectedSeats.add(rs.getString("maGhe"));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return selectedSeats;
//    }
//
//    // Phương thức lấy mã ghế mới nhất dựa trên thời gian đặt
//    public String getLatestSelectedSeat(String roomCode) {
//        String latestSeat = null;
//        String sql = "SELECT TOP 1 maGhe FROM Ghe WHERE trangThai = 'Da_Dat' AND SHPC = ? ORDER BY thoiGianDat DESC";
//        try (Connection conn = ConnectDB.getConnection();
//             PreparedStatement statement = conn.prepareStatement(sql)) {
//            
//            statement.setString(1, roomCode);
//            try (ResultSet rs = statement.executeQuery()) {
//                if (rs.next()) {
//                    latestSeat = rs.getString("maGhe");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return latestSeat;
//    }
//}
package dao;

import connectDB.ConnectDB;
import entity.Ghe;
import entity.PhongChieuPhim;
import entity.TrangThaiGhe;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GheDAO (sửa/điều chỉnh)
 * - capNhatTrangThaiGhe trả boolean và có logging / kiểm tra tồn tại bản ghi trước khi UPDATE
 * - layDanhSachGhe giữ nguyên nhưng có log kích thước danh sách
 * - getSelectedSeats / getLatestSelectedSeat giữ nguyên
 *
 * Ghi chú quan trọng (đọc trước khi dùng):
 * - Đảm bảo giá trị SHPC bạn truyền từ UI khớp với giá trị trong DB (ví dụ 'HPC01' trong SQL mẫu).
 *   Nếu UI truyền "PCP01" mà DB dùng "HPC01" thì UPDATE sẽ không tìm thấy bản ghi -> updated = 0.
 * - Nếu ConnectDB.getConnection() trả null hoặc connection có autoCommit = false, bạn cần kiểm tra ConnectDB.
 */
public class GheDAO {

    public List<Ghe> layDanhSachGhe(String SHPC) {
        List<Ghe> danhSachGhe = new ArrayList<>();
        String sql = "SELECT MaGhe, TrangThai FROM Ghe WHERE SHPC = ? " +
                     "ORDER BY LEFT(MaGhe, 1), CAST(SUBSTRING(MaGhe, 2, LEN(MaGhe) - 1) AS INT)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, SHPC);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maGhe = rs.getString("MaGhe");
                    String trangThai = rs.getString("TrangThai");
                    TrangThaiGhe trangThaiGhe;
                    if (trangThai != null) {
                        String norm = trangThai.trim().toLowerCase();
                        if (norm.equals("trong") || norm.equals("trống")) {
                            trangThaiGhe = TrangThaiGhe.TRONG;
                        } else if (norm.equals("da_dat") || norm.equals("đã đặt") || norm.equals("dadat") || norm.equals("da dat") || norm.equals("da-dat")) {
                            trangThaiGhe = TrangThaiGhe.DA_DAT;
                        } else {
                            trangThaiGhe = TrangThaiGhe.TRONG;
                        }
                    } else {
                        trangThaiGhe = TrangThaiGhe.TRONG;
                    }
                    // Lưu ý: constructor Ghe(ma, trangThai, phong) phải tồn tại trong entity.Ghe
                    danhSachGhe.add(new Ghe(maGhe, trangThaiGhe, new PhongChieuPhim(SHPC)));
                }
            }
            System.out.println("GheDAO: Loaded " + danhSachGhe.size() + " seats for SHPC=" + SHPC);
        } catch (SQLException e) {
            System.err.println("GheDAO.layDanhSachGhe: SQLException: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            System.err.println("GheDAO.layDanhSachGhe: Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        return danhSachGhe;
    }

    /**
     * Cập nhật trạng thái ghế. Trả về true nếu update thành công (số hàng bị ảnh hưởng > 0).
     * Thực hiện kiểm tra tồn tại bản ghi trước khi cập nhật để dễ debug khi UI truyền SHPC/maGhe sai.
     */
    public boolean capNhatTrangThaiGhe(String maGhe, String trangThai, String SHPC) {
        if (maGhe == null || maGhe.trim().isEmpty()) {
            System.out.println("GheDAO.capNhatTrangThaiGhe: maGhe null/empty");
            return false;
        }
        if (SHPC == null || SHPC.trim().isEmpty()) {
            System.out.println("GheDAO.capNhatTrangThaiGhe: SHPC null/empty");
            return false;
        }

        String checkSql = "SELECT COUNT(*) AS cnt FROM Ghe WHERE MaGhe = ? AND SHPC = ?";
        String updateSql = "UPDATE Ghe SET TrangThai = ?, ThoiGianDat = SYSDATETIME() WHERE MaGhe = ? AND SHPC = ?";

        try (Connection conn = ConnectDB.getConnection()) {
            if (conn == null) {
                System.err.println("GheDAO.capNhatTrangThaiGhe: ConnectDB.getConnection() returned null");
                return false;
            }

            // Kiểm tra bản ghi tồn tại
            try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                psCheck.setString(1, maGhe);
                psCheck.setString(2, SHPC);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        int cnt = rs.getInt("cnt");
                        if (cnt == 0) {
                            System.out.println("GheDAO.capNhatTrangThaiGhe: Không tìm thấy ghế trong DB: maGhe=" + maGhe + ", SHPC=" + SHPC);
                            return false;
                        }
                    }
                }
            }

            // Thực hiện update
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setString(1, trangThai);
                ps.setString(2, maGhe);
                ps.setString(3, SHPC);
                int updated = ps.executeUpdate();
                System.out.println("GheDAO.capNhatTrangThaiGhe: UPDATE maGhe=" + maGhe + ", SHPC=" + SHPC + ", trangThai=" + trangThai + " -> updated=" + updated);
                return updated > 0;
            } catch (SQLException ex) {
                System.err.println("GheDAO.capNhatTrangThaiGhe: SQLException khi UPDATE: " + ex.getMessage());
                ex.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            System.err.println("GheDAO.capNhatTrangThaiGhe: SQLException kết nối: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getSelectedSeats(String roomCode) {
        List<String> selectedSeats = new ArrayList<>();
        String sql = "SELECT MaGhe FROM Ghe WHERE TrangThai = 'Da_Dat' AND SHPC = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, roomCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    selectedSeats.add(rs.getString("MaGhe"));
                }
            }
        } catch (SQLException e) {
            System.err.println("GheDAO.getSelectedSeats: SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return selectedSeats;
    }

    public String getLatestSelectedSeat(String roomCode) {
        String latestSeat = null;
        String sql = "SELECT TOP 1 MaGhe FROM Ghe WHERE TrangThai = 'Da_Dat' AND SHPC = ? ORDER BY ThoiGianDat DESC";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, roomCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    latestSeat = rs.getString("MaGhe");
                }
            }
        } catch (SQLException e) {
            System.err.println("GheDAO.getLatestSelectedSeat: SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return latestSeat;
    }
 // Thêm vào cuối class Ve_DAO
    public List<String> getGheDaDat(String phongChieu, Date ngayChieu, Time gioBatDau) {
        List<String> gheDaDat = new ArrayList<>();
        String sql = "SELECT ChoNgoi FROM Ve WHERE PhongChieu = ? AND NgayChieu = ? AND ThoiGianBatDau = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, phongChieu);
            ps.setDate(2, ngayChieu);
            ps.setTime(3, gioBatDau);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String choNgoi = rs.getString("ChoNgoi");
                if (choNgoi != null && !choNgoi.trim().isEmpty()) {
                    String[] gheArr = choNgoi.split(",");
                    for (String ghe : gheArr) {
                        ghe = ghe.trim();
                        if (!ghe.isEmpty()) gheDaDat.add(ghe);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gheDaDat;
    }
}