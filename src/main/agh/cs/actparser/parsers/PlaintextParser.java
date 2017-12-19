package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.IElementRegistry;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Plaintext;

import java.util.List;

public class PlaintextParser extends AbstractParser {
    public PlaintextParser(List<String> linesToParse, List<IElementRegistry>
            registries) {
        super(linesToParse, registries);
    }

    @Override
    protected ElementKind getKind() {
        return ElementKind.Plaintext;
    }

    @Override
    protected void parseStructure(List<String> linesToParse) {
        identifier = null;
        bodyLines = linesToParse;
    }

    @Override
    protected void parseChildren(List<String> bodyLines) { }

    @Override
    public AbstractElement makeElement() {
        return new Plaintext(bodyLines);
    }
}
