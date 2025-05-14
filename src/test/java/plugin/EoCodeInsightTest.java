package plugin;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Disposer;
import com.intellij.remoteServer.runtime.ServerConnectionListener;
import com.intellij.testFramework.HeavyPlatformTestCase;
import com.intellij.testFramework.fixtures.*;
import com.intellij.testFramework.fixtures.impl.CodeInsightTestFixtureImpl;
import com.redhat.devtools.lsp4ij.LanguageServerManager;
import com.redhat.devtools.lsp4ij.lifecycle.LanguageServerLifecycleListener;
import com.redhat.devtools.lsp4ij.lifecycle.LanguageServerLifecycleManager;
import org.eclipse.lsp4j.services.LanguageServer;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

public class EoCodeInsightTest extends HeavyPlatformTestCase {

    private CodeInsightTestFixture myFixture;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Create fixture factory and builder
        IdeaTestFixtureFactory fixtureFactory = IdeaTestFixtureFactory.getFixtureFactory();
        TestFixtureBuilder<IdeaProjectTestFixture> fixtureBuilder = fixtureFactory.createFixtureBuilder(getName());
        TempDirTestFixture tempDirFixture = fixtureFactory.createTempDirTestFixture();

        // Create CodeInsightTestFixture
        myFixture = fixtureFactory.createCodeInsightFixture(fixtureBuilder.getFixture(), tempDirFixture);
        ((CodeInsightTestFixtureImpl)myFixture).canChangeDocumentDuringHighlighting(true);
        myFixture.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        try {
            if (myFixture != null) {
                myFixture.tearDown();
                myFixture = null;
            }
        } finally {
            super.tearDown();
        }
    }

    @Test
    public void testAnnotator() throws InterruptedException {

        myFixture.setTestDataPath(getTestDataPath());
        myFixture.copyFileToProject("badapp.eo");
        myFixture.configureByFile("badapp.eo");

        LanguageServerLifecycleManager.getInstance(myFixture.getProject()).addLanguageServerLifecycleListener(LanguageServerLifecycleListener);

        Thread.sleep(3000);

        myFixture.checkHighlighting(false, false, false, true);
    }


    @NotNull
    protected String getTestDataPath() {
        return "src/test/testData";
    }
}
