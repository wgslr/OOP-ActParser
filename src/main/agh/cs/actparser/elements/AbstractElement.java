package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.Collections;
import java.util.List;

public abstract class AbstractElement {
    protected final String identifier;
    public List<AbstractElement> children;

    abstract public ElementKind getKind();

    public AbstractElement(String identifier, List<AbstractElement> children) {
        this.identifier = identifier;
        this.children = children;
    }

    public String toString() {
        return headerToString() +
                children.stream()
                        .map(AbstractElement::toString)
                        .reduce(String::concat)
                        .orElse("");
    }

    protected String headerToString() {
        return "";
    }
}
