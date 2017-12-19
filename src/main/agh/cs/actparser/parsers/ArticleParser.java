package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Article;

import java.util.List;

public class ArticleParser extends AbstractParser {
    public ArticleParser(List<String> linesToParse) {
        super(linesToParse);
    }

    @Override
    protected ElementKind getKind() {
        return ElementKind.Article;
    }

    @Override
    public AbstractElement makeElement() {
        return new Article(identifier, childrenElements);
    }
}
