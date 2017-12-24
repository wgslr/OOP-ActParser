package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.IElementRegistry;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Document;

import java.util.List;

public class DocumentParser extends ElementParser {
    public DocumentParser(List<String> linesToParse, List<IElementRegistry> registries) {
        super(ElementKind.Document, linesToParse, registries);
    }

    /**
     * Identifies main parts of the parsed element and sets them in the
     * parser's fields.
     *
     * @param linesToParse
     */
    @Override
    protected void parseStructure(List<String> linesToParse) {
        this.idString = this.title = "";
        this.bodyLines = linesToParse;
    }

    /**
     * Create Element object for given title.
     *
     * @return Created Element instance.
     */
    @Override
    public Document makeElement() {
        return new Document(idString, title, childrenElements);
    }
}
