package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.List;

public class Document extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Document;
    }

    public Document(String identifier, List<AbstractElement> children) {
        super(identifier, children);
    }
}
