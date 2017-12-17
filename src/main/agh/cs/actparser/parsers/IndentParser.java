package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Indent;

import java.util.List;

public class IndentParser extends AbstractParser {
    @Override
    String getStartPattern() {
        return "^-";
    }

    @Override
    public AbstractElement createElement(List<String> linesPart) {
        return new Indent(null);
    }
}
