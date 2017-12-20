package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.List;

public class Chapter extends TitledElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Chapter;
    }

    public Chapter(String identifier, String title, List<AbstractElement>
            children) {
        super(identifier, title, children);
    }

    @Override
    protected String headerToString() {
        return String.format("Rozdział %s\n%s\n", idString, title);
    }
}
