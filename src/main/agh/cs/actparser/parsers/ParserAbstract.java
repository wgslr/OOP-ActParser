package agh.cs.actparser.parsers;

import javax.xml.bind.Element;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParserAbstract {

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

    public final String startPattern = "Art";
    private final Predicate<String> startPredicate;

    public ParserAbstract() {
        this.startPredicate = Pattern.compile(startPattern).asPredicate();
    }


    public List<Range> getPartsIndices(List<String> lines) {
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

    public List<List<String>> splitLines(List<String> lines) {
        return getPartsIndices(lines).stream()
                .map(r -> lines.subList(r.from, r.to))
                .collect(Collectors.toList());
    }

}
