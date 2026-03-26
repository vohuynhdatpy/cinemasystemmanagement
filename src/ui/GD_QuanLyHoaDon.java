package ui;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.HoaDon_Dao;
import entity.HoaDon;

public class GD_QuanLyHoaDon extends JPanel {

    private JTextField txtMaHD, txtTenNV, txtTenKH;
    private JDateChooser txtNgayLap;
    private JLabel lblTongDonHang, lblTongDoanhThu;
    private JTable table;
    private DefaultTableModel tableModel;
    private JRadioButton rdoTheoNgayLapTangDan, rdoTheoNgayLapGiamDan;
    private JRadioButton rdoTheoMaHDTangDan, rdoTheoMaHDGiamDan;
    private JRadioButton rdoTheoTongTienTangDan, rdoTheoTongTienGiamDan;
    private JButton btnTim, btnHuy;
    private HoaDon_Dao hoaDon_dao;
    private ButtonGroup sortButtonGroup;

    public GD_QuanLyHoaDon() {
        setLayout(new BorderLayout());
        hoaDon_dao = new HoaDon_Dao();

        createGUI();
        addEventListeners();

        // Load dữ liệu ban đầu
        loadTableData(hoaDon_dao.getAllHoaDon());
    }

    private void createGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        mainPanel.add(createTitlePanel(), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);
        mainPanel.add(createStatisticsPanel(), BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0,0,new Color(173,216,230),0,h,Color.WHITE);
                g2d.setPaint(gp);
                g2d.fillRect(0,0,w,h);
            }
        };
        titlePanel.setLayout(new BorderLayout());
        JLabel lblTitle = new JLabel("QUẢN LÝ HÓA ĐƠN", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 50));
        lblTitle.setForeground(Color.BLUE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
        titlePanel.add(lblTitle, BorderLayout.CENTER);
        return titlePanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(10,10));
        JPanel topPanel = new JPanel(new GridLayout(1,2,10,0));
        topPanel.add(createSearchPanel());
        topPanel.add(createSortPanel());
        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(createTablePanel(), BorderLayout.CENTER);
        return contentPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),
                "Tìm kiếm hóa đơn", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel fieldsPanel = new JPanel(new GridLayout(2,4,5,5));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        fieldsPanel.add(new JLabel("Mã hóa đơn:"));
        fieldsPanel.add(txtMaHD = new JTextField());
        fieldsPanel.add(new JLabel("Ngày lập:"));
        fieldsPanel.add(txtNgayLap = new JDateChooser());
        txtNgayLap.setDateFormatString("yyyy-MM-dd");
        fieldsPanel.add(new JLabel("Tên nhân viên:"));
        fieldsPanel.add(txtTenNV = new JTextField());
        fieldsPanel.add(new JLabel("Tên khách hàng:"));
        fieldsPanel.add(txtTenKH = new JTextField());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnTim = new JButton("Tìm");
        btnHuy = new JButton("Hủy");
        styleButtons();
        buttonPanel.add(btnTim);
        buttonPanel.add(btnHuy);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx=1.0; gbc.fill=GridBagConstraints.BOTH;
        searchPanel.add(fieldsPanel, gbc);
        gbc.gridy = 1; gbc.weighty=0;
        searchPanel.add(buttonPanel, gbc);
        return searchPanel;
    }

    private JPanel createSortPanel() {
        JPanel sortPanel = new JPanel(new GridLayout(3,2,5,5));
        sortPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),
                "Sắp xếp danh sách hóa đơn", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));

        rdoTheoNgayLapTangDan = new JRadioButton("Ngày lập tăng dần");
        rdoTheoNgayLapGiamDan = new JRadioButton("Ngày lập giảm dần");
        rdoTheoMaHDTangDan = new JRadioButton("Mã HD tăng dần");
        rdoTheoMaHDGiamDan = new JRadioButton("Mã HD giảm dần");
        rdoTheoTongTienTangDan = new JRadioButton("Tổng tiền tăng dần");
        rdoTheoTongTienGiamDan = new JRadioButton("Tổng tiền giảm dần");

        sortButtonGroup = new ButtonGroup();
        sortButtonGroup.add(rdoTheoNgayLapTangDan); sortButtonGroup.add(rdoTheoNgayLapGiamDan);
        sortButtonGroup.add(rdoTheoMaHDTangDan); sortButtonGroup.add(rdoTheoMaHDGiamDan);
        sortButtonGroup.add(rdoTheoTongTienTangDan); sortButtonGroup.add(rdoTheoTongTienGiamDan);

        sortPanel.add(rdoTheoNgayLapTangDan); sortPanel.add(rdoTheoNgayLapGiamDan);
        sortPanel.add(rdoTheoMaHDTangDan); sortPanel.add(rdoTheoMaHDGiamDan);
        sortPanel.add(rdoTheoTongTienTangDan); sortPanel.add(rdoTheoTongTienGiamDan);

        return sortPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),
                "Danh sách hóa đơn", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 12)));

        String[] columns = { "Mã HD", "Tên KH", "Tên NV", "Ngày lập", "Tổng tiền" };
        tableModel = new DefaultTableModel(columns,0) {
            @Override public boolean isCellEditable(int row,int column){return false;}
        };

        table = new JTable(tableModel);
        configureTable();
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createStatisticsPanel() {
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,5));
        lblTongDonHang = new JLabel("Tổng số đơn hàng: 0");
        lblTongDoanhThu = new JLabel("Tổng doanh thu: 0 VNĐ");
        lblTongDonHang.setFont(new Font("Arial", Font.BOLD, 14));
        lblTongDoanhThu.setFont(new Font("Arial", Font.BOLD, 14));
        statsPanel.add(lblTongDonHang);
        statsPanel.add(lblTongDoanhThu);
        return statsPanel;
    }

    private void configureTable() {
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0;i<table.getColumnCount();i++) table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    private void styleButtons() {
        Font font = new Font("Arial", Font.BOLD, 12);
        Dimension size = new Dimension(80,30);
        btnTim.setFont(font); btnHuy.setFont(font);
        btnTim.setPreferredSize(size); btnHuy.setPreferredSize(size);
        btnTim.setBackground(new Color(0,123,255)); btnTim.setForeground(Color.WHITE);
        btnHuy.setBackground(new Color(220,53,69)); btnHuy.setForeground(Color.WHITE);
        addHoverEffect(btnTim); addHoverEffect(btnHuy);
    }

    private void addHoverEffect(JButton button){
        button.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e){button.setBackground(button.getBackground().darker());}
            @Override public void mouseExited(MouseEvent e){button.setBackground(button.getBackground().brighter());}
        });
    }

    private void addEventListeners() {
        ActionListener sortListener = e -> {
            List<HoaDon> ds = new ArrayList<>();
            for(int i=0;i<tableModel.getRowCount();i++){
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(tableModel.getValueAt(i,0).toString());
                ds.add(hd);
            }
            JRadioButton rb = (JRadioButton)e.getSource();
            if(rb.isSelected()){
                if(rb==rdoTheoNgayLapTangDan) hoaDon_dao.sapXepTheoNgayLap(ds,true);
                else if(rb==rdoTheoNgayLapGiamDan) hoaDon_dao.sapXepTheoNgayLap(ds,false);
                else if(rb==rdoTheoMaHDTangDan) hoaDon_dao.sapXepTheoMaHD(ds,true);
                else if(rb==rdoTheoMaHDGiamDan) hoaDon_dao.sapXepTheoMaHD(ds,false);
                else if(rb==rdoTheoTongTienTangDan) hoaDon_dao.sapXepTheoTongTien(ds,true);
                else if(rb==rdoTheoTongTienGiamDan) hoaDon_dao.sapXepTheoTongTien(ds,false);
                loadTableData(ds);
            }
        };
        rdoTheoNgayLapTangDan.addActionListener(sortListener); rdoTheoNgayLapGiamDan.addActionListener(sortListener);
        rdoTheoMaHDTangDan.addActionListener(sortListener); rdoTheoMaHDGiamDan.addActionListener(sortListener);
        rdoTheoTongTienTangDan.addActionListener(sortListener); rdoTheoTongTienGiamDan.addActionListener(sortListener);

        btnTim.addActionListener(e -> timKiem());
        btnHuy.addActionListener(e -> {
            xoaRong();
            loadTableData(hoaDon_dao.getAllHoaDon());
        });
    }

    private void timKiem(){
        String maHD = txtMaHD.getText().trim();
        String tenNV = txtTenNV.getText().trim();
        String tenKH = txtTenKH.getText().trim();
        LocalDate ngayLap = null;
        if(txtNgayLap.getDate()!=null){
            ngayLap = txtNgayLap.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        List<HoaDon> ds = hoaDon_dao.getAllHoaDon();
        List<HoaDon> dsLoc = new ArrayList<>();
        for(HoaDon hd: ds){
            boolean match = true;
            if(!maHD.isEmpty() && !hd.getMaHoaDon().contains(maHD)) match=false;
            if(!tenNV.isEmpty() && !hd.getNhanVien().getTenNhanVien().contains(tenNV)) match=false;
            if(!tenKH.isEmpty() && !hd.getKhachHang().getTenKhachHang().contains(tenKH)) match=false;
            if(ngayLap!=null && !hd.getNgayLap().equals(ngayLap)) match=false;
            if(match) dsLoc.add(hd);
        }
        loadTableData(dsLoc);
    }

    private void xoaRong(){
        txtMaHD.setText(""); txtTenNV.setText(""); txtTenKH.setText(""); txtNgayLap.setDate(null);
        sortButtonGroup.clearSelection();
    }

    private void loadTableData(List<HoaDon> list){
        tableModel.setRowCount(0);
        for(HoaDon hd: list){
            tableModel.addRow(new Object[]{
                hd.getMaHoaDon(),
                hd.getKhachHang().getTenKhachHang(),
                hd.getNhanVien().getTenNhanVien(),
                hd.getNgayLap(),
                hd.getTongTien()
            });
        }
        updateStatistics(list.size(), hoaDon_dao.tinhTongDoanhThu(list));
    }

    private void updateStatistics(int tongDonHang, double tongDoanhThu){
        lblTongDonHang.setText("Tổng số đơn hàng: "+tongDonHang);
        lblTongDoanhThu.setText("Tổng doanh thu: "+String.format("%,.0f VNĐ", tongDoanhThu));
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch(Exception e){ e.printStackTrace(); }
            JFrame frame = new JFrame("Quản lý hóa đơn");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new GD_QuanLyHoaDon());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
