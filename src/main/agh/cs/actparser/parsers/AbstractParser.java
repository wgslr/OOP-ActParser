package agh.cs.actparser.parsers;

import agh.cs.actparser.elements.AbstractElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
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
            return "Range{" +
                    "from=" + from +
                    ", to=" + to +
                    '}';
        }
    }

    protected final Predicate<String> startPredicate;

    protected abstract String getStartPattern();

    protected abstract AbstractElement createElement(List<String> linesPart);


    public AbstractParser() {
        this.startPredicate = Pattern.compile(
                this.getStartPattern()).asPredicate();
    }

    public List<AbstractElement> parse(List<String> lines) {
        return getPartsIndices(lines).stream()
                .map(r -> lines.subList(r.from, r.to))
                .map(this::createElement)
                .collect(Collectors.toList());
    }


    private List<Range> getPartsIndices(List<String> lines) {
        List<Range> ranges = new ArrayList<>();
        int i = 0;
        for (String line : lines) {
            if (startPredicate.test(line)) {
                if (!ranges.isEmpty()) {
                    ranges.get(ranges.size() - 1).to = i;
                }
                ranges.add(new Range(i, lines.size()));
            }
            ++i;
        }
        return ranges;
    }

    protected String joinLines(List<String> lines) {
        return lines.stream()
                .collect(Collectors.joining(" "));
    }

}
