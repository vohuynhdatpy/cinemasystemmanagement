package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connectDB.ConnectDB;

public class LichChieu_DAO {
	public String getMaLichChieuByMaPhim(String maPhim) {
	    String maLichChieu = null;
	    String sql = "SELECT TOP 1 MaLichChieu FROM LichChieu WHERE MaPhim = ? ORDER BY NgayChieu DESC, GioChieu DESC";

	    try (Connection con = ConnectDB.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, maPhim);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                maLichChieu = rs.getString("MaLichChieu");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return maLichChieu;
	}

}
