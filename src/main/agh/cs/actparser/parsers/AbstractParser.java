package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.IElementRegistry;
import agh.cs.actparser.Identifier;
import agh.cs.actparser.elements.AbstractElement;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles parsing of a single document element
 * and invokes parsing of its children.
 */
public abstract class AbstractParser {

    protected String idString;

    /**
     * Element's content with idString dropped
     */
    protected List<String> bodyLines;

    /**
     * Registries to notify when parsing children
     */
    protected List<IElementRegistry> registries;

    protected LinkedHashMap<Identifier, AbstractElement>
            childrenElements = new LinkedHashMap<>();

    public AbstractParser(List<String> linesToParse,
                          List<IElementRegistry> registries) {
        this.registries = registries;
        parseStructure(linesToParse);
        parseChildren(bodyLines);
    }

    /**
     * @return Kind od parsed element
     */
    protected abstract ElementKind getKind();

    /**
     * Create Element object for given content.
     *
     * @return Created Element instance.
     */
    public abstract AbstractElement makeElement();

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
        if (startMatcher.groupCount() < 2) {
            throw new IllegalArgumentException(
                    "Given content cannot be parsed as " + getKind());
        }

        idString = startMatcher.group(1);
        String bodyInFirstLine = startMatcher.group(2);

        if (!bodyInFirstLine.trim().isEmpty()) {
            linesToParse.set(0, bodyInFirstLine);
            bodyLines = linesToParse;
        } else {
            bodyLines = linesToParse.subList(1, linesToParse.size());
        }
    }

    protected void parseChildren(List<String> bodyLines) {
        ElementFinder finder = new ElementFinder(bodyLines, getKind(),
                registries);
        List<AbstractElement> children = finder.makeElements();

        children.forEach(
                child -> childrenElements.put(child.identifier, child));
    }


    /**
     * @return Regular expression containing two capture groups - for element
     * idString and rest of the line
     */
    protected Pattern getStartPattern() {
        return Pattern.compile(getKind().getRegexp());
    }


}
