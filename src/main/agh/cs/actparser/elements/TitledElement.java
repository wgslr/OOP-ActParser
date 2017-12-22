package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Represents document element having a distinct title.
 */
public abstract class TitledElement extends AbstractElement {
    String title;

    public TitledElement(String identifier, String title,
                         LinkedHashMap<Identifier, AbstractElement>
            children) {
        super(identifier, children);
        this.title = title;
    }

    @Override
    public boolean isHeaderInline() {
        return false;
    }
}
