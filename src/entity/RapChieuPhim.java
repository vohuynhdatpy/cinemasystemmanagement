package entity;

public class RapChieuPhim {
    private String maRapChieu;
    private String tenRapChieu;
    private String diaDiem;

    public RapChieuPhim(String maRapChieu, String tenRapChieu, String diaDiem) {
        this.maRapChieu = maRapChieu;
        this.tenRapChieu = tenRapChieu;
        this.diaDiem = diaDiem;
    }

    public String getMaRapChieu() {
        return maRapChieu;
    }

    public void setMaRapChieu(String maRapChieu) {
        this.maRapChieu = maRapChieu;
    }

    public String getTenRapChieu() {
        return tenRapChieu;
    }

    public void setTenRapChieu(String tenRapChieu) {
        this.tenRapChieu = tenRapChieu;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }
}
