package tests;

import java.util.Locale;

import org.af.commons.Localizer;

public class LocalTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Localizer.getInstance().setLanguage("en");
		System.out.println(Localizer.getInstance().getString("AFCOMMONS_WIDGETS_VALIDATE_INTEGER"));
		System.out.println(Locale.getDefault().getLanguage());
		Localizer.getInstance().setLanguage("de");
		System.out.println(Localizer.getInstance().getString("AFCOMMONS_WIDGETS_VALIDATE_INTEGER"));
		System.out.println(Locale.getDefault().getLanguage());
	}

}
