package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;
import sun.awt.image.ImageWatched;

import java.util.LinkedHashMap;
import java.util.List;

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
    protected String headerToString() {
        return "Art. " + idString + ".\n";
    }
}
