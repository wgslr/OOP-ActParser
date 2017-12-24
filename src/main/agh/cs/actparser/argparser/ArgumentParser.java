package agh.cs.actparser.argparser;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Handles parsing of options passed as command line arguments.
 */
public class ArgumentParser {

    public static class Option {
        public final String name;
        public final String shortName;
        public final String description;
        /**
         * Should be null for boolean flags
         */
        public final Function<String, ?> parser;

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

    /**
     * Mapping from option full name to its provider value.
     */
    private HashMap<String, Object> nameToResult = new HashMap<>();

    /**
     * Adds Option to be recognized by parser.
     * @param option Option to add
     * @throws IllegalArgumentException When added Option already exists
     */
    public void addOption(Option option) {
        if (nameToOption.containsKey(option.name)) {
            throw new IllegalArgumentException(
                    "Option with name \"" + option.name +
                            "\" is already defined");
        }
        if (shortNameToOption.containsKey(option.shortName)) {
            throw new IllegalArgumentException(
                    "Option with short name \"" + option.name +
                            "\" is already defined");
        }

        nameToOption.put(option.name, option);
        shortNameToOption.put(option.shortName, option);

        if (option.parser == null) {
            // boolean
            nameToResult.put(option.name, false);
        }
    }

    /**
     * Sets options values according to given args.
     * @param args Provider arguments
     * @throws IllegalArgumentException if given args contain unexpected value
     */
    public void parse(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            Option param = matchOption(args[i]);

            if (param.parser == null) {
                // boolean flag
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

    /**
     * Identifies option by its long or short name as given on command line.
     * @param arg
     * @return Found Option object
     * @throws IllegalArgumentException If
     */
    private Option matchOption(String arg) {
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

    /**
     * Returns result associated with given full option name.
     * @param name Full option name
     * @param <T> Type of option's value
     * @return Found result
     */
    public <T> T getResult(String name) {
        return (T) nameToResult.get(name);
    }

    /**
     * Checks if given option was provided by the user
     * @param name
     * @return True if option's value is known
     */
    public boolean isSet(String name) {
        return nameToResult.containsKey(name);
    }

    /**
     * @return Help string for the application
     */
    public String getArgsHelp() {
        String bottom = "\nAvailable input formats with examples:\n"
                + "Range: 1..4 or I..IV or 2 (equivalent to 2..2)\n"
                + "Identifier: 1\n"
                + "String: konstytucja.txt\n"
                + "Flag: (does not require any value)\n"
                + "\nExample invocation: \n"
                + "-a 2 -u 2 -p 2 -l a -f ../../assets/uokik.txt";

        return nameToOption.values().stream()
                .sorted((x, y) -> x.name.compareToIgnoreCase(y.name))
                .map(opt -> opt.toString())
                .collect(Collectors.joining("\n"))
                + bottom;
    }

}
