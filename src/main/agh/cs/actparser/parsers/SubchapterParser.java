package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.IElementRegistry;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Subchapter;

import java.util.List;

public class SubchapterParser extends TitledElementParser {
    public SubchapterParser(List<String> linesToParse, List<IElementRegistry>
            registries) {
        super(linesToParse, registries);
    }

    @Override
    protected ElementKind getKind() {
        return ElementKind.Subchapter;
    }

    @Override
    protected void parseStructure(List<String> linesToParse) {
        identifier = title = linesToParse.get(0);
        bodyLines = linesToParse.subList(1, linesToParse.size());
    }

    /**
     * Create Element object for given content.
     *
     * @return Created Element instance.
     */
    @Override
    public AbstractElement makeElement() {
        return new Subchapter(identifier, title, childrenElements);
    }
}
