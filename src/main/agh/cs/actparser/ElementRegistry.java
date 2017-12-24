package agh.cs.actparser;

import agh.cs.actparser.elements.AbstractElement;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Registry storing only elements of given kind.
 * Allows querying ranges of elements.
 */
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
     * Returns elements matching given range, inclusively.
     * Elements given as boundaries must exist.
     *
     * @param from Identifier of first element to be returend
     * @param to Identifier of last element to be returend
     * @return Matching elements
     * @throws IllegalArgumentException if starting or ending element cannot
     *                                  be found
     */
    public List<AbstractElement> getRange(Identifier from, Identifier to) {
        if (!(elements.containsKey(from) && elements.containsKey(to))) {
            throw new IllegalArgumentException("There is no such element!");
        }

        return new ArrayList<>(elements.subMap(from, true, to, true).values());
    }

    /**
     * Returns elements matching given range, inclusively.
     * Elements given as boundaries must exist.
     *
     * @param range Range describing elements range to return
     * @return Matching elements
     * @throws IllegalArgumentException if starting or ending element cannot
     *                                  be found
     */
    public List<AbstractElement> getRange(Range<Identifier> range) {
        return getRange(range.from, range.to);
    }
}
