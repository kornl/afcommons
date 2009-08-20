package org.af.commons;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Localizer {

    private static Localizer instance;
    private Locale locale;
    private ResourceBundle defMessages;
    private ResourceBundle appMessages;
    private String language;

    public Localizer(String language) {
        this.language = language;
        locale = new Locale(language);
//        defMessages = ResourceBundle.getBundle("DefaultResources", locale);
        defMessages = ResourceBundle.getBundle("MessageBundleDef", locale);
        appMessages = ResourceBundle.getBundle("MessageBundleApp", locale);
        Locale.setDefault(locale);
        JOptionPane.setDefaultLocale(locale);
        // do we really need this?
        UIManager.put("OptionPane.yesButtonText", "Yes");
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.cancelButtonText", "Cancel");
        UIManager.put("OptionPane.titleText", "Select an Option");
    }

    public static void init(String language) {
        System.out.println("Initializing Localizer: " + language);
        instance = new Localizer(language);
    }

    /**
     * @return the singleton instance
     * @throws RuntimeException when not initialized before.
     */
    public static Localizer getInstance() {
        if (instance == null) {
            throw new RuntimeException("Call ErrorHandler:init first!");
        }
        return instance;
    }

    public String getLanguage() {
        return language;
    }

    public String getString(String key) {
        if (appMessages.containsKey(key))
            return appMessages.getString(key);
        return defMessages.getString(key);
	}
}
