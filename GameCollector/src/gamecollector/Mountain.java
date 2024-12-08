package gamecollector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Mountain extends Obstacles {

    public Mountain(int x, int y, int size) {
        super(x, y, size, size);

        // Load the image using the absolute path
        try {
            image = ImageIO.read(new File("C:\\Users\\ASUS\\OneDrive\\Desktop\\GameCollector\\src\\Images\\Mountain.jpg"))
                    .getScaledInstance(width, height, Image.SCALE_SMOOTH);

            if (image == null) {
                System.out.println("Image not loaded: Mountain.jpg");
            } else {
                System.out.println("Image loaded successfully: Mountain.jpg");
            }
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null); // Draw the mountain image
            System.out.println("Mountain image drawn at: (" + x + ", " + y + ") with size: " + width + "x" + height);
        } else {
            g.setColor(Color.GRAY); // Fallback to gray rectangle
            g.fillRect(x, y, width, height);
            System.out.println("Fallback gray rectangle drawn at: (" + x + ", " + y + ")");
        }
    }
}
