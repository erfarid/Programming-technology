package gamecollector;

import java.awt.*;

public class Mountain extends Obstacles {
    public Mountain(int x, int y, int size) {
        super(x, y, size, size); // Mountains are squares
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height); // Draw mountain as a black square
    }
}
