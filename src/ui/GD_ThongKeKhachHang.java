package ui;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import com.toedter.calendar.JDateChooser;

import dao.KhachHang_DAO;
import connectDB.ConnectDB;

public class GD_ThongKeKhachHang extends JPanel {
    private JDateChooser dateChooserTuNgay, dateChooserDenNgay;
    private JTable table;
    private JLabel lblTongKhachHang, lblTongDonHang, lblTongDoanhThu;
    private JComboBox<String> cboLoaiThongKe;
    private KhachHang_DAO khachHangDAO;

    public GD_ThongKeKhachHang() {
        connectDatabase();
		khachHangDAO = new KhachHang_DAO();
		initComponents();
        // Load dữ liệu ban đầu
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);
        dateChooserTuNgay.setDate(java.sql.Date.valueOf(firstDayOfMonth));
        dateChooserDenNgay.setDate(java.sql.Date.valueOf(now));
        cboLoaiThongKe.setSelectedItem("Tất cả khách hàng");
        handleXemThongKe();
    }
    private void connectDatabase() {
		try {
			Connection con = ConnectDB.getConnection();
			if (con == null || con.isClosed()) {
				JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Lỗi kết nối database: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
	}

    private void initComponents() {
        setLayout(new BorderLayout());

        // Panel tiêu đề với gradient
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(173, 216, 230), 0, h, Color.WHITE);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Tiêu đề
        JLabel titleLabel = new JLabel("THỐNG KÊ KHÁCH HÀNG", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);

        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel lọc
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        filterPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // Thêm các components cho filter
        filterPanel.add(new JLabel("Từ Ngày:"));
        dateChooserTuNgay = new JDateChooser();
        dateChooserTuNgay.setDateFormatString("dd-MM-yyyy");
        dateChooserTuNgay.setPreferredSize(new Dimension(150, 25));
        filterPanel.add(dateChooserTuNgay);

        filterPanel.add(new JLabel("Đến Ngày:"));
        dateChooserDenNgay = new JDateChooser();
        dateChooserDenNgay.setDateFormatString("dd-MM-yyyy");
        dateChooserDenNgay.setPreferredSize(new Dimension(150, 25));
        filterPanel.add(dateChooserDenNgay);

        filterPanel.add(new JLabel("Chọn loại thống kê:"));
        String[] loaiThongKe = {
            "====Chọn loại thống kê====",
            "Tất cả khách hàng",
            "Top 5 khách hàng",
            "Khách hàng có nhiều đơn nhất"
        };
        cboLoaiThongKe = new JComboBox<>(loaiThongKe);
        cboLoaiThongKe.setPreferredSize(new Dimension(200, 25));
        filterPanel.add(cboLoaiThongKe);

        // Buttons
        JButton btnXemThongKe = new JButton("Xem Thống Kê");
        JButton btnInThongKe = new JButton("In Thống Kê");
        JButton btnHuy = new JButton("Hủy");

        styleButton(btnXemThongKe);
        styleButton(btnInThongKe);
        styleButton(btnHuy);

        filterPanel.add(btnXemThongKe);
        filterPanel.add(btnInThongKe);
        filterPanel.add(btnHuy);

        // Tạo bảng
        String[] columnNames = {
            "Mã khách hàng", "Tên khách hàng", "Số điện thoại",
            "Email", "Ngày sinh", "Số lượng đơn", "Tổng tiền"
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // Đặt chiều rộng cột
        int[] columnWidths = {100, 200, 120, 150, 100, 100, 150};
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        JScrollPane scrollPane = new JScrollPane(table);

        // Panel tóm tắt
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        lblTongKhachHang = new JLabel("Tổng khách hàng: 0");
        lblTongDonHang = new JLabel("Tổng đơn hàng: 0");
        lblTongDoanhThu = new JLabel("Tổng doanh thu: 0 VND");

        Font summaryFont = new Font("Arial", Font.BOLD, 14);
        lblTongKhachHang.setFont(summaryFont);
        lblTongDonHang.setFont(summaryFont);
        lblTongDoanhThu.setFont(summaryFont);
        lblTongDoanhThu.setForeground(Color.RED);

        summaryPanel.add(lblTongKhachHang);
        summaryPanel.add(lblTongDonHang);
        summaryPanel.add(lblTongDoanhThu);

        // Thêm các thành phần vào panel chính
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(summaryPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Thêm sự kiện cho các nút
        btnXemThongKe.addActionListener(e -> handleXemThongKe());
        btnInThongKe.addActionListener(e -> handleInThongKe());
        btnHuy.addActionListener(e -> handleHuy());
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 30));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Thêm hiệu ứng hover
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

    private void handleXemThongKe() {
        if (dateChooserTuNgay.getDate() == null || dateChooserDenNgay.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khoảng thời gian!");
            return;
        }

        if (dateChooserTuNgay.getDate().after(dateChooserDenNgay.getDate())) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc!");
            return;
        }

        String loaiThongKe = (String) cboLoaiThongKe.getSelectedItem();
        if (loaiThongKe.equals("====Chọn loại thống kê====")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại thống kê!");
            return;
        }

        LocalDate tuNgay = dateChooserTuNgay.getDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate denNgay = dateChooserDenNgay.getDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();

        List<Object[]> data;
        switch (loaiThongKe) {
            case "Tất cả khách hàng":
                data = khachHangDAO.getAllKhachHangThongKe(tuNgay, denNgay);
                break;
            case "Top 5 khách hàng":
                data = khachHangDAO.getTop5KhachHang(tuNgay, denNgay);
                break;
            default: // "Khách hàng có nhiều đơn nhất"
                data = khachHangDAO.getKhachHangNhieuDonNhat(tuNgay, denNgay);
                break;
        }

        updateTableData(data);
        updateSummary(data);
    }

    private void updateTableData(List<Object[]> data) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        
        for (Object[] row : data) {
            String tongTien = "0 VND";
            if (row[6] != null) {
                tongTien = String.format("%,.0f VND", ((Number) row[6]).doubleValue());
            }
            
            model.addRow(new Object[] {
                row[0] != null ? row[0] : "", // Mã khách hàng
                row[1] != null ? row[1] : "", // Tên khách hàng
                row[2] != null ? row[2] : "", // SDT
                row[3] != null ? row[3] : "", // Email
                row[4] != null ? dateFormat.format(row[4]) : "", // Ngày sinh
                row[5] != null ? row[5] : 0, // Số lượng đơn
                tongTien // Tổng tiền đã format
            });
        }
    }

    private void updateSummary(List<Object[]> data) {
        int tongKhachHang = data.size();
        int tongDonHang = 0;
        double tongDoanhThu = 0;

        for (Object[] row : data) {
            if (row[5] != null) {
                tongDonHang += ((Number) row[5]).intValue();
            }
            if (row[6] != null) {
                tongDoanhThu += ((Number) row[6]).doubleValue();
            }
        }

        lblTongKhachHang.setText("Tổng khách hàng: " + tongKhachHang);
        lblTongDonHang.setText("Tổng đơn hàng: " + tongDonHang);
        lblTongDoanhThu.setText("Tổng doanh thu: " + String.format("%,.0f VND", tongDoanhThu));
    }

  
    private void handleInThongKe() {
        try {
            if (table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu để in!");
                return;
            }
            table.print();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi in: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleHuy() {
        dateChooserTuNgay.setDate(null);
        dateChooserDenNgay.setDate(null);
        cboLoaiThongKe.setSelectedIndex(0);
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        lblTongKhachHang.setText("Tổng khách hàng: 0");
        lblTongDonHang.setText("Tổng đơn hàng: 0");
        lblTongDoanhThu.setText("Tổng doanh thu: 0 VND");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Thống Kê Khách Hàng");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new GD_ThongKeKhachHang());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
