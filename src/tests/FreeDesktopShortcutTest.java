package tests;

import java.io.File;
import java.io.IOException;

import org.af.commons.OSTools;
import org.af.commons.install.DesktopShortcut;
import org.af.commons.install.FreeDesktopShortcut;

public class FreeDesktopShortcutTest {
	public static void main(String[] args) {
		FreeDesktopShortcut fd = new FreeDesktopShortcut(OSTools.guessDesktop(), "RTest", "R");				
		fd.setTerminal(true);
		try {
			fd.createDesktopEntry();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
