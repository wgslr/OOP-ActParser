package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;

public class Letter extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Letter;
    }

    public Letter(String identifier, LinkedHashMap<Identifier, AbstractElement> children) {
        super(identifier, children);
    }

    @Override
    public String toString() {
        return idString + ") ";
    }
}
