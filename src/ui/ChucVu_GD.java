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

public class ChucVu_GD extends JPanel {

   

    private DefaultTableModel model;
    private JTable table;

    private JTextField txtMaNV;
    private JTextField txtTenNhanVien;
    JComboBox<String> cboChucVu;

    private JButton btnThem, btnXoa, btnSua, btnXoaTrang, btnLamMoi;

    public ChucVu_GD() {
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

        JLabel titleLabel = new JLabel("Quản lý chức vụ", JLabel.CENTER);
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

        JLabel lblMaNV, lblTenNhanVien, lblChucVu;

        Box b = Box.createVerticalBox();
        Border innerPadding = BorderFactory.createEmptyBorder(10, 30, 10, 30);
        Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(lineBorder, "Quản lý chức vụ");

        titledBorder.setTitleFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        b.setBorder(BorderFactory.createCompoundBorder(titledBorder, innerPadding));

        Box b1, b2, b3, b4;

        b.add(b1 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b1.add(lblMaNV = new JLabel("Mã nhân viên:"));
        lblMaNV.setPreferredSize(new Dimension(120, 30)); // Đặt kích thước cố định cho JLabel
        b1.add(Box.createHorizontalStrut(10));
        b1.add(txtMaNV = new JTextField(20));

        b.add(b2 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b2.add(lblTenNhanVien = new JLabel("Tên nhân viên:"));
        lblTenNhanVien.setPreferredSize(new Dimension(120, 30));
        b2.add(Box.createHorizontalStrut(10));
        b2.add(txtTenNhanVien = new JTextField(20));

        b.add(b3 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b3.add(lblChucVu = new JLabel("Chức vụ:"));
        lblChucVu.setPreferredSize(new Dimension(120, 30));
        b3.add(Box.createHorizontalStrut(10));
        String[] comboChucVu = { "Quản lý", "Nhân viên bán vé", "Nhân viên phục vụ", "Nhân viên bảo vệ" };
        cboChucVu = new JComboBox<>(comboChucVu);
        b3.add(cboChucVu);

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
        lblMaNV.setFont(labelFont);
        lblTenNhanVien.setFont(labelFont);
        lblChucVu.setFont(labelFont);

        b.setPreferredSize(new Dimension(800, 200));
        b.setMaximumSize(new Dimension(800, 200));
        b.setMinimumSize(new Dimension(800, 200));

        Dimension buttonSize = new Dimension(120, 40);

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

        btnLamMoi.setPreferredSize(buttonSize);
        btnLamMoi.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnLamMoi.setBackground(new Color(255, 159, 10)); // Màu cam (Làm mới)
        btnLamMoi.setForeground(Color.WHITE);  // Màu chữ trắng
        btnLamMoi.setBorder(BorderFactory.createLineBorder(new Color(255, 159, 10), 2, true)); // Viền màu cam
        btnLamMoi.setOpaque(true);
        btnLamMoi.setBorderPainted(false);

        panelCenter.add(b);

        add(panelCenter, BorderLayout.CENTER);

        btnThem.addActionListener(e -> handleThem());
        btnXoa.addActionListener(e -> handleXoa());
        btnSua.addActionListener(e -> handleSua());
        btnXoaTrang.addActionListener(e -> handleXoaTrang());
        btnLamMoi.addActionListener(e -> handleLamMoi());

        String[] columnNames = {"Mã nhân viên", "Tên Nhân viên", "Chức vụ"};
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

        loadDataChucVu();
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
        if(row != -1) {
            String maNhanVien = (String) model.getValueAt(row, 0);
            String tenNhanVien = (String) model.getValueAt(row, 1);
            String chucVu = (String) model.getValueAt(row, 2);

            txtMaNV.setText(maNhanVien);
            txtTenNhanVien.setText(tenNhanVien);
            cboChucVu.setSelectedItem(chucVu);
        }
    }




    private void loadDataChucVu() {
        NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
        List<NhanVien> nhanVienList = nhanVienDAO.getAllNhanVien();

        model.setRowCount(0);

        for (NhanVien nhanVien : nhanVienList) {
            Object[] rowData = {
                    nhanVien.getMaNhanVien(),
                    nhanVien.getTenNhanVien(),
                    nhanVien.getChucVu()
            };
            model.addRow(rowData);
        }
    }



    private void handleLamMoi() {
        loadDataChucVu();
        handleXoaTrang();
    }

    private boolean validataForm() {
        String maNV = txtMaNV.getText().trim();
        String tenNhanVien = txtTenNhanVien.getText().trim();
        String chucVu = (String) cboChucVu.getSelectedItem();

        if (maNV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã nhân viên.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtMaNV.requestFocus();
            return false;
        } else if (!maNV.matches("[A-Za-z0-9]+")) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên chỉ bao gồm chữ và số.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtMaNV.requestFocus();
            return false;
        }

        if (tenNhanVien.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống!");
            txtTenNhanVien.requestFocus();
            return false;
        } else if (!Character.isUpperCase(tenNhanVien.charAt(0))) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên phải viết hoa chữ cái đầu!");
            txtTenNhanVien.requestFocus();
            return false;
        }

        if (chucVu == null || chucVu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Chức vụ.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            cboChucVu.requestFocus();
            return false;
        }

        return true;
    }


    private void handleThem() {
        if (!validataForm()) {
            return;
        }
        String maNhanVien = txtMaNV.getText().trim();
        String tenNhanVien = txtTenNhanVien.getText().trim();
        String chucVu = (String) cboChucVu.getSelectedItem();

        if (maNhanVien.isEmpty() || tenNhanVien.isEmpty() || chucVu == null || chucVu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        NhanVien nv = new NhanVien(maNhanVien, "", tenNhanVien, "", 0, "", chucVu);

        NhanVien_DAO nvDAO = new NhanVien_DAO();
        if (nvDAO.isMaNhanVienExist(maNhanVien)) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean success = nvDAO.insertNhanVien(nv);

        if (success) {
            loadDataChucVu();
            handleXoaTrang();
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm nhân viên!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleXoaTrang() {
        txtMaNV.setText("");
        txtMaNV.requestFocus();
        txtTenNhanVien.setText("");
        cboChucVu.setSelectedIndex(0);
    }

    private void handleXoa() {
        String maNhanVien = txtMaNV.getText().trim();

        if (maNhanVien.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mã nhân viên không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nhân viên có mã " + maNhanVien + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
            boolean isDeleted = nhanVienDAO.deleteNhanVien(maNhanVien);

            if (isDeleted) {
                JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                handleXoaTrang();
                loadDataChucVu();
            } else
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void handleSua() {
        String maNhanVien = txtMaNV.getText().trim();
        String tenNhanVien = txtTenNhanVien.getText().trim();
        String chucVu = cboChucVu.getSelectedItem().toString();

        if (maNhanVien.isEmpty() || tenNhanVien.isEmpty() || chucVu.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn cập nhật thông tin nhân viên này?", "Xác nhận cập nhật", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
            boolean isUpdated = nhanVienDAO.updateChucVuNhanVien(maNhanVien, tenNhanVien, chucVu);

            if (isUpdated) {
                JOptionPane.showMessageDialog(null, "Cập nhật thông tin nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                handleXoaTrang();
                loadDataChucVu();
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật thông tin nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý chức vụ"); // Tạo một JFrame
            ChucVu_GD chucVu_GD = new ChucVu_GD();
            frame.setContentPane(chucVu_GD); // Đặt JPanel làm nội dung của JFrame
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack(); // Điều chỉnh kích thước của JFrame cho phù hợp với JPanel
            frame.setVisible(true); // Hiển thị JFrame
        });
    }
}
