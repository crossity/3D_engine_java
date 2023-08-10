import java.awt.*;
import javax.swing.*;

public class MyFrame extends JFrame {
    Game panel;

    MyFrame(String title) {
        panel = new Game();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle(title);
    }
}