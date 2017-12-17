package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Article;

import java.util.List;

public class ArticleParser extends AbstractParser {

    public ArticleParser(List<AbstractParser> childrenParsers) {
        super(childrenParsers);
    }

    @Override
    protected String getStartPattern() {
        return "Art\\. (\\S+)\\.(.*)";
    }

    @Override
    protected AbstractElement createElement(String identifier,
                                            List<AbstractElement> children) {
        return new Article(identifier, children);
    };
}
