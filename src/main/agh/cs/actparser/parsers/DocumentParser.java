package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.Document;

import java.util.List;

public class DocumentParser extends AbstractParser {
    public DocumentParser(List<String> linesToParse) {
        super(linesToParse);
    }

    @Override
    protected void parseStructure(List<String> linesToParse) {
        identifier = "";
        bodyLines = linesToParse;
    }

    @Override
    protected ElementKind getKind() {
        return ElementKind.Document;
    }

    @Override
    public Document makeElement() {
        return new Document(identifier, childrenElements);
    }
}
