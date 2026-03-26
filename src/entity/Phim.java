package entity;

import java.util.List;

public class Phim {
	private String maPhim;
	private String tenPhim;
	private String daoDien;
	private String theLoai;
	private int thoiLuong;
	private String imageLink;
	private List<LichChieu> danhSachLichChieu;

	public Phim(String maPhim, String tenPhim, String daoDien, String theLoai, int thoiLuong) {
		this.maPhim = maPhim;
		this.tenPhim = tenPhim;
		this.daoDien = daoDien;
		this.theLoai = theLoai;
		this.thoiLuong = thoiLuong;
	}

	// Getters and Setters
	public String getMaPhim() {
		return maPhim;
	}

	public void setMaPhim(String maPhim) {
		this.maPhim = maPhim;
	}

	public String getTenPhim() {
		return tenPhim;
	}

	public void setTenPhim(String tenPhim) {
		this.tenPhim = tenPhim;
	}

	public String getTheLoai() {
		return theLoai;
	}

	public void setTheLoai(String theLoai) {
		this.theLoai = theLoai;
	}

	public int getThoiLuong() {
		return thoiLuong;
	}

	public void setThoiLuong(int thoiLuong) {
		this.thoiLuong = thoiLuong;
	}

	public String getDaoDien() {
		return daoDien;
	}

	public void setDaoDien(String daoDien) {
		this.daoDien = daoDien;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public List<LichChieu> getDanhSachLichChieu() {
		return danhSachLichChieu;
	}

	public void setDanhSachLichChieu(List<LichChieu> danhSachLichChieu) {
		this.danhSachLichChieu = danhSachLichChieu;
	}

	// Add a schedule to the movie
	public void themLichChieu(LichChieu lichChieu) {
		danhSachLichChieu.add(lichChieu);
	}
}
