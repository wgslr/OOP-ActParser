package agh.cs.actparser;

import agh.cs.actparser.argparser.ArgumentParser;
import agh.cs.actparser.argparser.ArgumentParsers;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Article;
import agh.cs.actparser.elements.Document;
import agh.cs.actparser.parsers.DocumentParser;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

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
    public static void main(String[] args) throws IOException {

        ArgumentParser argparser = new ArgumentParser();
        argparser.addOption(
                new ArgumentParser.Option("file", "f",
                        "File containing document to parse. Format: Text",
                        ArgumentParsers.getTextParser())
        );
        argparser.addOption(
                new ArgumentParser.Option("toc", "t",
                        "Whether to display table of contents. Format: Flag",
                        null)
        );
        argparser.addOption(
                new ArgumentParser.Option("articles", "a",
                        "One or more articles (artykuły) to display. Format:" +
                                " " +
                                "Range",
                        ArgumentParsers.getIdentifierRangeParser(ElementKind
                                .Article))
        );
        argparser.addOption(
                new ArgumentParser.Option("sections", "s",
                        "One or more sections (działy) to display. Format: " +
                                "Range",
                        ArgumentParsers.getIdentifierRangeParser(ElementKind
                                .Section))
        );
        argparser.addOption(
                new ArgumentParser.Option("chapters", "c",
                            "Chapters (rozdziały) to dispay. Format: Range",
                        ArgumentParsers.getIdentifierRangeParser(ElementKind
                                .Chapter))
        );
        argparser.addOption(
                new ArgumentParser.Option("paragraph", "p",
                "Paragraph (ustęp) to display. Format: Identifier",
                        ArgumentParsers.getIdentifierParser(ElementKind
                                .Paragraph))
        );
        argparser.addOption(
                new ArgumentParser.Option("point", "o",
                        "Point (punkt) to display. Format: Identifier",
                        ArgumentParsers.getIdentifierParser(ElementKind.Point))
        );
        argparser.addOption(
                new ArgumentParser.Option("letter", "l",
                        "Letter (litera) to display. Format: Identifier",
                        ArgumentParsers.getIdentifierParser(ElementKind.Letter))
        );
        argparser.addOption(
                new ArgumentParser.Option("help", "h",
                        "Display this help message. Format: Flag",
                        null)
        );

        try {
            argparser.parse(args);
        } catch (IllegalArgumentException ex) {
            System.out.println("Arguments could not be parsed: " + ex
                    .getMessage() + "\nAvailable arguments:\n"
                    + argparser.getArgsHelp());
            return;
        }

        if(argparser.getResult("help")){
            System.out.println(argparser.getArgsHelp());
            System.exit(0);
        }


        String filepath = (String) argparser.getResult("file");

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

        IFormatter formatter = argparser.getResult("toc") ?
                new TableOfContentFormatter()
                : new PlaintextFormatter();

        // Process
        ElementRegistry chaptersRegistry =
                new ElementRegistry(ElementKind.Chapter);
        ElementRegistry sectionsRegistry =
                new ElementRegistry(ElementKind.Section);
        ElementRegistry articleRegistry =
                new ElementRegistry(ElementKind.Article);

        DocumentParser docparser = new DocumentParser(inputLines, Arrays.asList(
                chaptersRegistry, sectionsRegistry, articleRegistry
        ));

        Document root = null;
        try {
            root = docparser.makeElement();
        } catch (IllegalArgumentException e) {
            System.out.println("Error occured during parsing of the document:" +
                    e.getMessage());
            System.exit(1);
        }

        List<AbstractElement> elementsToDisplay;

        try {

            if (argparser.isSet("chapters")) {
                elementsToDisplay = chaptersRegistry.getRange(argparser.getResult

                        ("chapters"));
            } else if (argparser.isSet("sections")) {
                elementsToDisplay = sectionsRegistry.getRange(argparser
                        .getResult
                        ("sections"));
            } else if (argparser.isSet("articles")) {
                elementsToDisplay = articleRegistry.getRange(argparser.getResult
                        ("articles"));
            } else {
                elementsToDisplay = Collections.singletonList(root);
            }

            List<Identifier> elementSpecification =
                    Stream.of(
                            (Identifier) argparser.getResult("paragraph"),
                            (Identifier) argparser.getResult("point"),
                            (Identifier) argparser.getResult("letter")
                    )
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());

            if (elementSpecification.isEmpty()) {
                formatter.print(elementsToDisplay);
            } else {
                if (elementsToDisplay.size() != 1 ||
                        !(elementsToDisplay.get(0) instanceof Article)) {
                    System.out.println("Single article must be chosen to " +
                            "display more specific elements.");
                    System.exit(1);
                }

                AbstractElement singleElement = elementsToDisplay.get(0)
                        .getDescendant(elementSpecification);
                formatter.print(singleElement);
            }

            System.exit(0);
        } catch (Exception ex) {
            System.out.println("Error! " + ex.getMessage());
            System.exit(1);
        }
    }
}
