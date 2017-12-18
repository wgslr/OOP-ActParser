package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Point;

import java.util.List;

public class PointParser extends AbstractParser {
    @Override
    protected String getStartPattern() {
        return "^(\\d+)\\)\\s(.*)";
    }

    @Override
    protected AbstractElement createElement(String identifier, List<AbstractElement> children) {
        return new Point(identifier, children);
    }

    public PointParser(List<AbstractParser> childrenParsers) {
        super(childrenParsers);
    }
}
