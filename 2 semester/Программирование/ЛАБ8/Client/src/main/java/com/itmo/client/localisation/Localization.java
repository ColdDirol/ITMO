package com.itmo.client.localisation;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {
    private static ResourceBundle language;

    public static void setLocale() {
        language = ResourceBundle.getBundle("language", Locale.getDefault());
    }

    public static ResourceBundle getLanguage() {
        return language;
    }

    public static void setLanguage(String lang) {
        language = ResourceBundle.getBundle("language", new Locale(lang));
        Locale.setDefault(new Locale(lang));
    }

    public static String getText(String string) throws UnsupportedEncodingException {
        return language.getString(string);
    }
}
