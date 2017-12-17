package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.Element;
import agh.cs.actparser.elements.Letter;

import java.util.List;

public class LetterParser extends AbstractParser {
    @Override
    public Element createElement(List<String> linesPart) {
        return new Letter();
    }
}
