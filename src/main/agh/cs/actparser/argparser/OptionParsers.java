package agh.cs.actparser.argparser;

import agh.cs.actparser.ElementKind;
import agh.cs.actparser.Identifier;
import agh.cs.actparser.Range;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains several functions providing parsers for command line
 * options.
 */
public class OptionParsers {

    static public Function<String, Identifier>
    getIdentifierParser(ElementKind kind) {
        return (arg -> Identifier.fromString(arg, kind));
    }

    static public Function<String, Range<Identifier>>
    getIdentifierRangeParser(ElementKind kind) {
        Pattern pattern = Pattern.compile("([\\p{L}\\d]+)\\.\\.([\\p{L}\\d]+)");

        Function<String, Identifier> singleParser = getIdentifierParser(kind);

        return arg -> {
            Matcher m = pattern.matcher(arg);
            if (m.matches()) {
                return new Range<>(
                        singleParser.apply(m.group(1)),
                        singleParser.apply(m.group(2))
                );
            } else {
                // single identifier
                Identifier identifier = singleParser.apply(arg);
                return new Range<>(identifier, identifier);
            }
        };
    }

    static public Function<String, String> getTextParser() {
        return Function.identity();
    }

}
