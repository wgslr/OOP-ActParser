package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.AbstractElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class AbstractParser {

    class Range {
        public int from;
        public int to;

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
     * Parsers used to find subelements of this element.
     */
    protected final List<AbstractParser> childrenParsers;

    protected final Pattern startPattern;
    protected final Predicate<String> startPredicate;

    /**
     * @return Regular expression containing two capture groups - for element
     * identifier and rest of the line
     */
    protected abstract String getStartPattern();

    /**
     * Creates document element found by this parser
     *
     * @param identifier Identifier extracted from element header
     * @param bodyLines  Lines of the element's body
     * @return Document element
     */
    protected abstract AbstractElement createElement(String identifier,
                                                     List<String> bodyLines);


    public AbstractParser(List<AbstractParser> childrenParsers) {
        this.childrenParsers = childrenParsers;
        this.startPattern = Pattern.compile(getStartPattern());
        this.startPredicate = startPattern.asPredicate();
    }

    public List<AbstractElement> parse(List<String> lines) {
        return getPartsIndices(lines).stream()
                .map(r -> lines.subList(r.from, r.to))
                .map(this::handleMatch)
                .collect(Collectors.toList());
    }


    private List<Range> getPartsIndices(List<String> lines) {
        List<Range> ranges = new ArrayList<>();
        int i = 0;
        for (String line : lines) {
            if (startPattern.asPredicate().test(line)) {
                if (!ranges.isEmpty()) {
                    ranges.get(ranges.size() - 1).to = i;
                }
                ranges.add(new Range(i, lines.size()));
            }
            ++i;
        }
        return ranges;
    }

    protected AbstractElement handleMatch(List<String> lines) {
        Matcher startMatcher = startPattern.matcher(lines.get(0));
        startMatcher.matches();
        String identifier = startMatcher.group(1);
        String bodyInFirstLine = startMatcher.group(2);

        if (!bodyInFirstLine.trim().isEmpty()) {
            lines.set(0, bodyInFirstLine);
        } else {
            lines.remove(0);
        }

        return createElement(identifier, lines);
    }

    protected String joinLines(List<String> lines) {
        return lines.stream().collect(Collectors.joining(" "));
    }

}
