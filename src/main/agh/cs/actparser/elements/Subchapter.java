package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.List;

public class Subchapter extends TitledElement {
    public Subchapter(String identifier, String title, List<AbstractElement> children) {
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
