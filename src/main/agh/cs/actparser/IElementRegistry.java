package agh.cs.actparser;

import agh.cs.actparser.elements.AbstractElement;

public interface IElementRegistry {
    /**
     * Adds element to registry.
     */
    void Add(AbstractElement element);
}
