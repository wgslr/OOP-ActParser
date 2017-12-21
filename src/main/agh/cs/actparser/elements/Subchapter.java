package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;
import java.util.List;

public class Subchapter extends TitledElement {
    public Subchapter(String identifier, String title, LinkedHashMap<Identifier, AbstractElement> children) {
        super(identifier, title, children);
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.Subchapter;
    }

    @Override
    protected String headerToString() {
        return title + "\n";
    }
}
