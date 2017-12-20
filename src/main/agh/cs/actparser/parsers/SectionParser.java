package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.IElementRegistry;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Section;

import java.util.List;

public class SectionParser extends TitledElementParser {
    public SectionParser(List<String> linesToParse, List<IElementRegistry>
            registries) {
        super(linesToParse, registries);
    }

    @Override
    protected ElementKind getKind() {
        return ElementKind.Section;
    }

    @Override
    public AbstractElement makeElement() {
        return new Section(idString, title, childrenElements);
    }
}
