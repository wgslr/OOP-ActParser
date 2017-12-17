package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Paragraph;

import java.util.List;

public class ParagraphParser extends AbstractParser {

    public ParagraphParser(List<AbstractParser> childrenParsers) {
        super(childrenParsers);
    }

    @Override
    protected String getStartPattern() {
        return "^(\\d+)\\.(.*)";
    }

    @Override
    protected AbstractElement createElement(String identifier, List<AbstractElement> children) {
        return new Paragraph(identifier, children);
    }
}
