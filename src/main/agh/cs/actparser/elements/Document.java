package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;

public class Document extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Document;
    }

    public Document(String identifier, String content,
            LinkedHashMap<Identifier, AbstractElement> children) {
        super(identifier, content, children);
    }
}
