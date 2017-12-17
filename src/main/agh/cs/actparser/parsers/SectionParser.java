package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.Element;
import agh.cs.actparser.elements.Section;

import java.util.List;

public class SectionParser extends AbstractParser {

    @Override
    public Element createElement(List<String> linesPart) {
        return new Section();
    }
}
