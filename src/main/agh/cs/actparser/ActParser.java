package agh.cs.actparser;

import agh.cs.actparser.parsers.ParserAbstract;

import java.util.Arrays;
import java.util.List;

public class ActParser {
    public static void main(String[] args) {
        List<String> lines = Arrays.asList(
                "Konstytucja", "Art 1", "Jeden",
                "Dwadzieścia trzy", "Art 2", "Wojna", "Art 3");
        List<List<String>> split = new ParserAbstract().splitLines(lines);
        System.out.println(split);
    }
}
