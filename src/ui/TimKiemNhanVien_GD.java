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
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TimKiemNhanVien_GD extends JPanel {

  

    private DefaultTableModel model;
    private JTable table;

    private JTextField txtMaNV;
    private JTextField txtSDT;
    private JTextField txtTenDangNhap;
    private JTextField txtTenNhanVien;
    private JTextField txtLuong;
    private JTextField txtCmnd;
    JComboBox<String> cboChucVu;

    private JButton btnTimKiem, btnXoaTrang, btnLamMoi;

    public TimKiemNhanVien_GD() {
        
        setLayout(new BorderLayout());

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

        JLabel titleLabel = new JLabel("Tìm kiếm nhân viên", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.BLUE);

        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
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

        Box b = Box.createVerticalBox();
        Border innerPadding = BorderFactory.createEmptyBorder(10, 30, 10, 30);
        Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(lineBorder, "Thông tin nhân viên");
        titledBorder.setTitleFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        Border outerMargin = BorderFactory.createEmptyBorder(20, 20, 0, 20);  // You can adjust these values for outer margin
        b.setBorder(BorderFactory.createCompoundBorder(outerMargin, BorderFactory.createCompoundBorder(titledBorder, innerPadding)));
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

        Font labelFont = new Font("Comic Sans MS", Font.PLAIN, 14);
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center alignment

        btnTimKiem = new JButton("Tìm");
        btnXoaTrang = new JButton("Xoá trắng");
        btnLamMoi = new JButton("Làm mới");

        btnTimKiem.addActionListener(e -> handleTimKiems());
        btnXoaTrang.addActionListener(e -> handleXoaTrang());
        btnLamMoi.addActionListener(e -> handleLamMoi());

        Dimension buttonSize = new Dimension(120, 40);

        btnTimKiem.setPreferredSize(buttonSize);
        btnTimKiem.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnTimKiem.setBackground(new Color(0, 123, 255)); // Màu xanh dương (Tìm)
        btnTimKiem.setForeground(Color.WHITE);  // Màu chữ trắng
        btnTimKiem.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2, true)); // Viền màu xanh dương
        btnTimKiem.setOpaque(true);
        btnTimKiem.setBorderPainted(false);

        btnXoaTrang.setPreferredSize(buttonSize);
        btnXoaTrang.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnXoaTrang.setBackground(new Color(23, 162, 184)); // Màu xanh biển (Xóa trắng)
        btnXoaTrang.setForeground(Color.WHITE);  // Màu chữ trắng
        btnXoaTrang.setBorder(BorderFactory.createLineBorder(new Color(23, 162, 184), 2, true)); // Viền màu xanh biển
        btnXoaTrang.setOpaque(true);
        btnXoaTrang.setBorderPainted(false);

        btnLamMoi.setPreferredSize(buttonSize);
        btnLamMoi.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnLamMoi.setBackground(new Color(255, 159, 10)); // Màu cam (Làm mới)
        btnLamMoi.setForeground(Color.WHITE);  // Màu chữ trắng
        btnLamMoi.setBorder(BorderFactory.createLineBorder(new Color(255, 159, 10), 2, true)); // Viền màu cam
        btnLamMoi.setOpaque(true);
        btnLamMoi.setBorderPainted(false);

        buttonPanel.add(btnTimKiem);
        buttonPanel.add(btnXoaTrang);
        buttonPanel.add(btnLamMoi);

        panelCenter.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCenter.add(buttonPanel);
        panelCenter.add(Box.createRigidArea(new Dimension(0, 20)));


        // Create the table
        String[] columnNames = {"Mã nhân viên","Số điện thoại", "Tên Nhân viên", "Tên đăng nhập", "Tiền lương", "CMND", "Chức vụ"};
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

    private void fillForm(int row) {
        if (row != -1) {
            String maNhanVien = table.getValueAt(row, 0).toString();
            String sdt = table.getValueAt(row, 1).toString();
            String tenNhanVien = table.getValueAt(row, 2).toString();
            String tenDangNhap = table.getValueAt(row, 3).toString();
            String luong = table.getValueAt(row, 4).toString();
            String cmnd = table.getValueAt(row, 5).toString();
            String chucVu = table.getValueAt(row, 6).toString();

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
        NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
        List<NhanVien> danhSachNhanVien = nhanVienDAO.getAllNhanVien();

        model.setRowCount(0);

        for (NhanVien nv : danhSachNhanVien) {
            Object[] row = {
                    nv.getMaNhanVien(),
                    nv.getSDT(),
                    nv.getTenNhanVien(),
                    nv.getTenDangNhap(),
                    nv.getLuong(),
                    nv.getCmnd(),
                    nv.getChucVu()
            };
            model.addRow(row);
        }
    }


    private void handleTimKiems() {
        String maNhanVien = txtMaNV.getText().trim();
        String sdt = txtSDT.getText().trim();
        String tenNhanVien = txtTenNhanVien.getText().trim();
        String tenDangNhap = txtTenDangNhap.getText().trim();
        Integer luong = null;
        String cmnd = txtCmnd.getText().trim();
        String chucVu = cboChucVu.getSelectedItem().toString();

        if (!txtLuong.getText().trim().isEmpty()) {
            try {
                luong = Integer.parseInt(txtLuong.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Lương phải là một số nguyên hợp lệ.");
                return;
            }
        }

        NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
        List<NhanVien> nhanVienList = nhanVienDAO.searchNhanVien(maNhanVien, sdt, tenNhanVien, tenDangNhap, luong, cmnd, chucVu);

        model.setRowCount(0);

        for (NhanVien nv : nhanVienList) {
            model.addRow(new Object[]{
                    nv.getMaNhanVien(),
                    nv.getSDT(),
                    nv.getTenNhanVien(),
                    nv.getTenDangNhap(),
                    nv.getLuong(),
                    nv.getCmnd(),
                    nv.getChucVu()
            });
        }

        // Hiển thị thông báo nếu không tìm thấy kết quả
        if (nhanVienList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên nào phù hợp với tiêu chí tìm kiếm.");
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


    private void handleLamMoi() {
        handleXoaTrang();
        loadDataNhanVien();
    }

    //public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> {
            //TimKiemNhanVien_GD frame = new TimKiemNhanVien_GD();
            //frame.setVisible(true);
        //});
    //}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Tạo JFrame
                JFrame frame = new JFrame("Tìm kiếm nhân viên");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1600, 900);  // Đặt kích thước cho JFrame
                frame.setLocationRelativeTo(null); // Đảm bảo JFrame xuất hiện ở giữa màn hình
                frame.add(new TimKiemNhanVien_GD());  // Thêm JPanel vào JFrame
                frame.setVisible(true);  // Hiển thị JFrame
            }
        });
    }

}
