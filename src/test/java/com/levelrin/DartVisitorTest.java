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

    @Test
    void shouldFormatAll() throws URISyntaxException, IOException {
        final Path beforePath = Paths.get(ClassLoader.getSystemResource("before.dart").toURI());
        final String originalText = Files.readString(beforePath, StandardCharsets.UTF_8);
        final CharStream charStream = CharStreams.fromString(originalText);
        final Dart2Lexer lexer = new Dart2Lexer(charStream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final Dart2Parser parser = new Dart2Parser(tokens);
        final ParseTree tree = parser.compilationUnit();
        final DartVisitor visitor = new DartVisitor();
        final String result = visitor.visit(tree).read("$.text");
        final Path afterPath = Paths.get(ClassLoader.getSystemResource("after.dart").toURI());
        final String expectedText = Files.readString(afterPath, StandardCharsets.UTF_8);
        MatcherAssert.assertThat(result, Matchers.equalTo(expectedText));
    }

}
