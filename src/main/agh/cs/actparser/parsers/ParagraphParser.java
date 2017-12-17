package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Paragraph;

import java.util.List;

public class ParagraphParser extends AbstractParser {

    @Override
    String getStartPattern() {
        return "\\b\\d+\\.";
    }

    @Override
    public AbstractElement createElement(List<String> linesPart) {
        return new Paragraph(null);
    }
}
