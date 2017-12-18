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
    Article, // Artykuł
    Paragraph, // Ustęp
    Point, // Punkt
    Letter, // litera
    Indent, // Tiret
    Plaintext;

    public static List<ElementKind> getMoreSpecificThan(ElementKind kind) {
        return Arrays.stream(ElementKind.values())
                .filter(k -> k.compareTo(kind) > 0)
                .collect(Collectors.toList());
    }

    public int getLevel(){
        return this.compareTo(Document);
    }


    /**
     * Returns regular expression identifing start of this element. The
     * expression contains two groups, first matching the identifier of element
     * and second any leftover content.
     */
    public String getRegexp() {
        switch (this) {
            case Section:
                return "^DZIAŁ ([IVXCD]+)(.*)";
            case Chapter:
                return "^Rozdział (\\d+)\\s?(.*)";
            case Article:
                return "^Art\\. (\\d+)\\.";
            case Paragraph:
                return "^(\\d+)\\.\\s";
            case Point:
                return "^(\\d+)\\)\\s";
            case Letter:
                return "^(\\p{L}+)\\)\\s";
            case Indent:
                return "^-\\s";
            case Plaintext:
                return "(?!)"; // never matches
            default:
                // matches also Document as there cannot
                // be more than one Document in a file
                throw new UnsupportedOperationException();
        }
    }
}
