package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.Element;
import agh.cs.actparser.elements.Indent;

import java.util.List;

public class IndentParser extends AbstractParser {
    @Override
    public Element createElement(List<String> linesPart) {
        return new Indent();
    }
}
