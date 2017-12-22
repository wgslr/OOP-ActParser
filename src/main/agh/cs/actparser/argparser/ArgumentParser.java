package agh.cs.actparser.argparser;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    }

    public Option getOptionByName(String name) {
        return nameToOption.get(name);
    }

    public void parse(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            Option param = matchOption(args[i]);
            if(param.type)
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

}
