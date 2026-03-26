package ui;

import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.KhachHang;
import entity.NhanVien;
import entity.TaiKhoan;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TaiKhoan_GD extends JFrame {

    private JMenuItem menuItemCapNhatNhanVien;
    private JMenuItem menuItemTimKiem;
    private JMenuItem menuItemTaiKhoan;

    private JMenuItem menuItemCapNhatKhachHang;
    private JMenuItem menuItemTimKhachHang;

    private JMenuItem menuItemThemPhim;
    private JMenuItem menuItemXoaPhim;
    private JMenuItem menuItemCapNhatPhim;

    private JMenuItem menuItemThemVe;
    private JMenuItem menuItemXoaVe;
    private JMenuItem menuItemCapNhatVe;

    private JMenuItem menuItemTaoHoaDon;
    private JMenuItem menuItemXemHoaDon;

    private JMenuItem menuItemThongKeDoanhThu;
    private JMenuItem menuItemThongKeKhachHang;

    private DefaultTableModel model;
    private JTable table;

    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    JComboBox<String> cboLoaiTaiKhoan;

    private JButton btnThem, btnXoa, btnSua, btnXoaTrang, btnLamMoi;

    public TaiKhoan_GD() {
        setTitle("Quản lý Rạp Chiếu Phim");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full-screen mode
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setJMenuBar(createMenuBar());

        JPanel panelTren = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(220, 220, 220));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelTren.setLayout(new BorderLayout());
        panelTren.setPreferredSize(new Dimension(0, 100));

        JLabel titleLabel = new JLabel("Quản lý tài khoản", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.BLUE);

        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Vẽ nền gradient từ xanh nhạt sang trắng
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                Color color1 = new Color(173, 216, 230); // Xanh nhạt
                Color color2 = Color.WHITE;
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        add(titlePanel, BorderLayout.NORTH);

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));

        JLabel lblSDT, lblTenNhanVien, lblChucVu;

        Box b = Box.createVerticalBox();
        Border innerPadding = BorderFactory.createEmptyBorder(10, 30, 10, 30);
        Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(lineBorder, "Quản lý chức vụ");

        titledBorder.setTitleFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        b.setBorder(BorderFactory.createCompoundBorder(titledBorder, innerPadding));
        Box b1, b2, b3, b4;

        b.add(b1 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b1.add(lblSDT = new JLabel("Tên đăng nhập:"));
        lblSDT.setPreferredSize(new Dimension(120, 30)); // Đặt kích thước cố định cho JLabel
        b1.add(Box.createHorizontalStrut(10));
        b1.add(txtTenDangNhap = new JTextField(20));

        b.add(b2 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b2.add(lblTenNhanVien = new JLabel("Mật khẩu:"));
        lblTenNhanVien.setPreferredSize(new Dimension(120, 30));
        b2.add(Box.createHorizontalStrut(10));
        b2.add(txtMatKhau = new JPasswordField(20));

        b.add(b3 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b3.add(lblChucVu = new JLabel("Loại tài khoản:"));
        lblChucVu.setPreferredSize(new Dimension(120, 30));
        b3.add(Box.createHorizontalStrut(10));
        String[] comboLoaiTK = { "Quản lý", "Nhân viên bán vé", "Nhân viên phục vụ", "Nhân viên bảo vệ", "Khách hàng"};
        cboLoaiTaiKhoan = new JComboBox<>(comboLoaiTK);
        b3.add(cboLoaiTaiKhoan);

        b.add(b4 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b4.add(Box.createHorizontalGlue()); // Căn giữa các nút
        b4.add(btnThem = new JButton("Thêm"));
        b4.add(Box.createHorizontalStrut(10));
        b4.add(btnXoa = new JButton("Xóa"));
        b4.add(Box.createHorizontalStrut(10));
        b4.add(btnSua = new JButton("Sửa"));
        b4.add(Box.createHorizontalStrut(10));
        b4.add(btnXoaTrang = new JButton("Xóa trắng"));
        b4.add(Box.createHorizontalStrut(10));
        b4.add(btnLamMoi = new JButton("Làm mới"));
        b4.add(Box.createHorizontalGlue()); // Căn giữa các nút

        Font labelFont = new Font("Comic Sans MS", Font.PLAIN, 14);
        lblSDT.setFont(labelFont);
        lblTenNhanVien.setFont(labelFont);
        lblChucVu.setFont(labelFont);

        b.setPreferredSize(new Dimension(800, 200));
        b.setMaximumSize(new Dimension(800, 200));
        b.setMinimumSize(new Dimension(800, 200));

        Dimension buttonSize = new Dimension(100, 45); // Adjust size as needed

        btnThem.setPreferredSize(buttonSize);
        btnXoa.setPreferredSize(buttonSize);
        btnSua.setPreferredSize(buttonSize);
        btnXoaTrang.setPreferredSize(buttonSize);
        btnLamMoi.setPreferredSize(buttonSize);

        styleButton(btnThem, new Color(40, 167, 69), Color.WHITE);   // Xanh lá
        styleButton(btnXoa, new Color(220, 53, 69), Color.WHITE);    // Đỏ
        styleButton(btnSua, new Color(255, 165, 0), Color.WHITE);    // Cam
        styleButton(btnXoaTrang, new Color(108, 117, 125), Color.WHITE); // Xám
        styleButton(btnLamMoi, new Color(0, 123, 255), Color.WHITE); // Xanh dương

        panelCenter.add(b);

        add(panelCenter, BorderLayout.CENTER);

        btnThem.addActionListener(e -> handleThem());
        btnXoa.addActionListener(e -> handleXoa());
        btnSua.addActionListener(e -> handleSua());
        btnXoaTrang.addActionListener(e -> handleXoaTrang());
        btnLamMoi.addActionListener(e -> handleLamMoi());

        String[] columnNames = {"Tên đăng nhập", "Mật khẩu", "Loại tài khoản"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));

        table.setRowHeight(30);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        header.setBackground(new Color(200, 200, 200));
        header.setForeground(Color.BLACK);

        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1200, 400));

        panelCenter.add(scrollPane);

        add(panelCenter, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = table.getSelectedRow();
                fillForm(row);
            }
        });

        loadDataTaiKhoan();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuNhanVien = createMenu("Nhân viên", "src/ui/icon/nhanVien.png");
        JMenu menuKhachHang = createMenu("Khách hàng", "src/ui/icon/khachHang.jpg");
        JMenu menuPhim = createMenu("Phim", "src/ui/icon/phim.png");
        JMenu menuVe = createMenu("Vé", "src/ui/icon/ve.jpg");
        JMenu menuHoaDon = createMenu("Hoá đơn", "src/ui/icon/khachHang.png");
        JMenu menuThongKe = createMenu("Thống kê", "src/ui/icon/khachHang.png");

        menuItemCapNhatNhanVien = createMenuItem("Cập nhật nhân viên");
        menuItemTimKiem = createMenuItem("Tìm kiếm");
        menuItemTaiKhoan = createMenuItem("Tài khoản");

        menuNhanVien.add(menuItemCapNhatNhanVien);
        menuNhanVien.add(menuItemTimKiem);
        menuNhanVien.add(menuItemTaiKhoan);

        // Initialize and add menu items for "Khách hàng"
        menuItemCapNhatKhachHang = createMenuItem("Cập nhật hhách hàng");
        menuItemTimKhachHang = createMenuItem("Tìm khách hàng");

        menuKhachHang.add(menuItemCapNhatKhachHang);
        menuKhachHang.add(menuItemTimKhachHang);

        // Initialize and add menu items for "Phim"
        menuItemThemPhim = createMenuItem("Thêm Phim");
        menuItemXoaPhim = createMenuItem("Xoá Phim");
        menuItemCapNhatPhim = createMenuItem("Cập nhật Phim");

        menuPhim.add(menuItemThemPhim);
        menuPhim.add(menuItemXoaPhim);
        menuPhim.add(menuItemCapNhatPhim);

        // Initialize and add menu items for "Vé"
        menuItemThemVe = createMenuItem("Thêm Vé");
        menuItemXoaVe = createMenuItem("Xoá Vé");
        menuItemCapNhatVe = createMenuItem("Cập nhật Vé");

        menuVe.add(menuItemThemVe);
        menuVe.add(menuItemXoaVe);
        menuVe.add(menuItemCapNhatVe);

        // Initialize and add menu items for "Hoá đơn"
        menuItemTaoHoaDon = createMenuItem("Tạo Hoá đơn");
        menuItemXemHoaDon = createMenuItem("Xem Hoá đơn");

        menuHoaDon.add(menuItemTaoHoaDon);
        menuHoaDon.add(menuItemXemHoaDon);

        // Initialize and add menu items for "Thống kê"
        menuItemThongKeDoanhThu = createMenuItem("Thống kê Doanh thu");
        menuItemThongKeKhachHang = createMenuItem("Thống kê Khách hàng");

        menuThongKe.add(menuItemThongKeDoanhThu);
        menuThongKe.add(menuItemThongKeKhachHang);

        // Add all menus to the menu bar
        menuBar.add(menuNhanVien);
        menuBar.add(menuKhachHang);
        menuBar.add(menuPhim);
        menuBar.add(menuVe);
        menuBar.add(menuHoaDon);
        menuBar.add(menuThongKe);

        // Assign action listeners to handle events
        assignMenuActionListeners();

        return menuBar;
    }

    private void assignMenuActionListeners() {
        menuItemCapNhatNhanVien.addActionListener(e -> handleCapNhatNhanVien());
        menuItemTimKiem.addActionListener(e -> handleTimKiem());
        menuItemTaiKhoan.addActionListener(e -> handleTaiKhoan());

        menuItemCapNhatKhachHang.addActionListener(e -> handleCapNhatKhachHang());
        menuItemTimKhachHang.addActionListener(e -> handleTimKhachHang());

        menuItemThemPhim.addActionListener(e -> handleThemPhim());
        menuItemXoaPhim.addActionListener(e -> handleXoaPhim());
        menuItemCapNhatPhim.addActionListener(e -> handleCapNhatPhim());

        menuItemThemVe.addActionListener(e -> handleThemVe());
        menuItemXoaVe.addActionListener(e -> handleXoaVe());
        menuItemCapNhatVe.addActionListener(e -> handleCapNhatVe());

        menuItemTaoHoaDon.addActionListener(e -> handleTaoHoaDon());
        menuItemXemHoaDon.addActionListener(e -> handleXemHoaDon());

        menuItemThongKeDoanhThu.addActionListener(e -> handleThongKeDoanhThu());
        menuItemThongKeKhachHang.addActionListener(e -> handleThongKeKhachHang());
    }

    private JMenu createMenu(String title, String iconPath) {
        JMenu menu = new JMenu(title);
        menu.setFont(new Font("Arial", Font.BOLD, 16));
        menu.setPreferredSize(new Dimension(200, 40));

        // Create and set resized icon for the menu
        ImageIcon originalIcon = new ImageIcon(iconPath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Set desired width and height
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        menu.setIcon(resizedIcon);

        return menu;
    }


    // Create a JMenuItem with customized font and size
    private JMenuItem createMenuItem(String title) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font size for menu items
        menuItem.setPreferredSize(new Dimension(200, 30)); // Set width and height
        return menuItem;
    }

    private void handleCapNhatNhanVien() {

    }

    private void handleTimKiem() {

    }

    private void handleTaiKhoan() {

    }

    private void handleCapNhatKhachHang() {

    }

    private void handleTimKhachHang() {

    }


    private void handleThemPhim() {
        JOptionPane.showMessageDialog(this, "Thêm Phim");
    }

    private void handleXoaPhim() {
        JOptionPane.showMessageDialog(this, "Xoá Phim");
    }

    private void handleCapNhatPhim() {
        JOptionPane.showMessageDialog(this, "Cập nhật Phim");
    }

    private void handleThemVe() {
        JOptionPane.showMessageDialog(this, "Thêm Vé");
    }

    private void handleXoaVe() {
        JOptionPane.showMessageDialog(this, "Xoá Vé");
    }

    private void handleCapNhatVe() {
        JOptionPane.showMessageDialog(this, "Cập nhật Vé");
    }

    private void handleTaoHoaDon() {
        JOptionPane.showMessageDialog(this, "Tạo Hoá đơn");
    }

    private void handleXemHoaDon() {
        JOptionPane.showMessageDialog(this, "Xem Hoá đơn");
    }

    private void handleThongKeDoanhThu() {
        JOptionPane.showMessageDialog(this, "Thống kê Doanh thu");
    }

    private void handleThongKeKhachHang() {
        JOptionPane.showMessageDialog(this, "Thống kê Khách hàng");
    }

    private void fillForm(int row) {
        if (row != -1) {
            String tenDangNhap = (String) table.getValueAt(row, 0);
            String matKhau = (String) table.getValueAt(row, 1);
            String loaiTaiKhoan = (String) table.getValueAt(row, 2);

            txtTenDangNhap.setText(tenDangNhap);
            txtMatKhau.setText(matKhau);
            cboLoaiTaiKhoan.setSelectedItem(loaiTaiKhoan);
        }
    }

    private void styleButton(JButton button, Color backgroundColor, Color textColor) {
        button.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        button.setOpaque(true);
        button.setBorderPainted(false);
    }

    private void loadDataTaiKhoan() {
        TaiKhoan_DAO taiKhoanDAO = new TaiKhoan_DAO();
        List<TaiKhoan> taiKhoanList = taiKhoanDAO.getAllTaiKhoan();

        model.setRowCount(0);

        for (TaiKhoan taiKhoan : taiKhoanList) {
            model.addRow(new Object[]{
                    taiKhoan.getTenDangNhap(),
                    taiKhoan.getMatKhau(),
                    taiKhoan.getLoaiTaiKhoan()
            });
        }
    }

    private void handleLamMoi() {
        loadDataTaiKhoan();
    }

    private void handleThem() {
        String tenDangNhap = txtTenDangNhap.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword()).trim();
        String loaiTaiKhoan = (String) cboLoaiTaiKhoan.getSelectedItem();

        if (tenDangNhap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TaiKhoan taiKhoan = new TaiKhoan(tenDangNhap, matKhau, loaiTaiKhoan);

        TaiKhoan_DAO taiKhoanDAO = new TaiKhoan_DAO();
        boolean success = taiKhoanDAO.themTaiKhoan(taiKhoan);

        if (success) {
            JOptionPane.showMessageDialog(this, "Tạo tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadDataTaiKhoan();
            handleXoaTrang();
        } else
            JOptionPane.showMessageDialog(this, "Tạo tài khoản thất bại. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }



    private void handleXoaTrang() {
        txtTenDangNhap.setText("");
        txtTenDangNhap.requestFocus();
        txtMatKhau.setText("");
        cboLoaiTaiKhoan.setSelectedIndex(0);
    }

    private void handleXoa() {
        String tenDangNhap = txtTenDangNhap.getText().trim();

        if (tenDangNhap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa tài khoản này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            TaiKhoan_DAO taiKhoanDAO = new TaiKhoan_DAO();

            boolean isDeleted = taiKhoanDAO.xoaTaiKhoan(tenDangNhap);

            if (isDeleted) {
                JOptionPane.showMessageDialog(this, "Tài khoản đã được xóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                handleXoaTrang();
            } else
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa tài khoản. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSua() {
        String tenDangNhap = txtTenDangNhap.getText();
        String matKhau = txtMatKhau.getText();
        String loaiTaiKhoan = cboLoaiTaiKhoan.getSelectedItem().toString();

        if (tenDangNhap.isEmpty() || matKhau.isEmpty() || loaiTaiKhoan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TaiKhoan taiKhoan = new TaiKhoan(tenDangNhap, matKhau, loaiTaiKhoan);

        TaiKhoan_DAO taiKhoanDAO = new TaiKhoan_DAO();
        boolean isUpdated = taiKhoanDAO.capNhatTaiKhoan(taiKhoan);

        if (isUpdated) {
            JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadDataTaiKhoan();
            handleXoaTrang();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaiKhoan_GD frame = new TaiKhoan_GD();
            frame.setVisible(true);
        });
    }
}
