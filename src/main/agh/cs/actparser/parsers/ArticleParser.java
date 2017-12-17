package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.Article;
import agh.cs.actparser.elements.Element;

import java.util.List;

public class ArticleParser extends AbstractParser {

    @Override
    public Element createElement(List<String> linesPart) {
        return new Article();
    }
}
