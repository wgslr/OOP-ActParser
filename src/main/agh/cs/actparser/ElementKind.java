package agh.cs.actparser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ElementKind {
    // Ordered from most general ones to most specific

    Document,
    Section, // Dział
    //Chapter, // Rozdział
    Article, // Artykuł
    Paragraph, // Ustęp
    Point, // Punkt
    Letter, // litera
    Indent, // Tiret
    Plaintext;

    public int toLevel(){
        return this.compareTo(Document);
    }

    public static List<ElementKind> getMoreSpecificThan(ElementKind kind) {
        return Arrays.stream(ElementKind.values())
                .filter(k -> k.compareTo(kind) > 0)
                .collect(Collectors.toList());
    }

}
