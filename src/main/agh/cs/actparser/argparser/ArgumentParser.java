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
        public final ArgumentType type;

        public Option(String name, ArgumentType type) {
            this.name = name;
            this.shortName = "";
            this.description = "";
            this.type = type;
        }

        public Option(String name, String shortName, String description,
                      ArgumentType type) {
            this.name = name;
            this.shortName = shortName;
            this.description = description;
            this.type = type;
        }

        @Override
        public String toString() {
            return (shortName.isEmpty() ? "" : "-" + shortName + " ")
                    + "--" + name
                    + " " + description
                    + " (" + type.toString() + ")";
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

        if(option.type == ArgumentType.Bool) {
            // default value
            nameToResult.put(option.name, false);
        }
    }

    public Option getOptionByName(String name) {
        return nameToOption.get(name);
    }

    public void parse(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            Option param = matchOption(args[i]);

            if (param.type == ArgumentType.Bool) {
                nameToResult.put(param.name, true);
            } else {
                if (args.length <= i + 1) {
                    throw new IllegalArgumentException(
                            "Missing value for argument \"" + args[i] + "\"");
                }

                Function<String, Object> parser = param.type.getParser();
                nameToResult.put(param.name, parser.apply(args[i + 1]));
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

    public Object getResult(String name) {
        System.out.println(nameToResult);
        return nameToResult.get(name);
    }

    public String getArgsHelp() {
        return nameToOption.values().stream()
                .sorted((x, y) -> x.name.compareToIgnoreCase(y.name))
                .map(opt -> opt.toString())
                .collect(Collectors.joining("\n"));
    }

}
