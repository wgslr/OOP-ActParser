package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.List;

public class Section extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Section;
    }

    public Section(String identifier, List<AbstractElement> children) {
        super(identifier, children);
    }
}
