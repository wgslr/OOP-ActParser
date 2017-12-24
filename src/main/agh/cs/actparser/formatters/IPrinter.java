package agh.cs.actparser.formatters;

import agh.cs.actparser.elements.AbstractElement;

import java.util.Collection;
import java.util.Collections;

/**
 * Capability to display document elements tree.
 */
public interface IPrinter {

    /**
     * @param element Single element to display
     */
    default void print(AbstractElement element) {
        print(Collections.singletonList(element));
    }

    /**
     * @param elements Multiple elements to display
     */
    void print(Collection<AbstractElement> elements);

}
