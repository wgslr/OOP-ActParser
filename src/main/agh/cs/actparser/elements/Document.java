package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;
import java.util.List;

public class Document extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Document;
    }

    public Document(String identifier, LinkedHashMap<Identifier, AbstractElement> children) {
        super(identifier, children);
    }
}
