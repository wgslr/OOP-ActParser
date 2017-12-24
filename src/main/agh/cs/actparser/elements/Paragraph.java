package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;

public class Paragraph extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Paragraph;
    }

    public Paragraph(String identifier, LinkedHashMap<Identifier, AbstractElement> children) {
        super(identifier, children);
    }

    @Override
    public String toString() {
        return idString + ". ";
    }
}
