package agh.cs.actparser;

import agh.cs.actparser.argparser.ArgumentParser;
import agh.cs.actparser.argparser.OptionParsers;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Article;
import agh.cs.actparser.elements.Document;
import agh.cs.actparser.printers.IPrinter;
import agh.cs.actparser.printers.PlaintextPrinter;
import agh.cs.actparser.printers.TableOfContentPrinter;
import agh.cs.actparser.parsers.DocumentParser;
import agh.cs.actparser.parsers.ElementParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ActParserApplication {
    private static ArgumentParser configureArgumentParser() {
        ArgumentParser argparser = new ArgumentParser();
        argparser.addOption(
                new ArgumentParser.Option("file", "f",
                        "File containing document to parse. Format: Text",
                        OptionParsers.getTextParser())
        );
        argparser.addOption(
                new ArgumentParser.Option("toc", "t",
                        "Whether to display table of contents. Format: Flag",
                        null)
        );
        argparser.addOption(
                new ArgumentParser.Option("articles", "a",
                        "One or more articles (artykuły) to display. " +
                                "Format: Range",
                        OptionParsers.getIdentifierRangeParser(ElementKind
                                .Article))
        );
        argparser.addOption(
                new ArgumentParser.Option("sections", "s",
                        "One or more sections (działy) to display. Format: " +
                                "Range",
                        OptionParsers.getIdentifierRangeParser(ElementKind
                                .Section))
        );
        argparser.addOption(
                new ArgumentParser.Option("chapters", "c",
                        "Chapters (rozdziały) to dispay. Format: Range",
                        OptionParsers.getIdentifierRangeParser(ElementKind
                                .Chapter))
        );
        argparser.addOption(
                new ArgumentParser.Option("paragraph", "u",
                        "Paragraph (ustęp) to display. Format: Identifier",
                        OptionParsers.getIdentifierParser(ElementKind
                                .Paragraph))
        );
        argparser.addOption(
                new ArgumentParser.Option("point", "p",
                        "Point (punkt) to display. Format: Identifier",
                        OptionParsers.getIdentifierParser(ElementKind.Point))
        );
        argparser.addOption(
                new ArgumentParser.Option("letter", "l",
                        "Letter (litera) to display. Format: Identifier",
                        OptionParsers.getIdentifierParser(ElementKind.Letter))
        );
        argparser.addOption(
                new ArgumentParser.Option("help", "h",
                        "Display this help message. Format: Flag",
                        null)
        );
        return argparser;
    }

    public static void main(String[] args) throws IOException {
        ArgumentParser argparser = configureArgumentParser();

        try {
            argparser.parse(args);
        } catch (IllegalArgumentException ex) {
            System.out.println("Arguments could not be parsed: " + ex
                    .getMessage() + "\nAvailable arguments:\n"
                    + argparser.getArgsHelp());
            return;
        }

        if (argparser.getResult("help")) {
            System.out.println(argparser.getArgsHelp());
            System.exit(0);
        }


        String filepath = argparser.getResult("file");

        List<String> inputLines = null;

        if (!argparser.isSet("file")) {
            System.out.println("File to read must be provided!");
            System.out.println(argparser.getArgsHelp());
            System.exit(1);
        }

        try {
            Path path = Paths.get(filepath);
            inputLines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("File \"" + filepath + "\" could not be read!");
            System.exit(1);
        }

        inputLines = new Preprocessor().process(inputLines);

        IPrinter printer = argparser.getResult("toc") ?
                new TableOfContentPrinter()
                : new PlaintextPrinter();


        ElementRegistry chaptersRegistry =
                new ElementRegistry(ElementKind.Chapter);
        ElementRegistry sectionsRegistry =
                new ElementRegistry(ElementKind.Section);
        ElementRegistry articleRegistry =
                new ElementRegistry(ElementKind.Article);

        ElementParser rootparser = new DocumentParser(
                inputLines,
                Arrays.asList(
                        chaptersRegistry, sectionsRegistry, articleRegistry));

        // Parse the document

        Document root = null;
        try {
            root = (Document) rootparser.makeElement();
        } catch (IllegalArgumentException e) {
            System.out.println("Error occured during parsing of the document:" +
                    e.getMessage());
            System.exit(1);
        }

        List<AbstractElement> elementsToDisplay;

        try {
            if (argparser.isSet("chapters")) {
                elementsToDisplay = chaptersRegistry.getRange(argparser
                        .getResult("chapters"));
            } else if (argparser.isSet("sections")) {
                elementsToDisplay = sectionsRegistry.getRange(argparser
                        .getResult("sections"));
            } else if (argparser.isSet("articles")) {
                elementsToDisplay = articleRegistry.getRange(argparser.getResult
                        ("articles"));
            } else {
                elementsToDisplay = Collections.singletonList(root);
            }

            List<Identifier> elementSpecification =
                    Stream.of(
                            argparser.<Identifier>getResult("paragraph"),
                            argparser.<Identifier>getResult("point"),
                            argparser.<Identifier>getResult("letter")
                    )
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());

            if (elementSpecification.isEmpty()) {
                printer.print(elementsToDisplay);
            } else {
                if (!isSingleArticleSelected(elementsToDisplay)) {
                    System.out.println("Single article must be chosen to " +
                            "display more specific elements.");
                    System.exit(1);
                }

                AbstractElement singleElement = elementsToDisplay.get(0)
                        .getDescendant(elementSpecification);
                printer.print(singleElement);
            }

            System.exit(0);
        } catch (Exception ex) {
            System.out.println("Error! " + ex.getMessage());
            System.exit(1);
        }
    }

    private static boolean isSingleArticleSelected(List<AbstractElement>
            elementsToDisplay) {
        return elementsToDisplay.size() == 1
                && elementsToDisplay.get(0) instanceof Article;
    }

}
