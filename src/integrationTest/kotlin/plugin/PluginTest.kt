package plugin

import com.intellij.codeInsight.daemon.impl.DaemonCodeAnalyzerEx
import com.intellij.driver.sdk.DaemonCodeAnalyzer
import com.intellij.driver.sdk.openFile
import com.intellij.driver.sdk.singleProject
import com.intellij.driver.sdk.ui.components.EditorComponentImpl
import com.intellij.driver.sdk.ui.components.codeEditor
import com.intellij.driver.sdk.ui.components.editor
import com.intellij.driver.sdk.ui.components.editorTabs
import com.intellij.driver.sdk.ui.components.ideFrame
import com.intellij.driver.sdk.ui.components.projectToolWindow
import com.intellij.driver.sdk.ui.components.textField
import com.intellij.driver.sdk.ui.components.tree
import com.intellij.driver.sdk.ui.xQuery
import com.intellij.driver.sdk.wait
import com.intellij.driver.sdk.waitFor
import com.intellij.driver.sdk.waitForCodeAnalysis
import com.intellij.driver.sdk.waitForIndicators
import com.intellij.driver.sdk.waitForProjectOpen
import com.intellij.ide.starter.ci.CIServer
import com.intellij.ide.starter.ci.NoCIServer
import com.intellij.ide.starter.di.di
import com.intellij.ide.starter.driver.engine.runIdeWithDriver
import com.intellij.ide.starter.ide.IdeProductProvider
import com.intellij.ide.starter.models.TestCase
import com.intellij.ide.starter.plugins.PluginConfigurator
import com.intellij.ide.starter.plugins.PluginWithExactVersion
import com.intellij.ide.starter.project.LocalProjectInfo
import com.intellij.ide.starter.project.NoProject
import com.intellij.ide.starter.runner.Starter
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.impl.DocumentMarkupModel
import com.intellij.openapi.project.currentOrDefaultProject
import com.intellij.openapi.project.getOpenedProjects
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBViewport
import com.jetbrains.rd.util.assert
import com.jetbrains.rd.util.string.printToString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import java.io.File
import kotlin.io.path.Path
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class PluginTest {
    init {
        di = DI {
            extend(di)
            bindSingleton<CIServer>(overrides = true) {
                object : CIServer by NoCIServer {
                    override fun reportTestFailure(testName: String, message: String, details: String) {
                        fail { "$testName fails: $message. \n$details" }
                    }
                }
            }
        }
    }
    @Test
    fun simpleTest() {
        Starter.newContext(
            "testExample",
            TestCase(
                IdeProductProvider.IC,
                LocalProjectInfo(Path("src/integrationTest/resources/test-projects/eo-hello-world"))
            ).withVersion("2024.2")
        ).apply {
            val pathToPlugin = System.getProperty("path.to.build.plugin")
            PluginConfigurator(this).installPluginFromFolder(File(pathToPlugin))
            PluginConfigurator(this).installPluginFromPluginManager(PluginWithExactVersion("com.redhat.devtools.lsp4ij", "0.12.0"))
        }.runIdeWithDriver().useDriverAndCloseIde {
            waitForIndicators(5.minutes)
            openFile("app.eo")
            waitForIndicators(5.minutes)
        }
    }
}