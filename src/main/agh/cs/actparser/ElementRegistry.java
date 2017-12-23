package agh.cs.actparser;

import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Article;

import java.util.*;

public class ElementRegistry implements IElementRegistry {
    NavigableMap<Identifier, AbstractElement> elements = new TreeMap<>();

    public final ElementKind storedKind;

    public ElementRegistry(ElementKind storedKind) {
        this.storedKind = storedKind;
    }

    @Override
    public void add(AbstractElement element) {
        if (element.getKind() == storedKind) {
            elements.put(element.identifier, element);
        }
    }


    /**
     * Returns articles with ids higher or equal to "from"
     *
     * @return Articles
     */
    public List<AbstractElement> getRange(Identifier from, Identifier to) {
        if (!(elements.containsKey(from) && elements.containsKey(to))) {
            System.out.println("From: " + from);
            System.out.println("To: " + to);
            System.out.println(elements);
            throw new IllegalArgumentException("There is no such element!");
        }

        return new ArrayList<>(elements.subMap(from, true, to, true).values());
    }

    public List<AbstractElement> getRange(Range<String> range) {
        Range<Identifier> parsedRange = new Range<>(
                Identifier.fromString(range.from, null),
                Identifier.fromString(range.to, null));
        return getRange(parsedRange.from, parsedRange.to);
    }
}