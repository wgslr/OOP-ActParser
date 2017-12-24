package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;

public class Title extends TitledElement {
    public Title(String identifier, String title, LinkedHashMap<Identifier, AbstractElement> children) {
        super(identifier, title, children);
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.Title;
    }

    @Override
    public String toString() {
        return title;
    }
}
