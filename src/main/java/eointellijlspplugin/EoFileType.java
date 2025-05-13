package eointellijlspplugin;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class EoFileType extends LanguageFileType {

    public static final EoFileType INSTANCE = new EoFileType();

    private EoFileType() {
        super(EoLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "EO file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "EO language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "eo";
    }

    @Override
    public Icon getIcon() {
        return EoIcons.FILE;
    }

}