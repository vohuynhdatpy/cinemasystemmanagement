package entity;

public class NhanVien {
	private String maNhanVien;
	private String SDT;
	private String tenNhanVien;
	private String tenDangNhap;
	private int luong;
	private String cmnd;
	private String chucVu;

	// Default constructor
	public NhanVien() {
	}

	// Constructor with all fields
	public NhanVien(String maNhanVien, String SDT, String tenNhanVien, String tenDangNhap, int luong, String cmnd,
			String chucVu) {
		this.maNhanVien = maNhanVien;
		this.SDT = SDT;
		this.tenNhanVien = tenNhanVien;
		this.tenDangNhap = tenDangNhap;
		this.luong = luong;
		this.cmnd = cmnd;
		this.chucVu = chucVu;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getSDT() {
		return SDT;
	}

	public void setSDT(String SDT) {
		this.SDT = SDT;
	}

	public String getTenNhanVien() {
		return tenNhanVien;
	}

	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
	}

	public String getTenDangNhap() {
		return tenDangNhap;
	}

	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public int getLuong() {
		return luong;
	}

	public void setLuong(int luong) {
		this.luong = luong;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
}
