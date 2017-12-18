package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Plaintext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ElementFinder {
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

    List<String> lines;
    ElementKind currentLevel;
    IParserFactory parserFactory = new ParserFactory();

    public ElementFinder(List<String> lines, ElementKind currentLevel) {
        this.lines = lines;
        this.currentLevel = currentLevel;
    }

    List<AbstractElement> getElements() {
        // Find first kind matching any elements
        List<Range> parts = new ArrayList<>();
        ElementKind childrenKind = ElementKind.Plaintext;
        for (ElementKind kind : ElementKind.getMoreSpecificThan(currentLevel)) {
            Predicate<String> predicate = kindToPattern(kind).asPredicate();
            parts = getPartsIndices(predicate);
            if (parts.size() > 0) {
                childrenKind = kind;
                break;
            }
        }

        List<AbstractElement> result = parseChildren(childrenKind, parts);

        List<String> plaintextLines = getPlaintextLines(parts);
        if (!plaintextLines.isEmpty()) {
            result.add(new Plaintext(plaintextLines));
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
        String regex;
        switch (kind) {
            case Section:
                regex = "^DZIAŁ ([IVXCD]+)(.*)";
                break;
            case Article:
                regex = "^Art\\. (\\d+)\\.";
                break;
            case Paragraph:
                regex = "^(\\d+)\\.\\s";
                break;
            case Point:
                regex = "^(\\d+)\\)\\s";
                break;
            case Letter:
                regex = "^(\\p{L}+)\\)\\s";
                break;
            case Indent:
                regex = "^-\\s";
                break;
            case Plaintext:
                regex = "(?!)"; // never matches
                break;
            default:
                // matches also Document as there cannot
                // be more than one Document in a file
                throw new IllegalArgumentException();
        }
        return Pattern.compile(regex);
    }
}
