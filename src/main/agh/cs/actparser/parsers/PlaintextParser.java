package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Plaintext;

import java.util.Collections;
import java.util.List;

public class PlaintextParser extends AbstractParser {
    public PlaintextParser(List<AbstractParser> childrenParsers) {
        super(childrenParsers);
    }

    @Override
    protected String getStartPattern() {
        return "(.*)()";
    }

    @Override
    public List<AbstractElement> parse(List<String> lines) {
        AbstractElement element = createElement(joinLines(lines), Collections
                .emptyList());
        return Collections.singletonList(element);
    }

    @Override
    protected AbstractElement createElement(String identifier,
                                            List<AbstractElement> children) {
        return new Plaintext(identifier);
    }
}
