package entity;

public enum TrangThaiGhe {
    TRONG("Trống"),
    DA_DAT("Đã đặt"),
    DANG_CHO("Đang chờ");

    private final String description;

    TrangThaiGhe(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
