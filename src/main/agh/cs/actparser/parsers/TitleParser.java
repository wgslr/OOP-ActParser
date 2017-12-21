package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.IElementRegistry;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Title;

import java.util.List;

public class TitleParser extends TitledElementParser {
    public TitleParser(List<String> linesToParse, List<IElementRegistry>
            registries) {
        super(linesToParse, registries);
    }

    @Override
    protected ElementKind getKind() {
        return ElementKind.Title;
    }

    @Override
    protected void parseStructure(List<String> linesToParse) {
        idString = "";
        title = linesToParse.get(0);
        bodyLines = linesToParse.subList(1, linesToParse.size());
    }

    /**
     * Create Element object for given content.
     *
     * @return Created Element instance.
     */
    @Override
    public AbstractElement makeElement() {
        return new Title(idString, title, childrenElements);
    }
}
