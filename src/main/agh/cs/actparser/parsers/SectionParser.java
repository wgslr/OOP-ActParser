package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Section;

import java.util.List;

public class SectionParser extends AbstractParser {
    @Override
    String getStartPattern() {
        return "^DZIAŁ [IVXLCD]+$";
    }

    @Override
    public AbstractElement createElement(List<String> linesPart) {
        return new Section(linesPart);
    }
}
