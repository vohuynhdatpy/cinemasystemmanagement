package entity;

import java.time.LocalDate;
import java.util.List;

public class LichChieu {
    private String maLichChieu;
    private Phim phim;
    private LocalDate ngayChieu;
    private List<SuatChieu> danhSachSuatChieu;
    private int trangThai; // 0: chưa chiếu, 1: đang chiếu, 2: kết thúc

    public LichChieu(String maLichChieu, Phim phim, LocalDate ngayChieu, List<SuatChieu> danhSachSuatChieu, int trangThai) {
        this.maLichChieu = maLichChieu;
        this.phim = phim;
        this.ngayChieu = ngayChieu;
        this.danhSachSuatChieu = danhSachSuatChieu;
        this.trangThai = trangThai;
    }

    // Getters and Setters
    public String getMaLichChieu() { return maLichChieu; }
    public void setMaLichChieu(String maLichChieu) { this.maLichChieu = maLichChieu; }

    public Phim getPhim() { return phim; }
    public void setPhim(Phim phim) { this.phim = phim; }

    public LocalDate getNgayChieu() { return ngayChieu; }
    public void setNgayChieu(LocalDate ngayChieu) { this.ngayChieu = ngayChieu; }

    public List<SuatChieu> getDanhSachSuatChieu() { return danhSachSuatChieu; }
    public void setDanhSachSuatChieu(List<SuatChieu> danhSachSuatChieu) { this.danhSachSuatChieu = danhSachSuatChieu; }

    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }

    public void themSuatChieu(SuatChieu suatChieu) {
        danhSachSuatChieu.add(suatChieu);
    }

    public void hienThiLichChieu() {
        System.out.println("Mã Lịch Chiếu: " + maLichChieu);
        System.out.println("Phim: " + (phim != null ? phim.getTenPhim() : "Chưa xác định"));
        System.out.println("Ngày Chiếu: " + ngayChieu);
        System.out.println("Trạng Thái: " + (trangThai == 0 ? "Chưa Chiếu" : trangThai == 1 ? "Đang Chiếu" : "Kết Thúc"));
        System.out.println("Danh Sách Suất Chiếu:");
        for (SuatChieu suat : danhSachSuatChieu) {
            suat.hienThiThongTin();
        }
    }
}
