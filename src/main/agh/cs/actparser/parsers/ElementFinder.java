package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.IElementRegistry;
import agh.cs.actparser.Range;
import agh.cs.actparser.elements.AbstractElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Handles identifying and parsing document elements.
 */
public class ElementFinder {
    class RangeToParse extends Range<Integer> {
        /**
         * Kind of element to be created from this lines range.
         */
        public final ElementKind kind;

        public RangeToParse(int from, int to, ElementKind kind) {
            super(from, to);
            this.kind = kind;
        }

        public List<String> makeSublist(List<String> elements) {
            return elements.subList(from, to);
        }

        @Override
        public String toString() {
            return "RangeToParse{from=" + from + ", to=" + to + '}';
        }
    }

    /**
     * Lines of text being parsed
     */
    List<String> lines;

    /**
     * Kind of parent element to all found elements.
     */
    ElementKind currentLevel;

    /**
     * Registries to notify about created elements.
     */
    List<IElementRegistry> registries;

    public ElementFinder(List<String> lines, ElementKind currentLevel) {
        this(lines, currentLevel, Collections.emptyList());
    }

    public ElementFinder(List<String> lines, ElementKind currentLevel, List
            <IElementRegistry> registries) {
        this.lines = lines;
        this.currentLevel = currentLevel;
        this.registries = registries;
    }

    public ElementFinder(List<String> lines, ElementKind currentLevel,
            List<IElementRegistry> registries,
            IParserFactory parserFactory) {
        this.lines = lines;
        this.currentLevel = currentLevel;
        this.registries = registries;
    }


    /**
     * Creates elements found in given lines.
     *
     * @return List of created elements in order of their presence in the
     * document.
     */
    public List<AbstractElement> makeElements() {
        List<RangeToParse> childrenRanges = getChildrenRanges();
        return parseRanges(childrenRanges);
    }

    /**
     * @return List of line ranges delimited by element headers.
     */
    private List<RangeToParse> getChildrenRanges() {
        List<RangeToParse> ranges = new ArrayList<>();
        // Kinds of element to be found
        List<ElementKind> availableKinds = currentLevel.getMoreSpecific();
        int firstHeaderLine = lines.size();

        // Look for elements in order of broader to narrower specificity
        // in order not to break the tree structure
        for (ElementKind kindToFind : availableKinds) {
            if (firstHeaderLine == 0) {
                break; // no lines left to aprse
            }
            List<String> linesToParse = lines.subList(0, firstHeaderLine);

            ranges.addAll(0, getPartsIndices(linesToParse, kindToFind));

            if (!ranges.isEmpty()) {
                firstHeaderLine = ranges.get(0).from;
            }
        }
        return ranges;
    }

    /**
     * Splits input on lines matching the kind's predicate
     */
    private List<RangeToParse> getPartsIndices(List<String> linesToParse,
            ElementKind kindToFind) {
        Predicate<String> predicate = kindToPredicate(kindToFind);
        List<RangeToParse> ranges = new ArrayList<>();
        Integer previous = null;

        for (int i = 0; i < linesToParse.size(); ++i) {
            String line = linesToParse.get(i);
            if (predicate.test(line)) {
                if (previous != null) {
                    ranges.add(new RangeToParse(previous, i, kindToFind));
                }
                previous = i;
            }
        }
        if (previous != null) {
            // last element extends to the last parsed lines
            ranges.add(new RangeToParse(previous, linesToParse.size(),
                    kindToFind));
        }
        return ranges;
    }

    /**
     * Create elements basing on identified ranges.
     *
     * @param childrenRanges Ranges from which elements can be created
     * @return Created elements
     */
    private List<AbstractElement> parseRanges(List<RangeToParse>
            childrenRanges) {
        return childrenRanges.stream()
                .map(range -> new ElementParser(
                        range.kind, range.makeSublist(lines), registries))
                .map(ElementParser::makeElement)
                .map(this::registerElement)
                .collect(Collectors.toList());
    }

    private AbstractElement registerElement(AbstractElement element) {
        for (IElementRegistry registry : registries) {
            registry.add(element);
        }
        return element;
    }


    private Predicate<String> kindToPredicate(ElementKind kind) {
        return Pattern.compile(kind.getRegexp()).asPredicate();
    }
}
