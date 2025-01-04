package com.levelrin;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.levelrin.antlr.generated.Dart2Parser;
import com.levelrin.antlr.generated.Dart2ParserBaseVisitor;
import java.util.List;
import java.util.StringJoiner;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Return type is a JSON.
 * All of it must have $.text attribute.
 */
public final class DartVisitor extends Dart2ParserBaseVisitor<DocumentContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DartVisitor.class);

    private final String indentUnit = "  ";

    private int currentIndentLevel = 0;

    @Override
    public DocumentContext visitCompilationUnit(final Dart2Parser.CompilationUnitContext context) {
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (libraryDeclarationContext != null) {
            final String libraryDeclarationText = this.visit(libraryDeclarationContext).read("$.text");
            text.append(libraryDeclarationText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitLibraryDeclaration(final Dart2Parser.LibraryDeclarationContext context) {
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (!importOrExportContexts.isEmpty()) {
            for (final Dart2Parser.ImportOrExportContext importOrExportContext : importOrExportContexts) {
                final String importOrExportText = this.visit(importOrExportContext).read("$.text");
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
            for (final Dart2Parser.TopLevelDeclarationContext topLevelDeclarationContext : topLevelDeclarationContexts) {
                final Dart2Parser.ClassDeclarationContext classDeclarationContext = topLevelDeclarationContext.classDeclaration();
                final Dart2Parser.MixinDeclarationContext mixinDeclarationContext = topLevelDeclarationContext.mixinDeclaration();
                final Dart2Parser.ExtensionDeclarationContext extensionDeclarationContext = topLevelDeclarationContext.extensionDeclaration();
                final Dart2Parser.EnumTypeContext enumTypeContext = topLevelDeclarationContext.enumType();
                final Dart2Parser.TypeAliasContext typeAliasContext = topLevelDeclarationContext.typeAlias();
                final Dart2Parser.FunctionSignatureContext functionSignatureContext = topLevelDeclarationContext.functionSignature();
                final Dart2Parser.GetterSignatureContext getterSignatureContext = topLevelDeclarationContext.getterSignature();
                final Dart2Parser.SetterSignatureContext setterSignatureContext = topLevelDeclarationContext.setterSignature();
                final Dart2Parser.FunctionBodyContext functionBodyContext = topLevelDeclarationContext.functionBody();
                final Dart2Parser.TypeContext typeContext = topLevelDeclarationContext.type();
                final Dart2Parser.StaticFinalDeclarationListContext staticFinalDeclarationListContext = topLevelDeclarationContext.staticFinalDeclarationList();
                final Dart2Parser.InitializedIdentifierListContext initializedIdentifierListContext = topLevelDeclarationContext.initializedIdentifierList();
                final Dart2Parser.VarOrTypeContext varOrTypeContext = topLevelDeclarationContext.varOrType();
                if (classDeclarationContext != null) {
                    final String classDeclarationText = this.visit(classDeclarationContext).read("$.text");
                    text.append(classDeclarationText);
                }
                if (mixinDeclarationContext != null) {
                    throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryDeclaration -> topLevelDeclaration -> mixinDeclaration");
                }
                if (extensionDeclarationContext != null) {
                    throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryDeclaration -> topLevelDeclaration -> extensionDeclaration");
                }
                if (enumTypeContext != null) {
                    throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryDeclaration -> topLevelDeclaration -> enumType");
                }
                if (typeAliasContext != null) {
                    throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryDeclaration -> topLevelDeclaration -> typeAlias");
                }
                if (functionSignatureContext != null) {
                    throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryDeclaration -> topLevelDeclaration -> functionSignature");
                }
                if (getterSignatureContext != null) {
                    throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryDeclaration -> topLevelDeclaration -> getterSignature");
                }
                if (setterSignatureContext != null) {
                    throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryDeclaration -> topLevelDeclaration -> setterSignature");
                }
                if (functionBodyContext != null) {
                    throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryDeclaration -> topLevelDeclaration -> functionBody");
                }
                if (typeContext != null) {
                    throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryDeclaration -> topLevelDeclaration -> type");
                }
                if (staticFinalDeclarationListContext != null) {
                    throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryDeclaration -> topLevelDeclaration -> staticFinalDeclarationList");
                }
                if (initializedIdentifierListContext != null) {
                    throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryDeclaration -> topLevelDeclaration -> initializedIdentifierList");
                }
                if (varOrTypeContext != null) {
                    throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryDeclaration -> topLevelDeclaration -> varOrType");
                }
            }
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitImportOrExport(final Dart2Parser.ImportOrExportContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitImportOrExport` text: {}", context.getText());
        }
        final Dart2Parser.LibraryImportContext libraryImportContext = context.libraryImport();
        final Dart2Parser.LibraryExportContext libraryExportContext = context.libraryExport();
        if (libraryExportContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitImportOrExport -> libraryExport");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (libraryImportContext != null) {
            final String libraryImportText = this.visit(libraryImportContext).read("$.text");
            text.append(libraryImportText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitLibraryImport(final Dart2Parser.LibraryImportContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitLibraryImport` text: {}", context.getText());
        }
        final Dart2Parser.MetadataContext metadataContext = context.metadata();
        final Dart2Parser.ImportSpecificationContext importSpecificationContext = context.importSpecification();
        if (!metadataContext.getText().isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLibraryImport -> metadata");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final String importSpecificationText = this.visit(importSpecificationContext).read("$.text");
        text.append(importSpecificationText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitImportSpecification(final Dart2Parser.ImportSpecificationContext context) {
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
        if (asTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitImportSpecification -> as");
        }
        if (identifierContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitImportSpecification -> identifier");
        }
        if (!combinatorContexts.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitImportSpecification -> combinator");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        text.append(importTerminal.getText());
        text.append(" ");
        // todo: visit configurableUriContext instead of getText().
        text.append(configurableUriContext.getText());
        text.append(scTerminal.getText());
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitClassDeclaration(final Dart2Parser.ClassDeclarationContext context) {
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
        if (typeParametersContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitClassDeclaration -> typeParameters");
        }
        if (superclassContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitClassDeclaration -> superclass");
        }
        if (interfacesContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitClassDeclaration -> interfaces");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (abstractTerminal != null) {
            text.append(abstractTerminal.getText());
            text.append(" ");
        }
        text.append(classTerminal.getText());
        text.append(" ");
        // todo: visit typeIdentifierContext instead of getText().
        text.append(typeIdentifierContext.getText());
        text.append(" ");
        text.append(obcTerminal.getText());
        text.append("\n\n");
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
        this.currentIndentLevel++;
        for (final Dart2Parser.ClassMemberDeclarationContext classMemberDeclarationContext : classMemberDeclarationContexts) {
            final String classMemberDeclarationText = this.visit(classMemberDeclarationContext).read("$.text");
            text.append(classMemberDeclarationText);
            text.append("\n\n");
        }
        this.currentIndentLevel--;
        text.append(cbcTerminal.getText());
        text.append("\n");
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitClassMemberDeclaration(final Dart2Parser.ClassMemberDeclarationContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitClassMemberDeclaration` text: {}", context.getText());
        }
        final Dart2Parser.DeclarationContext declarationContext = context.declaration();
        final TerminalNode scTerminal = context.SC();
        final Dart2Parser.MethodSignatureContext methodSignatureContext = context.methodSignature();
        final Dart2Parser.FunctionBodyContext functionBodyContext = context.functionBody();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (declarationContext != null) {
            final String declarationText = this.visit(declarationContext).read("$.text");
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            text.append(declarationText);
            text.append(scTerminal.getText());
        }
        if (methodSignatureContext != null) {
            final String methodSignatureText = this.visit(methodSignatureContext).read("$.text");
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            text.append(methodSignatureText);
            text.append(" ");
            final String functionBodyText = this.visit(functionBodyContext).read("$.text");
            text.append(functionBodyText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitMethodSignature(final Dart2Parser.MethodSignatureContext context) {
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
        if (staticTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitMethodSignature -> staticTerminal");
        }
        if (getterSignatureContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitMethodSignature -> getterSignature");
        }
        if (setterSignatureContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitMethodSignature -> setterSignature");
        }
        if (operatorSignatureContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitMethodSignature -> operatorSignature");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (factoryConstructorSignatureContext != null) {
            final String factoryConstructorSignatureText = this.visit(factoryConstructorSignatureContext).read("$.text");
            text.append(factoryConstructorSignatureText);
        }
        if (functionSignatureContext != null) {
            final String functionSignatureText = this.visit(functionSignatureContext).read("$.text");
            text.append(functionSignatureText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitFactoryConstructorSignature(final Dart2Parser.FactoryConstructorSignatureContext context) {
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        text.append(factoryTerminal.getText());
        text.append(" ");
        // todo: visit constructorNameContext instead of getText().
        text.append(constructorNameContext.getText());
        final String formalParameterListText = this.visit(formalParameterListContext).read("$.text");
        text.append(formalParameterListText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitFunctionSignature(final Dart2Parser.FunctionSignatureContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFunctionSignature` text: {}", context.getText());
        }
        final Dart2Parser.TypeContext typeContext = context.type();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final Dart2Parser.FormalParameterPartContext formalParameterPartContext = context.formalParameterPart();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (typeContext != null) {
            // todo: visit typeContext instead of getText().
            text.append(typeContext.getText());
            text.append(" ");
        }
        // todo: visit identifierContext instead of getText().
        text.append(identifierContext.getText());
        final String formalParameterText = this.visit(formalParameterPartContext).read("$.text");
        text.append(formalParameterText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitFormalParameterPart(final Dart2Parser.FormalParameterPartContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFormalParameterPart` text: {}", context.getText());
        }
        final Dart2Parser.TypeParametersContext typeParametersContext = context.typeParameters();
        final Dart2Parser.FormalParameterListContext formalParameterListContext = context.formalParameterList();
        if (typeParametersContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFormalParameterPart -> typeParameters");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final String formalParameterListText = this.visit(formalParameterListContext).read("$.text");
        text.append(formalParameterListText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitFormalParameterList(final Dart2Parser.FormalParameterListContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitFormalParameterList` text: {}", context.getText());
        }
        final TerminalNode opTerminal = context.OP();
        final TerminalNode cpTerminal = context.CP();
        final Dart2Parser.NormalFormalParametersContext normalFormalParametersContext = context.normalFormalParameters();
        final TerminalNode cTerminal = context.C();
        final Dart2Parser.OptionalOrNamedFormalParametersContext optionalOrNamedFormalParametersContext = context.optionalOrNamedFormalParameters();
        if (cTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFormalParameterList -> cTerminal");
        }
        if (optionalOrNamedFormalParametersContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFormalParameterList -> optionalOrNamedFormalParameters");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        text.append(opTerminal.getText());
        if (normalFormalParametersContext != null) {
            final String normalFormalParametersText = this.visit(normalFormalParametersContext).read("$.text");
            text.append(normalFormalParametersText);
        }
        text.append(cpTerminal.getText());
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitNormalFormalParameters(final Dart2Parser.NormalFormalParametersContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitNormalFormalParameters` text: {}", context.getText());
        }
        final List<Dart2Parser.NormalFormalParameterContext> normalFormalParameterContexts = context.normalFormalParameter();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final StringJoiner joiner = new StringJoiner(", ");
        for (final Dart2Parser.NormalFormalParameterContext normalFormalParameterContext : normalFormalParameterContexts) {
            final String normalFormalParameterText = this.visit(normalFormalParameterContext).read("$.text");
            joiner.add(normalFormalParameterText);
        }
        text.append(joiner);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitNormalFormalParameter(final Dart2Parser.NormalFormalParameterContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitNormalFormalParameter` text: {}", context.getText());
        }
        final Dart2Parser.MetadataContext metadataContext = context.metadata();
        final Dart2Parser.NormalFormalParameterNoMetadataContext normalFormalParameterNoMetadataContext = context.normalFormalParameterNoMetadata();
        if (!metadataContext.getText().isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNormalFormalParameter -> metadata");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final String normalFormalParameterNoMetadataText = this.visit(normalFormalParameterNoMetadataContext).read("$.text");
        text.append(normalFormalParameterNoMetadataText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitNormalFormalParameterNoMetadata(final Dart2Parser.NormalFormalParameterNoMetadataContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitNormalFormalParameterNoMetadata` text: {}", context.getText());
        }
        final Dart2Parser.FunctionFormalParameterContext functionFormalParameterContext = context.functionFormalParameter();
        final Dart2Parser.FieldFormalParameterContext fieldFormalParameterContext = context.fieldFormalParameter();
        final Dart2Parser.SimpleFormalParameterContext simpleFormalParameterContext = context.simpleFormalParameter();
        if (functionFormalParameterContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNormalFormalParameterNoMetadata -> functionFormalParameter");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (simpleFormalParameterContext != null) {
            final String simpleFormalParameterText = this.visit(simpleFormalParameterContext).read("$.text");
            text.append(simpleFormalParameterText);
        }
        if (fieldFormalParameterContext != null) {
            // todo: visit fieldFormalParameterContext instead of getText().
            text.append(fieldFormalParameterContext.getText());
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitSimpleFormalParameter(final Dart2Parser.SimpleFormalParameterContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitSimpleFormalParameter` text: {}", context.getText());
        }
        final Dart2Parser.DeclaredIdentifierContext declaredIdentifierContext = context.declaredIdentifier();
        final TerminalNode covariantTerminal = context.COVARIANT_();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        if (covariantTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitSimpleFormalParameter -> covariant");
        }
        if (identifierContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitSimpleFormalParameter -> identifier");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (declaredIdentifierContext != null) {
            final String declaredIdentifierText = this.visit(declaredIdentifierContext).read("$.text");
            text.append(declaredIdentifierText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitDeclaredIdentifier(Dart2Parser.DeclaredIdentifierContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitDeclaredIdentifier` text: {}", context.getText());
        }
        final TerminalNode covariantTerminal = context.COVARIANT_();
        final Dart2Parser.FinalConstVarOrTypeContext finalConstVarOrTypeContext = context.finalConstVarOrType();
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        if (covariantTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaredIdentifier -> covariant");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final String finalConstVarOrTypeText = this.visit(finalConstVarOrTypeContext).read("$.text");
        text.append(finalConstVarOrTypeText);
        text.append(" ");
        final String identifierText = this.visit(identifierContext).read("$.text");
        text.append(identifierText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitFinalConstVarOrType(final Dart2Parser.FinalConstVarOrTypeContext context) {
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
        if (constTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFinalConstVarOrType -> const");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (finalTerminal != null) {
            text.append(finalTerminal.getText());
        }
        if (typeContext != null) {
            final String typeText = this.visit(typeContext).read("$.text");
            text.append(" ");
            text.append(typeText);
        }
        if (varOrTypeContext != null) {
            // todo: visit varOrTypeContext instead of getText().
            text.append(varOrTypeContext.getText());
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitFunctionBody(final Dart2Parser.FunctionBodyContext context) {
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
        final Dart2Parser.BlockContext blockContext = context.block();
        if (nativeTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionBody -> native");
        }
        if (stringLiteralContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionBody -> stringLiteral");
        }
        if (scTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionBody -> sc");
        }
        if (asyncTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionBody -> async");
        }
        if (egTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionBody -> eg");
        }
        if (exprContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionBody -> expr");
        }
        if (stTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionBody -> st");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (blockContext != null) {
            final String blockText = this.visit(blockContext).read("$.text");
            text.append(blockText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitBlock(final Dart2Parser.BlockContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitBlock` text: {}", context.getText());
        }
        final TerminalNode obcTerminal = context.OBC();
        final Dart2Parser.StatementsContext statementsContext = context.statements();
        final TerminalNode cbcTerminal = context.CBC();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        text.append(obcTerminal.getText());
        text.append("\n");
        this.currentIndentLevel++;
        text.append(this.indentUnit.repeat(this.currentIndentLevel));
        final String statementsText = this.visit(statementsContext).read("$.text");
        text.append(statementsText);
        text.append("\n");
        this.currentIndentLevel--;
        text.append(this.indentUnit.repeat(this.currentIndentLevel));
        text.append(cbcTerminal.getText());
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitStatements(final Dart2Parser.StatementsContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitStatements` text: {}", context.getText());
        }
        final List<Dart2Parser.StatementContext> statementContexts = context.statement();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (statementContexts.size() > 1) {
            final Dart2Parser.StatementContext firstStatementContext = statementContexts.get(0);
            final String firstStatementText = this.visit(firstStatementContext).read("$.text");
            text.append(firstStatementText);
            for (int index = 1; index < statementContexts.size(); index++) {
                text.append("\n");
                text.append(this.indentUnit.repeat(this.currentIndentLevel));
                final Dart2Parser.StatementContext statementContext = statementContexts.get(index);
                final String statementText = this.visit(statementContext).read("$.text");
                text.append(statementText);
            }
        } else {
            for (final Dart2Parser.StatementContext statementContext : statementContexts) {
                final String statementText = this.visit(statementContext).read("$.text");
                text.append(statementText);
            }
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitStatement(final Dart2Parser.StatementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitStatement` text: {}", context.getText());
        }
        final List<Dart2Parser.LabelContext> labelContexts = context.label();
        final Dart2Parser.NonLabelledStatementContext nonLabelledStatementContext = context.nonLabelledStatement();
        if (!labelContexts.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitStatement -> label");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final String nonLabelledStatementText = this.visit(nonLabelledStatementContext).read("$.text");
        text.append(nonLabelledStatementText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitNonLabelledStatement(final Dart2Parser.NonLabelledStatementContext context) {
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
        if (switchStatementContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNonLabelledStatement -> switchStatement");
        }
        if (rethrowStatementContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNonLabelledStatement -> rethrowStatement");
        }
        if (tryStatementContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitNonLabelledStatement -> tryStatement");
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (blockContext != null) {
            final String blockText = this.visit(blockContext).read("$.text");
            text.append(blockText);
        }
        if (returnStatementContext != null) {
            final String returnStatementText = this.visit(returnStatementContext).read("$.text");
            text.append(returnStatementText);
        }
        if (forStatementContext != null) {
            final String forStatementText = this.visit(forStatementContext).read("$.text");
            text.append(forStatementText);
        }
        if (expressionStatementContext != null) {
            final String expressionText = this.visit(expressionStatementContext).read("$.text");
            text.append(expressionText);
        }
        if (localVariableDeclarationContext != null) {
            final String localVariableDeclarationText = this.visit(localVariableDeclarationContext).read("$.text");
            text.append(localVariableDeclarationText);
        }
        if (ifStatementContext != null) {
            final String ifStatementText = this.visit(ifStatementContext).read("$.text");
            text.append(ifStatementText);
        }
        if (whileStatementContext != null) {
            final String whileStatementText = this.visit(whileStatementContext).read("$.text");
            text.append(whileStatementText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitWhileStatement(final Dart2Parser.WhileStatementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitWhileStatement` text: {}", context.getText());
        }
        final TerminalNode whileTerminal = context.WHILE_();
        final TerminalNode opTerminal = context.OP();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode cpTerminal = context.CP();
        final Dart2Parser.StatementContext statementContext = context.statement();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        text.append(whileTerminal.getText());
        text.append(" ");
        text.append(opTerminal.getText());
        final String exprText = this.visit(exprContext).read("$.text");
        text.append(exprText);
        text.append(cpTerminal.getText());
        text.append(" ");
        final String statementText = this.visit(statementContext).read("$.text");
        text.append(statementText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitExpressionStatement(final Dart2Parser.ExpressionStatementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitExpressionStatement` text: {}", context.getText());
        }
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode scTerminal = context.SC();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (exprContext != null) {
            final String exprText = this.visit(exprContext).read("$.text");
            text.append(exprText);
        }
        text.append(scTerminal.getText());
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitIfStatement(final Dart2Parser.IfStatementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitIfStatement` text: {}", context.getText());
        }
        final TerminalNode ifTerminal = context.IF_();
        final TerminalNode opTerminal = context.OP();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode cpTerminal = context.CP();
        final List<Dart2Parser.StatementContext> statementContexts = context.statement();
        final TerminalNode elseTerminal = context.ELSE_();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        text.append(ifTerminal.getText());
        text.append(" ");
        text.append(opTerminal.getText());
        final String exprText = this.visit(exprContext).read("$.text");
        text.append(exprText);
        text.append(cpTerminal.getText());
        text.append(" ");
        final Dart2Parser.StatementContext firstStatementContext = statementContexts.get(0);
        final String firstStatementText = this.visit(firstStatementContext).read("$.text");
        text.append(firstStatementText);
        if (elseTerminal != null) {
            text.append(" ");
            text.append(elseTerminal.getText());
            text.append(" ");
            final Dart2Parser.StatementContext secondStatementContext = statementContexts.get(1);
            final String secondStatementText = this.visit(secondStatementContext).read("$.text");
            text.append(secondStatementText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitForStatement(final Dart2Parser.ForStatementContext context) {
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        text.append(forTerminal.getText());
        text.append(" ");
        text.append(opTerminal.getText());
        final String forLoopPartsText = this.visit(forLoopPartsContext).read("$.text");
        text.append(forLoopPartsText);
        text.append(cpTerminal.getText());
        text.append(" ");
        final String statementText = this.visit(statementContext).read("$.text");
        text.append(statementText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitForLoopParts(final Dart2Parser.ForLoopPartsContext context) {
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
        if (metadataContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitForLoopParts -> metadata");
        }
        if (identifierContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitForLoopParts -> identifier");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (forInitializerStatementContext != null) {
            final String forInitializerStatementText = this.visit(forInitializerStatementContext).read("$.text");
            text.append(forInitializerStatementText);
            text.append(" ");
            if (exprContext != null) {
                final String exprText = this.visit(exprContext).read("$.text");
                text.append(exprText);
            }
            text.append(scTerminal.getText());
            if (expressionListContext != null) {
                text.append(" ");
                final String expressionListText = this.visit(expressionListContext).read("$.text");
                text.append(expressionListText);
            }
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitExpressionList(final Dart2Parser.ExpressionListContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitExpressionList` text: {}", context.getText());
        }
        final List<Dart2Parser.ExprContext> exprContexts = context.expr();
        final List<TerminalNode> cTerminals = context.C();
        if (!cTerminals.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitExpressionList -> c");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final Dart2Parser.ExprContext firstExprContext = exprContexts.get(0);
        final String firstExprText = this.visit(firstExprContext).read("$.text");
        text.append(firstExprText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitForInitializerStatement(final Dart2Parser.ForInitializerStatementContext context) {
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (localVariableDeclarationContext != null) {
            final String localVariableDeclarationText = this.visit(localVariableDeclarationContext).read("$.text");
            text.append(localVariableDeclarationText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitLocalVariableDeclaration(final Dart2Parser.LocalVariableDeclarationContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitLocalVariableDeclaration` text: {}", context.getText());
        }
        final Dart2Parser.MetadataContext metadataContext = context.metadata();
        final Dart2Parser.InitializedVariableDeclarationContext initializedVariableDeclarationContext = context.initializedVariableDeclaration();
        final TerminalNode scTerminal = context.SC();
        if (!metadataContext.getText().isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLocalVariableDeclaration -> metadata");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final String initializedVariableDeclarationText = this.visit(initializedVariableDeclarationContext).read("$.text");
        text.append(initializedVariableDeclarationText);
        text.append(scTerminal.getText());
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitInitializedVariableDeclaration(final Dart2Parser.InitializedVariableDeclarationContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitInitializedVariableDeclaration` text: {}", context.getText());
        }
        final Dart2Parser.DeclaredIdentifierContext declaredIdentifierContext = context.declaredIdentifier();
        final TerminalNode eqTerminal = context.EQ();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final List<TerminalNode> cTerminals = context.C();
        final List<Dart2Parser.InitializedIdentifierContext> initializedIdentifierContexts = context.initializedIdentifier();
        if (!cTerminals.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitInitializedVariableDeclaration -> c");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final String declaredIdentifierText = this.visit(declaredIdentifierContext).read("$.text");
        text.append(declaredIdentifierText);
        if (eqTerminal != null) {
            text.append(" ");
            text.append(eqTerminal.getText());
            text.append(" ");
            final String exprText = this.visit(exprContext).read("$.text");
            text.append(exprText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitReturnStatement(final Dart2Parser.ReturnStatementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitReturnStatement` text: {}", context.getText());
        }
        final TerminalNode returnTerminal = context.RETURN_();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final TerminalNode scTerminal = context.SC();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        text.append(returnTerminal.getText());
        text.append(" ");
        if (exprContext != null) {
            final String exprText = this.visit(exprContext).read("$.text");
            text.append(exprText);
        }
        text.append(scTerminal.getText());
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitDeclaration(final Dart2Parser.DeclarationContext context) {
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
        if (staticTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> staticTerminal");
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
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> constTerminal");
        }
        if (staticFinalDeclarationListContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> staticFinalDeclarationList");
        }
        if (lateTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> lateTerminal");
        }
        if (varOrTypeContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> varOrType");
        }
        if (covariantTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitDeclaration -> covariantTerminal");
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (abstractTerminal != null) {
            text.append(abstractTerminal.getText());
            text.append(" ");
        }
        if (externalTerminal != null) {
            text.append(externalTerminal.getText());
            text.append(" ");
        }
        if (finalTerminal != null) {
            text.append(finalTerminal.getText());
            text.append(" ");
        }
        if (typeContext != null) {
            final String typeText = this.visit(typeContext).read("$.text");
            text.append(typeText);
            text.append(" ");
        }
        if (constantConstructorSignatureContext != null) {
            final String constantConstructorSignatureText = this.visit(constantConstructorSignatureContext).read("$.text");
            text.append(constantConstructorSignatureText);
        }
        if (initializedIdentifierListContext != null) {
            final String initializedIdentifierListText = this.visit(initializedIdentifierListContext).read("$.text");
            text.append(initializedIdentifierListText);
        }
        if (functionSignatureContext != null) {
            final String functionSignatureText = this.visit(functionSignatureContext).read("$.text");
            text.append(functionSignatureText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitConstantConstructorSignature(final Dart2Parser.ConstantConstructorSignatureContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitConstantConstructorSignature` text: {}", context.getText());
        }
        final TerminalNode constTerminal = context.CONST_();
        final Dart2Parser.ConstructorNameContext constructorNameContext = context.constructorName();
        final Dart2Parser.FormalParameterListContext formalParameterListContext = context.formalParameterList();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        text.append(constTerminal.getText());
        text.append(" ");
        // todo: visit constructorNameContext instead of getText().
        text.append(constructorNameContext.getText());
        final String formalParameterListText = this.visit(formalParameterListContext).read("$.text");
        text.append(formalParameterListText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitType(final Dart2Parser.TypeContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitType` text: {}", context.getText());
        }
        final Dart2Parser.FunctionTypeContext functionTypeContext = context.functionType();
        final TerminalNode quTerminal =  context.QU();
        final Dart2Parser.TypeNotFunctionContext typeNotFunctionContext = context.typeNotFunction();
        if (functionTypeContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitType -> functionType");
        }
        if (quTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitType -> qu");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (typeNotFunctionContext != null) {
            final String typeNotFunctionText = this.visit(typeNotFunctionContext).read("$.text");
            text.append(typeNotFunctionText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitTypeNotFunction(final Dart2Parser.TypeNotFunctionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTypeNotFunction` text: {}", context.getText());
        }
        final TerminalNode voidTerminal = context.VOID_();
        final Dart2Parser.TypeNotVoidNotFunctionContext typeNotVoidNotFunctionContext = context.typeNotVoidNotFunction();
        if (voidTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTypeNotFunction -> void");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (typeNotVoidNotFunctionContext != null) {
            final String typeNotVoidNotFunctionText = this.visit(typeNotVoidNotFunctionContext).read("$.text");
            text.append(typeNotVoidNotFunctionText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitTypeNotVoidNotFunction(final Dart2Parser.TypeNotVoidNotFunctionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTypeNotVoidNotFunction` text: {}", context.getText());
        }
        final Dart2Parser.TypeNameContext typeNameContext = context.typeName();
        final Dart2Parser.TypeArgumentsContext typeArgumentsContext = context.typeArguments();
        final TerminalNode quTerminal = context.QU();
        final TerminalNode functionTerminal = context.FUNCTION_();
        if (quTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTypeNotVoidNotFunction -> qu");
        }
        if (functionTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTypeNotVoidNotFunction -> function");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (typeNameContext != null) {
            // todo: visit typeNameContext instead of getText().
            text.append(typeNameContext.getText());
        }
        if (typeArgumentsContext != null) {
            final String typeArgumentsText = this.visit(typeArgumentsContext).read("$.text");
            text.append(typeArgumentsText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitTypeArguments(final Dart2Parser.TypeArgumentsContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTypeArguments` text: {}", context.getText());
        }
        final TerminalNode ltTerminal = context.LT();
        final Dart2Parser.TypeListContext typeListContext = context.typeList();
        final TerminalNode gtTerminal = context.GT();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final String ltTerminalText = ltTerminal.getText();
        text.append(ltTerminalText);
        final String typeListText = this.visit(typeListContext).read("$.text");
        text.append(typeListText);
        final String gtTerminalText = gtTerminal.getText();
        text.append(gtTerminalText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitTypeList(final Dart2Parser.TypeListContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitTypeList` text: {}", context.getText());
        }
        final List<Dart2Parser.TypeContext> typeContexts = context.type();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final String firstTypeText = this.visit(typeContexts.get(0)).read("$.text");
        text.append(firstTypeText);
        for (int index = 1; index < typeContexts.size(); index++) {
            text.append(", ");
            final String typeText = this.visit(typeContexts.get(index)).read("$.text");
            text.append(typeText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitInitializedIdentifierList(final Dart2Parser.InitializedIdentifierListContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitInitializedIdentifierList` text: {}", context.getText());
        }
        final List<Dart2Parser.InitializedIdentifierContext> initializedIdentifierContexts = context.initializedIdentifier();
        final List<TerminalNode> cTerminals = context.C();
        if (!cTerminals.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitInitializedIdentifierList -> c");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.InitializedIdentifierContext initializedIdentifierContext : initializedIdentifierContexts) {
            final String initializedIdentifierText = this.visit(initializedIdentifierContext).read("$.text");
            text.append(initializedIdentifierText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitInitializedIdentifier(final Dart2Parser.InitializedIdentifierContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitInitializedIdentifier` text: {}", context.getText());
        }
        // It can be a variable name.
        final Dart2Parser.IdentifierContext identifierContext = context.identifier();
        final TerminalNode eqTerminal = context.EQ();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        // todo: visit identifierContext instead of getText().
        text.append(identifierContext.getText());
        if (eqTerminal != null) {
            text.append(" ");
            text.append(eqTerminal.getText());
            text.append(" ");
            final String exprText = this.visit(exprContext).read("$.text");
            text.append(exprText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitExpr(final Dart2Parser.ExprContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitExpr` text: {}", context.getText());
        }
        final Dart2Parser.AssignableExpressionContext assignableExpressionContext = context.assignableExpression();
        final Dart2Parser.AssignmentOperatorContext assignmentOperatorContext = context.assignmentOperator();
        final Dart2Parser.ExprContext exprContext = context.expr();
        final Dart2Parser.ConditionalExpressionContext conditionalExpressionContext = context.conditionalExpression();
        final Dart2Parser.CascadeContext cascadeContext = context.cascade();
        final Dart2Parser.ThrowExpressionContext throwExpressionContext = context.throwExpression();
        if (cascadeContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitExpr -> cascade");
        }
        if (throwExpressionContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitExpr -> throwExpression");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (assignableExpressionContext != null) {
            // todo: visit assignableExpressionContext instead of getText().
            text.append(assignableExpressionContext.getText());
            text.append(" ");
            // todo: visit assignmentOperatorContext instead of getText().
            text.append(assignmentOperatorContext.getText());
            text.append(" ");
            final String exprText = this.visit(exprContext).read("$.text");
            text.append(exprText);
        }
        if (conditionalExpressionContext != null) {
            final String conditionalExpressionText = this.visit(conditionalExpressionContext).read("$.text");
            text.append(conditionalExpressionText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitConditionalExpression(final Dart2Parser.ConditionalExpressionContext context) {
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final String ifNullExpressionText = this.visit(ifNullExpressionContext).read("$.text");
        text.append(ifNullExpressionText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitIfNullExpression(final Dart2Parser.IfNullExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitIfNullExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.LogicalOrExpressionContext> logicalOrExpressionContexts = context.logicalOrExpression();
        final List<TerminalNode> ququTerminals = context.QUQU();
        if (!ququTerminals.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIfNullExpression -> ququ");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.LogicalOrExpressionContext logicalOrExpression : logicalOrExpressionContexts) {
            final String logicalOrExpressionText = this.visit(logicalOrExpression).read("$.text");
            text.append(logicalOrExpressionText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitLogicalOrExpression(final Dart2Parser.LogicalOrExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitLogicalOrExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.LogicalAndExpressionContext> logicalAndExpressionContexts = context.logicalAndExpression();
        final List<TerminalNode> ppTerminals = context.PP();
        if (!ppTerminals.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLogicalOrExpression -> pp");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.LogicalAndExpressionContext logicalAndExpression : logicalAndExpressionContexts) {
            final String logicalAndExpressionText = this.visit(logicalAndExpression).read("$.text");
            text.append(logicalAndExpressionText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitLogicalAndExpression(final Dart2Parser.LogicalAndExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitLogicalAndExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.EqualityExpressionContext> equalityExpressionContexts = context.equalityExpression();
        final List<TerminalNode> aaTerminals = context.AA();
        if (!aaTerminals.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLogicalAndExpression -> aa");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.EqualityExpressionContext equalityExpression : equalityExpressionContexts) {
            final String equalityExpressionText = this.visit(equalityExpression).read("$.text");
            text.append(equalityExpressionText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitEqualityExpression(final Dart2Parser.EqualityExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitEqualityExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.RelationalExpressionContext> relationalExpressionContexts = context.relationalExpression();
        final Dart2Parser.EqualityOperatorContext equalityOperatorContext = context.equalityOperator();
        final TerminalNode superTerminal = context.SUPER_();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final Dart2Parser.RelationalExpressionContext firstRelationalExpressionContext = relationalExpressionContexts.get(0);
        final String firstRelationalExpressionText = this.visit(firstRelationalExpressionContext).read("$.text");
        if (superTerminal == null) {
            text.append(firstRelationalExpressionText);
            if (equalityOperatorContext != null) {
                text.append(" ");
                // todo: visit equalityOperatorContext instead of getText().
                text.append(equalityOperatorContext.getText());
                text.append(" ");
                final Dart2Parser.RelationalExpressionContext secondRelationalExpressionContext = relationalExpressionContexts.get(1);
                final String secondRelationalExpressionText = this.visit(secondRelationalExpressionContext).read("$.text");
                text.append(secondRelationalExpressionText);
            }
        } else {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitEqualityExpression -> super");
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitRelationalExpression(final Dart2Parser.RelationalExpressionContext context) {
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final Dart2Parser.BitwiseOrExpressionContext firstBitwiseOrExpression = bitwiseOrExpressionContexts.get(0);
        final String firstBitwiseOrExpressionText = this.visit(firstBitwiseOrExpression).read("$.text");
        if (bitwiseOrExpressionContexts.size() > 1) {
            text.append(firstBitwiseOrExpressionText);
            text.append(" ");
            // todo: visit relationalOperatorContext instead of getText().
            text.append(relationalOperatorContext.getText());
            text.append(" ");
            final Dart2Parser.BitwiseOrExpressionContext secondBitwiseOrExpression = bitwiseOrExpressionContexts.get(1);
            final String secondBitwiseOrExpressionText = this.visit(secondBitwiseOrExpression).read("$.text");
            text.append(secondBitwiseOrExpressionText);
        } else {
            if (superTerminal == null) {
                text.append(firstBitwiseOrExpressionText);
            } else {
                throw new UnsupportedOperationException("The following parsing path is not supported yet: visitRelationalExpression -> super");
            }
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitBitwiseOrExpression(final Dart2Parser.BitwiseOrExpressionContext context) {
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.BitwiseXorExpressionContext bitwiseXorExpression : bitwiseXorExpressionContexts) {
            final String bitwiseXorExpressionText = this.visit(bitwiseXorExpression).read("$.text");
            text.append(bitwiseXorExpressionText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitBitwiseXorExpression(final Dart2Parser.BitwiseXorExpressionContext context) {
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.BitwiseAndExpressionContext bitwiseAndExpression : bitwiseAndExpressionContexts) {
            final String bitwiseAndExpressionText = this.visit(bitwiseAndExpression).read("$.text");
            text.append(bitwiseAndExpressionText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitBitwiseAndExpression(final Dart2Parser.BitwiseAndExpressionContext context) {
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.ShiftExpressionContext shiftExpression : shiftExpressionContexts) {
            final String shiftExpressionText = this.visit(shiftExpression).read("$.text");
            text.append(shiftExpressionText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitShiftExpression(final Dart2Parser.ShiftExpressionContext context) {
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.AdditiveExpressionContext additiveExpression : additiveExpressionContexts) {
            final String additiveExpressionText = this.visit(additiveExpression).read("$.text");
            text.append(additiveExpressionText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitAdditiveExpression(final Dart2Parser.AdditiveExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitAdditiveExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.MultiplicativeExpressionContext> multiplicativeExpressionContexts = context.multiplicativeExpression();
        final List<Dart2Parser.AdditiveOperatorContext> additiveOperatorContexts = context.additiveOperator();
        final TerminalNode superTerminal = context.SUPER_();
        if (superTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitAdditiveExpression -> super");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        for (int index = 0; index < multiplicativeExpressionContexts.size(); index++) {
            final Dart2Parser.MultiplicativeExpressionContext multiplicativeExpression = multiplicativeExpressionContexts.get(index);
            final String multiplicativeExpressionText = this.visit(multiplicativeExpression).read("$.text");
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
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitMultiplicativeExpression(final Dart2Parser.MultiplicativeExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitMultiplicativeExpression` text: {}", context.getText());
        }
        final List<Dart2Parser.UnaryExpressionContext> unaryExpressionContexts = context.unaryExpression();
        final List<Dart2Parser.MultiplicativeOperatorContext> multiplicativeOperatorContexts = context.multiplicativeOperator();
        final TerminalNode superTerminal = context.SUPER_();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (superTerminal == null) {
            final Dart2Parser.UnaryExpressionContext firstUnaryExpression = unaryExpressionContexts.get(0);
            final String firstUnaryExpressionText = this.visit(firstUnaryExpression).read("$.text");
            text.append(firstUnaryExpressionText);
            for (int index = 0; index < multiplicativeOperatorContexts.size(); index++) {
                text.append(" ");
                final Dart2Parser.MultiplicativeOperatorContext multiplicativeOperatorContext = multiplicativeOperatorContexts.get(index);
                // todo: visit multiplicativeOperatorContext instead of getText().
                final String multiplicativeOperatorText = multiplicativeOperatorContext.getText();
                text.append(multiplicativeOperatorText);
                text.append(" ");
                final Dart2Parser.UnaryExpressionContext unaryExpressionContext = unaryExpressionContexts.get(index + 1);
                final String unaryExpressionText = this.visit(unaryExpressionContext).read("$.text");
                text.append(unaryExpressionText);
            }
        } else {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitMultiplicativeExpression -> super");
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitUnaryExpression(final Dart2Parser.UnaryExpressionContext context) {
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
        if (prefixOperatorContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitUnaryExpression -> prefixOperator");
        }
        if (unaryExpressionContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitUnaryExpression -> unaryExpression");
        }
        if (awaitExpressionContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitUnaryExpression -> awaitExpression");
        }
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final String postfixExpressionText = this.visit(postfixExpressionContext).read("$.text");
        text.append(postfixExpressionText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitPostfixExpression(final Dart2Parser.PostfixExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitPostfixExpression` text: {}", context.getText());
        }
        final Dart2Parser.AssignableExpressionContext assignableExpressionContext = context.assignableExpression();
        final Dart2Parser.PostfixOperatorContext postfixOperatorContext = context.postfixOperator();
        final Dart2Parser.PrimaryContext primaryContext = context.primary();
        final List<Dart2Parser.SelectorContext> selectorContexts = context.selector();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (assignableExpressionContext != null) {
            // todo: visit assignableExpressionContext instead of getText().
            text.append(assignableExpressionContext.getText());
            // todo: visit postfixOperatorContext instead of getText().
            text.append(postfixOperatorContext.getText());
        }
        if (primaryContext != null) {
            final String primaryText = this.visit(primaryContext).read("$.text");
            text.append(primaryText);
        }
        for (final Dart2Parser.SelectorContext selectorContext : selectorContexts) {
            final String selectorText = this.visit(selectorContext).read("$.text");
            text.append(selectorText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitSelector(final Dart2Parser.SelectorContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitSelector` text: {}", context.getText());
        }
        final TerminalNode notTerminal = context.NOT();
        final Dart2Parser.AssignableSelectorContext assignableSelectorContext = context.assignableSelector();
        final Dart2Parser.ArgumentPartContext argumentPartContext = context.argumentPart();
        if (notTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitSelector -> not");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (assignableSelectorContext != null) {
            // todo: visit assignableSelectorContext instead of getText().
            text.append(assignableSelectorContext.getText());
        }
        if (argumentPartContext != null) {
            final String argumentPartText = this.visit(argumentPartContext).read("$.text");
            text.append(argumentPartText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitArgumentPart(final Dart2Parser.ArgumentPartContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitArgumentPart` text: {}", context.getText());
        }
        final Dart2Parser.TypeArgumentsContext typeArgumentsContext = context.typeArguments();
        final Dart2Parser.ArgumentsContext argumentsContext = context.arguments();
        if (typeArgumentsContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitArgumentPart -> typeArguments");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (argumentsContext != null) {
            final String argumentsText = this.visit(argumentsContext).read("$.text");
            text.append(argumentsText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitPrimary(final Dart2Parser.PrimaryContext context) {
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
        if (thisExpressionContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitPrimary -> thisExpression");
        }
        if (superTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitPrimary -> super");
        }
        if (unconditionalAssignableSelectorContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitPrimary -> unconditionalAssignableSelector");
        }
        if (argumentPartContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitPrimary -> argumentPart");
        }
        if (functionExpressionContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitPrimary -> functionExpression");
        }
        if (constObjectExpressionContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitPrimary -> constObjectExpression");
        }
        if (constructorInvocationContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitPrimary -> constructorInvocation");
        }
        if (opTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitPrimary -> op");
        }
        if (exprContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitPrimary -> expr");
        }
        if (cpTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitPrimary -> cp");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (newExpressionContext != null) {
            final String newExpressionText = this.visit(newExpressionContext).read("$.text");
            text.append(newExpressionText);
        }
        if (literalContext != null) {
            final String literalText = this.visit(literalContext).read("$.text");
            text.append(literalText);
        }
        if (identifierContext != null) {
            final String identifierText = this.visit(identifierContext).read("$.text");
            text.append(identifierText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitIdentifier(final Dart2Parser.IdentifierContext context) {
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (identifierTerminal != null) {
            text.append(identifierTerminal.getText());
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitLiteral(final Dart2Parser.LiteralContext context) {
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
        if (nullLiteralContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLiteral -> nullLiteral");
        }
        if (booleanLiteralContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLiteral -> booleanLiteral");
        }
        if (symbolLiteralContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLiteral -> symbolLiteral");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (stringLiteralContext != null) {
            // todo: visit stringLiteralContext instead of getText().
            final String stringLiteralText = stringLiteralContext.getText();
            text.append(stringLiteralText);
        }
        if (setOrMapLiteralContext != null) {
            final String setOrMapLiteralText = this.visit(setOrMapLiteralContext).read("$.text");
            text.append(setOrMapLiteralText);
        }
        if (listLiteralContext != null) {
            final String listLiteralText = this.visit(listLiteralContext).read("$.text");
            text.append(listLiteralText);
        }
        if (numericLiteralContext != null) {
            // todo: visit numericLiteralContext instead of getText().
            text.append(numericLiteralContext.getText());
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitListLiteral(final Dart2Parser.ListLiteralContext context) {
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (typeArgumentsContext != null) {
            // todo: visit typeArgumentsContext instead of getText().
            final String typeArgumentsText = typeArgumentsContext.getText();
            text.append(typeArgumentsText);
            text.append(" ");
        }
        text.append(obTerminal.getText());
        if (elementsContext != null) {
            text.append("\n");
            this.currentIndentLevel++;
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            final String elementsText = this.visit(elementsContext).read("$.text");
            text.append(elementsText);
            text.append("\n");
            this.currentIndentLevel--;
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
        }
        text.append(cbTerminal.getText());
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitSetOrMapLiteral(final Dart2Parser.SetOrMapLiteralContext context) {
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (typeArgumentsContext != null) {
            final String typeArgumentsText = this.visit(typeArgumentsContext).read("$.text");
            text.append(typeArgumentsText);
            text.append(" ");
        }
        if (elementsContext == null) {
            text.append(obcTerminal.getText());
            text.append(cbcTerminal.getText());
        } else {
            text.append(obcTerminal.getText());
            text.append("\n");
            this.currentIndentLevel++;
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            final String elementsText = this.visit(elementsContext).read("$.text");
            text.append(elementsText);
            text.append("\n");
            this.currentIndentLevel--;
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            text.append(cbcTerminal.getText());
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitElements(final Dart2Parser.ElementsContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitElements` text: {}", context.getText());
        }
        final List<Dart2Parser.ElementContext> elementContexts = context.element();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final Dart2Parser.ElementContext firstElementContext = elementContexts.get(0);
        final String firstElementText = this.visit(firstElementContext).read("$.text");
        text.append(firstElementText);
        for (int index = 1; index < elementContexts.size(); index++) {
            text.append(",");
            text.append("\n");
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            final String elementText = this.visit(elementContexts.get(index)).read("$.text");
            text.append(elementText);
        }
        text.append(",");
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitElement(final Dart2Parser.ElementContext context) {
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
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        if (expressionElementContext != null) {
            // todo: visit expressionElementContext instead of getText().
            final String expressionText = expressionElementContext.getText();
            text.append(expressionText);
        }
        if (mapElementContext != null) {
            final String mapElementText = this.visit(mapElementContext).read("$.text");
            text.append(mapElementText);
        }
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitMapElement(final Dart2Parser.MapElementContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitMapElement` text: {}", context.getText());
        }
        final List<Dart2Parser.ExprContext> exprContexts = context.expr();
        final TerminalNode coTerminal = context.CO();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final String firstExprText = this.visit(exprContexts.get(0)).read("$.text");
        text.append(firstExprText);
        text.append(coTerminal.getText());
        text.append(" ");
        final String secondExprText = this.visit(exprContexts.get(1)).read("$.text");
        text.append(secondExprText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitNewExpression(final Dart2Parser.NewExpressionContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitNewExpression` text: {}", context.getText());
        }
        final TerminalNode newTerminal = context.NEW_();
        // It can be a class name.
        final Dart2Parser.ConstructorDesignationContext constructorDesignationContext = context.constructorDesignation();
        final Dart2Parser.ArgumentsContext argumentsContext = context.arguments();
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        text.append(newTerminal.getText());
        text.append(" ");
        // todo: visit constructorDesignationContext instead of getText().
        text.append(constructorDesignationContext.getText());
        final String argumentsText = this.visit(argumentsContext).read("$.text");
        text.append(argumentsText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitArguments(final Dart2Parser.ArgumentsContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitArguments` text: {}", context.getText());
        }
        final TerminalNode opTerminal = context.OP();
        final Dart2Parser.ArgumentListContext argumentListContext = context.argumentList();
        final TerminalNode cTerminal = context.C();
        final TerminalNode cpTerminal = context.CP();
        if (cTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitArguments -> c");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        text.append(opTerminal.getText());
        if (argumentListContext != null) {
            final String argumentsText = this.visit(argumentListContext).read("$.text");
            text.append(argumentsText);
        }
        text.append(cpTerminal.getText());
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitArgumentList(final Dart2Parser.ArgumentListContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitArgumentList` text: {}", context.getText());
        }
        final List<Dart2Parser.NamedArgumentContext> namedArgumentContexts = context.namedArgument();
        final List<TerminalNode> cTerminals = context.C();
        final Dart2Parser.ExpressionListContext expressionListContext = context.expressionList();
        if (!namedArgumentContexts.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitArgumentList -> namedArgument");
        }
        final DocumentContext json = JsonPath.parse("{}");
        final StringBuilder text = new StringBuilder();
        final String expressionListText = this.visit(expressionListContext).read("$.text");
        text.append(expressionListText);
        json.put("$", "text", text.toString());
        return json;
    }

    @Override
    public DocumentContext visitChildren(final RuleNode node) {
        throw new UnsupportedOperationException(
            String.format(
                "The following rule is not implemented yet: %s text: %s",
                node.getClass(),
                node.getText()
            )
        );
    }

}
