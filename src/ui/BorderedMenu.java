package ui;
import javax.swing.*;
import java.awt.*;

public class BorderedMenu extends JMenu {
    public BorderedMenu(String title, String iconPath) {
        super(title);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Đường viền dày
        setOpaque(true);
        setBackground(Color.WHITE); // Màu nền trắng
        setForeground(Color.BLACK); // Màu chữ đen

        // Thêm icon nếu có
        if (iconPath != null) {
            ImageIcon originalIcon = new ImageIcon(iconPath);
            Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaledImage));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Không thêm hiệu ứng bóng
    }
} 