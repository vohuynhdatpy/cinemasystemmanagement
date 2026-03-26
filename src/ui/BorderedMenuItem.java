package ui;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BorderedMenuItem extends JMenuItem {
    public BorderedMenuItem(String title) {
        super(title);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Khung đen
        setOpaque(true);
        setBackground(Color.WHITE); // Màu nền
        setForeground(Color.BLACK); // Màu chữ
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Thêm hiệu ứng bóng
        g.setColor(new Color(0, 0, 0, 50)); // Màu bóng
        g.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 10, 10); // Vẽ bóng
    }
}