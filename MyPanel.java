import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyPanel extends JPanel implements ActionListener {
    public final int WIDTH = 256;
    public final int HEIGHT = 256;
    Timer timer;

    float delta;
    int fps;
    long current_time;
    float delta_counter;
    int fps_counter;

    MyPanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        timer = new Timer(1, this);
        timer.start();
        delta = 1.f;
        current_time = System.nanoTime();
        fps = 0;
        fps_counter = 0;
        delta_counter = 0.f;
    }

    public void fillTriangle(Graphics2D g2d, Vec2 p1, Vec2 p2, Vec2 p3) {
        g2d.fillPolygon(new int[] {(int)p1.x, (int)p2.x, (int)p3.x}, new int[] {(int)p1.y, (int)p2.y, (int)p3.y}, 3);
    }

    void update(Graphics2D g2d) {}

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;

        long next_time = System.nanoTime();
        delta = (next_time - current_time) / 1000000.f;
        current_time = next_time;
        delta_counter += delta * 1000000.f;
        fps_counter++;
        if (delta_counter >= 1000000000) {
            fps = fps_counter;
            delta_counter = 0.f;
            fps_counter = 0;
        }
        update(g2d);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}