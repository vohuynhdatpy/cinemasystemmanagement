package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connectDB.ConnectDB;
import entity.KhachHang;

public class KhachHang_DAO {

	// Insert a new customer
	public boolean insertKhachHang(KhachHang kh) {
	    String sql = "INSERT INTO KhachHang(MaKhachHang, SDT, TenDangNhap, TenKhachHang, Email, NgaySinh) VALUES(?, ?, ?, ?, ?, ?)";
	    try (Connection con = ConnectDB.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
	        pstmt.setString(1, kh.getMaKhachHang());
	        pstmt.setString(2, kh.getSDT());
	        pstmt.setString(3, kh.getTenDangNhap());
	        pstmt.setString(4, kh.getTenKhachHang());
	        pstmt.setString(5, kh.getEmail());
	        pstmt.setDate(6, new java.sql.Date(kh.getNgaySinh().getTime()));
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException ex) {
	        Logger.getLogger(KhachHang_DAO.class.getName()).log(Level.SEVERE, "Error inserting customer", ex);
	    }
	    return false;
	}


	public boolean updateKhachHang(KhachHang kh) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean isUpdated = false;

        try {
            con = ConnectDB.getConnection();
            if (con == null) {
                Logger.getLogger(KhachHang_DAO.class.getName()).log(Level.SEVERE, "Connection is null");
                return false;
            }

            // Update query with the correct column order
            String sql = "UPDATE KhachHang SET SDT = ?, tenDangNhap = ?, tenKhachHang = ?, email = ?, ngaySinh = ? WHERE maKhachHang = ?";

            pstmt = con.prepareStatement(sql);

            // Set the parameters in the correct order
            pstmt.setString(1, kh.getSDT());
            pstmt.setString(2, kh.getTenDangNhap());
            pstmt.setString(3, kh.getTenKhachHang());
            pstmt.setString(4, kh.getEmail());
            pstmt.setDate(5, new java.sql.Date(kh.getNgaySinh().getTime()));
            pstmt.setString(6, kh.getMaKhachHang());

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                isUpdated = true;
            }
        } catch (SQLException e) {
            Logger.getLogger(KhachHang_DAO.class.getName()).log(Level.SEVERE, "Error updating customer", e);
            e.printStackTrace();
        } finally {
            ConnectDB.closeAll(con, pstmt, null);
        }

        return isUpdated;
    }
	

	// Delete a customer by phone number
	public boolean deleteKhachHang(String maKhachHang) {
        String sql = "{CALL DeleteKhachHang(?)}";
		try (Connection con = ConnectDB.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, maKhachHang);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException ex) {
			Logger.getLogger(KhachHang_DAO.class.getName()).log(Level.SEVERE, "Lỗi khi xoá khách hàng", ex);
		}
		return false;
	}

	public KhachHang getKhachHangByMa(String maKhachHang) {
		String sql = "{CALL GetKhachHangByMaKhachHang(?)}";
		try (Connection con = ConnectDB.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, maKhachHang);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new KhachHang(rs.getString("MaKhachHang"), rs.getString("SDT"), rs.getString("TenDangNhap"),
							rs.getString("TenKhachHang"), rs.getString("Email"), rs.getDate("NgaySinh"));
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(KhachHang_DAO.class.getName()).log(Level.SEVERE, "Error fetching customer by MaKhachHang",
					ex);
		}
		return null;
	}

	// Fetch all customers
	public List<KhachHang> getAllKhachHang() {
		List<KhachHang> khachHangList = new ArrayList<>();
		String sql = "{CALL GetAllKhachHang}";
		try (Connection con = ConnectDB.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				KhachHang kh = new KhachHang(rs.getString("MaKhachHang"), rs.getString("SDT"),
						rs.getString("TenDangNhap"), rs.getString("TenKhachHang"), rs.getString("Email"),
						rs.getDate("NgaySinh"));
				khachHangList.add(kh);
			}
		} catch (SQLException ex) {
			Logger.getLogger(KhachHang_DAO.class.getName()).log(Level.SEVERE, "Error fetching all customers", ex);
		}
		return khachHangList;
	}

	public List<KhachHang> searchKhachHang(String maKhachHang, String sdt, String tenKhachHang, String tenDangNhap,
			String email, java.util.Date ngaySinh) {
		List<KhachHang> khachHangList = new ArrayList<>();
		String sql = "SELECT * FROM KhachHang WHERE 1=1";

		if (maKhachHang != null && !maKhachHang.isEmpty()) {
			sql += " AND MaKhachHang = ?";
		}
		if (sdt != null && !sdt.isEmpty()) {
			sql += " AND SDT = ?";
		}
		if (tenKhachHang != null && !tenKhachHang.isEmpty()) {
			sql += " AND TenKhachHang LIKE ?";
		}
		if (tenDangNhap != null && !tenDangNhap.isEmpty()) {
			sql += " AND TenDangNhap = ?";
		}
		if (email != null && !email.isEmpty()) {
			sql += " AND Email = ?";
		}
		if (ngaySinh != null) {
			sql += " AND NgaySinh = ?";
		}

		try (Connection con = ConnectDB.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

			int index = 1;

			if (maKhachHang != null && !maKhachHang.isEmpty()) {
				pstmt.setString(index++, maKhachHang);
			}
			if (sdt != null && !sdt.isEmpty()) {
				pstmt.setString(index++, sdt);
			}
			if (tenKhachHang != null && !tenKhachHang.isEmpty()) {
				pstmt.setString(index++, "%" + tenKhachHang + "%");
			}
			if (tenDangNhap != null && !tenDangNhap.isEmpty()) {
				pstmt.setString(index++, tenDangNhap);
			}
			if (email != null && !email.isEmpty()) {
				pstmt.setString(index++, email);
			}
			if (ngaySinh != null) {
				pstmt.setDate(index, new java.sql.Date(ngaySinh.getTime()));
			}

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					KhachHang kh = new KhachHang(rs.getString("MaKhachHang"), rs.getString("SDT"),
							rs.getString("TenDangNhap"), rs.getString("TenKhachHang"), rs.getString("Email"),
							rs.getDate("NgaySinh"));
					khachHangList.add(kh);
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(KhachHang_DAO.class.getName()).log(Level.SEVERE, "Error searching customers", ex);
		}
		return khachHangList;
	}

	// Lấy top 5 khách hàng có doanh thu cao nhất
	public List<Object[]> getTop5KhachHang(LocalDate tuNgay, LocalDate denNgay) {
		List<Object[]> result = new ArrayList<>();
		String sql = "{CALL GetTop5KhachHangByRevenue(?, ?)}";

		try (Connection con = ConnectDB.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setDate(1, java.sql.Date.valueOf(tuNgay));
			pstmt.setDate(2, java.sql.Date.valueOf(denNgay));
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Object[] row = { rs.getString("MaKhachHang"), rs.getString("TenKhachHang"), rs.getString("SDT"),
							rs.getString("Email"), rs.getDate("NgaySinh"), rs.getInt("SoLuongDon"),
							rs.getDouble("TongTien") };
					result.add(row);
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(KhachHang_DAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	// Lấy danh sách khách hàng theo số lượng đơn hàng
	public List<Object[]> getKhachHangNhieuDonNhat(LocalDate tuNgay, LocalDate denNgay) {
		List<Object[]> result = new ArrayList<>();
		String sql = "{CALL GetTopKhachHangByOrderCount(?, ?)}";

		try (Connection con = ConnectDB.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setDate(1, java.sql.Date.valueOf(tuNgay));
			pstmt.setDate(2, java.sql.Date.valueOf(denNgay));
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Object[] row = { rs.getString("MaKhachHang"), rs.getString("TenKhachHang"), rs.getString("SDT"),
							rs.getString("Email"), rs.getDate("NgaySinh"), rs.getInt("SoLuongDon"),
							rs.getDouble("TongTien") };
					result.add(row);
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(KhachHang_DAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

	public List<Object[]> getAllKhachHangThongKe(LocalDate tuNgay, LocalDate denNgay) {
		List<Object[]> result = new ArrayList<>();
		String sql = "{CALL getAllKhachHangThongKe(?, ?)}";

		try (Connection con = ConnectDB.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setDate(1, java.sql.Date.valueOf(tuNgay));
			pstmt.setDate(2, java.sql.Date.valueOf(denNgay));
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Object[] row = { rs.getString("MaKhachHang"), rs.getString("TenKhachHang"), rs.getString("SDT"),
							rs.getString("Email"), rs.getDate("NgaySinh"), rs.getInt("SoLuongDon"),
							rs.getDouble("TongTien") };
					result.add(row);
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(KhachHang_DAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}
	public String getMaKhachHangBySDT(String sdt) {
	    String maKhachHang = null;
	    String sql = "SELECT MaKhachHang FROM KhachHang WHERE SDT = ?";

	    try (Connection con = ConnectDB.getConnection();
	         PreparedStatement pstmt = con.prepareStatement(sql)) {

	        pstmt.setString(1, sdt);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                maKhachHang = rs.getString("MaKhachHang");
	            }
	        }

	    } catch (SQLException ex) {
	        Logger.getLogger(KhachHang_DAO.class.getName()).log(Level.SEVERE, "Lỗi khi lấy mã khách hàng theo SDT", ex);
	    }

	    return maKhachHang;
	}

}
