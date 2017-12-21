package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class AbstractElement {
    public final String idString;
    public final Identifier identifier;
    protected final LinkedHashMap<Identifier, AbstractElement> children;

    abstract public ElementKind getKind();

    public AbstractElement(String idString,
                           LinkedHashMap<Identifier, AbstractElement>
                                   children) {
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

    public AbstractElement getDescendant(List<Identifier> location) {
        if (location.isEmpty()) {
            throw new IllegalArgumentException("Location specification must " +
                    "not be empty");
        }
        Identifier needle = location.get(0);
        AbstractElement child = children.get(needle);
        if (child == null) {
            throw new IllegalArgumentException(String.format(
                    "There is no %s in %s", needle, identifier));
        }

        if(location.size() > 1)
            return child.getDescendant(location.subList(1, location.size()));
        else {
            return child;
        }
    }


    protected String headerToString() {
        return "";
    }
}
