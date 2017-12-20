package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.List;

public class Article extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Article;
    }

    public Article(String identifier, List<AbstractElement> children) {
        super(identifier, children);
    }

    @Override
    protected String headerToString() {
        return "Art. " + idString + ".\n";
    }
}
