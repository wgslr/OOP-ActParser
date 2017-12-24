package agh.cs.actparser.elements;

import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;

/**
 * Represents document element having a distinct title.
 */
public abstract class TitledElement extends AbstractElement {

    public TitledElement(String identifier, String title,
            LinkedHashMap<Identifier, AbstractElement> children) {
        super(identifier, title, children);
    }

    @Override
    public boolean isHeaderInline() {
        return false;
    }
}
