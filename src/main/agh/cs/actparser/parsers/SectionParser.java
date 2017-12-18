package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Section;

import java.util.List;

public class SectionParser extends AbstractParser {
    String title;

    public SectionParser(List<String> linesToParse) {
        super(linesToParse);
    }

    @Override
    protected ElementKind getKind() {
        return ElementKind.Section;
    }

    @Override
    public AbstractElement makeElement() {
        return new Section(identifier, title, childrenElements);
    }

    @Override
    protected void parseStructure(List<String> linesToParse) {
        super.parseStructure(linesToParse);
        if(linesToParse.size() < 2)  {
            throw new IllegalArgumentException("Invalid Section");
        }
        title = linesToParse.get(1);
        bodyLines = linesToParse.subList(2, linesToParse.size());
    }
}
