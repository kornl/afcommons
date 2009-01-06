package af.commons.io;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageManager {
    
	public static BufferedImage soften(BufferedImage bufferedImage) {
		float[] softenArray = {-1, -1, -1,
				-1,  9, -1,
				-1, -1, -1};
		Kernel kernel = new Kernel(3, 3, softenArray);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		return cOp.filter(bufferedImage, null);
	}
	
    public static void resize(File originalFile, File resizedFile, int newWidth, float quality, boolean soften)
        throws IOException {

//        if (quality < 0 || quality > 1) {
//            throw new IllegalArgumentException("Quality has to be between 0 and 1");
//        }

        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null;

        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);         

        if (iWidth > iHeight) {
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
        } else {
            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight, newWidth, Image.SCALE_SMOOTH);
        }

        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null),
                                                        BufferedImage.TYPE_INT_RGB);
        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();

        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        if (soften) {
            bufferedImage = soften(bufferedImage); 
        }
        
        // Write the jpeg to a file.
        FileOutputStream out = new FileOutputStream(resizedFile);

        ImageIO.write(bufferedImage, "jpg", resizedFile);
    }

}
