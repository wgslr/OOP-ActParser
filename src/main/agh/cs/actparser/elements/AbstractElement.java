package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedHashMap;
import java.util.List;

public abstract class AbstractElement {
    public final String idString;
    public final Identifier identifier;
    protected final LinkedHashMap<Identifier, AbstractElement> children;

    abstract public ElementKind getKind();

    public AbstractElement(String idString,
                           LinkedHashMap<Identifier, AbstractElement> children) {
        this.idString = idString;
        this.children = children;
        this.identifier = Identifier.fromString(idString, getKind());
    }

    public String toString() {
        return identifier.toString() + headerToString() +
                children.values().stream()
                        .map(AbstractElement::toString)
                        .reduce(String::concat)
                        .orElse("");
    }

    public String getDescendant(List<Identifier> location) {
        if(location.isEmpty()) {
            throw new IllegalArgumentException("Descendant specification is " +
                    "empty!");
        }
        throw new NotImplementedException();
    }


    protected String headerToString() {
        return "";
    }
}
