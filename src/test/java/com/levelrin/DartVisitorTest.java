package com.levelrin;

import com.levelrin.antlr.generated.Dart2Lexer;
import com.levelrin.antlr.generated.Dart2Parser;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

final class DartVisitorTest {

    /**
     * Assert that the formatter formats the code written in the `before` file that matches with the code written in the `after` file.
     * @param before The file name that has code before formatting.
     * @param after The file name that has code after formatting.
     */
    void compare(final String before, final String after) {
        try {
            final Path beforePath = Paths.get(ClassLoader.getSystemResource(before).toURI());
            final String originalText = Files.readString(beforePath, StandardCharsets.UTF_8);
            final CharStream charStream = CharStreams.fromString(originalText);
            final Dart2Lexer lexer = new Dart2Lexer(charStream);
            final CommonTokenStream tokens = new CommonTokenStream(lexer);
            final Dart2Parser parser = new Dart2Parser(tokens);
            final ParseTree tree = parser.compilationUnit();
            final DartVisitor visitor = new DartVisitor(tokens);
            final String result = visitor.visit(tree);
            final Path afterPath = Paths.get(ClassLoader.getSystemResource(after).toURI());
            final String expectedText = Files.readString(afterPath, StandardCharsets.UTF_8);
            MatcherAssert.assertThat(result, Matchers.equalTo(expectedText));
        } catch (final URISyntaxException | IOException ex) {
            throw new IllegalStateException(
                String.format(
                    "Failed to read files. before: %s, after: %s",
                    before,
                    after
                ),
                ex
            );
        }
    }

    @Test
    void shouldFormatExport() {
        this.compare("export-before.dart", "export-after.dart");
    }

    @Test
    void shouldFormatTopLevelDeclaration() {
        this.compare("top-level-declaration-before.dart", "top-level-declaration-after.dart");
    }

    @Test
    void shouldFormatAssignableSelector() {
        this.compare("assignable-selector-before.dart", "assignable-selector-after.dart");
    }

    @Test
    void shouldFormatConstructorDesignation() {
        this.compare("constructor-designation-before.dart", "constructor-designation-after.dart");
    }

    @Test
    void shouldFormatBitwiseAndExpression() {
        this.compare("bitwise-and-expression-before.dart", "bitwise-and-expression-after.dart");
    }

    @Test
    void shouldFormatAssert() {
        this.compare("assert-before.dart", "assert-after.dart");
    }

    @Test
    void shouldFormatLabel() {
        this.compare("label-before.dart", "label-after.dart");
    }

    @Test
    void shouldFormatCompoundAssignmentOperator() {
        this.compare("compound-assignment-operator-before.dart", "compound-assignment-operator-after.dart");
    }

    @Test
    void shouldFormatMetadata() {
        this.compare("metadata-before.dart", "metadata-after.dart");
    }

    @Test
    void shouldFormatInitializedIdentifierList() {
        this.compare("initialized-identifier-list-before.dart", "initialized-identifier-list-after.dart");
    }

    @Test
    void shouldFormatCovariant() {
        this.compare("covariant-before.dart", "covariant-after.dart");
    }

    @Test
    void shouldFormatExtension() {
        this.compare("extension-before.dart", "extension-after.dart");
    }

    @Test
    void shouldFormatEnum() {
        this.compare("enum-before.dart", "enum-after.dart");
    }

    @Test
    void shouldFormatListLiteral() {
        this.compare("list-literal-before.dart", "list-literal-after.dart");
    }

    @Test
    void shouldFormatElements() {
        this.compare("elements-before.dart", "elements-after.dart");
    }

    @Test
    void shouldFormatFinalConstVarOrType() {
        this.compare("final-const-var-or-type-before.dart", "final-const-var-or-type-after.dart");
    }

    @Test
    void shouldFormatOptionalParameterTypes() {
        this.compare("optional-parameter-types-before.dart", "optional-parameter-types-after.dart");
    }

    @Test
    void shouldFormatParameterTypeList() {
        this.compare("parameter-type-list-before.dart", "parameter-type-list-after.dart");
    }

    @Test
    void shouldFormatAssignableSelectorPart() {
        this.compare("assignable-selector-part-before.dart", "assignable-selector-part-after.dart");
    }

    @Test
    void shouldFormatTypeCast() {
        this.compare("type-cast-before.dart", "type-cast-after.dart");
    }

    @Test
    void shouldFormatLibraryAndParts() {
        this.compare("library-before.dart", "library-after.dart");
        this.compare("part-1-before.dart", "part-1-after.dart");
        this.compare("part-2-before.dart", "part-2-after.dart");
    }

    @Test
    void shouldFormatStatic() {
        this.compare("static-before.dart", "static-after.dart");
    }

    @Test
    void shouldFormatMethodChain() {
        this.compare("method-chain-before.dart", "method-chain-after.dart");
    }

    @Test
    void shouldFormatException() {
        this.compare("exception-before.dart", "exception-after.dart");
    }

    @Test
    void shouldFormatInterface() {
        this.compare("interface-before.dart", "interface-after.dart");
    }

    @Test
    void shouldFormatSuperParam() {
        this.compare("super-param-before.dart", "super-param-after.dart");
    }

    @Test
    void shouldFormatAsync() {
        this.compare("async-before.dart", "async-after.dart");
    }

    @Test
    void shouldFormatNullable() {
        this.compare("nullable-before.dart", "nullable-after.dart");
    }

    @Test
    void shouldFormatLambda() {
        this.compare("lambda-before.dart", "lambda-after.dart");
    }

    @Test
    void shouldFormatMixin() {
        this.compare("mixin-before.dart", "mixin-after.dart");
    }

    @Test
    void shouldFormatGenerics() {
        this.compare("generic-before.dart", "generic-after.dart");
    }

    @Test
    void shouldFormatSwitch() {
        this.compare("switch-before.dart", "switch-after.dart");
    }

    @Test
    void shouldFormatNamedParameters() {
        this.compare("named-param-before.dart", "named-param-after.dart");
    }

    @Test
    void shouldFormatParentAndChildClasses() {
        this.compare("inheritance-before.dart", "inheritance-after.dart");
    }

    @Test
    void shouldFormatComposition() {
        this.compare("composition-before.dart", "composition-after.dart");
    }

    @Test
    void shouldFormatFactoryConstructor() {
        this.compare("factory-constructor-before.dart", "factory-constructor-after.dart");
    }

    @Test
    void shouldFormatConstructor() {
        this.compare("constructor-before.dart", "constructor-after.dart");
    }

    @Test
    void shouldFormatWhileStatement() {
        this.compare("while-before.dart", "while-after.dart");
    }

    @Test
    void shouldFormatIfStatement() {
        this.compare("if-before.dart", "if-after.dart");
    }

    @Test
    void shouldFormatForLoop() {
        this.compare("for-before.dart", "for-after.dart");
    }

    @Test
    void shouldFormatMethod() {
        this.compare("method-before.dart", "method-after.dart");
    }

    @Test
    void shouldFormatList() {
        this.compare("list-before.dart", "list-after.dart");
    }

    @Test
    void shouldFormatMap() {
        this.compare("map-before.dart", "map-after.dart");
    }

    @Test
    void shouldFormatField() {
        this.compare("field-before.dart", "field-after.dart");
    }

    @Test
    void shouldFormatImport() {
        this.compare("import-before.dart", "import-after.dart");
    }

}
