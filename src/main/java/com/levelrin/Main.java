package com.levelrin;

import com.levelrin.antlr.generated.Dart2Lexer;
import com.levelrin.antlr.generated.Dart2Parser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Entrypoint of the formatter via command line.
 */
public final class Main {

    /**
     * As is.
     *
     * @param args Command line arguments.
     */
    public static void main(final String... args) throws ParseException, IOException {
        final Options options = new Options();
        options.addOption("h", "help", false, "Show help messages.")
            .addOption("q", "quiet", false, "Do not print debug logs.")
            .addOption("r", "recursive", true, "Format files in the directory recursively.");
        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd = parser.parse(options, args);
        if (cmd.hasOption('h')) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar dart-stylerin-{version}.jar [options]", options);
            return;
        }
        if (cmd.hasOption('q')) {
            System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "INFO");
        }
        if (cmd.hasOption('r')) {
            formatDirectory(cmd.getOptionValue('r'));
        }
        for (final String arg : cmd.getArgs()) {
            formatDirectory(arg);
        }
    }

    /**
     * As is.
     *
     * @param rawPath File path.
     * @throws IOException As is.
     */
    private static void formatFile(final String rawPath) throws IOException {
        final Path path = Paths.get(rawPath);
        final String content = Files.readString(path, StandardCharsets.UTF_8);
        final CharStream charStream = CharStreams.fromString(content);
        final Dart2Lexer lexer = new Dart2Lexer(charStream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final Dart2Parser parser = new Dart2Parser(tokens);
        final ParseTree tree = parser.compilationUnit();
        final DartVisitor visitor = new DartVisitor(tokens);
        final String result = visitor.visit(tree);
        Files.writeString(path, result);
    }

    /**
     * As is.
     *
     * @param rawPath Directory path.
     * @throws IOException As is.
     */
    private static void formatDirectory(final String rawPath) throws IOException {
        try (
            Stream<Path> stream = Files.walk(Paths.get(rawPath))
                .filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".dart"))
        ) {
            stream.forEach(path -> {
                try {
                    formatFile(path.toString());
                } catch (final IOException ex) {
                    throw new IllegalStateException("Failed to read the file. path: " + path, ex);
                }
            });
        }
    }

}
