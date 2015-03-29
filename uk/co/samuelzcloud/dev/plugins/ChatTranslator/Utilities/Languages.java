package uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities;

/**
 * Created by Samuel on 29/03/2015.
 * uk.co.samuelzcloud.dev.plugins.ChatTranslator.Utilities - Languages
 */
public enum Languages {
    AFRIKAANS("Afrikaans", "af"),
    ALBANIAN("Albanian", "sq"),
    ARABIC("Arabic", "ar"),
    ARMENIAN("Armenian", "hy"),
    AZERBAIJANI("Azerbaijani", "az"),
    BASQUE("Basque", "eu"),
    BELARUSIAN("Belarusian", "be"),
    BENGALI("Bengali", "bn"),
    BOSNIAN("Bosnian", "bs"),
    BULGARIAN("Bulgarian", "bg"),
    //CATALAN("Catalan", "ca"), // POSSIBLE NOT WORKING
    CHINESE("Chinese", "zh"),
    //CORNISH("Cornish", "kw"), // POSSIBLE NOT WORKING
    CHICHEWA("Chichewa", "ny"),
    CROATIAN("Croatian", "hr"),
    CZECH("Czech", "cs"),
    DANISH("Danish", "da"),
    DUTCH("Dutch", "nl"),
    ENGLISH("English", "en"),
    ESPERANTO("Esperanto", "eo"),
    ESTONIAN("Estonian", "et"),
    FINNISH("Finnish", "fi"),
    FRENCH("French", "fr"),
    GALICIAN("Galician", "gl"),
    GEORGIAN("Georgian", "ka"),
    GERMAN("German", "de"),
    GREEK("Greek", "el"),
    GUJARATI("Gujarati", "gu"),
    HAITIAN("Haitian", "ht"),
    HAUSA("Hausa", "ha"),
    HEBREW("Hebrew", "he"),
    HINDI("Hindi", "hi"),
    HUNGARIAN("Hungarian", "hu"),
    //ICELANDIC("Icelandic", "is"), // POSSIBLE NOT WORKING
    IGBO("Igbo", "ig"),
    INDONESIAN("Indonesian", "id"),
    IRISH("Irish", "ga"),
    ITALIAN("Italian", "it"),
    JAPANESE("Japanese", "ja"),
    JAVANESE("Javanese", "jv"),
    KANNADA("Kannada", "kn"),
    KAZAKH("Kazakh", "kk"),
    KHMER("Khmer", "km"),
    KOREAN("Korean", "ko"),
    //KURDISH("Kurdish", "ku"), // POSSIBLE NOT WORKING
    LAO("Lao", "lo"),
    LATIN("Latin", "la"),
    LATVIAN("Latvian", "lv"),
    LITHUANIAN("Lithuanian", "lt"),
    MACEDONIAN("Macedonian", "mk"),
    MALAGASY("Malagasy", "mg"),
    MALAY("Malay", "ms"),
    MALAYALAM("Malayalam", "ml"),
    MALTESE("Maltese", "mt"),
    MAORI("Maori", "mi"),
    MARATHI("Marathi", "mr"),
    MONGOLIAN("Mongolian", "mn"),
    NEPALI("Nepali", "ne"),
    NORWEGIAN("Norwegian", "no"),
    PERSIAN("Persian", "fa"),
    POLISH("Polish", "pl"),
    PORTUGUESE("Portuguese", "pt"),
    PUNJABI("Punjabi", "pa"),
    ROMANIAN("Romanian", "ro"),
    RUSSIAN("Russian", "ru"),
    SERBIAN("Serbian", "sr"),
    SINHALA("Sinhala", "si"),
    SLOVAK("Slovak", "sk"),
    SLOVENIAN("Slovenian", "sl"),
    SOMALI("Somali", "so"),
    SPANISH("Spanish", "es"),
    SUNDANESE("Sundanese", "su"),
    SWAHILI("Swahili", "sw"),
    SWEDISH("Swedish", "sv"),
    TAJIK("Tajik", "tg"),
    TAMIL("Tamil", "ta"),
    TELUGU("Telugu", "te"),
    THAI("Thai", "th"),
    TURKISH("Turkish", "tr"),
    UKRAINIAN("Ukrainian", "uk"),
    URDU("Urdu", "ur"),
    UZBEK("Uzbek", "uz"),
    VIETNAMESE("Vietnamese", "vi"),
    WELSH("Welsh", "cy"),
    YIDDISH("Yiddish", "yi"),
    YORUBA("Yoruba", "yo"),
    ZULU("Zulu", "zu");

    private String langName;
    private String langCode;

    Languages(String langName, String langCode) {
        this.langName = langName;
        this.langCode = langCode;
    }

    public String getLangName() {
        return this.langName;
    }

    public String getLangCode() {
        return this.langCode;
    }

}
