package com.levelrin;

import com.levelrin.antlr.generated.Dart2Parser;
import com.levelrin.antlr.generated.Dart2ParserBaseVisitor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Return type is a JSON.
 * All of it must have $.text attribute.
 */
public final class DartVisitor extends Dart2ParserBaseVisitor<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DartVisitor.class);

    private final String indentUnit = "  ";

    /**
     * Whenever we visit a rule, we will record its count.
     * The purpose is to know what went down when we visit a child context.
     * Ex: For example, we want to format the constructor call differently
     *     if the child also calls the constructor (nested constructor calls).
     *     We can identify the nested constructor calls by checking the constructor call counts
     *     after visiting the child context.
     * Key - Simple class name of the context. Ex: CompilationUnitContext.
     * Value - Number of visits.
     */
    private final Map<String, Integer> ruleVisitCounts = new HashMap<>();

    private int currentIndentLevel = 0;

    private final CommonTokenStream tokens;

    public DartVisitor(final CommonTokenStream tokens) {
        this.tokens = tokens;
    }

    @Override
    public String visitCompilationUnit(final Dart2Parser.CompilationUnitContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitCompilationUnit` text: {}", context.getText());
        }
        final Dart2Parser.LibraryDeclarationContext libraryDeclarationContext = context.libraryDeclaration();
        final Dart2Parser.PartDeclarationContext partDeclarationContext =  context.partDeclaration();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final Dart2Parser.StatementContext statementContext = context.statement();
        if (partDeclarationContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitCompilationUnit -> partDeclaration");
        }
        if (exprContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitCompilationUnit -> expr");
        }
        if (statementContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitCompilationUnit -> statement");
        }
        final StringBuilder text = new StringBuilder();
        if (libraryDeclarationContext != null) {
            final String libraryDeclarationText = this.visit(libraryDeclarationContext);
            text.append(libraryDeclarationText);
        }
        text.append("\n");
        return text.toString();
    }

    @Override
    public String visitLibraryDeclaration(final Dart2Parser.LibraryDeclarationContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitLibraryDeclaration` text: {}", context.getText());
        }
        final Dart2Parser.LibraryNameContext libraryNameContext = context.libraryName();
        final List<Dart2Parser.ImportOrExportContext> importOrExportContexts = context.importOrExport();
        final List<Dart2Parser.PartDirectiveContext> partDirectiveContexts = context.partDirective();
        final List<Dart2Parser.MetadataContext> metadataContexts = context.metadata();
        final List<Dart2Parser.TopLevelDeclarationContext> topLevelDeclarationContexts = context.topLevelDeclaration();
        if (libraryNameContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryDeclaration -> libraryName");
        }
        if (!partDirectiveContexts.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryDeclaration -> partDirective");
        }
        final StringBuilder text = new StringBuilder();
        if (!importOrExportContexts.isEmpty()) {
            for (final Dart2Parser.ImportOrExportContext importOrExportContext : importOrExportContexts) {
                final String importOrExportText = this.visit(importOrExportContext);
                text.append(importOrExportText);
                text.append("\n");
            }
            text.append("\n");
        }
        if (!metadataContexts.isEmpty()) {
            for (final Dart2Parser.MetadataContext metadataContext : metadataContexts) {
                if (!metadataContext.getText().isEmpty()) {
                    throw new UnsupportedOperationException(
                        String.format(
                            "The following metadata is not supported yet: %s",
                            metadataContext.getText()
                        )
                    );
                }
            }
        }
        if (!topLevelDeclarationContexts.isEmpty()) {
            for (int index = 0; index < topLevelDeclarationContexts.size(); index++) {
                if (index > 0) {
                    text.append("\n\n");
                }
                final Dart2Parser.TopLevelDeclarationContext topLevelDeclarationContext = topLevelDeclarationContexts.get(index);
                final String topLevelDeclarationText = this.visit(topLevelDeclarationContext);
                text.append(topLevelDeclarationText);
            }
        }
        return text.toString();
    }

    @Override
    public String visitTopLevelDeclaration(final Dart2Parser.TopLevelDeclarationContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTopLevelDeclaration` text: {}", context.getText());
        }
        final Dart2Parser.ClassDeclarationContext classDeclarationContext = context.classDeclaration();
        final Dart2Parser.MixinDeclarationContext mixinDeclarationContext = context.mixinDeclaration();
        final Dart2Parser.ExtensionDeclarationContext extensionDeclarationContext = context.extensionDeclaration();
        final Dart2Parser.EnumTypeContext enumTypeContext = context.enumType();
        final Dart2Parser.TypeAliasContext typeAliasContext = context.typeAlias();
        final Dart2Parser.FunctionSignatureContext functionSignatureContext = context.functionSignature();
        final Dart2Parser.GetterSignatureContext getterSignatureContext = context.getterSignature();
        final Dart2Parser.SetterSignatureContext setterSignatureContext = context.setterSignature();
        final Dart2Parser.FunctionBodyContext functionBodyContext = context.functionBody();
        final Dart2Parser.TypeContext typeContext = context.type();
        final Dart2Parser.StaticFinalDeclarationListContext staticFinalDeclarationListContext = context.staticFinalDeclarationList();
        final Dart2Parser.InitializedIdentifierListContext initializedIdentifierListContext = context.initializedIdentifierList();
        final Dart2Parser.VarOrTypeContext varOrTypeContext = context.varOrType();
        if (extensionDeclarationContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTopLevelDeclaration -> extensionDeclaration");
        }
        if (enumTypeContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTopLevelDeclaration -> enumType");
        }
        if (typeAliasContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTopLevelDeclaration -> typeAlias");
        }
        if (getterSignatureContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTopLevelDeclaration -> getterSignature");
        }
        if (setterSignatureContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTopLevelDeclaration -> setterSignature");
        }
        if (typeContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTopLevelDeclaration -> type");
        }
        if (staticFinalDeclarationListContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTopLevelDeclaration -> staticFinalDeclarationList");
        }
        if (initializedIdentifierListContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTopLevelDeclaration -> initializedIdentifierList");
        }
        if (varOrTypeContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTopLevelDeclaration -> varOrType");
        }
        final StringBuilder text = new StringBuilder();
        if (classDeclarationContext != null) {
            final String classDeclarationText = this.visit(classDeclarationContext);
            text.append(classDeclarationText);
        }
        if (mixinDeclarationContext != null) {
            text.append(this.visit(mixinDeclarationContext));
        }
        if (functionSignatureContext != null) {
            final String functionSignatureText = this.visit(functionSignatureContext);
            text.append(functionSignatureText);
        }
        if (functionBodyContext != null) {
            text.append(" ");
            final String functionBodyText = this.visit(functionBodyContext);
            text.append(functionBodyText);
        }
        return text.toString();
    }

    @Override
    public String visitMixinDeclaration(final Dart2Parser.MixinDeclarationContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitMixinDeclaration` text: {}", context.getText());
        }
        final TerminalNode mixinTerminal = context.MIXIN_();
        final Dart2Parser.TypeIdentifierContext typeIdentifierContext = context.typeIdentifier();
        final Dart2Parser.TypeParametersContext typeParametersContext = context.typeParameters();
        final TerminalNode onTerminal = context.ON_();
        // todo: use `typeNotVoidListContext` with tests.
        final Dart2Parser.TypeNotVoidListContext typeNotVoidListContext = context.typeNotVoidList();
        final Dart2Parser.InterfacesContext interfacesContext = context.interfaces();
        final TerminalNode obcTerminal = context.OBC();
        final List<Dart2Parser.MetadataContext> metadataContexts = context.metadata();
        final List<Dart2Parser.ClassMemberDeclarationContext> classMemberDeclarationContexts = context.classMemberDeclaration();
        final TerminalNode cbcTerminal = context.CBC();
        if (typeParametersContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitMixinDeclaration -> typeParameters");
        }
        if (onTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitMixinDeclaration -> on");
        }
        if (interfacesContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitMixinDeclaration -> interfaces");
        }
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(mixinTerminal));
        text.append(" ");
        text.append(this.visit(typeIdentifierContext));
        text.append(" ");
        text.append(this.visit(obcTerminal));
        for (int index = 0; index < classMemberDeclarationContexts.size(); index++) {
            final Dart2Parser.MetadataContext metadataContext = metadataContexts.get(index);
            final Dart2Parser.ClassMemberDeclarationContext classMemberDeclarationContext = classMemberDeclarationContexts.get(index);
            if (!metadataContext.getText().isEmpty()) {
                throw new UnsupportedOperationException("The following parsing path is not supported yet: visitMixinDeclaration -> metadata");
            }
            text.append("\n\n");
            this.currentIndentLevel++;
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            text.append(this.visit(classMemberDeclarationContext));
            this.currentIndentLevel--;
            if (index == classMemberDeclarationContexts.size() - 1) {
                text.append("\n\n");
                text.append(this.indentUnit.repeat(this.currentIndentLevel));
            }
        }
        text.append(this.visit(cbcTerminal));
        return text.toString();
    }

    @Override
    public String visitTypeIdentifier(final Dart2Parser.TypeIdentifierContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTypeIdentifier` text: {}", context.getText());
        }
        final TerminalNode identifierTerminal = context.IDENTIFIER();
        final TerminalNode asyncTerminal = context.ASYNC_();
        final TerminalNode hideTerminal = context.HIDE_();
        final TerminalNode ofTerminal = context.OF_();
        final TerminalNode onTerminal = context.ON_();
        final TerminalNode showTerminal = context.SHOW_();
        final TerminalNode syncTerminal = context.SYNC_();
        final TerminalNode awaitTerminal = context.AWAIT_();
        final TerminalNode yieldTerminal = context.YIELD_();
        final TerminalNode dynamicTerminal = context.DYNAMIC_();
        final TerminalNode nativeTerminal = context.NATIVE_();
        final TerminalNode functionTerminal = context.FUNCTION_();
        final StringBuilder text = new StringBuilder();
        if (identifierTerminal != null) {
            text.append(this.visit(identifierTerminal));
        } else if (asyncTerminal != null) {
            text.append(this.visit(asyncTerminal));
        } else if (hideTerminal != null) {
            text.append(this.visit(hideTerminal));
        } else if (ofTerminal != null) {
            text.append(this.visit(ofTerminal));
        } else if (onTerminal != null) {
            text.append(this.visit(onTerminal));
        } else if (showTerminal != null) {
            text.append(this.visit(showTerminal));
        } else if (syncTerminal != null) {
            text.append(this.visit(syncTerminal));
        } else if (awaitTerminal != null) {
            text.append(this.visit(awaitTerminal));
        } else if (yieldTerminal != null) {
            text.append(this.visit(yieldTerminal));
        } else if (dynamicTerminal != null) {
            text.append(this.visit(dynamicTerminal));
        } else if (nativeTerminal != null) {
            text.append(this.visit(nativeTerminal));
        } else if (functionTerminal != null) {
            text.append(this.visit(functionTerminal));
        }
        return text.toString();
    }

    @Override
    public String visitImportOrExport(final Dart2Parser.ImportOrExportContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitImportOrExport` text: {}", context.getText());
        }
        final Dart2Parser.LibraryImportContext libraryImportContext = context.libraryImport();
        final Dart2Parser.LibraryExportContext libraryExportContext = context.libraryExport();
        if (libraryExportContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitImportOrExport -> libraryExport");
        }
        final StringBuilder text = new StringBuilder();
        if (libraryImportContext != null) {
            final String libraryImportText = this.visit(libraryImportContext);
            text.append(libraryImportText);
        }
        return text.toString();
    }

    @Override
    public String visitLibraryImport(final Dart2Parser.LibraryImportContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitLibraryImport` text: {}", context.getText());
        }
        final Dart2Parser.MetadataContext metadataContext = context.metadata();
        final Dart2Parser.ImportSpecificationContext importSpecificationContext = context.importSpecification();
        if (!metadataContext.getText().isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryImport -> metadata");
        }
        final StringBuilder text = new StringBuilder();
        final String importSpecificationText = this.visit(importSpecificationContext);
        text.append(importSpecificationText);
        return text.toString();
    }

    @Override
    public String visitImportSpecification(final Dart2Parser.ImportSpecificationContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitImportSpecification` text: {}", context.getText());
        }
        final TerminalNode importTerminal = context.IMPORT_();
        final Dart2Parser.ConfigurableUriContext configurableUriContext = context.configurableUri();
        final TerminalNode deferredTerminal = context.DEFERRED_();
        final TerminalNode asTerminal = context.AS_();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final List<Dart2Parser.CombinatorContext> combinatorContexts = context.combinator();
        final TerminalNode scTerminal = context.SC();
        if (deferredTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitImportSpecification -> deferred");
        }
        if (!combinatorContexts.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitImportSpecification -> combinator");
        }
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(importTerminal));
        text.append(" ");
        text.append(this.visit(configurableUriContext));
        if (asTerminal != null) {
            text.append(" ");
            text.append(this.visit(asTerminal));
            text.append(" ");
            text.append(this.visit(identifierContext));
        }
        text.append(this.visit(scTerminal));
        return text.toString();
    }

    @Override
    public String visitConfigurableUri(final Dart2Parser.ConfigurableUriContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitConfigurableUri` text: {}", context.getText());
        }
        final Dart2Parser.UriContext uriContext = context.uri();
        final List<Dart2Parser.ConfigurationUriContext> configurationUriContexts = context.configurationUri();
        if (!configurationUriContexts.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitConfigurableUri -> configurationUri");
        }
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(uriContext));
        return text.toString();
    }

    @Override
    public String visitUri(final Dart2Parser.UriContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitUri` text: {}", context.getText());
        }
        final Dart2Parser.StringLiteralContext stringLiteralContext = context.stringLiteral();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(stringLiteralContext));
        return text.toString();
    }

    @Override
    public String visitStringLiteral(final Dart2Parser.StringLiteralContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitStringLiteral` text: {}", context.getText());
        }
        final List<Dart2Parser.MultilineStringContext> multilineStringContexts = context.multilineString();
        final List<Dart2Parser.SingleLineStringContext> singleLineStringContexts = context.singleLineString();
        if (!multilineStringContexts.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitStringLiteral -> multilineString");
        }
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.SingleLineStringContext singleLineStringContext : singleLineStringContexts) {
            text.append(this.visit(singleLineStringContext));
        }
        return text.toString();
    }

    @Override
    public String visitSingleLineString(final Dart2Parser.SingleLineStringContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitSingleLineString` text: {}", context.getText());
        }
        final TerminalNode singleLineString = context.SingleLineString();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(singleLineString));
        return text.toString();
    }

    @Override
    public String visitClassDeclaration(final Dart2Parser.ClassDeclarationContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitClassDeclaration` text: {}", context.getText());
        }
        final TerminalNode abstractTerminal = context.ABSTRACT_();
        final TerminalNode classTerminal = context.CLASS_();
        // It can be a class name.
        final Dart2Parser.TypeIdentifierContext typeIdentifierContext = context.typeIdentifier();
        final Dart2Parser.TypeParametersContext typeParametersContext = context.typeParameters();
        final Dart2Parser.SuperclassContext superclassContext = context.superclass();
        final Dart2Parser.InterfacesContext interfacesContext = context.interfaces();
        final TerminalNode obcTerminal = context.OBC();
        final List<Dart2Parser.MetadataContext> metadataContexts = context.metadata();
        final List<Dart2Parser.ClassMemberDeclarationContext> classMemberDeclarationContexts = context.classMemberDeclaration();
        final TerminalNode cbcTerminal = context.CBC();
        final Dart2Parser.MixinApplicationClassContext mixinApplicationClassContext = context.mixinApplicationClass();
        if (mixinApplicationClassContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitClassDeclaration -> mixinApplicationClass");
        }
        final StringBuilder text = new StringBuilder();
        if (abstractTerminal != null) {
            text.append(this.visit(abstractTerminal));
            text.append(" ");
        }
        text.append(this.visit(classTerminal));
        text.append(" ");
        text.append(this.visit(typeIdentifierContext));
        if (typeParametersContext == null) {
            text.append(" ");
        } else {
            text.append(this.visit(typeParametersContext));
            text.append(" ");
        }
        if (superclassContext != null) {
            text.append(this.visit(superclassContext));
            text.append(" ");
        }
        if (interfacesContext != null) {
            text.append(this.visit(interfacesContext));
            text.append(" ");
        }
        text.append(this.visit(obcTerminal));
        text.append("\n\n");
        this.currentIndentLevel++;
        if (!classMemberDeclarationContexts.isEmpty()) {
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
        }
        for (int index = 0; index < classMemberDeclarationContexts.size(); index++) {
            final Dart2Parser.MetadataContext metadataContext = metadataContexts.get(index);
            final Dart2Parser.ClassMemberDeclarationContext classMemberDeclarationContext = classMemberDeclarationContexts.get(index);
            if (!metadataContext.getText().isEmpty()) {
                text.append(this.visit(metadataContext));
                text.append("\n");
                text.append(this.indentUnit.repeat(this.currentIndentLevel));
            }
            text.append(this.visit(classMemberDeclarationContext));
            text.append("\n\n");
            if (index < classMemberDeclarationContexts.size() - 1) {
                text.append(this.indentUnit.repeat(this.currentIndentLevel));
            }
        }
        this.currentIndentLevel--;
        text.append(this.indentUnit.repeat(this.currentIndentLevel));
        text.append(this.visit(cbcTerminal));
        return text.toString();
    }

    @Override
    public String visitInterfaces(final Dart2Parser.InterfacesContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitInterfaces` text: {}", context.getText());
        }
        final TerminalNode implementsTerminal = context.IMPLEMENTS_();
        final Dart2Parser.TypeNotVoidListContext typeNotVoidListContext = context.typeNotVoidList();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(implementsTerminal));
        text.append(" ");
        text.append(this.visit(typeNotVoidListContext));
        return text.toString();
    }

    @Override
    public String visitTypeParameters(final Dart2Parser.TypeParametersContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTypeParameters` text: {}", context.getText());
        }
        final TerminalNode ltTerminal = context.LT();
        final List<Dart2Parser.TypeParameterContext> typeParameterContexts = context.typeParameter();
        final List<TerminalNode> cTerminals = context.C();
        final TerminalNode gtTerminal = context.GT();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(ltTerminal));
        final Dart2Parser.TypeParameterContext firstTypeParameter = typeParameterContexts.get(0);
        text.append(this.visit(firstTypeParameter));
        for (int index = 0; index < cTerminals.size(); index++) {
            final TerminalNode cTerminal = cTerminals.get(index);
            final Dart2Parser.TypeParameterContext typeParameterContext = typeParameterContexts.get(index + 1);
            text.append(this.visit(cTerminal));
            text.append(" ");
            text.append(this.visit(typeParameterContext));
        }
        text.append(this.visit(gtTerminal));
        return text.toString();
    }

    @Override
    public String visitTypeParameter(final Dart2Parser.TypeParameterContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTypeParameter` text: {}", context.getText());
        }
        final Dart2Parser.MetadataContext metadataContext = context.metadata();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final TerminalNode extendsTerminal = context.EXTENDS_();
        final Dart2Parser.TypeNotVoidContext typeNotVoidContext = context.typeNotVoid();
        final StringBuilder text = new StringBuilder();
        if (!metadataContext.getText().isEmpty()) {
            text.append(this.visit(metadataContext));
            text.append(" ");
        }
        text.append(this.visit(identifierContext));
        if (extendsTerminal != null) {
            text.append(" ");
            text.append(this.visit(extendsTerminal));
            text.append(" ");
            text.append(this.visit(typeNotVoidContext));
        }
        return text.toString();
    }

    @Override
    public String visitMetadata(final Dart2Parser.MetadataContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitMetadata` text: {}", context.getText());
        }
        final List<TerminalNode> atTerminals = context.AT();
        final List<Dart2Parser.MetadatumContext> metadatumContexts = context.metadatum();
        final StringBuilder text = new StringBuilder();
        for (int index = 0; index < atTerminals.size(); index++) {
            final TerminalNode atTerminal = atTerminals.get(index);
            final Dart2Parser.MetadatumContext metadatumContext = metadatumContexts.get(index);
            text.append(this.visit(atTerminal));
            text.append(this.visit(metadatumContext));
        }
        return text.toString();
    }

    @Override
    public String visitMetadatum(final Dart2Parser.MetadatumContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitMetadatum` text: {}", context.getText());
        }
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final Dart2Parser.QualifiedNameContext qualifiedNameContext = context.qualifiedName();
        // todo: use `constructorDesignationContext`, `argumentsContext`, and `argumentsContext` with tests.
        final Dart2Parser.ConstructorDesignationContext constructorDesignationContext = context.constructorDesignation();
        final Dart2Parser.ArgumentsContext argumentsContext = context.arguments();
        final StringBuilder text = new StringBuilder();
        if (identifierContext != null) {
            text.append(this.visit(identifierContext));
        } else if (qualifiedNameContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitMetadatum -> qualifiedName");
        } else {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitMetadatum -> constructorDesignation");
        }
        return text.toString();
    }

    @Override
    public String visitSuperclass(final Dart2Parser.SuperclassContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitSuperclass` text: {}", context.getText());
        }
        final TerminalNode extendsTerminal = context.EXTENDS_();
        final Dart2Parser.TypeNotVoidContext typeNotVoidContext = context.typeNotVoid();
        final Dart2Parser.MixinsContext mixinsContext = context.mixins();
        final StringBuilder text = new StringBuilder();
        if (extendsTerminal != null) {
            text.append(this.visit(extendsTerminal));
            text.append(" ");
            text.append(this.visit(typeNotVoidContext));
            if (mixinsContext != null) {
                text.append(" ");
                text.append(this.visit(mixinsContext));
            }
        } else {
            text.append(this.visit(mixinsContext));
        }
        return text.toString();
    }

    @Override
    public String visitMixins(final Dart2Parser.MixinsContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitMixins` text: {}", context.getText());
        }
        final TerminalNode withTerminal = context.WITH_();
        final Dart2Parser.TypeNotVoidListContext typeNotVoidListContext = context.typeNotVoidList();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(withTerminal));
        text.append(" ");
        text.append(this.visit(typeNotVoidListContext));
        return text.toString();
    }

    @Override
    public String visitTypeNotVoidList(final Dart2Parser.TypeNotVoidListContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTypeNotVoidList` text: {}", context.getText());
        }
        final List<Dart2Parser.TypeNotVoidContext> typeNotVoidContexts = context.typeNotVoid();
        final List<TerminalNode> cTerminals = context.C();
        final StringBuilder text = new StringBuilder();
        final Dart2Parser.TypeNotVoidContext firstTypeNotVoidContext = typeNotVoidContexts.get(0);
        text.append(this.visit(firstTypeNotVoidContext));
        for (int index = 0; index < cTerminals.size(); index++) {
            final TerminalNode cTerminal = cTerminals.get(index);
            final Dart2Parser.TypeNotVoidContext typeNotVoidContext = typeNotVoidContexts.get(index + 1);
            text.append(this.visit(cTerminal));
            text.append(" ");
            text.append(this.visit(typeNotVoidContext));
        }
        return text.toString();
    }

    @Override
    public String visitTypeNotVoid(final Dart2Parser.TypeNotVoidContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTypeNotVoid` text: {}", context.getText());
        }
        final Dart2Parser.FunctionTypeContext functionTypeContext = context.functionType();
        // todo: use `quTerminal` with tests.
        final TerminalNode quTerminal = context.QU();
        final Dart2Parser.TypeNotVoidNotFunctionContext typeNotVoidNotFunctionContext = context.typeNotVoidNotFunction();
        if (functionTypeContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTypeNotVoid -> functionType");
        }
        final StringBuilder text = new StringBuilder();
        if (typeNotVoidNotFunctionContext != null) {
            text.append(this.visit(typeNotVoidNotFunctionContext));
        }
        return text.toString();
    }

    @Override
    public String visitClassMemberDeclaration(final Dart2Parser.ClassMemberDeclarationContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitClassMemberDeclaration` text: {}", context.getText());
        }
        final Dart2Parser.DeclarationContext declarationContext = context.declaration();
        final TerminalNode scTerminal = context.SC();
        final Dart2Parser.MethodSignatureContext methodSignatureContext = context.methodSignature();
        final Dart2Parser.FunctionBodyContext functionBodyContext = context.functionBody();
        final StringBuilder text = new StringBuilder();
        if (declarationContext != null) {
            final String declarationText = this.visit(declarationContext);
            text.append(declarationText);
            text.append(this.visit(scTerminal));
        }
        if (methodSignatureContext != null) {
            final String methodSignatureText = this.visit(methodSignatureContext);
            text.append(methodSignatureText);
            text.append(" ");
            final String functionBodyText = this.visit(functionBodyContext);
            text.append(functionBodyText);
        }
        return text.toString();
    }

    @Override
    public String visitMethodSignature(final Dart2Parser.MethodSignatureContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitMethodSignature` text: {}", context.getText());
        }
        final Dart2Parser.ConstructorSignatureContext constructorSignatureContext = context.constructorSignature();
        final Dart2Parser.InitializersContext initializersContext = context.initializers();
        final Dart2Parser.FactoryConstructorSignatureContext factoryConstructorSignatureContext = context.factoryConstructorSignature();
        final TerminalNode staticTerminal = context.STATIC_();
        final Dart2Parser.FunctionSignatureContext functionSignatureContext = context.functionSignature();
        final Dart2Parser.GetterSignatureContext getterSignatureContext = context.getterSignature();
        final Dart2Parser.SetterSignatureContext setterSignatureContext = context.setterSignature();
        final Dart2Parser.OperatorSignatureContext operatorSignatureContext = context.operatorSignature();
        if (constructorSignatureContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitMethodSignature -> constructorSignature");
        }
        if (initializersContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitMethodSignature -> initializers");
        }
        if (operatorSignatureContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitMethodSignature -> operatorSignature");
        }
        final StringBuilder text = new StringBuilder();
        if (factoryConstructorSignatureContext != null) {
            final String factoryConstructorSignatureText = this.visit(factoryConstructorSignatureContext);
            text.append(factoryConstructorSignatureText);
        }
        if (staticTerminal != null) {
            text.append(this.visit(staticTerminal));
            text.append(" ");
        }
        if (getterSignatureContext != null) {
            text.append(this.visit(getterSignatureContext));
        }
        if (setterSignatureContext != null) {
            text.append(this.visit(setterSignatureContext));
        }
        if (functionSignatureContext != null) {
            final String functionSignatureText = this.visit(functionSignatureContext);
            text.append(functionSignatureText);
        }
        return text.toString();
    }

    @Override
    public String visitSetterSignature(final Dart2Parser.SetterSignatureContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitSetterSignature` text: {}", context.getText());
        }
        final Dart2Parser.TypeContext typeContext = context.type();
        final TerminalNode setTerminal = context.SET_();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final Dart2Parser.FormalParameterListContext formalParameterListContext = context.formalParameterList();
        final StringBuilder text = new StringBuilder();
        if (typeContext != null) {
            text.append(this.visit(typeContext));
            text.append(" ");
        }
        text.append(this.visit(setTerminal));
        text.append(" ");
        text.append(this.visit(identifierContext));
        text.append(this.visit(formalParameterListContext));
        return text.toString();
    }

    @Override
    public String visitGetterSignature(final Dart2Parser.GetterSignatureContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitGetterSignature` text: {}", context.getText());
        }
        final Dart2Parser.TypeContext typeContext = context.type();
        final TerminalNode getTerminal = context.GET_();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final StringBuilder text = new StringBuilder();
        if (typeContext != null) {
            text.append(this.visit(typeContext));
            text.append(" ");
        }
        text.append(this.visit(getTerminal));
        text.append(" ");
        text.append(this.visit(identifierContext));
        return text.toString();
    }

    @Override
    public String visitFactoryConstructorSignature(final Dart2Parser.FactoryConstructorSignatureContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFactoryConstructorSignature` text: {}", context.getText());
        }
        final TerminalNode constTerminal = context.CONST_();
        final TerminalNode factoryTerminal = context.FACTORY_();
        final Dart2Parser.ConstructorNameContext constructorNameContext = context.constructorName();
        final Dart2Parser.FormalParameterListContext formalParameterListContext = context.formalParameterList();
        if (constTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFactoryConstructorSignature -> const");
        }
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(factoryTerminal));
        text.append(" ");
        text.append(this.visit(constructorNameContext));
        final String formalParameterListText = this.visit(formalParameterListContext);
        text.append(formalParameterListText);
        return text.toString();
    }

    @Override
    public String visitConstructorName(final Dart2Parser.ConstructorNameContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitConstructorName` text: {}", context.getText());
        }
        final Dart2Parser.TypeIdentifierContext typeIdentifierContext = context.typeIdentifier();
        final TerminalNode dTerminal = context.D();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(typeIdentifierContext));
        if (dTerminal != null) {
            text.append(this.visit(dTerminal));
            text.append(this.visit(identifierContext));
        }
        return text.toString();
    }

    @Override
    public String visitFunctionSignature(final Dart2Parser.FunctionSignatureContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFunctionSignature` text: {}", context.getText());
        }
        final Dart2Parser.TypeContext typeContext = context.type();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final Dart2Parser.FormalParameterPartContext formalParameterPartContext = context.formalParameterPart();
        final StringBuilder text = new StringBuilder();
        if (typeContext != null) {
            text.append(this.visit(typeContext));
            text.append(" ");
        }
        text.append(this.visit(identifierContext));
        final String formalParameterText = this.visit(formalParameterPartContext);
        text.append(formalParameterText);
        return text.toString();
    }

    @Override
    public String visitFormalParameterPart(final Dart2Parser.FormalParameterPartContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFormalParameterPart` text: {}", context.getText());
        }
        final Dart2Parser.TypeParametersContext typeParametersContext = context.typeParameters();
        final Dart2Parser.FormalParameterListContext formalParameterListContext = context.formalParameterList();
        final StringBuilder text = new StringBuilder();
        if (typeParametersContext != null) {
            text.append(this.visit(typeParametersContext));
        }
        final String formalParameterListText = this.visit(formalParameterListContext);
        text.append(formalParameterListText);
        return text.toString();
    }

    @Override
    public String visitFormalParameterList(final Dart2Parser.FormalParameterListContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFormalParameterList` text: {}", context.getText());
        }
        final TerminalNode opTerminal = context.OP();
        final TerminalNode cpTerminal = context.CP();
        final Dart2Parser.NormalFormalParametersContext normalFormalParametersContext = context.normalFormalParameters();
        final TerminalNode cTerminal = context.C();
        final Dart2Parser.OptionalOrNamedFormalParametersContext optionalOrNamedFormalParametersContext = context.optionalOrNamedFormalParameters();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(opTerminal));
        if (normalFormalParametersContext != null) {
            final String normalFormalParametersText = this.visit(normalFormalParametersContext);
            text.append(normalFormalParametersText);
        }
        if (cTerminal != null) {
            text.append(this.visit(cTerminal));
            text.append(" ");
        }
        if (optionalOrNamedFormalParametersContext != null) {
            text.append(this.visit(optionalOrNamedFormalParametersContext));
        }
        text.append(this.visit(cpTerminal));
        return text.toString();
    }

    @Override
    public String visitOptionalOrNamedFormalParameters(final Dart2Parser.OptionalOrNamedFormalParametersContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitOptionalOrNamedFormalParameters` text: {}", context.getText());
        }
        final Dart2Parser.OptionalPositionalFormalParametersContext optionalPositionalFormalParametersContext = context.optionalPositionalFormalParameters();
        final Dart2Parser.NamedFormalParametersContext namedFormalParametersContext = context.namedFormalParameters();
        final StringBuilder text = new StringBuilder();
        if (optionalPositionalFormalParametersContext != null) {
            text.append(this.visit(optionalPositionalFormalParametersContext));
        }
        if (namedFormalParametersContext != null) {
            text.append(this.visit(namedFormalParametersContext));
        }
        return text.toString();
    }

    @Override
    public String visitOptionalPositionalFormalParameters(final Dart2Parser.OptionalPositionalFormalParametersContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitOptionalPositionalFormalParameters` text: {}", context.getText());
        }
        final TerminalNode obTerminal = context.OB();
        final List<Dart2Parser.DefaultFormalParameterContext> defaultFormalParameterContexts = context.defaultFormalParameter();
        final List<TerminalNode> cTerminals = context.C();
        final TerminalNode cbTerminal = context.CB();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(obTerminal));
        final Dart2Parser.DefaultFormalParameterContext firstDefaultFormalParameterContext = defaultFormalParameterContexts.get(0);
        text.append(this.visit(firstDefaultFormalParameterContext));
        if (defaultFormalParameterContexts.size() > 1) {
            for (int index = 0; index < cTerminals.size(); index++) {
                final TerminalNode cTerminal = cTerminals.get(index);
                if (defaultFormalParameterContexts.size() > index + 1) {
                    text.append(this.visit(cTerminal));
                    text.append(" ");
                    final Dart2Parser.DefaultFormalParameterContext defaultFormalParameterContext = defaultFormalParameterContexts.get(index + 1);
                    text.append(this.visit(defaultFormalParameterContext));
                } else {
                    text.append(this.visit(cTerminal));
                }
            }
        }
        text.append(this.visit(cbTerminal));
        return text.toString();
    }

    @Override
    public String visitDefaultFormalParameter(final Dart2Parser.DefaultFormalParameterContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitDefaultFormalParameter` text: {}", context.getText());
        }
        final Dart2Parser.NormalFormalParameterContext normalFormalParameterContext = context.normalFormalParameter();
        final TerminalNode eqTerminal = context.EQ();
        final Dart2Parser.ExprContext exprContext = context.expr();
        if (eqTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDefaultFormalParameter -> eq");
        }
        if (exprContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDefaultFormalParameter -> expr");
        }
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(normalFormalParameterContext));
        return text.toString();
    }

    @Override
    public String visitNamedFormalParameters(final Dart2Parser.NamedFormalParametersContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitNamedFormalParameters` text: {}", context.getText());
        }
        final TerminalNode obcTerminal = context.OBC();
        final List<Dart2Parser.DefaultNamedParameterContext> defaultNamedParameterContexts = context.defaultNamedParameter();
        final List<TerminalNode> cTerminals = context.C();
        final TerminalNode cbcTerminal = context.CBC();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(obcTerminal));
        text.append("\n");
        this.currentIndentLevel++;
        text.append(this.indentUnit.repeat(this.currentIndentLevel));
        final Dart2Parser.DefaultNamedParameterContext firstDefaultNamedParameterContext = defaultNamedParameterContexts.get(0);
        text.append(this.visit(firstDefaultNamedParameterContext));
        if (defaultNamedParameterContexts.size() > 1) {
            for (int index = 0; index < cTerminals.size(); index++) {
                final TerminalNode cTerminal = cTerminals.get(index);
                if (defaultNamedParameterContexts.size() > index + 1) {
                    text.append(this.visit(cTerminal));
                    text.append("\n");
                    text.append(this.indentUnit.repeat(this.currentIndentLevel));
                    final Dart2Parser.DefaultNamedParameterContext defaultNamedParameterContext = defaultNamedParameterContexts.get(index + 1);
                    text.append(this.visit(defaultNamedParameterContext));
                } else {
                    text.append(this.visit(cTerminal));
                }
            }
        }
        text.append("\n");
        this.currentIndentLevel--;
        text.append(this.indentUnit.repeat(this.currentIndentLevel));
        text.append(this.visit(cbcTerminal));
        return text.toString();
    }

    @Override
    public String visitDefaultNamedParameter(final Dart2Parser.DefaultNamedParameterContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitDefaultNamedParameter` text: {}", context.getText());
        }
        final Dart2Parser.MetadataContext metadataContext = context.metadata();
        final TerminalNode requiredTerminal = context.REQUIRED_();
        final Dart2Parser.NormalFormalParameterNoMetadataContext normalFormalParameterNoMetadataContext = context.normalFormalParameterNoMetadata();
        // todo: use `eqTerminal` and `coTerminal` with tests.
        final TerminalNode eqTerminal = context.EQ();
        final TerminalNode coTerminal = context.CO();
        final Dart2Parser.ExprContext exprContext = context.expr();
        if (exprContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDefaultNamedParameter -> expr");
        }
        if (!metadataContext.getText().isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDefaultNamedParameter -> metadata");
        }
        final StringBuilder text = new StringBuilder();
        if (requiredTerminal != null) {
            text.append(this.visit(requiredTerminal));
            text.append(" ");
        }
        text.append(this.visit(normalFormalParameterNoMetadataContext));
        return text.toString();
    }

    @Override
    public String visitNormalFormalParameters(final Dart2Parser.NormalFormalParametersContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitNormalFormalParameters` text: {}", context.getText());
        }
        final List<Dart2Parser.NormalFormalParameterContext> normalFormalParameterContexts = context.normalFormalParameter();
        final StringBuilder text = new StringBuilder();
        final StringJoiner joiner = new StringJoiner(", ");
        for (final Dart2Parser.NormalFormalParameterContext normalFormalParameterContext : normalFormalParameterContexts) {
            final String normalFormalParameterText = this.visit(normalFormalParameterContext);
            joiner.add(normalFormalParameterText);
        }
        text.append(joiner);
        return text.toString();
    }

    @Override
    public String visitNormalFormalParameter(final Dart2Parser.NormalFormalParameterContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitNormalFormalParameter` text: {}", context.getText());
        }
        final Dart2Parser.MetadataContext metadataContext = context.metadata();
        final Dart2Parser.NormalFormalParameterNoMetadataContext normalFormalParameterNoMetadataContext = context.normalFormalParameterNoMetadata();
        if (!metadataContext.getText().isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNormalFormalParameter -> metadata");
        }
        final StringBuilder text = new StringBuilder();
        final String normalFormalParameterNoMetadataText = this.visit(normalFormalParameterNoMetadataContext);
        text.append(normalFormalParameterNoMetadataText);
        return text.toString();
    }

    @Override
    public String visitNormalFormalParameterNoMetadata(final Dart2Parser.NormalFormalParameterNoMetadataContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitNormalFormalParameterNoMetadata` text: {}", context.getText());
        }
        final Dart2Parser.FunctionFormalParameterContext functionFormalParameterContext = context.functionFormalParameter();
        final Dart2Parser.FieldFormalParameterContext fieldFormalParameterContext = context.fieldFormalParameter();
        final Dart2Parser.SimpleFormalParameterContext simpleFormalParameterContext = context.simpleFormalParameter();
        if (functionFormalParameterContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNormalFormalParameterNoMetadata -> functionFormalParameter");
        }
        final StringBuilder text = new StringBuilder();
        if (simpleFormalParameterContext != null) {
            final String simpleFormalParameterText = this.visit(simpleFormalParameterContext);
            text.append(simpleFormalParameterText);
        }
        if (fieldFormalParameterContext != null) {
            text.append(this.visit(fieldFormalParameterContext));
        }
        return text.toString();
    }

    @Override
    public String visitFieldFormalParameter(final Dart2Parser.FieldFormalParameterContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFieldFormalParameter` text: {}", context.getText());
        }
        final Dart2Parser.FinalConstVarOrTypeContext finalConstVarOrTypeContext = context.finalConstVarOrType();
        final TerminalNode thisTerminal = context.THIS_();
        final TerminalNode superTerminal = context.SUPER_();
        final TerminalNode dTerminal = context.D();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final Dart2Parser.FormalParameterPartContext formalParameterPartContext = context.formalParameterPart();
        // todo: use `quTerminal` with tests.
        final TerminalNode quTerminal = context.QU();
        final StringBuilder text = new StringBuilder();
        if (finalConstVarOrTypeContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFieldFormalParameter -> finalConstVarOrType");
        }
        if (thisTerminal != null) {
            text.append(this.visit(thisTerminal));
        } else if (superTerminal != null) {
            text.append(this.visit(superTerminal));
        }
        text.append(this.visit(dTerminal));
        text.append(this.visit(identifierContext));
        if (formalParameterPartContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFieldFormalParameter -> formalParameterPart");
        }
        return text.toString();
    }

    @Override
    public String visitSimpleFormalParameter(final Dart2Parser.SimpleFormalParameterContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitSimpleFormalParameter` text: {}", context.getText());
        }
        final Dart2Parser.DeclaredIdentifierContext declaredIdentifierContext = context.declaredIdentifier();
        final TerminalNode covariantTerminal = context.COVARIANT_();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        if (covariantTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitSimpleFormalParameter -> covariant");
        }
        final StringBuilder text = new StringBuilder();
        if (declaredIdentifierContext != null) {
            final String declaredIdentifierText = this.visit(declaredIdentifierContext);
            text.append(declaredIdentifierText);
        }
        if (identifierContext != null) {
            text.append(this.visit(identifierContext));
        }
        return text.toString();
    }

    @Override
    public String visitDeclaredIdentifier(Dart2Parser.DeclaredIdentifierContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitDeclaredIdentifier` text: {}", context.getText());
        }
        final TerminalNode covariantTerminal = context.COVARIANT_();
        final Dart2Parser.FinalConstVarOrTypeContext finalConstVarOrTypeContext = context.finalConstVarOrType();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        if (covariantTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaredIdentifier -> covariant");
        }
        final StringBuilder text = new StringBuilder();
        final String finalConstVarOrTypeText = this.visit(finalConstVarOrTypeContext);
        text.append(finalConstVarOrTypeText);
        text.append(" ");
        final String identifierText = this.visit(identifierContext);
        text.append(identifierText);
        return text.toString();
    }

    @Override
    public String visitFinalConstVarOrType(final Dart2Parser.FinalConstVarOrTypeContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFinalConstVarOrType` text: {}", context.getText());
        }
        final TerminalNode lateTerminal = context.LATE_();
        final TerminalNode finalTerminal = context.FINAL_();
        final Dart2Parser.TypeContext typeContext = context.type();
        final TerminalNode constTerminal = context.CONST_();
        final Dart2Parser.VarOrTypeContext varOrTypeContext = context.varOrType();
        if (lateTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFinalConstVarOrType -> late");
        }
        final StringBuilder text = new StringBuilder();
        if (finalTerminal != null) {
            text.append(this.visit(finalTerminal));
        }
        if (constTerminal != null) {
            text.append(this.visit(constTerminal));
        }
        if (typeContext != null) {
            final String typeText = this.visit(typeContext);
            text.append(" ");
            text.append(typeText);
        }
        if (varOrTypeContext != null) {
            text.append(this.visit(varOrTypeContext));
        }
        return text.toString();
    }

    @Override
    public String visitFunctionBody(final Dart2Parser.FunctionBodyContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFunctionBody` text: {}", context.getText());
        }
        final TerminalNode nativeTerminal = context.NATIVE_();
        final Dart2Parser.StringLiteralContext stringLiteralContext = context.stringLiteral();
        final TerminalNode scTerminal = context.SC();
        final TerminalNode asyncTerminal = context.ASYNC_();
        final TerminalNode egTerminal = context.EG();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode stTerminal = context.ST();
        final TerminalNode syncTerminal = context.SYNC_();
        final Dart2Parser.BlockContext blockContext = context.block();
        if (nativeTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionBody -> native");
        }
        if (stringLiteralContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionBody -> stringLiteral");
        }
        if (stTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionBody -> st");
        }
        final StringBuilder text = new StringBuilder();
        if (egTerminal != null) {
            if (asyncTerminal != null) {
                text.append(this.visit(asyncTerminal));
                text.append(" ");
            }
            text.append(this.visit(egTerminal));
            text.append(" ");
            text.append(this.visit(exprContext));
            text.append(this.visit(scTerminal));
        }
        if (blockContext != null) {
            if (asyncTerminal != null) {
                text.append(this.visit(asyncTerminal));
                text.append(" ");
            }
            if (syncTerminal != null) {
                text.append(this.visit(syncTerminal));
                text.append(" ");
            }
            final String blockText = this.visit(blockContext);
            text.append(blockText);
        }
        return text.toString();
    }

    @Override
    public String visitBlock(final Dart2Parser.BlockContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitBlock` text: {}", context.getText());
        }
        final TerminalNode obcTerminal = context.OBC();
        final Dart2Parser.StatementsContext statementsContext = context.statements();
        final TerminalNode cbcTerminal = context.CBC();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(obcTerminal));
        text.append("\n");
        this.currentIndentLevel++;
        text.append(this.indentUnit.repeat(this.currentIndentLevel));
        final String statementsText = this.visit(statementsContext);
        text.append(statementsText);
        text.append("\n");
        this.currentIndentLevel--;
        text.append(this.indentUnit.repeat(this.currentIndentLevel));
        text.append(this.visit(cbcTerminal));
        return text.toString();
    }

    @Override
    public String visitStatements(final Dart2Parser.StatementsContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitStatements` text: {}", context.getText());
        }
        final List<Dart2Parser.StatementContext> statementContexts = context.statement();
        final StringBuilder text = new StringBuilder();
        for (int index = 0; index < statementContexts.size(); index++) {
            final Dart2Parser.StatementContext statementContext = statementContexts.get(index);
            text.append(this.visit(statementContext));
            if (index < statementContexts.size() - 1) {
                text.append("\n");
                text.append(this.indentUnit.repeat(this.currentIndentLevel));
            }
        }
        return text.toString();
    }

    @Override
    public String visitStatement(final Dart2Parser.StatementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitStatement` text: {}", context.getText());
        }
        final List<Dart2Parser.LabelContext> labelContexts = context.label();
        final Dart2Parser.NonLabelledStatementContext nonLabelledStatementContext = context.nonLabelledStatement();
        if (!labelContexts.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitStatement -> label");
        }
        final StringBuilder text = new StringBuilder();
        final String nonLabelledStatementText = this.visit(nonLabelledStatementContext);
        text.append(nonLabelledStatementText);
        return text.toString();
    }

    @Override
    public String visitNonLabelledStatement(final Dart2Parser.NonLabelledStatementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitNonLabelledStatement` text: {}", context.getText());
        }
        final Dart2Parser.BlockContext blockContext = context.block();
        final Dart2Parser.LocalVariableDeclarationContext localVariableDeclarationContext = context.localVariableDeclaration();
        final Dart2Parser.ForStatementContext forStatementContext = context.forStatement();
        final Dart2Parser.WhileStatementContext whileStatementContext = context.whileStatement();
        final Dart2Parser.DoStatementContext doStatementContext = context.doStatement();
        final Dart2Parser.SwitchStatementContext switchStatementContext = context.switchStatement();
        final Dart2Parser.IfStatementContext ifStatementContext = context.ifStatement();
        final Dart2Parser.RethrowStatementContext rethrowStatementContext = context.rethrowStatement();
        final Dart2Parser.TryStatementContext tryStatementContext = context.tryStatement();
        final Dart2Parser.BreakStatementContext breakStatementContext = context.breakStatement();
        final Dart2Parser.ContinueStatementContext continueStatementContext = context.continueStatement();
        final Dart2Parser.ReturnStatementContext returnStatementContext = context.returnStatement();
        final Dart2Parser.YieldStatementContext yieldStatementContext = context.yieldStatement();
        final Dart2Parser.YieldEachStatementContext yieldEachStatementContext = context.yieldEachStatement();
        final Dart2Parser.ExpressionStatementContext expressionStatementContext = context.expressionStatement();
        final Dart2Parser.AssertStatementContext assertStatementContext = context.assertStatement();
        final Dart2Parser.LocalFunctionDeclarationContext localFunctionDeclarationContext = context.localFunctionDeclaration();
        if (doStatementContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNonLabelledStatement -> doStatement");
        }
        if (rethrowStatementContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNonLabelledStatement -> rethrowStatement");
        }
        if (breakStatementContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNonLabelledStatement -> breakStatement");
        }
        if (continueStatementContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNonLabelledStatement -> continueStatement");
        }
        if (yieldStatementContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNonLabelledStatement -> yieldStatement");
        }
        if (yieldEachStatementContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNonLabelledStatement -> yieldEachStatement");
        }
        if (assertStatementContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNonLabelledStatement -> assertStatement");
        }
        if (localFunctionDeclarationContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNonLabelledStatement -> localFunctionDeclaration");
        }
        final StringBuilder text = new StringBuilder();
        if (blockContext != null) {
            text.append(this.visit(blockContext));
        } else if (returnStatementContext != null) {
            text.append(this.visit(returnStatementContext));
        } else if (forStatementContext != null) {
            text.append(this.visit(forStatementContext));
        } else if (expressionStatementContext != null) {
            text.append(this.visit(expressionStatementContext));
        } else if (localVariableDeclarationContext != null) {
            text.append(this.visit(localVariableDeclarationContext));
        } else if (ifStatementContext != null) {
            text.append(this.visit(ifStatementContext));
        } else if (tryStatementContext != null) {
            text.append(this.visit(tryStatementContext));
        } else if (whileStatementContext != null) {
            text.append(this.visit(whileStatementContext));
        } else if (switchStatementContext != null) {
            text.append(this.visit(switchStatementContext));
        }
        return text.toString();
    }

    @Override
    public String visitTryStatement(final Dart2Parser.TryStatementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTryStatement` text: {}", context.getText());
        }
        final TerminalNode tryTerminal = context.TRY_();
        final Dart2Parser.BlockContext blockContext = context.block();
        final List<Dart2Parser.OnPartContext> onPartContexts = context.onPart();
        final Dart2Parser.FinallyPartContext finallyPartContext = context.finallyPart();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(tryTerminal));
        text.append(" ");
        text.append(this.visit(blockContext));
        for (final Dart2Parser.OnPartContext onPartContext : onPartContexts) {
            text.append(" ");
            text.append(this.visit(onPartContext));
        }
        if (finallyPartContext != null) {
            text.append(" ");
            text.append(this.visit(finallyPartContext));
        }
        return text.toString();
    }

    @Override
    public String visitFinallyPart(final Dart2Parser.FinallyPartContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFinallyPart` text: {}", context.getText());
        }
        final TerminalNode finallyTerminal = context.FINALLY_();
        final Dart2Parser.BlockContext blockContext = context.block();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(finallyTerminal));
        text.append(" ");
        text.append(this.visit(blockContext));
        return text.toString();
    }

    @Override
    public String visitOnPart(final Dart2Parser.OnPartContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitOnPart` text: {}", context.getText());
        }
        final Dart2Parser.CatchPartContext catchPartContext = context.catchPart();
        final Dart2Parser.BlockContext blockContext = context.block();
        final TerminalNode onTerminal = context.ON_();
        final Dart2Parser.TypeNotVoidContext typeNotVoidContext = context.typeNotVoid();
        final StringBuilder text = new StringBuilder();
        if (onTerminal == null) {
            // catchPart block
            text.append(this.visit(catchPartContext));
            text.append(" ");
            text.append(this.visit(blockContext));
        } else {
            // ON_ typeNotVoid catchPart? block
            text.append(this.visit(onTerminal));
            text.append(" ");
            text.append(this.visit(typeNotVoidContext));
            if (catchPartContext != null) {
                text.append(" ");
                text.append(this.visit(catchPartContext));
            }
            text.append(" ");
            text.append(this.visit(blockContext));
        }
        return text.toString();
    }

    @Override
    public String visitCatchPart(final Dart2Parser.CatchPartContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitCatchPart` text: {}", context.getText());
        }
        final TerminalNode catchTerminal = context.CATCH_();
        final TerminalNode opTerminal = context.OP();
        final List<Dart2Parser.IdentifierContext> identifierContexts = context.identifier();
        final TerminalNode cTerminal = context.C();
        final TerminalNode cpTerminal = context.CP();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(catchTerminal));
        text.append(" ");
        text.append(this.visit(opTerminal));
        final Dart2Parser.IdentifierContext firstIdentifierContext = identifierContexts.get(0);
        text.append(this.visit(firstIdentifierContext));
        if (cTerminal != null) {
            text.append(this.visit(cTerminal));
            text.append(" ");
            final Dart2Parser.IdentifierContext secondIdentifierContext = identifierContexts.get(1);
            text.append(this.visit(secondIdentifierContext));
        }
        text.append(this.visit(cpTerminal));
        return text.toString();
    }

    @Override
    public String visitSwitchStatement(final Dart2Parser.SwitchStatementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitSwitchStatement` text: {}", context.getText());
        }
        final TerminalNode switchTerminal = context.SWITCH_();
        final TerminalNode opTerminal = context.OP();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode cpTerminal = context.CP();
        final TerminalNode obcTerminal = context.OBC();
        final List<Dart2Parser.SwitchCaseContext> switchCaseContexts = context.switchCase();
        final Dart2Parser.DefaultCaseContext defaultCaseContext = context.defaultCase();
        final TerminalNode cbcTerminal = context.CBC();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(switchTerminal));
        text.append(" ");
        text.append(this.visit(opTerminal));
        text.append(this.visit(exprContext));
        text.append(this.visit(cpTerminal));
        text.append(" ");
        text.append(this.visit(obcTerminal));
        this.currentIndentLevel++;
        for (final Dart2Parser.SwitchCaseContext switchCaseContext : switchCaseContexts) {
            text.append("\n");
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            text.append(this.visit(switchCaseContext));
        }
        if (defaultCaseContext != null) {
            text.append("\n");
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            text.append(this.visit(defaultCaseContext));
        }
        text.append("\n");
        this.currentIndentLevel--;
        text.append(this.indentUnit.repeat(this.currentIndentLevel));
        text.append(this.visit(cbcTerminal));
        return text.toString();
    }

    @Override
    public String visitSwitchCase(final Dart2Parser.SwitchCaseContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitSwitchCase` text: {}", context.getText());
        }
        final List<Dart2Parser.LabelContext> labelContexts = context.label();
        final TerminalNode caseTerminal = context.CASE_();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode coTerminal = context.CO();
        final Dart2Parser.StatementsContext statementsContext = context.statements();
        if (!labelContexts.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitSwitchCase -> label");
        }
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(caseTerminal));
        text.append(" ");
        text.append(this.visit(exprContext));
        text.append(this.visit(coTerminal));
        this.currentIndentLevel++;
        final int visitStatementCountBefore = this.ruleVisitCounts.getOrDefault(Dart2Parser.StatementContext.class.getSimpleName(), 0);
        final String statementsText = this.visit(statementsContext);
        final int visitStatementCountAfter = this.ruleVisitCounts.getOrDefault(Dart2Parser.StatementContext.class.getSimpleName(), 0);
        if (visitStatementCountBefore < visitStatementCountAfter) {
            text.append("\n");
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            text.append(statementsText);
        }
        this.currentIndentLevel--;
        return text.toString();
    }

    @Override
    public String visitDefaultCase(final Dart2Parser.DefaultCaseContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitDefaultCase` text: {}", context.getText());
        }
        final List<Dart2Parser.LabelContext> labelContexts = context.label();
        final TerminalNode defaultTerminal = context.DEFAULT_();
        final TerminalNode coTerminal = context.CO();
        final Dart2Parser.StatementsContext statementsContext = context.statements();
        if (!labelContexts.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDefaultCase -> label");
        }
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(defaultTerminal));
        text.append(this.visit(coTerminal));
        text.append("\n");
        this.currentIndentLevel++;
        text.append(this.indentUnit.repeat(this.currentIndentLevel));
        text.append(this.visit(statementsContext));
        this.currentIndentLevel--;
        return text.toString();
    }

    @Override
    public String visitWhileStatement(final Dart2Parser.WhileStatementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitWhileStatement` text: {}", context.getText());
        }
        final TerminalNode whileTerminal = context.WHILE_();
        final TerminalNode opTerminal = context.OP();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode cpTerminal = context.CP();
        final Dart2Parser.StatementContext statementContext = context.statement();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(whileTerminal));
        text.append(" ");
        text.append(this.visit(opTerminal));
        final String exprText = this.visit(exprContext);
        text.append(exprText);
        text.append(this.visit(cpTerminal));
        text.append(" ");
        final String statementText = this.visit(statementContext);
        text.append(statementText);
        return text.toString();
    }

    @Override
    public String visitExpressionStatement(final Dart2Parser.ExpressionStatementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitExpressionStatement` text: {}", context.getText());
        }
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode scTerminal = context.SC();
        final StringBuilder text = new StringBuilder();
        if (exprContext != null) {
            final String exprText = this.visit(exprContext);
            text.append(exprText);
        }
        text.append(this.visit(scTerminal));
        return text.toString();
    }

    @Override
    public String visitIfStatement(final Dart2Parser.IfStatementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitIfStatement` text: {}", context.getText());
        }
        final TerminalNode ifTerminal = context.IF_();
        final TerminalNode opTerminal = context.OP();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode cpTerminal = context.CP();
        final List<Dart2Parser.StatementContext> statementContexts = context.statement();
        final TerminalNode elseTerminal = context.ELSE_();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(ifTerminal));
        text.append(" ");
        text.append(this.visit(opTerminal));
        final String exprText = this.visit(exprContext);
        text.append(exprText);
        text.append(this.visit(cpTerminal));
        text.append(" ");
        final Dart2Parser.StatementContext firstStatementContext = statementContexts.get(0);
        final String firstStatementText = this.visit(firstStatementContext);
        text.append(firstStatementText);
        if (elseTerminal != null) {
            text.append(" ");
            text.append(this.visit(elseTerminal));
            text.append(" ");
            final Dart2Parser.StatementContext secondStatementContext = statementContexts.get(1);
            final String secondStatementText = this.visit(secondStatementContext);
            text.append(secondStatementText);
        }
        return text.toString();
    }

    @Override
    public String visitForStatement(final Dart2Parser.ForStatementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitForStatement` text: {}", context.getText());
        }
        final TerminalNode awaitTerminal = context.AWAIT_();
        final TerminalNode forTerminal = context.FOR_();
        final TerminalNode opTerminal = context.OP();
        final Dart2Parser.ForLoopPartsContext forLoopPartsContext = context.forLoopParts();
        final TerminalNode cpTerminal = context.CP();
        final Dart2Parser.StatementContext statementContext = context.statement();
        if (awaitTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitForStatement -> await");
        }
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(forTerminal));
        text.append(" ");
        text.append(this.visit(opTerminal));
        final String forLoopPartsText = this.visit(forLoopPartsContext);
        text.append(forLoopPartsText);
        text.append(this.visit(cpTerminal));
        text.append(" ");
        final String statementText = this.visit(statementContext);
        text.append(statementText);
        return text.toString();
    }

    @Override
    public String visitForLoopParts(final Dart2Parser.ForLoopPartsContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitForLoopParts` text: {}", context.getText());
        }
        final Dart2Parser.ForInitializerStatementContext forInitializerStatementContext = context.forInitializerStatement();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode scTerminal = context.SC();
        final Dart2Parser.ExpressionListContext expressionListContext = context.expressionList();
        final Dart2Parser.MetadataContext metadataContext = context.metadata();
        final Dart2Parser.DeclaredIdentifierContext declaredIdentifierContext = context.declaredIdentifier();
        final TerminalNode inTerminal = context.IN_();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        if (identifierContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitForLoopParts -> identifier");
        }
        final StringBuilder text = new StringBuilder();
        if (forInitializerStatementContext != null) {
            final String forInitializerStatementText = this.visit(forInitializerStatementContext);
            text.append(forInitializerStatementText);
            text.append(" ");
            if (exprContext != null) {
                final String exprText = this.visit(exprContext);
                text.append(exprText);
            }
            text.append(this.visit(scTerminal));
            if (expressionListContext != null) {
                text.append(" ");
                final String expressionListText = this.visit(expressionListContext);
                text.append(expressionListText);
            }
        }
        if (metadataContext != null) {
            if (!metadataContext.getText().isEmpty()) {
                throw new UnsupportedOperationException("The following parsing path is not supported yet: visitForLoopParts -> metadata");
            }
            text.append(this.visit(declaredIdentifierContext));
            text.append(" ");
            text.append(this.visit(inTerminal));
            text.append(" ");
            text.append(this.visit(exprContext));
        }
        return text.toString();
    }

    @Override
    public String visitExpressionList(final Dart2Parser.ExpressionListContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitExpressionList` text: {}", context.getText());
        }
        final List<Dart2Parser.ExprContext> exprContexts = context.expr();
        final List<TerminalNode> cTerminals = context.C();
        final StringBuilder text = new StringBuilder();
        final Dart2Parser.ExprContext firstExprContext = exprContexts.get(0);
        final String firstExprText = this.visit(firstExprContext);
        text.append(firstExprText);
        for (int index = 0; index < cTerminals.size(); index++) {
            final TerminalNode cTerminal = cTerminals.get(index);
            final Dart2Parser.ExprContext exprContext = exprContexts.get(index + 1);
            final int visitArgumentsCountBefore = this.ruleVisitCounts.getOrDefault(Dart2Parser.ArgumentsContext.class.getSimpleName(), 0);
            final String exprText = this.visit(exprContext);
            final int visitArgumentsCountAfter = this.ruleVisitCounts.getOrDefault(Dart2Parser.ArgumentsContext.class.getSimpleName(), 0);
            // We assume the following condition means nested object initialization. Ex: User('Rin', User('Ian'));
            if (visitArgumentsCountBefore < visitArgumentsCountAfter) {
                text.append(this.visit(cTerminal));
                text.append("\n");
                text.append(this.indentUnit.repeat(this.currentIndentLevel));
                text.append(exprText);
            } else {
                text.append(this.visit(cTerminal));
                text.append(" ");
                text.append(exprText);
            }
        }
        return text.toString();
    }

    @Override
    public String visitForInitializerStatement(final Dart2Parser.ForInitializerStatementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitForInitializerStatement` text: {}", context.getText());
        }
        final Dart2Parser.LocalVariableDeclarationContext localVariableDeclarationContext = context.localVariableDeclaration();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode scTerminal = context.SC();
        if (exprContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitForInitializerStatement -> expr");
        }
        if (scTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitForInitializerStatement -> sc");
        }
        final StringBuilder text = new StringBuilder();
        if (localVariableDeclarationContext != null) {
            final String localVariableDeclarationText = this.visit(localVariableDeclarationContext);
            text.append(localVariableDeclarationText);
        }
        return text.toString();
    }

    @Override
    public String visitLocalVariableDeclaration(final Dart2Parser.LocalVariableDeclarationContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitLocalVariableDeclaration` text: {}", context.getText());
        }
        final Dart2Parser.MetadataContext metadataContext = context.metadata();
        final Dart2Parser.InitializedVariableDeclarationContext initializedVariableDeclarationContext = context.initializedVariableDeclaration();
        final TerminalNode scTerminal = context.SC();
        if (!metadataContext.getText().isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLocalVariableDeclaration -> metadata");
        }
        final StringBuilder text = new StringBuilder();
        final String initializedVariableDeclarationText = this.visit(initializedVariableDeclarationContext);
        text.append(initializedVariableDeclarationText);
        text.append(this.visit(scTerminal));
        return text.toString();
    }

    @Override
    public String visitInitializedVariableDeclaration(final Dart2Parser.InitializedVariableDeclarationContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitInitializedVariableDeclaration` text: {}", context.getText());
        }
        final Dart2Parser.DeclaredIdentifierContext declaredIdentifierContext = context.declaredIdentifier();
        final TerminalNode eqTerminal = context.EQ();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final List<TerminalNode> cTerminals = context.C();
        // todo: use `initializedIdentifierContexts` with tests.
        final List<Dart2Parser.InitializedIdentifierContext> initializedIdentifierContexts = context.initializedIdentifier();
        if (!cTerminals.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitInitializedVariableDeclaration -> c");
        }
        final StringBuilder text = new StringBuilder();
        final String declaredIdentifierText = this.visit(declaredIdentifierContext);
        text.append(declaredIdentifierText);
        if (eqTerminal != null) {
            text.append(" ");
            text.append(this.visit(eqTerminal));
            text.append(" ");
            final String exprText = this.visit(exprContext);
            text.append(exprText);
        }
        return text.toString();
    }

    @Override
    public String visitReturnStatement(final Dart2Parser.ReturnStatementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitReturnStatement` text: {}", context.getText());
        }
        final TerminalNode returnTerminal = context.RETURN_();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode scTerminal = context.SC();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(returnTerminal));
        text.append(" ");
        if (exprContext != null) {
            final String exprText = this.visit(exprContext);
            text.append(exprText);
        }
        text.append(this.visit(scTerminal));
        return text.toString();
    }

    @Override
    public String visitDeclaration(final Dart2Parser.DeclarationContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitDeclaration` text: {}", context.getText());
        }
        final TerminalNode abstractTerminal = context.ABSTRACT_();
        final TerminalNode externalTerminal = context.EXTERNAL_();
        final Dart2Parser.FactoryConstructorSignatureContext factoryConstructorSignatureContext = context.factoryConstructorSignature();
        final Dart2Parser.ConstantConstructorSignatureContext constantConstructorSignatureContext = context.constantConstructorSignature();
        final Dart2Parser.ConstructorSignatureContext constructorSignatureContext = context.constructorSignature();
        final TerminalNode staticTerminal = context.STATIC_();
        final Dart2Parser.GetterSignatureContext getterSignatureContext = context.getterSignature();
        final Dart2Parser.SetterSignatureContext setterSignatureContext = context.setterSignature();
        final Dart2Parser.FunctionSignatureContext functionSignatureContext = context.functionSignature();
        final Dart2Parser.OperatorSignatureContext operatorSignatureContext = context.operatorSignature();
        final TerminalNode constTerminal = context.CONST_();
        final Dart2Parser.TypeContext typeContext = context.type();
        final Dart2Parser.StaticFinalDeclarationListContext staticFinalDeclarationListContext = context.staticFinalDeclarationList();
        final TerminalNode finalTerminal = context.FINAL_();
        final TerminalNode lateTerminal = context.LATE_();
        final Dart2Parser.InitializedIdentifierListContext initializedIdentifierListContext = context.initializedIdentifierList();
        final Dart2Parser.VarOrTypeContext varOrTypeContext = context.varOrType();
        final TerminalNode covariantTerminal = context.COVARIANT_();
        final Dart2Parser.IdentifierListContext identifierListContext = context.identifierList();
        final Dart2Parser.RedirectingFactoryConstructorSignatureContext redirectingFactoryConstructorSignatureContext = context.redirectingFactoryConstructorSignature();
        final Dart2Parser.RedirectionContext redirectionContext = context.redirection();
        final Dart2Parser.InitializersContext initializersContext = context.initializers();
        if (factoryConstructorSignatureContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> factoryConstructorSignature");
        }
        if (constructorSignatureContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> constructorSignature");
        }
        if (getterSignatureContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> getterSignature");
        }
        if (setterSignatureContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> setterSignature");
        }
        if (operatorSignatureContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> operatorSignature");
        }
        if (constTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> const");
        }
        if (covariantTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> covariant");
        }
        if (identifierListContext != null) {
           throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> identifierList");
        }
        if (redirectingFactoryConstructorSignatureContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> redirectingFactoryConstructorSignature");
        }
        if (redirectionContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> redirection");
        }
        if (initializersContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> initializers");
        }
        final StringBuilder text = new StringBuilder();
        if (abstractTerminal != null) {
            text.append(this.visit(abstractTerminal));
            text.append(" ");
        }
        if (externalTerminal != null && constantConstructorSignatureContext != null) {
            // EXTERNAL_ constantConstructorSignature
            text.append(this.visit(externalTerminal));
            text.append(" ");
            text.append(this.visit(constantConstructorSignatureContext));
        } else if (functionSignatureContext != null) {
            // ( EXTERNAL_ STATIC_?)? functionSignature
            if (externalTerminal != null) {
                text.append(this.visit(externalTerminal));
                text.append(" ");
            }
            if (staticTerminal != null) {
                text.append(this.visit(staticTerminal));
                text.append(" ");
            }
            text.append(this.visit(functionSignatureContext));
        } else if (staticTerminal != null && finalTerminal != null && staticFinalDeclarationListContext != null) {
            // STATIC_ FINAL_ type? staticFinalDeclarationList
            text.append(this.visit(staticTerminal));
            text.append(" ");
            text.append(this.visit(finalTerminal));
            text.append(" ");
            if (typeContext != null) {
                text.append(this.visit(typeContext));
                text.append(" ");
            }
            text.append(this.visit(staticFinalDeclarationListContext));
        } else if (staticTerminal != null && lateTerminal != null && finalTerminal != null && initializedIdentifierListContext != null) {
            // STATIC_ LATE_ FINAL_ type? initializedIdentifierList
            text.append(this.visit(staticTerminal));
            text.append(" ");
            text.append(this.visit(lateTerminal));
            text.append(" ");
            text.append(this.visit(finalTerminal));
            text.append(" ");
            if (typeContext != null) {
                text.append(this.visit(typeContext));
                text.append(" ");
            }
            text.append(this.visit(initializedIdentifierListContext));
        } else if (staticTerminal != null && varOrTypeContext != null && initializedIdentifierListContext != null) {
            // STATIC_ LATE_? varOrType initializedIdentifierList
            text.append(this.visit(staticTerminal));
            text.append(" ");
            if (lateTerminal != null) {
                text.append(this.visit(lateTerminal));
                text.append(" ");
            }
            text.append(this.visit(varOrTypeContext));
            text.append(" ");
            text.append(this.visit(initializedIdentifierListContext));
        } else if (finalTerminal != null && initializedIdentifierListContext != null) {
            // LATE_? FINAL_ type? initializedIdentifierList
            if (lateTerminal != null) {
                text.append(this.visit(lateTerminal));
                text.append(" ");
            }
            text.append(this.visit(finalTerminal));
            text.append(" ");
            if (typeContext != null) {
                text.append(this.visit(typeContext));
                text.append(" ");
            }
            text.append(this.visit(initializedIdentifierListContext));
        } else if (varOrTypeContext != null && initializedIdentifierListContext != null) {
            // LATE_? varOrType initializedIdentifierList
            if (lateTerminal != null) {
                text.append(this.visit(lateTerminal));
                text.append(" ");
            }
            text.append(this.visit(varOrTypeContext));
            text.append(" ");
            text.append(this.visit(initializedIdentifierListContext));
        } else if (constantConstructorSignatureContext != null) {
            // constantConstructorSignature ( redirection | initializers)?
            text.append(this.visit(constantConstructorSignatureContext));
        }
        return text.toString();
    }

    @Override
    public String visitVarOrType(final Dart2Parser.VarOrTypeContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitVarOrType` text: {}", context.getText());
        }
        final TerminalNode varTerminal = context.VAR_();
        final Dart2Parser.TypeContext typeContext = context.type();
        final StringBuilder text = new StringBuilder();
        if (varTerminal != null) {
            text.append(this.visit(varTerminal));
        } else if (typeContext != null) {
            text.append(this.visit(typeContext));
        }
        return text.toString();
    }

    @Override
    public String visitStaticFinalDeclarationList(final Dart2Parser.StaticFinalDeclarationListContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitStaticFinalDeclarationList` text: {}", context.getText());
        }
        final List<Dart2Parser.StaticFinalDeclarationContext> staticFinalDeclarationContexts = context.staticFinalDeclaration();
        final List<TerminalNode> cTerminals = context.C();
        final StringBuilder text = new StringBuilder();
        final Dart2Parser.StaticFinalDeclarationContext firstStaticFinalDeclarationContext = staticFinalDeclarationContexts.get(0);
        text.append(this.visit(firstStaticFinalDeclarationContext));
        for (int index = 0; index < cTerminals.size(); index++) {
            final TerminalNode cTerminal = cTerminals.get(index);
            final Dart2Parser.StaticFinalDeclarationContext staticFinalDeclarationContext = staticFinalDeclarationContexts.get(index + 1);
            text.append(this.visit(cTerminal));
            text.append(" ");
            text.append(this.visit(staticFinalDeclarationContext));
        }
        return text.toString();
    }

    @Override
    public String visitStaticFinalDeclaration(final Dart2Parser.StaticFinalDeclarationContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitStaticFinalDeclaration` text: {}", context.getText());
        }
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final TerminalNode eqTerminal = context.EQ();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(identifierContext));
        text.append(" ");
        text.append(this.visit(eqTerminal));
        text.append(" ");
        text.append(this.visit(exprContext));
        return text.toString();
    }

    @Override
    public String visitConstantConstructorSignature(final Dart2Parser.ConstantConstructorSignatureContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitConstantConstructorSignature` text: {}", context.getText());
        }
        final TerminalNode constTerminal = context.CONST_();
        final Dart2Parser.ConstructorNameContext constructorNameContext = context.constructorName();
        final Dart2Parser.FormalParameterListContext formalParameterListContext = context.formalParameterList();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(constTerminal));
        text.append(" ");
        text.append(this.visit(constructorNameContext));
        final String formalParameterListText = this.visit(formalParameterListContext);
        text.append(formalParameterListText);
        return text.toString();
    }

    @Override
    public String visitType(final Dart2Parser.TypeContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitType` text: {}", context.getText());
        }
        final Dart2Parser.FunctionTypeContext functionTypeContext = context.functionType();
        final TerminalNode quTerminal =  context.QU();
        final Dart2Parser.TypeNotFunctionContext typeNotFunctionContext = context.typeNotFunction();
        final StringBuilder text = new StringBuilder();
        if (functionTypeContext != null) {
            final String functionTypeText = this.visit(functionTypeContext);
            text.append(functionTypeText);
            if (quTerminal != null) {
                text.append(this.visit(quTerminal));
            }
        } else if (typeNotFunctionContext != null) {
            final String typeNotFunctionText = this.visit(typeNotFunctionContext);
            text.append(typeNotFunctionText);
        }
        return text.toString();
    }

    @Override
    public String visitFunctionType(final Dart2Parser.FunctionTypeContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFunctionType` text: {}", context.getText());
        }
        final Dart2Parser.FunctionTypeTailsContext functionTypeTailsContext = context.functionTypeTails();
        final Dart2Parser.TypeNotFunctionContext typeNotFunctionContext = context.typeNotFunction();
        final StringBuilder text = new StringBuilder();
        if (typeNotFunctionContext != null) {
            final String typeNotFunctionText = this.visit(typeNotFunctionContext);
            text.append(typeNotFunctionText);
            text.append(" ");
        }
        final String functionTypeText = this.visit(functionTypeTailsContext);
        text.append(functionTypeText);
        return text.toString();
    }

    @Override
    public String visitFunctionTypeTails(final Dart2Parser.FunctionTypeTailsContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFunctionTypeTails` text: {}", context.getText());
        }
        final Dart2Parser.FunctionTypeTailContext functionTypeTailContext = context.functionTypeTail();
        final TerminalNode quTerminal =  context.QU();
        final Dart2Parser.FunctionTypeTailsContext functionTypeTailsContext = context.functionTypeTails();
        if (quTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionTypeTails -> qu");
        }
        if (functionTypeTailsContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionTypeTails -> functionTypeTails");
        }
        final StringBuilder text = new StringBuilder();
        final String functionTypeTailText = this.visit(functionTypeTailContext);
        text.append(functionTypeTailText);
        return text.toString();
    }

    @Override
    public String visitFunctionTypeTail(final Dart2Parser.FunctionTypeTailContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFunctionTypeTail` text: {}", context.getText());
        }
        final TerminalNode functionTerminal = context.FUNCTION_();
        final Dart2Parser.TypeParametersContext typeParametersContext = context.typeParameters();
        final Dart2Parser.ParameterTypeListContext parameterTypeListContext = context.parameterTypeList();
        if (typeParametersContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionTypeTail -> typeParameters");
        }
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(functionTerminal));
        text.append(this.visit(parameterTypeListContext));
        return text.toString();
    }

    @Override
    public String visitParameterTypeList(final Dart2Parser.ParameterTypeListContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitParameterTypeList` text: {}", context.getText());
        }
        final TerminalNode opTerminal = context.OP();
        final TerminalNode cpTerminal = context.CP();
        final Dart2Parser.NormalParameterTypesContext normalParameterTypesContext = context.normalParameterTypes();
        final TerminalNode cTerminal = context.C();
        final Dart2Parser.OptionalParameterTypesContext optionalParameterTypesContext = context.optionalParameterTypes();
        if (cTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitParameterTypeList -> c");
        }
        if (optionalParameterTypesContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitParameterTypeList -> optionalParameterTypes");
        }
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(opTerminal));
        if (normalParameterTypesContext != null) {
            text.append(this.visit(normalParameterTypesContext));
        }
        text.append(this.visit(cpTerminal));
        return text.toString();
    }

    @Override
    public String visitNormalParameterTypes(final Dart2Parser.NormalParameterTypesContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitNormalParameterTypes` text: {}", context.getText());
        }
        final List<Dart2Parser.NormalParameterTypeContext> normalParameterTypeContexts = context.normalParameterType();
        final List<TerminalNode> cTerminals = context.C();
        final StringBuilder text = new StringBuilder();
        final Dart2Parser.NormalParameterTypeContext firstNormalParameterTypeContext = normalParameterTypeContexts.get(0);
        text.append(this.visit(firstNormalParameterTypeContext));
        for (int index = 0; index < cTerminals.size(); index++) {
            text.append(this.visit(cTerminals.get(index)));
            text.append(" ");
            text.append(this.visit(normalParameterTypeContexts.get(index)));
        }
        return text.toString();
    }

    @Override
    public String visitNormalParameterType(final Dart2Parser.NormalParameterTypeContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitNormalParameterType` text: {}", context.getText());
        }
        final Dart2Parser.MetadataContext metadataContext = context.metadata();
        final Dart2Parser.TypedIdentifierContext typedIdentifierContext = context.typedIdentifier();
        final Dart2Parser.TypeContext typeContext = context.type();
        if (!metadataContext.getText().isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNormalParameterType -> metadata");
        }
        if (typeContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNormalParameterType -> type");
        }
        final StringBuilder text = new StringBuilder();
        if (typedIdentifierContext != null) {
            text.append(this.visit(typedIdentifierContext));
        }
        return text.toString();
    }

    @Override
    public String visitTypedIdentifier(final Dart2Parser.TypedIdentifierContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTypedIdentifier` text: {}", context.getText());
        }
        final Dart2Parser.TypeContext typeContext = context.type();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(typeContext));
        text.append(" ");
        text.append(this.visit(identifierContext));
        return text.toString();
    }

    @Override
    public String visitTypeNotFunction(final Dart2Parser.TypeNotFunctionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTypeNotFunction` text: {}", context.getText());
        }
        final TerminalNode voidTerminal = context.VOID_();
        final Dart2Parser.TypeNotVoidNotFunctionContext typeNotVoidNotFunctionContext = context.typeNotVoidNotFunction();
        final StringBuilder text = new StringBuilder();
        if (voidTerminal != null) {
            text.append(this.visit(voidTerminal));
        }
        if (typeNotVoidNotFunctionContext != null) {
            final String typeNotVoidNotFunctionText = this.visit(typeNotVoidNotFunctionContext);
            text.append(typeNotVoidNotFunctionText);
        }
        return text.toString();
    }

    @Override
    public String visitTypeNotVoidNotFunction(final Dart2Parser.TypeNotVoidNotFunctionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTypeNotVoidNotFunction` text: {}", context.getText());
        }
        final Dart2Parser.TypeNameContext typeNameContext = context.typeName();
        final Dart2Parser.TypeArgumentsContext typeArgumentsContext = context.typeArguments();
        final TerminalNode quTerminal = context.QU();
        final TerminalNode functionTerminal = context.FUNCTION_();
        if (functionTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTypeNotVoidNotFunction -> function");
        }
        final StringBuilder text = new StringBuilder();
        if (typeNameContext != null) {
            text.append(this.visit(typeNameContext));
        }
        if (typeArgumentsContext != null) {
            final String typeArgumentsText = this.visit(typeArgumentsContext);
            text.append(typeArgumentsText);
        }
        if (quTerminal != null) {
            text.append(this.visit(quTerminal));
        }
        return text.toString();
    }

    @Override
    public String visitTypeName(final Dart2Parser.TypeNameContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTypeName` text: {}", context.getText());
        }
        final List<Dart2Parser.TypeIdentifierContext> typeIdentifierContexts = context.typeIdentifier();
        final TerminalNode dTerminal = context.D();
        final StringBuilder text = new StringBuilder();
        final Dart2Parser.TypeIdentifierContext firstTypeIdentifierContext = typeIdentifierContexts.get(0);
        text.append(this.visit(firstTypeIdentifierContext));
        if (dTerminal != null) {
            text.append(this.visit(dTerminal));
            final Dart2Parser.TypeIdentifierContext secondTypeIdentifierContext = typeIdentifierContexts.get(1);
            text.append(this.visit(secondTypeIdentifierContext));
        }
        return text.toString();
    }

    @Override
    public String visitTypeArguments(final Dart2Parser.TypeArgumentsContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTypeArguments` text: {}", context.getText());
        }
        final TerminalNode ltTerminal = context.LT();
        final Dart2Parser.TypeListContext typeListContext = context.typeList();
        final TerminalNode gtTerminal = context.GT();
        final StringBuilder text = new StringBuilder();
        final String ltTerminalText = this.visit(ltTerminal);
        text.append(ltTerminalText);
        final String typeListText = this.visit(typeListContext);
        text.append(typeListText);
        final String gtTerminalText = this.visit(gtTerminal);
        text.append(gtTerminalText);
        return text.toString();
    }

    @Override
    public String visitTypeList(final Dart2Parser.TypeListContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTypeList` text: {}", context.getText());
        }
        final List<Dart2Parser.TypeContext> typeContexts = context.type();
        final StringBuilder text = new StringBuilder();
        final String firstTypeText = this.visit(typeContexts.get(0));
        text.append(firstTypeText);
        for (int index = 1; index < typeContexts.size(); index++) {
            text.append(", ");
            final String typeText = this.visit(typeContexts.get(index));
            text.append(typeText);
        }
        return text.toString();
    }

    @Override
    public String visitInitializedIdentifierList(final Dart2Parser.InitializedIdentifierListContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitInitializedIdentifierList` text: {}", context.getText());
        }
        final List<Dart2Parser.InitializedIdentifierContext> initializedIdentifierContexts = context.initializedIdentifier();
        final List<TerminalNode> cTerminals = context.C();
        if (!cTerminals.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitInitializedIdentifierList -> c");
        }
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.InitializedIdentifierContext initializedIdentifierContext : initializedIdentifierContexts) {
            final String initializedIdentifierText = this.visit(initializedIdentifierContext);
            text.append(initializedIdentifierText);
        }
        return text.toString();
    }

    @Override
    public String visitInitializedIdentifier(final Dart2Parser.InitializedIdentifierContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitInitializedIdentifier` text: {}", context.getText());
        }
        // It can be a variable name.
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final TerminalNode eqTerminal = context.EQ();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(identifierContext));
        if (eqTerminal != null) {
            text.append(" ");
            text.append(this.visit(eqTerminal));
            text.append(" ");
            final String exprText = this.visit(exprContext);
            text.append(exprText);
        }
        return text.toString();
    }

    @Override
    public String visitExpr(final Dart2Parser.ExprContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitExpr` text: {}", context.getText());
        }
        final Dart2Parser.AssignableExpressionContext assignableExpressionContext = context.assignableExpression();
        final Dart2Parser.AssignmentOperatorContext assignmentOperatorContext = context.assignmentOperator();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final Dart2Parser.ConditionalExpressionContext conditionalExpressionContext = context.conditionalExpression();
        final Dart2Parser.CascadeContext cascadeContext = context.cascade();
        final Dart2Parser.ThrowExpressionContext throwExpressionContext = context.throwExpression();
        final StringBuilder text = new StringBuilder();
        if (assignableExpressionContext != null) {
            text.append(this.visit(assignableExpressionContext));
            text.append(" ");
            text.append(this.visit(assignmentOperatorContext));
            text.append(" ");
            final String exprText = this.visit(exprContext);
            text.append(exprText);
        } else if (conditionalExpressionContext != null) {
            text.append(this.visit(conditionalExpressionContext));
        } else if (cascadeContext != null) {
            text.append(this.visit(cascadeContext));
        } else if (throwExpressionContext != null) {
            text.append(this.visit(throwExpressionContext));
        }
        return text.toString();
    }

    @Override
    public String visitAssignmentOperator(final Dart2Parser.AssignmentOperatorContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitAssignmentOperator` text: {}", context.getText());
        }
        final TerminalNode eqTerminal = context.EQ();
        // todo: use `compoundAssignmentOperatorContext` with tests.
        final Dart2Parser.CompoundAssignmentOperatorContext compoundAssignmentOperatorContext = context.compoundAssignmentOperator();
        final StringBuilder text = new StringBuilder();
        if (eqTerminal != null) {
            text.append(this.visit(eqTerminal));
        } else {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitAssignmentOperator -> compoundAssignmentOperator");
        }
        return text.toString();
    }

    @Override
    public String visitAssignableExpression(final Dart2Parser.AssignableExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitAssignableExpression` text: {}", context.getText());
        }
        final Dart2Parser.PrimaryContext primaryContext = context.primary();
        final Dart2Parser.AssignableSelectorPartContext assignableSelectorPartContext = context.assignableSelectorPart();
        final TerminalNode superTerminal = context.SUPER_();
        // todo: use `unconditionalAssignableSelectorContext` with tests.
        final Dart2Parser.UnconditionalAssignableSelectorContext unconditionalAssignableSelectorContext = context.unconditionalAssignableSelector();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final StringBuilder text = new StringBuilder();
        if (primaryContext != null) {
            text.append(this.visit(primaryContext));
            text.append(this.visit(assignableSelectorPartContext));
        } else if (superTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitAssignableExpression -> super");
        } else {
            text.append(this.visit(identifierContext));
        }
        return text.toString();
    }

    @Override
    public String visitAssignableSelectorPart(final Dart2Parser.AssignableSelectorPartContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitAssignableSelectorPart` text: {}", context.getText());
        }
        final List<Dart2Parser.SelectorContext> selectorContexts = context.selector();
        final Dart2Parser.AssignableSelectorContext assignableSelectorContext = context.assignableSelector();
        if (!selectorContexts.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitAssignableSelectorPart -> selector");
        }
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(assignableSelectorContext));
        return text.toString();
    }

    @Override
    public String visitCascade(final Dart2Parser.CascadeContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitCascade` text: {}", context.getText());
        }
        final Dart2Parser.CascadeContext cascadeContext = context.cascade();
        final TerminalNode ddTerminal = context.DD();
        final Dart2Parser.CascadeSectionContext cascadeSectionContext = context.cascadeSection();
        final Dart2Parser.ConditionalExpressionContext conditionalExpressionContext = context.conditionalExpression();
        final TerminalNode quddTerminal = context.QUDD();
        if (quddTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitCascade -> qudd");
        }
        final StringBuilder text = new StringBuilder();
        if (cascadeContext != null) {
            text.append(this.visit(cascadeContext));
            text.append(this.visit(ddTerminal));
            text.append(this.visit(cascadeSectionContext));
        } else if (conditionalExpressionContext != null) {
            text.append(this.visit(conditionalExpressionContext));
            if (ddTerminal != null) {
                text.append(this.visit(ddTerminal));
            }
            text.append(this.visit(cascadeSectionContext));
        }
        return text.toString();
    }

    @Override
    public String visitCascadeSection(final Dart2Parser.CascadeSectionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitCascadeSection` text: {}", context.getText());
        }
        final Dart2Parser.CascadeSelectorContext cascadeSelectorContext = context.cascadeSelector();
        final Dart2Parser.CascadeSectionTailContext cascadeSectionTailContext = context.cascadeSectionTail();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(cascadeSelectorContext));
        text.append(this.visit(cascadeSectionTailContext));
        return text.toString();
    }

    @Override
    public String visitCascadeSelector(final Dart2Parser.CascadeSelectorContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitCascadeSelector` text: {}", context.getText());
        }
        final TerminalNode obTerminal = context.OB();
        // todo: use `exprContext` and `cbTerminal` with tests.
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode cbTerminal = context.CB();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final StringBuilder text = new StringBuilder();
        if (obTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitCascadeSelector -> ob");
        } else {
            text.append(this.visit(identifierContext));
        }
        return text.toString();
    }

    @Override
    public String visitCascadeSectionTail(final Dart2Parser.CascadeSectionTailContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitCascadeSectionTail` text: {}", context.getText());
        }
        final Dart2Parser.CascadeAssignmentContext cascadeAssignmentContext = context.cascadeAssignment();
        final List<Dart2Parser.SelectorContext> selectorContexts = context.selector();
        final Dart2Parser.AssignableSelectorContext assignableSelectorContext = context.assignableSelector();
        final StringBuilder text = new StringBuilder();
        if (cascadeAssignmentContext != null && assignableSelectorContext == null) {
            // cascadeAssignment
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitCascadeSectionTail -> cascadeAssignment");
        } else {
            // selector* ( assignableSelector cascadeAssignment)?
            for (final Dart2Parser.SelectorContext selectorContext : selectorContexts) {
                text.append(this.visit(selectorContext));
            }
            if (assignableSelectorContext != null) {
                throw new UnsupportedOperationException("The following parsing path is not supported yet: visitCascadeSectionTail -> assignableSelector");
            }
        }
        return text.toString();
    }

    @Override
    public String visitThrowExpression(final Dart2Parser.ThrowExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitThrowExpression` text: {}", context.getText());
        }
        final TerminalNode throwTerminal = context.THROW_();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(throwTerminal));
        text.append(" ");
        text.append(this.visit(exprContext));
        return text.toString();
    }

    @Override
    public String visitConditionalExpression(final Dart2Parser.ConditionalExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitConditionalExpression` text: {}", context.getText());
        }
        final Dart2Parser.IfNullExpressionContext ifNullExpressionContext = context.ifNullExpression();
        final TerminalNode quTerminal = context.QU();
        final List<Dart2Parser.ExpressionWithoutCascadeContext> expressionWithoutCascadeContexts = context.expressionWithoutCascade();
        final TerminalNode coTerminal = context.CO();
        if (quTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitConditionalExpression -> qu");
        }
        if (!expressionWithoutCascadeContexts.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitConditionalExpression -> expressionWithoutCascade");
        }
        if (coTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitConditionalExpression -> co");
        }
        final StringBuilder text = new StringBuilder();
        final String ifNullExpressionText = this.visit(ifNullExpressionContext);
        text.append(ifNullExpressionText);
        return text.toString();
    }

    @Override
    public String visitIfNullExpression(final Dart2Parser.IfNullExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitIfNullExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.LogicalOrExpressionContext> logicalOrExpressionContexts = context.logicalOrExpression();
        final List<TerminalNode> ququTerminals = context.QUQU();
        final StringBuilder text = new StringBuilder();
        final Dart2Parser.LogicalOrExpressionContext firstLogicalOrExpression = logicalOrExpressionContexts.get(0);
        text.append(this.visit(firstLogicalOrExpression));
        for (int index = 0; index < ququTerminals.size(); index++) {
            final TerminalNode ququTerminal = ququTerminals.get(index);
            final Dart2Parser.LogicalOrExpressionContext logicalOrExpressionContext = logicalOrExpressionContexts.get(index + 1);
            text.append(" ");
            text.append(this.visit(ququTerminal));
            text.append(" ");
            text.append(this.visit(logicalOrExpressionContext));
        }
        return text.toString();
    }

    @Override
    public String visitLogicalOrExpression(final Dart2Parser.LogicalOrExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitLogicalOrExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.LogicalAndExpressionContext> logicalAndExpressionContexts = context.logicalAndExpression();
        final List<TerminalNode> ppTerminals = context.PP();
        final Dart2Parser.LogicalAndExpressionContext firstLogicalAndExpressionContext = logicalAndExpressionContexts.get(0);
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(firstLogicalAndExpressionContext));
        for (int index = 0; index < ppTerminals.size(); index++) {
            final TerminalNode ppTerminal = ppTerminals.get(index);
            final Dart2Parser.LogicalAndExpressionContext logicalAndExpressionContext = logicalAndExpressionContexts.get(index + 1);
            text.append(" ");
            text.append(this.visit(ppTerminal));
            text.append(" ");
            text.append(this.visit(logicalAndExpressionContext));
        }
        return text.toString();
    }

    @Override
    public String visitLogicalAndExpression(final Dart2Parser.LogicalAndExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitLogicalAndExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.EqualityExpressionContext> equalityExpressionContexts = context.equalityExpression();
        final List<TerminalNode> aaTerminals = context.AA();
        final StringBuilder text = new StringBuilder();
        final Dart2Parser.EqualityExpressionContext firstEqualityExpressionContext = equalityExpressionContexts.get(0);
        text.append(this.visit(firstEqualityExpressionContext));
        for (int index = 0; index < aaTerminals.size(); index++) {
            final TerminalNode aaTerminal = aaTerminals.get(index);
            final Dart2Parser.EqualityExpressionContext equalityExpressionContext = equalityExpressionContexts.get(index + 1);
            text.append(" ");
            text.append(this.visit(aaTerminal));
            text.append(" ");
            text.append(this.visit(equalityExpressionContext));
        }
        return text.toString();
    }

    @Override
    public String visitEqualityExpression(final Dart2Parser.EqualityExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitEqualityExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.RelationalExpressionContext> relationalExpressionContexts = context.relationalExpression();
        final Dart2Parser.EqualityOperatorContext equalityOperatorContext = context.equalityOperator();
        final TerminalNode superTerminal = context.SUPER_();
        final StringBuilder text = new StringBuilder();
        final Dart2Parser.RelationalExpressionContext firstRelationalExpressionContext = relationalExpressionContexts.get(0);
        final String firstRelationalExpressionText = this.visit(firstRelationalExpressionContext);
        if (superTerminal == null) {
            text.append(firstRelationalExpressionText);
            if (equalityOperatorContext != null) {
                text.append(" ");
                text.append(this.visit(equalityOperatorContext));
                text.append(" ");
                final Dart2Parser.RelationalExpressionContext secondRelationalExpressionContext = relationalExpressionContexts.get(1);
                final String secondRelationalExpressionText = this.visit(secondRelationalExpressionContext);
                text.append(secondRelationalExpressionText);
            }
        } else {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitEqualityExpression -> super");
        }
        return text.toString();
    }

    @Override
    public String visitEqualityOperator(final Dart2Parser.EqualityOperatorContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitEqualityOperator` text: {}", context.getText());
        }
        final TerminalNode eeTerminal = context.EE();
        final TerminalNode neTerminal = context.NE();
        final StringBuilder text = new StringBuilder();
        if (eeTerminal != null) {
            text.append(this.visit(eeTerminal));
        } else {
            text.append(this.visit(neTerminal));
        }
        return text.toString();
    }

    @Override
    public String visitRelationalExpression(final Dart2Parser.RelationalExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitRelationalExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.BitwiseOrExpressionContext> bitwiseOrExpressionContexts = context.bitwiseOrExpression();
        final Dart2Parser.TypeTestContext typeTestContext = context.typeTest();
        final Dart2Parser.TypeCastContext typeCastContext = context.typeCast();
        final Dart2Parser.RelationalOperatorContext relationalOperatorContext = context.relationalOperator();
        final TerminalNode superTerminal = context.SUPER_();
        if (typeTestContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitRelationalExpression -> typeTest");
        }
        if (typeCastContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitRelationalExpression -> typeCast");
        }
        final StringBuilder text = new StringBuilder();
        final Dart2Parser.BitwiseOrExpressionContext firstBitwiseOrExpression = bitwiseOrExpressionContexts.get(0);
        final String firstBitwiseOrExpressionText = this.visit(firstBitwiseOrExpression);
        if (bitwiseOrExpressionContexts.size() > 1) {
            text.append(firstBitwiseOrExpressionText);
            text.append(" ");
            // todo: visit relationalOperatorContext instead of getText().
            text.append(relationalOperatorContext.getText());
            text.append(" ");
            final Dart2Parser.BitwiseOrExpressionContext secondBitwiseOrExpression = bitwiseOrExpressionContexts.get(1);
            final String secondBitwiseOrExpressionText = this.visit(secondBitwiseOrExpression);
            text.append(secondBitwiseOrExpressionText);
        } else {
            if (superTerminal == null) {
                text.append(firstBitwiseOrExpressionText);
            } else {
                throw new UnsupportedOperationException("The following parsing path is not supported yet: visitRelationalExpression -> super");
            }
        }
        return text.toString();
    }

    @Override
    public String visitBitwiseOrExpression(final Dart2Parser.BitwiseOrExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitBitwiseOrExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.BitwiseXorExpressionContext> bitwiseXorExpressionContexts = context.bitwiseXorExpression();
        final List<TerminalNode> pTerminals = context.P();
        final TerminalNode superTerminal = context.SUPER_();
        if (!pTerminals.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitBitwiseOrExpression -> p");
        }
        if (superTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitBitwiseOrExpression -> super");
        }
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.BitwiseXorExpressionContext bitwiseXorExpression : bitwiseXorExpressionContexts) {
            final String bitwiseXorExpressionText = this.visit(bitwiseXorExpression);
            text.append(bitwiseXorExpressionText);
        }
        return text.toString();
    }

    @Override
    public String visitBitwiseXorExpression(final Dart2Parser.BitwiseXorExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitBitwiseXorExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.BitwiseAndExpressionContext> bitwiseAndExpressionContexts = context.bitwiseAndExpression();
        final List<TerminalNode> cirTerminals = context.CIR();
        final TerminalNode superTerminal = context.SUPER_();
        if (!cirTerminals.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitBitwiseXorExpression -> cir");
        }
        if (superTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitBitwiseXorExpression -> super");
        }
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.BitwiseAndExpressionContext bitwiseAndExpression : bitwiseAndExpressionContexts) {
            final String bitwiseAndExpressionText = this.visit(bitwiseAndExpression);
            text.append(bitwiseAndExpressionText);
        }
        return text.toString();
    }

    @Override
    public String visitBitwiseAndExpression(final Dart2Parser.BitwiseAndExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitBitwiseAndExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.ShiftExpressionContext> shiftExpressionContexts = context.shiftExpression();
        final List<TerminalNode> aTerminals = context.A();
        final TerminalNode superTerminal = context.SUPER_();
        if (!aTerminals.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitBitwiseAndExpression -> a");
        }
        if (superTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitBitwiseAndExpression -> super");
        }
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.ShiftExpressionContext shiftExpression : shiftExpressionContexts) {
            final String shiftExpressionText = this.visit(shiftExpression);
            text.append(shiftExpressionText);
        }
        return text.toString();
    }

    @Override
    public String visitShiftExpression(final Dart2Parser.ShiftExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitShiftExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.AdditiveExpressionContext> additiveExpressionContexts = context.additiveExpression();
        final List<Dart2Parser.ShiftOperatorContext> shiftOperatorContexts = context.shiftOperator();
        final TerminalNode superTerminal = context.SUPER_();
        if (!shiftOperatorContexts.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitShiftExpression -> shiftOperator");
        }
        if (superTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitShiftExpression -> super");
        }
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.AdditiveExpressionContext additiveExpression : additiveExpressionContexts) {
            final String additiveExpressionText = this.visit(additiveExpression);
            text.append(additiveExpressionText);
        }
        return text.toString();
    }

    @Override
    public String visitAdditiveExpression(final Dart2Parser.AdditiveExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitAdditiveExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.MultiplicativeExpressionContext> multiplicativeExpressionContexts = context.multiplicativeExpression();
        final List<Dart2Parser.AdditiveOperatorContext> additiveOperatorContexts = context.additiveOperator();
        final TerminalNode superTerminal = context.SUPER_();
        if (superTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitAdditiveExpression -> super");
        }
        final StringBuilder text = new StringBuilder();
        for (int index = 0; index < multiplicativeExpressionContexts.size(); index++) {
            final Dart2Parser.MultiplicativeExpressionContext multiplicativeExpression = multiplicativeExpressionContexts.get(index);
            final String multiplicativeExpressionText = this.visit(multiplicativeExpression);
            text.append(multiplicativeExpressionText);
            if (index < additiveOperatorContexts.size()) {
                final Dart2Parser.AdditiveOperatorContext additiveOperatorContext = additiveOperatorContexts.get(index);
                text.append(" ");
                // todo: visit additiveOperatorContext instead of getText().
                final String additiveOperatorText = additiveOperatorContext.getText();
                text.append(additiveOperatorText);
                text.append(" ");
            }
        }
        return text.toString();
    }

    @Override
    public String visitMultiplicativeExpression(final Dart2Parser.MultiplicativeExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitMultiplicativeExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.UnaryExpressionContext> unaryExpressionContexts = context.unaryExpression();
        final List<Dart2Parser.MultiplicativeOperatorContext> multiplicativeOperatorContexts = context.multiplicativeOperator();
        final TerminalNode superTerminal = context.SUPER_();
        final StringBuilder text = new StringBuilder();
        if (superTerminal == null) {
            final Dart2Parser.UnaryExpressionContext firstUnaryExpression = unaryExpressionContexts.get(0);
            final String firstUnaryExpressionText = this.visit(firstUnaryExpression);
            text.append(firstUnaryExpressionText);
            for (int index = 0; index < multiplicativeOperatorContexts.size(); index++) {
                text.append(" ");
                final Dart2Parser.MultiplicativeOperatorContext multiplicativeOperatorContext = multiplicativeOperatorContexts.get(index);
                // todo: visit multiplicativeOperatorContext instead of getText().
                final String multiplicativeOperatorText = multiplicativeOperatorContext.getText();
                text.append(multiplicativeOperatorText);
                text.append(" ");
                final Dart2Parser.UnaryExpressionContext unaryExpressionContext = unaryExpressionContexts.get(index + 1);
                final String unaryExpressionText = this.visit(unaryExpressionContext);
                text.append(unaryExpressionText);
            }
        } else {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitMultiplicativeExpression -> super");
        }
        return text.toString();
    }

    @Override
    public String visitUnaryExpression(final Dart2Parser.UnaryExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitUnaryExpression` text: {}", context.getText());
        }
        final Dart2Parser.PrefixOperatorContext prefixOperatorContext = context.prefixOperator();
        final Dart2Parser.UnaryExpressionContext unaryExpressionContext = context.unaryExpression();
        final Dart2Parser.AwaitExpressionContext awaitExpressionContext = context.awaitExpression();
        final Dart2Parser.PostfixExpressionContext postfixExpressionContext = context.postfixExpression();
        final Dart2Parser.MinusOperatorContext minusOperatorContext = context.minusOperator();
        final Dart2Parser.TildeOperatorContext tildeOperatorContext = context.tildeOperator();
        final TerminalNode superTerminal = context.SUPER_();
        final Dart2Parser.IncrementOperatorContext incrementOperatorContext = context.incrementOperator();
        final Dart2Parser.AssignableExpressionContext assignableExpressionContext = context.assignableExpression();
        if (minusOperatorContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitUnaryExpression -> minusOperator");
        }
        if (tildeOperatorContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitUnaryExpression -> tildeOperator");
        }
        if (superTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitUnaryExpression -> super");
        }
        if (incrementOperatorContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitUnaryExpression -> incrementOperator");
        }
        if (assignableExpressionContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitUnaryExpression -> assignableExpression");
        }
        final StringBuilder text = new StringBuilder();
        if (prefixOperatorContext != null) {
            // todo: visit prefixOperatorContext instead of getText().
            text.append(prefixOperatorContext.getText());
            text.append(this.visit(unaryExpressionContext));
        }
        if (awaitExpressionContext != null) {
            text.append(this.visit(awaitExpressionContext));
        }
        if (postfixExpressionContext != null) {
            final String postfixExpressionText = this.visit(postfixExpressionContext);
            text.append(postfixExpressionText);
        }
        return text.toString();
    }

    @Override
    public String visitAwaitExpression(final Dart2Parser.AwaitExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitAwaitExpression` text: {}", context.getText());
        }
        final TerminalNode awaitTerminal = context.AWAIT_();
        final Dart2Parser.UnaryExpressionContext unaryExpressionContext = context.unaryExpression();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(awaitTerminal));
        text.append(" ");
        text.append(this.visit(unaryExpressionContext));
        return text.toString();
    }

    @Override
    public String visitPostfixExpression(final Dart2Parser.PostfixExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitPostfixExpression` text: {}", context.getText());
        }
        final Dart2Parser.AssignableExpressionContext assignableExpressionContext = context.assignableExpression();
        final Dart2Parser.PostfixOperatorContext postfixOperatorContext = context.postfixOperator();
        final Dart2Parser.PrimaryContext primaryContext = context.primary();
        final List<Dart2Parser.SelectorContext> selectorContexts = context.selector();
        final StringBuilder text = new StringBuilder();
        if (assignableExpressionContext != null) {
            text.append(this.visit(assignableExpressionContext));
            text.append(this.visit(postfixOperatorContext));
        }
        if (primaryContext != null) {
            final String primaryText = this.visit(primaryContext);
            text.append(primaryText);
        }
        for (final Dart2Parser.SelectorContext selectorContext : selectorContexts) {
            final String selectorText = this.visit(selectorContext);
            text.append(selectorText);
        }
        return text.toString();
    }

    @Override
    public String visitPostfixOperator(final Dart2Parser.PostfixOperatorContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitPostfixOperator` text: {}", context.getText());
        }
        final Dart2Parser.IncrementOperatorContext incrementOperatorContext = context.incrementOperator();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(incrementOperatorContext));
        return text.toString();
    }

    @Override
    public String visitIncrementOperator(final Dart2Parser.IncrementOperatorContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitIncrementOperator` text: {}", context.getText());
        }
        final TerminalNode plplTerminal = context.PLPL();
        final TerminalNode mmTerminal = context.MM();
        final StringBuilder text = new StringBuilder();
        if (plplTerminal != null) {
            text.append(this.visit(plplTerminal));
        } else {
            text.append(this.visit(mmTerminal));
        }
        return text.toString();
    }

    @Override
    public String visitSelector(final Dart2Parser.SelectorContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitSelector` text: {}", context.getText());
        }
        final TerminalNode notTerminal = context.NOT();
        final Dart2Parser.AssignableSelectorContext assignableSelectorContext = context.assignableSelector();
        final Dart2Parser.ArgumentPartContext argumentPartContext = context.argumentPart();
        final StringBuilder text = new StringBuilder();
        if (notTerminal != null) {
            text.append(this.visit(notTerminal));
        } else if (assignableSelectorContext != null) {
            text.append(this.visit(assignableSelectorContext));
        } else {
            final String argumentPartText = this.visit(argumentPartContext);
            text.append(argumentPartText);
        }
        return text.toString();
    }

    @Override
    public String visitAssignableSelector(final Dart2Parser.AssignableSelectorContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitAssignableSelector` text: {}", context.getText());
        }
        final Dart2Parser.UnconditionalAssignableSelectorContext unconditionalAssignableSelectorContext = context.unconditionalAssignableSelector();
        final TerminalNode qudTerminal = context.QUD();
        // todo: use `quTerminal`, `obTerminal`, `exprContext`, and `cbTerminal` with tests.
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final TerminalNode quTerminal = context.QU();
        final TerminalNode obTerminal = context.OB();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode cbTerminal = context.CB();
        final StringBuilder text = new StringBuilder();
        if (unconditionalAssignableSelectorContext != null) {
            text.append(this.visit(unconditionalAssignableSelectorContext));
        } else if (qudTerminal != null) {
            text.append(this.visit(qudTerminal));
            text.append(this.visit(identifierContext));
        } else {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitAssignableSelector -> qu");
        }
        return text.toString();
    }

    @Override
    public String visitUnconditionalAssignableSelector(final Dart2Parser.UnconditionalAssignableSelectorContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitUnconditionalAssignableSelector` text: {}", context.getText());
        }
        final TerminalNode obTerminal = context.OB();
        // todo: use `exprContext` and `cbTerminal` with tests.
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode cbTerminal = context.CB();
        final TerminalNode dTerminal = context.D();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final StringBuilder text = new StringBuilder();
        if (obTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitUnconditionalAssignableSelector -> ob");
        } else {
            text.append(this.visit(dTerminal));
            text.append(this.visit(identifierContext));
        }
        return text.toString();
    }

    @Override
    public String visitArgumentPart(final Dart2Parser.ArgumentPartContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitArgumentPart` text: {}", context.getText());
        }
        final Dart2Parser.TypeArgumentsContext typeArgumentsContext = context.typeArguments();
        final Dart2Parser.ArgumentsContext argumentsContext = context.arguments();
        final StringBuilder text = new StringBuilder();
        if (typeArgumentsContext != null) {
            text.append(this.visit(typeArgumentsContext));
        }
        if (argumentsContext != null) {
            final String argumentsText = this.visit(argumentsContext);
            text.append(argumentsText);
        }
        return text.toString();
    }

    @Override
    public String visitPrimary(final Dart2Parser.PrimaryContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitPrimary` text: {}", context.getText());
        }
        final Dart2Parser.ThisExpressionContext thisExpressionContext = context.thisExpression();
        final TerminalNode superTerminal = context.SUPER_();
        final Dart2Parser.UnconditionalAssignableSelectorContext unconditionalAssignableSelectorContext = context.unconditionalAssignableSelector();
        final Dart2Parser.ArgumentPartContext argumentPartContext = context.argumentPart();
        final Dart2Parser.FunctionExpressionContext functionExpressionContext = context.functionExpression();
        final Dart2Parser.LiteralContext literalContext = context.literal();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final Dart2Parser.NewExpressionContext newExpressionContext = context.newExpression();
        final Dart2Parser.ConstObjectExpressionContext constObjectExpressionContext = context.constObjectExpression();
        final Dart2Parser.ConstructorInvocationContext constructorInvocationContext = context.constructorInvocation();
        final TerminalNode opTerminal = context.OP();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode cpTerminal = context.CP();
        if (argumentPartContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitPrimary -> argumentPart");
        }
        if (constructorInvocationContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitPrimary -> constructorInvocation");
        }
        final StringBuilder text = new StringBuilder();
        if (thisExpressionContext != null) {
            // todo: visit thisExpressionContext instead of getText().
            text.append(thisExpressionContext.getText());
        }
        if (superTerminal != null) {
            text.append(this.visit(superTerminal));
            if (unconditionalAssignableSelectorContext != null) {
                // todo: visit unconditionalAssignableSelectorContext instead of getText().
                text.append(unconditionalAssignableSelectorContext.getText());
            }
        }
        if (functionExpressionContext != null) {
            text.append(this.visit(functionExpressionContext));
        }
        if (newExpressionContext != null) {
            final String newExpressionText = this.visit(newExpressionContext);
            text.append(newExpressionText);
        }
        if (constObjectExpressionContext != null) {
            text.append(this.visit(constObjectExpressionContext));
        }
        if (literalContext != null) {
            final String literalText = this.visit(literalContext);
            text.append(literalText);
        }
        if (identifierContext != null) {
            final String identifierText = this.visit(identifierContext);
            text.append(identifierText);
        }
        if (opTerminal != null) {
            text.append(this.visit(opTerminal));
            text.append(this.visit(exprContext));
            text.append(this.visit(cpTerminal));
        }
        return text.toString();
    }

    @Override
    public String visitConstObjectExpression(final Dart2Parser.ConstObjectExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitConstObjectExpression` text: {}", context.getText());
        }
        final TerminalNode constTerminal = context.CONST_();
        final Dart2Parser.ConstructorDesignationContext constructorDesignationContext = context.constructorDesignation();
        final Dart2Parser.ArgumentsContext argumentsContext = context.arguments();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(constTerminal));
        text.append(" ");
        // todo: visit constructorDesignationContext instead of getText().
        text.append(constructorDesignationContext.getText());
        text.append(this.visit(argumentsContext));
        return text.toString();
    }

    @Override
    public String visitFunctionExpression(final Dart2Parser.FunctionExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFunctionExpression` text: {}", context.getText());
        }
        final Dart2Parser.FormalParameterPartContext formalParameterPartContext = context.formalParameterPart();
        final Dart2Parser.FunctionExpressionBodyContext functionExpressionBodyContext = context.functionExpressionBody();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(formalParameterPartContext));
        text.append(" ");
        text.append(this.visit(functionExpressionBodyContext));
        return text.toString();
    }

    @Override
    public String visitFunctionExpressionBody(final Dart2Parser.FunctionExpressionBodyContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFunctionExpressionBody` text: {}", context.getText());
        }
        final TerminalNode asyncTerminal = context.ASYNC_();
        final TerminalNode egTerminal = context.EG();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode stTerminal = context.ST();
        final TerminalNode syncTerminal = context.SYNC_();
        final Dart2Parser.BlockContext blockContext = context.block();
        if (stTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionExpressionBody -> st");
        }
        final StringBuilder text = new StringBuilder();
        if (egTerminal != null) {
            // ASYNC_? EG expr
            if (asyncTerminal != null) {
                text.append(this.visit(asyncTerminal));
                text.append(" ");
            }
            text.append(this.visit(egTerminal));
            text.append(" ");
            text.append(this.visit(exprContext));
        } else if (blockContext != null) {
            // ( ASYNC_ ST? | SYNC_ ST)? block
            if (asyncTerminal != null) {
                text.append(this.visit(asyncTerminal));
                text.append(" ");
            } else if (syncTerminal != null) {
                text.append(this.visit(syncTerminal));
                text.append(" ");
            }
            text.append(this.visit(blockContext));
        }
        return text.toString();
    }

    @Override
    public String visitIdentifier(final Dart2Parser.IdentifierContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitIdentifier` text: {}", context.getText());
        }
        final TerminalNode identifierTerminal = context.IDENTIFIER();
        final TerminalNode abstractTerminal = context.ABSTRACT_();
        final TerminalNode asTerminal = context.AS_();
        final TerminalNode covariantTerminal = context.COVARIANT_();
        final TerminalNode deferredTerminal = context.DEFERRED_();
        final TerminalNode dynamicTerminal = context.DYNAMIC_();
        final TerminalNode exportTerminal = context.EXPORT_();
        final TerminalNode externalTerminal = context.EXTERNAL_();
        final TerminalNode extensionTerminal = context.EXTENSION_();
        final TerminalNode factoryTerminal = context.FACTORY_();
        final TerminalNode functionTerminal = context.FUNCTION_();
        final TerminalNode getTerminal = context.GET_();
        final TerminalNode implementsTerminal = context.IMPLEMENTS_();
        final TerminalNode importTerminal = context.IMPORT_();
        final TerminalNode interfaceTerminal = context.INTERFACE_();
        final TerminalNode lateTerminal = context.LATE_();
        final TerminalNode libraryTerminal = context.LIBRARY_();
        final TerminalNode mixinTerminal = context.MIXIN_();
        final TerminalNode operatorTerminal = context.OPERATOR_();
        final TerminalNode partTerminal = context.PART_();
        final TerminalNode requiredTerminal = context.REQUIRED_();
        final TerminalNode setTerminal = context.SET_();
        final TerminalNode staticTerminal = context.STATIC_();
        final TerminalNode typedefTerminal = context.TYPEDEF_();
        final TerminalNode asyncTerminal = context.ASYNC_();
        final TerminalNode hideTerminal = context.HIDE_();
        final TerminalNode ofTerminal = context.OF_();
        final TerminalNode onTerminal = context.ON_();
        final TerminalNode showTerminal = context.SHOW_();
        final TerminalNode syncTerminal = context.SYNC_();
        final TerminalNode awaitTerminal = context.AWAIT_();
        final TerminalNode yieldTerminal = context.YIELD_();
        final TerminalNode nativeTerminal = context.NATIVE_();
        if (abstractTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> abstract");
        }
        if (asTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> as");
        }
        if (covariantTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> covariant");
        }
        if (deferredTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> deferred");
        }
        if (dynamicTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> dynamic");
        }
        if (exportTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> export");
        }
        if (externalTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> external");
        }
        if (extensionTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> extension");
        }
        if (factoryTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> factory");
        }
        if (functionTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> function");
        }
        if (getTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> get");
        }
        if (implementsTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> implements");
        }
        if (importTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> import");
        }
        if (interfaceTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> interface");
        }
        if (lateTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> late");
        }
        if (libraryTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> library");
        }
        if (mixinTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> mixin");
        }
        if (operatorTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> operator");
        }
        if (partTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> part");
        }
        if (requiredTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> required");
        }
        if (setTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> set");
        }
        if (staticTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> static");
        }
        if (typedefTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> typedef");
        }
        if (asyncTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> async");
        }
        if (hideTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> hide");
        }
        if (ofTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> of");
        }
        if (onTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> on");
        }
        if (showTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> show");
        }
        if (syncTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> sync");
        }
        if (awaitTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> await");
        }
        if (yieldTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> yield");
        }
        if (nativeTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIdentifier -> native");
        }
        final StringBuilder text = new StringBuilder();
        if (identifierTerminal != null) {
            text.append(this.visit(identifierTerminal));
        }
        return text.toString();
    }

    @Override
    public String visitLiteral(final Dart2Parser.LiteralContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitLiteral` text: {}", context.getText());
        }
        final Dart2Parser.NullLiteralContext nullLiteralContext = context.nullLiteral();
        final Dart2Parser.BooleanLiteralContext booleanLiteralContext = context.booleanLiteral();
        final Dart2Parser.NumericLiteralContext numericLiteralContext = context.numericLiteral();
        final Dart2Parser.StringLiteralContext stringLiteralContext = context.stringLiteral();
        final Dart2Parser.SymbolLiteralContext symbolLiteralContext = context.symbolLiteral();
        final Dart2Parser.ListLiteralContext listLiteralContext = context.listLiteral();
        final Dart2Parser.SetOrMapLiteralContext setOrMapLiteralContext = context.setOrMapLiteral();
        if (symbolLiteralContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLiteral -> symbolLiteral");
        }
        final StringBuilder text = new StringBuilder();
        if (nullLiteralContext != null) {
            // todo: visit nullLiteralContext instead of getText().
            text.append(nullLiteralContext.getText());
        }
        if (booleanLiteralContext != null) {
            // todo: visit booleanLiteralContext instead of getText().
            text.append(booleanLiteralContext.getText());
        }
        if (stringLiteralContext != null) {
            // todo: visit stringLiteralContext instead of getText().
            final String stringLiteralText = stringLiteralContext.getText();
            text.append(stringLiteralText);
        }
        if (setOrMapLiteralContext != null) {
            final String setOrMapLiteralText = this.visit(setOrMapLiteralContext);
            text.append(setOrMapLiteralText);
        }
        if (listLiteralContext != null) {
            final String listLiteralText = this.visit(listLiteralContext);
            text.append(listLiteralText);
        }
        if (numericLiteralContext != null) {
            // todo: visit numericLiteralContext instead of getText().
            text.append(numericLiteralContext.getText());
        }
        return text.toString();
    }

    @Override
    public String visitListLiteral(final Dart2Parser.ListLiteralContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `ListLiteralContext` text: {}", context.getText());
        }
        final TerminalNode constTerminal = context.CONST_();
        final Dart2Parser.TypeArgumentsContext typeArgumentsContext = context.typeArguments();
        final TerminalNode obTerminal = context.OB();
        final Dart2Parser.ElementsContext elementsContext = context.elements();
        final TerminalNode cbTerminal = context.CB();
        if (constTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitListLiteral -> const");
        }
        final StringBuilder text = new StringBuilder();
        if (typeArgumentsContext != null) {
            text.append(this.visit(typeArgumentsContext));
        }
        text.append(this.visit(obTerminal));
        if (elementsContext != null) {
            text.append("\n");
            this.currentIndentLevel++;
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            final String elementsText = this.visit(elementsContext);
            text.append(elementsText);
            text.append("\n");
            this.currentIndentLevel--;
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
        }
        text.append(this.visit(cbTerminal));
        return text.toString();
    }

    @Override
    public String visitSetOrMapLiteral(final Dart2Parser.SetOrMapLiteralContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitSetOrMapLiteral` text: {}", context.getText());
        }
        final TerminalNode constTerminal = context.CONST_();
        final Dart2Parser.TypeArgumentsContext typeArgumentsContext = context.typeArguments();
        final TerminalNode obcTerminal = context.OBC();
        final Dart2Parser.ElementsContext elementsContext = context.elements();
        final TerminalNode cbcTerminal = context.CBC();
        if (constTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitSetOrMapLiteral -> const");
        }
        final StringBuilder text = new StringBuilder();
        if (typeArgumentsContext != null) {
            final String typeArgumentsText = this.visit(typeArgumentsContext);
            text.append(typeArgumentsText);
        }
        if (elementsContext == null) {
            text.append(this.visit(obcTerminal));
            text.append(this.visit(cbcTerminal));
        } else {
            text.append(this.visit(obcTerminal));
            text.append("\n");
            this.currentIndentLevel++;
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            final String elementsText = this.visit(elementsContext);
            text.append(elementsText);
            text.append("\n");
            this.currentIndentLevel--;
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            text.append(this.visit(cbcTerminal));
        }
        return text.toString();
    }

    @Override
    public String visitElements(final Dart2Parser.ElementsContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitElements` text: {}", context.getText());
        }
        final List<Dart2Parser.ElementContext> elementContexts = context.element();
        final StringBuilder text = new StringBuilder();
        final Dart2Parser.ElementContext firstElementContext = elementContexts.get(0);
        final String firstElementText = this.visit(firstElementContext);
        text.append(firstElementText);
        for (int index = 1; index < elementContexts.size(); index++) {
            text.append(",");
            text.append("\n");
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            final String elementText = this.visit(elementContexts.get(index));
            text.append(elementText);
        }
        text.append(",");
        return text.toString();
    }

    @Override
    public String visitElement(final Dart2Parser.ElementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitElement` text: {}", context.getText());
        }
        final Dart2Parser.ExpressionElementContext expressionElementContext = context.expressionElement();
        final Dart2Parser.MapElementContext mapElementContext = context.mapElement();
        final Dart2Parser.SpreadElementContext spreadElementContext = context.spreadElement();
        final Dart2Parser.IfElementContext ifElementContext = context.ifElement();
        final Dart2Parser.ForElementContext forElementContext = context.forElement();
        if (spreadElementContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitElement -> spreadElement");
        }
        if (ifElementContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitElement -> ifElement");
        }
        if (forElementContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitElement -> forElement");
        }
        final StringBuilder text = new StringBuilder();
        if (expressionElementContext != null) {
            text.append(this.visit(expressionElementContext));
        } else if (mapElementContext != null) {
            final String mapElementText = this.visit(mapElementContext);
            text.append(mapElementText);
        }
        return text.toString();
    }

    @Override
    public String visitExpressionElement(final Dart2Parser.ExpressionElementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitExpressionElement` text: {}", context.getText());
        }
        final Dart2Parser.ExprContext exprContext = context.expr();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(exprContext));
        return text.toString();
    }

    @Override
    public String visitMapElement(final Dart2Parser.MapElementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitMapElement` text: {}", context.getText());
        }
        final List<Dart2Parser.ExprContext> exprContexts = context.expr();
        final TerminalNode coTerminal = context.CO();
        final StringBuilder text = new StringBuilder();
        final String firstExprText = this.visit(exprContexts.get(0));
        text.append(firstExprText);
        text.append(this.visit(coTerminal));
        text.append(" ");
        final String secondExprText = this.visit(exprContexts.get(1));
        text.append(secondExprText);
        return text.toString();
    }

    @Override
    public String visitNewExpression(final Dart2Parser.NewExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitNewExpression` text: {}", context.getText());
        }
        final TerminalNode newTerminal = context.NEW_();
        // It can be a class name.
        final Dart2Parser.ConstructorDesignationContext constructorDesignationContext = context.constructorDesignation();
        final Dart2Parser.ArgumentsContext argumentsContext = context.arguments();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(newTerminal));
        text.append(" ");
        // todo: visit constructorDesignationContext instead of getText().
        text.append(constructorDesignationContext.getText());
        final String argumentsText = this.visit(argumentsContext);
        text.append(argumentsText);
        return text.toString();
    }

    @Override
    public String visitArguments(final Dart2Parser.ArgumentsContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitArguments` text: {}", context.getText());
        }
        final TerminalNode opTerminal = context.OP();
        final Dart2Parser.ArgumentListContext argumentListContext = context.argumentList();
        final TerminalNode cTerminal = context.C();
        final TerminalNode cpTerminal = context.CP();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(opTerminal));
        if (argumentListContext != null) {
            final int visitArgumentsCountBefore = this.ruleVisitCounts.getOrDefault(Dart2Parser.ArgumentsContext.class.getSimpleName(), 0);
            final int visitNamedArgumentCountBefore = this.ruleVisitCounts.getOrDefault(Dart2Parser.NamedArgumentContext.class.getSimpleName(), 0);
            // We need to increment the indentation up front so that the child will have the correct indentation level.
            // If the incrementation turns out to be wrong, we can remove it later.
            this.currentIndentLevel++;
            final String argumentsText = this.visit(argumentListContext);
            final int visitArgumentsCountAfter = this.ruleVisitCounts.getOrDefault(Dart2Parser.ArgumentsContext.class.getSimpleName(), 0);
            final int visitNamedArgumentCountAfter = this.ruleVisitCounts.getOrDefault(Dart2Parser.NamedArgumentContext.class.getSimpleName(), 0);
            // We assume the following condition means nested object initialization. Ex: User(User('Rin'));
            final boolean objNested = visitArgumentsCountBefore < visitArgumentsCountAfter;
            final boolean namedParamUsed = visitNamedArgumentCountBefore < visitNamedArgumentCountAfter;
            if (objNested || namedParamUsed) {
                text.append("\n");
                text.append(this.indentUnit.repeat(this.currentIndentLevel));
                text.append(argumentsText);
                if (cTerminal != null) {
                    text.append(this.visit(cTerminal));
                }
                text.append("\n");
                this.currentIndentLevel--;
                text.append(this.indentUnit.repeat(this.currentIndentLevel));
            } else {
                this.currentIndentLevel--;
                text.append(
                    // Dedent the arguments because it was wrong.
                    argumentsText.replaceAll(
                        "\n" + this.indentUnit,
                        "\n"
                    )
                );
                if (cTerminal != null) {
                    text.append(this.visit(cTerminal));
                }
            }
        }
        text.append(this.visit(cpTerminal));
        return text.toString();
    }

    @Override
    public String visitArgumentList(final Dart2Parser.ArgumentListContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitArgumentList` text: {}", context.getText());
        }
        final List<Dart2Parser.NamedArgumentContext> namedArgumentContexts = context.namedArgument();
        final List<TerminalNode> cTerminals = context.C();
        final Dart2Parser.ExpressionListContext expressionListContext = context.expressionList();
        final StringBuilder text = new StringBuilder();
        if (!namedArgumentContexts.isEmpty()) {
            final Dart2Parser.NamedArgumentContext firstNamedArgumentContext = namedArgumentContexts.get(0);
            text.append(this.visit(firstNamedArgumentContext));
        }
        if (expressionListContext != null) {
            final String expressionListText = this.visit(expressionListContext);
            text.append(expressionListText);
        }
        for (int index = 0; index < cTerminals.size(); index++) {
            final TerminalNode cTerminal = cTerminals.get(index);
            final Dart2Parser.NamedArgumentContext namedArgumentContext;
            if (cTerminals.size() == namedArgumentContexts.size()) {
                namedArgumentContext = namedArgumentContexts.get(index);
            } else {
                namedArgumentContext = namedArgumentContexts.get(index + 1);
            }
            text.append(this.visit(cTerminal));
            text.append("\n");
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            text.append(this.visit(namedArgumentContext));
        }
        return text.toString();
    }

    @Override
    public String visitNamedArgument(final Dart2Parser.NamedArgumentContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitNamedArgument` text: {}", context.getText());
        }
        final Dart2Parser.LabelContext labelContext = context.label();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(labelContext));
        text.append(" ");
        text.append(this.visit(exprContext));
        return text.toString();
    }

    @Override
    public String visitLabel(final Dart2Parser.LabelContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitLabel` text: {}", context.getText());
        }
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final TerminalNode coTerminal = context.CO();
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(identifierContext));
        text.append(this.visit(coTerminal));
        return text.toString();
    }

    @Override
    public String visitChildren(final RuleNode node) {
        throw new UnsupportedOperationException(
            String.format(
                "The following rule is not implemented yet: %s text: %s",
                node.getClass(),
                node.getText()
            )
        );
    }

    @Override
    public String visit(final ParseTree tree) {
        final String ruleName = tree.getClass().getSimpleName();
        this.ruleVisitCounts.putIfAbsent(ruleName, 1);
        this.ruleVisitCounts.computeIfPresent(ruleName, (ignored, currentCount) -> currentCount + 1);
        return tree.accept(this);
    }

    @Override
    public String visitTerminal(final TerminalNode node) {
        final int tokenIndex = node.getSymbol().getTokenIndex();
        final int commentChannel = 3;
        final List<Token> comments = this.tokens.getHiddenTokensToLeft(tokenIndex, commentChannel);
        final StringBuilder text = new StringBuilder();
        if (comments != null) {
            for (final Token comment : comments) {
                text.append(comment.getText());
                text.append("\n");
                text.append(this.indentUnit.repeat(this.currentIndentLevel));
            }
        }
        text.append(node.getText());
        return text.toString();
    }

}
