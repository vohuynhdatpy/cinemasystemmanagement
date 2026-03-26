package ui;

import dao.KhachHang_DAO;
import dao.TaiKhoan_DAO;
import entity.KhachHang;
import entity.TaiKhoan;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class DangKy extends JFrame implements ActionListener {
    private JTextField txtSDT, txtTenDangNhap, txtTenKhachHang, txtEmail, txtMa;
    private JSpinner txtNgaySinh;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap, btnDangKy;
    private JComboBox<String> cboLoaiTaiKhoan;

    public DangKy() {
        setTitle("Đăng ký");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ImageIcon originalIcon = new ImageIcon("src/ui/icon/logo-cgv.jpg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(500, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel lblImage = new JLabel(scaledIcon);
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setPreferredSize(new Dimension(600, 160));
        add(lblImage, BorderLayout.NORTH);

        JPanel panelForm = new JPanel();

        Box b = Box.createVerticalBox();
        Border innerPadding = BorderFactory.createEmptyBorder(10, 30, 10, 30);
        Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(lineBorder, "Form đăng ký");

        titledBorder.setTitleFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        b.setBorder(BorderFactory.createCompoundBorder(titledBorder, innerPadding));

        Box b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;

        JLabel lblMa, lblSDT, lblTenDangNhap, lblTenKhachHang, lblEmail, lblNgaySinh, lblMatKhau, lblTieuDe, lblLoaiTaiKhoan, lblDK;

        b.add(b1 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b1.add(lblTieuDe = new JLabel("ĐĂNG KÝ"));
        lblTieuDe.setFont(new Font("Comic Sans MS", Font.PLAIN, 23));

        b.add(b0 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b0.add(lblMa = new JLabel("Mã KH: "));
        b0.add(txtMa = new JTextField());

        b.add(b2 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b2.add(lblSDT = new JLabel("Số điện thoại: "));
        b2.add(txtSDT = new JTextField());

        b.add(b3 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b3.add(lblTenDangNhap = new JLabel("Tên đăng nhập: "));
        b3.add(txtTenDangNhap = new JTextField());

        b.add(b4 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b4.add(lblTenKhachHang = new JLabel("Tên khách hàng: "));
        b4.add(txtTenKhachHang = new JTextField());

        b.add(b5 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b5.add(lblEmail = new JLabel("Email: "));
        b5.add(txtEmail = new JTextField());

        b.add(b6 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b6.add(lblNgaySinh = new JLabel("Ngày sinh: "));
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        txtNgaySinh = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(txtNgaySinh, "dd/MM/yyyy");
        txtNgaySinh.setEditor(dateEditor);

        b6.add(txtNgaySinh);

        b.add(b7 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b7.add(lblMatKhau = new JLabel("Mật khẩu: "));
        b7.add(txtMatKhau = new JPasswordField());

        b.add(b8 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b8.add(lblLoaiTaiKhoan = new JLabel("Loại tài khoản: "));
        cboLoaiTaiKhoan = new JComboBox<>(new String[]{"Khách hàng", "Quản trị viên", "Nhân viên bán vé", "Nhân viên phục vụ", "Nhân viên bảo vệ"});
        b8.add(cboLoaiTaiKhoan);

        b.add(b9 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b9.add(btnDangKy = new JButton("Tạo tài khoản"));

        b.add(b10 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b10.add(lblDK = new JLabel("Bạn đã có tài khoản? "));
        b10.add(btnDangNhap = new JButton("Đăng nhập"));

        lblMa.setPreferredSize(lblTenKhachHang.getPreferredSize());
        lblSDT.setPreferredSize(lblTenKhachHang.getPreferredSize());
        lblNgaySinh.setPreferredSize(lblTenKhachHang.getPreferredSize());
        lblMatKhau.setPreferredSize(lblTenKhachHang.getPreferredSize());
        lblLoaiTaiKhoan.setPreferredSize(lblTenKhachHang.getPreferredSize());
        lblMatKhau.setPreferredSize(lblTenKhachHang.getPreferredSize());
        lblEmail.setPreferredSize(lblTenKhachHang.getPreferredSize());
        lblTenDangNhap.setPreferredSize(lblTenKhachHang.getPreferredSize());

        lblMa.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblSDT.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblTenDangNhap.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblTenKhachHang.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblNgaySinh.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblMatKhau.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblLoaiTaiKhoan.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblEmail.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));

        txtMa.setPreferredSize(new Dimension(250, 30));
        txtSDT.setPreferredSize(new Dimension(250, 30));
        txtTenDangNhap.setPreferredSize(new Dimension(250, 30));
        txtTenKhachHang.setPreferredSize(new Dimension(250, 30));
        txtEmail.setPreferredSize(new Dimension(250, 30));
        txtNgaySinh.setPreferredSize(new Dimension(250, 30));
        txtMatKhau.setPreferredSize(new Dimension(250, 30));
        cboLoaiTaiKhoan.setPreferredSize(new Dimension(250, 30));
        btnDangKy.setPreferredSize(new Dimension(150, 40));
        btnDangKy.setFont(new Font("Arial", Font.BOLD, 16));
        btnDangNhap.setPreferredSize(new Dimension(150, 40));
        btnDangNhap.setFont(new Font("Arial", Font.BOLD, 16));

        panelForm.add(b);

        styleButton(btnDangNhap);
        styleButton_2(btnDangKy);

        add(panelForm, BorderLayout.CENTER);

        btnDangKy.addActionListener(this);
        btnDangNhap.addActionListener(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DangKy dk = new DangKy();
            dk.setVisible(true);
        });
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));

        button.setPreferredSize(new Dimension(120, 30));

        button.setBackground(new Color(40, 167, 69));
        button.setForeground(Color.WHITE);

        button.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2, true));

        button.setOpaque(true);
        button.setBorderPainted(false);
    }

    private void styleButton_2(JButton button) {
        button.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));

        button.setPreferredSize(new Dimension(120, 30));

        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);

        button.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2, true));

        button.setOpaque(true);
        button.setBorderPainted(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnDangKy)
            actionDangKy();
        else if(e.getSource() == btnDangNhap) {
            this.dispose(); // Close the current window
            DangNhap dn = new DangNhap();
            dn.setVisible(true);
        }
    }

    private boolean validData() {
        if (txtMa.getText().trim().isEmpty() || txtSDT.getText().trim().isEmpty() ||
                txtTenDangNhap.getText().trim().isEmpty() || txtTenKhachHang.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() || txtMatKhau.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String maKH = txtMa.getText().trim();
        if (!maKH.matches("^[a-zA-Z0-9]+$")) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng chỉ được chứa chữ cái và số.");
            txtMa.requestFocus();
            return false;
        }

        String sdt = txtSDT.getText().trim();
        if (!sdt.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ. Vui lòng nhập lại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String email = txtEmail.getText().trim();
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(emailPattern)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ. Vui lòng nhập lại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String matKhau = new String(txtMatKhau.getPassword()).trim();
        if (matKhau.length() < 6) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        Date ngaySinh = (Date) txtNgaySinh.getValue();
        if (ngaySinh == null) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống.");
            txtNgaySinh.requestFocus();
            return false;
        } else {
            Date today = new Date();
            if (ngaySinh.after(today)) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ. Vui lòng chọn ngày trước ngày hiện tại.");
                txtNgaySinh.requestFocus();
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
                txtNgaySinh.requestFocus();
                return false;
            }
        }
        return true;
    }


    private void actionDangKy() {
        if (!validData()) {
            return;
        }
        String ma = txtMa.getText().trim();
        String sdt = txtSDT.getText().trim();
        String tenDangNhap = txtTenDangNhap.getText().trim();
        String tenKhachHang = txtTenKhachHang.getText().trim();
        String email = txtEmail.getText().trim();
        Date ngaySinh = (Date) txtNgaySinh.getValue();
        String matKhau = new String(txtMatKhau.getPassword()).trim();
        String loaiTaiKhoan = (String) cboLoaiTaiKhoan.getSelectedItem();

        if (ma.isEmpty() || sdt.isEmpty() || tenDangNhap.isEmpty() || tenKhachHang.isEmpty() || email.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        KhachHang khachHang = new KhachHang(ma, sdt, tenDangNhap, tenKhachHang, email, ngaySinh);
        TaiKhoan taiKhoan = new TaiKhoan(ma, matKhau, loaiTaiKhoan);

        KhachHang_DAO khachHangDAO = new KhachHang_DAO();
        TaiKhoan_DAO taiKhoanDAO = new TaiKhoan_DAO();

        KhachHang existingCustomer = khachHangDAO.getKhachHangByMa(ma);
        if (existingCustomer != null) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng đã được đăng ký!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean isCustomerInserted = khachHangDAO.insertKhachHang(khachHang);
        boolean isAccountInserted = taiKhoanDAO.themTaiKhoan(taiKhoan);

        if (isCustomerInserted && isAccountInserted) {
            JOptionPane.showMessageDialog(this, "Đăng ký thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Đăng ký thất bại! Vui lòng thử lại.", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtMa.setText("");
        txtSDT.setText("");
        txtTenDangNhap.setText("");
        txtTenKhachHang.setText("");
        txtEmail.setText("");
        txtNgaySinh.setValue(new Date());
        txtMatKhau.setText("");
        cboLoaiTaiKhoan.setSelectedIndex(0);
    }
}
