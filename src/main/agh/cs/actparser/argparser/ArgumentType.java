package agh.cs.actparser.argparser;

import agh.cs.actparser.Identifier;
import agh.cs.actparser.Range;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ArgumentType {
    Number,
    IdentifierRange,
    Text,
    Bool;

    public Function<String, Object> getParser() {
        switch (this) {
            case Number:
                return Integer::parseInt;
            case IdentifierRange:
                return this::RangeParser;
            case Text:
                return (x -> x);
            case Bool:
                return (x -> true);
            default:
                throw new InternalError("Invalid enum value");
        }
    }

    private Object RangeParser(String arg) {
        Pattern pattern = Pattern.compile("(\\p{L}+)..(\\p{L}+)");
        Matcher m = pattern.matcher(arg);
        if (m.matches()) {
            return new Range<String>(m.group(1), m.group(2));
//            return new Range<String>(
//                    Identifier.fromString(m.group(1)),
//                    Identifier.fromString(m.group(2)));
        } else {
            // single identifier
            return new Range(arg, arg);
        }
    }

}
