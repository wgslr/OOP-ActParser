package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;

public class Point extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Point;
    }

    public Point(String idString, String content,
            LinkedHashMap<Identifier, AbstractElement> children) {
        super(idString, content, children);
    }

    @Override
    public String toString() {
        return idString + ") ";
    }
}
