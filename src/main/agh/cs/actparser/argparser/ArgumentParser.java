package agh.cs.actparser.argparser;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.awt.*;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ArgumentParser {

    public static class Option {
        public final String name;
        public final String shortName;
        public final String description;
        /**
         * Should be null for boolean flags
         */
        public final Function<String, ?> parser;

        public Option(String name, Function<String, ?> parser) {
            this.name = name;
            this.shortName = "";
            this.description = "";
            this.parser = parser;
        }

        public Option(String name, String shortName, String description,
                      Function<String, ?> parser) {
            this.name = name;
            this.shortName = shortName;
            this.description = description;
            this.parser = parser;
        }

        @Override
        public String toString() {
            return (shortName.isEmpty() ? "   " : "-" + shortName + " ")
                    + "--" + name
                    + "\t" + description;
        }
    }

    private HashMap<String, Option> nameToOption = new HashMap<>();
    private HashMap<String, Option> shortNameToOption = new HashMap<>();
    private HashMap<String, Object> nameToResult = new HashMap<>();

    public void addOption(Option option) {
        if (nameToOption.containsKey(option.name)) {
            throw new IllegalArgumentException("Option with name \"" + option
                    .name + "\" already added.");
        }
        if (shortNameToOption.containsKey(option.shortName)) {
            throw new IllegalArgumentException("Option with short name \"" +
                    option
                            .name + "\" already added.");
        }

        nameToOption.put(option.name, option);
        shortNameToOption.put(option.shortName, option);

        if(option.parser == null) {
            // boolean
            nameToResult.put(option.name, false);
        }
    }

    public Option getOptionByName(String name) {
        return nameToOption.get(name);
    }

    public void parse(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            Option param = matchOption(args[i]);

            if (param.parser == null) {
                nameToResult.put(param.name, true);
            } else {
                if (args.length <= i + 1) {
                    throw new IllegalArgumentException(
                            "Missing value for argument \"" + args[i] + "\"");
                }

                nameToResult.put(param.name, param.parser.apply(args[i + 1]));
                ++i;
            }

        }
    }

    public Option matchOption(String arg) {
        boolean isFullname = arg.startsWith("--");
        HashMap<String, Option> dictionary;
        Pattern pattern;

        if (isFullname) {
            pattern = Pattern.compile("--(\\p{L}+)");
            dictionary = nameToOption;
        } else {
            pattern = Pattern.compile("-(\\p{L})");
            dictionary = shortNameToOption;
        }

        Matcher m = pattern.matcher(arg);

        Option searchResult = Optional.of(m)
                .map(x -> m.matches() ? m : null)
                .map(x -> dictionary.get(m.group(1)))
                .orElseThrow(() -> new IllegalArgumentException
                        (String.format("\"%s\" is not a valid argument name",
                                arg)));

        return searchResult;
    }

    public <T> T getResult(String name) {
        return (T)nameToResult.get(name);
    }

    public boolean isSet(String name) {
        return nameToResult.containsKey(name);
    }

    public String getArgsHelp() {
        return nameToOption.values().stream()
                .sorted((x, y) -> x.name.compareToIgnoreCase(y.name))
                .map(opt -> opt.toString())
                .collect(Collectors.joining("\n"));
    }

}
