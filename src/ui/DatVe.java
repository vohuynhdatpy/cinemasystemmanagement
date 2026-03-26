package ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

import com.toedter.calendar.JDateChooser;

import dao.*;
import entity.*;

public class DatVe extends JPanel {

    private static final long serialVersionUID = 1L;

    private DefaultTableModel model;
    private JComboBox<String> showtimeComboBox;
    private JDateChooser dateChooser;
    private JLabel posterLabel;
    private JTextField totalField, nameField, phoneField, seatsField;
    private JLabel seatCountLabel;
    private JPanel ticketInfoPanel;

    private final Phim_DAO phimDAO = new Phim_DAO();
    private final GheDAO gheDAO = new GheDAO();
    private final Ve_DAO veDAO = new Ve_DAO();

    private static final String ROOM_CODE = "HPC01";

    private String maPhim, selectedMovie, selectedDirector, selectedGenre, selectedDuration;
    private double ticketPrice = 60000;
    private final List<String> selectedSeatsList = new ArrayList<>();

    public DatVe() {
        setLayout(new BorderLayout());

        // === TIÊU ĐỀ ===
        GradientPanel titlePanel = new GradientPanel();
        titlePanel.setLayout(new FlowLayout());
        JLabel titleLabel = new JLabel("ĐẶT VÉ XEM PHIM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 44));
        titleLabel.setForeground(Color.BLUE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // === BẢNG PHIM ===
        String[] columnNames = { "Mã phim", "Tên phim", "Đạo diễn", "Thể loại", "Thời lượng" };
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Courier New", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(520, 0));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        loadData();
        if (model.getRowCount() > 0)
            table.setRowSelectionInterval(0, 0);

        // === LEFT PANEL ===
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel choosePanel = new JPanel();
        choosePanel.setLayout(new BoxLayout(choosePanel, BoxLayout.Y_AXIS));
        choosePanel.setBorder(BorderFactory.createTitledBorder("Chọn ngày xem"));

        // Ngày xem
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(new JLabel("Ngày:"));
        dateChooser = new JDateChooser();
        dateChooser.setDate(new java.util.Date());
        dateChooser.setMinSelectableDate(new java.util.Date());
        datePanel.add(dateChooser);
        choosePanel.add(datePanel);

        // Suất xem
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timePanel.add(new JLabel("Suất xem:"));
        showtimeComboBox = new JComboBox<>(new String[] { "18:00 - 20:00", "20:30 - 22:30" });
        timePanel.add(showtimeComboBox);
        choosePanel.add(timePanel);

        // Button chọn ghế
        JButton bookButton = new JButton("Đặt chỗ ngồi");
        bookButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookButton.addActionListener(e -> openSeatSelection());
        choosePanel.add(Box.createVerticalStrut(10));
        choosePanel.add(bookButton);

        leftPanel.add(choosePanel, BorderLayout.NORTH);

        // === THÔNG TIN VÉ ===
        ticketInfoPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        ticketInfoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin vé"));
        ticketInfoPanel.setBackground(new Color(240, 240, 240));
        updateTicketInfo("Chưa chọn", "Chưa chọn", "Chưa chọn", "Chưa chọn", "Chưa chọn", "Chưa chọn");
        leftPanel.add(ticketInfoPanel, BorderLayout.CENTER);
        mainPanel.add(leftPanel, BorderLayout.WEST);

        // === POSTER ===
        JPanel posterPanel = new JPanel();
        posterPanel.setBorder(BorderFactory.createTitledBorder("Poster phim"));
        posterPanel.setPreferredSize(new Dimension(300, 400));
        posterLabel = new JLabel("<No poster>", JLabel.CENTER);
        posterPanel.add(posterLabel);
        mainPanel.add(posterPanel, BorderLayout.EAST);

        // === THANH TOÁN ===
        JPanel paymentPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Thanh toán"));

        paymentPanel.add(new JLabel("Tên khách hàng:"));
        nameField = new JTextField(10);
        paymentPanel.add(nameField);

        paymentPanel.add(new JLabel("Số điện thoại:"));
        phoneField = new JTextField(10);
        paymentPanel.add(phoneField);

        paymentPanel.add(new JLabel("Ghế đã chọn:"));
        seatsField = new JTextField(10);
        seatsField.setEditable(false);
        paymentPanel.add(seatsField);

        paymentPanel.add(new JLabel("Số lượng:"));
        seatCountLabel = new JLabel("0");
        paymentPanel.add(seatCountLabel);

        paymentPanel.add(new JLabel("Tổng tiền:"));
        totalField = new JTextField(10);
        totalField.setEditable(false);
        totalField.setText("0 VND");
        paymentPanel.add(totalField);

        paymentPanel.add(new JLabel(""));
        JButton payButton = new JButton("Thanh toán");
        payButton.setBackground(new Color(0, 128, 0));
        payButton.setForeground(Color.WHITE);
        payButton.addActionListener(e -> nhaphoadon());
        paymentPanel.add(payButton);

        mainPanel.add(paymentPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // === CHỌN PHIM ===
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int modelRow = table.convertRowIndexToModel(row);
                    maPhim = (String) model.getValueAt(modelRow, 0);
                    selectedMovie = (String) model.getValueAt(modelRow, 1);
                    selectedDirector = (String) model.getValueAt(modelRow, 2);
                    selectedGenre = (String) model.getValueAt(modelRow, 3);
                    selectedDuration = model.getValueAt(modelRow, 4).toString();
                    updateTicketInfo(selectedMovie, selectedDirector, selectedGenre, selectedDuration,
                            getSelectedDate(), getSelectedShowtime());
                    updatePoster(maPhim);
                }
            }
        });

        dateChooser.getDateEditor().addPropertyChangeListener("date", evt -> updateTotalPriceWithSeats(selectedSeatsList.size()));
    }

    // === CHỌN GHẾ ===
    private void openSeatSelection() {
        if (maPhim == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phim!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JFrame frame = new JFrame("Chọn ghế - " + selectedMovie);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DatChoNgoi datChoNgoi = new DatChoNgoi(ROOM_CODE);
        frame.add(datChoNgoi);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                List<String> selected = datChoNgoi.getSelectedSeats();
                selectedSeatsList.clear();
                selectedSeatsList.addAll(selected);
                if (!selected.isEmpty()) {
                    seatsField.setText(String.join(", ", selected));
                    seatCountLabel.setText(String.valueOf(selected.size()));
                    updateTotalPriceWithSeats(selected.size());
                    updateTicketInfo(selectedMovie, selectedDirector, selectedGenre, selectedDuration,
                            getSelectedDate(), getSelectedShowtime());
                } else {
                    seatsField.setText("");
                    seatCountLabel.setText("0");
                    updateTotalPriceWithSeats(0);
                }
            }
        });
    }

    // === TẠO HÓA ĐƠN ===
    public HoaDon taohoadon(String mave) {
        String maHD = "HD" + String.valueOf(System.currentTimeMillis()).substring(0, 8);
        String tongtien = totalField.getText().replaceAll("[^0-9.]", "");
        Double tongTien = Double.parseDouble(tongtien);

        KhachHang_DAO khDAO = new KhachHang_DAO();
        String maKH = khDAO.getMaKhachHangBySDT(phoneField.getText());
        KhachHang kh;

        if (maKH == null) {
            maKH = "KH" + String.valueOf(System.currentTimeMillis()).substring(0, 8);
            kh = new KhachHang(maKH);
            kh.setSDT(phoneField.getText());
            kh.setTenKhachHang(nameField.getText());
            kh.setNgaySinh(new java.util.Date());
            khDAO.insertKhachHang(kh);
        } else {
            kh = new KhachHang(maKH);
            kh.setSDT(phoneField.getText());
            kh.setTenKhachHang(nameField.getText());
        }

        Ve ve = new Ve(mave);
        NhanVien nv = new NhanVien();

        return new HoaDon(maHD, LocalDate.now(), tongTien, kh, nv, ve);
    }

    // === XỬ LÝ THANH TOÁN ===
    private String handlePayment() {
        String customerName = nameField.getText().trim();
        String phoneNumber = phoneField.getText().trim();

        if (customerName.isEmpty() || phoneNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Tên và SĐT.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (selectedSeatsList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ghế!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (maPhim == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phim!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        String maVe = "VE" + String.valueOf(System.currentTimeMillis()).substring(0, 8);
        Date ngayChieu = new Date(dateChooser.getDate().getTime());
        String gioBatDauStr = getSelectedShowtime().split(" - ")[0] + ":00";
        Time gioBatDau = Time.valueOf(gioBatDauStr);
        Time gioKetThuc = new Time(gioBatDau.getTime() + 2 * 60 * 60 * 1000);

        double total = ticketPrice * selectedSeatsList.size();
        if (isWeekend(dateChooser.getDate()))
            total *= 1.2;

        boolean success = veDAO.luuThongTinVe(
                maVe, maPhim, selectedMovie, selectedDirector, selectedGenre,
                Integer.parseInt(selectedDuration), String.join(",", selectedSeatsList),
                ngayChieu, gioBatDau, gioKetThuc, ROOM_CODE, total, customerName
        );

        if (success) {
        	LichChieu_DAO lcdao = new LichChieu_DAO();
        	String malc = lcdao.getMaLichChieuByMaPhim(maPhim);
        	Ve_DAO vedao = new Ve_DAO();
        	vedao.luuVeM(maVe, malc,  phoneNumber,String.join(",", selectedSeatsList), total);
            inHoaDon(maVe, customerName, phoneNumber, getSelectedShowtime(), total);
            JOptionPane.showMessageDialog(this, "Thanh toán thành công! Mã vé: " + maVe,
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            return maVe;
            //resetUI();
        } else {
            JOptionPane.showMessageDialog(this, "Lưu vé thất bại (DB).", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    public void nhaphoadon()
    {
    	String mave = handlePayment();
    	if(mave==null)
    	{
    		return;
    	}
    	HoaDon hd = taohoadon(mave);
        HoaDon_Dao a = new HoaDon_Dao();
        a.insertHoaDon(hd);
        resetUI();
    }
    // === IN HÓA ĐƠN ===
    private void inHoaDon(String maVe, String tenKH, String sdt, String suat, double tongTien) {
        String fileName = "hoa_don_" + maVe + ".txt";
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write("=== HÓA ĐƠN ĐẶT VÉ ===\n");
            fw.write("Mã vé: " + maVe + "\n");
            fw.write("Phim: " + selectedMovie + "\n");
            fw.write("Ghế: " + String.join(", ", selectedSeatsList) + "\n");
            fw.write("Ngày: " + getSelectedDate() + "\n");
            fw.write("Suất: " + suat + "\n");
            fw.write("Khách: " + tenKH + " - " + sdt + "\n");
            fw.write("Tổng tiền: " + String.format("%.0f", tongTien) + " VND\n");
            fw.write("Cảm ơn quý khách!\n");
            Desktop.getDesktop().open(new File(fileName));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi in hóa đơn: " + ex.getMessage());
        }
    }

    // === CÁC HÀM HỖ TRỢ ===
    private void updateTotalPriceWithSeats(int seatCount) {
        if (seatCount == 0) {
            totalField.setText("0 VND");
            return;
        }
        double base = ticketPrice * seatCount;
        if (isWeekend(dateChooser.getDate()))
            base *= 1.2;
        totalField.setText(String.format("%.0f VND", base));
    }

    private boolean isWeekend(java.util.Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        return day == Calendar.SATURDAY || day == Calendar.SUNDAY;
    }

    private String getSelectedDate() {
        return dateChooser.getDate() != null ? new SimpleDateFormat("dd/MM/yyyy").format(dateChooser.getDate()) : "Chưa chọn";
    }

    private String getSelectedShowtime() {
        String s = (String) showtimeComboBox.getSelectedItem();
        return s != null ? s.split(" - ")[0] : "Chưa chọn";
    }

    private void updateTicketInfo(String ten, String dd, String tl, String thoiLuong, String ngay, String suat) {
        ticketInfoPanel.removeAll();
        ticketInfoPanel.add(createLabel("Tên phim:"));
        ticketInfoPanel.add(createLabel(ten));
        ticketInfoPanel.add(createLabel("Đạo diễn:"));
        ticketInfoPanel.add(createLabel(dd));
        ticketInfoPanel.add(createLabel("Thể loại:"));
        ticketInfoPanel.add(createLabel(tl));
        ticketInfoPanel.add(createLabel("Thời lượng:"));
        ticketInfoPanel.add(createLabel(thoiLuong + " phút"));
        ticketInfoPanel.add(createLabel("Ngày chiếu:"));
        ticketInfoPanel.add(createLabel(ngay));
        ticketInfoPanel.add(createLabel("Suất chiếu:"));
        ticketInfoPanel.add(createLabel(suat));
        ticketInfoPanel.revalidate();
        ticketInfoPanel.repaint();
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        return lbl;
    }

    private void updatePoster(String maPhim) {
        if (maPhim == null) {
            posterLabel.setIcon(null);
            posterLabel.setText("<No poster>");
            return;
        }
        String imagePath = phimDAO.getPosterByMaPhim(maPhim);
        if (imagePath == null || imagePath.trim().isEmpty()) {
            posterLabel.setIcon(null);
            posterLabel.setText("<No poster>");
            return;
        }
        java.net.URL imgURL = getClass().getClassLoader().getResource(imagePath);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage().getScaledInstance(295, 400, Image.SCALE_SMOOTH);
            posterLabel.setIcon(new ImageIcon(img));
            posterLabel.setText("");
        } else {
            posterLabel.setText("<No poster>");
        }
    }

    private void loadData() {
        List<Phim> ds = phimDAO.getAllPhim();
        model.setRowCount(0);
        for (Phim p : ds) {
            model.addRow(new Object[] { p.getMaPhim(), p.getTenPhim(), p.getDaoDien(), p.getTheLoai(), p.getThoiLuong() });
        }
    }

    private void resetUI() {
        nameField.setText("");
        phoneField.setText("");
        seatsField.setText("");
        seatCountLabel.setText("0");
        totalField.setText("0 VND");
        selectedSeatsList.clear();
        maPhim = null;
        updatePoster(null);
        updateTicketInfo("Chưa chọn", "Chưa chọn", "Chưa chọn", "Chưa chọn", "Chưa chọn", "Chưa chọn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Đặt vé xem phim");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1400, 700);
            frame.add(new DatVe());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
