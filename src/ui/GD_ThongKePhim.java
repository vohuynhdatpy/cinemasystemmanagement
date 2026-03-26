package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.Phim_DAO;

public class GD_ThongKePhim extends JPanel {
	private JDateChooser dateChooserTuNgay, dateChooserDenNgay;
	private JTable table;
	private JLabel lblTongPhim, lblTongDonHang, lblTongDoanhThu;
	private JComboBox<String> cboLoaiThongKe;
	private DefaultTableModel tableModel;
	private Phim_DAO phimDAO;

	public GD_ThongKePhim() {
		connectDatabase();
		phimDAO = new Phim_DAO();
		initComponents();
        // Load dữ liệu ban đầu
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);
        dateChooserTuNgay.setDate(java.sql.Date.valueOf(firstDayOfMonth));
        dateChooserDenNgay.setDate(java.sql.Date.valueOf(now));
        cboLoaiThongKe.setSelectedItem("Tất cả phim");
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
		JLabel titleLabel = new JLabel("THỐNG KÊ PHIM", JLabel.CENTER);
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
		String[] loaiThongKe = { "====Chọn loại thống kê====", "Tất cả phim", "Top 5 phim được xem nhiều nhất",
				"Phim có doanh thu cao nhất" };
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
		String[] columnNames = { "Mã phim", "Tên phim", "Loại phim", "Thời lượng", "Số lượng đơn", "Tổng tiền" };
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

		// Đặt chiều rộng cột
		int[] columnWidths = { 80, 300, 100, 100, 100, 150 };
		for (int i = 0; i < columnWidths.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
		}

		JScrollPane scrollPane = new JScrollPane(table);

		// Panel tóm tắt
		JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
		lblTongPhim = new JLabel("Tổng phim: 0");
		lblTongDonHang = new JLabel("Tổng đơn hàng: 0");
		lblTongDoanhThu = new JLabel("Tổng doanh thu: 0 VND");

		Font summaryFont = new Font("Arial", Font.BOLD, 14);
		lblTongPhim.setFont(summaryFont);
		lblTongDonHang.setFont(summaryFont);
		lblTongDoanhThu.setFont(summaryFont);
		lblTongDoanhThu.setForeground(Color.RED);

		summaryPanel.add(lblTongPhim);
		summaryPanel.add(lblTongDonHang);
		summaryPanel.add(lblTongDoanhThu);

		mainPanel.add(filterPanel, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(summaryPanel, BorderLayout.SOUTH);

		add(mainPanel, BorderLayout.CENTER);

		// Thêm sự kiện cho các nút
		btnXemThongKe.addActionListener(e -> handleXemThongKe());
		btnInThongKe.addActionListener(e -> handleInThongKe());
		btnHuy.addActionListener(e -> handleHuy());

		formatTable();
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

	private void formatTable() {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		// Center align for most columns
		for (int i = 0; i < table.getColumnCount(); i++) {
			if (i != 1 && i != 5) { // Skip name and total amount columns
				table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
		}

		// Right align for total amount column
		table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
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

		LocalDate tuNgay = dateChooserTuNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate denNgay = dateChooserDenNgay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		List<Object[]> data;
		switch (loaiThongKe) {
		case "Tất cả phim":
			data = phimDAO.getAllPhimThongKe(tuNgay, denNgay);
			break;
		case "Top 5 phim được xem nhiều nhất":
			data = phimDAO.getTop5PhimXemNhieuNhat(tuNgay, denNgay);
			break;
		case "Phim có doanh thu cao nhất":
			data = phimDAO.getPhimDoanhThuCaoNhat(tuNgay, denNgay);
			break;
		default:
			return;
		}

		updateTableData(data);
		updateSummary(data);
	}

	private void updateTableData(List<Object[]> data) {
		tableModel.setRowCount(0);
		for (Object[] row : data) {
			tableModel.addRow(new Object[] { row[0], // Mã phim
					row[1], // Tên phim
					row[2], // Loại phim
					row[3], // Thời lượng
					row[4], // Số lượng đơn
					String.format("%,.0f VND", ((Number) row[5]).doubleValue()) // Tổng tiền
			});
		}
		formatTable();
	}

	private void updateSummary(List<Object[]> data) {
		int tongPhim = data.size();
		int tongDonHang = 0;
		double tongDoanhThu = 0;

		for (Object[] row : data) {
			tongDonHang += ((Number) row[4]).intValue();
			tongDoanhThu += ((Number) row[5]).doubleValue();
		}

		lblTongPhim.setText("Tổng phim: " + tongPhim);
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
			JOptionPane.showMessageDialog(this, "Lỗi khi in: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void handleHuy() {
		dateChooserTuNgay.setDate(null);
		dateChooserDenNgay.setDate(null);
		cboLoaiThongKe.setSelectedIndex(0);
		tableModel.setRowCount(0);
		lblTongPhim.setText("Tổng phim: 0");
		lblTongDonHang.setText("Tổng đơn hàng: 0");
		lblTongDoanhThu.setText("Tổng doanh thu: 0 VND");
		formatTable();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

				JFrame frame = new JFrame("Thống kê phim");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(new GD_ThongKePhim());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Lỗi khởi động: " + e.getMessage(), "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
