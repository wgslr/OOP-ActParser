package agh.cs.actparser;

import agh.cs.actparser.elements.AbstractElement;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

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
            throw new IllegalArgumentException("There is no such element!");
        }

        return new ArrayList<>(elements.subMap(from, true, to, true).values());
    }

    public List<AbstractElement> getRange(Range<Identifier> range) {
        return getRange(range.from, range.to);
    }
}
