package agh.cs.actparser;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Cleaner {
    private final String[] patternsToRemove;

    Cleaner() {
        this.patternsToRemove = new String[]{
                "\\d{4}-\\d{2}-\\d{2}",
                ".*\\(uchylony\\).*$",
                ".*pominięt[ey].*$",
                "^?$"
        };
    }

    public Cleaner(String[] patternsToRemove) {
        this.patternsToRemove = patternsToRemove;
    }


    /**
     * Removes lines matched by the given patterns from provider list.
     */
    public List<String> filter(List<String> Lines) {
        List<Predicate<String>> predicates = getPredicates();
        return Lines.stream()
                .filter(x -> predicates.stream().noneMatch(
                        pred -> pred.test(x)))
                .collect(Collectors.toList());
    }

    private List<Predicate<String>> getPredicates() {
        return Arrays.stream(patternsToRemove)
                .map((x) -> Pattern.compile(x).asPredicate())
                .collect(Collectors.toList());
    }

}
