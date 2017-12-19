package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.List;

public class Letter extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Letter;
    }

    public Letter(String identifier, List<AbstractElement> children) {
        super(identifier, children);
    }

    @Override
    protected String headerToString() {
        return identifier + ") ";
    }
}
