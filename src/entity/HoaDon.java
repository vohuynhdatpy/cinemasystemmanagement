package entity;

import java.time.LocalDate;

public class HoaDon {
    private String maHoaDon;
    private LocalDate ngayLap;
    private double tongTien;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private Ve ve;
    
    // Constructor đầy đủ tham số
    public HoaDon(String maHoaDon, LocalDate ngayLap, double tongTien, KhachHang khachHang, NhanVien nhanVien, Ve ve) {
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.ve = ve;
    }
    
    // Constructor mặc định
    public HoaDon() {
    }
    
    // Các getter và setter
    public String getMaHoaDon() {
        return maHoaDon;
    }
    
    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }
    
    public LocalDate getNgayLap() {
        return ngayLap;
    }
    
    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }
    
    public double getTongTien() {
        return tongTien;
    }
    
    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
    
    public KhachHang getKhachHang() {
        return khachHang;
    }
    
    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }
    
    public NhanVien getNhanVien() {
        return nhanVien;
    }
    
    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }
    
    public Ve getVe() {
        return ve;
    }
    
    public void setVe(Ve ve) {
        this.ve = ve;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maHoaDon == null) ? 0 : maHoaDon.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HoaDon other = (HoaDon) obj;
        if (maHoaDon == null) {
            if (other.maHoaDon != null)
                return false;
        } else if (!maHoaDon.equals(other.maHoaDon))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "HoaDon [maHoaDon=" + maHoaDon + ", ngayLap=" + ngayLap + ", tongTien=" + tongTien + ", khachHang="
                + khachHang + ", nhanVien=" + nhanVien + ", ve=" + ve + "]";
    }
}
