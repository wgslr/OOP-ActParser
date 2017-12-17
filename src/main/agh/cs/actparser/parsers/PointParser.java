package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.Element;
import agh.cs.actparser.elements.Point;

import java.util.List;

public class PointParser extends AbstractParser {

    @Override
    public Element createElement(List<String> linesPart) {
        return new Point();
    }
}
