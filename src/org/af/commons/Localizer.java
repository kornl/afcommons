package org.af.commons;

import javax.swing.*;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Localizer {

    private static Localizer instance;
    private Locale locale;;
    private String language;
    // store for all messages
    private Properties props;

    protected Localizer() {
        locale = Locale.getDefault();
//        defMessages = ResourceBundle.getBundle("DefaultResources", locale);
        Locale.setDefault(locale);
        JOptionPane.setDefaultLocale(locale);

//        // do we really need this?
//        UIManager.put("OptionPane.yesButtonText", "Yes");
//        UIManager.put("OptionPane.noButtonText", "No");
//        UIManager.put("OptionPane.cancelButtonText", "Cancel");
//        UIManager.put("OptionPane.titleText", "Select an Option");
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

    /**
     * Adds a ResourceBundle to this Localizer. If keys were already defined, they are overwritten.
     * @param name Name of the ResourceBundle. Has to be on the class path.
     */
    public void addResourceBundle(String name) {
        ResourceBundle rb = ResourceBundle.getBundle(name, locale);
        Enumeration<String> en = rb.getKeys();
        while (en.hasMoreElements()) {
            String k = en.nextElement();
            props.put(k, rb.getObject(k));
        }
    }

    /**
     * Returns the abbreviation of the current language, e.g. "en".
     * @return The abbreviation of the current language.
     */
    public String getLanguage() {
        return language;
    }


    /**
     * Sets the current language, e.g. "en".
     * Note that this also sets the locale and will clear all current messages
     * in thus Localizer.
     * @param lang Abbreviation of language.
     */
    public String setLanguage(String lang) {
        this.language = lang;
    }

    /**
     * Get a localized string, probably for some widget or message.
     * @param key Key to access the string.
     * @return Localized version of message string.
     */
    public String getString(String key) {
        return props.getProperty(key);
	}

    /**
     * Deletes all currently set messages.
     */
    public void clearAllStrings() {
        props.clear();
    }

}
