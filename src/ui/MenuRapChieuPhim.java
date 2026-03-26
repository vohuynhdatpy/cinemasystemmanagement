package ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class MenuRapChieuPhim extends JFrame {
	private JMenuItem menuItemTrangChu;

	private JMenuItem menuItemCapNhatNhanVien;
	private JMenuItem menuItemTimKiemNhanVien;
	private JMenuItem menuItemChucVu;

	private JMenuItem menuItemCapNhatKhachHang;
	private JMenuItem menuItemTimKhachHang;

	private JMenuItem menuItemTimKiemPhim;

	private JMenuItem menuItemDatVe;

	

	private JMenuItem menuItemThongKeDoanhThu;
	private JMenuItem menuItemThongKeKhachHang;
	private JMenuItem menuItemThongKePhim;

	private JMenuItem menuItemQuanLyHoaDon;


	private CardLayout cardLayout;
	private JPanel mainPanel;

	public MenuRapChieuPhim() {
		setTitle("Quản lý Rạp Chiếu Phim");
		setExtendedState(JFrame.MAXIMIZED_BOTH); // Full-screen mode
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center window

		// Create the menu bar and set it for the JFrame
		setJMenuBar(createMenuBar());
		// Tạo panel chính với CardLayout
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		// Thm panel chính
		mainPanel.add(new TrangChu(), "TrangChuPanel");
		mainPanel.add(new TimKiemNhanVien_GD(), "TimKiemNhanVienPanel");
		mainPanel.add(new CapNhatNhanVien_GD(), "CapNhatNhanVienPanel");
		mainPanel.add(new ChucVu_GD(), "ChucVuPanel");
		mainPanel.add(new TimKiemKhachHang_GD(), "TimKiemKhachHangPanel");
		mainPanel.add(new CapNhatKhachHang_GD(), "CapNhatKhachHangPanel");
		mainPanel.add(new TimKiemPhim_GD(), "TimKiemPhimPanel");
		mainPanel.add(new DatVe(), "DatVePanel");
		mainPanel.add(new GD_QuanLyHoaDon(), "QuanLyHoaDonPanel");
		mainPanel.add(new GD_ThongKeDoanhThu(), "ThongKeDoanhThuPanel");	
		mainPanel.add(new GD_ThongKeKhachHang(), "ThongKeKhachHangPanel");
		mainPanel.add(new GD_ThongKePhim(), "ThongKePhimPanel");
		add(mainPanel);

	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		// Create and add main menus
		JMenu menuTrangChu = createMenu("Trang chủ", "src/ui/icon/trangChuICon.png");
		JMenu menuNhanVien = createMenu("Nhân viên", "src/ui/icon/nhanVien.png");
		JMenu menuKhachHang = createMenu("Khách hàng", "src/ui/icon/khachHang.jpg");
		JMenu menuPhim = createMenu("Phim", "src/ui/icon/phim.png");
		JMenu menuVe = createMenu("Vé", "src/ui/icon/ve.jpg");
		// JMenu menuSuatChieu = createMenu("Suất chiếu", "src/ui/icon/suatChieu.png");
		JMenu menuHoaDon = createMenu("Hóa đơn", "src/ui/icon/hoaDon.png");
		JMenu menuThongKe = createMenu("Thống kê", "src/ui/icon/thongKe.png");
		// border cho từng phần tử của JMenu có màu đen color.black

		menuBar.setBackground(new Color(152, 159, 225)); // Màu nền cho menu cha

		menuTrangChu.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		menuNhanVien.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		menuKhachHang.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		menuPhim.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		menuVe.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		// menuSuatChieu.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
		// Color.BLACK));
		menuHoaDon.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		menuThongKe.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

		menuBar.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(10, 41, 108)));
		Dimension menuSize = new Dimension(219, 40); // Chiều rộng và chiều cao

		menuTrangChu.setPreferredSize(menuSize);
		menuNhanVien.setPreferredSize(menuSize);
		menuKhachHang.setPreferredSize(menuSize);
		menuPhim.setPreferredSize(menuSize);
		menuVe.setPreferredSize(menuSize);
		// menuSuatChieu.setPreferredSize(menuSize);
		menuHoaDon.setPreferredSize(menuSize);
		menuThongKe.setPreferredSize(menuSize);
		// Initialize and add menu items for "Trang chủ"
		menuItemTrangChu = createMenuItem("Trang chủ");
		menuItemTrangChu.setPreferredSize(menuSize);
		menuTrangChu.add(menuItemTrangChu);

		// Initialize and add menu items for "Nhân viên"
		menuItemCapNhatNhanVien = createMenuItem("Cập nhật nhân viên");
		menuItemTimKiemNhanVien = createMenuItem("Tìm kiếm nhân viên");
		menuItemChucVu = createMenuItem("Chức vụ");
		menuItemCapNhatNhanVien.setPreferredSize(menuSize);
		menuItemTimKiemNhanVien.setPreferredSize(menuSize);
		menuItemChucVu.setPreferredSize(menuSize);
		menuNhanVien.add(menuItemCapNhatNhanVien);
		menuNhanVien.add(menuItemTimKiemNhanVien);
		menuNhanVien.add(menuItemChucVu);

		// tạo border cho menuItemCapNhatNhanVien và menuItemTimKiemNhanVien
		menuItemCapNhatNhanVien.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		menuItemTimKiemNhanVien.setBorder(BorderFactory.createMatteBorder(2, 2, 4, 2, Color.BLACK));
		menuItemChucVu.setBorder(BorderFactory.createMatteBorder(2, 2, 4, 2, Color.BLACK));
		// Initialize and add menu items for "Khách hàng"
		menuItemCapNhatKhachHang = createMenuItem("Cập nhật khách hàng");
		menuItemTimKhachHang = createMenuItem("Tìm kiếm khách hàng");
		menuItemCapNhatKhachHang.setPreferredSize(menuSize);
		menuItemTimKhachHang.setPreferredSize(menuSize);
		menuKhachHang.add(menuItemCapNhatKhachHang);
		menuKhachHang.add(menuItemTimKhachHang);

		// tạo border cho menuItemCapNhatKhachHang và menuItemTimKhachHang
		menuItemCapNhatKhachHang.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		menuItemTimKhachHang.setBorder(BorderFactory.createMatteBorder(2, 2, 4, 2, Color.BLACK));

		// Initialize and add menu items for "Phim"
		menuItemTimKiemPhim = createMenuItem("Tìm kiếm phim");
		menuItemTimKiemPhim.setPreferredSize(menuSize);
		menuPhim.add(menuItemTimKiemPhim);

		// tạo border cho menuItemTimKiemPhim và menuItemCapNhatPhim
		menuItemTimKiemPhim.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		

		// Initialize and add menu items for "Vé"
		menuItemDatVe = createMenuItem("Đặt vé");
		menuItemDatVe.setPreferredSize(menuSize);
		menuVe.add(menuItemDatVe);

		// tạo border cho menuItemDatVe
		menuItemDatVe.setBorder(BorderFactory.createMatteBorder(2, 2, 4, 2, Color.BLACK));

	

		
	

		// Thêm menu Hóa đơn vào menu bar
		menuItemQuanLyHoaDon = createMenuItem("Quản lý hóa đơn");
		menuItemQuanLyHoaDon.setPreferredSize(menuSize);
		menuHoaDon.add(menuItemQuanLyHoaDon);
		menuBar.add(menuHoaDon);
		//border cho menuItemQuanLyHoaDon
		menuItemQuanLyHoaDon.setBorder(BorderFactory.createMatteBorder(2, 2, 4, 2, Color.BLACK));

		// Initialize and add menu items for "Thống kê"
		menuItemThongKeDoanhThu = createMenuItem("Thống kê Doanh thu");
		menuItemThongKeKhachHang = createMenuItem("Thống kê Khách hàng");
		menuItemThongKePhim = createMenuItem("Thống kê Phim");
		menuItemThongKeDoanhThu.setPreferredSize(menuSize);
		menuItemThongKeKhachHang.setPreferredSize(menuSize);
		menuItemThongKePhim.setPreferredSize(menuSize);
		menuThongKe.add(menuItemThongKeDoanhThu);
		menuThongKe.add(menuItemThongKeKhachHang);
		menuThongKe.add(menuItemThongKePhim);

		// tạo border cho menuItemThongKeDoanhThu và menuItemThongKeKhachHang
		menuItemThongKeDoanhThu.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		menuItemThongKeKhachHang.setBorder(BorderFactory.createMatteBorder(2, 2, 4, 2, Color.BLACK));
		menuItemThongKePhim.setBorder(BorderFactory.createMatteBorder(2, 2, 4, 2, Color.BLACK));

	

		// Add all menus to the menu bar
		menuBar.add(menuTrangChu);
		menuBar.add(menuNhanVien);
		menuBar.add(menuKhachHang);
		menuBar.add(menuPhim);
		menuBar.add(menuVe);
		// menuBar.add(menuSuatChieu);
		menuBar.add(menuHoaDon);
		menuBar.add(menuThongKe);

		// Đặt margin cho các menu thành 0
		for (Component component : menuBar.getComponents()) {
			if (component instanceof JMenu) {
				((JMenu) component).setMargin(new Insets(0, 0, 0, 0));
			}
		}

		// Assign action listeners to handle events
		phanChiaMenu();

		return menuBar;
	}

	private void phanChiaMenu() {
		// menutrangChu không có item
		menuItemTrangChu.addActionListener(e -> {
			cardLayout.show(mainPanel, "TrangChuPanel"); // Hiển thị panel trang chủ
		});
		
		menuItemTimKiemNhanVien.addActionListener(e -> {
			cardLayout.show(mainPanel, "TimKiemNhanVienPanel"); // Hiển thị panel tìm kiếm nhân viên
		});
		
		menuItemCapNhatNhanVien.addActionListener(e -> {
			cardLayout.show(mainPanel, "CapNhatNhanVienPanel"); // Hiển thị panel cập nhật nhân viên
		});
		menuItemChucVu.addActionListener(e -> {
			cardLayout.show(mainPanel, "ChucVuPanel"); // Hiển thị panel chức vụ
		});
		
		menuItemTimKhachHang.addActionListener(e -> {
			cardLayout.show(mainPanel, "TimKiemKhachHangPanel"); // Hiển thị panel tìm kiếm khách hàng
		});	
		
		menuItemCapNhatKhachHang.addActionListener(e -> {
			cardLayout.show(mainPanel, "CapNhatKhachHangPanel"); // Hiển thị panel cập nhật khách hàng
		});
		
		menuItemTimKiemPhim.addActionListener(e -> {
			cardLayout.show(mainPanel, "TimKiemPhimPanel"); // Hiển thị panel tìm kiếm phim
		});
		
		menuItemDatVe.addActionListener(e -> {
			cardLayout.show(mainPanel, "DatVePanel"); // Hiển thị panel tìm kiếm phim
		});
		menuItemQuanLyHoaDon.addActionListener(e -> {
			cardLayout.show(mainPanel, "QuanLyHoaDonPanel"); // Hiển thị panel quản lý hóa đơn
		});
		
		menuItemThongKeDoanhThu.addActionListener(e -> {
			cardLayout.show(mainPanel, "ThongKeDoanhThuPanel"); // Hiển thị panel thống kê doanh thu
		});
		menuItemThongKeKhachHang.addActionListener(e -> {
			cardLayout.show(mainPanel, "ThongKeKhachHangPanel"); // Hiển thị panel thống kê khách hàng
		});
		menuItemThongKePhim.addActionListener(e -> {
			cardLayout.show(mainPanel, "ThongKePhimPanel"); // Hiển thị panel thống kê phim
		});

	}

	private JMenu createMenu(String title, String iconPath) {
		JMenu menu = new JMenu(title); // Sử dụng JMenu
		menu.setFont(new Font("Arial", Font.BOLD, 16));

		// Create and set resized icon for the menu
		ImageIcon originalIcon = new ImageIcon(iconPath);
		Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

		ImageIcon resizedIcon = new ImageIcon(scaledImage);
		menu.setIcon(resizedIcon);

		return menu;
	}

	// Create a JMenuItem with customized font and size
	private JMenuItem createMenuItem(String title) {

		JMenuItem menuItem = new JMenuItem(title); // Sử dụng JMenuItem
		menuItem.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font size for menu items
		menuItem.setPreferredSize(new Dimension(219, 40)); // Đặt chiều rộng và chiều cao
		menuItem.setBackground(new Color(152, 159, 225)); // Màu nền cho menu item con
		return menuItem;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			MenuRapChieuPhim frame = new MenuRapChieuPhim();
			frame.setVisible(true); // Hiển thị cửa sổ
		});
	}
}
