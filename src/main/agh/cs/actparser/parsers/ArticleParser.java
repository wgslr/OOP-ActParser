package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.Article;
import agh.cs.actparser.elements.AbstractElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleParser extends AbstractParser {
    String getStartPattern() {
        return "Art\\.";
    }

    private final Pattern identifierPattern =
            Pattern.compile("Art\\. (\\S+)\\.(.*)");

    // rozbij to na dwie linie


    @Override
    public AbstractElement createElement(List<String> linesPart) {
        return new Article(null, getIdentifier(linesPart.get(0)));
    }

    private String getIdentifier(String line) {
        Matcher m = identifierPattern.matcher(line);
        if (m.find()) {
            return m.group(1);
        } else {
            return "";
        }
    }
}
