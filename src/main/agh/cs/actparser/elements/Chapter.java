package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;
import java.util.List;

public class Chapter extends TitledElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Chapter;
    }

    public Chapter(String identifier, String title,
                   LinkedHashMap<Identifier, AbstractElement> children) {
        super(identifier, title, children);
    }

    @Override
    public String headerToString() {
        return String.format("Rozdział %s %s", idString, title);
    }
}
