package entity;

import java.util.Date;

public class KhachHang {
    private String maKhachHang;
    private String SDT;
    private String tenDangNhap;
    private String tenKhachHang;
    private String email;
    private Date ngaySinh;

    // Default constructor
    public KhachHang() {

    }

    // Constructor with all fields
    public KhachHang(String maKhachHang, String SDT, String tenDangNhap, String tenKhachHang, String email, Date ngaySinh) {
        this.maKhachHang = maKhachHang;
        this.SDT = SDT;
        this.tenDangNhap = tenDangNhap;
        this.tenKhachHang = tenKhachHang;
        this.email = email;
        this.ngaySinh = ngaySinh;
    }
    
    public KhachHang(String ma) {
    	super();
    	this.maKhachHang=ma;
    }
    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}
