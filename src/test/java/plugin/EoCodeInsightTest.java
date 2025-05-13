package plugin;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase;
import eointellijlspplugin.EoFileType;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class EoCodeInsightTest extends LightPlatformCodeInsightFixture4TestCase {

    @Override
    protected String getTestDataPath() {
        return "src/test/testData";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        FileTypeManager fileTypeManager = FileTypeManager.getInstance();
        ApplicationManager.getApplication().runWriteAction(() ->
                fileTypeManager.associateExtension(EoFileType.INSTANCE, "eo")
        );
    }


    @Test
    public void testAnnotator() throws ExecutionException, InterruptedException {

        myFixture.configureByFiles("badapp.eo");
        Thread.sleep(3000);

        System.out.println(myFixture.getFile().getFileType());

        System.out.println(PluginManager.getLoadedPlugins().stream().map((x) -> x.getName()).toList());

        myFixture.checkHighlighting(false, false, false, true);
    }
}
