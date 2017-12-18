package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Indent;

import java.util.List;

public class IndentParser extends AbstractParser {
    public IndentParser(List<String> linesToParse) {
        super(linesToParse);
    }

    @Override
    protected ElementKind getKind() {
        return ElementKind.Indent;
    }

    @Override
    public AbstractElement makeElement() {
        return new Indent(identifier, childrenElements);
    }
}
