package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.List;

public class Indent extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return null;
    }

    public Indent(String identifier, List<AbstractElement> children) {
        super(identifier, children);
    }
}
