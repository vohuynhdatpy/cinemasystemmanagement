package ui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

public class TrangChu extends JPanel {
    
    public TrangChu() {
        ImageIcon imageIcon = new ImageIcon("src/ui/icon/trangChu.jpg");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width, 
                                                    Toolkit.getDefaultToolkit().getScreenSize().height, 
                                                    Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaledImage));
        label.setLayout(null);

        add(label);
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(new TrangChu());
        frame.pack();
        frame.setVisible(true);
    }
}
