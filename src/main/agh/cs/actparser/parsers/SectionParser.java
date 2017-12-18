package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Section;

import java.util.List;

public class SectionParser extends AbstractParser {
    @Override
    protected String getStartPattern() {
        return "^DZIAŁ ([IVXCD]+)(.*)";
    }

    @Override
    protected AbstractElement createElement(String identifier, List<AbstractElement> children) {
        return new Section(identifier, children);
    }

    @Override
    protected ElementStructure parseStructure(List<String> lines) {
        String identifier = lines.get(1);
        return new ElementStructure(identifier, lines.subList(2,lines.size()));
    }

    public SectionParser(List<AbstractParser> childrenParsers) {
        super(childrenParsers);
    }
}
