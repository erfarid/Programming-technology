package gamecollector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Tree extends Obstacles {

    public Tree(int x, int y, int size) {
        super(x, y, size, size);

        // Load the image using the absolute path
        try {
           image = ImageIO.read(new File("C:\\Users\\ASUS\\OneDrive\\Desktop\\GameCollector\\src\\Images\\Tree.jpg"))
                   .getScaledInstance(width, height, Image.SCALE_SMOOTH);

            if (image == null) {
               // System.out.println("Image not loaded: Tree.png");
            } else {
              //  System.out.println("Image loaded successfully: Tree.png");
            }
        } catch (IOException e) {
            //System.out.println("Error loading image: " + e.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null); // Draw the tree image
           // System.out.println("Tree image drawn at: (" + x + ", " + y + ") with size: " + width + "x" + height);
        } else {
            g.setColor(Color.GREEN); // Fallback to green rectangle
            g.fillRect(x, y, width, height);
           // System.out.println("Fallback green rectangle drawn at: (" + x + ", " + y + ")");
        }
    }
}
