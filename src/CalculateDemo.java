
import javax.swing.*;
import java.awt.*;

public class CalculateDemo {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            var frame = new CalculateFace();
            frame.setTitle("КАЛЬКУЛЯТОР");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
