package gamecollector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PicnicBasket {

    int x, y, size;
    private Image image; // Image to represent the basket

    public PicnicBasket(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;

        // Load the basket image
        try {
            image = ImageIO.read(new File("C:\\Users\\ASUS\\OneDrive\\Desktop\\GameCollector\\src\\Images\\Basket.jpg"))
                    .getScaledInstance(size, size, Image.SCALE_SMOOTH);

            if (image == null) {
             //   System.out.println("Image not loaded: Basket.jpg");
            } else {
               // System.out.println("Image loaded successfully: Basket.jpg");
            }
        } catch (IOException e) {
            //System.out.println("Error loading image: " + e.getMessage());
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size); // Define the bounding box of the basket
    }



    // Static method to generate a picnic basket that does not overlap with other baskets or obstacles
    public static PicnicBasket generateNonOverlappingBasket(
        int parkWidth,
        int parkHeight,
        int basketSize,
        List<Obstacles> obstacles,
        List<PicnicBasket> baskets
    ) {
        PicnicBasket basket = null;
        boolean validPosition = false;
        int maxRetries = 100; // Limit retries to avoid infinite loops
        int retries = 0;

        while (!validPosition && retries < maxRetries) {
            retries++;

            // Generate random position for the basket
            int x = (int) (Math.random() * (parkWidth - basketSize));
            int y = (int) (Math.random() * (parkHeight - basketSize));
            basket = new PicnicBasket(x, y, basketSize);

            // Get the bounds of the proposed basket
            Rectangle basketBounds = basket.getBounds();

            validPosition = true;

            // Check for overlap with obstacles
            for (Obstacles obstacle : obstacles) {
                if (basketBounds.intersects(obstacle.getBounds())) {
                    validPosition = false;
                    break;
                }
            }

            // Check for overlap with other baskets
            for (PicnicBasket existingBasket : baskets) {
                if (basketBounds.intersects(existingBasket.getBounds())) {
                    validPosition = false;
                    break;
                }
            }
        }

        if (retries >= maxRetries) {
            throw new RuntimeException("Unable to place picnic baskets without collision after " + maxRetries + " retries.");
        }

        return basket;
    }

    public void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, size, size, null); // Draw the basket image
            //System.out.println("Basket image drawn at: (" + x + ", " + y + ") with size: " + size + "x" + size);
        } else {
            g.setColor(Color.RED); // Fallback to red circle
            g.fillOval(x, y, size, size);
           // System.out.println("Fallback red circle drawn at: (" + x + ", " + y + ")");
        }
    }
}
