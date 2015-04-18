package uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities;

/*
 * Created by Samuel on 09/04/2015.
 * uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities - Language
 */
/**
 * Language - an enum of language name/codes supported by the Yandex API
 */
public enum Language {
    ALBANIAN("Albanian", "sq"),
    ARMENIAN("Armenian", "hy"),
    AZERBAIJANI("Azerbaijani", "az"),
    BELARUSIAN("Belarusian", "be"),
    BULGARIAN("Bulgarian", "bg"),
    CATALAN("Catalan", "ca"),
    CROATIAN("Croatian", "hr"),
    CZECH("Czech", "cs"),
    DANISH("Danish", "da"),
    DUTCH("Dutch", "nl"),
    ENGLISH("English", "en"),
    ESTONIAN("Estonian", "et"),
    FINNISH("Finnish", "fi"),
    FRENCH("French", "fr"),
    GERMAN("German", "de"),
    GEORGIAN("Georgian", "ka"),
    GREEK("Greek", "el"),
    HUNGARIAN("Hungarian", "hu"),
    ITALIAN("Italian", "it"),
    LATVIAN("Latvian", "lv"),
    LITHUANIAN("Lithuanian", "lt"),
    MACEDONIAN("Macedonian", "mk"),
    NORWEGIAN("Norwegian", "no"),
    POLISH("Polish", "pl"),
    PORTUGUESE("Portuguese", "pt"),
    ROMANIAN("Romanian", "ro"),
    RUSSIAN("Russian", "ru"),
    SERBIAN("Serbain", "sr"),
    SLOVAK("Slovak", "sk"),
    SLOVENIAN("Slovenian", "sl"),
    SPANISH("Spanish", "es"),
    SWEDISH("Swedish", "sv"),
    TURKISH("Turkish", "tr"),
    UKRAINIAN("Ukrainian", "uk"),
    DISABLED("Disabled", "disabled");

    /**
     * String representation of this language.
     */
    private final String name;
    private final String code;

    /**
     * Enum constructor.
     * @param pName The language identifier.
     * @param pCode The language code.
     */
    Language(final String pName, final String pCode) {
        this.name = pName;
        this.code = pCode;
    }

    /**
     * Get the language from provided language string.
     * @return The language object.
     */
    public static Language fromString(final String pLanguage) {
        for (Language lang : values()) {
            if (lang.getName().equalsIgnoreCase(pLanguage) || lang.getCode().equalsIgnoreCase(pLanguage)) {
                return lang;
            }
        }
        return null;
    }

    /**
     * Returns the name of this language.
     * @return The name of this language.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the code of this language.
     * @return The code of this language.
     */
    public String getCode() {
        return this.code;
    }

}