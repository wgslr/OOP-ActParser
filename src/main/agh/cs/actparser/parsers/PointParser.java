package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Point;

import java.util.List;

public class PointParser extends AbstractParser {

    public PointParser(List<String> linesToParse) {
        super(linesToParse);
    }

    @Override
    protected ElementKind getKind() {
        return ElementKind.Point;
    }

    @Override
    public AbstractElement makeElement() {
        return new Point(identifier, childrenElements);
    }
}
