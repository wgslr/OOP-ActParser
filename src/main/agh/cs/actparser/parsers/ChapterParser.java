package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Chapter;

import java.util.List;

public class ChapterParser extends AbstractParser {
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

    @Override
    protected void parseStructure(List<String> linesToParse) {
        super.parseStructure(linesToParse);

        if(linesToParse.size() < 2)  {
            throw new IllegalArgumentException("Invalid Chapter");
        }
        title = linesToParse.get(1);
        bodyLines = linesToParse.subList(2, linesToParse.size());
    }
}
