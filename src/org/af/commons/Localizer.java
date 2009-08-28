package org.af.commons;

import java.util.*;

public class Localizer {

    private static Localizer instance;
    private Locale locale = Locale.getDefault();
    private String language = Locale.getDefault().getLanguage();
    // store for all messages
    private Properties props = new Properties();
    private List<String> bundles = new ArrayList<String>();

    protected Localizer() {
        setLanguage("en");
//        // do we really need this?
//        UIManager.put("OptionPane.yesButtonText", "Yes");
//        UIManager.put("OptionPane.noButtonText", "No");
//        UIManager.put("OptionPane.cancelButtonText", "Cancel");
//        UIManager.put("OptionPane.titleText", "Select an Option");
    }


    /**
     * Get singleton instance.
     * @return The singleton instance
     */
    public static Localizer getInstance() {
        if (instance == null)
            instance = new Localizer();
        return instance;
    }

    /**
     * Adds a ResourceBundle to this Localizer. If keys were already defined, they are overwritten.
     * @param name Name of the ResourceBundle. Has to be on the class path.
     */
    public void addResourceBundle(String name) {
        bundles.add(name);
        addResourceBundleProps(name);
    }

    protected void addResourceBundleProps(String name) {
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
     * Note that this also sets the locale, will add the afcommons resource bundle
     *  and will reload all previously added resource bundles.
     * @param lang Abbreviation of language.
     */
    public void setLanguage(String lang) {
        this.language = lang;
        Locale.setDefault(locale);
        addResourceBundle("org.af.commons.widgets.ResourceBundle");
        for (String b:bundles)
            addResourceBundleProps(b);
    }

    /**
     * Get a localized string, probably for some widget or message.
     * @param key Key to access the string.
     * @return Localized version of message string
     * @exception MissingResourceException if no object for the given key can be found
     */
    public String getString(String key) throws MissingResourceException {
        String s = props.getProperty(key);
        if (s == null)
            throw new MissingResourceException("Could not find message for key!",
                    this.getClass().toString(), key);
        return s;
	}

    /**
     * Deletes all currently set messages and the cached list of added bundles, but will add
     * the afcommons bundle at the end.
     */
    public void clearAllAddedBundles() {
        props.clear();
        bundles.clear();
        addResourceBundle("org.af.commons.widgets.ResourceBundle");
    }

}
