package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Document;
import agh.cs.actparser.elements.Plaintext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ElementFinder {
    /**
     * Represents a range of indices.
     */
    class Range {
        public final int from;
        public final int to;

        public Range(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "Range{from=" + from + ", to=" + to + '}';
        }
    }

    /**
     * Lines of document on which finder operates.
     */
    List<String> lines;

    ElementKind currentLevel;
    IParserFactory parserFactory = new ParserFactory();

    public ElementFinder(List<String> lines, ElementKind currentLevel) {
        this.lines = lines;
        this.currentLevel = currentLevel;
    }

    public ElementFinder(List<String> lines, ElementKind currentLevel,
                         IParserFactory parserFactory) {
        this.lines = lines;
        this.currentLevel = currentLevel;
        this.parserFactory = parserFactory;
    }

    List<AbstractElement> getChildrenElements() {
        // Find most general kind for which children elements can be found

        List<Range> parts = new ArrayList<>();
        List<ElementKind> availableKinds = ElementKind.getMoreSpecificThan
                (currentLevel);
        ElementKind childrenKind = ElementKind.Plaintext;

        for (ElementKind potentialKind : availableKinds) {
            Predicate<String> predicate = kindToPattern(potentialKind).asPredicate();
            parts = getPartsIndices(predicate);
            if (parts.size() > 0) {
                childrenKind = potentialKind;
                break;
            }
        }

        List<AbstractElement> result = parseChildren(childrenKind, parts);

        List<String> plaintextLines = getPlaintextLines(parts);
        if (!plaintextLines.isEmpty()) {
            result.add(0, new Plaintext(plaintextLines));
        }

        return result;
    }

    private List<AbstractElement> parseChildren(ElementKind childrenKind,
                                            List<Range>
            childrenRanges) {
        return childrenRanges.stream()
                .map(range -> lines.subList(range.from, range.to))
                .map(sublist -> parserFactory.makeParser(childrenKind, sublist))
                .map(parser -> parser.makeElement())
                .collect(Collectors.toList());
    }

    /**
     * @param ranges Ranges identified as containing children elements
     * @return
     */
    private List<String> getPlaintextLines(List<Range> ranges) {
        if (ranges.isEmpty()) {
            return lines;
        } else {
            return lines.subList(0, ranges.get(0).from);
        }
    }

    /**
     * Splits input on lines matching the predicate
     */
    private List<Range> getPartsIndices(Predicate<String> predicate) {
        List<Range> ranges = new ArrayList<>();
        Integer previous = null;

        for (int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            if (predicate.test(line)) {
                if (previous != null) {
                    ranges.add(new Range(previous, i));
                }
                previous = i;
            }
        }
        if (previous != null) {
            ranges.add(new Range(previous, lines.size()));
        }
        return ranges;
    }


    private Pattern kindToPattern(ElementKind kind) {
        return Pattern.compile(kind.getRegexp());
    }
}
