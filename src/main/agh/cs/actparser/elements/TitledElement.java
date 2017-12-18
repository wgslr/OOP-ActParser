package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.List;

/**
 * Represents document element having a distinct title.
 */
public abstract class TitledElement extends AbstractElement {
    String title;

    public TitledElement(String identifier, String title, List<AbstractElement>
            children) {
        super(identifier, children);
        this.title = title;
    }
}