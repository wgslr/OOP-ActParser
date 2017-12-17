package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.Chapter;
import agh.cs.actparser.elements.Element;

import java.util.List;

public class ChapterParser extends AbstractParser {
    @Override
    public Element createElement(List<String> linesPart) {
        return new Chapter();
    }
}
