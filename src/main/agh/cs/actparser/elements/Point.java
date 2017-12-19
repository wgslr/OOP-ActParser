package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.List;

public class Point extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Point;
    }

    public Point(String identifier, List<AbstractElement> children) {
        super(identifier, children);
    }

    @Override
    protected String headerToString() {
        return identifier + ") ";
    }
}
