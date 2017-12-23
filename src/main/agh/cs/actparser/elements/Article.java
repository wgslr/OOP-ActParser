package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;

public class Article extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Article;
    }

    public Article(String identifier, LinkedHashMap<Identifier, AbstractElement>
            children) {
        super(identifier, children);
    }

    @Override
    public String headerToString() {
        return "Art. " + idString + ". ";
    }
}
