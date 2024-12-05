package gamecollector;

import java.awt.*;

public class Tree extends Obstacles {
    public Tree(int x, int y, int size) {
        super(x, y, size, size); // Trees are squares
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height); // Draw tree as a green square
    }
}
