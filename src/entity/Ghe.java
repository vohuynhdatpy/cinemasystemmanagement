package entity;

public class Ghe {
    private String maGhe;
    private TrangThaiGhe trangThaiGhe;
    private PhongChieuPhim phongChieuPhim;  // Tham chiếu đến phòng chiếu phim

    public Ghe(String maGhe, TrangThaiGhe trangThaiGhe, PhongChieuPhim phongChieuPhim) {
        this.maGhe = maGhe;
        this.trangThaiGhe = trangThaiGhe;
        this.phongChieuPhim = phongChieuPhim;
    }

    public String getMaGhe() {
        return maGhe;
    }

    public void setMaGhe(String maGhe) {
        this.maGhe = maGhe;
    }

    public TrangThaiGhe getTrangThaiGhe() {
        return trangThaiGhe;
    }

    public void setTrangThaiGhe(TrangThaiGhe trangThaiGhe) {
        this.trangThaiGhe = trangThaiGhe;
    }

    public PhongChieuPhim getPhongChieuPhim() {
        return phongChieuPhim;
    }

    public void setPhongChieuPhim(PhongChieuPhim phongChieuPhim) {
        this.phongChieuPhim = phongChieuPhim;
    }
}
