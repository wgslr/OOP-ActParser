package agh.cs.actparser.parsers;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.elements.AbstractElement;
import agh.cs.actparser.elements.Plaintext;

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

    class ElementStructure {
        String identifier;
        List<String> bodyLines;

        public ElementStructure(String identifier, List<String> lines) {
            this.identifier = identifier;
            this.bodyLines = lines;
        }
    }


    /**
     * Parsers used to find subelements of this element.
     */
    protected final List<AbstractParser> childrenParsers;

    protected final Pattern startPattern;
    protected final Predicate<String> startPredicate;

    protected final List<String> lines;

    /**
     * @return Regular expression containing two capture groups - for element
     * identifier and rest of the line
     */
    protected abstract String getStartPattern();

    /**
     * Creates document element found by this parser
     *
     * @param identifier Identifier extracted from element header
     * @param children
     * @return Document element
     */
    protected abstract AbstractElement createElement(String identifier,
                                                     List<AbstractElement>
                                                             children);

    public abstract AbstractElement makeElement();

    public AbstractParser(List<String> lines) {
        this.lines = lines;
    }


    public AbstractParser(List<AbstractParser> childrenParsers) {
        this.childrenParsers = childrenParsers;
        this.startPattern = Pattern.compile(getStartPattern());
        this.startPredicate = startPattern.asPredicate();
    }

    public List<AbstractElement> parse(List<String> lines) {
        List<Range> childrenRanges = getPartsIndices(lines);

        List<String> plaintextLines;
        if(childrenRanges.isEmpty()){
            plaintextLines = lines;
        } else {
            plaintextLines = lines.subList(0, childrenRanges.get(0).from);
        }

        List<AbstractElement> elements = childrenRanges.stream()
                .map(r -> new ArrayList<String>(lines.subList(r.from, r.to)))
                .filter(list -> !list.isEmpty())
                .map(this::handleMatch)
                .collect(Collectors.toList());

        if (!plaintextLines.isEmpty()) {
            Plaintext plaintext = new Plaintext(joinLines(plaintextLines));
            elements.add(0, plaintext);
        }

        return elements;
    }

    protected List<Range> getPartsIndices(List<String> lines) {
        List<Range> ranges = new ArrayList<>();
        for(int i = 0; i < lines.size(); ++i){
            String line = lines.get(i);
            if (startPattern.asPredicate().test(line)) {
                if (!ranges.isEmpty()) {
                    ranges.get(ranges.size() - 1).to = i;
                }
                ranges.add(new Range(i, lines.size()));
            }
        }
        return ranges;
    }

    protected AbstractElement handleMatch(List<String> lines) {
        ElementStructure st = parseStructure(lines);
        List<AbstractElement> children = parseChildren(st.bodyLines);

        return createElement(st.identifier, children);
    }

    protected ElementStructure parseStructure(List<String> lines) {
        Matcher startMatcher = startPattern.matcher(lines.get(0));
        startMatcher.find();


        String identifier = startMatcher.group(1);
        String bodyInFirstLine = startMatcher.group(2);

        if (!bodyInFirstLine.trim().isEmpty()) {
            lines.set(0, bodyInFirstLine);
        } else {
            lines.remove(0);
        }
        return new ElementStructure(identifier, lines);
    }

    protected List<AbstractElement> parseChildren(List<String> lines) {
        List<AbstractElement> children = new ArrayList<>();
        for (AbstractParser parser : childrenParsers) {
            children = parser.parse(lines);

            // TODO NIE TAK - PLAINTEXT NIE WYSTARCZA
            if (areChildrenNontrivial(children)) {
                break;
            }
            else {
                System.out.println("No children found by " + parser);
            }
        }
        return children;
    }

    private boolean areChildrenNontrivial(List<AbstractElement> children) {
        return (children.size() != 0)
                &&
                (children.stream().anyMatch(c -> c.getKind() != ElementKind.Plaintext));
    }



    protected String joinLines(List<String> lines) {
        return lines.stream().collect(Collectors.joining(" "));
    }

}
