package agh.cs.actparser.parsers;

import agh.cs.actparser.IElementRegistry;

import java.util.List;
import java.util.regex.Matcher;

public abstract class TitledElementParser extends AbstractParser {
    String title;

    public TitledElementParser(List<String> linesToParse,
                               List<IElementRegistry> registries) {
        super(linesToParse, registries);
    }

    @Override
    protected void parseStructure(List<String> linesToParse) {
        Matcher startMatcher = getStartPattern().matcher(linesToParse.get(0));

        // locate capture groups
        startMatcher.find();
        if (startMatcher.groupCount() < 2) {
            throw new IllegalArgumentException(
                    "Given content cannot be parsed as " + getKind());
        }

        idString = startMatcher.group(1);
        String textAfterIdentifier = startMatcher.group(2);

        if(!textAfterIdentifier.trim().isEmpty()){
            // Title inline
            title = textAfterIdentifier;
            bodyLines = linesToParse.subList(1, linesToParse.size());
        } else {
            title = linesToParse.get(1);

            if(linesToParse.size() < 2) {
                throw new IllegalArgumentException("Titled element has no " +
                        "title!");
            }
            bodyLines = linesToParse.subList(2, linesToParse.size());
        }
    }
}
