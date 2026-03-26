package ui;

import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import entity.KhachHang;
import entity.NhanVien;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TimKiemKhachHang_GD extends JPanel {

    

    private DefaultTableModel model;
    private JTable table;

    private JTextField txtMaKh;
    private JTextField txtSDT;
    private JTextField txtTenDangNhap;
    private JTextField txtTenKhachHang;
    private JTextField txtEmail;
    private JSpinner spnNgaySinh;

    private JButton btnTim, btnXoaTrang, btnLamMoi;

    public TimKiemKhachHang_GD() {
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

        JLabel titleLabel = new JLabel("Tìm kiếm khách hàng", JLabel.CENTER);
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
        Box b1, b2, b3, b4;

        JLabel lblMaKH, lblSDT, lblTenDangNhap, lblTenKhachHang, lblEmail, lblNgaySinh;

        b.add(b1 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b1.add(lblMaKH = new JLabel("Mã khách hàng:"));
        b1.add(txtMaKh = new JTextField());
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
        txtMaKh.setPreferredSize(textFieldSize);
        txtSDT.setPreferredSize(textFieldSize);
        txtTenDangNhap.setPreferredSize(textFieldSize);
        txtTenKhachHang.setPreferredSize(textFieldSize);
        txtEmail.setPreferredSize(textFieldSize);

        panelCenter.add(b);

        add(panelCenter, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center alignment

        btnTim = new JButton("Tìm");
        btnXoaTrang = new JButton("Xoá trắng");
        btnLamMoi = new JButton("Làm mới");

        btnTim.addActionListener(e -> handleTim());
        btnXoaTrang.addActionListener(e -> handleXoaTrang());
        btnLamMoi.addActionListener(e -> handLamMoi());

        Dimension buttonSize = new Dimension(120, 40);

        btnTim.setPreferredSize(buttonSize);
        btnTim.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnTim.setBackground(new Color(0, 123, 255)); // Màu xanh dương (Tìm)
        btnTim.setForeground(Color.WHITE);  // Màu chữ trắng
        btnTim.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2, true)); // Viền màu xanh dương
        btnTim.setOpaque(true);
        btnTim.setBorderPainted(false);

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

        buttonPanel.add(btnTim);
        buttonPanel.add(btnXoaTrang);
        buttonPanel.add(btnLamMoi);

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

            txtMaKh.setText(maKhachHang);
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
        model.setRowCount(0);

        KhachHang_DAO khachHangDAO = new KhachHang_DAO();
        List<KhachHang> khachHangs = khachHangDAO.getAllKhachHang();

        for (KhachHang khachHang : khachHangs) {
            Object[] row = new Object[6];
            row[0] = khachHang.getMaKhachHang();
            row[1] = khachHang.getSDT();
            row[2] = khachHang.getTenDangNhap();
            row[3] = khachHang.getTenKhachHang();
            row[4] = khachHang.getEmail();
            row[5] = khachHang.getNgaySinh();

            model.addRow(row);
        }
    }

    private void handleXoaTrang() {
        txtMaKh.setText("");
        txtSDT.setText("");
        txtTenDangNhap.setText("");
        txtTenKhachHang.setText("");
        txtEmail.setText("");

        spnNgaySinh.setValue(new java.util.Date());
    }

    private void handLamMoi() {
        handleXoaTrang();
        loadDataKhachHang();
    }


    private void handleTim() {
        String maKhachHang = txtMaKh.getText().trim();
        String sdt = txtSDT.getText().trim();
        String tenKhachHang = txtTenKhachHang.getText().trim();
        String tenDangNhap = txtTenDangNhap.getText().trim();
        String email = txtEmail.getText().trim();
        Date ngaySinh = null;

        ngaySinh = (Date) spnNgaySinh.getValue();

        KhachHang_DAO khachHangDAO = new KhachHang_DAO();
        List<KhachHang> khachHangList = khachHangDAO.searchKhachHang(maKhachHang, sdt, tenKhachHang, tenDangNhap, email, ngaySinh);

        model.setRowCount(0);

        for (KhachHang kh : khachHangList) {
            model.addRow(new Object[]{
                    kh.getMaKhachHang(),
                    kh.getSDT(),
                    kh.getTenDangNhap(),
                    kh.getTenKhachHang(),
                    kh.getEmail(),
                    new SimpleDateFormat("yyyy-MM-dd").format(kh.getNgaySinh())
            });
        }

        if (khachHangList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng nào phù hợp với tiêu chí tìm kiếm.");
        }
    }

    //public static void main(String[] args) {
    //    SwingUtilities.invokeLater(() -> {
     //       TimKiemKhachHang_GD frame = new TimKiemKhachHang_GD();
       //     frame.setVisible(true);
       // });
    //}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Tạo JFrame để hiển thị TimKiemKhachHang_GD
                JFrame frame = new JFrame("Tìm kiếm khách hàng");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1600, 900);  // Đặt kích thước cho JFrame
                frame.setLocationRelativeTo(null); // Đảm bảo JFrame xuất hiện ở giữa màn hình

                // Thêm JPanel TimKiemKhachHang_GD vào JFrame
                frame.add(new TimKiemKhachHang_GD());

                frame.setVisible(true);  // Hiển thị JFrame
            }
        });
    }

}
