package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.LinkedHashMap;
import java.util.List;

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
        return headerToString();
    }

    public AbstractElement getDescendant(List<Identifier> location) {
        if (location.isEmpty()) {
            return this;
        }
        Identifier needle = location.get(0);
        AbstractElement child = children.get(needle);
        if (child == null) {
            throw new IllegalArgumentException(String.format(
                    "There is no %s in %s", needle, identifier));
        }

        return child.getDescendant(location.subList(1, location.size()));
    }

    public LinkedHashMap<Identifier, AbstractElement> getChildren() {
        //return new LinkedHashMap<>(children);
        return children;
    }


    public String headerToString() {
        return "";
    }

    /**
     * @return True if this element's content should be on the same line as
     * header
     */
    public boolean isHeaderInline() {
        return true;
    };
}
