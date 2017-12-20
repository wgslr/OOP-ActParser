package agh.cs.actparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Preprocessor {
    private final String[] patternsToRemove;

    Preprocessor() {
        this.patternsToRemove = new String[]{
                "\\d{4}-\\d{2}-\\d{2}",
                ".*\\(uchylony\\).*$",
                ".*pominięt[ey].*$",
                "^.$",
                "©Kancelaria Sejmu",
        };
    }

    public Preprocessor(String[] patternsToRemove) {
        this.patternsToRemove = patternsToRemove;
    }

    public List<String> process(List<String> lines) {
        List<String> filtered = filter(lines);
        return joinLines(filtered);
    }

    /**
     * Join lines when then do not introduce new element
     */
    protected List<String> joinLines(List<String> lines) {
        List<Predicate<String>> headerPredicates =
                ElementKind.Plaintext.getLessSpecific().stream()
                        .map(ElementKind::getRegexp)
                        .map(Pattern::compile)
                        .map(Pattern::asPredicate)
                        .collect(Collectors.toList());

        List<String> result = new ArrayList<>();
        result.add(lines.get(0));

        for (int i = 1; i < lines.size(); ++i) {
            String line = lines.get(i);
            if (headerPredicates.stream()
                    .anyMatch(pred -> pred.test(line))) {
                // Line begins new element
                result.add(line);
            } else {
                String previous = result.get(result.size() - 1);
                previous += "\n" + line;

                // Remove hyphenation
                previous = previous.replace("-\n", "");
                previous = previous.replace("\n", " ");

                result.set(result.size() - 1, previous);
            }
        }
        return result;
    }


    /**
     * Removes lines matched by the given patterns from provider list.
     */
    protected List<String> filter(List<String> Lines) {
        List<Predicate<String>> predicates = getPredicates();
        return Lines.stream()
                .filter(x -> predicates.stream().noneMatch(
                        pred -> pred.test(x)))
                .collect(Collectors.toList());
    }

    protected List<Predicate<String>> getPredicates() {
        return Arrays.stream(patternsToRemove)
                .map((x) -> Pattern.compile(x).asPredicate())
                .collect(Collectors.toList());
    }

}
