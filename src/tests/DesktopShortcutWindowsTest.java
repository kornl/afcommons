package tests;

import java.io.File;
import java.io.IOException;

import org.af.commons.install.WindowsDesktopShortcut;

public class DesktopShortcutWindowsTest {
    public static void main(String[] args) {
        try {
            File d = new File(System.getProperty("user.home"), "Desktop");
            WindowsDesktopShortcut sc = new WindowsDesktopShortcut(d, "Test1", new File("C:\\Programme\\Adobe\\Reader 8.0\\Reader\\acrord32.exe"));
            sc.create();
            sc = new WindowsDesktopShortcut(d, "Test2", new File("C:\\Programme\\Adobe\\Reader 8.0\\Reader\\acrord32.exe"));
            sc.addParameter(new File("c:\\test.pdf"));
            sc.setWorkingDir("c:\\");
            sc.setDescription("bla bla blubb");
            sc.create();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
