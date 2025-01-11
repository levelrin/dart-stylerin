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
        if (mixinDeclarationContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTopLevelDeclaration -> mixinDeclaration");
        }
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
        if (asTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitImportSpecification -> as");
        }
        if (identifierContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitImportSpecification -> identifier");
        }
        if (!combinatorContexts.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitImportSpecification -> combinator");
        }
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(importTerminal));
        text.append(" ");
        // todo: visit configurableUriContext instead of getText().
        text.append(configurableUriContext.getText());
        text.append(this.visit(scTerminal));
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
        if (typeParametersContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitClassDeclaration -> typeParameters");
        }
        if (interfacesContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitClassDeclaration -> interfaces");
        }
        final StringBuilder text = new StringBuilder();
        if (abstractTerminal != null) {
            text.append(this.visit(abstractTerminal));
        }
        text.append(this.visit(classTerminal));
        text.append(" ");
        // todo: visit typeIdentifierContext instead of getText().
        text.append(typeIdentifierContext.getText());
        text.append(" ");
        if (superclassContext != null) {
            final String superclassText = this.visit(superclassContext);
            text.append(superclassText);
            text.append(" ");
        }
        text.append(this.visit(obcTerminal));
        text.append("\n\n");
        this.currentIndentLevel++;
        for (final Dart2Parser.MetadataContext metadataContext : metadataContexts) {
            if (!metadataContext.getText().isEmpty()) {
                final String metadataText = this.visit(metadataContext);
                text.append(this.indentUnit.repeat(this.currentIndentLevel));
                text.append(metadataText);
                text.append("\n");
            }
        }
        for (final Dart2Parser.ClassMemberDeclarationContext classMemberDeclarationContext : classMemberDeclarationContexts) {
            final String classMemberDeclarationText = this.visit(classMemberDeclarationContext);
            text.append(this.indentUnit.repeat(this.currentIndentLevel));
            text.append(classMemberDeclarationText);
            text.append("\n\n");
        }
        this.currentIndentLevel--;
        text.append(this.visit(cbcTerminal));
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
            // todo: visit metadatumContext instead of getText().
            text.append(metadatumContext.getText());
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
        if (mixinsContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitSuperclass -> mixins");
        }
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(extendsTerminal));
        text.append(" ");
        // todo: visit typeNotVoidContext instead of getText().
        text.append(typeNotVoidContext.getText());
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
        final StringBuilder text = new StringBuilder();
        if (factoryConstructorSignatureContext != null) {
            final String factoryConstructorSignatureText = this.visit(factoryConstructorSignatureContext);
            text.append(factoryConstructorSignatureText);
        }
        if (functionSignatureContext != null) {
            final String functionSignatureText = this.visit(functionSignatureContext);
            text.append(functionSignatureText);
        }
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
        // todo: visit constructorNameContext instead of getText().
        text.append(constructorNameContext.getText());
        final String formalParameterListText = this.visit(formalParameterListContext);
        text.append(formalParameterListText);
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
            // todo: visit typeContext instead of getText().
            text.append(typeContext.getText());
            text.append(" ");
        }
        // todo: visit identifierContext instead of getText().
        text.append(identifierContext.getText());
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
        if (typeParametersContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFormalParameterPart -> typeParameters");
        }
        final StringBuilder text = new StringBuilder();
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
        }
        if (optionalOrNamedFormalParametersContext != null) {
            text.append(" ");
            // todo: visit optionalOrNamedFormalParametersContext instead of getText().
            text.append(optionalOrNamedFormalParametersContext.getText());
        }
        text.append(this.visit(cpTerminal));
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
            // todo: visit fieldFormalParameterContext instead of getText().
            text.append(fieldFormalParameterContext.getText());
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
        if (identifierContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitSimpleFormalParameter -> identifier");
        }
        final StringBuilder text = new StringBuilder();
        if (declaredIdentifierContext != null) {
            final String declaredIdentifierText = this.visit(declaredIdentifierContext);
            text.append(declaredIdentifierText);
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
        if (constTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFinalConstVarOrType -> const");
        }
        final StringBuilder text = new StringBuilder();
        if (finalTerminal != null) {
            text.append(this.visit(finalTerminal));
        }
        if (typeContext != null) {
            final String typeText = this.visit(typeContext);
            text.append(" ");
            text.append(typeText);
        }
        if (varOrTypeContext != null) {
            // todo: visit varOrTypeContext instead of getText().
            text.append(varOrTypeContext.getText());
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
        final StringBuilder text = new StringBuilder();
        if (blockContext != null) {
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
        if (statementContexts.size() > 1) {
            final Dart2Parser.StatementContext firstStatementContext = statementContexts.get(0);
            final String firstStatementText = this.visit(firstStatementContext);
            text.append(firstStatementText);
            for (int index = 1; index < statementContexts.size(); index++) {
                text.append("\n");
                text.append(this.indentUnit.repeat(this.currentIndentLevel));
                final Dart2Parser.StatementContext statementContext = statementContexts.get(index);
                final String statementText = this.visit(statementContext);
                text.append(statementText);
            }
        } else {
            for (final Dart2Parser.StatementContext statementContext : statementContexts) {
                final String statementText = this.visit(statementContext);
                text.append(statementText);
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
        final StringBuilder text = new StringBuilder();
        if (blockContext != null) {
            final String blockText = this.visit(blockContext);
            text.append(blockText);
        }
        if (returnStatementContext != null) {
            final String returnStatementText = this.visit(returnStatementContext);
            text.append(returnStatementText);
        }
        if (forStatementContext != null) {
            final String forStatementText = this.visit(forStatementContext);
            text.append(forStatementText);
        }
        if (expressionStatementContext != null) {
            final String expressionText = this.visit(expressionStatementContext);
            text.append(expressionText);
        }
        if (localVariableDeclarationContext != null) {
            final String localVariableDeclarationText = this.visit(localVariableDeclarationContext);
            text.append(localVariableDeclarationText);
        }
        if (ifStatementContext != null) {
            final String ifStatementText = this.visit(ifStatementContext);
            text.append(ifStatementText);
        }
        if (whileStatementContext != null) {
            final String whileStatementText = this.visit(whileStatementContext);
            text.append(whileStatementText);
        }
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
        if (metadataContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitForLoopParts -> metadata");
        }
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
        final StringBuilder text = new StringBuilder();
        if (abstractTerminal != null) {
            text.append(this.visit(abstractTerminal));
            text.append(" ");
        }
        if (externalTerminal != null) {
            text.append(this.visit(externalTerminal));
            text.append(" ");
        }
        if (finalTerminal != null) {
            text.append(this.visit(finalTerminal));
            text.append(" ");
        }
        if (typeContext != null) {
            final String typeText = this.visit(typeContext);
            text.append(typeText);
            text.append(" ");
        }
        if (constantConstructorSignatureContext != null) {
            final String constantConstructorSignatureText = this.visit(constantConstructorSignatureContext);
            text.append(constantConstructorSignatureText);
        }
        if (initializedIdentifierListContext != null) {
            final String initializedIdentifierListText = this.visit(initializedIdentifierListContext);
            text.append(initializedIdentifierListText);
        }
        if (functionSignatureContext != null) {
            final String functionSignatureText = this.visit(functionSignatureContext);
            text.append(functionSignatureText);
        }
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
        // todo: visit constructorNameContext instead of getText().
        text.append(constructorNameContext.getText());
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
        if (quTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitType -> qu");
        }
        final StringBuilder text = new StringBuilder();
        if (functionTypeContext != null) {
            final String functionTypeText = this.visit(functionTypeContext);
            text.append(functionTypeText);
        }
        if (typeNotFunctionContext != null) {
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
        if (voidTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitTypeNotFunction -> void");
        }
        final StringBuilder text = new StringBuilder();
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
            // todo: visit typeNameContext instead of getText().
            text.append(typeNameContext.getText());
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
        // todo: visit identifierContext instead of getText().
        text.append(identifierContext.getText());
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
        if (cascadeContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitExpr -> cascade");
        }
        if (throwExpressionContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitExpr -> throwExpression");
        }
        final StringBuilder text = new StringBuilder();
        if (assignableExpressionContext != null) {
            // todo: visit assignableExpressionContext instead of getText().
            text.append(assignableExpressionContext.getText());
            text.append(" ");
            // todo: visit assignmentOperatorContext instead of getText().
            text.append(assignmentOperatorContext.getText());
            text.append(" ");
            final String exprText = this.visit(exprContext);
            text.append(exprText);
        }
        if (conditionalExpressionContext != null) {
            final String conditionalExpressionText = this.visit(conditionalExpressionContext);
            text.append(conditionalExpressionText);
        }
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
        if (!ququTerminals.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitIfNullExpression -> ququ");
        }
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.LogicalOrExpressionContext logicalOrExpression : logicalOrExpressionContexts) {
            final String logicalOrExpressionText = this.visit(logicalOrExpression);
            text.append(logicalOrExpressionText);
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
        if (!ppTerminals.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLogicalOrExpression -> pp");
        }
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.LogicalAndExpressionContext logicalAndExpression : logicalAndExpressionContexts) {
            final String logicalAndExpressionText = this.visit(logicalAndExpression);
            text.append(logicalAndExpressionText);
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
        if (!aaTerminals.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLogicalAndExpression -> aa");
        }
        final StringBuilder text = new StringBuilder();
        for (final Dart2Parser.EqualityExpressionContext equalityExpression : equalityExpressionContexts) {
            final String equalityExpressionText = this.visit(equalityExpression);
            text.append(equalityExpressionText);
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
                // todo: visit equalityOperatorContext instead of getText().
                text.append(equalityOperatorContext.getText());
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
        final StringBuilder text = new StringBuilder();
        if (postfixExpressionContext != null) {
            final String postfixExpressionText = this.visit(postfixExpressionContext);
            text.append(postfixExpressionText);
        }
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
            // todo: visit assignableExpressionContext instead of getText().
            text.append(assignableExpressionContext.getText());
            // todo: visit postfixOperatorContext instead of getText().
            text.append(postfixOperatorContext.getText());
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
    public String visitSelector(final Dart2Parser.SelectorContext context) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter `visitSelector` text: {}", context.getText());
        }
        final TerminalNode notTerminal = context.NOT();
        final Dart2Parser.AssignableSelectorContext assignableSelectorContext = context.assignableSelector();
        final Dart2Parser.ArgumentPartContext argumentPartContext = context.argumentPart();
        if (notTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitSelector -> not");
        }
        final StringBuilder text = new StringBuilder();
        if (assignableSelectorContext != null) {
            // todo: visit assignableSelectorContext instead of getText().
            text.append(assignableSelectorContext.getText());
        }
        if (argumentPartContext != null) {
            final String argumentPartText = this.visit(argumentPartContext);
            text.append(argumentPartText);
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
        if (typeArgumentsContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitArgumentPart -> typeArguments");
        }
        final StringBuilder text = new StringBuilder();
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
        if (superTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitPrimary -> super");
        }
        if (unconditionalAssignableSelectorContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitPrimary -> unconditionalAssignableSelector");
        }
        if (argumentPartContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitPrimary -> argumentPart");
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
        final StringBuilder text = new StringBuilder();
        if (thisExpressionContext != null) {
            // todo: visit thisExpressionContext instead of getText().
            text.append(thisExpressionContext.getText());
        }
        if (functionExpressionContext != null) {
            text.append(this.visit(functionExpressionContext));
        }
        if (newExpressionContext != null) {
            final String newExpressionText = this.visit(newExpressionContext);
            text.append(newExpressionText);
        }
        if (literalContext != null) {
            final String literalText = this.visit(literalContext);
            text.append(literalText);
        }
        if (identifierContext != null) {
            final String identifierText = this.visit(identifierContext);
            text.append(identifierText);
        }
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
        if (asyncTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionExpressionBody -> async");
        }
        if (egTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionExpressionBody -> eg");
        }
        if (exprContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionExpressionBody -> expr");
        }
        if (stTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionExpressionBody -> st");
        }
        if (syncTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitFunctionExpressionBody -> sync");
        }
        final StringBuilder text = new StringBuilder();
        if (blockContext != null) {
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
        if (booleanLiteralContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLiteral -> booleanLiteral");
        }
        if (symbolLiteralContext != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitLiteral -> symbolLiteral");
        }
        final StringBuilder text = new StringBuilder();
        if (nullLiteralContext != null) {
            // todo: visit nullLiteralContext instead of getText().
            text.append(nullLiteralContext.getText());
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
            // todo: visit typeArgumentsContext instead of getText().
            final String typeArgumentsText = typeArgumentsContext.getText();
            text.append(typeArgumentsText);
            text.append(" ");
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
            text.append(" ");
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
            // todo: visit expressionElementContext instead of getText().
            final String expressionText = expressionElementContext.getText();
            text.append(expressionText);
        }
        if (mapElementContext != null) {
            final String mapElementText = this.visit(mapElementContext);
            text.append(mapElementText);
        }
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
        if (cTerminal != null) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitArguments -> c");
        }
        final StringBuilder text = new StringBuilder();
        text.append(this.visit(opTerminal));
        if (argumentListContext != null) {
            final int visitArgumentsCountBefore = this.ruleVisitCounts.getOrDefault(Dart2Parser.ArgumentsContext.class.getSimpleName(), 0);
            // We need to increment the indentation up front so that the child will have the correct indentation level.
            // If the incrementation turns out to be wrong, we can remove it later.
            this.currentIndentLevel++;
            final String argumentsText = this.visit(argumentListContext);
            final int visitArgumentsCountAfter = this.ruleVisitCounts.getOrDefault(Dart2Parser.ArgumentsContext.class.getSimpleName(), 0);
            // We assume the following condition means nested object initialization. Ex: User(User('Rin'));
            if (visitArgumentsCountBefore < visitArgumentsCountAfter) {
                text.append("\n");
                text.append(this.indentUnit.repeat(this.currentIndentLevel));
                text.append(argumentsText);
                text.append("\n");
                this.currentIndentLevel--;
                text.append(this.indentUnit.repeat(this.currentIndentLevel));
            } else {
                this.currentIndentLevel--;
                text.append(argumentsText);
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
        if (!namedArgumentContexts.isEmpty()) {
            throw new UnsupportedOperationException("The following parsing path is not supported yet: visitArgumentList -> namedArgument");
        }
        final StringBuilder text = new StringBuilder();
        final String expressionListText = this.visit(expressionListContext);
        text.append(expressionListText);
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
