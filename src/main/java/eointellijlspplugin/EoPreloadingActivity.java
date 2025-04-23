package eointellijlspplugin;

import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.progress.ProgressIndicator;
import org.wso2.lsp4intellij.IntellijLanguageClient;
import org.wso2.lsp4intellij.client.languageserver.serverdefinition.RawCommandServerDefinition;
import com.intellij.ide.plugins.PluginManagerCore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EoPreloadingActivity extends PreloadingActivity {

    @Override
    public void preload() {
        var pluginPath = PluginManagerCore.getPlugin(PluginId.getId("com.example.eo-intellij-lsp-plugin")).getPluginPath();
        var serverPath = pluginPath.resolve("server-linux");
        IntellijLanguageClient.addServerDefinition(new EoServerDefinition("eo", new String[]{serverPath.toString(), "--stdio"}));
    }

    @Override
    public void preload(ProgressIndicator indicator) {
        preload();
    }
}