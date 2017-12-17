package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.List;

public class Chapter extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return null;
    }

    public Chapter(String identifier, List<AbstractElement> children) {
        super(identifier, children);
    }
}
