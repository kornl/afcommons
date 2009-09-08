package tests;

import java.io.IOException;

import org.af.commons.install.FreeDesktopShortcut;
import org.af.commons.tools.OSTools;

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
