package ui;

import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import entity.KhachHang;
import entity.NhanVien;

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

public class CapNhatNhanVien_GD extends JPanel {

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

    private JTextField txtMaNV;
    private JTextField txtSDT;
    private JTextField txtTenDangNhap;
    private JTextField txtTenNhanVien;
    private JTextField txtLuong;
    private JTextField txtCmnd;
    JComboBox<String> cboChucVu;

    private JButton btnThem, btnXoa, btnSua, btnXoaTrang;

    public CapNhatNhanVien_GD() {
        setLayout(new BorderLayout());

        // Tiêu đề
		GradientPanel titlePanel = new GradientPanel();
		titlePanel.setLayout(new FlowLayout());
		JLabel titleLabel = new JLabel("Cập nhật nhân viên", JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 44));
		titleLabel.setForeground(Color.BLACK); // Màu chữ
		titlePanel.add(titleLabel);
		add(titlePanel, BorderLayout.NORTH);        

        JPanel panelCenter = new JPanel();

        Box b = Box.createVerticalBox();
        Border innerPadding = BorderFactory.createEmptyBorder(10, 30, 10, 30);
        Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(lineBorder, "Thông tin nhân viên");

        titledBorder.setTitleFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        b.setBorder(BorderFactory.createCompoundBorder(titledBorder, innerPadding));
        Box b1, b2, b3, b4;

        JLabel lblMaNV, lblSDT, lblTenDangNhap, lblTenNhanVien, lblLuong, lblCmnd, lblChucVu;

        b.add(b1 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b1.add(lblMaNV = new JLabel("Mã nhân viên:"));
        b1.add(txtMaNV = new JTextField());
        b1.add(lblSDT = new JLabel("Số điện thoại:"));
        b1.add(txtSDT = new JTextField());

        b.add(b2 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b2.add(lblTenDangNhap = new JLabel("Tên đăng nhập:"));
        b2.add(txtTenDangNhap = new JTextField());
        b2.add(lblTenNhanVien = new JLabel("Tên nhân viên:"));
        b2.add(txtTenNhanVien = new JTextField());

        b.add(b3 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b3.add(lblLuong = new JLabel("Lương:"));
        b3.add(txtLuong = new JTextField());
        b3.add(lblCmnd = new JLabel("Cmnd:"));
        b3.add(txtCmnd = new JTextField());

        b.add(b4 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b4.add(lblChucVu = new JLabel("Chức vụ: "));
        String[] comboChucVu = { "Quản lý", "Nhân viên bán vé", "Nhân viên phục vụ", "Nhân viên bảo vệ", "Nhân viên bảo trì" };
        cboChucVu = new JComboBox<>(comboChucVu);
        b4.add(cboChucVu);


        // Đặt kích thước cho các JLabel
        lblMaNV.setPreferredSize(lblTenDangNhap.getPreferredSize());
        lblSDT.setPreferredSize(lblTenDangNhap.getPreferredSize());
        lblTenNhanVien.setPreferredSize(lblTenDangNhap.getPreferredSize());
        lblLuong.setPreferredSize(lblTenDangNhap.getPreferredSize());
        lblCmnd.setPreferredSize(lblTenDangNhap.getPreferredSize());
        lblChucVu.setPreferredSize(lblTenDangNhap.getPreferredSize());

        Font labelFont = new Font("Arial", Font.BOLD, 15);
        lblMaNV.setFont(labelFont);
        lblSDT.setFont(labelFont);
        lblTenDangNhap.setFont(labelFont);
        lblTenNhanVien.setFont(labelFont);
        lblLuong.setFont(labelFont);
        lblCmnd.setFont(labelFont);
        lblChucVu.setFont(labelFont);

        Dimension textFieldSize = new Dimension(600, 30);
        txtMaNV.setPreferredSize(textFieldSize);
        txtSDT.setPreferredSize(textFieldSize);
        txtTenDangNhap.setPreferredSize(textFieldSize);
        txtTenNhanVien.setPreferredSize(textFieldSize);
        txtLuong.setPreferredSize(textFieldSize);
        txtCmnd.setPreferredSize(textFieldSize);
        cboChucVu.setPreferredSize(textFieldSize);

        panelCenter.add(b);

        add(panelCenter, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xoá");
        btnSua = new JButton("Sửa");
        btnXoaTrang = new JButton("Xoá trắng");

        btnThem.addActionListener(e -> handleThem());
        btnXoa.addActionListener(e -> handleXoa());
        btnSua.addActionListener(e -> handleSua());
        btnXoaTrang.addActionListener(e -> handleXoaTrang());

        // Kích thước các nút
        Dimension buttonSize = new Dimension(120, 40);

// Cập nhật nút "Thêm"
        btnThem.setPreferredSize(buttonSize);
        btnThem.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnThem.setBackground(new Color(40, 167, 69)); // Màu xanh lá cây (Thêm)
        btnThem.setForeground(Color.WHITE);  // Màu chữ trắng
        btnThem.setBorder(BorderFactory.createLineBorder(new Color(40, 167, 69), 2, true)); // Viền màu xanh lá
        btnThem.setOpaque(true);
        btnThem.setBorderPainted(false);

        btnXoa.setPreferredSize(buttonSize);
        btnXoa.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnXoa.setBackground(new Color(220, 53, 69)); // Màu đỏ (Xóa)
        btnXoa.setForeground(Color.WHITE);  // Màu chữ trắng
        btnXoa.setBorder(BorderFactory.createLineBorder(new Color(220, 53, 69), 2, true)); // Viền màu đỏ
        btnXoa.setOpaque(true);
        btnXoa.setBorderPainted(false);

        btnSua.setPreferredSize(buttonSize);
        btnSua.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnSua.setBackground(new Color(255, 193, 7)); // Màu vàng (Sửa)
        btnSua.setForeground(Color.WHITE);  // Màu chữ trắng
        btnSua.setBorder(BorderFactory.createLineBorder(new Color(255, 193, 7), 2, true)); // Viền màu vàng
        btnSua.setOpaque(true);
        btnSua.setBorderPainted(false);

        btnXoaTrang.setPreferredSize(buttonSize);
        btnXoaTrang.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnXoaTrang.setBackground(new Color(23, 162, 184)); // Màu xanh biển (Xóa trắng)
        btnXoaTrang.setForeground(Color.WHITE);  // Màu chữ trắng
        btnXoaTrang.setBorder(BorderFactory.createLineBorder(new Color(23, 162, 184), 2, true)); // Viền màu xanh biển
        btnXoaTrang.setOpaque(true);
        btnXoaTrang.setBorderPainted(false);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoaTrang);

        panelCenter.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCenter.add(buttonPanel);
        panelCenter.add(Box.createRigidArea(new Dimension(0, 20)));

        String[] columnNames = {"Mã nhân viên","Số điện thoại", "Tên Nhân viên", "Tên đăng nhập", "Tiền lương", "CMND", "Chức vụ"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        // Set font for table
        table.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        table.setRowHeight(30);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        header.setBackground(new Color(200, 200, 200));
        header.setForeground(Color.BLACK);

        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1400, 400));

        panelCenter.add(scrollPane);

        add(panelCenter, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = table.getSelectedRow();
                fillForm(row);
            }
        });

        loadDataNhanVien();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Create and add main menus
        JMenu menuNhanVien = createMenu("Nhân viên", "src/ui/icon/nhanVien.png");
        JMenu menuKhachHang = createMenu("Khách hàng", "src/ui/icon/khachHang.jpg");
        JMenu menuPhim = createMenu("Phim", "src/ui/icon/phim.png");
        JMenu menuVe = createMenu("Vé", "src/ui/icon/ve.jpg");
        JMenu menuHoaDon = createMenu("Hoá đơn", "src/ui/icon/khachHang.png");
        JMenu menuThongKe = createMenu("Thống kê", "src/ui/icon/khachHang.png");

        // Initialize and add menu items for "Nhân viên"
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
            String maNhanVien = (String) table.getValueAt(row, 0);
            String sdt = (String) table.getValueAt(row, 1);
            String tenNhanVien = (String) table.getValueAt(row, 2);
            String tenDangNhap = (String) table.getValueAt(row, 3);
            String luong = String.valueOf(table.getValueAt(row, 4));  // Đảm bảo luong là String
            String cmnd = (String) table.getValueAt(row, 5);
            String chucVu = (String) table.getValueAt(row, 6);

            txtMaNV.setText(maNhanVien);
            txtSDT.setText(sdt);
            txtTenNhanVien.setText(tenNhanVien);
            txtTenDangNhap.setText(tenDangNhap);
            txtLuong.setText(luong);
            txtCmnd.setText(cmnd);
            cboChucVu.setSelectedItem(chucVu);
        }
    }




    private void loadDataNhanVien() {
        NhanVien_DAO nvDao = new NhanVien_DAO();
        List<NhanVien> nhanVienList = nvDao.getAllNhanVien();

        model.setRowCount(0);

        for (NhanVien nv : nhanVienList) {
            Object[] rowData = {
                    nv.getMaNhanVien(),
                    nv.getSDT(),
                    nv.getTenNhanVien(),
                    nv.getTenDangNhap(),
                    nv.getLuong(),
                    nv.getCmnd(),
                    nv.getChucVu()
            };
            model.addRow(rowData);
        }
    }

    private boolean validataForm() {
        String maNV = txtMaNV.getText().trim();
        if (maNV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã nhân viên.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtMaNV.requestFocus();
            return false;
        } else if (!maNV.matches("[A-Za-z0-9]+")) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên chỉ bao gồm chữ và số.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtMaNV.requestFocus();
            return false;
        }

        String sdt = txtSDT.getText().trim();
        if (sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống!");
            txtSDT.requestFocus();
            return false;
        } else if (!sdt.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm 10 chữ số!");
            txtSDT.requestFocus();
            return false;
        }

        if (txtTenDangNhap.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống!");
            txtTenDangNhap.requestFocus();
            return false;
        }

        String tenNhanVien = txtTenNhanVien.getText().trim();
        if (tenNhanVien.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống!");
            txtTenNhanVien.requestFocus();
            return false;
        } else if (!Character.isUpperCase(tenNhanVien.charAt(0))) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên phải viết hoa chữ cái đầu!");
            txtTenNhanVien.requestFocus();
            return false;
        }

        try {
            double luong = Double.parseDouble(txtLuong.getText().trim());
            if (luong <= 0) {
                JOptionPane.showMessageDialog(this, "Lương phải là số dương!");
                txtLuong.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lương phải là một số hợp lệ!");
            txtLuong.requestFocus();
            return false;
        }

        String cmnd = txtCmnd.getText().trim();
        if (cmnd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "CMND không được để trống!");
            txtCmnd.requestFocus();
            return false;
        } else if (!cmnd.matches("\\d{9}")) {
            JOptionPane.showMessageDialog(this, "CMND phải gồm 9 chữ số!");
            txtCmnd.requestFocus();
            return false;
        }

        if (cboChucVu.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ!");
            cboChucVu.requestFocus();
            return false;
        }

        return true;
    }


    private void handleThem() {
        if (!validataForm()) {
            return;
        }
        String maNhanVien = txtMaNV.getText();
        String SDT = txtSDT.getText();
        String tenNhanVien = txtTenNhanVien.getText();
        String tenDangNhap = txtTenDangNhap.getText();
        int luong = Integer.parseInt(txtLuong.getText());
        String cmnd = txtCmnd.getText();
        String chucVu = (String) cboChucVu.getSelectedItem();

        NhanVien nv = new NhanVien(maNhanVien, SDT, tenNhanVien, tenDangNhap, luong, cmnd, chucVu);

        NhanVien_DAO dao = new NhanVien_DAO();
        boolean isSuccess = dao.insertNhanVien(nv);

        if (isSuccess) {
            handleXoaTrang();
            loadDataNhanVien();
            JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Có lỗi khi thêm nhân viên", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleXoaTrang() {
        txtMaNV.setText("");
        txtSDT.setText("");
        txtTenNhanVien.setText("");
        txtTenDangNhap.setText("");
        txtLuong.setText("");
        txtCmnd.setText("");
        cboChucVu.setSelectedIndex(0);
    }


    private void handleXoa() {
        String maNhanVien = txtMaNV.getText().trim();

        if (maNhanVien.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();

            boolean success = nhanVienDAO.deleteNhanVien(maNhanVien);

            if (success) {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                handleXoaTrang();
                loadDataNhanVien();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại. Vui lòng thử lại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleSua() {
        String maNhanVien = txtMaNV.getText().trim();

        if (maNhanVien.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sdt = txtSDT.getText().trim();
        String tenNhanVien = txtTenNhanVien.getText().trim();
        String tenDangNhap = txtTenDangNhap.getText().trim();
        String luongStr = txtLuong.getText().trim();
        String cmnd = txtCmnd.getText().trim();
        String chucVu = (String) cboChucVu.getSelectedItem();

        if (tenNhanVien.isEmpty() || tenDangNhap.isEmpty() || luongStr.isEmpty() || cmnd.isEmpty() || chucVu == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int luong;
        try {
            luong = Integer.parseInt(luongStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lương phải là số nguyên hợp lệ!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn cập nhật thông tin nhân viên này?", "Xác nhận sửa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            NhanVien nv = new NhanVien(maNhanVien, sdt, tenNhanVien, tenDangNhap, luong, cmnd, chucVu);

            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();

            boolean success = nhanVienDAO.updateNhanVien(nv);

            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                handleXoaTrang();
                loadDataNhanVien();
            } else
                JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thất bại. Vui lòng thử lại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CapNhatNhanVien_GD frame = new CapNhatNhanVien_GD();
            frame.setVisible(true);
        });
    }
}
