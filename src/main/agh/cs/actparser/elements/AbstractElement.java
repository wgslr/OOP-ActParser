package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.List;

public abstract class AbstractElement {
    public final String idString;
    public final Identifier identifier;
    protected final List<AbstractElement> children;

    abstract public ElementKind getKind();

    public AbstractElement(String idString, List<AbstractElement> children) {
        this.idString = idString;
        this.children = children;
        this.identifier = Identifier.fromString(idString, getKind());
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
