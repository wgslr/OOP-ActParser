package agh.cs.actparser;

import agh.cs.actparser.elements.Document;
import agh.cs.actparser.parsers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ParsingEngine {
    // TODO handle non-existent file
    public static void main(String[] args) throws IOException {
        //System.out.println(ElementKind.Section.compareTo(ElementKind
        //                                                             .Letter));

        Cleaner c = new Cleaner();

        //Path path = Paths.get("./assets/konstytucja.txt");
        //Path path = Paths.get("./assets/uokik_min.txt");
        Path path = Paths.get("./assets/uokik.txt");
        List<String> lines = c.filter(Files.readAllLines(path));

        ArticleRegistry registry = new ArticleRegistry();

        DocumentParser parser = new DocumentParser(lines, Arrays.asList(registry));
        Document doc = parser.makeElement();

        System.out.print(doc);

        System.out.println(registry.articles.size());
        System.out.println(registry.articles.keySet());
        //System.out.print(doc.children);
    }
}
