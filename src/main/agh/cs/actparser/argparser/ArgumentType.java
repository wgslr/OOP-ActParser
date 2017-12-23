package agh.cs.actparser.argparser;

import com.sun.org.apache.xpath.internal.Arg;
import com.sun.org.apache.xpath.internal.operations.Bool;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.InvalidPropertiesFormatException;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ArgumentType {
    Number,
    Range,
    Text,
    Bool;

    public Function<String, Object> getParser() {
        switch (this) {
            case Number:
                return Integer::parseInt;
            case Range:
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
        Pattern pattern = Pattern.compile("(\\d+[a-z]*)..(\\d+[a-z]*)");
        Matcher m = pattern.matcher(arg);
        if (m.matches()) {
            return new agh.cs.actparser.Range<String>(m.group(1), m.group(2));
        } else {
            throw new IllegalArgumentException("Bad range specifier \"" + arg
                    + "\"");

        }
    }

}
