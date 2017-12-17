package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Point;

import java.util.List;

public class PointParser extends AbstractParser {

    @Override
    String getStartPattern() {
        return "^\\d\\)";
    }

    @Override
    public AbstractElement createElement(List<String> linesPart) {
        return new Point(linesPart);
    }
}
