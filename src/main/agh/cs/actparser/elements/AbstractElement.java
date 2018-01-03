package agh.cs.actparser.elements;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Document element. Described by an identifier and containing child elements.
 */
public abstract class AbstractElement {
    /**
     * Whole string containing element identifier.
     */
    public final String idString;

    /**
     * Textual content of the element. Usually its title.
     */
    public final String content;

    /**
     * Parsed identifier based on {@link #idString}
     */
    public final Identifier identifier;

    protected LinkedHashMap<Identifier, AbstractElement> children;

    abstract public ElementKind getKind();

    public AbstractElement(String idString, String content,
            LinkedHashMap<Identifier, AbstractElement> children) {
        this.idString = idString;
        this.content = content;
        this.children = children;
        this.identifier = Identifier.fromString(idString, getKind());
    }

    /**
     * Locates a descendant element. If given location is empty - this element.
     *
     * @param location Description of element location, in order of
     *                 increasing element specificity
     * @return Found element
     * @throws IllegalArgumentException If looked up element does not exist
     */
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

    public Collection<AbstractElement> getChildren() {
        return children.values();
    }

    @Override
    public String toString() {
        return "";
    }

    /**
     * @return True if this element's title should be on the same line as
     * header
     */
    public boolean isHeaderInline() {
        return true;
    }

}
