package agh.cs.actparser;

import agh.cs.actparser.argparser.ArgumentParser;
import agh.cs.actparser.argparser.ArgumentType;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Document;
import agh.cs.actparser.parsers.DocumentParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParsingEngine {
    // TODO handle non-existent file
    public static void main(String[] args) throws IOException {

        ArgumentParser argparser = new ArgumentParser();
        argparser.addOption(
                new ArgumentParser.Option("file", "f",
                        "File containing document to parse",
                        ArgumentType.Text)
        );
        argparser.addOption(
                new ArgumentParser.Option("toc", "t",
                        "Whether to display table of contents", ArgumentType
                        .Bool)
        );
        argparser.addOption(
                new ArgumentParser.Option("articles", "a",
                        "One or more articles to display. Syntax: 1a..100",
                        ArgumentType.IdentifierRange)
        );
        argparser.addOption(
                new ArgumentParser.Option("sections", "s",
                        "One or more sections to display. Syntax: I..VI",
                        ArgumentType.IdentifierRange)
        );
        argparser.addOption(
                new ArgumentParser.Option("chapters", "c",
                        "One or more chapters to display. Syntax: " +
                                "1..21 or III..IV",
                        ArgumentType.IdentifierRange)
        );
        argparser.addOption(
                new ArgumentParser.Option("paragraph", "p",
                        "Paragraph to display", ArgumentType.Number)
        );
        argparser.addOption(
                new ArgumentParser.Option("point", "o",
                        "Point to display", ArgumentType.Number)
        );

        try {
            argparser.parse(args);
        } catch (IllegalArgumentException ex) {
            System.out.println("Arguments could not be parsed: " + ex
                    .getMessage() + "\nAvailable arguments:\n"
                    + argparser.getArgsHelp());
            return;
        }


        // TODO access chapters beneath sections

        String filepath = (String) argparser.getResult("file");

        List<String> inputLines;

        try {
            Path path = Paths.get(filepath);
            inputLines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("File \"" + filepath + "\" could not be read!");
            return;
        }

        inputLines = new Preprocessor().process(inputLines);

        IFormatter formatter = (Boolean) argparser.getResult("toc") ?
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

        // TODO error handling
        Document root = docparser.makeElement();

        List<AbstractElement> elementsToDisplay;

        if (argparser.isSet("chapters")) {
            elementsToDisplay = chaptersRegistry.getRange((Range<String>)
                    argparser.getResult("chapters"));
        } else if (argparser.isSet("sections")) {
            elementsToDisplay = sectionsRegistry.getRange((Range<String>)
                    argparser.getResult("chapters"));
        } else if (argparser.isSet("articles")) {
            elementsToDisplay = articleRegistry.getRange((Range<String>)
                    argparser.getResult("chapters"));
        } else {
            elementsToDisplay = Collections.singletonList(root);
        }

        formatter.print(elementsToDisplay);


        //System.out.println(ElementKind.Section.compareTo(ElementKind
        //
        // .Letter));
//
//        Preprocessor c = new Preprocessor();
//
////        Path path = Paths.get("./assets/konstytucja.txt");
//        //Path path = Paths.get("./assets/uokik_min.txt");
//        Path path = Paths.get("./assets/uokik.txt");
//        List<String> lines = c.process(Files.readAllLines(path));
//
//        Files.write(Paths.get("processed.txt"), lines);
//
//        //System.out.println(String.join("\n", lines));
//
//        ElementRegistry registry = new ElementRegistry();
//
//        DocumentParser parser = new DocumentParser(lines,
//                Arrays.asList(registry));
//        Document doc = parser.makeElement();
//
//        //System.out.print(doc);
//
////        System.out.println(registry.articles.size());
////        System.out.println(registry.articles.keySet());
//
////        registry.getRange(Identifier.fromString("104", null),
////                Identifier.fromString
////                ("105j", null))
////                .forEach(a ->
////                System.out.print(a));
//        //System.out.print(doc.children);
//
//        System.out.println();
//
//
//        Identifier articleLocator = Identifier.fromString("114",
//                ElementKind.Article);
//        registry.articles.values().forEach(
//                art -> System.out.println(art.identifier)
//        );
//        Article root = (Article) registry.getRange(articleLocator,
//                articleLocator).get
//                (0);
//        System.out.println(
//                root
//                        .getDescendant(/*Arrays.asList(
//                                Identifier.fromString("2", ElementKind
//                                        .Paragraph),
//                                Identifier.fromString("2", ElementKind
//                                        .Point),
//                                Identifier.fromString("a", ElementKind
//                                        .Letter)*/
//                                Collections.emptyList()));
//
//
//        TableOfContentFormatter toc = new TableOfContentFormatter();
//        toc.print(Collections.singletonList(doc));
//
//        PlaintextFormatter pf = new PlaintextFormatter();
//        pf.print(Collections.singletonList(doc));
//
//        ArgumentParser ap = new ArgumentParser();
//        ap.parse(args);

    }
}
