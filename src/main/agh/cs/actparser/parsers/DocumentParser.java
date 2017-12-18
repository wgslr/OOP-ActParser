package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Document;

import java.util.Collections;
import java.util.List;

public class DocumentParser extends AbstractParser {
    public DocumentParser(List<AbstractParser> childrenParsers) {
        super(childrenParsers);
        System.out.println("childrenParsers: " + childrenParsers);
    }

    @Override
    protected String getStartPattern() {
        return "(?!)";
    }

    @Override
    protected List<Range> getPartsIndices(List<String> lines) {
        return Collections.singletonList(new Range(0, lines.size()));
    }

    @Override
    protected ElementStructure parseStructure(List<String> lines) {
        return new ElementStructure("", lines);
    }

    @Override
    protected AbstractElement createElement(String identifier,
                                            List<AbstractElement> children) {
        return new Document(identifier, children);
    }
}
