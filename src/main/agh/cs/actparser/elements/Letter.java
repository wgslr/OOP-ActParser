package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;

public class Letter extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Letter;
    }

    public Letter(String identifier, String content, LinkedHashMap<Identifier,
            AbstractElement> children) {
        super(identifier, content, children);
    }

    @Override
    public String toString() {
        return idString + ") ";
    }
}
