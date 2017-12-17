package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.Element;
import agh.cs.actparser.elements.Paragraph;

import java.util.List;

public class ParagraphParser extends AbstractParser {

    @Override
    public Element createElement(List<String> linesPart) {
        return new Paragraph();
    }
}
