package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;

import java.util.List;

public class Paragraph extends AbstractElement {
    @Override
    public ElementKind getKind() {
        return ElementKind.Paragraph;
    }

    public Paragraph(String identifier, List<AbstractElement> children) {
        super(identifier, children);
    }


}
