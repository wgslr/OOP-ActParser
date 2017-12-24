package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.IElementRegistry;
import agh.cs.actparser.Identifier;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.ElementFactory;
import agh.cs.actparser.elements.IElementFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles parsing of a single document element
 * and invokes parsing of its children.
 */
public class ElementParser {

    /**
     * Kind of parsed element
     */
    protected final ElementKind kind;

    protected String idString;
    protected String title;

    /**
     * Element's content with idString and title dropped
     */
    protected List<String> bodyLines;

    /**
     * Registries to notify when parsing children
     */
    protected List<IElementRegistry> registries;

    protected LinkedHashMap<Identifier, AbstractElement> childrenElements =
            new LinkedHashMap<>();

    protected IElementFactory elementFactory = new ElementFactory();

    public ElementParser(
            ElementKind kind,
            List<String> linesToParse,
            List<IElementRegistry> registries) {
        this.kind = kind;
        this.registries = registries;

        parseStructure(linesToParse);
        parseChildren(bodyLines);
    }

    /**
     * Create Element object for given title.
     *
     * @return Created Element instance.
     */
    public AbstractElement makeElement() {
        return elementFactory.makeElement(kind, idString, title,
                childrenElements);
    }

    /**
     * Identifies main parts of the parsed element and sets them in the
     * parser's fields.
     *
     * @param linesToParse
     */
    protected void parseStructure(List<String> linesToParse) {
        Matcher startMatcher = getStartPattern().matcher(linesToParse.get(0));

        // locate capture groups
        startMatcher.find();

        idString = startMatcher.group(1);
        title = startMatcher.group(2);
        String bodyInFirstLine = startMatcher.group(3);

        if (bodyInFirstLine.trim().isEmpty()) {
            bodyLines = linesToParse.subList(1, linesToParse.size());
        } else {
            // pass leftover text to children parsers
            linesToParse.set(0, bodyInFirstLine);
            bodyLines = linesToParse;
        }
    }

    protected void parseChildren(List<String> bodyLines) {
        ElementFinder finder =
                new ElementFinder(bodyLines, kind, registries);
        List<AbstractElement> children = finder.makeElements();

        children.forEach(
                child -> childrenElements.put(child.identifier, child));
    }


    /**
     * @return Regular expression containing two capture groups - for element
     * idString and rest of the line
     */
    protected Pattern getStartPattern() {
        return Pattern.compile(kind.getRegexp());
    }


}
