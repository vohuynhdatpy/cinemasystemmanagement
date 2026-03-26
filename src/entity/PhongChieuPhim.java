package entity;

import java.util.List;

public class PhongChieuPhim {
    private String SHPC;
    private int soGhe;
    private List<Ghe> danhSachGhe;  // Danh sách ghế trong phòng chiếu

    public PhongChieuPhim(String SHPC, int soGhe) {
        this.SHPC = SHPC;
        this.soGhe = soGhe;
    }

    public PhongChieuPhim(String shpc) {
        this.SHPC = shpc;
    }

    public String getSHPC() {
        return SHPC;
    }

    public void setSHPC(String SHPC) {
        this.SHPC = SHPC;
    }

    public int getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(int soGhe) {
        this.soGhe = soGhe;
    }

    public List<Ghe> getDanhSachGhe() {
        return danhSachGhe;
    }

    public void setDanhSachGhe(List<Ghe> danhSachGhe) {
        this.danhSachGhe = danhSachGhe;
    }
}
