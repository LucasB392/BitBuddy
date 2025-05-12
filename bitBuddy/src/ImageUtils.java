import java.awt.*;
import java.awt.image.*;

/**
 * Class to handle sprites and images with transparent backgrounds
 * <br><br>
 * This class is responsible to accepting with transparent backgrounds and updating their colour.<br><br>
 * 
 * @version 1.0
 * @author Elmar
 */

public class ImageUtils {

	/**
	 * Update the image.
	 * 
	 * @param image the image with the transparent background
	 * @param color the colour used for conversion
	 * @return the updated image
	 */
    public static BufferedImage makeColorTransparent(BufferedImage image, final Color color) {
        ImageFilter filter = new RGBImageFilter() {
            // Convert the given color to a marker value.
            private final int markerRGB = color.getRGB() | 0xFF000000;
            @Override
            public final int filterRGB(int x, int y, int rgb) {
                // If the pixel matches the marker color, set its alpha to 0.
                if ((rgb | 0xFF000000) == markerRGB) {
                    return 0x00FFFFFF & rgb;
                } else {
                    return rgb;
                }
            }
        };
        ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
        Image transparentImage = Toolkit.getDefaultToolkit().createImage(ip);
        BufferedImage bimage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bimage.createGraphics();
        g2d.drawImage(transparentImage, 0, 0, null);
        g2d.dispose();
        return bimage;
    }
}

