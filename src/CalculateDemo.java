
import javax.swing.*;
import java.awt.*;

public class CalculateDemo {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculateFace();
            }
        });
    }
}
