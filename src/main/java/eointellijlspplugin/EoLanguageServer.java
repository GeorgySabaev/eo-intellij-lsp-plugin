package eointellijlspplugin;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.extensions.PluginId;
import com.redhat.devtools.lsp4ij.server.OSProcessStreamConnectionProvider;
import org.apache.commons.lang3.SystemUtils;

public class EoLanguageServer extends OSProcessStreamConnectionProvider {

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

    public EoLanguageServer() {
        var pluginPath = PluginManagerCore.getPlugin(PluginId.getId("com.example.eo-intellij-lsp-plugin")).getPluginPath();
        var serverPath = pluginPath.resolve(getBinaryName());
//        GeneralCommandLine commandLine = new GeneralCommandLine(String.valueOf(serverPath), "--stdio");
        GeneralCommandLine commandLine = new GeneralCommandLine("echo", "bibibi");
        super.setCommandLine(commandLine);
    }
}