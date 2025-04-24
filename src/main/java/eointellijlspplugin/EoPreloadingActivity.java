package eointellijlspplugin;

import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.progress.ProgressIndicator;
import org.wso2.lsp4intellij.IntellijLanguageClient;
import com.intellij.ide.plugins.PluginManagerCore;

import org.apache.commons.lang3.SystemUtils;

public class EoPreloadingActivity extends PreloadingActivity {

    private String getBinaryName(){
        if (SystemUtils.IS_OS_WINDOWS) {
            return "server-win.exe";
        }
        if (SystemUtils.IS_OS_LINUX) {
            return "server-linux";
        }
        if (SystemUtils.IS_OS_MAC_OSX) {
            return "server-macos";
        }
        throw new RuntimeException("OS not supported.");
    }

    @Override
    public void preload() {
        var pluginPath = PluginManagerCore.getPlugin(PluginId.getId("com.example.eo-intellij-lsp-plugin")).getPluginPath();
        var serverPath = pluginPath.resolve(getBinaryName());
        IntellijLanguageClient.addServerDefinition(new EoServerDefinition("eo", new String[]{serverPath.toString(), "--stdio"}));
    }

    @Override
    public void preload(ProgressIndicator indicator) {
        preload();
    }
}