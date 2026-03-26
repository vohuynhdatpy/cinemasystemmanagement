package entity;

public class GheLichChieuSuatChieu {
    private Ghe ghe; // Ghế
    private LichChieu lichChieu; // Lịch chiếu
    private SuatChieu suatChieu; // Suất chiếu
    private TrangThaiGhe trangThaiGhe; // Trạng thái ghế cho lịch chiếu và suất chiếu này

    public GheLichChieuSuatChieu(Ghe ghe, LichChieu lichChieu, SuatChieu suatChieu, TrangThaiGhe trangThaiGhe) {
        this.ghe = ghe;
        this.lichChieu = lichChieu;
        this.suatChieu = suatChieu;
        this.trangThaiGhe = trangThaiGhe;
    }

    public Ghe getGhe() {
        return ghe;
    }

    public void setGhe(Ghe ghe) {
        this.ghe = ghe;
    }

    public LichChieu getLichChieu() {
        return lichChieu;
    }

    public void setLichChieu(LichChieu lichChieu) {
        this.lichChieu = lichChieu;
    }

    public SuatChieu getSuatChieu() {
        return suatChieu;
    }

    public void setSuatChieu(SuatChieu suatChieu) {
        this.suatChieu = suatChieu;
    }

    public TrangThaiGhe getTrangThaiGhe() {
        return trangThaiGhe;
    }

    public void setTrangThaiGhe(TrangThaiGhe trangThaiGhe) {
        this.trangThaiGhe = trangThaiGhe;
    }

    public boolean isAvailable() {
        return this.trangThaiGhe == TrangThaiGhe.TRONG;
    }

    public void datGhe() {
        if (isAvailable()) {
            this.trangThaiGhe = TrangThaiGhe.DA_DAT;
        } else {
            System.out.println("Ghế này đã được đặt cho lịch và suất chiếu này.");
        }
    }
}
