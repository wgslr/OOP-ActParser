package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;

public class Chapter extends TitledElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Chapter;
    }

    public Chapter(String identifier, String content,
                   LinkedHashMap<Identifier, AbstractElement> children) {
        super(identifier, content, children);
    }

    @Override
    public String toString() {
        return String.format("Rozdział %s %s", idString, content);
    }
}
