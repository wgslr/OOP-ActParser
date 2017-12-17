package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.Document;
import agh.cs.actparser.elements.Element;

import java.util.List;

public class DocumentParser extends AbstractParser {

    @Override
    public Element createElement(List<String> linesPart) {
        return new Document();
    }
}
