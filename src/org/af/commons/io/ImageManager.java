package org.af.commons.io;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageManager {
    
	public static BufferedImage soften(BufferedImage bufferedImage) {
		float[] softenArray = {-1, -1, -1,
				-1,  9, -1,
				-1, -1, -1};
		Kernel kernel = new Kernel(3, 3, softenArray);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		return cOp.filter(bufferedImage, null);
	}
	
	public static void addBorder(File originalFile, File resizedFile, int newWidth, int newHeight, float quality, boolean soften) throws IOException {
        Image i = new ImageIcon(originalFile.getCanonicalPath()).getImage();        
        BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.createGraphics();
        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, newWidth, newHeight);
        g.drawImage(i, (newWidth-i.getWidth(null))/2, (newHeight-i.getHeight(null))/2, null);
        g.dispose();
        if (soften) {
            bufferedImage = soften(bufferedImage); 
        }
        ImageIO.write(bufferedImage, "jpg", resizedFile);
	}
	
    public static void resize(File originalFile, File resizedFile, int newWidth, int newHeight, float quality, boolean soften) throws IOException {

        Image i = new ImageIcon(originalFile.getCanonicalPath()).getImage();

        ImageIO.write(resize(i, newWidth, newHeight, quality, soften), "jpg", resizedFile);
    }
    
    public static BufferedImage resize(Image i, int newWidth, int newHeight, float quality, boolean soften) {

        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);         

        double scale = Math.min(newWidth/(double)iWidth, newHeight/(double)iHeight);

        Image resizedImage = null;
        
        resizedImage = i.getScaledInstance((int)(scale*iWidth), (int)(scale*iHeight), Image.SCALE_SMOOTH);

        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight,
                                                        BufferedImage.TYPE_INT_RGB);
        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();

        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, newWidth, newHeight);
        g.drawImage(temp, (newWidth-temp.getWidth(null))/2, (newHeight-temp.getHeight(null))/2, null);
        g.dispose();

        if (soften) {
            bufferedImage = soften(bufferedImage); 
        }

        return bufferedImage;
    }
    
    public static void resize(File originalFile, File resizedFile, int newHeight, float quality, boolean soften) throws IOException {

        Image i = new ImageIcon(originalFile.getCanonicalPath()).getImage();

        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);         

        Image resizedImage = null;
        
        resizedImage = i.getScaledInstance((newHeight * iWidth) / iHeight, newHeight, Image.SCALE_SMOOTH);

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

        ImageIO.write(bufferedImage, "jpg", resizedFile);
    }
    
    public static BufferedImage getBufferedImage(Image i) {
    	BufferedImage bufferedImage = new BufferedImage(i.getWidth(null), i.getHeight(null),
                BufferedImage.TYPE_INT_RGB);
    	 Graphics g = bufferedImage.createGraphics();
         
         g.drawImage(i, 0, 0, null);
         g.dispose();

         return bufferedImage;
    }

}
