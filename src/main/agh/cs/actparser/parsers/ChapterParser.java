package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.Chapter;
import agh.cs.actparser.elements.AbstractElement;

import java.util.List;

public class ChapterParser extends AbstractParser {
    @Override
    String getStartPattern() {
        return "^Rozdział \\d+$";
    }

    @Override
    public AbstractElement createElement(List<String> linesPart) {
        return new Chapter(linesPart);
    }
}
