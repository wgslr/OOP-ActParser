package agh.cs.actparser;

import agh.cs.actparser.parsers.ParserFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ElementKind {
    // Ordered from most general ones to most specific

    Document,
    Section, // Dział
    Chapter, // Rozdział
    Title,
    Article, // Artykuł
    Paragraph, // Ustęp
    Point, // Punkt
    Letter, // litera
    Plaintext;

    public List<ElementKind> getMoreSpecific() {
        return Arrays.stream(ElementKind.values())
                .filter(k -> k.compareTo(this) > 0)
                .collect(Collectors.toList());
    }

    public List<ElementKind> getLessSpecific() {
        return Arrays.stream(ElementKind.values())
                .filter(k -> k.compareTo(this) < 0)
                .collect(Collectors.toList());
    }


    /**
     * Returns regular expression identifing start of this element. The
     * expression contains two groups, first matching the idString of element
     * and second any leftover content.
     */
    public String getRegexp() {
        switch (this) {
            case Section:
                return "^DZIAŁ ([IVXCD]+)\\s*(.*)";
            case Chapter:
                return "^Rozdział (\\d+|[IVXCD]+)\\s*(.*)";
            case Title:
                return "^([\\p{Lu} ]+)\\s*$";
            case Article:
                return "^Art\\. (\\d+[a-z]*)\\.\\s*(.*)";
            case Paragraph:
                return "^(\\d+)\\.\\s(.*)";
            case Point:
                return "^(\\d+)\\)\\s(.*)";
            case Letter:
                return "^(\\p{L})\\)\\s(.*)";
            case Plaintext:
                return "()().*"; // always matches
            case Document:
                return "(?!)"; // never matches - there can be only one document
            default:
                throw new IllegalArgumentException();
        }
    }
}
