package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.List;

public class Section extends TitledElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Section;
    }

    public Section(String identifier, String title, List<AbstractElement>
            children) {
        super(identifier, title, children);
    }

    @Override
    protected String headerToString() {
        return String.format("DZIAŁ %s\n%s\n", identifier, title);
    }
}
