package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DatChoNgoi extends JPanel {
    private static final int ROWS = 5;
    private static final int COLS = 10;
    private final JToggleButton[][] seatButtons = new JToggleButton[ROWS][COLS];
    private final List<String> selectedSeats = new ArrayList<>();

    public DatChoNgoi(String roomCode) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Phòng chiếu: " + roomCode));

        // === MÀN HÌNH ===
        JLabel screenLabel = new JLabel("MÀN HÌNH", JLabel.CENTER);
        screenLabel.setFont(new Font("Arial", Font.BOLD, 20));
        screenLabel.setOpaque(true);
        screenLabel.setBackground(Color.BLACK);
        screenLabel.setForeground(Color.WHITE);
        screenLabel.setPreferredSize(new Dimension(0, 50));

        // === GHẾ ===
        JPanel seatPanel = new JPanel(new GridLayout(ROWS, COLS, 5, 5));
        char rowLabel = 'A';

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                String seatId = String.valueOf((char) (rowLabel + row)) + (col + 1);
                seatButtons[row][col] = new JToggleButton(seatId);
                seatButtons[row][col].setPreferredSize(new Dimension(50, 50));
                seatButtons[row][col].setBackground(Color.GREEN);
                seatButtons[row][col].setForeground(Color.BLACK);
                seatButtons[row][col].setFont(new Font("Arial", Font.BOLD, 12));

                // Xử lý chọn ghế
                int r = row, c = col;
                seatButtons[row][col].addActionListener(e -> {
                    if (seatButtons[r][c].isSelected()) {
                        seatButtons[r][c].setBackground(Color.CYAN);
                    } else {
                        seatButtons[r][c].setBackground(Color.GREEN);
                    }
                });

                seatPanel.add(seatButtons[row][col]);
            }
        }

        // === NÚT XÁC NHẬN ===
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton confirmButton = new JButton("XÁC NHẬN CHỖ NGỒI");
        confirmButton.setBackground(new Color(0, 128, 0));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setPreferredSize(new Dimension(250, 50));

        confirmButton.addActionListener(e -> {
            selectedSeats.clear();
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (seatButtons[row][col].isSelected()) {
                        selectedSeats.add(seatButtons[row][col].getText());
                    }
                }
            }
            // Đóng cửa sổ (sẽ kích hoạt windowClosed)
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        buttonPanel.add(confirmButton);

        // === THÊM VÀO GIAO DIỆN ===
        add(screenLabel, BorderLayout.NORTH);
        add(seatPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public List<String> getSelectedSeats() {
        return new ArrayList<>(selectedSeats); // Trả về bản sao
    }
}