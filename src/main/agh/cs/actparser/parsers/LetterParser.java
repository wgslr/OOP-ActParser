package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.IElementRegistry;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Letter;

import java.util.List;

public class LetterParser extends AbstractParser {
    public LetterParser(List<String> linesToParse, List<IElementRegistry>
            registries) {
        super(linesToParse, registries);
    }

    @Override
    protected ElementKind getKind() {
        return ElementKind.Letter;
    }

    @Override
    public AbstractElement makeElement() {
        return new Letter(identifier, childrenElements);
    }
}
