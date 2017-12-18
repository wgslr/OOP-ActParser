package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Chapter;
import agh.cs.actparser.elements.TitledElement;

import java.util.List;

public class ChapterParser extends TitledElementParser {
    String title;

    public ChapterParser(List<String> linesToParse) {
        super(linesToParse);
    }

    @Override
    protected ElementKind getKind() {
        return ElementKind.Chapter;
    }

    @Override
    public AbstractElement makeElement() {
        return new Chapter(identifier, title, childrenElements);
    }
}
