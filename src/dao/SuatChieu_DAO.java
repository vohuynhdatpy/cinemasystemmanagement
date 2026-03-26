package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;

public class SuatChieu_DAO {

	// Phương thức để lấy tất cả suất chiếu từ cơ sở dữ liệu
	public List<String> getAllSuatChieu() {
		List<String> suatChieuList = new ArrayList<>();
		String sql = "SELECT MaSuatChieu, ThoiGianBatDau, ThoiGianKetThuc FROM SuatChieu";
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

		try (Connection con = ConnectDB.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				String maSuatChieu = rs.getString("MaSuatChieu");
				Time thoiGianBatDau = rs.getTime("ThoiGianBatDau");
				Time thoiGianKetThuc = rs.getTime("ThoiGianKetThuc");

				// Định dạng lại thời gian thành chuỗi hh:mm:ss
				String formattedThoiGianBatDau = timeFormat.format(thoiGianBatDau);
				String formattedThoiGianKetThuc = timeFormat.format(thoiGianKetThuc);

				suatChieuList.add(maSuatChieu + " - " + formattedThoiGianBatDau + " - " + formattedThoiGianKetThuc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return suatChieuList;
	}

}
