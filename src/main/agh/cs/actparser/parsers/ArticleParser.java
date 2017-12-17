package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.Article;
import agh.cs.actparser.elements.AbstractElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleParser extends AbstractParser {

    public ArticleParser(List<AbstractParser> childrenParsers) {
        super(childrenParsers);
    }

    @Override
    protected String getStartPattern() {
        return "Art\\. (\\S+)\\.(.*)";
    }

    @Override
    protected AbstractElement createElement(String identifier, List<String>
            bodyLines) {
        //return new Article();
        System.out.println("Creating article: " + joinLines(bodyLines));
        return null;
    }
}
