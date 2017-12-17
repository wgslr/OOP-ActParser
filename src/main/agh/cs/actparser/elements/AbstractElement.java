package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractElement {
    List<AbstractElement> children;

    public AbstractElement(List<AbstractElement> children) {
        this.children = children;
    }

    public String toString() {
        return children.stream()
                .map(AbstractElement::toString).reduce
                (String::concat)
                .orElse("");
    }

}
