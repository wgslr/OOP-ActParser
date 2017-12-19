package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.IElementRegistry;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Paragraph;

import java.util.List;

public class ParagraphParser extends AbstractParser {

    public ParagraphParser(List<String> linesToParse, List<IElementRegistry>
            registries) {
        super(linesToParse, registries);
    }

    @Override
    protected ElementKind getKind() {
        return ElementKind.Paragraph;
    }

    @Override
    public AbstractElement makeElement() {
        return new Paragraph(identifier, childrenElements);
    }
}
