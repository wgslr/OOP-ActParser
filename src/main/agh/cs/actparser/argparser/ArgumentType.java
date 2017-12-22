package agh.cs.actparser.argparser;

import com.sun.org.apache.xpath.internal.Arg;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.InvalidPropertiesFormatException;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ArgumentType {
    Number,
    Range,
    Text;

    public Function<String, ?> getParser() {
        switch (this) {
            case Number:
                return Integer::parseInt;
            case Range:
                return this::RangeParser;
            case Text:
                return Function.identity();
        }
    }

    private Object RangeParser(String arg) {
        Pattern pattern = Pattern.compile("(\\d+)..(\\d+)");
        Matcher m = pattern.matcher(arg);
        if (m.matches()) {

        } else {
            throw new IllegalArgumentException("Bad range specifier \"" + arg
                    + "\"");

        }
    }

}
