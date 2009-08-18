package tests;

import org.af.commons.OSTools;
import org.af.commons.install.FreeDesktopShortcut;

import java.io.IOException;

public class FreeDesktopShortcutTest {
	public static void main(String[] args) {
		FreeDesktopShortcut fd = new FreeDesktopShortcut(OSTools.guessDesktop(), "RTest", "R");				
		fd.setTerminal(true);
		//fd.addParameter("CMD BATCH");
		//fd.addParameter(new File("/home/kornel/test.R"));
		try {
			fd.create();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
