package ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.toedter.calendar.JDateChooser;

import dao.HoaDon_Dao;
import entity.HoaDon;

public class GD_ThongKeDoanhThu extends JPanel {

    private JDateChooser txtTuNgay, txtDenNgay;
    private JTable table;
    private JLabel lblTongDonHang, lblTongDoanhThu;
    private DefaultTableModel tableModel;
    private HoaDon_Dao hoaDon_Dao;

    public GD_ThongKeDoanhThu() {
        setLayout(new BorderLayout());

        // --- Tiêu đề ---
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(173, 216, 230), 0, getHeight(), Color.WHITE);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        titlePanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("THỐNG KÊ DOANH THU", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titlePanel, BorderLayout.NORTH);

        // --- Main Panel ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Filter Panel ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        filterPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        filterPanel.add(new JLabel("Từ Ngày:"));
        txtTuNgay = new JDateChooser();
        txtTuNgay.setDateFormatString("dd-MM-yyyy");
        txtTuNgay.setPreferredSize(new Dimension(150, 25));
        filterPanel.add(txtTuNgay);

        filterPanel.add(new JLabel("Đến Ngày:"));
        txtDenNgay = new JDateChooser();
        txtDenNgay.setDateFormatString("dd-MM-yyyy");
        txtDenNgay.setPreferredSize(new Dimension(150, 25));
        filterPanel.add(txtDenNgay);

        JButton btnXemThongKe = new JButton("Xem Thống Kê");
        JButton btnInThongKe = new JButton("In Thống Kê");
        JButton btnHuy = new JButton("Hủy");
        styleButton(btnXemThongKe);
        styleButton(btnInThongKe);
        styleButton(btnHuy);

        filterPanel.add(btnXemThongKe);
        filterPanel.add(btnInThongKe);
        filterPanel.add(btnHuy);

        mainPanel.add(filterPanel, BorderLayout.NORTH);

        // --- Table ---
        String[] columnNames = { "Mã hóa đơn", "Tên khách hàng", "Tên nhân viên", "Ngày lập", "Tổng tiền" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        int[] columnWidths = { 100, 200, 200, 150, 150 };
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- Summary Panel ---
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        lblTongDonHang = new JLabel("Tổng đơn hàng: 0");
        lblTongDonHang.setFont(new Font("Arial", Font.BOLD, 14));
        lblTongDoanhThu = new JLabel("Tổng doanh thu: 0 VND");
        lblTongDoanhThu.setFont(new Font("Arial", Font.BOLD, 14));
        lblTongDoanhThu.setForeground(Color.RED);
        summaryPanel.add(lblTongDonHang);
        summaryPanel.add(lblTongDoanhThu);
        mainPanel.add(summaryPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // --- Khởi tạo DAO ---
        hoaDon_Dao = new HoaDon_Dao();

        // Load tất cả hóa đơn khi khởi động
        loadAllData();

        // --- Thêm sự kiện cho các nút ---
        btnXemThongKe.addActionListener(e -> xemThongKe());
        btnInThongKe.addActionListener(e -> inThongKe());
        btnHuy.addActionListener(e -> huyThongKe());
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 30));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 105, 217));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 123, 255));
            }
        });
    }

    private void loadAllData() {
        try {
            tableModel.setRowCount(0);
            List<HoaDon> dsHoaDon = hoaDon_Dao.getAllHoaDon();
            for (HoaDon hd : dsHoaDon) {
                tableModel.addRow(new Object[] {
                        hd.getMaHoaDon(),
                        hd.getKhachHang().getTenKhachHang(),
                        hd.getNhanVien().getTenNhanVien(),
                        hd.getNgayLap(),
                        String.format("%,.0f", hd.getTongTien())
                });
            }
            updateSummary(dsHoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSummary(List<HoaDon> dsHoaDon) {
        int soLuong = dsHoaDon.size();
        double tongDoanhThu = dsHoaDon.stream().mapToDouble(HoaDon::getTongTien).sum();
        lblTongDonHang.setText("Tổng đơn hàng: " + String.format("%,d", soLuong));
        lblTongDoanhThu.setText("Tổng doanh thu: " + String.format("%,.0f VNĐ", tongDoanhThu));
    }

    private void xemThongKe() {
        if (txtTuNgay.getDate() == null || txtDenNgay.getDate() == null) {
            loadAllData();
            return;
        }

        LocalDate tuNgay = txtTuNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate denNgay = txtDenNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (denNgay.isBefore(tuNgay)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau ngày bắt đầu!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<HoaDon> dsHoaDon = hoaDon_Dao.getHoaDonTheoKhoangThoiGian(tuNgay, denNgay);
        tableModel.setRowCount(0);
        for (HoaDon hd : dsHoaDon) {
            tableModel.addRow(new Object[] {
                hd.getMaHoaDon(),
                hd.getKhachHang() != null ? hd.getKhachHang().getTenKhachHang() : "",
                hd.getNhanVien() != null ? hd.getNhanVien().getTenNhanVien() : "", // nếu null thì để trống
                hd.getNgayLap(),
                String.format("%,.0f", hd.getTongTien())
            });
        }
        updateSummary(dsHoaDon);

    }

    private void inThongKe() {
        try {
            if (table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu để in!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            MessageFormat header = new MessageFormat("THỐNG KÊ DOANH THU");
            String tongDonHang = lblTongDonHang.getText().replace("Tổng đơn hàng: ", "");
            String tongDoanhThu = lblTongDoanhThu.getText().replace("Tổng doanh thu: ", "");
            MessageFormat footer = new MessageFormat("Tổng đơn hàng: " + tongDonHang + "     Tổng doanh thu: "
                    + tongDoanhThu);

            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.PORTRAIT);
            set.add(new JobName("Thong ke doanh thu", null));

            boolean complete = table.print(JTable.PrintMode.FIT_WIDTH, header, footer, true, set, true);
            if (complete) {
                JOptionPane.showMessageDialog(this, "In thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi in: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void huyThongKe() {
        txtTuNgay.setDate(null);
        txtDenNgay.setDate(null);
        loadAllData();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý Hóa Đơn");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new GD_ThongKeDoanhThu());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
