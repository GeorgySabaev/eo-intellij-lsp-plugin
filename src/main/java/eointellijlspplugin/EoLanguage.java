package eointellijlspplugin;

import com.intellij.lang.Language;

public class EoLanguage extends Language {

    public static final EoLanguage INSTANCE = new EoLanguage();

    private EoLanguage() {
        super("Eo");
    }
}