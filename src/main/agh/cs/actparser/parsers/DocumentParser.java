package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.Document;
import agh.cs.actparser.elements.AbstractElement;

import java.util.List;

public class DocumentParser extends AbstractParser {

    @Override
    String getStartPattern() {
        return "(?!)"; // never matches
    }

    @Override
    public AbstractElement createElement(List<String> linesPart) {
        return new Document(null);
    }
}
