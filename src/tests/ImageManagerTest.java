package tests;

import java.io.File;
import java.io.IOException;

import org.af.commons.io.ImageManager;


public class ImageManagerTest {
    public static void main(String[] args) {
        File in = new File(ImageManagerTest.class.getResource("test.jpg").getFile());
        File out = new File(System.getProperty("java.io.temp"), "resized.jpg");
        try {
            ImageManager.resize(in, out, 50, 1, true);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
