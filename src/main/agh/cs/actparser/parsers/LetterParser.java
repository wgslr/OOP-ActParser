package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Letter;

import java.util.List;

public class LetterParser extends AbstractParser {
    @Override
    String getStartPattern() {
        return "\\b\\p{Lower}\\)";
    }

    @Override
    public AbstractElement createElement(List<String> linesPart) {
        return new Letter(linesPart);
    }
}
