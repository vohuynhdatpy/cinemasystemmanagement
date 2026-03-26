package entity;

import java.sql.Date;
import java.sql.Time;

public class Ve {
    private String maVe;
    private String maPhim;
    private String tenPhim;
    private String daoDien;
    private String theLoai;
    private int thoiLuong;
    private String choNgoi;     // Mã ghế (VD: A1, B2,...)
    private Date ngayChieu;
    private Time thoiGianBatDau;
    private Time thoiGianKetThuc;
    private String phongChieu;  // Mã phòng chiếu
    private double gia;
    private String tenKhachHang;

    public Ve(String mave) {
    	super();
    	this.maVe = mave;
    }

    public Ve(String maVe, String maPhim, String tenPhim, String daoDien, String theLoai, int thoiLuong, 
              String choNgoi, Date ngayChieu, Time thoiGianBatDau, Time thoiGianKetThuc, 
              String phongChieu, double gia, String tenKhachHang) {
        this.maVe = maVe;
        this.maPhim = maPhim;
        this.tenPhim = tenPhim;
        this.daoDien = daoDien;
        this.theLoai = theLoai;
        this.thoiLuong = thoiLuong;
        this.choNgoi = choNgoi;
        this.ngayChieu = ngayChieu;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.phongChieu = phongChieu;
        this.gia = gia;
        this.tenKhachHang = tenKhachHang;
    }
    
    public Ve() {
    	
    }

    // Getter và Setter cho các thuộc tính
    public String getMaVe() {
        return maVe;
    }

    public void setMaVe(String maVe) {
        this.maVe = maVe;
    }

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

    public String getDaoDien() {
        return daoDien;
    }

    public void setDaoDien(String daoDien) {
        this.daoDien = daoDien;
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

    public String getChoNgoi() {
        return choNgoi;
    }

    public void setChoNgoi(String choNgoi) {
        this.choNgoi = choNgoi;
    }

    public Date getNgayChieu() {
        return ngayChieu;
    }

    public void setNgayChieu(Date ngayChieu) {
        this.ngayChieu = ngayChieu;
    }

    public Time getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(Time thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public Time getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(Time thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public String getPhongChieu() {
        return phongChieu;
    }

    public void setPhongChieu(String phongChieu) {
        this.phongChieu = phongChieu;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }
}
