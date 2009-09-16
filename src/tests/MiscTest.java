package tests;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.af.commons.Localizer;
import org.af.commons.errorhandling.ErrorDialog;
import org.af.commons.errorhandling.InformDialog;

public class MiscTest {

	/**
	 * @param args
	 * @throws Exception  
	 */
	public static void main(String[] args) throws Exception {

		Constructor con = ErrorDialog.class.getConstructor(new Class[] {String.class, Throwable.class, boolean.class});
		Object obj = con.newInstance(Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_PLEASE_SEND"), null, true);
		//((ErrorDialog) obj).showDialog();
		Method m = obj.getClass().getMethod("showDialog");
		m.invoke(obj);

		//Constructor con = ErrorDialog.class.getConstructor(new Class[] {String.class, Throwable.class, boolean.class});
		//Object obj = con.newInstance(Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_PLEASE_SEND"), null, true);
		//obj.getClass().getDeclaredMethod("showDialog").invoke(obj);

	}

}
