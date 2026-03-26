package entity;

import java.time.LocalTime;

public class SuatChieu {
    private String maSuatChieu;
    private LocalTime thoiGianChieu;
    private Phim phim;                 // Linked to Phim
    private PhongChieuPhim phongChieu;  // Linked to PhongChieuPhim (Movie Hall)

    public SuatChieu(String maSuatChieu, LocalTime thoiGianChieu, Phim phim, PhongChieuPhim phongChieu) {
        this.maSuatChieu = maSuatChieu;
        this.thoiGianChieu = thoiGianChieu;
        this.phim = phim;
        this.phongChieu = phongChieu;
    }

    // Getters and Setters
    public String getMaSuatChieu() { return maSuatChieu; }
    public void setMaSuatChieu(String maSuatChieu) { this.maSuatChieu = maSuatChieu; }

    public LocalTime getThoiGianChieu() { return thoiGianChieu; }
    public void setThoiGianChieu(LocalTime thoiGianChieu) { this.thoiGianChieu = thoiGianChieu; }

    public Phim getPhim() { return phim; }
    public void setPhim(Phim phim) { this.phim = phim; }

    public PhongChieuPhim getPhongChieu() { return phongChieu; }
    public void setPhongChieu(PhongChieuPhim phongChieu) { this.phongChieu = phongChieu; }

    public void hienThiThongTin() {
        System.out.println("Mã Suất Chiếu: " + maSuatChieu);
        System.out.println("Thời Gian Chiếu: " + thoiGianChieu);
        System.out.println("Tên Phim: " + (phim != null ? phim.getTenPhim() : "Chưa xác định"));
        System.out.println("Phòng Chiếu: " + (phongChieu != null ? phongChieu.getSHPC() : "Chưa xác định"));
    }
}
