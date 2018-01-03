package agh.cs.actparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles cleaning file lines before proper parsing.
 */
public class Preprocessor {
    private final List<Predicate<String>> patternsToRemovePredicates;

    Preprocessor() {
        this.patternsToRemovePredicates = Stream.of(
                "\\d{4}-\\d{2}-\\d{2}", // date
                ".*\\(uchylony\\).*$",
                ".*pominięt[ey].*$",
                "^.$",
                "©Kancelaria Sejmu"
        )
                .map((x) -> Pattern.compile(x).asPredicate())
                .collect(Collectors.toList());
    }

    public Preprocessor(String[] patternsToRemove) {
        this.patternsToRemovePredicates = Arrays.stream(patternsToRemove)
                .map(x -> Pattern.compile(x).asPredicate())
                .collect(Collectors.toList());
    }

    /**
     * Filters and (if possible) concatenates lines.
     *
     * @param lines
     * @return Processed lines
     */
    public List<String> process(List<String> lines) {
        return joinLines(filter(lines));
    }

    /**
     * Concatenates lines when they do not introduce new element
     */
    protected List<String> joinLines(List<String> lines) {
        List<Predicate<String>> headerPredicates =
                // Plaintext should not prevent joining lines
                ElementKind.Plaintext.getLessSpecific().stream()
                        .map(ElementKind::getRegexp)
                        .map(Pattern::compile)
                        .map(Pattern::asPredicate)
                        .collect(Collectors.toList());

        // Predicates which force adding **next** line to the current
        List<Predicate<String>> forwardConsumingPredicates =
                Stream.of(ElementKind.Chapter,
                        ElementKind.Section)
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

                // Unfortunate necessity for detecting chapter titles
                if (forwardConsumingPredicates.stream()
                        .anyMatch(pred -> pred.test(line))) {

                    result.set(result.size() - 1,
                            joinLines(line, lines.get(i + 1)));
                    ++i;
                }
            } else {
                String previous = result.get(result.size() - 1);
                result.set(result.size() - 1, joinLines(previous, line));
            }
        }
        return result;
    }

    private String joinLines(String prev, String next) {
        String result = prev + "\n" + next;

        result = result.replace("-\n", "");
        result = result.replace("\n", " ");
        return result;
    }


    /**
     * Removes lines matching patterns given in the constructor.
     */
    protected List<String> filter(List<String> Lines) {
        List<Predicate<String>> predicates = patternsToRemovePredicates;
        return Lines.stream()
                .filter(x -> predicates.stream().noneMatch(
                        pred -> pred.test(x)))
                .collect(Collectors.toList());
    }

}
