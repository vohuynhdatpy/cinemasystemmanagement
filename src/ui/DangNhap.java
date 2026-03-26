package ui;

import dao.TaiKhoan_DAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DangNhap extends JFrame implements ActionListener {
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap, btnDangKy;

    public DangNhap() {
        setTitle("Đăng nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 530);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Load and scale image
        ImageIcon originalIcon = new ImageIcon("src/ui/icon/logo-cgv.jpg"); // Replace with your image path
        Image scaledImage = originalIcon.getImage().getScaledInstance(500, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Image Panel
        JLabel lblImage = new JLabel(scaledIcon);
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setPreferredSize(new Dimension(600, 150));
        add(lblImage, BorderLayout.NORTH);

        // Login Form Panel
        JPanel panelForm = new JPanel();

        Box b = Box.createVerticalBox();
        Box b1, b2, b3, b4, b5;

        JLabel lblTieuDe, lblTenDangNhap, lblMatKhau, lblDK;

        b.add(b1 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b1.add(lblTieuDe = new JLabel("ĐĂNG NHẬP"));
        lblTieuDe.setFont(new Font("Comic Sans MS", Font.PLAIN, 23));

        b.add(b2 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b2.add(lblTenDangNhap = new JLabel("Tên đăng nhập: "));
        b2.add(txtTenDangNhap = new JTextField());

        b.add(b3 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b3.add(lblMatKhau = new JLabel("Mật khẩu: "));
        b3.add(txtMatKhau = new JPasswordField());

        b.add(b4 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b4.add(btnDangNhap = new JButton("Đăng nhập"));

        b.add(b5 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));
        b5.add(lblDK = new JLabel("Bạn chưa có tài khoản? "));
        b5.add(btnDangKy = new JButton("Đăng ký"));

        lblTenDangNhap.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblMatKhau.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblDK.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblMatKhau.setPreferredSize(lblTenDangNhap.getPreferredSize());
        txtTenDangNhap.setPreferredSize(new Dimension(250, 30));
        txtMatKhau.setPreferredSize(new Dimension(250, 30));

        styleButton(btnDangNhap);
        styleButton_2(btnDangKy);

        panelForm.add(b);

        add(panelForm, BorderLayout.CENTER);

        btnDangKy.addActionListener(this);
        btnDangNhap.addActionListener(this);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DangNhap dn = new DangNhap();
            dn.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDangKy) {
            DangKy dk = new DangKy();
            dk.setVisible(true);
            dispose();
        } else if (e.getSource() == btnDangNhap) {
            String tenDangNhap = txtTenDangNhap.getText();
            String matKhau = txtMatKhau.getText();

            TaiKhoan_DAO taiKhoanDAO = new TaiKhoan_DAO();
            if (taiKhoanDAO.xacThucTaiKhoan(tenDangNhap, matKhau)) {
                MenuRapChieuPhim menuRapChieuPhim = new MenuRapChieuPhim();
                menuRapChieuPhim.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
