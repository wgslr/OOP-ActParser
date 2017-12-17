package agh.cs.actparser;

import agh.cs.actparser.parsers.AbstractParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ActParser {
    // TODO handle non-existent file
    public static void main(String[] args) throws IOException {
        System.out.println(DocumentElement.Section.compareTo(DocumentElement
                                                                     .Letter));

        Path path = Paths.get("./assets/konstytucja.txt");
        List<String> lines = Files.readAllLines(path);
        //List<List<String>> split = new AbstractParser().splitLines(lines);
        //System.out.println(split);


    }
}
