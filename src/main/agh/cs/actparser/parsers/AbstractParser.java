package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractParser {

    protected String identifier;

    /**
     * Element content with identifier dropped
     */
    protected List<String> bodyLines;

    protected List<AbstractElement> childrenElements;

    public AbstractParser(List<String> linesToParse) {
        parseStructure(linesToParse);
        parseChildren(bodyLines);
    }

    protected abstract ElementKind getKind();

    /**
     * Create Element object for given content.
     * @return Created Element instance.
     */
    public abstract AbstractElement makeElement();

    /**
     * Identifies main parts of the parsed element and sets them in the
     * parser's fields.
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

        identifier = startMatcher.group(1);
        String bodyInFirstLine = startMatcher.group(2);

        if (!bodyInFirstLine.trim().isEmpty()) {
            linesToParse.set(0, bodyInFirstLine);
            bodyLines = linesToParse;
        } else {
            bodyLines = linesToParse.subList(1, linesToParse.size());
        }
    }

    protected void parseChildren(List<String> bodyLines) {
        ElementFinder finder = new ElementFinder(bodyLines, getKind());
        childrenElements = finder.makeChildrenElements();
    }


    /**
     * @return Regular expression containing two capture groups - for element
     * identifier and rest of the line
     */
    protected Pattern getStartPattern() {
        return Pattern.compile(getKind().getRegexp());
    }


}
