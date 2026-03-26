package ui;

import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import entity.KhachHang;
import entity.NhanVien;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class CapNhatKhachHang_GD extends JPanel {

   
    private DefaultTableModel model;
    private JTable table;

    private JTextField txtMaKH;
    private JTextField txtSDT;
    private JTextField txtTenDangNhap;
    private JTextField txtTenKhachHang;
    private JTextField txtEmail;
    private JSpinner spnNgaySinh;

    private JButton btnThem, btnXoa, btnSua, btnXoaTrang;

    public CapNhatKhachHang_GD() {
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

        JLabel titleLabel = new JLabel("Cập nhật khách hàng", JLabel.CENTER);
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

        Box b = Box.createVerticalBox();
        Border innerPadding = BorderFactory.createEmptyBorder(10, 30, 10, 30);
        Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(lineBorder, "Thông tin khách hàng");

        titledBorder.setTitleFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        b.setBorder(BorderFactory.createCompoundBorder(titledBorder, innerPadding));
        Box b1, b2, b3;

        JLabel lblMaKH, lblSDT, lblTenDangNhap, lblTenKhachHang, lblEmail, lblNgaySinh;

        b.add(b1 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b1.add(lblMaKH = new JLabel("Mã khách hàng:"));
        b1.add(txtMaKH = new JTextField());
        b1.add(lblSDT = new JLabel("Số điện thoại:"));
        b1.add(txtSDT = new JTextField());

        b.add(b2 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b2.add(lblTenDangNhap = new JLabel("Tên đăng nhập:"));
        b2.add(txtTenDangNhap = new JTextField());
        b2.add(lblTenKhachHang = new JLabel("Tên khách hàng:"));
        b2.add(txtTenKhachHang = new JTextField());

        b.add(b3 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b3.add(lblEmail = new JLabel("Email:"));
        b3.add(txtEmail = new JTextField());
        b3.add(lblNgaySinh = new JLabel("Ngày sinh: "));
        spnNgaySinh = new JSpinner(new SpinnerDateModel());
        spnNgaySinh.setEditor(new JSpinner.DateEditor(spnNgaySinh, "yyyy-MM-dd"));
        b3.add(spnNgaySinh);


        lblMaKH.setPreferredSize(lblTenDangNhap.getPreferredSize());
        lblSDT.setPreferredSize(lblTenDangNhap.getPreferredSize());
        lblTenDangNhap.setPreferredSize(lblTenDangNhap.getPreferredSize());
        lblTenKhachHang.setPreferredSize(lblTenDangNhap.getPreferredSize());
        lblEmail.setPreferredSize(lblTenDangNhap.getPreferredSize());
        lblNgaySinh.setPreferredSize(lblTenDangNhap.getPreferredSize());

        Font labelFont = new Font("Comic Sans MS", Font.PLAIN, 14);
        lblMaKH.setFont(labelFont);
        lblSDT.setFont(labelFont);
        lblTenDangNhap.setFont(labelFont);
        lblTenKhachHang.setFont(labelFont);
        lblEmail.setFont(labelFont);
        lblNgaySinh.setFont(labelFont);

        Dimension textFieldSize = new Dimension(600, 30);
        txtMaKH.setPreferredSize(textFieldSize);
        txtSDT.setPreferredSize(textFieldSize);
        txtTenDangNhap.setPreferredSize(textFieldSize);
        txtTenKhachHang.setPreferredSize(textFieldSize);
        txtEmail.setPreferredSize(textFieldSize);

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

        Dimension buttonSize = new Dimension(120, 40);

        btnThem.setPreferredSize(buttonSize);
        btnThem.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnThem.setBackground(new Color(40, 167, 69)); // Màu xanh lá cây (thêm)
        btnThem.setForeground(Color.WHITE);
        btnThem.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2, true));
        btnThem.setOpaque(true);
        btnThem.setBorderPainted(false);

        btnXoa.setPreferredSize(buttonSize);
        btnXoa.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnXoa.setBackground(new Color(220, 53, 69)); // Màu đỏ (xóa)
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setBorder(BorderFactory.createLineBorder(Color.RED, 2, true));
        btnXoa.setOpaque(true);
        btnXoa.setBorderPainted(false);

        btnSua.setPreferredSize(buttonSize);
        btnSua.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnSua.setBackground(new Color(255, 193, 7)); // Màu vàng (sửa)
        btnSua.setForeground(Color.WHITE);
        btnSua.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2, true));
        btnSua.setOpaque(true);
        btnSua.setBorderPainted(false);

        btnXoaTrang.setPreferredSize(buttonSize);
        btnXoaTrang.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnXoaTrang.setBackground(new Color(23, 162, 184)); // Màu xanh biển (xóa trắng)
        btnXoaTrang.setForeground(Color.WHITE);
        btnXoaTrang.setBorder(BorderFactory.createLineBorder(new Color(23, 162, 184), 2, true));
        btnXoaTrang.setOpaque(true);
        btnXoaTrang.setBorderPainted(false);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoaTrang);

        panelCenter.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCenter.add(buttonPanel);
        panelCenter.add(Box.createRigidArea(new Dimension(0, 20)));


        String[] columnNames = {"Mã khách hàng", "Số điện thoại", "Tên đăng nhập", "Tên khách hàng", "Email", "Ngày sinh"};
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

        loadDataKhachHang();
    }

  

  
    private void fillForm(int row) {
        if (row != -1) {
            String maKhachHang = model.getValueAt(row, 0).toString();
            String sdt = model.getValueAt(row, 1).toString();
            String tenDangNhap = model.getValueAt(row, 2).toString();
            String tenKhachHang = model.getValueAt(row, 3).toString();
            String email = model.getValueAt(row, 4).toString();
            String ngaySinh = model.getValueAt(row, 5).toString();

            txtMaKH.setText(maKhachHang);
            txtSDT.setText(sdt);
            txtTenDangNhap.setText(tenDangNhap);
            txtTenKhachHang.setText(tenKhachHang);
            txtEmail.setText(email);

            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(ngaySinh);
                spnNgaySinh.setValue(date);
            } catch (ParseException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi chuyển đổi ngày sinh: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void loadDataKhachHang() {
        try {
            KhachHang_DAO khachHangDAO = new KhachHang_DAO();
            List<KhachHang> khachHangs = khachHangDAO.getAllKhachHang();

            model.setRowCount(0);

            for (KhachHang khachHang : khachHangs) {
                Object[] row = {
                        khachHang.getMaKhachHang(),
                        khachHang.getSDT(),
                        khachHang.getTenDangNhap(),
                        khachHang.getTenKhachHang(),
                        khachHang.getEmail(),
                        khachHang.getNgaySinh()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validataForm() {
        if (txtMaKH.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng không được để trống.");
            txtMaKH.requestFocus();
            return false;
        }

        String maKH = txtMaKH.getText().trim();
        if (!maKH.matches("^[a-zA-Z0-9]+$")) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng chỉ được chứa chữ cái và số.");
            txtMaKH.requestFocus();
            return false;
        }

        String sdt = txtSDT.getText().trim();
        if (sdt.isEmpty() || !sdt.matches("\\d{10}")) { // Kiểm tra nếu số điện thoại không phải là 10 chữ số
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ. Vui lòng nhập 10 chữ số.");
            txtSDT.requestFocus();
            return false;
        }

        if (txtTenDangNhap.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống.");
            txtTenDangNhap.requestFocus();
            return false;
        }

        String tenKhachHang = txtTenKhachHang.getText().trim();

        if (tenKhachHang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống.");
            txtTenKhachHang.requestFocus();
            return false;
        }

        if (!Character.isUpperCase(tenKhachHang.charAt(0))) {
            JOptionPane.showMessageDialog(this, "Chữ cái đầu của tên khách hàng phải viết hoa.");
            txtTenKhachHang.requestFocus();
            return false;
        }

        String email = txtEmail.getText().trim();
        if (email.isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ. Vui lòng nhập đúng định dạng.");
            txtEmail.requestFocus();
            return false;
        }

        Date ngaySinh = (Date) spnNgaySinh.getValue();
        if (ngaySinh == null) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống.");
            spnNgaySinh.requestFocus();
            return false;
        } else {
            Date today = new Date();
            if (ngaySinh.after(today)) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ. Vui lòng chọn ngày trước ngày hiện tại.");
                spnNgaySinh.requestFocus();
                return false;
            }

            Calendar calNgaySinh = Calendar.getInstance();
            calNgaySinh.setTime(ngaySinh);

            Calendar calToday = Calendar.getInstance();
            calToday.setTime(today);

            int age = calToday.get(Calendar.YEAR) - calNgaySinh.get(Calendar.YEAR);
            if (calToday.get(Calendar.DAY_OF_YEAR) < calNgaySinh.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            if (age < 18) {
                JOptionPane.showMessageDialog(this, "Người dùng phải từ 18 tuổi trở lên.");
                spnNgaySinh.requestFocus();
                return false;
            }
        }


        return true;
    }


    private void handleThem() {
        if (!validataForm()) {
            return;
        }

        String maKhachHang = txtMaKH.getText().trim();
        String sdt = txtSDT.getText().trim();
        String tenDangNhap = txtTenDangNhap.getText().trim();
        String tenKhachHang = txtTenKhachHang.getText().trim();
        String email = txtEmail.getText().trim();
        java.util.Date ngaySinh = (java.util.Date) spnNgaySinh.getValue();

        KhachHang kh = new KhachHang(maKhachHang, sdt, tenDangNhap, tenKhachHang, email, ngaySinh);

        KhachHang_DAO dao = new KhachHang_DAO();
        boolean isInserted = dao.insertKhachHang(kh);

        if (isInserted) {
            JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadDataKhachHang();
            handleXoaTrang();
        } else {
            JOptionPane.showMessageDialog(null, "Có lỗi khi thêm khách hàng. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleXoaTrang() {
        txtMaKH.setText("");
        txtSDT.setText("");
        txtTenDangNhap.setText("");
        txtTenKhachHang.setText("");
        txtEmail.setText("");

        spnNgaySinh.setValue(new java.util.Date());
    }


    private void handleXoa() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maKhachHang = table.getValueAt(selectedRow, 0).toString(); // Giả sử mã khách hàng ở cột đầu tiên
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                KhachHang_DAO khachHangDAO = new KhachHang_DAO(); // Tạo đối tượng KhachHang_DAO

                if (khachHangDAO.deleteKhachHang(maKhachHang)) { // Gọi phương thức xóa
                    JOptionPane.showMessageDialog(null, "Xóa khách hàng thành công.");
                    ((DefaultTableModel) table.getModel()).removeRow(selectedRow); // Xóa hàng khỏi giao diện
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa khách hàng thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một hàng để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }



    private void handleSua() {
        String maKhachHang = txtMaKH.getText();
        String tenDangNhap = txtTenDangNhap.getText();
        String tenKhachHang = txtTenKhachHang.getText();
        String email = txtEmail.getText();  // Email
        String soDienThoai = txtSDT.getText();
        Date ngaySinh = (Date) spnNgaySinh.getValue();

        KhachHang kh = new KhachHang(maKhachHang, soDienThoai, tenDangNhap, tenKhachHang, email, ngaySinh);

        KhachHang_DAO dao = new KhachHang_DAO();
        boolean isUpdated = dao.updateKhachHang(kh);

        if (isUpdated) {
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin khách hàng thành công!");

            handleXoaTrang();
            loadDataKhachHang();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin khách hàng thất bại!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CapNhatKhachHang_GD frame = new CapNhatKhachHang_GD();
            frame.setVisible(true);
        });
    }
}
