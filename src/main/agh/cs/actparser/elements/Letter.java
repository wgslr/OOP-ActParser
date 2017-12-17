package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.List;

public class Letter extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return null;
    }

    public Letter(String identifier, List<AbstractElement> children) {
        super(identifier, children);
    }
}
