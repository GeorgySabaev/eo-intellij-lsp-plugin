package eointellijlspplugin;

import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.ClientInfo;
import org.eclipse.lsp4j.SemanticTokensCapabilities;
import org.wso2.lsp4intellij.client.languageserver.serverdefinition.RawCommandServerDefinition;

import java.util.List;

public class EoServerDefinition extends RawCommandServerDefinition {
    public EoServerDefinition(String ext, String[] command) {
        super(ext, command);
    }

    @Override
    public void customizeInitializeParams(InitializeParams params) {
        var capabilities = params.getCapabilities();
        var textDocument = capabilities.getTextDocument();
        var semanticTokensCapabilities = new SemanticTokensCapabilities();
        semanticTokensCapabilities.setDynamicRegistration(true);
        semanticTokensCapabilities.setTokenTypes(List.of(
                "namespace",
                "type",
                "class",
                "enum",
                "interface",
                "struct",
                "typeParameter",
                "parameter",
                "variable",
                "property",
                "enumMember",
                "event",
                "function",
                "method",
                "macro",
                "keyword",
                "modifier",
                "comment",
                "string",
                "number",
                "regexp",
                "operator"
        ));
        semanticTokensCapabilities.setTokenModifiers(List.of(
                "declaration",
                "definition",
                "readonly",
                "static",
                "deprecated",
                "abstract",
                "async",
                "modification",
                "documentation",
                "defaultLibrary"
        ));
        semanticTokensCapabilities.setFormats(List.of(
                "relative"
        ));
        textDocument.setSemanticTokens(semanticTokensCapabilities);
        capabilities.setTextDocument(textDocument);
        params.setCapabilities(capabilities);
    }
}
