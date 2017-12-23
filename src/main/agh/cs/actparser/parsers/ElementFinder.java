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

public class ElementFinder {
    /**
     * Represents a range to be parsed for an element child.
     * Identified child type is stored in `kind`.
     */
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
     * Kind of elements for which children the finder is looking.
     */
    ElementKind currentLevel;

    List<IElementRegistry> registries;

    /**
     * Supplier of children parsers.
     */
    IParserFactory parserFactory = new ParserFactory();

    public ElementFinder(List<String> lines, ElementKind currentLevel){
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
        this.parserFactory = parserFactory;
        this.registries = registries;
    }

    public List<AbstractElement> makeChildrenElements() {
        List<RangeToParse> childrenRanges = getChildrenRanges();
        return parseChildren(childrenRanges);
    }

    private List<RangeToParse> getChildrenRanges() {
        List<RangeToParse> ranges = new ArrayList<>();
        int firstChildLine = lines.size();
        List<ElementKind> availableKinds = currentLevel.getMoreSpecific();

        for (int i = 0; i < availableKinds.size() && firstChildLine != 0; ++i) {
            ElementKind kindToFind = availableKinds.get(i);
            ranges.addAll(0,
                    getPartsIndices(lines.subList(0, firstChildLine),
                            kindToFind));

            if (!ranges.isEmpty()) {
                firstChildLine = ranges.get(0).from;
            }
        }
        if (firstChildLine != 0) {
            ranges.add(new RangeToParse(0, firstChildLine, ElementKind.Plaintext));
        }
        return ranges;
    }

    private List<AbstractElement> parseChildren(List<RangeToParse> childrenRanges) {
        return childrenRanges.stream()
                .map(range -> parserFactory.makeParser(range.kind,
                        range.makeSublist(lines), registries))
                .map(AbstractParser::makeElement)
                .map(this::registerElement)
                .collect(Collectors.toList());
    }

    private AbstractElement registerElement(AbstractElement element) {
        for (IElementRegistry registry : registries) {
            registry.add(element);
        }
        return element;
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
            ranges.add(new RangeToParse(previous, linesToParse.size(), kindToFind));
        }
        return ranges;
    }


    private Predicate<String> kindToPredicate(ElementKind kind) {
        return Pattern.compile(kind.getRegexp()).asPredicate();
    }
}
