package agh.cs.actparser;

import agh.cs.actparser.elements.Article;
import agh.cs.actparser.elements.Document;
import agh.cs.actparser.parsers.*;
import sun.rmi.server.Activation$ActivationSystemImpl_Stub;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ParsingEngine {
    // TODO handle non-existent file
    public static void main(String[] args) throws IOException {
        //System.out.println(ElementKind.Section.compareTo(ElementKind
        //
        // .Letter));

        Preprocessor c = new Preprocessor();

//        Path path = Paths.get("./assets/konstytucja.txt");
        //Path path = Paths.get("./assets/uokik_min.txt");
        Path path = Paths.get("./assets/uokik.txt");
        List<String> lines = c.process(Files.readAllLines(path));

        Files.write(Paths.get("processed.txt"), lines);

        //System.out.println(String.join("\n", lines));

        ArticleRegistry registry = new ArticleRegistry();

        DocumentParser parser = new DocumentParser(lines,
                Arrays.asList(registry));
        Document doc = parser.makeElement();

        //System.out.print(doc);

//        System.out.println(registry.articles.size());
//        System.out.println(registry.articles.keySet());

//        registry.getRange(Identifier.fromString("104", null),
//                Identifier.fromString
//                ("105j", null))
//                .forEach(a ->
//                System.out.print(a));
        //System.out.print(doc.children);

        System.out.println();


        Identifier articleLocator = Identifier.fromString("114",
                ElementKind.Article);
        registry.articles.values().forEach(
                art -> System.out.println(art.identifier)
        );
        Article root = (Article) registry.getRange(articleLocator,
                articleLocator).get
                (0);
        System.out.println(
                root
                        .getDescendant(/*Arrays.asList(
                                Identifier.fromString("2", ElementKind
                                        .Paragraph),
                                Identifier.fromString("2", ElementKind
                                        .Point),
                                Identifier.fromString("a", ElementKind
                                        .Letter)*/
                                Collections.emptyList()));


        TableOfContentFormatter toc = new TableOfContentFormatter();
        toc.print(Collections.singletonList(doc));

        PlaintextFormatter pf = new PlaintextFormatter();
        pf.print(Collections.singletonList(doc));

    }
}
