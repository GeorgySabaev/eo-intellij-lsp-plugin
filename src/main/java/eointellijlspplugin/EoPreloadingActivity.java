package eointellijlspplugin;

import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.progress.ProgressIndicator;
import org.wso2.lsp4intellij.IntellijLanguageClient;
import org.wso2.lsp4intellij.client.languageserver.serverdefinition.RawCommandServerDefinition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EoPreloadingActivity extends PreloadingActivity {

    @Override
    public void preload() {
        IntellijLanguageClient.addServerDefinition(new EoServerDefinition("eo", new String[]{"bash", "/home/gasabaev/tmp/sc"}));
    }

    @Override
    public void preload(ProgressIndicator indicator) {
        preload();
    }
}