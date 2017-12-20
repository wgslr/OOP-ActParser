package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.IElementRegistry;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Point;

import java.util.List;

public class PointParser extends AbstractParser {

    public PointParser(List<String> linesToParse, List<IElementRegistry>
            registries) {
        super(linesToParse, registries);
    }

    @Override
    protected ElementKind getKind() {
        return ElementKind.Point;
    }

    @Override
    public AbstractElement makeElement() {
        return new Point(idString, childrenElements);
    }
}
