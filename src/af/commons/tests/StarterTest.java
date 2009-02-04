package af.commons.tests;

import java.io.File;
import java.io.IOException;

import af.commons.install.WindowsDesktop;

public class StarterTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		WindowsDesktop wd = new WindowsDesktop();
		wd.setIconpath("notepad.exe, 0");
		wd.setIconpath("C:\\Documents and Settings\\biostat\\icon32.ico");
		try {
			wd.createDesktopEntry("testStarter", new File("C:\\"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
