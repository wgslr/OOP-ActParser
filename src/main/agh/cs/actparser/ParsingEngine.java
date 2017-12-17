package agh.cs.actparser;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.parsers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class ParsingEngine {
    // TODO handle non-existent file
    public static void main(String[] args) throws IOException {
        //System.out.println(ElementKind.Section.compareTo(ElementKind
        //                                                             .Letter));

        Cleaner c = new Cleaner();

        Path path = Paths.get("./assets/konstytucja.txt");
        //Path path = Paths.get("./assets/uokik_min.txt");
        List<String> lines = c.filter(Files.readAllLines(path));

        //AbstractParser p = new ArticleParser(Collections.emptyList());

        AbstractParser parser = new ParserFactory().getNestedParsers();

        List<AbstractElement> elements = parser.parse(lines);
        for(AbstractElement ae : elements) {
            System.out.println(ae);
        }

        //List<List<String>> split = new AbstractParser().splitLines(lines);
        //System.out.println(split);
    }
}
