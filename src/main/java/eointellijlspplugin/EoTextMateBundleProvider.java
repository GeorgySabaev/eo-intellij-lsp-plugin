package eointellijlspplugin;

import com.intellij.openapi.application.PathManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.textmate.api.TextMateBundleProvider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;


public class EoTextMateBundleProvider implements TextMateBundleProvider {
    @NotNull
    @Override
    public List<PluginBundle> getBundles() {
        try {
            var bundleTmpDir = Files.createTempDirectory(Path.of(PathManager.getTempPath()), "textmate");
            for (String fileToCopy : List.of("package.json", "language-configuration.json", "eo.tmLanguage.json")) {
                URL resource = EoTextMateBundleProvider.class.getClassLoader().getResource("textmate_bundle/" + fileToCopy);
                try (InputStream resourceStream = Objects.requireNonNull(resource).openStream()) {
                    Path target = bundleTmpDir.resolve(fileToCopy);
                    Files.createDirectories(target.getParent());
                    Files.copy(resourceStream, target);
                }
            }
            PluginBundle bundle = new PluginBundle("Eolang", Objects.requireNonNull(bundleTmpDir));
            return List.of(bundle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}