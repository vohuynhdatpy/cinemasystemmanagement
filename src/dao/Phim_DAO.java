package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connectDB.ConnectDB;
import entity.Phim;

public class Phim_DAO {

	public List<Phim> getAllPhim() {
		List<Phim> dsPhim = new ArrayList<>();
		String sql = "SELECT MaPhim, TenPhim, DaoDien, TheLoai, ThoiLuong FROM Phim";
		try (Connection con = ConnectDB.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				String maPhim = rs.getString("MaPhim");
				String tenPhim = rs.getString("TenPhim");
				String daoDien = rs.getString("DaoDien");
				String theLoai = rs.getString("TheLoai");
				int thoiLuong = rs.getInt("ThoiLuong");
				Phim phim = new Phim(maPhim, tenPhim, daoDien, theLoai, thoiLuong);
				dsPhim.add(phim);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsPhim;
	}

	public List<Date> getShowtimeDatesByMovieId(String movieId) {
		List<Date> showtimeDates = new ArrayList<>();

		// SQL để lấy các ngày chiếu của một phim cụ thể từ bảng Phim_LichChieu và
		// LichChieu
		String sql = "SELECT DISTINCT lc.NgayChieu " + "FROM Phim_LichChieu plc "
				+ "JOIN LichChieu lc ON plc.MaLichChieu = lc.MaLichChieu " + "WHERE plc.MaPhim = ?";

		try (Connection con = ConnectDB.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, movieId); // Đặt giá trị cho tham số movieId
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Date ngayChieu = rs.getDate("NgayChieu"); // Lấy ngày chiếu từ cột "NgayChieu"
					showtimeDates.add(ngayChieu); // Thêm vào danh sách
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Xử lý ngoại lệ, ví dụ ghi log hoặc thông báo lỗi
		}

		return showtimeDates;
	}

	public List<String> getShowtimesByDateAndMovie(String date, String movieId) {
		List<String> showtimes = new ArrayList<>();
		String sql = """
				SELECT sc.ThoiGianBatDau, sc.ThoiGianKetThuc
				FROM Phim_LichChieu plc
				INNER JOIN LichChieu lc ON plc.MaLichChieu = lc.MaLichChieu
				INNER JOIN LichChieu_SuatChieu lcs ON lc.MaLichChieu = lcs.MaLichChieu
				INNER JOIN SuatChieu sc ON lcs.MaSuatChieu = sc.MaSuatChieu
				WHERE lc.NgayChieu = ? AND plc.MaPhim = ?
				ORDER BY sc.ThoiGianBatDau
				""";

		try (Connection con = ConnectDB.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, date); // Đặt tham số cho ngày chiếu
			pstmt.setString(2, movieId); // Đặt tham số cho mã phim

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					String startTime = rs.getString("ThoiGianBatDau");
					String endTime = rs.getString("ThoiGianKetThuc");
					showtimes.add(startTime + " - " + endTime); // Thêm thông tin suất chiếu vào danh sách
				}
			}

		} catch (SQLException e) {
			e.printStackTrace(); // Xử lý ngoại lệ
		}

		return showtimes;
	}

	public String getPosterByMaPhim(String maPhim) {
		String posterPath = null;
		String sql = "SELECT imageLink FROM Phim WHERE MaPhim = ?";

		try (Connection con = ConnectDB.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, maPhim);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					posterPath = rs.getString("imageLink");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return posterPath;
	}

	public List<Date> getLichChieuDatesByMaPhim(String maPhim) {
		List<Date> lichChieuDates = new ArrayList<>();
		String sql = "SELECT DISTINCT NgayChieu FROM LichChieu lc "
				+ "JOIN Phim_LichChieu plc ON lc.MaLichChieu = plc.MaLichChieu " + "WHERE plc.MaPhim = ?";

		try (Connection con = ConnectDB.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, maPhim);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					lichChieuDates.add(rs.getDate("NgayChieu"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lichChieuDates;
	}

	public List<String> getMoviesByDate(String date) {
		List<String> movieIds = new ArrayList<>();
		String sql = """
				SELECT DISTINCT plc.MaPhim
				FROM Phim_LichChieu plc
				JOIN LichChieu lc ON plc.MaLichChieu = lc.MaLichChieu
				WHERE lc.NgayChieu = ?
				""";

		try (Connection con = ConnectDB.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, date); // Đặt tham số cho ngày chiếu

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					String movieId = rs.getString("MaPhim");
					movieIds.add(movieId); // Thêm mã phim vào danh sách
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Xử lý ngoại lệ
		}

		return movieIds;
	}

	public List<Object[]> getAllPhimThongKe(LocalDate tuNgay, LocalDate denNgay) {
		List<Object[]> result = new ArrayList<>();
		String sql = """
				    SELECT
				        p.MaPhim,
				        p.TenPhim,
				        p.TheLoai,
				        p.ThoiLuong,
				        COUNT(DISTINCT hd.MaHoaDon) as SoLuongDon,
				        ISNULL(SUM(hd.Tong), 0) as TongTien
				    FROM Phim p
				    LEFT JOIN LichChieuM lc ON p.MaPhim = lc.MaPhim
				    LEFT JOIN VeM v ON lc.MaLichChieu = v.MaLichChieu
				    LEFT JOIN HoaDon hd ON v.MaVe = hd.MaVe
				        AND hd.NgayLap BETWEEN ? AND ?
				    GROUP BY
				        p.MaPhim,
				        p.TenPhim,
				        p.TheLoai,
				        p.ThoiLuong
				    ORDER BY p.MaPhim ASC
				""";

		return executePhimQuery(sql, tuNgay, denNgay);
	}

	public List<Object[]> getTop5PhimXemNhieuNhat(LocalDate tuNgay, LocalDate denNgay) {
		String sql = """
				    SELECT TOP 5
				        p.MaPhim,
				        p.TenPhim,
				        p.TheLoai,
				        p.ThoiLuong,
				        COUNT(DISTINCT hd.MaHoaDon) as SoLuongDon,
				        ISNULL(SUM(hd.Tong), 0) as TongTien
				    FROM Phim p
				    LEFT JOIN LichChieuM lc ON p.MaPhim = lc.MaPhim
				    LEFT JOIN VeM v ON lc.MaLichChieu = v.MaLichChieu
				    LEFT JOIN HoaDon hd ON v.MaVe = hd.MaVe
				        AND hd.NgayLap BETWEEN ? AND ?
				    GROUP BY
				        p.MaPhim,
				        p.TenPhim,
				        p.TheLoai,
				        p.ThoiLuong
				    ORDER BY COUNT(DISTINCT hd.MaHoaDon) DESC
				""";

		return executePhimQuery(sql, tuNgay, denNgay);
	}

	public List<Object[]> getPhimDoanhThuCaoNhat(LocalDate tuNgay, LocalDate denNgay) {
		String sql = """
				    WITH DoanhThuPhim AS (
				        SELECT
				            p.MaPhim,
				            p.TenPhim,
				            p.TheLoai,
				            p.ThoiLuong,
				            COUNT(DISTINCT hd.MaHoaDon) as SoLuongDon,
				            ISNULL(SUM(hd.Tong), 0) as TongTien,
				            DENSE_RANK() OVER (ORDER BY SUM(hd.Tong) DESC) as XepHang
				        FROM Phim p
				        LEFT JOIN LichChieuM lc ON p.MaPhim = lc.MaPhim
				        LEFT JOIN VeM v ON lc.MaLichChieu = v.MaLichChieu
				        LEFT JOIN HoaDon hd ON v.MaVe = hd.MaVe
				            AND hd.NgayLap BETWEEN ? AND ?
				        GROUP BY
				            p.MaPhim,
				            p.TenPhim,
				            p.TheLoai,
				            p.ThoiLuong
				    )
				    SELECT
				        MaPhim,
				        TenPhim,
				        TheLoai,
				        ThoiLuong,
				        SoLuongDon,
				        TongTien
				    FROM DoanhThuPhim
				    WHERE XepHang = 1
				    ORDER BY TongTien DESC, TenPhim ASC
				""";

		return executePhimQuery(sql, tuNgay, denNgay);
	}

	private List<Object[]> executePhimQuery(String sql, LocalDate tuNgay, LocalDate denNgay) {
		List<Object[]> result = new ArrayList<>();
		try (Connection con = ConnectDB.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setDate(1, java.sql.Date.valueOf(tuNgay));
			stmt.setDate(2, java.sql.Date.valueOf(denNgay));

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Object[] row = { rs.getString("MaPhim"), rs.getString("TenPhim"), rs.getString("TheLoai"),
							rs.getInt("ThoiLuong") + " phút", rs.getInt("SoLuongDon"), rs.getDouble("TongTien") };
					result.add(row);
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(Phim_DAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}

}
