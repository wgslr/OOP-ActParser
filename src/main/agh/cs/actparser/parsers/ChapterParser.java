package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.IElementRegistry;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Chapter;

import java.util.List;

public class ChapterParser extends TitledElementParser {
    public ChapterParser(List<String> linesToParse, List<IElementRegistry>
            registries) {
        super(linesToParse, registries);
    }

    @Override
    protected ElementKind getKind() {
        return ElementKind.Chapter;
    }

    @Override
    public AbstractElement makeElement() {
        return new Chapter(idString, title, childrenElements);
    }
}
