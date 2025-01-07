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
            final DartVisitor visitor = new DartVisitor();
            final String result = visitor.visit(tree).read("$.text");
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
