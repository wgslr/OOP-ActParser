package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;

import java.util.ArrayList;
import java.util.Collections;
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
        // Descend by element kind specificty trying to parse with lower
        // kind what was left by highers

        List<Range> parts = new ArrayList<>();
        List<ElementKind> availableKinds = currentLevel.getMoreSpecific();
        List<String> linesToParse = lines;
        List<AbstractElement> result = new ArrayList<>();


        for (int i = 0; i < availableKinds.size() && !linesToParse.isEmpty(); ++i) {
            ElementKind searchedKind = availableKinds.get(i);
            Predicate<String> predicate = kindToPredicate(searchedKind);
            parts = getPartsIndices(linesToParse, predicate);

            result.addAll(0, parseChildren(searchedKind, parts));
            linesToParse = getUnmatchedLines(linesToParse, parts);
        }

        if (!linesToParse.isEmpty()) {
            AbstractParser leafParser = parserFactory.makeLeafParser(linesToParse);
            result.add(0, leafParser.makeElement());
        }

        return result;
    }

    private List<String> getUnmatchedLines(List<String> linesToParse, List<Range> identifiedRanges) {
        if (identifiedRanges.isEmpty()) {
            return linesToParse;
        } else {
            return linesToParse.subList(0, identifiedRanges.get(0).from);
        }
    }

    private List<AbstractElement> parseChildren(ElementKind childrenKind,
                                                List<Range> childrenRanges) {
        return childrenRanges.stream()
                .map(range -> lines.subList(range.from, range.to))
                .map(sublist -> parserFactory.makeParser(childrenKind, sublist))
                .map(AbstractParser::makeElement)
                .collect(Collectors.toList());
    }

    /**
     * @param ranges Ranges identified as containing children elements
     * @return
     */
    private List<String> getLeafLines(List<Range> ranges) {
        if (ranges.isEmpty()) {
            return lines;
        } else {
            return lines.subList(0, ranges.get(0).from);
        }
    }

    /**
     * Splits input on lines matching the predicate
     */
    private List<Range> getPartsIndices(List<String> linesToParse, Predicate<String> predicate) {
        List<Range> ranges = new ArrayList<>();
        Integer previous = null;

        for (int i = 0; i < linesToParse.size(); ++i) {
            String line = linesToParse.get(i);
            if (predicate.test(line)) {
                if (previous != null) {
                    ranges.add(new Range(previous, i));
                }
                previous = i;
            }
        }
        if (previous != null) {
            ranges.add(new Range(previous, linesToParse.size()));
        }
        return ranges;
    }


    private Predicate<String> kindToPredicate(ElementKind kind) {
        return Pattern.compile(kind.getRegexp()).asPredicate();
    }
}
