package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;

public class Section extends TitledElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Section;
    }

    public Section(String identifier, String title,
                   LinkedHashMap<Identifier, AbstractElement> children) {
        super(identifier, title, children);
    }

    @Override
    public String toString() {
        return String.format("DZIAŁ %s %s", idString, title);
    }
}
