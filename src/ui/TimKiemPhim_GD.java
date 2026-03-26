package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDayChooser;

import dao.Phim_DAO;
import entity.Phim;

public class TimKiemPhim_GD extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DefaultTableModel model;

	// Lấy dữ liệu từ cơ sở dữ liệu
	Phim_DAO phimDAO = new Phim_DAO();
	List<Phim> originalPhimList = phimDAO.getAllPhim();
	List<Phim> dsPhim = new ArrayList<>(originalPhimList);
	private JCalendar calendar;
	private JPanel showtimeArea;
	private Map<String, List<String>> showtimesByDate = new HashMap<>(); // Lưu trữ các suất chiếu cho từng ngày
	// Khai báo biến todayMoviesPanel và todayMoviesModel
	private JPanel todayMoviesPanel;

	public TimKiemPhim_GD() {
		setLayout(new BorderLayout());
		calendar = new JCalendar();

		// Tiêu đề
		GradientPanel titlePanel = new GradientPanel();
		titlePanel.setLayout(new FlowLayout());
		JLabel titleLabel = new JLabel("Danh sách phim", JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 44));
		titleLabel.setForeground(Color.BLACK); // Màu chữ
		titlePanel.add(titleLabel);
		add(titlePanel, BorderLayout.NORTH);

		// Tạo panel để thêm khoảng cách dọc trên bảng
		JPanel spacerPanel = new JPanel();
		spacerPanel.setPreferredSize(new Dimension(0, 40)); // Điều chỉnh chiều cao theo nhu cầu
		add(spacerPanel, BorderLayout.CENTER); // Thêm panel rỗng

		// Bảng phim
		String[] columnNames = { "Mã phim", "Tên phim", "Đạo diễn", "Thể loại", "Thời lượng" };
		model = new DefaultTableModel(columnNames, 0);

		// Thêm dữ liệu vào model từ danh sách phim
		for (Phim phim : dsPhim) {
			model.addRow(new Object[] { phim.getMaPhim(), phim.getTenPhim(), phim.getDaoDien(), phim.getTheLoai(),
					phim.getThoiLuong() });
		}

		JTable table = new JTable(model);
		table.setRowHeight(30); // Đặt chiều cao cho hàng của bảng

		// Đặt cỡ chữ cho bảng
		table.setFont(new Font("Courier New", Font.PLAIN, 16)); // Thay đổi kích thước chữ ở đây

		// Đặt cỡ chữ cho tiêu đề cột
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Thay đổi kích thước chữ tiêu đề cột

		JScrollPane scrollPane = new JScrollPane(table);

		// Thêm bảng vào giữa, dưới panel rỗng
		add(scrollPane, BorderLayout.CENTER); // Thêm bảng vào scroll pane
		// render lich chieu tuong ung voi phim
		Phim_DAO phimDAO = new Phim_DAO(); // Khởi tạo đối tượng Phim_DAO

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						String movieId = table.getValueAt(selectedRow, 0).toString(); // Lấy mã phim từ cột đầu tiên
						System.out.println("Selected Movie ID: " + movieId);

						// Gọi phương thức getShowtimeDatesByMovieId từ đối tượng phimDAO
						List<Date> showtimeDates = phimDAO.getShowtimeDatesByMovieId(movieId); // Lấy ngày chiếu
						highlightShowtimeDates(showtimeDates, movieId); // Đánh dấu các ngày trên lịch
					}
				}
			}
		});

		// Panel cho phần sắp xếp và tìm kiếm
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS)); // Xếp chồng các thành phần theo chiều dọc
		sidePanel.setBorder(BorderFactory.createTitledBorder("")); // Tiêu đề cho panel

		// Phần sắp xếp
		JPanel sortPanel = new JPanel();
		sortPanel.setLayout(new GridLayout(3, 2)); // Đặt bố cục thành 3 hàng và 2 cột
		sortPanel.setBorder(BorderFactory.createTitledBorder("Sắp xếp")); // Tiêu đề cho panel sắp xếp
		sortPanel.setPreferredSize(new Dimension(200, 100)); // Đặt kích thước ưa thích

		// Tạo các JRadioButton cho các tùy chọn sắp xếp
		JRadioButton sortByNameAsc = new JRadioButton("Theo tên tăng dần");
		JRadioButton sortByNameDesc = new JRadioButton("Theo tên giảm dần");
		JRadioButton sortByIdAsc = new JRadioButton("Theo mã phim tăng dần");
		JRadioButton sortByIdDesc = new JRadioButton("Theo mã phim giảm dần");
		JRadioButton sortByDurationAsc = new JRadioButton("Theo thời lượng tăng dần");
		JRadioButton sortByDurationDesc = new JRadioButton("Theo thời lượng giảm dần");

		// Nhóm các JRadioButton
		ButtonGroup sortGroup = new ButtonGroup();
		sortGroup.add(sortByNameAsc);
		sortGroup.add(sortByNameDesc);
		sortGroup.add(sortByIdAsc);
		sortGroup.add(sortByIdDesc);
		sortGroup.add(sortByDurationAsc);
		sortGroup.add(sortByDurationDesc);

		// Thêm các JRadioButton vào sortPanel
		sortPanel.add(sortByNameAsc);
		sortPanel.add(sortByNameDesc);
		sortPanel.add(sortByIdAsc);
		sortPanel.add(sortByIdDesc);
		sortPanel.add(sortByDurationAsc); // Thêm vào panel
		sortPanel.add(sortByDurationDesc); // Thêm vào panel

		// Thêm panel sắp xếp vào sidePanel
		sidePanel.add(sortPanel); // Thêm panel sắp xếp vào panel bên
		// thêm chức năng sắp xếp cho các nút JRadioButton
		sortByNameAsc.addActionListener(new SortActionListener("name", true));
		sortByNameDesc.addActionListener(new SortActionListener("name", false));
		sortByIdAsc.addActionListener(new SortActionListener("id", true));
		sortByIdDesc.addActionListener(new SortActionListener("id", false));
		sortByDurationAsc.addActionListener(new SortActionListener("duration", true));
		sortByDurationDesc.addActionListener(new SortActionListener("duration", false));

		// Phần tìm kiếm
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new GridLayout(5, 4)); // Sử dụng GridLayout với 5 hàng và 4 cột
		searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm phim")); // Tiêu đề cho phần tìm phim

		// Các ô nhập và checkbox
		JCheckBox nameCheckBox = new JCheckBox(); // Checkbox cho tên
		JTextField nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(75, 10)); // Đặt kích thước ưa thích

		JCheckBox idCheckBox = new JCheckBox(); // Checkbox cho mã
		JTextField idField = new JTextField();
		idField.setPreferredSize(new Dimension(75, 10)); // Đặt kích thước ưa thích

		JCheckBox directorCheckBox = new JCheckBox(); // Checkbox cho đạo diễn
		JTextField directorField = new JTextField();
		directorField.setPreferredSize(new Dimension(75, 10)); // Đặt kích thước ưa thích

		JCheckBox genreCheckBox = new JCheckBox(); // Checkbox cho loại
		String[] genreOptions = { "Hành Động", "Tâm Lý", "Kinh Dị", "Tình Cảm","Viễn Tưởng" };
		JComboBox<String> genreComboBox = new JComboBox<>(genreOptions);
		genreComboBox.setPreferredSize(new Dimension(75, 10)); // Đặt kích thước cho ComboBox

		// Hàm để cập nhật trạng thái của các ô nhập
		ActionListener checkBoxListener = e -> {
			// Kiểm tra trạng thái của từng checkbox và cập nhật ô nhập tương ứng
			nameField.setEnabled(nameCheckBox.isSelected());
			nameField.setBackground(nameCheckBox.isSelected() ? Color.WHITE : Color.LIGHT_GRAY); // Đặt màu nền

			idField.setEnabled(idCheckBox.isSelected());
			idField.setBackground(idCheckBox.isSelected() ? Color.WHITE : Color.LIGHT_GRAY); // Đặt màu nền

			directorField.setEnabled(directorCheckBox.isSelected());
			directorField.setBackground(directorCheckBox.isSelected() ? Color.WHITE : Color.LIGHT_GRAY); // Đặt màu nền

			genreComboBox.setEnabled(genreCheckBox.isSelected());
			genreComboBox.setBackground(genreCheckBox.isSelected() ? Color.WHITE : Color.LIGHT_GRAY); // Đặt màu nền
		};

		// Thêm ItemListener cho các checkbox
		nameCheckBox.addItemListener(e -> checkBoxListener.actionPerformed(null));
		idCheckBox.addItemListener(e -> checkBoxListener.actionPerformed(null));
		directorCheckBox.addItemListener(e -> checkBoxListener.actionPerformed(null));
		genreCheckBox.addItemListener(e -> checkBoxListener.actionPerformed(null));

		// Khởi tạo màu nền cho các ô nhập

		// Thêm các thành phần vào searchPanel
		searchPanel.add(nameCheckBox);
		searchPanel.add(new JLabel("Theo tên:"));
		searchPanel.add(nameField); // Ô JTextField ở cột 3
		searchPanel.add(new JLabel()); // Ô trống cho cột 4

		searchPanel.add(idCheckBox);
		searchPanel.add(new JLabel("Theo mã:"));
		searchPanel.add(idField); // Ô JTextField ở cột 3
		searchPanel.add(new JLabel()); // Ô trống cho cột 4

		searchPanel.add(directorCheckBox);
		searchPanel.add(new JLabel("Theo đạo diễn:"));
		searchPanel.add(directorField); // Ô JTextField ở cột 3
		searchPanel.add(new JLabel()); // Ô trống cho cột 4

		searchPanel.add(genreCheckBox);
		searchPanel.add(new JLabel("Theo loại:"));
		searchPanel.add(genreComboBox); // Ô JComboBox ở cột 3
		searchPanel.add(new JLabel()); // Ô trống cho cột 4

		// Nút tìm và hủy
		JPanel buttonPanel = new JPanel();
		JButton searchButton = new JButton("Tìm");
		JButton cancelButton = new JButton("Hủy");
		// SET SIZE
		searchButton.setPreferredSize(new Dimension(60, 20));
		cancelButton.setPreferredSize(new Dimension(60, 20));

		// Đặt màu cho nút
		searchButton.setBackground(Color.BLUE); // Màu xanh cho nút Tìm
		searchButton.setForeground(Color.WHITE); // Màu chữ trắng

		cancelButton.setBackground(Color.RED); // Màu đỏ cho nút Hủy
		cancelButton.setForeground(Color.WHITE); // Màu chữ trắng

		buttonPanel.add(cancelButton); // Thêm nút Hủy vào buttonPanel
		buttonPanel.add(searchButton); // Thêm nút Tìm vào buttonPanel

		// Thêm buttonPanel vào searchPanel ở hàng 6, cột 4
		searchPanel.add(new JLabel()); // Ô trống cho cột 1
		searchPanel.add(new JLabel()); // Ô trống cho cột 2
		searchPanel.add(new JLabel()); // Ô trống cho cột 3
		searchPanel.add(buttonPanel); // Thêm panel nút vào cột 4

		sidePanel.add(searchPanel); // Thêm panel tìm kiếm vào panel bên

		// Panel for showing schedules
		JPanel showtimePanel = new JPanel();
		showtimePanel.setLayout(new BorderLayout()); // Use BorderLayout to separate calendar and showtimes
		showtimePanel.setBorder(BorderFactory.createTitledBorder("Lịch chiếu"));
		showtimePanel.setPreferredSize(new Dimension(300, 200));

		// Add JCalendar to showtimePanel
		calendar.setPreferredSize(new Dimension(150, 150));

		// Lấy ngày hiện t��i
		Calendar today = Calendar.getInstance();
		// Đặt ngày tối thiểu cho JCalendar là ngày hiện tại
		calendar.setMinSelectableDate(today.getTime()); // Ngày hôm nay

		showtimePanel.add(calendar, BorderLayout.NORTH); // Add calendar to the top

		// Lắng nghe khi người dùng chọn ngày trong lịch
		calendar.getDayChooser().addPropertyChangeListener("day", evt -> {
			Calendar selectedDate = calendar.getCalendar();
			String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate.getTime());
			System.out.println("Ngày đã chọn: " + dateStr);

			// Kiểm tra xem ngày đó có suất chiếu nào không
			if (showtimesByDate.containsKey(dateStr)) {
				List<String> showtimes = showtimesByDate.get(dateStr);
				updateShowtimeButtons(showtimes);
			} else {
				updateShowtimeButtons(Collections.emptyList()); // Không có suất chiếu
			}
		});

		// Panel for showtimes
		showtimeArea = new JPanel(new GridLayout(2, 6, 5, 5)); // 4x4 grid with spacing
		showtimeArea.setBorder(BorderFactory.createTitledBorder("Suất chiếu trong ngày")); // Title for showtimes

		showtimePanel.add(showtimeArea); // Add showtimeArea below calendar

		sidePanel.add(showtimePanel); // Add showtimePanel to sidePanel

		add(sidePanel, BorderLayout.WEST); // Thêm panel bên vào bên trái

		// Panel "Các phim chiếu trong hôm nay" chỉ hiển thị mã phim dưới dạng các nút
		todayMoviesPanel = new JPanel(new FlowLayout());
		todayMoviesPanel.setBorder(BorderFactory.createTitledBorder("Các phim chiếu trong hôm nay"));
		todayMoviesPanel.setPreferredSize(new Dimension(300, 100));

		// Thêm todayMoviesPanel vào dưới showtimeArea
		showtimePanel.add(todayMoviesPanel, BorderLayout.SOUTH);

		// Gọi phương thức để tải dữ liệu phim chiếu hôm nay
		loadTodayMovies();

		// Phần code cho chức năng

		// Thêm ActionListener cho nút "Tìm"
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// Lưu trữ danh sách phim tìm thấy
				DefaultTableModel filteredModel = new DefaultTableModel(columnNames, 0);

				String searchText = nameField.getText().trim(); // Không chuyển thành lowercase
				String idText = idField.getText().trim();
				String directorText = directorField.getText().trim(); // Không chuyển thành lowercase
				String genreText = (String) genreComboBox.getSelectedItem();

				// Tìm kiếm và thêm phim vào filteredModel
				for (int i = 0; i < model.getRowCount(); i++) {
					boolean matches = false; // Biến để kiểm tra xem có phim nào phù hợp

					// Lấy dữ liệu từ model
					String currentName = model.getValueAt(i, 1).toString(); // Không chuyển thành lowercase
					String currentId = model.getValueAt(i, 0).toString();
					String currentDirector = model.getValueAt(i, 2).toString(); // Không chuyển thành lowercase
					String currentGenre = model.getValueAt(i, 3).toString();

					// Kiểm tra từng tiêu chí tìm kiếm
					if (nameCheckBox.isSelected() && currentName.contains(searchText)) {
						matches = true; // Nếu tìm thấy theo tên
					}
					if (idCheckBox.isSelected() && currentId.equals(idText)) {
						matches = true; // Nếu tìm thấy theo mã
					}
					if (directorCheckBox.isSelected() && currentDirector.contains(directorText)) {
						matches = true; // Nếu tìm thấy theo đạo diễn
					}
					if (genreCheckBox.isSelected() && currentGenre.equals(genreText)) {
						matches = true; // Nếu tìm thấy theo thể loại
					}

					// Nếu có ít nhất một tiêu chí phù hợp, thêm phim vào filteredModel
					if (matches) {
						filteredModel.addRow(new Object[] { currentId, currentName, currentDirector, currentGenre,
								model.getValueAt(i, 4) });
					}
				}

				// Cập nhật bảng với dữ liệu đã lọc
				model.setRowCount(0); // Xóa tất cả hàng hiện tại
				for (int i = 0; i < filteredModel.getRowCount(); i++) {
					model.addRow(new Object[] { filteredModel.getValueAt(i, 0), filteredModel.getValueAt(i, 1),
							filteredModel.getValueAt(i, 2), filteredModel.getValueAt(i, 3),
							filteredModel.getValueAt(i, 4) });
				}
			}
		});

		// Nút hủy
		cancelButton.addActionListener(e -> {
			// Khôi phục lại dữ liệu ban đầu
			model.setRowCount(0); // Xóa tất cả hàng hiện tại
			for (Phim phim : originalPhimList) {
				model.addRow(new Object[] { phim.getMaPhim(), phim.getTenPhim(), phim.getDaoDien(), phim.getTheLoai(),
						phim.getThoiLuong() });
			}
			// Đặt lại trạng thái của các ô nhập và checkbox
			nameField.setText("");
			idField.setText("");
			directorField.setText("");
			genreComboBox.setSelectedIndex(0);
			nameCheckBox.setSelected(false);
			idCheckBox.setSelected(false);
			directorCheckBox.setSelected(false);
			genreCheckBox.setSelected(false);
		});

	}

	private void sortData(String criteria, boolean ascending) {
		Comparator<Phim> comparator;

		switch (criteria) {
		case "name":
			comparator = Comparator.comparing(Phim::getTenPhim);
			break;
		case "id":
			comparator = Comparator.comparing(Phim::getMaPhim);
			break;
		case "duration":
			comparator = Comparator.comparingInt(Phim::getThoiLuong);
			break;
		default:
			return;
		}

		// Đảo ngược thứ tự nếu không phải tăng dần
		if (!ascending) {
			comparator = comparator.reversed();
		}

		Collections.sort(dsPhim, comparator);
		updateTable();
	}

	private void updateTable() {
		model.setRowCount(0); // Xóa dữ liệu hiện tại
		for (Phim phim : dsPhim) {
			model.addRow(new Object[] { phim.getMaPhim(), phim.getTenPhim(), phim.getDaoDien(), phim.getTheLoai(),
					phim.getThoiLuong() });
		}
	}

	private class SortActionListener implements ActionListener {
		private final String criteria;
		private final boolean ascending;

		public SortActionListener(String criteria, boolean ascending) {
			this.criteria = criteria;
			this.ascending = ascending;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			sortData(criteria, ascending);
		}
	}

	public void highlightShowtimeDates(List<Date> showtimeDates, String movieId) {
		// Lấy JDayChooser từ JCalendar
		JDayChooser dayChooser = calendar.getDayChooser();

		// Xóa tất cả các đánh dấu trước đó
		for (int i = 1; i <= 31; i++) {
			Component component = dayChooser.getDayPanel().getComponent(i + 6);
			if (component instanceof JButton) {
				component.setBackground(null);
			}
		}

		// Lấy ngày chiếu và suất chiếu từ cơ sở dữ liệu
		showtimesByDate.clear(); // Xóa thông tin suất chiếu cũ
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		for (Date date : showtimeDates) {
			String dateStr = dateFormat.format(date);
			List<String> showtimes = phimDAO.getShowtimesByDateAndMovie(dateStr, movieId);
			showtimesByDate.put(dateStr, showtimes);
			// in ngày chiếu và lịch chiếu tương ứng
			System.out.println("Ngày chiếu: " + dateStr + " - Lịch chiếu: " + showtimes);
		}

		// Tô màu các ngày có lịch chiếu
		Calendar cal = Calendar.getInstance();
		for (String dateStr : showtimesByDate.keySet()) {
			try {
				cal.setTime(dateFormat.parse(dateStr));
				int day = cal.get(Calendar.DAY_OF_MONTH);
				Component component = dayChooser.getDayPanel().getComponent(day + 11);
				if (component instanceof JButton) {
					component.setBackground(Color.YELLOW);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		dayChooser.repaint();
	}

	private void updateShowtimeButtons(List<String> showtimes) {
		showtimeArea.removeAll(); // Xóa các nút suất chiếu cũ

		for (String showtime : showtimes) {
			JButton showtimeButton = new JButton(showtime);
			showtimeButton.setPreferredSize(new Dimension(100, 30)); // Đặt kích thước nút suất chiếu
			showtimeArea.add(showtimeButton); // Thêm nút vào khu vực suất chiếu
		}

		showtimeArea.revalidate();
		showtimeArea.repaint();
	}

	private void loadTodayMovies() {
		// Lấy ngày hiện tại dưới dạng chuỗi
		String todayStr = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

		// Gọi hàm getMoviesByDate để lấy danh sách mã phim chiếu hôm nay
		List<String> todayMovies = phimDAO.getMoviesByDate(todayStr);

		// Xóa các nút phim cũ trước khi thêm mới
		todayMoviesPanel.removeAll();

		// Tạo một nút cho mỗi mã phim và thêm vào panel
		for (String movieId : todayMovies) {
			JButton movieButton = new JButton(movieId);
			movieButton.setPreferredSize(new Dimension(100, 30)); // Set button size if needed
			todayMoviesPanel.add(movieButton);
		}

		// Refresh panel to show updated buttons
		todayMoviesPanel.revalidate();
		todayMoviesPanel.repaint();
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			javax.swing.JFrame frame = new javax.swing.JFrame("Giao diện tìm kiếm phim");
			frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
			frame.add(new TimKiemPhim_GD());
			// frame size max
			frame.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
			frame.setVisible(true);
		});
	}
}
